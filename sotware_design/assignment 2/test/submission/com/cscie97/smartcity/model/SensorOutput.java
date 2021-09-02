package com.cscie97.smartcity.model;

/**
 * SensorOutput
 * a Sensor Output represents an output action executed by a device. All devices have at least a speaker output sensor.
 * This class could be made as a result of a real sensor output or a simulated sensor output.
 */
public class SensorOutput {
    SensorOutputTypeEnum type;
    String value;

    /**
     * SensorOutput Constructor
     * @param type SensorOutputTypeEnum: type of output this object is representing (SPEAKER)
     * @param value String: string value/translation of the information output/communicated
     */
    public SensorOutput(SensorOutputTypeEnum type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "\nSensorOutput{" +
                "type=" + type +
                ", value='" + value + '\'' +
                "}\n";
    }
}
