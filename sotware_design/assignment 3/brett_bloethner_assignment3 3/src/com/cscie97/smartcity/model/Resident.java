package com.cscie97.smartcity.model;

/**
 * Resident
 * A resident is a person in the smart city system and it extends the Person abstract class. A resident does not belong
 * to a City but can be geographically located inside of a Citys Location. Residents are much more full featured persons
 * than Visitors who are more anonymous and ephemeral.
 */
public class Resident extends Person {
    private String name;
    private String phoneNumber;
    private String blockchainAddress;
    private ResidentRoleEnum role;

    /**
     * Resident Constructor
     * @param personId String: a globally unique personId
     * @param name String: a friendly name for/of the person
     * @param biometricId String: a unique ID used for authentication
     * @param phoneNumber String: the residents phone number
     * @param blockchainAddress String: address of the blockchain account belonging to this resident
     * @param role String: the residents role (CHILD, ADULT, PUBLIC_ADMINISTRATOR)
     * @param location Location: the current location of the resident
     */
    public Resident(String personId, String name, String biometricId, String phoneNumber, String blockchainAddress, ResidentRoleEnum role, Location location) {
        super(personId, biometricId, location);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.blockchainAddress = blockchainAddress;
        this.role = role;
    }

    /**
     * Resident constructor overload. Construct a Resident from data in another Resident (ie make a copy)
     * @param resident Resident: A valid Resident object
     */
    public Resident(Resident resident) {
        super(resident.getPersonId(), resident.getBiometricId(), resident.getLocation());
        this.name = resident.getName();
        this.phoneNumber = resident.getPhoneNumber();
        this.blockchainAddress = resident.getBlockchainAddress();
        this.role = resident.getRole();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBlockchainAddress() {
        return this.blockchainAddress;
    }

    public void setBlockchainAddress(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
    }

    public ResidentRoleEnum getRole() {
        return this.role;
    }

    public void setRole(ResidentRoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "\nResident{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", blockchainAddress='" + blockchainAddress + '\'' +
                ", role=" + role +
                "}\n" + super.toString();
    }
}
