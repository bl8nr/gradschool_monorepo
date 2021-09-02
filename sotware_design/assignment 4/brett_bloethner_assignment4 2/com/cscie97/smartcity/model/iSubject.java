package com.cscie97.smartcity.model;

public interface iSubject {
    public void registerObserver(iObserver observer) throws Throwable;
    public void removeObserver(iObserver observer) throws  Throwable;
    public void notifyObservers(SensorEvent sensorEvent) throws  Throwable;
}
