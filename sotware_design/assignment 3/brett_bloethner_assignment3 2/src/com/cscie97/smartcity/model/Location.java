package com.cscie97.smartcity.model;

/**
 * Location
 * This location class represents the location for a city, device or person. Devices or persons would have a radius
 * or 0.0 while cities would have a radius greater than 0.0 to represent the citys total area.
 */
public class Location {
    private Float latitude;
    private Float longitude;
    private Float radius;

    /**
     * Location Constructor
     * @param latitude Float: Latitude of the location
     * @param longitude Float: Longitude of the location
     * @param radius Float: Radius of the location (if its a point (device, person), then this should be set to 0.0)
     */
    public Location(Float latitude, Float longitude, Float radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    /**
     * Get the distance from the edge of this location instance to the edge of another, passed in, location instance
     * If the distance is less than 0, then these two locations are touching each others radius. If one Location has a
     * 0.0 radius (person or device) than that means that the person or device is "inside" the radius of the other location
     *
     * This solution, as well as deg2rad() and rad2deg() has been adapted from the following stack overflow post...
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     *
     * @param location Loation: The location that your measuring the distance from
     * @return Float: Distance between the edge of each Locations radius
     */
    public Float getDistaceFromEdge(Location location) {
        double theta = this.longitude - location.getLongitude();
        double dist = Math.sin(deg2rad(this.latitude)) * Math.sin(deg2rad(location.getLatitude())) + Math.cos(deg2rad(this.latitude)) * Math.cos(deg2rad(location.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        double distanceFromCenters = dist * 60 * 1.1515;
        double distanceFromEdges = (distanceFromCenters - this.radius - location.radius);

        return (float)distanceFromEdges;
    }

    /**
     * Convert degrees to radians
     * @param deg double: number in degrees
     * @return double: Equivalent number in radians
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Convert radians to degrees
     * @param rad double: number in radians
     * @return double: Equivalent number in degrees
     */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
