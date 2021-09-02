package com.cscie97.smartcity.authentication;

/**
 * Permission Class
 * The Permission class represents what a User is able to do. If a User is associated with a Permission then they
 * have access to the methods that also use the Permission, otherwise the User cannot use those methods. Permissions
 * are the leaf nodes in the Entitlement composition.
 */
public class Permission extends Entitlement {

    /**
     * Constructor
     * @param id String a unique ID not yet used by any Entitlement in the Auth Service
     * @param name String friendly name for the Permission
     * @param description String friendly description for the Permission
     */
    public Permission(String id, String name, String description) {
        super(id, name, description);
    }

    // standard visitor pattern acceptor
    @Override
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitPermission(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "Permission {" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", description=" + super.getDescription() +
                "}";
    }
}
