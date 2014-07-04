package net.njay.mesigi.packet.user;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.njay.mesigi.client.user.User;
import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.serverinterconnect.packet.Packet;

public class UserStateChangePacket extends Packet{

    private User user;
    private UserStatus oldState;

    public UserStateChangePacket() {}

    public UserStateChangePacket(User user, UserStatus oldState) {
        this.user = user;
        this.oldState = oldState;
    }

    @Override
    public void readPacketContent(DataInputStream input) throws IOException {
        user = new User();
        user.readFromStream(input);
        oldState = UserStatus.valueOf(Packet.readString(input));
    }

    @Override
    public void writePacketContent(DataOutputStream output) throws IOException {
        user.writeToStream(output);
        Packet.writeString(oldState.name(), output);
    }
}
