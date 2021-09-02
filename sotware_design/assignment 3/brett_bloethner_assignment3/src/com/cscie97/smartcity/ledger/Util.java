package com.cscie97.smartcity.ledger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    /**
     * Generate a SHA-256 string hash of a passed in string sample
     * CITATION: https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java
     * @param sample String a sample from which the hash will be created
     * @return String A unique hash derived from the sample
     */
    public static String generateSha256HashFromString(String sample) {

        // create the hash in byte array form
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(sample.getBytes(StandardCharsets.UTF_8));

        // convert the byte array to a hex string
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
