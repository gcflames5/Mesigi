package net.njay.mesigi.util.upnp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import net.njay.mesigi.util.log.LogLevel;
import net.njay.mesigi.util.log.ReflectionLogger;
import net.njay.mesigi.util.upnp.exception.CleanPortNotFoundException;
import net.njay.mesigi.util.upnp.exception.GatewayNotFoundException;
import org.xml.sax.SAXException;

public class UpnpConnector {

    private int[] ports;

    public UpnpConnector(int[] ports){
        this.ports = ports;
    }

    public UpnpConnector(int portMin, int portMax){
        ports = new int[portMax - --portMin];
        ReflectionLogger.log(LogLevel.INFO, portMax + " " + portMin + " " + (portMax---portMin) + " " + ports.length);
        for (int i = portMin, index = 0; i <= portMax; i++, index++)
            ports[index] = i;
    }

    public int forward() throws GatewayNotFoundException, CleanPortNotFoundException {
        for (int port : this.ports){
            try{
                if (scan(port))
                    return port;
            }catch(GatewayNotFoundException e){ throw e; }
            catch(Exception e1){ continue; }
        }
        throw new CleanPortNotFoundException("No open ports were found between " + ports[0] + " and " + ports[ports.length-1] + "!");
    }

    private boolean scan(int port) throws GatewayNotFoundException, SocketException, UnknownHostException, IOException, SAXException, ParserConfigurationException{
        UpnpManager upnp = new UpnpManager(port);
        upnp.discover();
        return upnp.map();
    }

}