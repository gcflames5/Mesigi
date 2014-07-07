package net.njay.mesigi.util.dbconn;

import net.njay.mesigi.client.auth.Credentials;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.util.log.LogLevel;
import net.njay.mesigi.util.log.ReflectionLogger;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Nick on 7/4/14.
 */
public class DatabaseConnector {

    public static final String AUTH_SERVER_ADDRESS = "http://localhost/mesigi/";

    public static boolean validate(AuthenticationPacket packet) throws IOException{
        URL url = new URL(AUTH_SERVER_ADDRESS + "session.php?user=" + packet.getUsername() +
                "&sessionid=" + packet.getSessionId());
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        return Boolean.valueOf(body);
    }

    public static String validate(Credentials credentials) throws IOException {
        URL url = new URL(AUTH_SERVER_ADDRESS + "authCheck.php?user=" + credentials.getUsername() +
            "&pass=" + credentials.getPassword());
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        if (body.contains("ERROR:"))
            return null;
        return body;
    }

}
