/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import org.eclipse.smarthome.core.types.Command;

/**
 *
 * @author Stefan Feilmeier
 */
public interface IOOutput extends IO {
    public void handleCommand(Command command);
}
