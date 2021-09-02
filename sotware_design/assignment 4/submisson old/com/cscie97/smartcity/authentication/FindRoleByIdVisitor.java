package com.cscie97.smartcity.authentication;

/**
 * FindRoleByIdVisitor Class
 * This Visitor is used to iterate through the Entitlement composition tree until it finds a Role that
 * has the same ID provided to the Visitor
 */
public class FindRoleByIdVisitor implements AuthServiceVisitor {
    String findById;
    private Role result = null;

    /**
     * Constructor
     * @param id String an ID associated with a valid Role object
     */
    public FindRoleByIdVisitor(String id) {
        this.findById = id;
    }

    // get the result from after the visitor has completed
    public Role getResult() {
        return this.result;
    }

    /**
     * Visit Role and test whether or not the Role matches the provided ID, if it does not
     * then send the Visitor to visit all the Roles children, this works because the Visitor
     * is meant to be introduced at the top of the tree at the Root Role
     * @param role A valid Role object
     */
    public void visitRole(Role role) {
        if (role.getId().equals(findById)) {
            this.result = role;
        } else {
            role.getChildrenMap().forEach((k, v) -> {
                v.acceptVisitor(this);
            });
        }
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

    public void visitPermission(Permission permission) {
        // not implemented
    }

    public void visitSmartCityAuthenticationService(SmartCityAuthenticationService smartCityAuthenticationService) {
        // not implemented
    }

    public void visitCredential(Credential credential) {
        // not implemented
    }
}
