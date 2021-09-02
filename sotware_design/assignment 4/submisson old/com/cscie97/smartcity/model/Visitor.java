package com.cscie97.smartcity.model;

/**
 * Visitor
 * A visitor is a person in the smart city system. It does not belong to a city and is effectively anonymous
 * Right now, the visitor extends person but pretty much only implements the default functionality of Person
 */
public class Visitor extends Person {

    /**
     * Visitor Constructor
     * @param personId String: A globally unique personId
     * @param biometricId String: A unique ID used for authentication
     * @param location Location: The current location of the person
     */
    public Visitor(String personId, String biometricId, Location location) {
        super(personId, biometricId, location);
    }

    /**
     * Visitor constructor overload. Construct a Visitor from data in another Visitor (ie make a copy)
     * @param visitor Visitor: A valid visitor object
     */
    public Visitor(Visitor visitor) {
        super(visitor.getPersonId(), visitor.getBiometricId(), visitor.getLocation());
    }

    @Override
    public String toString() {
        return "\nVisitor{}\n" + super.toString();
    }
}
