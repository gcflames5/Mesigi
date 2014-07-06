package net.njay.mesigi.util.upnp;

import net.njay.mesigi.util.upnp.exception.CleanPortNotFoundException;
import net.njay.mesigi.util.upnp.exception.GatewayNotFoundException;

public class Upnp {

    public static int forward(int start, int end) throws GatewayNotFoundException, CleanPortNotFoundException {
        return new UpnpConnector(start, end).forward();
    }
}
