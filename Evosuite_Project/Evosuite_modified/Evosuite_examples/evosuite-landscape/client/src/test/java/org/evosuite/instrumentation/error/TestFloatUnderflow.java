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
package org.evosuite.instrumentation.error;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestFloatUnderflow {

    private float x;
    private float y;

    public TestFloatUnderflow(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Creates the test data
    @Parameters
    public static Collection<Object[]> data() {
        Object[] values = new Object[]{(-Float.MAX_VALUE), (-Float.MAX_VALUE) / 2, 0, Float.MAX_VALUE / 2, Float.MAX_VALUE};
        List<Object[]> valuePairs = new ArrayList<>();
        for (Object val1 : values) {
            for (Object val2 : values) {
                valuePairs.add(new Object[]{val1, val2});
            }
        }
        return valuePairs;
    }

    private void assertUnderflow(double doubleResult, int distance, float floatResult) {
        if (doubleResult < -Float.MAX_VALUE) {
            assertTrue("Expected negative value for " + x + " and " + y + ": " + distance, distance < 0);
            assertEquals(Float.NEGATIVE_INFINITY, floatResult, 0.0F);
        } else {
            assertTrue("Expected positive value for " + x + " and " + y + ": " + distance, distance >= 0);
        }
    }


    @Test
    public void testAddUnderflow() {
        int result = ErrorConditionChecker.underflowDistance(x, y, Opcodes.FADD);
        assertUnderflow((double) x + (double) y, result, x + y);
    }

    @Test
    public void testSubUnderflow() {
        int result = ErrorConditionChecker.underflowDistance(x, y, Opcodes.FSUB);
        assertUnderflow((double) x - (double) y, result, x - y);
    }

    @Test
    public void testMulUnderflow() {
        int result = ErrorConditionChecker.underflowDistance(x, y, Opcodes.FMUL);
        assertUnderflow((double) x * (double) y, result, x * y);
    }

    @Test
    public void testDivUnderflow() {
        Assume.assumeTrue(y != 0F);

        int result = ErrorConditionChecker.underflowDistance(x, y, Opcodes.FDIV);
        assertUnderflow((double) x / (double) y, result, x / y);
    }
}
