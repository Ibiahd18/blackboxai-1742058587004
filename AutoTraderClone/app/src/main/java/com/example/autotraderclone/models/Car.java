package com.example.autotraderclone.models;

import java.util.List;
import java.util.ArrayList;

public class Car {
    private String id;
    private String make;
    private String model;
    private int year;
    private double price;
    private int mileage;
    
    // Performance metrics
    private int horsepower;
    private int torque;
    private double acceleration; // 0-60 mph time
    private double fuelEfficiency; // MPG
    
    // Features and safety
    private List<String> features;
    private List<String> safetyFeatures;
    
    public Car(String id, String make, String model, int year, double price, int mileage) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.features = new ArrayList<>();
        this.safetyFeatures = new ArrayList<>();
    }
    
    // Performance setters
    public void setPerformanceMetrics(int horsepower, int torque, double acceleration, double fuelEfficiency) {
        this.horsepower = horsepower;
        this.torque = torque;
        this.acceleration = acceleration;
        this.fuelEfficiency = fuelEfficiency;
    }
    
    // Add features
    public void addFeature(String feature) {
        this.features.add(feature);
    }
    
    public void addSafetyFeature(String safetyFeature) {
        this.safetyFeatures.add(safetyFeature);
    }
    
    // Getters
    public String getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public int getMileage() { return mileage; }
    public int getHorsepower() { return horsepower; }
    public int getTorque() { return torque; }
    public double getAcceleration() { return acceleration; }
    public double getFuelEfficiency() { return fuelEfficiency; }
    public List<String> getFeatures() { return features; }
    public List<String> getSafetyFeatures() { return safetyFeatures; }
    
    // Helper method to get full name
    public String getFullName() {
        return year + " " + make + " " + model;
    }
    
    // Static method to create sample cars for testing
    public static List<Car> getSampleCars() {
        List<Car> cars = new ArrayList<>();
        
        // Sample Car 1: Tesla Model 3
        Car tesla = new Car("1", "Tesla", "Model 3", 2023, 45990.00, 0);
        tesla.setPerformanceMetrics(283, 330, 5.8, 132); // 132 MPGe
        tesla.addFeature("15-inch Touchscreen");
        tesla.addFeature("Autopilot");
        tesla.addFeature("Premium Audio");
        tesla.addSafetyFeature("Automatic Emergency Braking");
        tesla.addSafetyFeature("Lane Departure Warning");
        tesla.addSafetyFeature("360° Cameras");
        cars.add(tesla);
        
        // Sample Car 2: BMW M3
        Car bmw = new Car("2", "BMW", "M3", 2023, 72995.00, 0);
        bmw.setPerformanceMetrics(473, 406, 4.1, 16);
        bmw.addFeature("iDrive 8");
        bmw.addFeature("Harman Kardon Audio");
        bmw.addFeature("Sport Seats");
        bmw.addSafetyFeature("Active Driving Assistant");
        bmw.addSafetyFeature("Parking Assistant");
        bmw.addSafetyFeature("Head-up Display");
        cars.add(bmw);
        
        // Sample Car 3: Toyota Camry
        Car toyota = new Car("3", "Toyota", "Camry", 2023, 25945.00, 0);
        toyota.setPerformanceMetrics(203, 184, 7.6, 28);
        toyota.addFeature("7-inch Touchscreen");
        toyota.addFeature("Apple CarPlay");
        toyota.addFeature("Dual-Zone Climate Control");
        toyota.addSafetyFeature("Toyota Safety Sense 2.5+");
        toyota.addSafetyFeature("Pre-Collision System");
        toyota.addSafetyFeature("Lane Tracing Assist");
        cars.add(toyota);
        
        return cars;
    }
}
