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
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.core.items.events.AbstractItemEventSubscriber;
import org.eclipse.smarthome.core.items.events.ItemStateEvent;
import org.openhab.binding.fems.FemsBindingConstants;

/**
 *
 * @author Stefan Feilmeier
 */
public class ItemEventSubscriber extends AbstractItemEventSubscriber {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private volatile boolean firstStart = true;

    @Override
    protected void receiveUpdate(ItemStateEvent updateEvent) {
        if (updateEvent.getItemName().equals("BSMU_Battery_Stack_Overall_SOC")) {
            FemsBindingConstants.initStatus.setModbus(true);
        }
        waitForModbus();
    }

    private synchronized void waitForModbus() {
        if (firstStart) {
            firstStart = false;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (FemsBindingConstants.initStatus.getModbus() == null) {
                        FemsBindingConstants.initStatus.setModbus(false);
                    }
                }
            };
            scheduler.schedule(runnable, 5, TimeUnit.SECONDS);
        }
    }
}
