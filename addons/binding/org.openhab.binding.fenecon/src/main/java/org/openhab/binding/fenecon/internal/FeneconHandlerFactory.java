/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.fenecon.FeneconBindingConstants;
import org.openhab.binding.fenecon.handler.FeneconCessHandler;
import org.openhab.binding.fenecon.handler.FeneconDessHandler;
import org.openhab.binding.fenecon.handler.FeneconIndustrialHandler;
import org.openhab.binding.fenecon.handler.FeneconMiniESHandler;
import org.osgi.service.component.ComponentContext;

/**
 * The {@link FeneconHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Stefan Feilmeier - Initial contribution
 */
public class FeneconHandlerFactory extends BaseThingHandlerFactory {
    // private Logger logger = LoggerFactory.getLogger(FeneconHandlerFactory.class);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return FeneconBindingConstants.SUPPORTED_THING_TYPES.contains(thingTypeUID);
    }

    @Override
    protected void activate(ComponentContext componentContext) {
        super.activate(componentContext);
    };

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(FeneconBindingConstants.THING_TYPE_DESS)) {
            return new FeneconDessHandler(thing);
        } else if (thingTypeUID.equals(FeneconBindingConstants.THING_TYPE_INDUSTRIAL)) {
            return new FeneconIndustrialHandler(thing);
        } else if (thingTypeUID.equals(FeneconBindingConstants.THING_TYPE_CESS)) {
            return new FeneconCessHandler(thing);
        } else if (thingTypeUID.equals(FeneconBindingConstants.THING_TYPE_MINIES)) {
            return new FeneconMiniESHandler(thing);
        }
        return null;
    }
}
