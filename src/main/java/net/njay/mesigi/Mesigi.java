package net.njay.mesigi;

import net.njay.customevents.event.Event;
import net.njay.customevents.event.Listener;
import net.njay.mesigi.client.Client;
import net.njay.mesigi.client.auth.Credentials;
import net.njay.mesigi.client.message.Message;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.packet.auth.AuthenticationRequestPacket;
import net.njay.mesigi.packet.auth.AuthenticationSecretPacket;
import net.njay.mesigi.packet.conversation.ConversationCreatePacket;
import net.njay.mesigi.packet.message.MessageSendPacket;
import net.njay.mesigi.packet.user.UserStateChangePacket;
import net.njay.mesigi.server.Server;
import net.njay.mesigi.util.cmd.CmdUtil;
import net.njay.mesigi.util.log.LogLevel;
import net.njay.mesigi.util.log.ReflectionLogger;
import net.njay.serverinterconnect.packet.Packet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nick on 7/3/14.
 */
public class Mesigi implements Listener {

    public static void main(String args[]) {
        try{
            Event.addListener(new Mesigi());
            registerPackets();
            ReflectionLogger.filter(LogLevel.SEVERE, ReflectionLogger.FilterMode.WHITELIST, Mesigi.class);
            String mode = CmdUtil.getStringAttribute(args, "-m", "-mode");
            if (mode.contains("s"))
                startServer(args);
            else if (mode.contains("c"))
                startClient(args);
            else
                System.out.println("Unknown mode: " + mode);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void startClient(String args[]) throws IOException {
        Credentials cred = new Credentials("username", "password", "uuid");
        final Client client = new Client(new User("test", UserStatus.ONLINE), cred,
                "127.0.0.1", CmdUtil.getIntAttribute(args, "-port"));
        client.initialize();
        client.sendPacket(new MessageSendPacket(
           new Message("", new User("test", UserStatus.ONLINE), "", new ArrayList<User>())));
    }

    public static void startServer(String args[]){
        Server server = new Server(CmdUtil.getIntAttribute(args, "-p", "-port"));
    }

    public static void registerPackets()  {
        Packet.registerPacket(MessageSendPacket.class);
        Packet.registerPacket(AuthenticationPacket.class);
        Packet.registerPacket(AuthenticationRequestPacket.class);
        Packet.registerPacket(AuthenticationSecretPacket.class);
        Packet.registerPacket(ConversationCreatePacket.class);
        Packet.registerPacket(UserStateChangePacket.class);
    }
}
