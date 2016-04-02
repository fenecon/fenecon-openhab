/**
 * Copyright (c) 2015 Stefan Feilmeier.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.femsdessui.internal;

import org.openhab.ui.dashboard.DashboardTile;

/**
 * The dashboard tile for the FEMS DESS UI
 *
 * @author Stefan Feilmeier
 *
 */
public class FemsDessUIDashboardTile implements DashboardTile {

    @Override
    public String getName() {
        return "FENECON PRO-Stromspeicher";
    }

    @Override
    public String getUrl() {
        return "../basicui/app?sitemap=dess";
    }

    @Override
    public String getOverlay() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return "";
    }
}
