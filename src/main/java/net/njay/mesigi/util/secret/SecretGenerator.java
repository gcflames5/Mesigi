package net.njay.mesigi.util.secret;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SecretGenerator {

    private static SecureRandom random = new SecureRandom();

    public static String generateSecret() {
        return new BigInteger(500, random).toString(32);
    }

}
