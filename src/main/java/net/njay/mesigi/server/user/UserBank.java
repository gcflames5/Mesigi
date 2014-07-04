package net.njay.mesigi.server.user;

import net.njay.mesigi.client.user.User;

import java.util.HashMap;

/**
 * Created by Nick on 7/4/14.
 */
public class UserBank {

    private HashMap<User, String> userBank; //<User, SessionId>

    public UserBank(){
        userBank = new HashMap<User, String>();
    }

    public HashMap<User, String> getMap(){ return this.userBank; }


}
