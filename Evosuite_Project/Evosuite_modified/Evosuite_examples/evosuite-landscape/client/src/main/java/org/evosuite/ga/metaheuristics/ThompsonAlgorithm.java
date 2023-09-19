package org.evosuite.ga.metaheuristics;

import org.evosuite.Properties;
import org.evosuite.utils.LoggingUtils;

import java.io.*;

public class ThompsonAlgorithm {
    //Local Search configuration
    double lscrossover_rate= Properties.CROSSOVER_RATE;
    double lsmutation_rate = Properties.MUTATION_RATE;
    double lstruncation_rate = Properties. TRUNCATION_RATE;
    double lsp_test_insert = Properties. P_TEST_INSERTION;
    double lsp_statement_insert=Properties.P_STATEMENT_INSERTION;
    double lsp_change_parameter = Properties.P_CHANGE_PARAMETER;
    double lsp_test_delete = Properties.P_TEST_DELETE;
    double lsp_test_change = Properties.P_TEST_CHANGE;
    double lsip_test_insert = Properties.P_TEST_INSERT;


    public int execThom(){

        try {
            BufferedReader reader = new BufferedReader(new FileReader("swap.txt"));
            String content = reader.readLine();
            lsmutation_rate=Float.parseFloat(content);
            //lscrossover_rate=Float.parseFloat(content);


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoggingUtils.getEvoLogger().info("configurations");
        LoggingUtils.getEvoLogger().info("CROSSOVER_RATE:"+lscrossover_rate);
        LoggingUtils.getEvoLogger().info("MUTATION_RATE in props_be:" + Properties.MUTATION_RATE);
        LoggingUtils.getEvoLogger().info("MUTATION_RATE:"+lsmutation_rate);
        LoggingUtils.getEvoLogger().info("MUTATION_RATE in props_af:" + Properties.MUTATION_RATE);
        LoggingUtils.getEvoLogger().info("TRUNCATION_RATE:"+lstruncation_rate);
        LoggingUtils.getEvoLogger().info("P_TEST_INSERTION:"+lsp_test_insert);
        LoggingUtils.getEvoLogger().info("P_STATEMENT_INSERTION:"+lsp_statement_insert);
        LoggingUtils.getEvoLogger().info("P_CHANGE_PARAMETER:"+lsp_change_parameter);
        LoggingUtils.getEvoLogger().info("P_TEST_DELETE:"+lsp_test_delete);
        LoggingUtils.getEvoLogger().info("P_TEST_CHANGE:"+lsp_test_change);
        LoggingUtils.getEvoLogger().info("P_TEST_INSERT:"+lsip_test_insert);



//        pass values

        Properties.CROSSOVER_RATE=lscrossover_rate;
        Properties.MUTATION_RATE = lsmutation_rate;
        LoggingUtils.getEvoLogger().info("MUTATION_RATE in props2:" + Properties.MUTATION_RATE);
        Properties.TRUNCATION_RATE =  lstruncation_rate  ;
        Properties.P_TEST_INSERTION = lsp_test_insert  ;
        Properties.P_STATEMENT_INSERTION = lsp_statement_insert;
        Properties.P_CHANGE_PARAMETER = lsp_change_parameter  ;
        Properties.P_TEST_DELETE = lsp_test_delete  ;
        Properties.P_TEST_CHANGE = lsp_test_change  ;
        Properties.P_TEST_INSERT = lsp_test_insert;



        return 0;

    }





}
