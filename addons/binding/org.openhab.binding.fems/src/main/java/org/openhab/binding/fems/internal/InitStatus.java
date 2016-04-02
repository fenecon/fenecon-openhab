/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.openhab.binding.fems.FemsBindingConstants;
import org.openhab.binding.fems.internal.types.IO;
import org.openhab.binding.fems.internal.types.IOUserLed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a string in the form "---" up to "XXX" and manages Beaglebone UserLED
 * 1st char: IP
 * 2nd char: Internet
 * 3rd char: Modbus
 *
 * @author Stefan Feilmeier
 */
public class InitStatus {
    private volatile boolean ip = false;
    private volatile boolean internet = false;
    private volatile boolean modbus = false;
    private volatile boolean initialized = false;
    private volatile InitLog log = new InitLog(InitStatus.class);
    private Logger logger = LoggerFactory.getLogger(InitStatus.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> job;
    private boolean blinkOn = false;

    public InitStatus() {
        setIp(false);
        setInternet(false);
        setModbus(false);
        startStatusWatcher();
    }

    private void startStatusWatcher() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    OnOffType newStatus = blinkOn ? OnOffType.ON : OnOffType.OFF;
                    blinkOn = !blinkOn;
                    if (!getIp()) {
                        setUserLed(FemsBindingConstants.UserLED_1, newStatus);
                    }
                    if (!getInternet()) {
                        setUserLed(FemsBindingConstants.UserLED_2, newStatus);
                    }
                    if (!getModbus()) {
                        setUserLed(FemsBindingConstants.UserLED_3, newStatus);
                    }
                    checkInitialized();
                } catch (Exception e) {
                    logger.debug("Exception occurred during execution: {}", e.getMessage(), e);
                }
            }
        };

        job = scheduler.scheduleAtFixedRate(runnable, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void dispose() {
        if (job != null) {
            job.cancel(true);
        }
    }

    public boolean getIp() {
        return ip;
    }

    public void setIp(boolean ip) {
        this.ip = ip;
        if (ip) {
            setUserLed(FemsBindingConstants.UserLED_1, OnOffType.ON);
        }
        checkInitialized();
    }

    public boolean getInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
        if (internet) {
            setUserLed(FemsBindingConstants.UserLED_2, OnOffType.ON);
        }
        checkInitialized();
    }

    public void setModbus(boolean modbus) {
        this.modbus = modbus;
        if (modbus) {
            setUserLed(FemsBindingConstants.UserLED_3, OnOffType.ON);
            // TODO: deactivate again, if setModbus(true) is not called for a certain period
        }
        checkInitialized();
    }

    public Boolean getModbus() {
        return modbus;
    }

    private void checkInitialized() {
        if (ip && internet && modbus) {
            setUserLed(FemsBindingConstants.UserLED_4, OnOffType.ON);
            this.initialized = true;
        } else {
            setUserLed(FemsBindingConstants.UserLED_4, OnOffType.OFF);
            this.initialized = false;
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String toString() {
        return (ip ? "X" : "-") + (internet ? "X" : "-") + (modbus ? "X" : "-");
    }

    public void logInfo(String text) {
        log.info(text);
    }

    public void logError(String text) {
        log.error(text);
    }

    private synchronized void setUserLed(String id, OnOffType type) {
        IO io = FemsBindingConstants.IOS.get(id);
        if (io != null && io instanceof IOUserLed) {
            IOUserLed userLed = (IOUserLed) io;
            userLed.handleCommand(type);
        }
    }
}