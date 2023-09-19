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
package com.examples.with.different.packagename.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Andrea Arcuri on 13/09/15.
 */
public class ClassToCheckGenerators {

    private final static Logger logger = LoggerFactory.getLogger(ClassToCheckGenerators.class);


    public void bar(WithGenerator foo) {
        logger.debug("WithGenerator");
    }


    public void gi(IGeneratorForItself foo) {
        logger.debug("IGeneratorForItself");
    }

    public void xi(IX foo) {
        logger.debug("IX");
    }

    public void ga(AGeneratorForItself foo) {
        logger.debug("AGeneratorForItself");
    }

    public void xa(AX foo) {
        logger.debug("AX");
    }

    public void g(GeneratorForItself foo) {
        logger.debug("GeneratorForItself");
    }

    public void x(X foo) {
        logger.debug("X");
    }

    public void forceAnalysis(GeneratorForX gx) {
    }
}
