package com.cscie97.smartcity.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SmartCityAuthenticationService
 * The Smart City Authentication Service is responsible for all of the functionality of authentication in the
 * Smart City. It manages Roles, Permissions, Resources, Resource Roles, Users, Credentials and Auth Tokens as
 * they relate to authentication in the Smart City system. It includes maps to store Users, Resources and Auth
 * Tokens as well as a composite tree of Role, Permission and Resource Roles.
 */
public class SmartCityAuthenticationService {
    private static SmartCityAuthenticationService uniqueInstance = new SmartCityAuthenticationService();
    private HashMap<String, User> users;
    private HashMap<String, Resource> resources;
    private HashMap<String, AuthToken> authTokens;
    private Role rootRole;


    /**
     * Constructor
     * Create the empty hash maps. Establish a system root User with all permissions by giving them the
     * top level rootPermission Entitlement. Add a system SCMS User for the Model Service as well.
     */
    private SmartCityAuthenticationService() {

        // create empty hash maps
        this.users = new HashMap<>();
        this.resources = new HashMap<>();
        this.authTokens = new HashMap<>();

        // creat top level root role and root permission, entitlement admin needs to be created on boot or else root admin cant add entitlements
        this.rootRole = new Role("rootRole", "rootRole", "root role");
        Permission rootPermission = new Permission("rootPermission", "rootPermission", "root permission");
        this.rootRole.addEntitlement(rootPermission);
        this.rootRole.addEntitlement(new Permission("auth_role_entitlement_admin", "User Administrator", "Create, Update, Delete Entitlements"));

        // create root user and add the top level root role to the root user
        User rootUser = new User("root_user", "root_user");
        this.users.put(rootUser.getId(), rootUser);
        rootUser.addCredential(new Credential(UUID.randomUUID().toString(), "password", CredentialType.PASSWORD));
        rootUser.addEntitlement(this.rootRole);

        // create a user for the model service and add the root role to them as well
        // this is needed for some side effects that are a result of some coupling/reuse of the methods in the model service
        User controllerUser = new User("smcs", "smcs");
        this.users.put(controllerUser.getId(), controllerUser);
        controllerUser.addCredential(new Credential(UUID.randomUUID().toString(), "fldi954*#jd&36s", CredentialType.PASSWORD));
        controllerUser.addEntitlement(this.rootRole);

        System.out.println("Creating administrative user \"root_user\"");
    }


    public HashMap<String, User> getUsersHashMap() {
        return this.users;
    }

    public HashMap<String, AuthToken> getAuthTokensHashMap() {
        return this.authTokens;
    }

    // singleton pattern getter
    public static SmartCityAuthenticationService getInstance() {
        return uniqueInstance;
    }

    /**
     * Create a new Resource
     * @param id String a ID unique to all of the Resources stored in the Auth Service
     * @param description String a friendly description of the Resource
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if a Resource with the provided ID already exists or Auth Token fails
     */
    public void createResource(String id, String description, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_resource_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        if (this.resources.get(id) != null) {
            throw new AuthServiceException("createResource()", "A Resource with this ID already exists");
        }
        this.resources.put(id, new Resource(id, description));
        System.out.printf("NEW RESOURCE CREATED { id: %s, description: %s }\n", id, description);
    }

    /**
     * Create a new Resource Role, attach that Role to another Role and a Resource
     * @param id String a ID unique to all of the Roles stored in the Auth Service
     * @param roleId String the ID of a valid existing Role
     * @param resourceId String the ID of a valid existing Resource
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if either the Role or Resource dont exist or Auth Token fails
     */
    public void createResourceRole(String id, String roleId, String resourceId, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_resource_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        Role role = this.findRoleById(roleId, authToken);
        Resource resource = this.resources.get(resourceId);

        // throw error if the required Resource or Role does not exist
        if ((role == null) || (resource == null)) {
            throw new AuthServiceException("createResourceRole()", "Filed to find Role or Resource");
        }

        // add new Resource Role to the entitlement whose ID is provided
        ResourceRole newResourceRole = new ResourceRole(id, id, id, role, resource);
        role.addEntitlement(newResourceRole);
        System.out.printf("NEW RESOURCE ROLE CREATED { id: %s, roleId: %s, resourceId: %s \n", id, roleId, resourceId);
    }


    /**
     * Create a new Permission
     * @param id String a ID unique to all of the Entitlements stored in the Auth Service
     * @param name String a friendly name for the Permission
     * @param description String a friendly description of the Permission
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if the Entitlement ID is already in use or if the authToken doesnt have access or Auth Token fails
     */
    public void createPermission(String id, String name, String description, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        // if a Permission or Role already exist by this ID then return an error
        if ((this.findPermissionById(id, authToken) != null) || (this.findRoleById(id, authToken) != null)) {
            throw new AuthServiceException("createPermission()", "An Entitlement with this ID already exists");
        }

        // create a new Permission and add it to the entitlement tree
        // adding it to the Root Role top level entitlement gives only the Root Role access unless the Permission
        // until its added to another Role or associated with a User manually
        this.rootRole.addEntitlement(new Permission(id, name, description));
        System.out.printf("\nNEW PERMISSION CREATED { Name: %s, ID: %s, Description: %s }\n", name, id, description);
    }


    /**
     * Create a new Role Entitlement
     * @param id String a ID unique to all of the Entitlements stored in the Auth Service
     * @param name String a friendly name for the Permission
     * @param description String a friendly description of the Role
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException Entitlement with that ID already exists or if auth token is invalid
     */
    public void createRole(String id, String name, String description, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        // if a Permission or Role already exist by this ID then return an error
        if ((this.findPermissionById(id, authToken) != null) || (this.findRoleById(id, authToken) != null)) {
            throw new AuthServiceException("createPermission()", "An Entitlement with this ID already exists");
        }

        this.rootRole.addEntitlement(new Role(id, name, description));
        System.out.printf("NEW ROLE CREATED { ID: %s, Name: %s, Description: %s }\n", id, name, description);
    }


    /**
     * Add a Permission to a Role
     * @param roleId String the ID of a valid existing Role
     * @param permissionId String the ID of a valid existing Permission
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if either the Permission or the Role dont exist or if auth token is invalid
     */
    public void addPermissionToRole(String roleId, String permissionId, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        Role role = this.findRoleById(roleId, authToken);
        Permission permission = this.findPermissionById(permissionId, authToken);

        // if either the Permission or the Role dont exist then throw and error
        if ((role == null) || (permission == null)) {
            throw new AuthServiceException("addPermissionToRole()", "Failed to find Role or Permission");
        }

        // add the Permission to the Entitlement
        role.addEntitlement(permission);
        System.out.printf("PERMISSION ADDED TO ROLE { permissionId: %s, roleId: %s }\n", permissionId, roleId);
    }


    /**
     * Create a new User
     * @param id String a unique ID that matches the ID of the User in the Model Service
     * @param name String a friendly name for the User
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if a User with the provided ID already exists or if auth token is invalid
     */
    public void createUser(String id, String name, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_user_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        // if any User already exists by this ID then throw an error
        if (this.users.get(id) != null) {
            throw new AuthServiceException("createUser()", "A User with that ID already exists");
        }

        // create a new User and give them default credentials of "password" password
        User newUser = new User(id, name);
        this.users.put(newUser.getId(), newUser);
        this.updateUserCredential(newUser.getId(), CredentialType.PASSWORD, "password", authToken);

        System.out.printf("NEW USER CREATED { ID: %s, Name: %s }\n", id, name);
    }


    /**
     * Update a User Credential for the User ID provided
     * @param userId String the ID of a valid existing User
     * @param type CredentialType the type of credential provided
     * @param credential String an unhashed String of the new Credential
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException if user can be found or if auth token is invalid
     */
    public void updateUserCredential(String userId, CredentialType type, String credential, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_user_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        User user = this.users.get(userId);

        // if no User with the provided ID exists then return an error
        if (user == null) {
            throw new AuthServiceException("updateUserCredential()", "No User with that ID exists");
        }

        // add the new Credential to the User
        user.addCredential(new Credential(UUID.randomUUID().toString(), credential, type));
        System.out.printf("USER CREDENTIAL UPDATED { userID: %s, credentialType: %s, credential: %s }\n", userId, type, credential);
    }

    /**
     * Add a Role Entitlement to the User whose ID is provided
     * @param userId String the ID of a valid existing User
     * @param roleId String the ID of a valid existing Role
     * @param authToken String a valid Auth Token
     * @throws AuthServiceException
     */
    public void addRoleToUser(String userId, String roleId, String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        Role role = this.findRoleById(roleId, authToken);
        User user = this.users.get(userId);

        // if either the User or the Role cant be found then throw an error
        if ((role == null) || (user == null)) {
            throw new AuthServiceException("addPermissionToRole()", "Failed to find Role or User");
        }

        // add the Entitlement to the User
        user.addEntitlement(role);

        System.out.printf("ROLE ADDED TO USER { userId: %s, roleID: %s }\n", userId, roleId);
    }


    /**
     * Log a User in by verify their credentials then issue an Auth Token. This method uses both user ID
     * and credential string and is meant to be used with Username Password authentication
     * @param userId String the ID of a valid existing User
     * @param credential String of a potential matching un hashed credential
     * @return AuthToken a valid new Auth Token for the user logged in
     * @throws AuthServiceException
     */
    public AuthToken login(String userId, String credential) throws AuthServiceException {
        User user = this.users.get(userId);

        // if no User with the provided ID exists then return an error
        if (user == null) {
            throw new AuthServiceException("login()", "Failed to find User");
        }

        // if the credential string provided is invalid then return an error
        if (!user.verifyCredentialString(credential)) {
            throw new AuthServiceException("login()", "Incorrect credentials");
        }

        // issue and return a new Auth Token for that user
        AuthToken authToken = new AuthToken(user);
        this.authTokens.put(authToken.getToken(), authToken);

        System.out.printf("\nLOGGED IN USER { userId: %s }\n", userId, authToken.getToken());
        System.out.printf("AUTH TOKEN CREATED { userId: %s, token: %s }\n", userId, authToken.getToken());

        return authToken;
    }

    /**
     * Log a User in by verify their credentials then issue an Auth Token. This method uses only the credential
     * and is meant to be used with Biometric authentications
     * @param credential String an un hashed credential that could be valid
     * @return AuthToken a valid new Auth Token for the user logged in
     * @throws AuthServiceException
     */
    public AuthToken login(String credential) throws AuthServiceException {
        User user = null;

        // look for user with credential matching what was provided
        for (Map.Entry<String, User> entry : this.users.entrySet()) {
            User v = entry.getValue();
            if (v.verifyCredentialString(credential)) {
                user = v;
            }
        }

        // if no user is found then return an error
        if (user == null) {
            throw new AuthServiceException("login()", "Failed to find user with that biometric identifier");
        }

        // issue and return a new Auth Token for that user
        AuthToken authToken = new AuthToken(user);
        this.authTokens.put(authToken.getToken(), authToken);

        System.out.printf("\nLOGGED IN USER VIA BIOMETRICS { userId: %s }\n", user.getId());
        System.out.printf("AUTH TOKEN CREATED { userId: %s, token: %s }\n", user.getId(), authToken.getToken());

        return authToken;
    }


    /**
     * Iterate through Auth Tokens to find every token related to the User ID provided and invalidate
     * those tokens
     * @param userId String the ID of a valid existing User
     * @throws AuthServiceException
     */
    public void logout(String userId) throws AuthServiceException {
        this.authTokens.forEach((k, v) -> {
            if (v.getUser().getId().equals(userId)) {
                v.setState(AuthTokenStateEnum.INACTIVE);
            }
        });

        System.out.printf("\nLOGGED OUT USER { userId: %s }\n", userId);
    }


    /**
     * Check to see if the Auth Token provided has access to the Permission ID also provided. Visitor Pattern
     * is used to accomplish this.
     * @param authTokenString String a valid Auth Token
     * @param permissionId String the ID of a valid existing Permission
     * @throws InvalidAccessTokenException if the user does not have access to the Permission ID provided
     */
    public void checkAccess(String authTokenString, String permissionId) throws InvalidAccessTokenException {
        CheckAccessVisitor checkAccessVisitor = new CheckAccessVisitor(authTokenString, permissionId);

        this.authTokens.forEach((k, v) -> {
            v.acceptVisitor(checkAccessVisitor);
        });

        if (!checkAccessVisitor.getUserHasAccess()) {
            throw new InvalidAccessTokenException("checkAccess()", "Invalid or Missing Auth Token");
        }
    }

    /**
     *  Method to print the inventory of all the Entitlements, User, Auth Tokens and Credentials stored
     *  in the Authentication Service. This method utilizes the Visitor Pattern extensively.
     *  @param authToken String a valid Auth Token
     */
    public void printInventory(String authToken) throws AuthServiceException {

        // verify that the caller has a valid authToken for the right permission, else throw error
        try {
            this.checkAccess(authToken, "rootPermission");
        } catch (InvalidAccessTokenException e) {
            throw new AuthServiceException("createPermission()", "Invalid Auth Service Admin Token");
        }

        InventoryVisitor inventoryVisitor = new InventoryVisitor();
        System.out.println("********** BEGIN INVENTORY ********** \n");
        System.out.println("********** PRINTING ENTITLEMENTS **********");
        this.rootRole.acceptVisitor(inventoryVisitor);
        System.out.println("********** END PRINTING ENTITLEMENTS ********** \n");
        this.acceptVisitor(inventoryVisitor);
        System.out.println("\n********** END INVENTORY **********");
    }

    /**
     * Standard visitor acceptor
     * @param authServiceVisitor a valid AuthServiceVisitor
     */
    private void acceptVisitor(AuthServiceVisitor authServiceVisitor) {
        authServiceVisitor.visitSmartCityAuthenticationService(this);
    }

    /**
     * Find Role in Auth Service storage by Role ID. Visitor Pattern used.
     * @param id String the ID of a valid existing Role
     * @return Role if one is found, otherwise null
     */
    private Role findRoleById(String id, String authToken) {

        // verify that the caller has a valid authToken for the right permission, else return no results
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            return null;
        }

        FindRoleByIdVisitor findRoleByIdVisitor = new FindRoleByIdVisitor(id);
        this.rootRole.acceptVisitor(findRoleByIdVisitor);
        return findRoleByIdVisitor.getResult();
    }

    /**
     * Find Permission in Auth Service storage by Permission ID. Visitor Pattern used.
     * @param id String the ID of a valid existing Permission
     * @return Permission if one if found, otherwise null
     */
    private Permission findPermissionById(String id, String authToken) {

        // verify that the caller has a valid authToken for the right permission, else return no results
        try {
            this.checkAccess(authToken, "auth_role_entitlement_admin");
        } catch (InvalidAccessTokenException e) {
            return null;
        }

        FindPermissionByIdVisitor findPermissionById = new FindPermissionByIdVisitor(id);
        this.rootRole.acceptVisitor(findPermissionById);
        return findPermissionById.getResult();
    }
}
