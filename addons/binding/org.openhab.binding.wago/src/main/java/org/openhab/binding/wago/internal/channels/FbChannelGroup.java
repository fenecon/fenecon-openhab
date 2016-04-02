package org.openhab.binding.wago.internal.channels;

import java.io.IOException;
import java.util.TreeMap;

import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

public abstract class FbChannelGroup {
    public abstract int getStartAdress();

    protected abstract FbChannel[] getChannels();

    /**
     * Create a modbus request for all the channels in the group; null if the group is empty
     *
     * @return a modbus request
     */
    public abstract ModbusRequest getModbusRequest();

    public TreeMap<String, State> handleModbusResponse(ModbusResponse res) throws IOException {
        throw new IOException("Undefined Modbus response: " + res.toString());
    }

    /**
     * Send a command to a channel
     *
     * @param tcpConnection
     * @param id
     * @param command
     * @return true if channel was found in this group and the command was successful
     * @throws ModbusIOException
     * @throws ModbusSlaveException
     * @throws ModbusException
     */
    public abstract boolean handleCommand(TCPMasterConnection tcpConnection, String id, Command command)
            throws ModbusIOException, ModbusSlaveException, ModbusException;
}
