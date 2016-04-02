/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol;

import java.util.ArrayList;

import org.openhab.binding.fenecon.internal.essprotocol.modbus.BitWordElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalDoublewordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.OnOffBitItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ReservedElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ShortDecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.StringWordItem;

public class FeneconMiniESProtocolFactory {
    public static ArrayList<ModbusElementRange> getProtocol() {
        ArrayList<ModbusElementRange> protocol = new ArrayList<ModbusElementRange>();

        protocol.add(new ModbusElementRange(100,
                /* 100 */ new StringWordItem("System_status", //
                        "Standby", "Off-Grid", "On-Grid", "Fail", "Off-Grid PV"),
                /* 101 */ new StringWordItem("Control_mode", "", "Remote", "Local"),
                /* 102-103 */ new ReservedElement(102, 103),
                /* 104-105 */ new DecimalDoublewordItem("Battery_allowable_charging_power_limitation"), // Wh
                /* 106-107 */ new DecimalDoublewordItem("Total_energy_discharged_from_battery"), // Wh
                /* 108 */
                new StringWordItem("Battery_group_status", //
                        "Initial", "Off-Grid", "Being On-Grid", "On-Grid", "Stopping", "Fail"),
                /* 109 */ new DecimalWordItem("Battery_group_SOC"), // %
                /* 110 */ new DecimalWordItem("Battery_group_voltage", 0.1), // V
                /* 111 */ new ShortDecimalWordItem("Battery_group_current", 0.1), // A
                /* 112 */ new ShortDecimalWordItem("Battery_group_power"), // W
                /* 113 */
                new BitWordElement("Battery_group_alarm_message", //
                        new OnOffBitItem(0, "System_should_be_stopped"), //
                        new OnOffBitItem(1, "Common_low_voltage"), //
                        new OnOffBitItem(2, "Common_high_voltage"), //
                        new OnOffBitItem(3, "Carging_over_current"), //
                        new OnOffBitItem(4, "Discharging_over_current"), //
                        new OnOffBitItem(5, "Over_temperature"), //
                        new OnOffBitItem(6, "Internal_communication_broken")),
                /* 114 */ new StringWordItem("Operation_status", //
                        "Self_checking", "Standby", "Off-Grid PV", "Off-Grid", "On-Grid", "Fail", "Bypass 1",
                        "Bypass 2"),
                /* 115-117 */ new ReservedElement(115, 117), //
                /* 118 */ new ShortDecimalWordItem("PCS_output_current", 0.1), // A
                /* 119-120 */ new ReservedElement(119, 120), //
                /* 121 */ new DecimalWordItem("Grid_voltage", 0.1), // V
                /* 122-123 */ new ReservedElement(122, 123), //
                /* 124 */ new ShortDecimalWordItem("PCS_active_power", 1), // W
                /* 125-126 */ new ReservedElement(125, 126), //
                /* 127 */ new ShortDecimalWordItem("PCS_reactive_power", 1), // Var
                /* 128-130 */ new ReservedElement(128, 130), //
                /* 131 */ new DecimalWordItem("Grid_frequency", 0.01), // Hz
                /* 132-133 */ new ReservedElement(132, 133),
                /* 134 */ new DecimalWordItem("PCS_maximum_output_apparent_power"), // W
                /* 135-138 */ new ReservedElement(135, 138),
                /* 139 */ new ShortDecimalWordItem("Meter_total_active_power", 1), // W
                /* 140 */ new ShortDecimalWordItem("Meter_total_reactive_power", 1), // Var
                /* 141 */ new DecimalWordItem("System_charging_limitation"), // W
                /* 142 */ new DecimalWordItem("System_discharging_limitation"), // W
                /* 143 */ new DecimalWordItem("PV_inverter_power") // W
        ));

        return protocol;
    }
}
