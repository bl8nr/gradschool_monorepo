package com.cscie97.smartcity.authentication;

/**
 * FindPermissionByIdVisitor Class
 * This Visitor is used to iterate through the Entitlement composition tree until it finds a Permission that
 * has the same ID provided to the Visitor
 */
public class FindPermissionByIdVisitor implements AuthServiceVisitor {
    private String findById;
    private Permission result = null;

    /**
     * Constructor
     * @param id String an ID associated with a valid Permission object
     */
    public FindPermissionByIdVisitor(String id) {
        this.findById = id;
    }

    public Permission getResult() {
        return this.result;
    }

    /**
     * Visit a Role object and send the Visitor to visit all of its children
     * @param role A valid Role object
     */
    public void visitRole(Role role) {
        role.getChildrenMap().forEach((k, v) -> {
            v.acceptVisitor(this);
        });
    }

    /**
     * Visit a Permission object and see if its ID matches the ID provided to the visitor, if so then
     * save the Permission as the result
     * @param permission A valid Permission object
     */
    public void visitPermission(Permission permission) {
        if (permission.getId().equals(findById)) {
            this.result = permission;
        }
    }

    public void visitSmartCityAuthenticationService(SmartCityAuthenticationService smartCityAuthenticationService) {
        // not implemented
    }

    public void visitCredential(Credential credential) {
        // not implemented
    }

    public void visitUser(User user) {
        // not implemented
    }

    public void visitResource(Resource resource) {
        // not implemented
    }

    public void visitAuthToken(AuthToken authToken) {
        // not implemented
    }
}
