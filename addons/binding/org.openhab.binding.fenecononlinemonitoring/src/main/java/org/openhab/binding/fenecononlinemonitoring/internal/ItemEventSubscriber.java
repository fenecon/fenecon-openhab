/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecononlinemonitoring.internal;

import java.math.BigDecimal;

import org.eclipse.smarthome.core.items.events.AbstractItemEventSubscriber;
import org.eclipse.smarthome.core.items.events.ItemStateEvent;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class ItemEventSubscriber extends AbstractItemEventSubscriber {
    private final Logger logger = LoggerFactory.getLogger(ItemEventSubscriber.class);

    @Override
    protected void receiveUpdate(ItemStateEvent updateEvent) {
        String itemName = updateEvent.getItemName();
        State state = updateEvent.getItemState();
        TimedItemState<?> tis = null;

        if (state instanceof DecimalType) {
            DecimalType s = (DecimalType) state;
            tis = new TimedItemState<BigDecimal>(itemName, s.toBigDecimal());
        } else if (state instanceof OnOffType) {
            OnOffType s = (OnOffType) state;
            if (s == OnOffType.OFF) {
                tis = new TimedItemState<Integer>(itemName, new Integer(0));
            } else {
                tis = new TimedItemState<Integer>(itemName, new Integer(1));
            }
        } else if (state instanceof StringType) {
            StringType s = (StringType) state;
            tis = new TimedItemState<String>(itemName, s.toString());
        }

        if (tis == null) {
            logger.error("Unable to convert item state \"" + itemName + "\" to json: " + state);
        } else {
            OnlineMonitoringConnector.offer(tis);
        }
    }
}
