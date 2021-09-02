package com.cscie97.smartcity.authentication;

/**
 * Inventory Visitor Class
 * The Inventory Class is responsible for iterating through all of the objects in the Auth Service
 * and printing out the data of each of the object
 */
public class InventoryVisitor implements AuthServiceVisitor {

    public InventoryVisitor() {
    }

    /**
     * Visit User and print out the User and each of their Credentials
     * @param user A valid User object
     */
    public void visitUser(User user) {
        System.out.println(user.toString());
        user.getCredentials().forEach((k, v) -> {
            System.out.printf("CREDENTIAL of %s: ", user.getId());
            v.acceptVisitor(this);
        });
    }

    /**
     * Visit Resource and print out the Resources details
     * @param resource A valid Resource object
     */
    public void visitResource(Resource resource) {
        System.out.println(resource);
    }

    /**
     * Visit authToken and print out the authToken details
     * @param authToken A valid authToken object
     */
    public void visitAuthToken(AuthToken authToken) {
        System.out.println(authToken);
    }

    /**
     * Visit Role and print out the Role details then send the Visitor to visit the Roles children
     * @param role A valid Role object
     */
    public void visitRole(Role role) {
        System.out.println(role);

        // if the Role is a Resource Role, then send the Visitor to visit the Resource
        if (role instanceof ResourceRole) {
            System.out.printf("RESOURCE OF %s: ", role.getId());
            ((ResourceRole) role).getResource().acceptVisitor(this);
        }

        // send the Visitor to visit each of the Roles children
        role.getChildrenMap().forEach((k, v) -> {
            System.out.printf("%s OF %s: ", v.getClass().getSimpleName().toUpperCase(), role.getId());
            v.acceptVisitor(this);
        });
    }

    /**
     * Visit Permission and print out the Permission details
     * @param permission A valid Permission object
     */
    public void visitPermission(Permission permission) {
        System.out.println(permission);
    }

    /**
     * Visit Credential and print out the Credential details
     * @param credential A valid Credential Object
     */
    public void visitCredential(Credential credential) {
        System.out.println(credential);
    }

    /**
     * Visit SmartCityAuthenticationService and use it for navigation
     * @param smartCityAuthenticationService A valid SmartyCityAuthenticationService object
     */
    public void visitSmartCityAuthenticationService(SmartCityAuthenticationService smartCityAuthenticationService) {

        // iterate through the Users and visit each once
        System.out.println("********** PRINTING USERS AND CREDENTIALS **********");
        smartCityAuthenticationService.getUsersHashMap().forEach((k, v) -> {
            v.acceptVisitor(this);
        });

        // iterate through the AuthTokens and visit each one
        System.out.println("\n********** PRINTING AUTH TOKENS **********");
        smartCityAuthenticationService.getAuthTokensHashMap().forEach((k, v) -> {
            v.acceptVisitor(this);
        });

    }
}
