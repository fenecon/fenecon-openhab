/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bulldog.core.gpio.DigitalIO;
import org.bulldog.devices.lcd.HD44780Compatible;
import org.bulldog.devices.lcd.LcdFont;
import org.bulldog.devices.lcd.LcdMode;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.fems.FemsBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class IOLcd {
    private final HD44780Compatible lcd;
    private Logger logger = LoggerFactory.getLogger(IOLcd.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> job;

    private ConcurrentLinkedQueue<String> secondRowQueue = new ConcurrentLinkedQueue<String>();
    private String currentFirstRow = "";
    private String currentSecondRow = "";
    private int seconds = 0;

    public IOLcd(DigitalIO rs, DigitalIO rw, DigitalIO enable, DigitalIO db4, DigitalIO db5, DigitalIO db6,
            DigitalIO db7) {
        lcd = new HD44780Compatible(rs, rw, enable, db4, db5, db6, db7);
        lcd.setMode(LcdMode.Display2x16, LcdFont.Font_5x8);
        lcd.blinkCursor(false);
        lcd.showCursor(false);
        lcd.clear(); // turn off by default
        startLcdClock();
    }

    private void startLcdClock() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Date now = new Date();
                    String longTimeString = FemsBindingConstants.LONG_TIME_FORMAT.format(now);
                    String initStatus = FemsBindingConstants.initStatus.toString();
                    String nextFirstRow = String.format("%-4s %-3s %-8s", "FEMS", initStatus, longTimeString);
                    if (!nextFirstRow.equals(currentFirstRow)) {
                        currentFirstRow = nextFirstRow;
                        lcd.writeAt(0, 0, String.format("%1$-16s", currentFirstRow));
                    }

                    seconds++;
                    if ((currentSecondRow.isEmpty() || seconds > 5) && !secondRowQueue.isEmpty()) {
                        seconds = 0;
                        String nextSecondRow = secondRowQueue.poll();
                        if (nextSecondRow == null) {
                            nextSecondRow = "";
                        }
                        if (!nextSecondRow.equals(currentSecondRow)) {
                            currentSecondRow = nextSecondRow;
                            lcd.writeAt(1, 0, String.format("%1$-16s", currentSecondRow));
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Exception occurred during execution: {}", e.getMessage(), e);
                }
            }
        };

        job = scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public void handleCommand(int row, Command command) {
        if (command instanceof StringType) {
            String commandString = ((StringType) command).toString();
            String shortTimeString = FemsBindingConstants.SHORT_TIME_FORMAT.format(new Date());
            secondRowQueue.offer(String.format("%-5s %-10s", shortTimeString, commandString));
        }
    }

    public void dispose() {
        if (job != null) {
            job.cancel(true);
        }
        lcd.clear();
    }

    public String getFirstRow() {
        return currentFirstRow;
    }

    public String getSecondRow() {
        return currentSecondRow;
    }
}
