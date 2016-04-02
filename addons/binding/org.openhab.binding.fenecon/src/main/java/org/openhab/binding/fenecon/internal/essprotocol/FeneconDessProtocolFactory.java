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
import org.openhab.binding.fenecon.internal.essprotocol.modbus.PercentageWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ReservedElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.StringWordItem;

public class FeneconDessProtocolFactory {
    public static ArrayList<ModbusElementRange> getProtocol() {
        ArrayList<ModbusElementRange> protocol = new ArrayList<ModbusElementRange>();
        // TODO: Change to StringWordItems where possible
        protocol.add(new ModbusElementRange(10000,
                /* 0 */ new StringWordItem("DESS_System_running_status", "Initialization", "Standby", "Starting",
                        "Running", "Fault"),
                /* 1 */ new StringWordItem("BSMU_BSMU_Working_Status", "Initialization", "Off-Grid", "Being On-Grid",
                        "On-Grid Operation", "Being Stop", "Fault", "Debug Mode", "Reserved", "Low Consumption Mode",
                        "Start Precharging", "Precharging"),
                /* 2 */ new StringWordItem("BSMU_Stack_Charging_discharging_Status",
                        "Standby (Neither Charging nor Discharging)", "Charging", "Discharging"),
                /* 3 */ new StringWordItem("BSMU_Status_of_Battery_Group_Contactors", "Totally Disconnected",
                        "Partially Disconnected"),
                /* 4 */ new BitWordElement("BSMU_Battery_on_grid_status", new OnOffBitItem(0, "Group0"),
                        new OnOffBitItem(1, "Group1"), new OnOffBitItem(2, "Group2"), new OnOffBitItem(3, "Group3"),
                        new OnOffBitItem(4, "Group4"), new OnOffBitItem(5, "Group5"), new OnOffBitItem(6, "Group6"),
                        new OnOffBitItem(7, "Group7"), new OnOffBitItem(8, "Group8"), new OnOffBitItem(9, "Group9"),
                        new OnOffBitItem(10, "Group10"), new OnOffBitItem(11, "Group11"),
                        new OnOffBitItem(12, "Group12"), new OnOffBitItem(13, "Group13"),
                        new OnOffBitItem(14, "Group14"), new OnOffBitItem(15, "Group15")),
                /*
                 * Every Bit Forming One Group
                 * 0: Not On_grid
                 * 1: On_grid
                 */
                /* 5 */ new StringWordItem("BSMU_Working_Mode_of_Battery_Stack", "Normal Mode", "Nominal Mode"),
                /* 6 */
                new BitWordElement("BSMU_Battery_Enabling_Status", new OnOffBitItem(0, "Group0"),
                        new OnOffBitItem(1, "Group1"), new OnOffBitItem(2, "Group2"), new OnOffBitItem(3, "Group3"),
                        new OnOffBitItem(4, "Group4"), new OnOffBitItem(5, "Group5"), new OnOffBitItem(6, "Group6"),
                        new OnOffBitItem(7, "Group7"), new OnOffBitItem(8, "Group8"), new OnOffBitItem(9, "Group9"),
                        new OnOffBitItem(10, "Group10"), new OnOffBitItem(11, "Group11"),
                        new OnOffBitItem(12, "Group12"), new OnOffBitItem(13, "Group13"),
                        new OnOffBitItem(14, "Group14"), new OnOffBitItem(15, "Group15")),
                /*
                 * Every Bit Forming One Group
                 * 0: Forbidden
                 * 1: Enable
                 */
                /* 7 */
                new BitWordElement("BSMU_Isolation_Switch_Status_of_Battery", new OnOffBitItem(0, "Group0"),
                        new OnOffBitItem(1, "Group1"), new OnOffBitItem(2, "Group2"), new OnOffBitItem(3, "Group3"),
                        new OnOffBitItem(4, "Group4"), new OnOffBitItem(5, "Group5"), new OnOffBitItem(6, "Group6"),
                        new OnOffBitItem(7, "Group7"), new OnOffBitItem(8, "Group8"), new OnOffBitItem(9, "Group9"),
                        new OnOffBitItem(10, "Group10"), new OnOffBitItem(11, "Group11"),
                        new OnOffBitItem(12, "Group12"), new OnOffBitItem(13, "Group13"),
                        new OnOffBitItem(14, "Group14"), new OnOffBitItem(15, "Group15")),
                /*
                 * Every Bit Forming One Group
                 * 0: Disconnected
                 * 1: Connected
                 */
                /* 8 */
                new BitWordElement("BSMU_Status_of_PCS_Cabinet", new OnOffBitItem(0, "Isolation_Switch1"),
                        new OnOffBitItem(1, "Isolation_Switch2")),
                /*
                 * 0: Disconnected
                 * 1: Connected
                 * bit0: Isolation Switch 1
                 * bit1: Isolation Switch 2
                 */
                /* 9 */ new DecimalWordItem("BSMU_Enabling_Status_of_Dynamic_On_grid"),
                /*
                 * 0: Forbidden
                 * 1: Enabling
                 */

        /* 10 */ new DecimalWordItem("BSMU_Dynamic_Fault_Isolation_Enabling_Status"),
                /*
                 * 0: Forbidden
                 * 1: Enabling
                 */
                /* 11 */ new DecimalWordItem("BSMU_Minimum_Working_Groups_of_Fault_Isolated"),
                /* 12-19 */ new ReservedElement(12, 19),
                /* 20 */ new BitWordElement("BSMU_PBMS_Warning_Information0",
                        new OnOffBitItem(0, "Stack_Charging_Common_Over_current_Warning"),
                        new OnOffBitItem(1, "Stack_Discharging_Common_Over_current_Warning"),
                        new OnOffBitItem(2, "Stack_Charging_Current_Limited_Warning"),
                        new OnOffBitItem(3, "Stack_Discharging_Current_Limited_Warning"),
                        new OnOffBitItem(4, "Stack_Common_High_voltage_Warning"),
                        new OnOffBitItem(5, "Stack_Common_Low_voltage_Warning"),
                        new OnOffBitItem(6, "Stack_Voltage_Change_Abnormal_Warning"),
                        new OnOffBitItem(7, "Stack_Common_High_temperature_Warning"),
                        new OnOffBitItem(8, "Stack_Common_Low_temperature_Warning"),
                        new OnOffBitItem(9, "Stack_Temperature_Change_Abnormal_Warning"),
                        new OnOffBitItem(10, "Stack_Serious_High_voltage_Warning"),
                        new OnOffBitItem(11, "Stack_Serious_Low_voltage_Warning"),
                        new OnOffBitItem(12, "Stack_Serious_Low_temperature_Warning"),
                        new OnOffBitItem(13, "Stack_Charging_Serious_Over_current_Warning"),
                        new OnOffBitItem(14, "Stack_Discharging_Serious_Over_current_Warning"),
                        new OnOffBitItem(15, "Stack_Battery_Capacity_Abnormal_Warning")),
                /* 21 */ new BitWordElement("BSMU_PBMS_Warning_Information1",
                        new OnOffBitItem(0, "Stack_EEPROM_Parameter_Failure"),
                        new OnOffBitItem(1, "Combiner_Cabinet_Isolation_Switch_Disconnected"),
                        new OnOffBitItem(2, "Communication_Disconnected_between_Stack_and_Temperature_Collector"),
                        new OnOffBitItem(3, "Temperature_Collector_Failure"), new OnOffBitItem(4, "Hall_Inaccurate"),
                        new OnOffBitItem(5, "PCS_Communication_Failure"),
                        new OnOffBitItem(6, "Pre_charging_or_Main_Contactor_Abnormal_Engaged"),
                        new OnOffBitItem(7, "Voltage_Sampling_Abnormal"),
                        new OnOffBitItem(8, "Pre_charging_Contactor_Abnormal_or_PCS485_Channel_Abnormal"),
                        new OnOffBitItem(9, "Main_Contactor_Abnormal")),
                /* 22 */ new BitWordElement("BSMU_PBMS_Warning_Information2",
                        new OnOffBitItem(0, "Group_0_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(1, "Group_1_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(2, "Group_2_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(3, "Group_3_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(4, "Group_4_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(5, "Group_5_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(6, "Group_6_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(7, "Group_7_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(8, "Group_8_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(9, "Group_9_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(10, "Group_10_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(11, "Group_11_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(12, "Group_12_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(13, "Group_13_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(14, "Group_14_Enabling_Unable_to_Be_On_grid"),
                        new OnOffBitItem(15, "Group_15_Enabling_Unable_to_Be_On_grid")),
                /* 23 */ new BitWordElement("BSMU_PBMS_Warning_Information3",
                        new OnOffBitItem(0, "Group_0_Isolation_Switch_Open"),
                        new OnOffBitItem(1, "Group_1_Isolation_Switch_Open"),
                        new OnOffBitItem(2, "Group_2_Isolation_Switch_Open"),
                        new OnOffBitItem(3, "Group_3_Isolation_Switch_Open"),
                        new OnOffBitItem(4, "Group_4_Isolation_Switch_Open"),
                        new OnOffBitItem(5, "Group_5_Isolation_Switch_Open"),
                        new OnOffBitItem(6, "Group_6_Isolation_Switch_Open"),
                        new OnOffBitItem(7, "Group_7_Isolation_Switch_Open"),
                        new OnOffBitItem(8, "Group_8_Isolation_Switch_Open"),
                        new OnOffBitItem(9, "Group_9_Isolation_Switch_Open"),
                        new OnOffBitItem(10, "Group_10_Isolation_Switch_Open"),
                        new OnOffBitItem(11, "Group_11_Isolation_Switch_Open"),
                        new OnOffBitItem(12, "Group_12_Isolation_Switch_Open"),
                        new OnOffBitItem(13, "Group_13_Isolation_Switch_Open"),
                        new OnOffBitItem(14, "Group_14_Isolation_Switch_Open"),
                        new OnOffBitItem(15, "Group_15_Isolation_Switch_Open")),
                /* 24 */ new ReservedElement(24),
                /*
                 * 0: Normal
                 * 1:Warning
                 */
                /* 25 */ new DecimalWordItem("BSMU_Distributed_Equilibrium_Sampling_Warning"),
                /* 26 */ new DecimalWordItem("BSMU_Distributed_Equilibrium_Control_Warning"),
                /* 27 */ new DecimalWordItem("BSMU_Distributed_Warning_Reserved"),
                /* 28-29 */ new ReservedElement(28, 29),
                /* 30 */ new BitWordElement("BSMU_PBMS_Fault_Information0",
                        new OnOffBitItem(0, "No_Enabling_Group_or_no_Available_Battery_Group"),
                        new OnOffBitItem(1, "Stack_Common_Leakage"), new OnOffBitItem(2, "Stack_Serious_Leakage"),
                        new OnOffBitItem(3, "Stack_Startup_Failed"), new OnOffBitItem(4, "Stack_Shutdown_Failed")),
                /* 31 */ new BitWordElement("BSMU_PBMS_Fault_Information0",
                        new OnOffBitItem(0, "CAN_Communication_between_Stack_and_Group_0_broken"),
                        new OnOffBitItem(1, "CAN_Communication_between_Stack_and_Group_1_broken"),
                        new OnOffBitItem(2, "CAN_Communication_between_Stack_and_Group_2_broken"),
                        new OnOffBitItem(3, "CAN_Communication_between_Stack_and_Group_3_broken"),
                        new OnOffBitItem(4, "CAN_Communication_between_Stack_and_Group_4_broken"),
                        new OnOffBitItem(5, "CAN_Communication_between_Stack_and_Group_5_broken"),
                        new OnOffBitItem(6, "CAN_Communication_between_Stack_and_Group_6_broken"),
                        new OnOffBitItem(7, "CAN_Communication_between_Stack_and_Group_7_broken"),
                        new OnOffBitItem(8, "CAN_Communication_between_Stack_and_Group_8_broken"),
                        new OnOffBitItem(9, "CAN_Communication_between_Stack_and_Group_9_broken"),
                        new OnOffBitItem(10, "CAN_Communication_between_Stack_and_Group_10_broken"),
                        new OnOffBitItem(11, "CAN_Communication_between_Stack_and_Group_11_broken"),
                        new OnOffBitItem(12, "CAN_Communication_between_Stack_and_Group_12_broken"),
                        new OnOffBitItem(13, "CAN_Communication_between_Stack_and_Group_13_broken"),
                        new OnOffBitItem(14, "CAN_Communication_between_Stack_and_Group_14_broken"),
                        new OnOffBitItem(15, "CAN_Communication_between_Stack_and_Group_15_broken")),
                /* 32-39 */ new ReservedElement(32, 39),
                /*
                 * 0: Normal
                 * 1: Fault
                 */
                /* 40 */ new DecimalWordItem("BSMU_Protection_Status"),
                /*
                 * 0: Normal
                 * 1: Common Warning
                 * 2: Serious Warning
                 * 3: Common Fault
                 * 4: Serious Fault
                 */
                /* 41 */ new DecimalWordItem("BSMU_Factory_Settings_Result"),
                /*
                 * 0: Not Operate
                 * 1: Save Successfully
                 * 2: Save Failed
                 * 3: Save and Verify Failed
                 * 4: Restore Successful
                 * 5: EEP Parameters Restore Failed
                 * 6: Restore Backup Parameters Failed
                 * 7: EEP Parameters Modify Abnormal
                 */
                /* 42-44 */ new ReservedElement(42, 44),
                /* 45 */ new BitWordElement("BECU0_Battery_Group0_status", new OnOffBitItem(0, "Contactor0"),
                        new OnOffBitItem(1, "Contactor1"), new OnOffBitItem(2, "Contactor2"),
                        new OnOffBitItem(3, "Contactor3"), new OnOffBitItem(4, "Contactor4"),
                        new OnOffBitItem(5, "Contactor5"), new OnOffBitItem(6, "Contactor6"),
                        new OnOffBitItem(7, "Contactor7"), new OnOffBitItem(8, "Contactor8"),
                        new OnOffBitItem(9, "Contactor9"), new OnOffBitItem(10, "Contactor10"),
                        new OnOffBitItem(11, "Contactor11"), new OnOffBitItem(12, "Contactor12"),
                        new OnOffBitItem(13, "Contactor13"), new OnOffBitItem(14, "Contactor14"),
                        new OnOffBitItem(15, "Contactor15")),
                /* 46 */ new BitWordElement("BECU0_IO_Detection_Status_of_Battery_Group0",
                        new OnOffBitItem(0, "Periphery0"), new OnOffBitItem(1, "Periphery"),
                        new OnOffBitItem(2, "Periphery2"), new OnOffBitItem(3, "Periphery3"),
                        new OnOffBitItem(4, "Periphery4"), new OnOffBitItem(5, "Periphery5"),
                        new OnOffBitItem(6, "Periphery6"), new OnOffBitItem(7, "Periphery7"),
                        new OnOffBitItem(8, "Periphery8"), new OnOffBitItem(9, "Periphery9"),
                        new OnOffBitItem(10, "Periphery10"), new OnOffBitItem(11, "Periphery11"),
                        new OnOffBitItem(12, "Periphery12"), new OnOffBitItem(13, "Periphery13"),
                        new OnOffBitItem(14, "Periphery14"), new OnOffBitItem(15, "Periphery15")),
                /* 47 */ new DecimalWordItem("BECU0_BMS0_Warning_Information"),
                /* 48 */ new DecimalWordItem("BECU0_BMS0_Fault_Information0"),
                /* 49 */ new DecimalWordItem("BECU0_BMS0_Fault_Information1"),
                /* 50 */ new BitWordElement("BECU1_Battery_Group1_status", new OnOffBitItem(0, "Contactor0"),
                        new OnOffBitItem(1, "Contactor1"), new OnOffBitItem(2, "Contactor2"),
                        new OnOffBitItem(3, "Contactor3"), new OnOffBitItem(4, "Contactor4"),
                        new OnOffBitItem(5, "Contactor5"), new OnOffBitItem(6, "Contactor6"),
                        new OnOffBitItem(7, "Contactor7"), new OnOffBitItem(8, "Contactor8"),
                        new OnOffBitItem(9, "Contactor9"), new OnOffBitItem(10, "Contactor10"),
                        new OnOffBitItem(11, "Contactor11"), new OnOffBitItem(12, "Contactor12"),
                        new OnOffBitItem(13, "Contactor13"), new OnOffBitItem(14, "Contactor14"),
                        new OnOffBitItem(15, "Contactor15")),
                /* 51 */ new BitWordElement("BECU1_IO_Detection_Status_of_Battery_Group1",
                        new OnOffBitItem(0, "Periphery0"), new OnOffBitItem(1, "Periphery"),
                        new OnOffBitItem(2, "Periphery2"), new OnOffBitItem(3, "Periphery3"),
                        new OnOffBitItem(4, "Periphery4"), new OnOffBitItem(5, "Periphery5"),
                        new OnOffBitItem(6, "Periphery6"), new OnOffBitItem(7, "Periphery7"),
                        new OnOffBitItem(8, "Periphery8"), new OnOffBitItem(9, "Periphery9"),
                        new OnOffBitItem(10, "Periphery10"), new OnOffBitItem(11, "Periphery11"),
                        new OnOffBitItem(12, "Periphery12"), new OnOffBitItem(13, "Periphery13"),
                        new OnOffBitItem(14, "Periphery14"), new OnOffBitItem(15, "Periphery15")),
                /* 52 */ new DecimalWordItem("BECU1_BMS1_Warning_Information"),
                /* 53 */ new DecimalWordItem("BECU1_BMS1_Fault_Information0"),
                /* 54 */ new DecimalWordItem("BECU1_BMS1_Fault_Information1"),
                /* 55 */ new BitWordElement("BECU2_Battery_Group2_status", new OnOffBitItem(0, "Contactor0"),
                        new OnOffBitItem(1, "Contactor1"), new OnOffBitItem(2, "Contactor2"),
                        new OnOffBitItem(3, "Contactor3"), new OnOffBitItem(4, "Contactor4"),
                        new OnOffBitItem(5, "Contactor5"), new OnOffBitItem(6, "Contactor6"),
                        new OnOffBitItem(7, "Contactor7"), new OnOffBitItem(8, "Contactor8"),
                        new OnOffBitItem(9, "Contactor9"), new OnOffBitItem(10, "Contactor10"),
                        new OnOffBitItem(11, "Contactor11"), new OnOffBitItem(12, "Contactor12"),
                        new OnOffBitItem(13, "Contactor13"), new OnOffBitItem(14, "Contactor14"),
                        new OnOffBitItem(15, "Contactor15")),
                /* 56 */ new BitWordElement("BECU2_IO_Detection_Status_of_Battery_Group2",
                        new OnOffBitItem(0, "Periphery0"), new OnOffBitItem(1, "Periphery"),
                        new OnOffBitItem(2, "Periphery2"), new OnOffBitItem(3, "Periphery3"),
                        new OnOffBitItem(4, "Periphery4"), new OnOffBitItem(5, "Periphery5"),
                        new OnOffBitItem(6, "Periphery6"), new OnOffBitItem(7, "Periphery7"),
                        new OnOffBitItem(8, "Periphery8"), new OnOffBitItem(9, "Periphery9"),
                        new OnOffBitItem(10, "Periphery10"), new OnOffBitItem(11, "Periphery11"),
                        new OnOffBitItem(12, "Periphery12"), new OnOffBitItem(13, "Periphery13"),
                        new OnOffBitItem(14, "Periphery14"), new OnOffBitItem(15, "Periphery15")),
                /* 57 */ new DecimalWordItem("BECU2_BMS2_Warning_Information"),
                /* 58 */ new DecimalWordItem("BECU2_BMS2_Fault_Information0"),
                /* 59 */ new DecimalWordItem("BECU2_BMS2_Fault_Information1"),
                /* 60 */ new BitWordElement("BECU3_Battery_Group3_status", new OnOffBitItem(0, "Contactor0"),
                        new OnOffBitItem(1, "Contactor1"), new OnOffBitItem(2, "Contactor2"),
                        new OnOffBitItem(3, "Contactor3"), new OnOffBitItem(4, "Contactor4"),
                        new OnOffBitItem(5, "Contactor5"), new OnOffBitItem(6, "Contactor6"),
                        new OnOffBitItem(7, "Contactor7"), new OnOffBitItem(8, "Contactor8"),
                        new OnOffBitItem(9, "Contactor9"), new OnOffBitItem(10, "Contactor10"),
                        new OnOffBitItem(11, "Contactor11"), new OnOffBitItem(12, "Contactor12"),
                        new OnOffBitItem(13, "Contactor13"), new OnOffBitItem(14, "Contactor14"),
                        new OnOffBitItem(15, "Contactor15")),
                /* 61 */ new BitWordElement("BECU3_IO_Detection_Status_of_Battery_Group3",
                        new OnOffBitItem(0, "Periphery0"), new OnOffBitItem(1, "Periphery"),
                        new OnOffBitItem(2, "Periphery2"), new OnOffBitItem(3, "Periphery3"),
                        new OnOffBitItem(4, "Periphery4"), new OnOffBitItem(5, "Periphery5"),
                        new OnOffBitItem(6, "Periphery6"), new OnOffBitItem(7, "Periphery7"),
                        new OnOffBitItem(8, "Periphery8"), new OnOffBitItem(9, "Periphery9"),
                        new OnOffBitItem(10, "Periphery10"), new OnOffBitItem(11, "Periphery11"),
                        new OnOffBitItem(12, "Periphery12"), new OnOffBitItem(13, "Periphery13"),
                        new OnOffBitItem(14, "Periphery14"), new OnOffBitItem(15, "Periphery15")),
                /* 62 */ new DecimalWordItem("BECU3_BMS3_Warning_Information"),
                /* 63 */ new DecimalWordItem("BECU3_BMS3_Fault_Information0"),
                /* 64 */ new DecimalWordItem("BECU3_BMS3_Fault_Information1")));

        protocol.add(new ModbusElementRange(10100,
                /* 100 */ new StringWordItem("PCS_Summary_Inverter_Overall_Operation_Status", "Self inspection",
                        "Standby", "Being Startup", "Off-Grid operation", "On-Grid operation", "Fault"),
                /* 101 */
                new BitWordElement("PCS1_Inverter1_Warning_Code1", new OnOffBitItem(0, "Grid_Under_Voltage"),
                        new OnOffBitItem(1, "Grid_Over_Voltage"), new OnOffBitItem(2, "Grid_under_Frequency"),
                        new OnOffBitItem(3, "Grid_Over_Frequency"), new OnOffBitItem(4, "Grid_flicker"),
                        new OnOffBitItem(5, "Grid_Abnormal"), new OnOffBitItem(6, "DC_Under_Voltage"),
                        new OnOffBitItem(7, "Input_Over_resistance"),
                        new OnOffBitItem(8, "Combine_Jumper_Settings_Wrong"),
                        new OnOffBitItem(9, "Communication_with_Inverter_failure"),
                        new OnOffBitItem(10, "System_Time_Failure")),
                /* 102 */ new ReservedElement(102),
                /* 103 */ new BitWordElement("PCS1_Inverter1_Fault_Code1", new OnOffBitItem(0, "Over_load_100_110"),
                        new OnOffBitItem(1, "Over_load_110_120"), new OnOffBitItem(2, "Over_load_120_150"),
                        new OnOffBitItem(3, "Over_load_150_200"), new OnOffBitItem(4, "Over_load_200_220"),
                        new OnOffBitItem(5, "Over_load_220_300"), new OnOffBitItem(6, "Inverter_serious_Over_Current"),
                        new OnOffBitItem(7, "Grid_Over_Current"),
                        new OnOffBitItem(8, "Repeated_Inverter_common_over_currents"),
                        new OnOffBitItem(9, "Invert_voltage_sampling_ref_error"),
                        new OnOffBitItem(10, "Grid_voltage_sampling_ref_error"),
                        new OnOffBitItem(11, "Control_Current_sampling_ref_error"),
                        new OnOffBitItem(12, "Invert_Current_sampling_ref_error"),
                        new OnOffBitItem(13, "Grid_Current_sampling_ref_error"),
                        new OnOffBitItem(14, "PDP_Protection_Power_Components_Driver_Protection"),
                        new OnOffBitItem(15, "Hardware_over_current_protection")),
                /* 104 */ new BitWordElement("PCS1_Inverter1_Fault_Code2",
                        new OnOffBitItem(0, "Hardware_Invert_Over_Voltage_Protection"),
                        new OnOffBitItem(1, "Hardware_DC_Over_Voltage_Protection"),
                        new OnOffBitItem(2, "Hardware_Over_Temperature_Protection"),
                        new OnOffBitItem(3, "No_Signal_Captured"), new OnOffBitItem(4, "DC_Over_Voltage"),
                        new OnOffBitItem(5, "Battery_disconnected"), new OnOffBitItem(6, "Invert_under_Voltage"),
                        new OnOffBitItem(7, "Invert_Over_Voltage"), new OnOffBitItem(8, "Current_Sensor_Fault"),
                        new OnOffBitItem(9, "Voltage_Sensor_Fault"), new OnOffBitItem(12, "Fan_Fault"),
                        new OnOffBitItem(13, "Phase_break"), new OnOffBitItem(14, "Invert_Relay_Fault"),
                        new OnOffBitItem(15, "Grid_Relay_Fault")),
                /* 105 */ new BitWordElement("PCS1_Inverter1_Fault_Code3",
                        new OnOffBitItem(0, "Control_PCB_Over_temperature"),
                        new OnOffBitItem(1, "Power_PCB_Over_temperature"),
                        new OnOffBitItem(2, "DC_terminal_Over_temperature"),
                        new OnOffBitItem(3, "Capacitor_Over_temperature"),
                        new OnOffBitItem(4, "Radiator_Over_temperature"),
                        new OnOffBitItem(5, "Transformer_Over_temperature"),
                        new OnOffBitItem(6, "3_phase_Combine_Communication_Fault"), new OnOffBitItem(7, "EEPROM_Fault"),
                        new OnOffBitItem(8, "Load_sampling_ref_error"),
                        new OnOffBitItem(9, "Power_on_current_limitation_fault"),
                        new OnOffBitItem(10, "3_phase_Combine_Signal_Synchronization_Mistake")),
                /* 106 */ new BitWordElement("PCS2_Inverter2_Warning_Code1", new OnOffBitItem(0, "Grid_Under_Voltage"),
                        new OnOffBitItem(1, "Grid_Over_Voltage"), new OnOffBitItem(2, "Grid_under_Frequency"),
                        new OnOffBitItem(3, "Grid_Over_Frequency"), new OnOffBitItem(4, "Grid_flicker"),
                        new OnOffBitItem(5, "Grid_Abnormal"), new OnOffBitItem(6, "DC_Under_Voltage"),
                        new OnOffBitItem(7, "Input_Over_resistance"),
                        new OnOffBitItem(8, "Combine_Jumper_Settings_Wrong"),
                        new OnOffBitItem(9, "Communication_with_Inverter_failure"),
                        new OnOffBitItem(10, "System_Time_Failure")),
                /* 107 */ new ReservedElement(107),
                /* 108 */ new BitWordElement("PCS2_Inverter2_Fault_Code1", new OnOffBitItem(0, "Over_load_100_110"),
                        new OnOffBitItem(1, "Over_load_110_120"), new OnOffBitItem(2, "Over_load_120_150"),
                        new OnOffBitItem(3, "Over_load_150_200"), new OnOffBitItem(4, "Over_load_200_220"),
                        new OnOffBitItem(5, "Over_load_220_300"), new OnOffBitItem(6, "Inverter_serious_Over_Current"),
                        new OnOffBitItem(7, "Grid_Over_Current"),
                        new OnOffBitItem(8, "Repeated_Inverter_common_over_currents"),
                        new OnOffBitItem(9, "Invert_voltage_sampling_ref_error"),
                        new OnOffBitItem(10, "Grid_voltage_sampling_ref_error"),
                        new OnOffBitItem(11, "Control_Current_sampling_ref_error"),
                        new OnOffBitItem(12, "Invert_Current_sampling_ref_error"),
                        new OnOffBitItem(13, "Grid_Current_sampling_ref_error"),
                        new OnOffBitItem(14, "PDP_Protection_Power_Components_Driver_Protection"),
                        new OnOffBitItem(15, "Hardware_over_current_protection")),
                /* 109 */ new BitWordElement("PCS2_Inverter2_Fault_Code2",
                        new OnOffBitItem(0, "Hardware_Invert_Over_Voltage_Protection"),
                        new OnOffBitItem(1, "Hardware_DC_Over_Voltage_Protection"),
                        new OnOffBitItem(2, "Hardware_Over_Temperature_Protection"),
                        new OnOffBitItem(3, "No_Signal_Captured"), new OnOffBitItem(4, "DC_Over_Voltage"),
                        new OnOffBitItem(5, "Battery_disconnected"), new OnOffBitItem(6, "Invert_under_Voltage"),
                        new OnOffBitItem(7, "Invert_Over_Voltage"), new OnOffBitItem(8, "Current_Sensor_Fault"),
                        new OnOffBitItem(9, "Voltage_Sensor_Fault"), new OnOffBitItem(12, "Fan_Fault"),
                        new OnOffBitItem(13, "Phase_break"), new OnOffBitItem(14, "Invert_Relay_Fault"),
                        new OnOffBitItem(15, "Grid_Relay_Fault")),
                /* 110 */ new BitWordElement("PCS2_Inverter2_Fault_Code3",
                        new OnOffBitItem(0, "Control_PCB_Over_temperature"),
                        new OnOffBitItem(1, "Power_PCB_Over_temperature"),
                        new OnOffBitItem(2, "DC_terminal_Over_temperature"),
                        new OnOffBitItem(3, "Capacitor_Over_temperature"),
                        new OnOffBitItem(4, "Radiator_Over_temperature"),
                        new OnOffBitItem(5, "Transformer_Over_temperature"),
                        new OnOffBitItem(6, "3_phase_Combine_Communication_Fault"), new OnOffBitItem(7, "EEPROM_Fault"),
                        new OnOffBitItem(8, "Load_sampling_ref_error"),
                        new OnOffBitItem(9, "Power_on_current_limitation_fault"),
                        new OnOffBitItem(10, "3_phase_Combine_Signal_Synchronization_Mistake")),
                /* 111 */ new BitWordElement("PCS3_Inverter3_Warning_Code1", new OnOffBitItem(0, "Grid_Under_Voltage"),
                        new OnOffBitItem(1, "Grid_Over_Voltage"), new OnOffBitItem(2, "Grid_under_Frequency"),
                        new OnOffBitItem(3, "Grid_Over_Frequency"), new OnOffBitItem(4, "Grid_flicker"),
                        new OnOffBitItem(5, "Grid_Abnormal"), new OnOffBitItem(6, "DC_Under_Voltage"),
                        new OnOffBitItem(7, "Input_Over_resistance"),
                        new OnOffBitItem(8, "Combine_Jumper_Settings_Wrong"),
                        new OnOffBitItem(9, "Communication_with_Inverter_failure"),
                        new OnOffBitItem(10, "System_Time_Failure")),
                /* 112 */ new ReservedElement(112),
                /* 113 */ new BitWordElement("PCS3_Inverter3_Fault_Code1", new OnOffBitItem(0, "Over_load_100_110"),
                        new OnOffBitItem(1, "Over_load_110_120"), new OnOffBitItem(2, "Over_load_120_150"),
                        new OnOffBitItem(3, "Over_load_150_200"), new OnOffBitItem(4, "Over_load_200_220"),
                        new OnOffBitItem(5, "Over_load_220_300"), new OnOffBitItem(6, "Inverter_serious_Over_Current"),
                        new OnOffBitItem(7, "Grid_Over_Current"),
                        new OnOffBitItem(8, "Repeated_Inverter_common_over_currents"),
                        new OnOffBitItem(9, "Invert_voltage_sampling_ref_error"),
                        new OnOffBitItem(10, "Grid_voltage_sampling_ref_error"),
                        new OnOffBitItem(11, "Control_Current_sampling_ref_error"),
                        new OnOffBitItem(12, "Invert_Current_sampling_ref_error"),
                        new OnOffBitItem(13, "Grid_Current_sampling_ref_error"),
                        new OnOffBitItem(14, "PDP_Protection_Power_Components_Driver_Protection"),
                        new OnOffBitItem(15, "Hardware_over_current_protection")),
                /* 114 */ new BitWordElement("PCS3_Inverter3_Fault_Code2",
                        new OnOffBitItem(0, "Hardware_Invert_Over_Voltage_Protection"),
                        new OnOffBitItem(1, "Hardware_DC_Over_Voltage_Protection"),
                        new OnOffBitItem(2, "Hardware_Over_Temperature_Protection"),
                        new OnOffBitItem(3, "No_Signal_Captured"), new OnOffBitItem(4, "DC_Over_Voltage"),
                        new OnOffBitItem(5, "Battery_disconnected"), new OnOffBitItem(6, "Invert_under_Voltage"),
                        new OnOffBitItem(7, "Invert_Over_Voltage"), new OnOffBitItem(8, "Current_Sensor_Fault"),
                        new OnOffBitItem(9, "Voltage_Sensor_Fault"), new OnOffBitItem(12, "Fan_Fault"),
                        new OnOffBitItem(13, "Phase_break"), new OnOffBitItem(14, "Invert_Relay_Fault"),
                        new OnOffBitItem(15, "Grid_Relay_Fault")),
                /* 115 */ new BitWordElement("PCS3_Inverter3_Fault_Code3",
                        new OnOffBitItem(0, "Control_PCB_Over_temperature"),
                        new OnOffBitItem(1, "Power_PCB_Over_temperature"),
                        new OnOffBitItem(2, "DC_terminal_Over_temperature"),
                        new OnOffBitItem(3, "Capacitor_Over_temperature"),
                        new OnOffBitItem(4, "Radiator_Over_temperature"),
                        new OnOffBitItem(5, "Transformer_Over_temperature"),
                        new OnOffBitItem(6, "3_phase_Combine_Communication_Fault"), new OnOffBitItem(7, "EEPROM_Fault"),
                        new OnOffBitItem(8, "Load_sampling_ref_error"),
                        new OnOffBitItem(9, "Power_on_current_limitation_fault"),
                        new OnOffBitItem(10, "3_phase_Combine_Signal_Synchronization_Mistake")),
                /* 116-119 */ new ReservedElement(116, 119),
                /* 120 */ new DecimalWordItem("PV1_Charger1_Working_Status"),
                /*
                 * 0: Shutdown
                 * 1: Working
                 */
                /* 121 */ new DecimalWordItem("PV1_Charger1_MPPT_Status"),
                /*
                 * 0: No
                 * 1: Yes
                 */
                /* 122 */ new DecimalWordItem("PV1_Charger1_Charging_Status"),
                /*
                 * 0: No Charging
                 * 1: Fast Charging
                 * 2: Absorb Charging
                 * 3: Floating Charging
                 * 4: Re_floating Charging
                 * 5: Limited Power Charging_Battery Full)
                 * 6: Limited Power Charging_Over_temperature)
                 */
                /* 123 */
                new BitWordElement("PV1_Charger1_Warning_Code", new OnOffBitItem(0, "Input_Over_Voltage"),
                        new OnOffBitItem(1, "Input_Under_Voltage"), new OnOffBitItem(2, "PV_Under_Voltage"),
                        new OnOffBitItem(3, "Fan_Failure"), new OnOffBitItem(5, "Communication_Failure")),
                /* 124 */ new BitWordElement("PV1_Charger1_Fault_Code", new OnOffBitItem(0, "IPM_Protection"),
                        new OnOffBitItem(1, "A_circuit_Over_Current"), new OnOffBitItem(2, "B_circuit_Over_Current"),
                        new OnOffBitItem(3, "PV_Over_Voltage"), new OnOffBitItem(4, "Current_Sensor_Failure"),
                        new OnOffBitItem(5, "Radiator_Over_temperature"), new OnOffBitItem(6, "Relay_Failure"),
                        new OnOffBitItem(7, "Battery_Voltage_Sampling_Failure")),
                /* 125 */ new DecimalWordItem("PV2_Charger2_Working_Status"),
                /*
                 * 0: Shutdown
                 * 1: Working
                 */
                /* 126 */ new DecimalWordItem("PV2_Charger2_MPPT_Status"),
                /*
                 * 0: No
                 * 1: Yes
                 */
                /* 127 */ new DecimalWordItem("PV2_Charger2_Charging_Status"),
                /*
                 * 0: No Charging
                 * 1: Fast Charging
                 * 2: Absorb Charging
                 * 3: Floating Charging
                 * 4: Re_floating Charging
                 * 5: Limited Power Charging_Battery Full)
                 * 6: Limited Power Charging_Over_temperature)
                 */
                /* 128 */
                new BitWordElement("PV2_Charger2_Warning_Code", new OnOffBitItem(0, "Input_Over_Voltage"),
                        new OnOffBitItem(1, "Input_Under_Voltage"), new OnOffBitItem(2, "PV_Under_Voltage"),
                        new OnOffBitItem(3, "Fan_Failure"), new OnOffBitItem(5, "Communication_Failure")),
                /* 129 */ new BitWordElement("PV2_Charger2_Fault_Code", new OnOffBitItem(0, "IPM_Protection"),
                        new OnOffBitItem(1, "A_circuit_Over_Current"), new OnOffBitItem(2, "B_circuit_Over_Current"),
                        new OnOffBitItem(3, "PV_Over_Voltage"), new OnOffBitItem(4, "Current_Sensor_Failure"),
                        new OnOffBitItem(5, "Radiator_Over_temperature"), new OnOffBitItem(6, "Relay_Failure"),
                        new OnOffBitItem(7, "Battery_Voltage_Sampling_Failure")),
                /* 130-139 */ new ReservedElement(130, 139),
                /* 140 */ new DecimalWordItem("BSMU_Battery_Stack_Current", 0.1, 30000),
                /* 141 */ new DecimalWordItem("BSMU_Battery_Stack_Voltage", 0.1),
                /* 142 */ new DecimalWordItem("BSMU_Battery_Stack_Power", 0.1),
                /* 143 */ new PercentageWordItem("BSMU_Battery_Stack_Overall_SOC"),
                /* 144 */ new PercentageWordItem("BSMU_Battery_Stack_SOH"),
                /* 145 */ new DecimalWordItem("BSMU_Stack_Charging_Current_Limit_Value_A", 0.1),
                /* 146 */ new DecimalWordItem("BSMU_Stack_Discharging_Current_Limit_Value", 0.1),
                /* 147 */ new DecimalWordItem("BSMU_Stack_Charging_Current_Limit_Value_kW", 0.1),
                /* 148 */ new DecimalWordItem("BSMU_Stack_Discharging_Power_Limit_Value", 0.1),
                /* 149 */ new DecimalWordItem("BSMU_Remote_SCI_Station_Address"),
                /* 150 */ new DecimalWordItem("BSMU_Remote_SCI_Baud_Rate"),
                /* 151-152 */ new DecimalDoublewordItem("BSMU_Battery_Stack_Historical_Charging_Energy"),
                /* 153-154 */ new DecimalDoublewordItem("BSMU_Battery_Stack_Historical_Discharging_Energy"),
                /* 155-159 */ new ReservedElement(155, 159),
                /* 160 */ new PercentageWordItem("BSMU_Available_Group_SOC_of_Battery_Stack"),
                /* 161 */ new PercentageWordItem("BSMU_On_grid_Group_SOC_of_Battery_Stack"),
                /* 162 */ new DecimalWordItem("BSMU_Stack_Electric_Energy_that_can_be_charged"),
                /* 163 */ new DecimalWordItem("BSMU_Stack_Electric_Energy_that_can_be_discharged"),
                /* 164 */ new DecimalWordItem("BSMU_Nomianl_Electric_Energy_of_Available_Group"),
                /* 165 */ new DecimalWordItem("BSMU_Nomianl_Electric_Energy_of_On_grid_Group"),
                /* 166 */ new DecimalWordItem("BSMU_Current_Charging_Energy_kWh_of_Stack"),
                /* 167 */ new DecimalWordItem("BSMU_Current_Discharging_Energy_kWh_of_Stack"),
                /* 168-169 */ new DecimalDoublewordItem("BSMU_Continous_Power_on_Time_of_Stack_System"),
                /* 170-171 */ new DecimalDoublewordItem("BSMU_Available_Charging_Time_of_Stack"),
                /* 172-173 */ new DecimalDoublewordItem("BSMU_Available_Discharging_Time_of_Stack"),
                /* 174 */ new DecimalWordItem("BSMU_Inverter_EmergentShutdown_Message"),
                /*
                 * 0xff: Emergent Stop
                 * 0x00: Normal
                 */
                /* 175 */ new DecimalWordItem("BSMU_Modbus_Protocol_Version")));
                /* 103 Representing v001.03 */

        // wordRanges.addAll( getBECUCorrespondingInformation(0, 0) );
        // wordRanges.addAll( getBECUCorrespondingInformation(1, 200) );
        // wordRanges.addAll( getBECUCorrespondingInformation(2, 400) );
        // wordRanges.addAll( getBECUCorrespondingInformation(3, 600) );

        protocol.add(
                new ModbusElementRange(11100, /* 1100 */ new DecimalWordItem("PCS_Summary_Inverter_Networking_Mode"),
                        /*
                         * 0: DC_bus Single phase
                         * 1: AC_bus Single phase
                         * 2: DC_bus Split phase
                         * 3: AC_bus Split phase
                         * 4: DC_bus Three phase
                         * 5: AC_bus Three phase
                         */
                        /* 1101 */ new DecimalWordItem("PCS_Summary_Inverter_Active_Power_Sum", 1, 30000),
                        /* 1102 */ new DecimalWordItem("PCS_Summary_Inverter_Reactive_Power_Sum", 1, 30000),
                        /* 1103 */ new DecimalWordItem("PCS_Summary_Inverter_Apparent_Power_Sum", 1, 30000),
                        /* 1104 */ new DecimalWordItem("PCS_Summary_Inverter_Output_Current_Sum", 0.1),
                        /* 1105-06 */ new DecimalDoublewordItem("PCS_Summary_Inverter_Accumulative_Discharging_energy"),
                        /* 1107-08 */ new DecimalDoublewordItem("PCS_Summary_Inverter_Accumulative_Charging_energy"),
                        /* 1109-10 */ new DecimalDoublewordItem("PCS_Summary_Grid_Accumulative_Electricity_Sold"),
                        /* 1111-12 */ new DecimalDoublewordItem("PCS_Summary_Grid_Accumulative_Electricity_Bought"),
                        /* 1113-14 */ new DecimalDoublewordItem("PCS_Summary_Accumulative_Electricity_Used_by_load"),
                        /* 1115-16 */ new DecimalDoublewordItem("PCS_Summary_PV_Electricity_Self_consumption"),
                        /* 1117-18 */ new DecimalDoublewordItem("PCS_Summary_Battery_electricity_charged_from_grid"),
                        /* 1119-20 */ new DecimalDoublewordItem("PCS_Summary_Photovoltaic_Inverter_Electricity_Sold"),
                        /* 1121-29 */ new ReservedElement(1121, 1129),
                        /* 1130 */ new DecimalWordItem("PCS1_Inverter1_Operation_Status"),
                        /*
                         * 0: Not Working
                         * 1: Off_grid Matching Load
                         * 2: On_grid Full power Charging
                         * 3: On_grid Full power Discharging
                         * 4: Grid_tied Matching Load Charging
                         * 5：Grid_tied Matching Load Discharging
                         * 6: Grid_tied Limited Power Discharging_Over_frequency)
                         * 7: Grid_tied Limited Power Charging_Over_temperature)
                         * 8: Grid_tied Limited Power Discharging_Over_temperature)
                         * 9: Grid_tied Limited Power Charging_Battery Full)
                         * 10: Grid_tied Limited Power Discharging_Battery Empty)
                         * 11: UPS Emergent Power Supply( Inverter not Working and Grid Connecting with Load)
                         */
                        /* 1131 */ new DecimalWordItem("PCS1_Inverter1_Output_Current", 0.1),
                        /* 1132 */ new DecimalWordItem("PCS1_Inverter1_Output_Voltage", 0.1),
                        /* 1133 */ new DecimalWordItem("PCS1_Inverter1_Active_Power", 1, 10000),
                        /* 1134 */ new DecimalWordItem("PCS1_Inverter1_Reactive_Power", 1, 10000),
                        /* 1135 */ new DecimalWordItem("PCS1_Inverter1_Apparent_Power", 1, 10000),
                        /* 1136 */ new DecimalWordItem("PCS1_Grid_Phase1_Current", 0.1),
                        /* 1137 */ new DecimalWordItem("PCS1_Grid_Phase1_Voltage", 0.1),
                        /* 1138 */ new DecimalWordItem("PCS1_Grid_Phase1_Active_Power", 1, 10000),
                        /* 1139 */ new DecimalWordItem("PCS1_Grid_Phase1_Reactive_Power", 1, 10000),
                        /* 1140 */ new DecimalWordItem("PCS1_Grid_Phase1_Apparent_Power", 1, 10000),
                        /* 1141 */ new DecimalWordItem("PCS1_Phase1_Load_Active_Power", 1, 10000),
                        /* 1142 */ new DecimalWordItem("PCS1_Phase1_Load_Reactive_Power", 1, 10000),
                        /* 1143 */ new DecimalWordItem("PCS1_Phase1_Load_Apparent_Power", 1, 10000),
                        /* 1144 */ new DecimalWordItem("PCS1_Phase1_PV_Inverter_Active_Power", 1, 10000),
                        /* 1145-59 */ new ReservedElement(1145, 1159),
                        /* 1160 */ new DecimalWordItem("PCS2_Inverter2_Operation_Status"),
                        /*
                         * 0: Not Working
                         * 1: Off_grid Matching Load
                         * 2: On_grid Full power Charging
                         * 3: On_grid Full power Discharging
                         * 4: Grid_tied Matching Load Charging
                         * 5： Grid_tied Matching Load Discharging
                         * 6: Grid_tied Limited Power Discharging_Over_frequency)
                         * 7: Grid_tied Limited Power Charging_Over_temperature)
                         * 8: Grid_tied Limited Power Discharging_Over_temperature)
                         * 9: Grid_tied Limited Power Charging _Battery Full)
                         * 10: Grid_tied Limited Power Discharging_Battery Empty)
                         * 11: UPS Emergent Power Supply( Inverter not Working and Grid Connecting with Load)
                         */
                        /* 1161 */ new DecimalWordItem("PCS2_Inverter2_Output_Current", 0.1),
                        /* 1162 */ new DecimalWordItem("PCS2_Inverter2_Output_Voltage", 0.1),
                        /* 1163 */ new DecimalWordItem("PCS2_Inverter2_Active_Power", 1, 10000),
                        /* 1164 */ new DecimalWordItem("PCS2_Inverter2_Reactive_Power", 1, 10000),
                        /* 1165 */ new DecimalWordItem("PCS2_Inverter2_Apparent_Power", 1, 10000),
                        /* 1166 */ new DecimalWordItem("PCS2_Grid_Phase2_Current_B", 0.1),
                        /* 1167 */ new DecimalWordItem("PCS2_Grid_Phase2_Voltage_B", 0.1),
                        /* 1168 */ new DecimalWordItem("PCS2_Grid_Phase2_Active_Power", 1, 10000),
                        /* 1169 */ new DecimalWordItem("PCS2_Grid_Phase2_Reactive_Power", 1, 10000),
                        /* 1170 */ new DecimalWordItem("PCS2_Grid_Phase2_Apparent_Power", 1, 10000),
                        /* 1171 */ new DecimalWordItem("PCS2_Phase2_Load_Active_Power", 1, 10000),
                        /* 1172 */ new DecimalWordItem("PCS2_Phase2_Load_Reactive_Power", 1, 10000),
                        /* 1173 */ new DecimalWordItem("PCS2_Phase2_Load_Apparent_Power", 1, 10000),
                        /* 1174 */ new DecimalWordItem("PCS2_Phase2_PV_Inverter_Active_Power", 1, 10000),
                        /* 1175-89 */ new ReservedElement(1175, 1189),
                        /* 1190 */ new DecimalWordItem("PCS3_Inverter3_Operation_Status"),
                        /*
                         * 0: Not Working
                         * 1: Off_grid Matching Load
                         * 2: On_grid Full power Charging
                         * 3: On_grid Full power Discharging
                         * 4: Grid_tied Matching Load Charging
                         * 5： Grid_tied Matching Load Discharging
                         * 6: Grid_tied Limited Power Discharging_Over_frequency)
                         * 7: Grid_tied Limited Power Charging_Over_temperature)
                         * 8: Grid_tied Limited Power Discharging_Over_temperature)
                         * 9: Grid_tied Limited Power Charging _Battery Full)
                         * 10: Grid_tied Limited Power Discharging_Battery Empty)
                         * 11: UPS Emergent Power Supply( Inverter not Working and Grid Connecting with Load)
                         */
                        /* 1191 */ new DecimalWordItem("PCS3_Inverter3_Output_Current", 0.1),
                        /* 1192 */ new DecimalWordItem("PCS3_Inverter3_Output_Voltage", 0.1),
                        /* 1193 */ new DecimalWordItem("PCS3_Inverter3_Active_Power", 1, 10000),
                        /* 1194 */ new DecimalWordItem("PCS3_Inverter3_Reactive_Power", 1, 10000),
                        /* 1195 */ new DecimalWordItem("PCS3_Inverter3_Apparent_Power", 1, 10000),
                        /* 1196 */ new DecimalWordItem("PCS3_Grid_Phase3_Current", 0.1),
                        /* 1197 */ new DecimalWordItem("PCS3_Grid_Phase3_Voltage", 0.1),
                        /* 1198 */ new DecimalWordItem("PCS3_Grid_Phase3_Active_Power", 1, 10000),
                        /* 1199 */ new DecimalWordItem("PCS3_Grid_Phase3_Reactive_Power", 1, 10000),
                        /* 1200 */ new DecimalWordItem("PCS3_Grid_Phase3_Apparent_Power", 1, 10000),
                        /* 1201 */ new DecimalWordItem("PCS3_Phase3_Load_Active_Power", 1, 10000),
                        /* 1202 */ new DecimalWordItem("PCS3_Phase3_Load_Reactive_Power", 1, 10000),
                        /* 1203 */ new DecimalWordItem("PCS3_Phase3_Load_Apparent_Power", 1, 10000),
                        /* 1204 */ new DecimalWordItem("PCS3_Phase3_PV_Inverter_Active_Power", 1, 10000)));

        protocol.add(new ModbusElementRange(11220, /* 1220 */ new DecimalWordItem("PV_Summary_Number_of_Charger"),
                /* 1221 */ new DecimalWordItem("PV_Summary_Charger_Total_Output_Current", 0.1),
                /* 1222 */ new DecimalWordItem("PV_Summary_Charger_Total_Output_Power"),
                /* 1223-24 */ new DecimalDoublewordItem("PV_Summary_Charger_Cumulative_Output"),
                /* 1225-29 */ new ReservedElement(1225, 1229),
                /* 1230 */ new DecimalWordItem("PV1_Charger1_Output_Voltage", 0.1),
                /* 1231 */ new DecimalWordItem("PV1_Charger1_Output_Current", 0.1),
                /* 1232 */ new DecimalWordItem("PV1_Charger1_Output_Power"),
                /* 1233 */ new DecimalWordItem("PV1_Charger1_Output_Voltage2", 0.1),
                /* 1234 */ new DecimalWordItem("PV1_Charger1_Radiator_Temperature"),
                /* 1235-36 */ new DecimalDoublewordItem("PV1_Charger1_Cumulative_Output"),
                /* 1237-39 */ new ReservedElement(1237, 1239),
                /* 1240 */ new DecimalWordItem("PV2_Charger2_Output_Voltage", 0.1),
                /* 1241 */ new DecimalWordItem("PV2_Charger2_Output_Current", 0.1),
                /* 1242 */ new DecimalWordItem("PV2_Charger2_Output_Power"),
                /* 1243 */ new DecimalWordItem("PV2_Charger2_Output_Voltage2", 0.1),
                /* 1244 */ new DecimalWordItem("PV2_Charger2_Radiator_Temperature"),
                /* 1245-46 */ new DecimalDoublewordItem("PV2_Charger2_Cumulative_Output")));

        // generateItemsFile(protocol);

        return protocol;
    }

    // TODO: use BECU information
    @SuppressWarnings("unused")
    private static ArrayList<ModbusElementRange> getBECUCorrespondingInformation(int batteryGroup, int offset) {
        ArrayList<ModbusElementRange> protocol = new ArrayList<ModbusElementRange>();
        protocol.add(new ModbusElementRange(10180 + offset,
                new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Battery_Group" + batteryGroup, 0.1),
                new DecimalWordItem("BECU" + batteryGroup + "_Current_of_Battery_Group" + batteryGroup, 0.1, 5000),
                new DecimalWordItem("BECU" + batteryGroup + "_Current_SOC_of_Battery_Group" + batteryGroup, 0.01),
                /* ItemPercentage does not allow double values */
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_Overall_Sampling_Voltage_of_of_Battery_Group" + batteryGroup, 0.1),
                new PercentageWordItem("BECU" + batteryGroup + "_SOH_of_Battery_Group" + batteryGroup),
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_Battery_Average_Temperature_of_Battery_Group" + batteryGroup, 1, 40),
                new DecimalWordItem("BECU" + batteryGroup + "_Environmental_Temperature_of_BatteryGroup" + batteryGroup,
                        1, 40),
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_Charging_Current_Limit_Value_of_Battery_Group" + batteryGroup, 0.1),
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_Discharging_Current_Limit_Value_of_Battery_Group" + batteryGroup,
                        0.1),
                new DecimalDoublewordItem("BECU" + batteryGroup + "_Remanet_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Charing_discharging_Times_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem("BECU" + batteryGroup + "_Overall_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem("BECU" + batteryGroup + "_Overall_capacity_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Single_Charging_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Single_Discharging_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Single_Charging_capacity_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Single_Discharging_capacity_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Historical_Charging_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Historical_Discharging_Energy_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Historical_Charging_capacity_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem(
                        "BECU" + batteryGroup + "_Historical_Discharging_capacity_of_Battery_Group" + batteryGroup),
                new DecimalDoublewordItem("BECU" + batteryGroup + "_Actual_Capacity_of_Battery_Group" + batteryGroup),
                new PercentageWordItem("BECU" + batteryGroup + "_Enviroment_Humidity_of_Battery_Group" + batteryGroup),
                new PercentageWordItem("BECU" + batteryGroup + "_SOC_Actual_SOC_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Max_Voltage_Cell_No_of_Battery_Group" + batteryGroup),
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_The_Voltage_of_the_Max_Voltage_cell_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_The_temperature_of_the_Max_Voltage_Cell_of_Battery_Group"
                        + batteryGroup, 1, 40),
                new DecimalWordItem("BECU" + batteryGroup + "_Min_Voltage_Cell_No_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Min_Voltage_of_Min_Voltage_cell_of_Battery_Group"),
                new DecimalWordItem("BECU" + batteryGroup
                        + "_The_temperature_of_the_Min_Voltage_Battery_of_Battery_Group" + batteryGroup, 1, 40),
                new DecimalWordItem("BECU" + batteryGroup + "_Max_Temperature_cell_No_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_The_temperature_of_Max_Temperature_Cell_of_Battery_Group"
                        + batteryGroup, 1, 40),
                new DecimalWordItem(
                        "BECU" + batteryGroup + "_The_voltage_of_Max_Temperature_Cell_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Min_Temperature_cell_No_of_Battery_Group" + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_The_Temperature_of_Min_Temperature_cell_of_Battery_Group"
                        + batteryGroup, 1, 40),
                new DecimalWordItem("BECU" + batteryGroup + "_The_voltage_of_the_Min_Temperature_cell_of_Battery_Group"
                        + batteryGroup)));
        protocol.add(
                new ModbusElementRange(10230 + offset, new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell0"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell1"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell2"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell3"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell4"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell5"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell6"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell7"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell8"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell9"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell10"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell11"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell12"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell13"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell14"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell15"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell16"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell17"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell18"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell19"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell20"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell21"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell22"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell23"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell24"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell25"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell26"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell27"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell28"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Voltage_of_Cell29"),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell0", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell1", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell2", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell3", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell4", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell5", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell6", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell7", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell8", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell9", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell10", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell11", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell12", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell13", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell14", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell15", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell16", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell17", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell18", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell19", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell20", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell21", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell22", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell23", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell24", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell25", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell26", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell27", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell28", 1, 40),
                        new DecimalWordItem("BECU" + batteryGroup + "_Temperature_of_Cell29", 1, 40)));
        protocol.add(new ModbusElementRange(10290 + offset,
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell0_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell1_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell2_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell3_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell4_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell5_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell6_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell7_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell8_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell9_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell10_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell11_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell12_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell13_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell14_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell15_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell16_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell17_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell18_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell19_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell20_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell21_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell22_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell23_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell24_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell25_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell26_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell27_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell28_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Charging_Equilibrium_Status_of_Cell29_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell0_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell1_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell2_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell3_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell4_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell5_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell6_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell7_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell8_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell9_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell10_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell11_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell12_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell13_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell14_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell15_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell16_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell17_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell18_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell19_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell20_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell21_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell22_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell23_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell24_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell25_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell26_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell27_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell28_in_Battery_Group"
                        + batteryGroup),
                new DecimalWordItem("BECU" + batteryGroup + "_Discharging_Equilibrium_Status_of_Cell29_in_Battery_Group"
                        + batteryGroup)));
        /*
         * 0: Not Equilibrium
         * 1: Charging Equilibrium
         */

        return protocol;
    }
}
