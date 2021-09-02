package com.cscie97.smartcity.authentication;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * AuthToken Class
 * The AuthToken represents a token used by the system to validate against Permissions before a method is
 * executed. AuthTokens have a Entitlement composition tree that reflects what Roles and Permissions they
 * have access to, it is also associated with only one User.
 */
public class AuthToken implements AuthServiceVisitorElement {
    private String id;
    private Date expirationTime;
    private AuthTokenStateEnum state;
    private User user;
    private String token;
    private HashMap<String, Entitlement> entitlements;

    /**
     * Constructor
     * set token id to random string, set expiration time to now+2hours, set token to active, assign the
     * String version of the token a random ID/string, and load the associated Users' Entitlements into
     * Auth Tokens entitlements
     * @param user a Valid user object
     */
    public AuthToken(User user) {
        this.id = UUID.randomUUID().toString();
        this.expirationTime = Date.from(new Date().toInstant().plus(Duration.ofHours(2)));
        this.state = AuthTokenStateEnum.ACTIVE;
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.entitlements = user.getEntitlements();
    }

    public String getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public AuthTokenStateEnum getState() {
        return this.state;
    }

    public void setState(AuthTokenStateEnum state) {
        this.state = state;
    }

    /**
     * Iterate through the Entitlements and use the FindPermissionByIdVisitor to traverse down each Entitlement
     * tree and return the result that was got by the Visitor. This getPermissionById only gets a Permission by
     * ID within the scope of the Permissions associated with the Auth Token.
     * @param id String an ID associated with a valid Permission object
     * @return the Permission if its found, otherwise null
     */
    public Permission getPermissionById(String id) {
        FindPermissionByIdVisitor findPermissionById = new FindPermissionByIdVisitor(id);

        this.entitlements.forEach((k, v) -> {
            v.acceptVisitor(findPermissionById);
        });

        return findPermissionById.getResult();
    }

    // standard visitor pattern acceptor
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitAuthToken(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "AuthToken {" +
                "id='" + id + '\'' +
                ", expirationTime=" + expirationTime +
                ", state=" + state +
                ", user=" + user.getId() +
                ", token='" + token + '\'' +
                ", entitlements=" + entitlements.size() +
                '}';
    }
}
