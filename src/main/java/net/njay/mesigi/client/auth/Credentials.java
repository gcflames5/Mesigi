package net.njay.mesigi.client.auth;

/**
 * Created by Nick on 7/4/14.
 */
public class Credentials {

    private String username, password, uuid, secretKey;

    public Credentials(String username, String password, String uuid, String secretKey){
        this.username = username;
        this.password = password;
        this.uuid = uuid;
    }

    public String getUsername(){ return this.username; }
    public String getPassword(){ return this.password; }
    public String getUuid(){ return this.uuid; }
    public String getSecretKey(){ return this.secretKey; }

}
