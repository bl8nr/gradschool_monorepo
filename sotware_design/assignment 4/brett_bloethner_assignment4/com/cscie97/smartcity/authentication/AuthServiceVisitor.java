package com.cscie97.smartcity.authentication;

/**
 * Interface which all Auth Service Visitors must implement in order to support the objects theyre visiting
 */
public interface AuthServiceVisitor {
    public void visitUser(User user);
    public void visitResource(Resource resource);
    public void visitAuthToken(AuthToken authToken);
    public void visitRole(Role role);
    public void visitPermission(Permission permission);
    public void visitSmartCityAuthenticationService(SmartCityAuthenticationService smartCityAuthenticationService);
    public void visitCredential(Credential credential);
}
