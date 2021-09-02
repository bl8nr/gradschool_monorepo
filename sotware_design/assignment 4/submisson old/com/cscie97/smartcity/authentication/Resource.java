package com.cscie97.smartcity.authentication;

/**
 * Resource Class
 * The Resource class represents and actual IoT device in the Smart City system
 */
public class Resource implements AuthServiceVisitorElement {
    private String id;
    private String description;

    /**
     * Constructor
     * @param id String an ID unique to all Resources in the Auth Service
     * @param description String a friendly descrpition for the Resource
     */
    public Resource(String id, String description) {
        this.id = id;
        this.description = description;
    }

    // standard visitor pattern acceptor
    public void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitResource(this);
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "Resource {" +
                "id='" + id +
                ", description='" + description +
                "}";
    }
}
