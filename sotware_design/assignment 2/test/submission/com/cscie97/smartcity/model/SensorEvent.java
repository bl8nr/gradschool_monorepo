package com.cscie97.smartcity.model;

/**
 * Sensor Event
 * a Sensor Event represents an event that occurred that a sensor detected. This object is meant to be digested by a
 * devices registerSensorEvent() method where the device can then handle the even accordingly. This class could be made
 * as a result of a real sensor event or a simulated event.
 */
public class SensorEvent {
    private SensorEventTypeEnum type;
    private String value;
    private String subject;

    /**
     * Sensor Event Constructor
     * @param type SensorEventTypeEnum: the type of sensor this event is being detected by
     * @param value String: the value measured by the sensor
     * @param subject String: the optional (could be null) subject of whats/whose measured (person)
     */
    public SensorEvent(SensorEventTypeEnum type, String value, String subject) {
        this.type = type;
        this.value = value;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "\nSensorEvent{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", subject='" + subject + '\'' +
                "}\n";
    }
}
