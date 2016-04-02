/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecononlinemonitoring.internal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.fenecononlinemonitoring.FeneconOnlineMonitoringBindingConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class Activator implements BundleActivator {

    private final Logger logger = LoggerFactory.getLogger(Activator.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Future<?> job = null;
    private OnlineMonitoringConnector onlineMonitoring;

    @Override
    public void start(BundleContext context) throws Exception {
        Properties properties = new Properties();
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream("/etc/fems"));
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String apikey = properties.getProperty("apikey"); // null if not read
        if (apikey == null) {
            logger.error("Apikey is missing!");
            onlineMonitoring = null;
        } else {
            onlineMonitoring = new OnlineMonitoringConnector(apikey);
        }
        OnlineMonitoringConnector.offer(new TimedItemState<String>(
                FeneconOnlineMonitoringBindingConstants.FEMS_SYSTEMMESSAGE, "openHAB started"));
        InetAddress ipv4 = getCurrentIPaddress();
        if (ipv4 != null) {
            OnlineMonitoringConnector.offer(new TimedItemState<String>("ipv4", ipv4.getHostAddress()));
        }
        // TODO: send InitState from fems binding

        if (onlineMonitoring != null) {
            logger.info("FENECON Online-Monitoring has been started.");
            job = scheduler.scheduleAtFixedRate(onlineMonitoring, 0, 60, TimeUnit.SECONDS);
        } else {
            logger.info("FENECON Online-Monitoring has been started - but Apikey was missing");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        try {
            job.cancel(true);
        } catch (Exception e) {
            ;
        }
        job = null;
        if (onlineMonitoring != null) {
            onlineMonitoring.dispose();
        }
        logger.info("FENECON Online-Monitoring has been stopped.");
    }

    /**
     * Gets current IPv4 network address
     *
     * @return
     */
    private InetAddress getCurrentIPaddress() {
        try {
            NetworkInterface n = NetworkInterface.getByName("eth0"); // can be null -> throw NullPointerException in
                                                                     // next line
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = ee.nextElement();
                if (i instanceof Inet4Address) {
                    return i;
                }
            }
        } catch (Throwable e) {
            /* no IP-Address */ }
        return null;
    }
}
