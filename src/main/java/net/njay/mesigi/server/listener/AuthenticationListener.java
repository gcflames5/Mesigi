package net.njay.mesigi.server.listener;

import net.njay.customevents.event.Event;
import net.njay.customevents.event.EventHandler;
import net.njay.customevents.event.Listener;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.mesigi.event.user.UserConnectEvent;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.packet.auth.AuthenticationRequestPacket;
import net.njay.mesigi.packet.auth.AuthenticationSecretPacket;
import net.njay.mesigi.server.Server;
import net.njay.mesigi.util.dbconn.DatabaseConnector;
import net.njay.serverinterconnect.connection.TcpConnection;
import net.njay.serverinterconnect.event.PacketRecievedEvent;

/**
 * Created by Nick on 7/4/14.
 */
public class AuthenticationListener implements Listener {

    private Server server;
    private TcpConnection conn;


    public AuthenticationListener(Server server, TcpConnection conn){
        this.server = server;
        this.conn = conn;
    }

    @EventHandler
    public void onPacketRecieve(PacketRecievedEvent e){
        if (conn != e.getConnection()) return;
        if (e.getPacket() instanceof AuthenticationSecretPacket){
            AuthenticationSecretPacket secretPacket = (AuthenticationSecretPacket) e.getPacket();
            AuthenticationRequestPacket requestPacket = new AuthenticationRequestPacket(secretPacket.getSecret());
            e.getConnection().sendPacket(requestPacket);
        }else if (e.getPacket() instanceof AuthenticationPacket){
            AuthenticationPacket authenticationPacket = (AuthenticationPacket) e.getPacket();
            if (DatabaseConnector.isValid(authenticationPacket)){
                Event.callEvent(new UserConnectEvent(authenticationPacket.getSessionId(), new User(authenticationPacket.getUsername(), UserStatus.ONLINE)));
            }else{
                server.terminateConnection(conn);
            }
            Event.removeListener(this);
        }else{
            server.terminateConnection(conn);
            Event.removeListener(this);
        }
    }

}
