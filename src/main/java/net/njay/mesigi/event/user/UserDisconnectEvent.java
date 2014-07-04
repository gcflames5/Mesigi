package net.njay.mesigi.event.user;

import net.njay.customevents.event.Event;
import net.njay.mesigi.client.user.User;

/**
 * Created by Nick on 7/4/14.
 */
public class UserDisconnectEvent extends Event {

    private String sessionId;
    private User user;

    public UserDisconnectEvent(String sessionId, User user){
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId(){ return this.sessionId; }
    public User getUser(){ return this.user; }

}
