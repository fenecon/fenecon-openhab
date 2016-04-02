/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import org.eclipse.smarthome.core.types.State;

public abstract class ModbusItem {
	private String name;
	private State state;
	
	public ModbusItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	};
	
	public State getState() {
		return state;
	}
	
	protected void setState(State state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "ModbusItem [name=" + name + ", state=" + state + "]";
	}
}
