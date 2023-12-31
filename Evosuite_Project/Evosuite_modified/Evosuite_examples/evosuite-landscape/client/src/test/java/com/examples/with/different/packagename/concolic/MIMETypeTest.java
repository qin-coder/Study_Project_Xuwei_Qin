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
package com.examples.with.different.packagename.concolic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MIMETypeTest {

    /**
     * Method used in org.evosuite.symbolic.TestMIMEType.
     *
     * @throws MalformedMIMETypeException
     */
    public static void test() throws MalformedMIMETypeException {
        new MIMEType("text/xml", false);
    }

    @Test
    public void mimeTest() throws MalformedMIMETypeException {
        new MIMEType("text/xml");
    }

    @Test
    public void mimeExceptionTest() {
        Exception exception = assertThrows(MalformedMIMETypeException.class, () -> {
            new MIMEType("xml");
        });

        String expectedMessage = "No '/' in mime-type (xml)!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
