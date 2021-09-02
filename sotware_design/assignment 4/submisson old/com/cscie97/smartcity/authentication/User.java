package com.cscie97.smartcity.authentication;

import java.util.HashMap;

/**
 * User Class
 * The User class represents a User in the Smart City Authentication Service. A Users ID matches the Users ID
 * in the Smart City Model Service and once Users are created in the Model Service , they're also created in
 * the Authentication Service. Users include a map of Credentials that allows them to authenticate under multiple
 * various authentication methods as well as a map of entitlements that allow evaluations of the Users individual
 * Roles and Permissions.
 */
public class User implements AuthServiceVisitorElement {
    private String id;
    private String name;
    private HashMap<String, Credential> credentials;
    private HashMap<String, Entitlement> entitlements;

    /**
     * Constructor
     * @param id String a unique ID that also matches the Users ID in the Model Service
     * @param name String the Users friendly name
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.credentials = new HashMap<String, Credential>();
        this.entitlements = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public HashMap<String, Credential> getCredentials() {
        return this.credentials;
    }

    public void addCredential(Credential credential) {
        this.credentials.put(credential.getId(), credential);
    }

    public HashMap<String, Entitlement> getEntitlements() {
        return this.entitlements;
    }

    public void addEntitlement(Entitlement entitlement) {
        this.entitlements.put(entitlement.getId(), entitlement);
    }

    /**
     * Iterate through each credential associated with this User and see if any of them match the
     * non hashed credential string passed in
     * @param credentialString String a not yet hashed credential string
     * @return whether or not any of the credentials matched the credentialString
     */
    public Boolean verifyCredentialString(String credentialString) {
        final Boolean[] verified = {false};
        this.credentials.forEach((k, v) -> {
            if (v.verifyCredentialString(credentialString)) {
                verified[0] = true;
            }
        });
        return verified[0];
    }

    /**
     * Use the visitor pattern to try to find if the User has access to the Permission ID passed in
     * @param id Strting a valid Permission ID
     * @return Permission if the User has access to the Permission with the provided ID, null of not
     */
    public Permission findPermissionById(String id) {
        FindPermissionByIdVisitor findPermissionById = new FindPermissionByIdVisitor(id);

        this.entitlements.forEach((k, v) -> {
            v.acceptVisitor(findPermissionById);
        });

        return findPermissionById.getResult();
    }

    // standard visitor pattern acceptor
    @Override
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitUser(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "User {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", credentials=" + credentials.size() +
                ", entitlements=" + entitlements.size() +
                '}';
    }
}
