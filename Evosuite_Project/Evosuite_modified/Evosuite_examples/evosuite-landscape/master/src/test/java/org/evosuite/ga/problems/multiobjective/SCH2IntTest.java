/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ga.problems.multiobjective;

import org.evosuite.Properties;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.NSGAChromosome;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.ga.metaheuristics.NSGAII;
import org.evosuite.ga.metaheuristics.RandomFactory;
import org.evosuite.ga.operators.crossover.SBXCrossover;
import org.evosuite.ga.operators.selection.BinaryTournamentSelectionCrowdedComparison;
import org.evosuite.ga.problems.Problem;
import org.evosuite.ga.problems.metrics.GenerationalDistance;
import org.evosuite.ga.problems.metrics.Metrics;
import org.evosuite.ga.problems.metrics.Spacing;
import org.evosuite.ga.variables.DoubleVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingDouble;

/**
 * @author José Campos
 */
public class SCH2IntTest {
    @Before
    public void setUp() {
        Properties.POPULATION = 100;
        Properties.STOPPING_CONDITION = Properties.StoppingCondition.MAXGENERATIONS;
        Properties.SEARCH_BUDGET = 10_000;
        Properties.CROSSOVER_RATE = 0.9;
        Properties.RANDOM_SEED = 1L;
    }

    @Test
    public void testSCH2Fitnesses() {
        Problem<NSGAChromosome> p = new SCH2();
        FitnessFunction<NSGAChromosome> f1 = p.getFitnessFunctions().get(0);
        FitnessFunction<NSGAChromosome> f2 = p.getFitnessFunctions().get(1);

        double[] values_n = {-3.0};
        NSGAChromosome c = new NSGAChromosome(-5.0, 10.0, values_n);
        Assert.assertEquals(((DoubleVariable) c.getVariables().get(0)).getValue(), -3.0, 0.0);
        Assert.assertEquals(f1.getFitness(c), 3.0, 0.0);
        Assert.assertEquals(f2.getFitness(c), 64.0, 0.0);

        double[] values_z = {0.0};
        c = new NSGAChromosome(-5.0, 10.0, values_z);
        Assert.assertEquals(((DoubleVariable) c.getVariables().get(0)).getValue(), 0.0, 0.0);
        Assert.assertEquals(f1.getFitness(c), 0.0, 0.0);
        Assert.assertEquals(f2.getFitness(c), 25.0, 0.0);

        double[] values_p = {9.0};
        c = new NSGAChromosome(-5.0, 10.0, values_p);
        Assert.assertEquals(((DoubleVariable) c.getVariables().get(0)).getValue(), 9.0, 0.0);
        Assert.assertEquals(f1.getFitness(c), 5.0, 0.0);
        Assert.assertEquals(f2.getFitness(c), 16.0, 0.0);
    }

    /**
     * Testing NSGA-II with SCH2 Problem
     *
     * @throws IOException
     * @throws NumberFormatException
     */
    @Test
    public void testSCH2() throws NumberFormatException, IOException {
        Properties.MUTATION_RATE = 1d / 1d;

        ChromosomeFactory<NSGAChromosome> factory = new RandomFactory(false, 1, -5.0, 10.0);

        GeneticAlgorithm<NSGAChromosome> ga = new NSGAII<>(factory);
        BinaryTournamentSelectionCrowdedComparison<NSGAChromosome> ts =
                new BinaryTournamentSelectionCrowdedComparison<>();
        ts.setMaximize(false);
        ga.setSelectionFunction(ts);
        ga.setCrossOverFunction(new SBXCrossover());

        Problem<NSGAChromosome> p = new SCH2();
        final FitnessFunction<NSGAChromosome> f1 = p.getFitnessFunctions().get(0);
        final FitnessFunction<NSGAChromosome> f2 = p.getFitnessFunctions().get(1);
        ga.addFitnessFunction(f1);
        ga.addFitnessFunction(f2);

        // execute
        ga.generateSolution();

        List<NSGAChromosome> chromosomes = new ArrayList<>(ga.getPopulation());
        chromosomes.sort(comparingDouble(chr -> chr.getFitness(f1)));

        double[][] front = new double[Properties.POPULATION][2];
        int index = 0;

        for (NSGAChromosome chromosome : chromosomes) {
            System.out.printf("%f,%f\n", chromosome.getFitness(f1), chromosome.getFitness(f2));
            front[index][0] = chromosome.getFitness(f1);
            front[index][1] = chromosome.getFitness(f2);

            index++;
        }

        // load True Pareto Front
        double[][] trueParetoFront = Metrics.readFront("Schaffer2.pf");

        GenerationalDistance gd = new GenerationalDistance();
        double gdd = gd.evaluate(front, trueParetoFront);
        System.out.println("GenerationalDistance: " + gdd);
        Assert.assertTrue(gdd < 0.001);

        Spacing sp = new Spacing();
        double spd = sp.evaluate(front);
        double spdt = sp.evaluate(trueParetoFront);
        // compute difference between the generated front and a theoretical ideal front
        // a difference of 0 (or any value < 1 and close to 0) means that the generated
        // front is similar to a theoretical ideal front
        double diff = Math.abs(spd - spdt);
        System.out.println("SpacingFront (" + spd + ") - SpacingTrueFront (" + spdt + ") = " + diff);
        Assert.assertTrue(diff < 0.60);
    }
}
