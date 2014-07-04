package net.njay.mesigi.server;

import net.njay.customevents.event.Event;
import net.njay.mesigi.server.listener.AuthenticationListener;
import net.njay.serverinterconnect.connection.TcpConnection;
import net.njay.serverinterconnect.server.TcpServerManager;

import javax.net.ssl.SSLServerSocket;

/**
 * Created by Nick on 7/4/14.
 */
public class AuthenticatedTcpServerManager extends TcpServerManager {

    private Server server;

    public AuthenticatedTcpServerManager(Server server, SSLServerSocket serversocket) {
        super(serversocket);
        this.server = server;
    }

    @Override
    public void submitConnection(TcpConnection connection){
        super.submitConnection(connection);
        Event.addListener(new AuthenticationListener(server, connection));
    }

}
