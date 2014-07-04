package net.njay.mesigi.client.listener;

import net.njay.customevents.event.Event;
import net.njay.customevents.event.EventHandler;
import net.njay.customevents.event.Listener;
import net.njay.mesigi.client.Client;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.packet.auth.AuthenticationRequestPacket;
import net.njay.serverinterconnect.event.PacketRecievedEvent;

/**
 * Created by Nick on 7/4/14.
 */
public class AuthenticationListener implements Listener {

    private Client client;

    public AuthenticationListener(Client client){
        this.client = client;
    }

    @EventHandler
    public void onPacketRecieve(PacketRecievedEvent e){
        if (!(e.getPacket() instanceof AuthenticationRequestPacket)) return;
        AuthenticationRequestPacket arPacket = (AuthenticationRequestPacket) e.getPacket();
        if (arPacket.getSecret().equals(client.getCredentials().getSecretKey())){
            AuthenticationPacket authPacket = new AuthenticationPacket(client.getCredentials().getUsername(), client.getCredentials().getUuid());
            client.getTcpManager().getConnection().sendPacket(authPacket);
            client.setInitialized(true);
        }else{
            //Possible malicious sender trying to obtain credentials from user
            e.getConnection().terminate();
        }
        Event.removeListener(this);
    }

}
