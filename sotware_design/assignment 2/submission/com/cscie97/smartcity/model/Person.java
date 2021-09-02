package com.cscie97.smartcity.model;

/**
 * Person
 * The person class is an abstract class that determines the minimum requirements a Resident and Visitor must
 * both share in order to be valid and storeable
 */
public abstract class Person {
    private String personId;
    private String biometricId;
    private Location location;

    /**
     * Person Constructor
     * @param personId String: A person Id unique against all other people in the system
     * @param biometricId String: A biometric id used for authentication
     * @param location Location: The current location of the Person
     */
    public Person(String personId, String biometricId, Location location) {
        this.personId = personId;
        this.biometricId = biometricId;
        this.location = location;
    }

    public String getPersonId() {
        return this.personId;
    }

    public String getBiometricId() {
        return this.biometricId;
    }

    protected void setBiometricId(String biometricId) {
        this.biometricId = biometricId;
    }

    public Location getLocation() {
        return this.location;
    }

    protected void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", biometricId='" + biometricId + '\'' +
                '}';
    }
}
