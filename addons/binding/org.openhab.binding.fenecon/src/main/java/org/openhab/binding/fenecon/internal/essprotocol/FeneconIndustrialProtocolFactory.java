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
import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.OnOffBitItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.PercentageWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ReservedElement;

public class FeneconIndustrialProtocolFactory {
    public static ArrayList<ModbusElementRange> getProtocol() {
        ArrayList<ModbusElementRange> protocol = new ArrayList<ModbusElementRange>();

        // SYS
        protocol.add(new ModbusElementRange(0x0100,
                /* 0x0100 */ new BitWordElement("SYS_Alarm_Info", //
                        new OnOffBitItem(0, "Warning_State"), //
                        new OnOffBitItem(1, "Protection_State"), //
                        new OnOffBitItem(2, "Derating_State"), //
                        new OnOffBitItem(3, "Charge_Forbidden"), //
                        new OnOffBitItem(4, "Discharge_Forbidden")),
                /* 0x0101 */ new BitWordElement("SYS_Work_Status", //
                        new OnOffBitItem(0, "Initial"), //
                        new OnOffBitItem(1, "Fault"), //
                        new OnOffBitItem(2, "Stop"), //
                        new OnOffBitItem(3, "Hot_Standby"), //
                        new OnOffBitItem(4, "Monitoring"), //
                        new OnOffBitItem(5, "Standby"), //
                        new OnOffBitItem(6, "Operation"), //
                        new OnOffBitItem(7, "Debug")),
                /* 0x0102 */ new BitWordElement("SYS_Control_Mode", //
                        new OnOffBitItem(0, "Remote"), //
                        new OnOffBitItem(1, "Local")),
                /* 0x0103 */ new ReservedElement(0x0103)));
        protocol.add(new ModbusElementRange(0x0110,
                /* 0x0110 */ new BitWordElement("SYS_Alarm_Info", //
                        new OnOffBitItem(1, "Status_abnormal_of_AC_surge_protector"), //
                        new OnOffBitItem(2, "Close_of_control_switch"), //
                        new OnOffBitItem(3, "Emergency_stop"), //
                        new OnOffBitItem(5, "Status_abnormal_of_frog_detector"), //
                        new OnOffBitItem(6, "Serious_leakage"), //
                        new OnOffBitItem(7, "Normal_leakage")),
                /* 0x0111 */ new BitWordElement("SYS_Alarm_Info", //
                        new OnOffBitItem(0, "Failure_of_temperature_sensor_in_control_cabinet"), //
                        new OnOffBitItem(9, "Failure_of_humidity_sensor_in_control_cabinet"), //
                        new OnOffBitItem(12, "Failure_of_storage_device"), //
                        new OnOffBitItem(13, "Exceeding_of_humidity_in_control_cabinet"))));
        protocol.add(new ModbusElementRange(0x0125,
                /* 0x0125 */ new BitWordElement("SYS_Fault_Info", //
                        new OnOffBitItem(2, "Communication_interrupt_for_multimeter"), //
                        new OnOffBitItem(4, "Communication_interrupt_of_remoting")), //
                /* 0x0126 */ new BitWordElement("SYS_Fault_Info", //
                        new OnOffBitItem(0, "Serious_over_temperature_in_control_cabinet"))));
        protocol.add(new ModbusElementRange(0x0130,
                /* 0x0130 */ new BitWordElement("SYS_Alarm", //
                        new OnOffBitItem(0, "Recheck_abnormal_for_DC_precharge_contactor"), //
                        new OnOffBitItem(3, "Recheck_abnormal_for_AC_precharge_contactor"), //
                        new OnOffBitItem(4, "Recheck_abnormal_for_AC_main_contactor"), //
                        new OnOffBitItem(5, "Recheck_abnormal_for_AC_breaker"), //
                        new OnOffBitItem(12, "Cabinet_door_open"), //
                        new OnOffBitItem(14, "AC_breaker_is_not_closed")), //
                /* 0x0131 */
                new BitWordElement("SYS_Alarm", //
                        new OnOffBitItem(0, "Normal_overload"), //
                        new OnOffBitItem(1, "Severe_overlad"), //
                        new OnOffBitItem(2, "Current_limitation_of_battery"), //
                        new OnOffBitItem(3, "Power_derating_when_temperature_is_out_of_range"), //
                        new OnOffBitItem(4, "Normal_overtemperature_of_PCS"), //
                        new OnOffBitItem(5, "AC_three_phase_current_unbalance_alarm"), //
                        new OnOffBitItem(6, "Failed_to_restore_factory_settings"), //
                        new OnOffBitItem(7, "Board_failure_alarm"), //
                        new OnOffBitItem(8, "Selfcheck_failure_alarm"), //
                        new OnOffBitItem(9, "Stop_after_receiving_BMS_failure"), //
                        new OnOffBitItem(10, "Cooling_system_failure"), //
                        new OnOffBitItem(11, "Temperature_difference_in_three_phase_IGBT_is_big"), //
                        new OnOffBitItem(12, "The_input_data_in_EEPROM_is_out_of_range"), //
                        new OnOffBitItem(13, "Backup_failure_alarm_for_EEPROM_data"), //
                        new OnOffBitItem(14, "DC_capacitor_leakage_contactor_closed_failure"), //
                        new OnOffBitItem(15, "DC_capacitor_leakage_contactor_disclosed_failure")), //
                /* 0x0132 */
                new BitWordElement("SYS_Alarm", //
                        new OnOffBitItem(0, "Communication_interrupt_between_PCS_and_BMS"), //
                        new OnOffBitItem(1, "Communication_interrupt_between_PCS_and_Master"), //
                        new OnOffBitItem(2, "Communication_interrupt_between_PCS_and_unit_controller"), //
                        new OnOffBitItem(5, "Cabinet_temperature_too_high"), //
                        new OnOffBitItem(6, "Cabinet_humidity_too_high"), //
                        new OnOffBitItem(7, "Stop_after_receiving_signal_from_H31_control_board"), //
                        new OnOffBitItem(8, "DC_breaker_not_closed")), //
                /* 0x0133 */ new ReservedElement(0x0133), //
                /* 0x0134 */
                new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "DC_precharge_contactor_not_closed"), //
                        new OnOffBitItem(1, "AC_precharge_contactor_not_closed"), //
                        new OnOffBitItem(2, "AC_main_contactor_not_closed"), //
                        new OnOffBitItem(5, "AC_breaker_tripping"), //
                        new OnOffBitItem(6, "AC_main_contactor_disconnect_during_operation"), //
                        new OnOffBitItem(7, "DC_breaker_disconnect_during_operation"), //
                        new OnOffBitItem(8, "AC_main_contactor_unable_to_disconnect"), //
                        new OnOffBitItem(11, "PDP_failure"), //
                        new OnOffBitItem(12, "High_voltage_protection_of_DC_current_point_1"), //
                        new OnOffBitItem(13, "High_voltage_protection_of_DC_current_point_2"), //
                        new OnOffBitItem(14, "Synchronization_signals_failure_protection"), //
                        new OnOffBitItem(15, "Synchronization_signals_continuous_capture_mistake")),
                /* 0x0135 */ new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "Synchronization_signals_multiple_capture_mistake"), //
                        new OnOffBitItem(1, "Radiator_over_temperature_protection"), //
                        new OnOffBitItem(2, "Iron_core_in_reactor_over_temperature_protection")),
                /* 0x0136 */ new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "DC_short_circuit_protection"), //
                        new OnOffBitItem(1, "DC_over_voltage_protection"), //
                        new OnOffBitItem(2, "DC_under_voltage_protection"), //
                        new OnOffBitItem(3, "DC_reverse_or_protection_not_connected"), //
                        new OnOffBitItem(4, "Disconnect_protection_on_DC_side"), //
                        new OnOffBitItem(5, "Rectified_voltage_abnormal_protection"), //
                        new OnOffBitItem(6, "DC_over_current_protection"), //
                        new OnOffBitItem(7, "Phase_protection_on_Phase_A"), //
                        new OnOffBitItem(8, "Phase_protection_on_Phase_B"), //
                        new OnOffBitItem(9, "Phase_protection_on_Phase_C"), //
                        new OnOffBitItem(10, "High_protection_for_RMS_of_Phase_A"), //
                        new OnOffBitItem(11, "High_protection_for_RMS_of_Phase_B"), //
                        new OnOffBitItem(12, "High_protection_for_RMS_of_Phase_C"), //
                        new OnOffBitItem(13, "Voltage_sampling_failure_of_Grid_phase_A"), //
                        new OnOffBitItem(14, "Voltage_sampling_failure_of_Grid_phase_B"), //
                        new OnOffBitItem(15, "Voltage_sampling_failure_of_Grid_phase_C")), //
                /* 0x0137 */
                new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "Voltage_sampling_failure_of_inverter_phase_A"), //
                        new OnOffBitItem(1, "Voltage_sampling_failure_of_inverter_phase_B"), //
                        new OnOffBitItem(2, "Voltage_sampling_failure_of_inverter_phase_C"), //
                        new OnOffBitItem(3, "AC_current_sampling_failure"), //
                        new OnOffBitItem(4, "DC_current_sampling_failure"), //
                        new OnOffBitItem(5, "Over_temperature_protection_of_phase_A"), //
                        new OnOffBitItem(6, "Over_temperature_protection_of_phase_B"), //
                        new OnOffBitItem(7, "Over_temperature_protection_of_phase_C"), //
                        new OnOffBitItem(8, "Temperature_sampling_failure_of_phase_A"), //
                        new OnOffBitItem(9, "Temperature_sampling_failure_of_phase_B"), //
                        new OnOffBitItem(10, "Temperature_sampling_failure_of_phase_C"), //
                        new OnOffBitItem(11, "Precharge_not_full_protection_of_phase_A"), //
                        new OnOffBitItem(12, "Precharge_not_full_protection_of_phase_B"), //
                        new OnOffBitItem(13, "Precharge_not_full_protection_of_phase_C"), //
                        new OnOffBitItem(14, "Maladjustment_phase_sequence_error_protection"), //
                        new OnOffBitItem(15, "DSP_protection")), //
                /* 0x0138 */
                new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "Severe_over_voltage_protection_for_grid_phase_A_"), //
                        new OnOffBitItem(1, "Normal_over_voltage_protection_for_grid_phase_A"), //
                        new OnOffBitItem(2, "Severe_over_voltage_protection_for_grid_phase_B"), //
                        new OnOffBitItem(3, "Normal_over_voltage_protection_for_grid_phase_B"), //
                        new OnOffBitItem(4, "Severe_over_votlage_protection_for_grid_phase_C"), //
                        new OnOffBitItem(5, "Normal_over_voltage_protection_for_grid_phase_C"), //
                        new OnOffBitItem(6, "Severe_under_voltage_protection_for_grid_phase_A"), //
                        new OnOffBitItem(7, "Normal_under_voltage_protection_for_grid_phase_A"), //
                        new OnOffBitItem(8, "Severe_under_voltage_protection_for_grid_phase_B"), //
                        new OnOffBitItem(9, "Normal_under_voltage_protection_for_grid_phase_B"), //
                        new OnOffBitItem(10, "Severe_under_voltage_protection_for_grid_phase_C"), //
                        new OnOffBitItem(11, "Normal_under_voltage_protection_for_grid_phase_C"), //
                        new OnOffBitItem(12, "Severe_high_of_frequency"), //
                        new OnOffBitItem(13, "Normal_high_of_frequency"), //
                        new OnOffBitItem(14, "Severe_low_of_frequency"), //
                        new OnOffBitItem(15, "Normal_low_of_frequency")), //
                /* 0x0139 */
                new BitWordElement("SYS_Fault", //
                        new OnOffBitItem(0, "Phase_missing_of_grid_phase_A"), //
                        new OnOffBitItem(1, "Phase_missing_of_grid_phase_B"), //
                        new OnOffBitItem(2, "Phase_missing_of_grid_phase_C"), //
                        new OnOffBitItem(3, "Islanding_protection"), //
                        new OnOffBitItem(4, "Low_voltage_ridethrough_of_phase_A"), //
                        new OnOffBitItem(5, "Low_voltage_ridethrough_of_phase_B"), //
                        new OnOffBitItem(6, "Low_voltage_ridethrough_of_phase_C"))));

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
        protocol.add(new ModbusElementRange(0xB000,
                /* 0xB000 */ new BitWordElement("PCS_Operating_Status", //
                        new OnOffBitItem(0, "initialization"), //
                        new OnOffBitItem(1, "fault"), //
                        new OnOffBitItem(2, "halt"), //
                        new OnOffBitItem(3, "hot_standby"), //
                        new OnOffBitItem(4, "dynamic_monitoring"), //
                        new OnOffBitItem(5, "standby"), //
                        new OnOffBitItem(6, "operation"), //
                        new OnOffBitItem(7, "debugging"))));
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
        protocol.add(new ModbusElementRange(0x1000,
                /* 0x1000 */ new BitWordElement("PCS_Battery_Charge_Status", //
                        new OnOffBitItem(0, "Initial"), //
                        new OnOffBitItem(1, "Stop"), //
                        new OnOffBitItem(2, "Starting"), //
                        new OnOffBitItem(3, "Operation"), //
                        new OnOffBitItem(4, "Stopping"), //
                        new OnOffBitItem(5, "Fault"), //
                        new OnOffBitItem(6, "Debugging"), //
                        new OnOffBitItem(8, "Low_consumption")), //
                /* 0x1001 */
                new BitWordElement("PCS_Battery_Charge_Status", //
                        new OnOffBitItem(0, "No"), //
                        new OnOffBitItem(1, "Charge"), //
                        new OnOffBitItem(2, "Discharge")), //
                /* 0x1002 */
                new BitWordElement("PCS_Battery_Work_Status", //
                        new OnOffBitItem(0, "All_open"), //
                        new OnOffBitItem(1, "Not_all_open")), //
                /* 0x1003 */ new ReservedElement(0x1003),
                /* 0x1004 */ new BitWordElement("PCS_Battery_Work_Mode", //
                        new OnOffBitItem(0, "Normal_mode"), //
                        new OnOffBitItem(1, "Capacity_check_mode"), //
                        new OnOffBitItem(2, "Balancing_mode"), //
                        new OnOffBitItem(3, "Energy_safe_mode"), //
                        new OnOffBitItem(4, "Emergency_status")), //
                /* 0x1005 */
                new BitWordElement("PCS_Battery_Control_Mode", //
                        new OnOffBitItem(0, "Local"), //
                        new OnOffBitItem(1, "Remote"))));

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

        // ProtocolFactoryHelper.generateItemsFile(protocol, "industrial");
        return protocol;
    }
}