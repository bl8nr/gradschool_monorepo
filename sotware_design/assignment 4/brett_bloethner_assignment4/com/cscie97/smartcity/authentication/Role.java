package com.cscie97.smartcity.authentication;

import java.util.HashMap;

/**
 * Role Class
 * Role extends the Entitlement subclass and represents a Role that a User can be associated with and the
 * Role contains Permission as well as other Roles. Users who are associated to Role that contain Permissions
 * have access to methods that require that Permission
 */
public class Role extends Entitlement {
    private HashMap<String, Entitlement> children;

    /**
     * Constructor
     * @param id String an ID unique to all of the Entitlements
     * @param name String an friendly name for the Role
     * @param description String a friendly description for the Role
     */
    public Role(String id, String name, String description) {
        super(id, name, description);
        this.children = new HashMap<>();
    }

    public HashMap<String, Entitlement> getChildrenMap() {
        return this.children;
    };

    /**
     * Add an Entitlement (Role or Permission) to this Role
     * @param entitlement a valid Entitlement object (Role or Permission)
     */
    @Override
    public void addEntitlement(Entitlement entitlement) {
        this.children.put(entitlement.getId(), entitlement);
    }

    // standard visitor pattern acceptor
    @Override
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitRole(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "Role {" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", description=" + super.getDescription() +
                "}";
    }
}
