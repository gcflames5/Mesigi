package net.njay.mesigi.util.upnp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import net.njay.mesigi.util.upnp.exception.GatewayNotFoundException;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

public class UpnpManager {

    private static List<UpnpManager> upnpManagerList = new ArrayList<UpnpManager>();

    private int port;
    private GatewayDevice d;

    public UpnpManager(int port){
        this.port = port;
        upnpManagerList.add(this);
    }

    public GatewayDevice discover() throws SocketException, UnknownHostException, IOException, SAXException, ParserConfigurationException{
        GatewayDiscover discover = new GatewayDiscover();
        discover.discover();
        this.d = discover.getValidGateway();
        return this.d;
    }

    public boolean map() throws IOException, SAXException, GatewayNotFoundException {
        if (d == null) throw new GatewayNotFoundException("No valid gateway found");
        InetAddress localAddress = d.getLocalAddress();
        String externalIPAddress = d.getExternalIPAddress();
        PortMappingEntry portMapping = new PortMappingEntry();

        if (!d.getSpecificPortMappingEntry(port,"TCP",portMapping)) {
            if (d.addPortMapping(port,port,localAddress.getHostAddress(),"TCP","test"))
                return true;
            else
                return false;
        } else {
            d.deletePortMapping(port, "TCP");
            return map();
        }
    }

    public void remove() throws IOException, SAXException{
        d.deletePortMapping(port, "TCP");
    }

    public static synchronized void cleanup(){
        for (UpnpManager manager: upnpManagerList) try{ manager.remove(); } catch (Exception e){ continue; }
    }

}
