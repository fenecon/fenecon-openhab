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
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ReverseDecimalDoublewordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.SecureDecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ShortDecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.StringWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.VersionWordItem;

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

        protocol.add(new ModbusElementRange(983,

        /* 983 */ new DecimalWordItem("Target_Curr_Read", 0.1), /* 0.1A */

        /* 984 */ new StringWordItem("Debug_Mode_State", "Standby", "PCS aging", "Curing SOC", "Cumulative capacity"),
                /* 985-986 */ new ReservedElement(985, 986), /* 987 */ new DecimalWordItem("Target_SOC_Read"), /* % */
                /* 988-991 */ new ReservedElement(988, 991),
                /* 992 */ new StringWordItem("PCS_ForceRun_State", "no mandatory charge", "energy from grid")));

        protocol.add(new ModbusElementRange(2003,

        /* 2003 */ new DecimalWordItem("PCS1_DC_Volt", 0.1), /* 0.1V */

        /* 2004 */ new DecimalWordItem("PCS1_Invert_Volt", 0.1), /* 0.1V */
                /* 2005-2006 */ new ReservedElement(2005, 2006),
                /* 2007 */ new DecimalWordItem("PCS1_Invert_Power", 1, 10000), /* 1W */

        /* 2008 */ new DecimalWordItem("PCS1_Invert_Freq", 0.01), /* 0.01HZ */

        /* 2009 */ new DecimalWordItem("PCS1_Grid_Volt", 0.1), /* 0.1V */

        /* 2010 */ new DecimalWordItem("PCS1_Grid_Freq", 0.01), /* 0.01HZ */

        /* 2011 */new BitWordElement("PCS1_Fault1", //
                new OnOffBitItem(0, "control_overload_100"), //
                new OnOffBitItem(1, "control_overload_110"), //
                new OnOffBitItem(2, "control_overload_150"), //
                new OnOffBitItem(3, "control_overload_200"), //
                new OnOffBitItem(4, "control_overload_220"), //
                new OnOffBitItem(5, "control_overload_300"), //
                new OnOffBitItem(6, "control_instant_overload_102"), new OnOffBitItem(7, "grid_overload"),
                new OnOffBitItem(8, "waveforms_error"), new OnOffBitItem(9, "invert_voltage_zero_drift_error"),
                new OnOffBitItem(10, "grid_voltage_zero_drift_error"), new OnOffBitItem(11, "control_zero_drift_error"),
                new OnOffBitItem(12, "invert_zero_drift_error"), new OnOffBitItem(13, "grid_zero_drift_error"),
                new OnOffBitItem(14, "pdp_protection"), new OnOffBitItem(15, "hardware_control_protection")),

        /* 2012 */new BitWordElement("PCS1_Fault2", //
                new OnOffBitItem(0, "hardware_invert_voltage_protection"), //
                new OnOffBitItem(1, "hardware_dc_voltage_protection"), //
                new OnOffBitItem(2, "hardware_temperature_protection"), //
                new OnOffBitItem(3, "no_capture_signal"), //
                new OnOffBitItem(4, "dc_over_voltage"), //
                new OnOffBitItem(5, "dc_side_snap"), //
                new OnOffBitItem(6, "low_invert_voltage"), new OnOffBitItem(7, "high_invert_voltage"),
                new OnOffBitItem(8, "current_transformer_fault"), new OnOffBitItem(9, "voltage_transformer_fault"),
                new OnOffBitItem(10, "power_is_uncontrolled"), new OnOffBitItem(11, "current_is_uncontrolled"),
                new OnOffBitItem(12, "fan_fault"), new OnOffBitItem(13, "phase_loss_fault"),
                new OnOffBitItem(14, "invert_relay_fault"), new OnOffBitItem(15, "fan_relay_fault")),

        /* 2013 */new BitWordElement("PCS1_Fault3", //
                new OnOffBitItem(0, "control_panel_over_temperature"), //
                new OnOffBitItem(1, "power_panel_over_temperature"), //
                new OnOffBitItem(2, "dc_entrance_over_temperature"), //
                new OnOffBitItem(3, "capacitance_over_temperature"), //
                new OnOffBitItem(4, "radiator_over_temperature"), //
                new OnOffBitItem(5, "transformer_over_temperature"), //
                new OnOffBitItem(6, "group_network_communication_fault"), new OnOffBitItem(7, "EEPROM_fault"),
                new OnOffBitItem(8, "load_zero_drift_error"), new OnOffBitItem(9, "advanced_charging_fault"),
                new OnOffBitItem(10, "group_network_sync_signal_error")),

        /* 2014-2017 */ new ReservedElement(2014, 2017),
                /* 2018 */ new ShortDecimalWordItem("PCS1_Grid_Power", 0.1), /* 0.1W */

        /* 2019-2020 */ new DecimalDoublewordItem("PCS1_SellE"), /* 0.1kwh */

        /* 2021-2022 */ new DecimalDoublewordItem("PCS1_BuyE"), /* 0.1kwh */

        /* 2023-2034 */ new ReservedElement(2023, 2034),
                /* 2035-2036 */ new DecimalDoublewordItem("PCS1_PVE"), /* 0.1kwh */

        /* 2037-2040 */ new ReservedElement(2037, 2040),

        /* 2041 */ new BitWordElement("PCS1_Alarm1", //
                new OnOffBitItem(0, "grid_low_voltage"), //
                new OnOffBitItem(1, "grid_high_voltage"), //
                new OnOffBitItem(2, "grid_low_frequence"), //
                new OnOffBitItem(3, "grid_high_frequence"), //
                new OnOffBitItem(4, "grid_break_abruptly"), //
                new OnOffBitItem(5, "grid_condition_not_allow_connect"), //
                new OnOffBitItem(6, "low_dc_voltage"), new OnOffBitItem(7, "high_input_impedance"),
                new OnOffBitItem(8, "setting_network_jumper_wrong"),
                new OnOffBitItem(9, "communication_fault_inverter"), new OnOffBitItem(10, "system_time_failed")),

        /* 2042 */ new BitWordElement("PCS1_Alarm2"), /* No Error */

        /* 2043 */new VersionWordItem("PCS1_Version"), /* 10 shows a version 1.0 */
                /* 2044-2044 */ new ReservedElement(2044, 2044), /* 2045 */ new StringWordItem("PCS1_Work_State",
                        "Self", "Standby", "Starting", "Run from Network", "network operation", "Fault")));
        protocol.add(new ModbusElementRange(2063,
                /* 2063 */ new SecureDecimalWordItem("PCS1_Load_Power", 1, 10000, 0), /* W */
                /* 2064-2065 */ new ReservedElement(2064, 2065),
                /* 2066 */ new DecimalWordItem("PCS1_PV_Power", 1, 10000) /* W */

        ));
        protocol.add(new ModbusElementRange(3000, /* 3000 */ new DecimalWordItem("BECU1_Charge_Curr", 0.1), /* 0.1A */

        /* 3001 */ new DecimalWordItem("BECU1_Discharge_Curr", 0.1), /* 0.1A */

        /* 3002 */ new DecimalWordItem("BECU1_Volt", 0.1), /* 0.1V */

        /* 3003 */ new DecimalWordItem("BECU1_Curr", 0.1), /* 0.1A */

        /* 3004 */ new DecimalWordItem("BECU1_SOC", 0.01), /*  */

        /* 3005 */ new BitWordElement("BECU1_Alarm1", //
                new OnOffBitItem(0, "charge_over_current_alarm"), //
                new OnOffBitItem(1, "discharge_over_current_alarm"), //
                new OnOffBitItem(2, "charge_limit_alarm"), //
                new OnOffBitItem(3, "discharge_limit_alarm"), new OnOffBitItem(4, "high_voltage_alarm"),
                new OnOffBitItem(5, "low_voltage_alarm"), new OnOffBitItem(6, "abnormal_voltage_change_alarm"),
                new OnOffBitItem(7, "high_temperature_alarm"), new OnOffBitItem(8, "low_temperature_alarm"),
                new OnOffBitItem(9, "abnormal_temperature_change_alarm"),
                new OnOffBitItem(10, "severe_high_voltage_alarm"), new OnOffBitItem(11, "severe_low_voltage_alarm"),
                new OnOffBitItem(12, "severe_low_temperature_alarm"),
                new OnOffBitItem(13, "severe_charge_over_current_alarm"),
                new OnOffBitItem(14, "severe_discharge_over_current_alarm"),
                new OnOffBitItem(15, "abnormal_cell_capacity_alarm")),

        /* 3006 */ new BitWordElement("BECU1_Alarm2", //
                new OnOffBitItem(0, "balanced_sampling_alarm"), //
                new OnOffBitItem(1, "balanced_control_alarm"), //
                new OnOffBitItem(2, "hall_sensor_does_not_work_accurately"), //
                new OnOffBitItem(3, "smoke_alarm"), new OnOffBitItem(4, "general_leakage"),
                new OnOffBitItem(5, "severe_leakage"), new OnOffBitItem(13, "high_voltage_offset"),
                new OnOffBitItem(14, "low_voltage_offset"), new OnOffBitItem(15, "high_temperature_offset")),

        /* 3007 */new BitWordElement("BECU1_Fault1", //
                new OnOffBitItem(2, "voltage_sampling_circuit_abnormal"), //
                new OnOffBitItem(4, "voltage_sampling_line_breake"), //
                new OnOffBitItem(5, "temperature_sampling_line_breakes"), //
                new OnOffBitItem(6, "master_slave_CAN_breakes"), new OnOffBitItem(9, "current_sampling_fail"),
                new OnOffBitItem(10, "battery_fail"), new OnOffBitItem(11, "contactor_1_test_back_abnormal"),
                new OnOffBitItem(12, "contactor_2_test_back_abnormal"),
                new OnOffBitItem(13, "contactor_3_test_back_abnormal"),
                new OnOffBitItem(14, "contactor_4_test_back_abnormal"),
                new OnOffBitItem(15, "contactor_5_test_back_abnormal")),

        /* 3008 */new BitWordElement("BECU1_Fault2", //
                new OnOffBitItem(2, "severe_high_temperature"), //
                new OnOffBitItem(7, "smoke_alarm"), //
                new OnOffBitItem(8, "fuse_high_temperature_alarm"), //
                new OnOffBitItem(10, "general_leakage"), new OnOffBitItem(11, "severe_leakage"),
                new OnOffBitItem(12, "master_stack_CAN_line_break"),
                new OnOffBitItem(13, "cathode_contactor_does_not_close")),

        /* 3009 */ new VersionWordItem("BECU1_Version"), /* version */
                /* 3010-3011 */ new ReservedElement(3010, 3011),
                /* 3012 */ new DecimalWordItem("BECU1_Min_Volt_NO"), /* 42370 */

        /* 3013 */ new DecimalWordItem("BECU1_Min_Volt"), /* 1mv */

        /* 3014 */ new DecimalWordItem("BECU1_Max_Volt_NO"), /* 42370 */

        /* 3015 */ new DecimalWordItem("BECU1_Max_Volt"), /* 1mv */

        /* 3016 */ new DecimalWordItem("BECU1_Min_Temp_NO"), /* 42370 */

        /* 3017 */ new DecimalWordItem("BECU1_Min_Temp", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3018 */ new DecimalWordItem("BECU1_Max_Temp_NO"), /* 42370 */

        /* 3019 */ new DecimalWordItem("BECU1_Max_Temp", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3020 */ new DecimalWordItem("BECU1_Volt1"), /* 1mv */

        /* 3021 */ new DecimalWordItem("BECU1_Volt2"), /* 1mv */

        /* 3022 */ new DecimalWordItem("BECU1_Volt3"), /* 1mv */

        /* 3023 */ new DecimalWordItem("BECU1_Volt4"), /* 1mv */

        /* 3024 */ new DecimalWordItem("BECU1_Volt5"), /* 1mv */

        /* 3025 */ new DecimalWordItem("BECU1_Volt6"), /* 1mv */

        /* 3026 */ new DecimalWordItem("BECU1_Volt7"), /* 1mv */

        /* 3027 */ new DecimalWordItem("BECU1_Volt8"), /* 1mv */

        /* 3028 */ new DecimalWordItem("BECU1_Volt9"), /* 1mv */

        /* 3029 */ new DecimalWordItem("BECU1_Volt10"), /* 1mv */

        /* 3030 */ new DecimalWordItem("BECU1_Volt11"), /* 1mv */

        /* 3031 */ new DecimalWordItem("BECU1_Volt12"), /* 1mv */

        /* 3032 */ new DecimalWordItem("BECU1_Volt13"), /* 1mv */

        /* 3033 */ new DecimalWordItem("BECU1_Volt14"), /* 1mv */

        /* 3034 */ new DecimalWordItem("BECU1_Volt15"), /* 1mv */

        /* 3035 */ new DecimalWordItem("BECU1_Volt16"), /* 1mv */

        /* 3036 */ new DecimalWordItem("BECU1_Temp1", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3037 */ new DecimalWordItem("BECU1_Temp2", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3038 */ new DecimalWordItem("BECU1_Temp3", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3039 */ new DecimalWordItem("BECU1_Temp4", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3040 */ new DecimalWordItem("BECU1_Temp5", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3041 */ new DecimalWordItem("BECU1_Temp6", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3042 */ new DecimalWordItem("BECU1_Temp7", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3043 */ new DecimalWordItem("BECU1_Temp8", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3044 */ new DecimalWordItem("BECU1_Temp9", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3045 */ new DecimalWordItem("BECU1_Temp10", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3046 */ new DecimalWordItem("BECU1_Temp11", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3047 */ new DecimalWordItem("BECU1_Temp12", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3048 */ new DecimalWordItem("BECU1_Temp13", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3049 */ new DecimalWordItem("BECU1_Temp14", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3050 */ new DecimalWordItem("BECU1_Temp15", 1, 40), /* [0,200] represents -40-160 ℃ */

        /* 3051 */ new DecimalWordItem("BECU1_Temp16", 1, 40))); /* [0,200] represents -40-160 ℃ */
        protocol.add(new ModbusElementRange(3200, /* 3200 */ new DecimalWordItem("BECU2_Charge_Curr", 0.1), /*  */

        /* 3201 */ new DecimalWordItem("BECU2_Discharge_Curr", 0.1), /*  */

        /* 3202 */ new DecimalWordItem("BECU2_Volt", 0.1), /*  */

        /* 3203 */ new DecimalWordItem("BECU2_Curr", 0.1), /*  */

        /* 3204 */ new DecimalWordItem("BECU2_SOC", 0.01), /*  */

        /* 3205 */ new DecimalWordItem("BECU2_Alarm1"), /*  */

        /* 3206 */ new DecimalWordItem("BECU2_Alarm2"), /*  */

        /* 3207 */ new DecimalWordItem("BECU2_Fault1"), /*  */

        /* 3208 */ new DecimalWordItem("BECU2_Fault2"), /*  */

        /* 3209 */ new DecimalWordItem("BECU2_Version"), /*  */
                /* 3210-3211 */ new ReservedElement(3210, 3211),
                /* 3212 */ new DecimalWordItem("BECU2_Min_Volt_NO"), /*  */

        /* 3213 */ new DecimalWordItem("BECU2_Min_Volt"), /*  */

        /* 3214 */ new DecimalWordItem("BECU2_Max_Volt_NO"), /*  */

        /* 3215 */ new DecimalWordItem("BECU2_Max_Volt"), /*  */

        /* 3216 */ new DecimalWordItem("BECU2_Min_Temp_NO"), /*  */

        /* 3217 */ new DecimalWordItem("BECU2_Min_Temp", 1, 40), /*  */

        /* 3218 */ new DecimalWordItem("BECU2_Max_Temp_NO"), /*  */

        /* 3219 */ new DecimalWordItem("BECU2_Max_Temp", 1, 40), /*  */

        /* 3220 */ new DecimalWordItem("BECU2_Volt1"), /*  */

        /* 3221 */ new DecimalWordItem("BECU2_Volt2"), /*  */

        /* 3222 */ new DecimalWordItem("BECU2_Volt3"), /*  */

        /* 3223 */ new DecimalWordItem("BECU2_Volt4"), /*  */

        /* 3224 */ new DecimalWordItem("BECU2_Volt5"), /*  */

        /* 3225 */ new DecimalWordItem("BECU2_Volt6"), /*  */

        /* 3226 */ new DecimalWordItem("BECU2_Volt7"), /*  */

        /* 3227 */ new DecimalWordItem("BECU2_Volt8"), /*  */

        /* 3228 */ new DecimalWordItem("BECU2_Volt9"), /*  */

        /* 3229 */ new DecimalWordItem("BECU2_Volt10"), /*  */

        /* 3230 */ new DecimalWordItem("BECU2_Volt11"), /*  */

        /* 3231 */ new DecimalWordItem("BECU2_Volt12"), /*  */

        /* 3232 */ new DecimalWordItem("BECU2_Volt13"), /*  */

        /* 3233 */ new DecimalWordItem("BECU2_Volt14"), /*  */

        /* 3234 */ new DecimalWordItem("BECU2_Volt15"), /*  */

        /* 3235 */ new DecimalWordItem("BECU2_Volt16"), /*  */

        /* 3236 */ new DecimalWordItem("BECU2_Temp1", 1, 40), /*  */

        /* 3237 */ new DecimalWordItem("BECU2_Temp2", 1, 40), /*  */

        /* 3238 */ new DecimalWordItem("BECU2_Temp3", 1, 40), /*  */

        /* 3239 */ new DecimalWordItem("BECU2_Temp4", 1, 40), /*  */

        /* 3240 */ new DecimalWordItem("BECU2_Temp5", 1, 40), /*  */

        /* 3241 */ new DecimalWordItem("BECU2_Temp6", 1, 40), /*  */

        /* 3242 */ new DecimalWordItem("BECU2_Temp7", 1, 40), /*  */

        /* 3243 */ new DecimalWordItem("BECU2_Temp8", 1, 40), /*  */

        /* 3244 */ new DecimalWordItem("BECU2_Temp9", 1, 40), /*  */

        /* 3245 */ new DecimalWordItem("BECU2_Temp10", 1, 40), /*  */

        /* 3246 */ new DecimalWordItem("BECU2_Temp11", 1, 40), /*  */

        /* 3247 */ new DecimalWordItem("BECU2_Temp12", 1, 40), /*  */

        /* 3248 */ new DecimalWordItem("BECU2_Temp13", 1, 40), /*  */

        /* 3249 */ new DecimalWordItem("BECU2_Temp14", 1, 40), /*  */

        /* 3250 */ new DecimalWordItem("BECU2_Temp15", 1, 40), /*  */

        /* 3251 */ new DecimalWordItem("BECU2_Temp16", 1, 40))); /*  */

        protocol.add(new ModbusElementRange(4000,

        /* 4000 */ new StringWordItem("System_Work_State", "Standby", "Off-grid", "On-Grid", "Fault", "Off-grid PV"),
                /* 4001 */ new StringWordItem("System_Work_Mode_State", "Emergency Mode", "Consumers peak pattern",
                        "Economic Model", "Eco Mode", "Debug Mode", "Smooth PV", "Remote scheduling", "37 Riyuan",
                        "Timing Mode"),
                /* 4002-4003 */ new ReservedElement(4002, 4003),
                /* 4004 */ new ShortDecimalWordItem("PCS_Grid_Power_Total"),
                /* 4005 */ new SecureDecimalWordItem("PCS_Load_Power_Total", 1, 0,
                        55536 /* 65536 - 1000 Error value of PCS_Load_Active */),
                /* 4006 */ new DecimalWordItem("PCS_PV_Power_Total"),

        /* 4007-4029 */ new ReservedElement(4007, 4029),
                /* 4030-4031 */ new ReverseDecimalDoublewordItem("PCS_Summary_Grid_Buy_Accumulative"),
                /* 4032-4033 */ new ReverseDecimalDoublewordItem("PCS_Summary_Grid_Sell_Accumulative"),
                /* 4034-4035 */ new ReverseDecimalDoublewordItem("PCS_Summary_Consumption_Accumulative"),
                /* 4036-4037 */ new ReverseDecimalDoublewordItem("PCS_Summary_PV_Accumulative")));

        protocol.add(new ModbusElementRange(4800, /* 4800 */ new DecimalWordItem("BECU_num"), /* Number */

        /* 4801 */ new DecimalWordItem("BECU_Work_State"), /* 0 */
                /* 4802-4802 */ new ReservedElement(4802, 4802),

        /* 4803 */ new DecimalWordItem("Stack_Charge_Curr", 0.1), /* 0.1A */

        /* 4804 */ new DecimalWordItem("Stack_Disharge_Curr", 0.1), /* 0.1A */

        /* 4805 */ new DecimalWordItem("Stack_Volt", 0.1), /* 0.1V */

        /* 4806 */ new DecimalWordItem("Stack_Curr", 0.1), /* 0.1A */

        /* 4807 */new BitWordElement("Stack_Work_State", //
                new OnOffBitItem(0, "initialization"), //
                new OnOffBitItem(1, "off_grid"), //
                new OnOffBitItem(2, "on_grid"), //
                new OnOffBitItem(3, "network_operation"), //
                new OnOffBitItem(4, "Stopping"), //
                new OnOffBitItem(5, "fault"), //
                new OnOffBitItem(6, "debugging"), new OnOffBitItem(7, "lock"), new OnOffBitItem(8, "low_power")),

        /* 4808 */new BitWordElement("Stack_Fault1", //
                new OnOffBitItem(0, "no_available_battery_group"), //
                new OnOffBitItem(1, "stack_general_leakage"), //
                new OnOffBitItem(2, "stack_severe_leakage"), //
                new OnOffBitItem(3, "stack_starting_fail"), //
                new OnOffBitItem(4, "stack_stopping_fail")),

        /* 4809 */new BitWordElement("Stack_Fault1", //
                new OnOffBitItem(0, "stack_group_0_CAN_communication_interrupt"), //
                new OnOffBitItem(1, "stack_group_1_CAN_communication_interrupt"), //
                new OnOffBitItem(2, "stack_group_2_CAN_communication_interrupt"), //
                new OnOffBitItem(3, "stack_group_3_CAN_communication_interrupt"), //
                new OnOffBitItem(4, "stack_group_4_CAN_communication_interrupt"), //
                new OnOffBitItem(5, "stack_group_5_CAN_communication_interrupt"), //
                new OnOffBitItem(6, "stack_group_6_CAN_communication_interrupt"), //
                new OnOffBitItem(7, "stack_group_7_CAN_communication_interrupt"), //
                new OnOffBitItem(8, "stack_group_8_CAN_communication_interrupt"), //
                new OnOffBitItem(9, "stack_group_9_CAN_communication_interrupt"), //
                new OnOffBitItem(10, "stack_group_10_CAN_communication_interrupt"), //
                new OnOffBitItem(11, "stack_group_11_CAN_communication_interrupt"), //
                new OnOffBitItem(12, "stack_group_12_CAN_communication_interrupt"), //
                new OnOffBitItem(13, "stack_group_13_CAN_communication_interrupt"), //
                new OnOffBitItem(14, "stack_group_14_CAN_communication_interrupt"), //
                new OnOffBitItem(15, "stack_group_15_CAN_communication_interrupt") //
        ),

        /* 4810 */new BitWordElement("Stack_Alarm1", //
                new OnOffBitItem(0, "alarm_charge"), //
                new OnOffBitItem(1, "alarm_discharge"), //
                new OnOffBitItem(2, "limit_alarm_charge"), //
                new OnOffBitItem(3, "limit_alarm_discharge"), //
                new OnOffBitItem(4, "high_voltage_alarm"), //
                new OnOffBitItem(5, "low_voltage_alarm"), //
                new OnOffBitItem(6, "abnormal_voltage_change_alarm"), //
                new OnOffBitItem(7, "high_temperature_alarm"), //
                new OnOffBitItem(8, "low_temperature_alarm"), //
                new OnOffBitItem(9, "abnormal_temperature_change_alarm"), //
                new OnOffBitItem(10, "severe_high_voltage_alarm"), //
                new OnOffBitItem(11, "severe_low_voltage_alarm"), //
                new OnOffBitItem(12, "severe_low_temperature_alarm"), //
                new OnOffBitItem(13, "severve_over_current_alarm_charge"), //
                new OnOffBitItem(14, "severve_over_current_alarm_discharge"), //
                new OnOffBitItem(15, "abnormal_cell_capacity_alarm") //
        ),

        /* 4811 */new BitWordElement("Stack_Alarm2", //
                new OnOffBitItem(0, "parameter_EEPROM_lose_effectiveness"), //
                new OnOffBitItem(1, "isolating_switch_confluencea_ark_break"), //
                new OnOffBitItem(2, "communication_between_stack_temperature_break"), //
                new OnOffBitItem(3, "temperature_collector_fail"), //
                new OnOffBitItem(4, "hall_sensor_do_not_work_accurately"), //
                new OnOffBitItem(5, "communication_PCS_break"), //
                new OnOffBitItem(6, "advanced_charging_or_main_contactor_close_abnormally"), //
                new OnOffBitItem(7, "abnormal_sampled_voltage"), //
                new OnOffBitItem(8, "abnormal_advanced_contactor_or_abnormal_RS485_gallery_of_PCS"), //
                new OnOffBitItem(9, "abnormal_main_contactor"), //
                new OnOffBitItem(10, "general_leakage"), //
                new OnOffBitItem(11, "severe_leakage"), //
                new OnOffBitItem(12, "smoke_alarm"), //
                new OnOffBitItem(13, "communication_wire_ammeter_break") //
        ),

        /* 4812 */ new DecimalWordItem("Stack_SOC"), /* % */
                /* 4813-4813 */ new ReservedElement(4813, 4813),

        /* 4815-4814 */ new DecimalDoublewordItem("Stack_ChargeE"), /* kwh */

        /* 4817-4816 */ new DecimalDoublewordItem("Stack_DischargeE"), /* kwh */
                /* 4818-4820 */ new ReservedElement(4818, 4820),
                /* 4821 */ new VersionWordItem("Stack_Version"), /* 10 shows a version 1.0 */
                /* 4822-4823 */ new ReservedElement(4822, 4823),
                /* 4824 */ new DecimalWordItem("L1_Power", 1), /* 10w */

        /* 4825 */ new DecimalWordItem("L2_Power", 1), /* 10w */

        /* 4826 */ new DecimalWordItem("L3_Power", 1), /* 10w */
                /* 4827-4849 */ new ReservedElement(4827, 4849),
                /* 4850 */ new DecimalWordItem("Stack_Power", 1), /* 1W */

        /* 4851 */ new StringWordItem("Buttons_State", "No by", "Adoption"),

        /* 4852 */ new StringWordItem("Power_Button_State", "No by", "Adoption"),

        /* 4853 */ new StringWordItem("Local_Button_State", "No by", "Adoption"),

        /* 4854 */ new StringWordItem("Remote_Button_State", "No by", "Adoption"),

        /* 4855 */ new StringWordItem("Network_State", "anomaly", "Normal"),

        /* 4856 */ new StringWordItem("Hardware_Ctrl_State", "Off", "On")));
        protocol.add(new ModbusElementRange(5003, /* 5003-5004 */ new DecimalDoublewordItem("PCS_SellE"), /* 0.1khw */

        /* 5005-5006 */ new DecimalDoublewordItem("PCS_BuyE"), /* 0.1khw */

        /* 5007-5010 */ new ReservedElement(5007, 5010),
                /* 5011-5012 */ new DecimalDoublewordItem("Load_UsedE") /* 0.1khw */

        ));
        protocol.add(new ModbusElementRange(30111,
                /* 30111 */ new DecimalWordItem("On_Grid_Under_Volt_Protection_Limit"), /* 1min */

        /* 30112 */ new DecimalWordItem("On_Grid_Under_Freq_Protection_Limit"), /* 1h */

        /* 30113 */ new DecimalWordItem("On_Grid_Over_Volt_Protection_Limit"), /* 1min */

        /* 30114 */ new DecimalWordItem("On_Grid_Over_Freq_Protection_Limit"), /* 1h */

        /* 30115 */ new StringWordItem("Grid_Standard_State", "VDE4105", "AS4777", "CEI021"),
                /* 30116-30128 */ new ReservedElement(30116, 30128),
                /* 30129 */ new DecimalWordItem("StartChargeMin_State"), /* 1min */

        /* 30130 */ new DecimalWordItem("StartChargeHour_State"), /* 1h */

        /* 30131 */ new DecimalWordItem("EndChargeMin_State"), /* 1min */

        /* 30132 */ new DecimalWordItem("EndChargeHour_State"), /* 1h */

        /* 30133 */ new DecimalWordItem("StartDischargeMin_State"), /* 1min */

        /* 30134 */ new DecimalWordItem("StartDischargeHour_State"), /* 1h */

        /* 30135 */ new DecimalWordItem("EndDischargeMin_State"), /* 1min */

        /* 30136 */ new DecimalWordItem("EndDischargeHour_State"), /* 1h */

        /* 30137 */ new DecimalWordItem("MaxSOC"), /* % */

        /* 30138 */ new DecimalWordItem("MinSOC"), /* % */

        /* 30139 */ new DecimalWordItem("ChargeSOC"), /* % */
                /* 30140-30156 */ new ReservedElement(30140, 30156),
                /* 30157 */ new StringWordItem("Start_State", "Off", "On"),
                /* 30158-30164 */ new ReservedElement(30158, 30164),
                /* 30165 */ new StringWordItem("Off_Grid_Ctrl_State", "Enable", "Disable"),

        /* 30166 */ new StringWordItem("On_Grid_Ctrl_State", "Enable", "Disable"),

        /* 30167 */ new StringWordItem("PV_Phase_State", "", "single-phase PV", "", "Three-phase PV"),

        /* 30168 */ new StringWordItem("Mini_Phase_State", "L1", "L2", "L3"),

        /* 30169 */ new StringWordItem("Power_Interaction_State", "Enable", "Disable")));
        protocol.add(new ModbusElementRange(30111,
                /* 30557 */ new StringWordItem("System_Ctrl_Mode_State", "Local Strategy", "Remote Policy")));

        return protocol;
    }
}
