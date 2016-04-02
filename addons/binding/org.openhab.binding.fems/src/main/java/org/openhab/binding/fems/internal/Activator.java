/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.Pwm;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.fems.FemsBindingConstants;
import org.openhab.binding.fems.internal.types.IO;
import org.openhab.binding.fems.internal.types.IOAnalogOutput;
import org.openhab.binding.fems.internal.types.IOAnalogOutputCtrlVolt;
import org.openhab.binding.fems.internal.types.IODigitalOutput;
import org.openhab.binding.fems.internal.types.IOLcd;
import org.openhab.binding.fems.internal.types.IOLcdBacklight;
import org.openhab.binding.fems.internal.types.IOLcdRow;
import org.openhab.binding.fems.internal.types.IOOutput;
import org.openhab.binding.fems.internal.types.IOUserLed;
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
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> job = null;
    private final FemsInititializer initializer;
    private final DeviceType deviceType;

    public Activator() {
        // TODO: /etc/fems auslesen und devicetype lesen
        // read FEMS properties from /etc/fems
        /*
         * Properties properties = new Properties();
         * BufferedInputStream stream = null;
         * try {
         * stream = new BufferedInputStream(new FileInputStream("/etc/fems"));
         * properties.load(stream);
         * apikey = properties.getProperty("apikey");
         * ess = properties.getProperty("ess", "dess");
         * debug = Boolean.parseBoolean(properties.getProperty("debug", "false"));
         * if (stream != null)
         * stream.close();
         * } catch (IOException e) {
         * log.error(e.getMessage());
         * }
         */

        this.deviceType = DeviceType.FEMS_V1;
        try {
            Board bbb = Platform.createBoard();
            initHardwareMap(bbb, deviceType);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        initializer = new FemsInititializer();
    }

    @Override
    public void start(BundleContext context) throws Exception {
        logger.debug("FEMS Test has been started.");
        job = executor.submit(initializer);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        try {
            job.cancel(true);
        } catch (Exception e) {
            ;
        }
        job = null;
        logger.debug("FEMS Test has been stopped.");
    }

    public void initHardwareMap(Board bbb, DeviceType deviceType) {
        // FEMS v1 only
        if (deviceType.equals(DeviceType.FEMS_V1)) {
            // LCD Display
            IOLcd lcd = new IOLcd(bbb.getPin(BBBNames.P9_15).as(DigitalIO.class), // rs pin
                    bbb.getPin(BBBNames.P9_23).as(DigitalIO.class), // rw pin
                    bbb.getPin(BBBNames.P9_12).as(DigitalIO.class), // enable pin
                    bbb.getPin(BBBNames.P8_30).as(DigitalIO.class), // db 4
                    bbb.getPin(BBBNames.P8_28).as(DigitalIO.class), // db 5
                    bbb.getPin(BBBNames.P8_29).as(DigitalIO.class), // db 6
                    bbb.getPin(BBBNames.P8_27).as(DigitalIO.class)); // db 7
            FemsBindingConstants.IOS.put(FemsBindingConstants.LCD_1, new IOLcdRow(lcd, FemsBindingConstants.LCD_1));
            FemsBindingConstants.IOS.put(FemsBindingConstants.LCD_2, new IOLcdRow(lcd, FemsBindingConstants.LCD_2));
            FemsBindingConstants.IOS.put(FemsBindingConstants.LCD_Backlight,
                    new IOLcdBacklight(OnOffType.ON, bbb.getPin(BBBNames.P9_22).as(Pwm.class)));

            // Relay Outputs
            FemsBindingConstants.IOS.put(FemsBindingConstants.RelayOutput_1,
                    new IODigitalOutput(bbb.getPin(BBBNames.P8_12).as(DigitalOutput.class), OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.RelayOutput_2,
                    new IODigitalOutput(bbb.getPin(BBBNames.P8_11).as(DigitalOutput.class), OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.RelayOutput_3,
                    new IODigitalOutput(bbb.getPin(BBBNames.P8_16).as(DigitalOutput.class), OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.RelayOutput_4,
                    new IODigitalOutput(bbb.getPin(BBBNames.P8_15).as(DigitalOutput.class), OnOffType.OFF));

            // Analog Outputs
            Map<String, IOAnalogOutputCtrlVolt> aovs = new HashMap<String, IOAnalogOutputCtrlVolt>();
            aovs.put(FemsBindingConstants.AnalogOutput_1_Volt,
                    new IOAnalogOutputCtrlVolt(bbb.getPin(BBBNames.P9_28).as(DigitalOutput.class), OnOffType.ON));
            aovs.put(FemsBindingConstants.AnalogOutput_2_Volt,
                    new IOAnalogOutputCtrlVolt(bbb.getPin(BBBNames.P9_29).as(DigitalOutput.class), OnOffType.ON));
            aovs.put(FemsBindingConstants.AnalogOutput_3_Volt,
                    new IOAnalogOutputCtrlVolt(bbb.getPin(BBBNames.P9_30).as(DigitalOutput.class), OnOffType.ON));
            aovs.put(FemsBindingConstants.AnalogOutput_4_Volt,
                    new IOAnalogOutputCtrlVolt(bbb.getPin(BBBNames.P9_31).as(DigitalOutput.class), OnOffType.ON));
            FemsBindingConstants.IOS.putAll(aovs);

            try {
                FemsBindingConstants.IOS.put(FemsBindingConstants.AnalogOutput_1,
                        new IOAnalogOutput(bbb.getPin(BBBNames.EHRPWM1A_P9_14).as(Pwm.class), OnOffType.OFF,
                                aovs.get(FemsBindingConstants.AnalogOutput_1_Volt)));
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
            try {
                FemsBindingConstants.IOS.put(FemsBindingConstants.AnalogOutput_2,
                        new IOAnalogOutput(bbb.getPin(BBBNames.EHRPWM1B_P9_16).as(Pwm.class), OnOffType.OFF,
                                aovs.get(FemsBindingConstants.AnalogOutput_2_Volt)));
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
            try {
                FemsBindingConstants.IOS.put(FemsBindingConstants.AnalogOutput_3,
                        new IOAnalogOutput(bbb.getPin(BBBNames.EHRPWM2A_P8_19).as(Pwm.class), OnOffType.OFF,
                                aovs.get(FemsBindingConstants.AnalogOutput_3_Volt)));
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
            try {
                FemsBindingConstants.IOS.put(FemsBindingConstants.AnalogOutput_4,
                        new IOAnalogOutput(bbb.getPin(BBBNames.EHRPWM2B_P8_13).as(Pwm.class), OnOffType.OFF,
                                aovs.get(FemsBindingConstants.AnalogOutput_4_Volt)));
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        }

        // FEMS v1 and Beaglebone
        if (deviceType.equals(DeviceType.FEMS_V1) || deviceType.equals(DeviceType.BEAGLEBONE)) {
            // BeagleBone User LEDs
            FemsBindingConstants.IOS.put(FemsBindingConstants.UserLED_1,
                    new IOUserLed(FemsBindingConstants.UserLED_1, OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.UserLED_2,
                    new IOUserLed(FemsBindingConstants.UserLED_2, OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.UserLED_3,
                    new IOUserLed(FemsBindingConstants.UserLED_3, OnOffType.OFF));
            FemsBindingConstants.IOS.put(FemsBindingConstants.UserLED_4,
                    new IOUserLed(FemsBindingConstants.UserLED_4, OnOffType.OFF));
        }
    }

    public void handleCommand(String id, Command command) {
        logger.info("FEMS Activator: handle command " + id + " : " + command.toString());
        IO io = FemsBindingConstants.IOS.get(id);
        if (io != null) {
            if (io instanceof IOOutput) {
                IOOutput ioOutput = (IOOutput) io;
                ioOutput.handleCommand(command);
            }
        }
    }
}
