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
package com.examples.with.different.packagename.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class OpenStreamInATryCatch {

    private static final Logger logger = LoggerFactory.getLogger(OpenStreamInATryCatch.class);

    public boolean open(int x) {

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(OpenStream.FILE_NAME);
            logger.error("This should never be executed without a VFS");
        } catch (Exception e) {
            logger.error("Denied permission", e);
        }

        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error("open", e);
            }
        }

        if (x > 0) {
            return true;
        } else {
            return false;
        }
    }

}
