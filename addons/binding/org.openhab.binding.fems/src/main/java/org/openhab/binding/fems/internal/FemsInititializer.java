/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import org.eclipse.smarthome.core.library.types.StringType;
import org.openhab.binding.fems.FemsBindingConstants;
import org.openhab.binding.fems.internal.types.IO;
import org.openhab.binding.fems.internal.types.IOLcdRow;

/**
 *
 * @author Stefan Feilmeier
 */
public class FemsInititializer implements Runnable {
    // private final Logger logger = LoggerFactory.getLogger(FemsInititializer.class);

    // TODO: make own worker thread for IP/DHCP/Internet test;
    // TODO: Write latest IP address to item -> send to online monitoring

    @Override
    public void run() {

        FemsBindingConstants.initStatus.logInfo("FEMS Initialization");
        writeToLcd("FEMS Selbsttest");

        // receive IP address
        InetAddress ip = receiveIpAddress();
        if (ip == null) {
            FemsBindingConstants.initStatus.logError("No IP available");
            FemsBindingConstants.initStatus.setIp(false);
            writeToLcd("IP Fehler");
        } else {
            FemsBindingConstants.initStatus.logInfo("IP: " + ip.getHostAddress());
            FemsBindingConstants.initStatus.setIp(true);

            // check date + time
            if (isDateValid()) { /* date is already valid, so we check internet access only */
                FemsBindingConstants.initStatus
                        .logInfo("Date ok: " + FemsBindingConstants.LONG_DATE_FORMAT.format(new Date()));
                if (isInternetAvailable()) {
                    FemsBindingConstants.initStatus.setInternet(true);
                    FemsBindingConstants.initStatus.logInfo("Internet access is available");
                } else {
                    FemsBindingConstants.initStatus.setInternet(false);
                    FemsBindingConstants.initStatus.logError("Internet access is NOT available");
                    writeToLcd("Internet Fehler");
                }

            } else {
                FemsBindingConstants.initStatus
                        .logInfo("Date not ok: " + FemsBindingConstants.LONG_DATE_FORMAT.format(new Date()));
                try {
                    Runtime rt = Runtime.getRuntime();
                    Process proc = rt.exec(
                            "/usr/sbin/ntpdate -b -u fenecon.de 0.pool.ntp.org 1.pool.ntp.org 2.pool.ntp.org 3.pool.ntp.org");
                    proc.waitFor();
                    if (!isDateValid()) {
                        // try one more time
                        proc = rt.exec(
                                "/usr/sbin/ntpdate -b -u fenecon.de 0.pool.ntp.org 1.pool.ntp.org 2.pool.ntp.org 3.pool.ntp.org");
                        proc.waitFor();
                    }
                } catch (IOException | InterruptedException e) {
                    FemsBindingConstants.initStatus.logError(e.getMessage());
                }
                if (isDateValid()) {
                    FemsBindingConstants.initStatus.setInternet(true);
                    FemsBindingConstants.initStatus
                            .logInfo("Date now ok: " + FemsBindingConstants.LONG_DATE_FORMAT.format(new Date()));
                } else {
                    FemsBindingConstants.initStatus.setInternet(false);
                    FemsBindingConstants.initStatus
                            .logError("Wrong Date: " + FemsBindingConstants.LONG_DATE_FORMAT.format(new Date()));
                    writeToLcd("Datum Fehler");
                }
            }
        }

        // System update
        FemsBindingConstants.initStatus.logInfo("Start system update");
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("/usr/bin/fems-autoupdate");
        } catch (IOException e) {
            FemsBindingConstants.initStatus.logInfo(e.getMessage());
        }

        // TODO: remove yaler
        // Check if Yaler is active
        if (FemsYaler.getFemsYaler().isActive()) {
            FemsBindingConstants.initStatus.logInfo("Yaler is activated");
        } else {
            FemsBindingConstants.initStatus.logInfo("Yaler is deactivated");
        }
    }

    private InetAddress receiveIpAddress() {
        // check for valid ip address
        InetAddress ip = getCurrentIPaddress();
        if (ip == null) {
            Runtime rt = Runtime.getRuntime();
            Process proc;
            try {
                proc = rt.exec("/sbin/dhclient eth0");
                proc.waitFor();
                ip = getCurrentIPaddress(); /* try again */
            } catch (IOException | InterruptedException e) {
                return null;
            }
        }
        return ip;
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

    /**
     * Checks if the current system date is valid
     *
     * @return
     */
    private boolean isDateValid() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        if (year < 2014 || year > 2025) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isInternetAvailable() {
        try {
            URL url = new URL("https://fenecon.de");
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5000);
            con.getContent();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void writeToLcd(String text) {
        IO io = FemsBindingConstants.IOS.get(FemsBindingConstants.LCD_2);
        if (io != null && io instanceof IOLcdRow) {
            IOLcdRow lcdRow = (IOLcdRow) io;
            lcdRow.handleCommand(new StringType(text));
        }
    }
}
