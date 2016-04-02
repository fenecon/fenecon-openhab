package org.openhab.binding.wago.internal.modules;

import org.openhab.binding.wago.internal.channels.FbChannel;
import org.openhab.binding.wago.internal.channels.FbInputCoilChannel;
import org.openhab.binding.wago.internal.channels.FbOutputCoilChannel;

/**
 * WAGO I/O 750-523 1-channel relay output module
 *
 * @author Stefan Feilmeier
 *
 */
public class Fb523RO1Ch extends FbModule {
    private final static String name = "RelayOutput_";

    public Fb523RO1Ch(int count) {
        super(new FbChannel[] { new FbOutputCoilChannel(name + count), // control digital output signal
                new FbOutputCoilChannel(name + count + "_Ch2"), // unused
                new FbInputCoilChannel(name + count + "_Hand"), // status "Hand-Control"
                new FbInputCoilChannel(name + count + "_Ch4") }); // unused

    }

    @Override
    public String getName() {
        return "WAGO I/O 750-523 1-channel relay output module";
    }
}
