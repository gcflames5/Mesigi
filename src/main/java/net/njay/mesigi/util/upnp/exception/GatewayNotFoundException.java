package net.njay.mesigi.util.upnp.exception;

/**
 * Created by Nick on 7/5/14.
 */
public class GatewayNotFoundException extends Exception{

    public GatewayNotFoundException(){
        super();
    }

    public GatewayNotFoundException(String message){
        super(message);
    }
}
