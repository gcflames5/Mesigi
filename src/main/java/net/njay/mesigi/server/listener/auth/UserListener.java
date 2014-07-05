package net.njay.mesigi.server.listener.auth;

import net.njay.mesigi.event.user.UserConnectEvent;
import net.njay.mesigi.event.user.UserDisconnectEvent;
import net.njay.mesigi.server.Server;
import org.bukkit.event.EventHandler;

/**
 * Created by Nick on 7/4/14.
 */
public class UserListener {

    private Server server;

    public UserListener(Server server){
        this.server = server;
    }

    @EventHandler
    public void onUserJoin(UserConnectEvent e){
        server.getUserBank().getMap().put(e.getUser(), e.getSessionId());
    }

    @EventHandler
    public void onUserLeave(UserDisconnectEvent e){
        server.getUserBank().getMap().remove(e.getUser());
    }

}
