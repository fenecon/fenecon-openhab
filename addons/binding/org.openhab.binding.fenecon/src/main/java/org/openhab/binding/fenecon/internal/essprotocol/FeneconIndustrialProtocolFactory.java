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

import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.PercentageWordItem;

public class FeneconIndustrialProtocolFactory {
    public static ArrayList<ModbusElementRange> getProtocol() {
        ArrayList<ModbusElementRange> protocol = new ArrayList<ModbusElementRange>();

        // SYS
        protocol.add(new ModbusElementRange(0x0200, /* 0x0200 */ new DecimalWordItem("SYS_DC_voltage", 0.1),
                /* 0x0201 */ new DecimalWordItem("SYS_DC_current", 0.1),
                /* 0x0202 */ new DecimalWordItem("SYS_DC_power", 0.1)));
        protocol.add(new ModbusElementRange(0x0210,
                /* 0x0210 */ new DecimalWordItem("SYS_Total_active_power_of_three_phase", 0.1),
                /* 0x0211 */ new DecimalWordItem("SYS_Total_reactive_power_of_three_phase", 0.1),
                /* 0x0212 */ new DecimalWordItem("SYS_Total_apparent_power_of_three_phase", 0.1),
                /* 0x0213 */ new DecimalWordItem("SYS_Phase_A_current", 0.1),
                /* 0x0214 */ new DecimalWordItem("SYS_Phase_B_current", 0.1),
                /* 0x0215 */ new DecimalWordItem("SYS_Phase_C_current", 0.1),
                /* 0x0216 */ new DecimalWordItem("SYS_Line_voltage_UAB", 0.1),
                /* 0x0217 */ new DecimalWordItem("SYS_Line_voltage_UBC", 0.1),
                /* 0x0218 */ new DecimalWordItem("SYS_Line_voltage_UCA", 0.1),
                /* 0x0219 */ new DecimalWordItem("SYS_Voltage_of_Phase_A", 0.1),
                /* 0x021A */ new DecimalWordItem("SYS_Voltage_of_Phase_B", 0.1),
                /* 0x021B */ new DecimalWordItem("SYS_Voltage_of_Phase_C", 0.1),
                /* 0x021C */ new DecimalWordItem("SYS_System_frequency", 0.1),
                /* 0x021D */ new DecimalWordItem("SYS_Total_power_factor_of_three_phase", 0.001),
                /* 0x021E */ new DecimalWordItem("SYS_Total_input_energy_Low_level", 1),
                /* 0x021F */ new DecimalWordItem("SYS_Total_input_energy_High_level", 1),
                /* 0x0220 */ new DecimalWordItem("SYS_Total_output_energy_Low_level", 1),
                /* 0x0221 */ new DecimalWordItem("SYS_Total_output_energy_High_level", 1)));
        protocol.add(
                new ModbusElementRange(0x0230, /* 0x0230 */ new DecimalWordItem("SYS_Permissible_discharge_power", 0.1),
                        /* 0x0231 */ new DecimalWordItem("SYS_Permissible_charge_power", 0.1),
                        /* 0x0232 */ new DecimalWordItem("SYS_Permissible_apparent_power", 0.1),
                        /* 0x0233 */ new DecimalWordItem("SYS_Permissible_reative_power", 0.1)));
        protocol.add(new ModbusElementRange(0x0246,
                /* 0x0246 */ new DecimalWordItem("SYS_Ambinet_temperature_in_control_cabinet", 1)));
        protocol.add(new ModbusElementRange(0x024F,
                /* 0x024F */ new DecimalWordItem("SYS_Ambinet_humidity_in_control_cabinet", 1)));

        // PCS
        protocol.add(new ModbusElementRange(0xB200, /* 0xB200 */ new DecimalWordItem("PCS_DC_voltage", 0.1),
                /* 0xB201 */ new DecimalWordItem("PCS_DC_current", 0.1),
                /* 0xB202 */ new DecimalWordItem("PCS_DC_power", 0.1),
                /* 0xB203 */ new DecimalWordItem("PCS_DC_electric_energy", 0.1),
                /* 0xB204 */ new DecimalWordItem("PCS_DC_midpoint_voltage_1", 0.1),
                /* 0xB205 */ new DecimalWordItem("PCS_DC_midpoint_voltage_2", 0.1)));
        protocol.add(new ModbusElementRange(0xB210,
                /* 0xB210 */ new DecimalWordItem("PCS_Active_power_sum_fo_3_phases", 0.1),
                /* 0xB211 */ new DecimalWordItem("PCS_Reactive_power_sum_fo_3_phases", 0.1),
                /* 0xB212 */ new DecimalWordItem("PCS_Apparent_power_sum_fo_3_phases", 0.1),
                /* 0xB213 */ new DecimalWordItem("PCS_A_phase_current", 0.1),
                /* 0xB214 */ new DecimalWordItem("PCS_B_phase_current", 0.1),
                /* 0xB215 */ new DecimalWordItem("PCS_C_phase_current", 0.1),
                /* 0xB216 */ new DecimalWordItem("PCS_UAB_line_voltage", 0.1),
                /* 0xB217 */ new DecimalWordItem("PCS_UBC_line_voltage", 0.1),
                /* 0xB218 */ new DecimalWordItem("PCS_UCA_line_voltage", 0.1),
                /* 0xB219 */ new DecimalWordItem("PCS_System_frequency", 0.1),
                /* 0xB21A */ new DecimalWordItem("PCS_AC_electric_energy", 0.1)));
        protocol.add(
                new ModbusElementRange(0xB230, /* 0xB230 */ new DecimalWordItem("PCS_Allowable_discharging_power", 0.1),
                        /* 0xB231 */ new DecimalWordItem("PCS_Allowable_charging_power", 0.1),
                        /* 0xB232 */ new DecimalWordItem("PCS_Allowable_apparent_power", 0.1)));
        protocol.add(new ModbusElementRange(0xB240, /* 0xB240 */ new DecimalWordItem("PCS_A_phase_IPM_temp", 1),
                /* 0xB241 */ new DecimalWordItem("PCS_B_phase_IPM_temp", 1),
                /* 0xB242 */ new DecimalWordItem("PCS_C_phase_IPM_temp", 1),
                /* 0xB243 */ new DecimalWordItem("PCS_Module_cabinet_ambient_temp", 1),
                /* 0xB244 */ new DecimalWordItem("PCS_Module_cabinet_ambient_temp", 1),
                /* 0xB245 */ new DecimalWordItem("PCS_Radiator_roof_temp_A", 1),
                /* 0xB246 */ new DecimalWordItem("PCS_Radiator_roof_temp_B", 1),
                /* 0xB247 */ new DecimalWordItem("PCS_Radiator_roof_temp_C", 1),
                /* 0xB248 */ new DecimalWordItem("PCS_Electric_reactor_iron_core_temp", 1)));

        // BMS
        protocol.add(
                new ModbusElementRange(0x1300, /* 0x1300 */ new DecimalWordItem("BMS_Voltage_of_battery_stack", 0.1),
                        /* 0x1301 */ new DecimalWordItem("BMS_Current_of_battery_stack", 0.1),
                        /* 0x1302 */ new DecimalWordItem("BMS_Power_of_battery_stack", 0.1),
                        /* 0x1303 */ new PercentageWordItem("BMS_SOC_of_whole_battery_stack"),
                        /* 0x1304 */ new PercentageWordItem("BMS_SOH_of_whole_battery_stack"),
                        /* 0x1305 */ new DecimalWordItem("BMS_Charge_current_limitation_of_battery_stack", 0.1),
                        /* 0x1306 */ new DecimalWordItem("BMS_Discharge_current_limitation_of_battery_stack", 0.1),
                        /* 0x1307 */ new DecimalWordItem("BMS_Charge_power_limitation_of_battery_stack", 0.1),
                        /* 0x1308 */ new DecimalWordItem("BMS_Discharge_power_limitation_of_battery_stack", 0.1),
                        /* 0x1309 */ new DecimalWordItem("BMS_Total_capacity_of_usable_battery_strings"),
                        /* 0x130A */ new DecimalWordItem("BMS_Total_charge_energy_of_battery_stack_low_level"),
                        /* 0x130B */ new DecimalWordItem("BMS_Total_charge_energy_of_battery_stack_high_level"),
                        /* 0x130C */ new DecimalWordItem("BMS_Total_discharge_energy_of_battery_stack_low_level"),
                        /* 0x130D */ new DecimalWordItem("BMS_Total_discharge_energy_of_battery_stack_high_level")));
        protocol.add(new ModbusElementRange(0x1400,
                /* 0x1400 */ new DecimalWordItem("BMS_Total_voltage_of_battery_string_1", 0.1),
                /* 0x1401 */ new DecimalWordItem("BMS_Current_of_battery_string_1", 0.1),
                /* 0x1402 */ new DecimalWordItem("BMS_SOC_of_battery_string_1", 1),
                /* 0x1403 */ new DecimalWordItem("BMS_SOH_of_battery_string_1", 1),
                /* 0x1404 */ new DecimalWordItem("BMS_Average_temperature_of_battery_string_1", 1),
                /* 0x1405 */ new DecimalWordItem("BMS_Ambinet_temperature_of_battery_string_1", 1),
                /* 0x1406 */ new DecimalWordItem("BMS_Charge_current_limitation_of_battery_string_1", 0.1),
                /* 0x1407 */ new DecimalWordItem("BMS_Discharge_current_limitation_of_battery_string_1", 0.1),
                /* 0x1408 */ new DecimalWordItem("BMS_Rest_capacity_of_battery_string_1_low_level", 1),
                /* 0x1409 */ new DecimalWordItem("BMS_Rest_capacity_of_battery_string_1_high_level", 1),
                /* 0x140A */ new DecimalWordItem("BMS_Charge_time_of_battery_string__low_level", 1),
                /* 0x140B */ new DecimalWordItem("BMS_Charge_time_of_battery_string__high_level", 1),
                /* 0x140C */ new DecimalWordItem("BMS_Total_energy_of_battery_string_1_low_level", 1),
                /* 0x140D */ new DecimalWordItem("BMS_Total_energy_of_battery_string_1_high_level", 1),
                /* 0x140E */ new DecimalWordItem("BMS_Total_capacity_of_battery_string_1_low_level", 1),
                /* 0x140F */ new DecimalWordItem("BMS_Total_capacity_of_battery_string_1_high_level", 1),
                /* 0x1410 */ new DecimalWordItem("BMS_Single_charge_power_for_battery_string_1_low_level", 1),
                /* 0x1411 */ new DecimalWordItem("BMS_Single_charge_power_for_battery_string_1_high_level", 1),
                /* 0x1412 */ new DecimalWordItem("BMS_Single_discharge_power_for_battery_string_1_low_level", 1),
                /* 0x1413 */ new DecimalWordItem("BMS_Single_discharge_power_for_battery_string_1_high_level", 1),
                /* 0x1414 */ new DecimalWordItem("BMS_Single_charge_energy_for_battery_string_1_low_level", 1),
                /* 0x1415 */ new DecimalWordItem("BMS_Single_charge_energy_for_battery_string_1_high_level", 1),
                /* 0x1416 */ new DecimalWordItem("BMS_Single_discharge_energy_for_battery_string_1_low_level", 1),
                /* 0x1417 */ new DecimalWordItem("BMS_Single_discharge_energy_for_battery_string_1_high_level", 1),
                /* 0x1418 */ new DecimalWordItem("BMS_Charge_power_of_battery_string_1_in_history_low_level", 1),
                /* 0x1419 */ new DecimalWordItem("BMS_Charge_power_of_battery_string_1_in_history_high_level", 1),
                /* 0x141A */ new DecimalWordItem("BMS_Discharge_power_of_battery_string_1_in_history_low_level", 1),
                /* 0x141B */ new DecimalWordItem("BMS_Discharge_power_of_battery_string_1_in_history_high_level", 1),
                /* 0x141C */ new DecimalWordItem("BMS_Single_charge_energy_in_history_for_battery_string_1_low_level",
                        1),
                /* 0x141D */ new DecimalWordItem("Single_charge_energy_in_history_for_battery_string_1_high_level", 1),
                /* 0x141E */ new DecimalWordItem(
                        "BMS_Single_discharge_energy_in_history_for_battery_string_1_low_level"),
                /* 0x141F */ new DecimalWordItem(
                        "BMS_Single_discharge_energy_in_history_for_battery_string_1_high_level_")));
        protocol.add(new ModbusElementRange(0x1430,
                /* 0x1430 */ new DecimalWordItem("BMS_Cell_number_with_biggest_voltage_in_battery_string_1", 1),
                /* 0x1431 */ new DecimalWordItem("BMS_Biggest_voltage_value_of_cell_in_battery_string_1", 0.001),
                /* 0x1432 */ new DecimalWordItem("BMS_Temperature_of_cell_with_biggest_voltage_in_battery_string_1", 1),
                /* 0x1433 */ new DecimalWordItem("BMS_Cell_number_with_smallest_voltage_in_battery_string_1", 1),
                /* 0x1434 */ new DecimalWordItem("BMS_Smallest_voltage_value_of_cell_in_battery_string_1", 0.001),
                /* 0x1435 */ new DecimalWordItem("BMS_Temperature_of_cell_with_smallest_voltage_in_battery_string_1",
                        1)));
        protocol.add(new ModbusElementRange(0x143A,
                /* 0x143A */ new DecimalWordItem("BMS_Cell_number_with_highest_temperature_in_battery_string_1", 1),
                /* 0x143B */ new DecimalWordItem("BMS_Highest_temperature_of_cell_in_battery_string_1", 1),
                /* 0x143C */ new DecimalWordItem("BMS_Voltage_of_cell_with_highest_temperature_in_battery_string_1",
                        0.001),
                /* 0x143D */ new DecimalWordItem("BMS_Cell_number_with_lowest_temperature_in_battery_string_1", 1),
                /* 0x143E */ new DecimalWordItem("BMS_lowest_temperature_of_cell_in_battery_string_1", 1),
                /* 0x143F */ new DecimalWordItem("BMS_Voltage_of_cell_with_lowest_temperature_in_battery_string_1",
                        0.001)));

        return protocol;
    }
}