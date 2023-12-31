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
package com.examples.with.different.packagename.testcarver;

import org.junit.Assert;
import org.junit.Test;

public class ObjectWrapperSequenceTest {

    @Test
    public void test() {
        int x = 42;
        Foo foo = new Foo();
        foo.setX(x);

        ObjectWrapper wrapper = new ObjectWrapper();
        wrapper.set(foo);
        Assert.assertEquals(42, ((Foo) wrapper.get()).getX());
    }

    public static class Foo {
        private int x = 0;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

}
