/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class InitLog {
    private String logText = null;
    private final Logger logger;

    public InitLog(@SuppressWarnings("rawtypes") Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    private void addLog(String text) {
        if (logText == null) {
            logText = text;
        } else {
            logText += "\n" + text;
        }
    }

    public void info(String text) {
        logger.info(text);
        addLog(text);
    }

    public void error(String text) {
        logger.error(text);
        addLog("ERROR: " + text);
    }

    public String getLog() {
        return logText;
    }
}
