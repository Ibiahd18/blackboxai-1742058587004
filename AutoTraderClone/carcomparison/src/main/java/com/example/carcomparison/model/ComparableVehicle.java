package com.example.carcomparison.model;

import java.util.List;

/**
 * Interface that defines the required methods for a vehicle to be comparable in the comparison module.
 * Any vehicle class that wants to use the comparison feature must implement this interface.
 */
public interface ComparableVehicle {
    String getId();
    String getMake();
    String getModel();
    int getYear();
    double getPrice();
    
    // Performance metrics
    int getHorsepower();
    int getTorque();
    double getAcceleration();
    double getFuelEfficiency();
    
    // Features and safety
    List<String> getFeatures();
    List<String> getSafetyFeatures();
    
    // Helper method
    default String getFullName() {
        return getYear() + " " + getMake() + " " + getModel();
    }
}
