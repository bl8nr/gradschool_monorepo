package com.cscie97.smartcity.authentication;

import java.lang.UnsupportedOperationException;

/**
 * The Entitlement represents something a User is entitled to and is the bases of the Role and Permission
 * class which extend Entitlement. This class is no instantiated on its own, rather Permission and Role are
 * used.
 */
public class Entitlement implements AuthServiceVisitorElement {
    private String id;
    private String name;
    private String description;

    /**
     * Constructor
     * @param id String a unique ID not yet used by any other Entitlements
     * @param name String a friendly name for the Entitlement
     * @param description String a friendly description for the Entitlement
     */
    public Entitlement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * If this method is called by a Permission (which doesnt implement addEntitlemen()) then an
     * exception will be thrown
     * @param entitlement A valid Entitlement object
     * @throws Exception if called by a Class that hasn't overwritten then method
     */
    public void addEntitlement(Entitlement entitlement) throws Exception {
        throw new UnsupportedOperationException();
    }

    // standard visitor pattern acceptor
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        // not implemented, implemented in subclasses
    }
}
