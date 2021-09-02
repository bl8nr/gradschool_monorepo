package com.cscie97.smartcity.authentication;

/**
 * Resource Role Class
 * The Resource Role class connects a Resource and a Role via two associations it makes, one to a Role and one
 * to a Resource.
 */
public class ResourceRole extends Role implements AuthServiceVisitorElement {
    private Role role;
    private Resource resource;

    /**
     * Constructor
     * @param id String a unique ID not used yet by any other Entitlement
     * @param name String a friendly name for the ResourceRole
     * @param description String a friendly description for the ResourceRole
     * @param role a valid Role
     * @param resource a valid Resource
     */
    public ResourceRole(String id, String name, String description, Role role, Resource resource) {
        super(id, name, description);
        this.role = role;
        this.resource = resource;
    }

    public Role getRole() {
        return this.role;
    }

    public Resource getResource() {
        return this.resource;
    }

    // override toString for presentable output
    @Override
    public String toString() {
        return "ResourceRole {" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", description=" + super.getDescription() +
                "}";
    }
}
