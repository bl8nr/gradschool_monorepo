package com.cscie97.smartcity.model;

import com.cscie97.smartcity.controller.SmartCityControllerServiceException;

public interface iObserver {
    public void update(SensorEvent sensorEvent) throws SmartCityControllerServiceException;
}
