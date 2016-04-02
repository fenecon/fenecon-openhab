/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class FemsYaler {
    private static final String serviceFileName = "fems-yalertunnel.service";
    private static final Path serviceFile = Paths.get("/lib/systemd/system/", serviceFileName);
    private static final Path systemctlExecutable = Paths.get("/bin/systemctl");
    private static final Path yalerExecutable = Paths.get("/usr/bin/fems-yalertunnel");
    private static Logger logger = LoggerFactory.getLogger(FemsYaler.class);

    private static boolean serviceIsActive = false;
    private static FemsYaler femsYaler = null;
    private static Lock systemctlLock = new ReentrantLock();

    public static FemsYaler getFemsYaler() {
        if (femsYaler == null) {
            femsYaler = new FemsYaler();
        }
        return femsYaler;
    }

    public FemsYaler() {
        /* get status information */
        Runtime rt = Runtime.getRuntime();
        Process proc;
        try {
            systemctlLock.lock();
            proc = rt.exec(systemctlExecutable + " is-active " + serviceFileName + " --quiet");
            if (proc.waitFor() == 0) {
                serviceIsActive = true;
            } else {
                serviceIsActive = false;
            }
        } catch (InterruptedException | IOException e) {
            logger.warn("Status of yaler service is unknown: " + e.getMessage());
        } finally {
            systemctlLock.unlock();
        }
        logger.info("Yaler service status is " + (serviceIsActive ? "online" : "offline"));
    }

    public boolean isActive() {
        return serviceIsActive;
    }

    /**
     * activate tunnel. Return true, if it was actually activated and was not already activated before
     *
     * @param relayDomain
     * @throws Exception
     */
    public boolean activateTunnel(String relayDomain) throws Exception {
        /* do nothing if service is active */
        if (serviceIsActive) {
            return false;
        }

        /* check if relayDomain is consistent */
        if (!relayDomain.matches("fenecon-\\w{4}-\\w{4}")) {
            throw new Exception("Invalid relayDomain: " + relayDomain);
        }
        ;
        /* stop service if it is running */
        Runtime rt = Runtime.getRuntime();
        Process proc;
        systemctlLock.lock();
        proc = rt.exec(systemctlExecutable + " stop " + serviceFileName);
        proc.waitFor();
        systemctlLock.unlock();

        /* remove old serviceFile */
        try {
            Files.deleteIfExists(serviceFile);
        } catch (IOException e) {
            logger.info("Unable to deleteIfExists " + serviceFile + ": " + e.getMessage());
        }
        /* create unitFile */
        String unitText = "[Unit]\n" + "Description=FEMS yalertunnel on port 22\n" + "\n" + "[Service]\n" + "ExecStart="
                + yalerExecutable + " proxy 127.0.0.1:22 via.yaler.net:80 " + relayDomain + "\n" + "RestartSec=30\n"
                + "Restart=always\n" + "\n" + "[Install]\n" + "WantedBy=multi-user.target\n";
        Files.write(serviceFile, unitText.getBytes());
        /* enable service */
        systemctlLock.lock();
        proc = rt.exec(systemctlExecutable + " enable " + serviceFileName);
        int success = proc.waitFor();
        systemctlLock.unlock();
        if (success != 0) {
            throw new Exception("Unable to enable yaler service");
        }
        /* start service */
        systemctlLock.lock();
        proc = rt.exec(systemctlExecutable + " start " + serviceFileName);
        success = proc.waitFor();
        systemctlLock.unlock();
        if (success != 0) {
            throw new Exception("Unable to start yaler service");
        }
        /* set as active */
        serviceIsActive = true;
        logger.info("Yalertunnel is now activated");

        return true;
    }

    /**
     * deactivate tunnel. Return true, if it was actually deactivated and was not already deactivated before
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean deactivateTunnel() throws IOException, InterruptedException {
        /* do nothing if service is not active */
        if (!serviceIsActive) {
            return false;
        }

        /* stop service */
        Runtime rt = Runtime.getRuntime();
        Process proc;
        systemctlLock.lock();
        proc = rt.exec(systemctlExecutable + " stop " + serviceFileName);
        int success = proc.waitFor();
        systemctlLock.unlock();
        if (success != 0) {
            logger.info("Unable to stop yaler service");
        }
        /* disable service */
        rt = Runtime.getRuntime();
        systemctlLock.lock();
        proc = rt.exec(systemctlExecutable + " disable " + serviceFileName);
        success = proc.waitFor();
        systemctlLock.unlock();
        if (success != 0) {
            logger.info("Unable to disable yaler service");
        }
        /* delete service file */
        try {
            Files.deleteIfExists(serviceFile);
        } catch (IOException e) {
            logger.info("Unable to deleteIfExists " + serviceFile + ": " + e.getMessage());
        }
        /* set as inactive */
        serviceIsActive = false;
        logger.info("Yalertunnel is now deactivated");

        return true;
    }
}
