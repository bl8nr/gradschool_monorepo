package com.cscie97.smartcity.authentication;

import java.util.Date;

/**
 * CheckAccessVisitor Class
 * This visitor is sent through the Auth Token map where it visits each Auth Token and uses the
 * FindPermissionById visitor (via authToken.getPermissionById()) to go through the AuthTokens
 * Entitlements until it completes and can report whether or not it found a Permission with the
 * ID permissionID provided
 *
 */
public class CheckAccessVisitor implements AuthServiceVisitor {
    private Boolean userHasAccess = false;
    private String authTokenString;
    private String permissionId;

    /**
     * Constructor
     * @param authTokenString String an authToken string matching a valid AuthToken object
     * @param permissionId String an ID associated with a valid Permission
     */
    public CheckAccessVisitor(String authTokenString, String permissionId) {
        this.authTokenString = authTokenString;
        this.permissionId = permissionId;
    }

    // get the result for after the visitor has completed
    public Boolean getUserHasAccess() {
        return this.userHasAccess;
    }

    /**
     * Visit each authtoken and if the auth token matches the one provided in the constructor and
     * the auth token is not expired and the auth token is active and the auth token contains a
     * Permission with the same ID as the one provided in the constructor, then set userHasAccess to
     * true to show that the AutToken has access to the required Permission
     * @param authToken
     */
    public void visitAuthToken(AuthToken authToken) {
        if (    (authToken.getToken().equals(authTokenString)) &&
                (authToken.getState() == AuthTokenStateEnum.ACTIVE) &&
                (authToken.getExpirationTime().compareTo(new Date()) > 0) &&
                (authToken.getPermissionById(permissionId) != null)) {
            userHasAccess = true;
        }
    }

    public void visitUser(User user) {
        // not implemented
    }

    public void visitResource(Resource resource) {
        // not implemented
    }

    public void visitRole(Role role) {
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
