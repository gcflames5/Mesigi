package net.njay.mesigi.server.listener;

import net.njay.customevents.event.Event;
import net.njay.customevents.event.EventHandler;
import net.njay.customevents.event.Listener;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.mesigi.event.user.UserConnectEvent;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.server.Server;
import net.njay.mesigi.util.dbconn.DatabaseConnector;
import net.njay.serverinterconnect.event.PacketRecievedEvent;

/**
 * Created by Nick on 7/4/14.
 */
public class AuthenticationListener implements Listener {

    private Server server;

    public AuthenticationListener(Server server){
        this.server = server;
    }

    @EventHandler
    public void onPacketRecieve(PacketRecievedEvent e){
        if (!(e.getPacket() instanceof AuthenticationPacket)) return;
        AuthenticationPacket authenticationPacket = (AuthenticationPacket) e.getPacket();
        if (DatabaseConnector.isValid(authenticationPacket)){
            Event.callEvent(new UserConnectEvent(authenticationPacket.getSessionId(), new User(authenticationPacket.getUsername(), UserStatus.ONLINE)));
        }else{
            server.getTcpManager().getConnections().remove(e.getConnection());
            e.getConnection().terminate();
        }
    }

}
