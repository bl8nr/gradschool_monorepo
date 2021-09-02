package com.cscie97.smartcity.authentication;

/**
 * Interface for the objects to be visited by the AuthService Visitors to implement
 */
public interface AuthServiceVisitorElement {
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) throws Exception;
}
