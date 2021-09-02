package com.cscie97.smartcity.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Credential Class
 * This class is responsible for storing and verifying credentials. Users are associated with 0 or more
 * Credentials and those Credentials are used to log users in. This class also hashes the credentials for
 * security and does the validation for possible String matches to the stored hashed credential.
 */
public class Credential implements AuthServiceVisitorElement {
    private String id;
    private String credential;
    private CredentialType type;

    /**
     *
     * @param id String a unique ID not yet used be any other Credentials
     * @param credential String a non hashed credential string
     * @param type CredentialType the type of Credential being stored
     */
    public Credential(String id, String credential, CredentialType type) {
        this.id = id;
        this.credential = generateSha256HashFromString(credential); // hash credential for storage
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Verify a provided credential string against the stored hashed credential
     * @param credentialString A non hashed possible match to the stored hashed credential
     * @return Boolean true if theres a match, false if not
     */
    public Boolean verifyCredentialString(String credentialString) {

        // hash the credentialString and see if the hash matches the stored hash
        return this.credential.equals(generateSha256HashFromString(credentialString));
    }

    /**
     * Utility method for creating a SHA256 hash, used from my Assignment 1
     * @param sample String to be hashed
     * @return String hashed string
     */
    private static String generateSha256HashFromString(String sample) {

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

    // standard visitor pattern acceptor
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitCredential(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "Credential {" +
                "id='" + id + '\'' +
                ", credential='" + credential + '\'' +
                ", type=" + type +
                '}';
    }
}
