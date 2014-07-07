package net.njay.mesigi.client.auth;

import net.njay.mesigi.util.secret.SecretGenerator;

/**
 * Created by Nick on 7/4/14.
 */
public class Credentials {

    private String username, password, secretKey;

    public Credentials(String username, String password){
        this.username = username;
        this.password = password;
        this.secretKey = SecretGenerator.generateSecret();
    }

    public String getUsername(){ return this.username; }
    public String getPassword(){ return this.password; }
    public String getSecretKey(){ return this.secretKey; }

}
