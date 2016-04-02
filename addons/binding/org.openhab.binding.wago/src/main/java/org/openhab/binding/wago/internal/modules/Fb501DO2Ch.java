package org.openhab.binding.wago.internal.modules;

import org.openhab.binding.wago.internal.channels.FbChannel;
import org.openhab.binding.wago.internal.channels.FbOutputCoilChannel;

/**
 * WAGO I/O 750-501 2-channel digital output module
 *
 * @author Stefan Feilmeier
 *
 */
public class Fb501DO2Ch extends FbModule {
    private final static String name = "DigitalOutput_";

    public Fb501DO2Ch(int count) {
        super(new FbChannel[] { new FbOutputCoilChannel(name + count + "_1"),
                new FbOutputCoilChannel(name + count + "_2") });

    }

    @Override
    public String getName() {
        return "WAGO I/O 750-501 2-channel digital output module";
    }
}
