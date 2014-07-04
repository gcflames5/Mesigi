package net.njay.mesigi.client.user;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.serverinterconnect.packet.Packet;
import net.njay.serverinterconnect.packet.Transferable;

public class User extends Transferable{

    private String displayName;
    private UserStatus status;

    public User(){}

    public User(String displayName, UserStatus status){
        this.displayName = displayName;
        this.status = status;
    }

    public String getDisplayName(){ return this.displayName; }
    public UserStatus getStatus(){ return this.status; }

    public void writeToStream(DataOutputStream output) throws IOException{
        Packet.writeString(displayName, output);
        Packet.writeString(status.name(), output);
    }

    public void readFromStream(DataInputStream input) throws IOException{
        displayName = Packet.readString(input);
        status = UserStatus.valueOf(Packet.readString(input));
    }

}
