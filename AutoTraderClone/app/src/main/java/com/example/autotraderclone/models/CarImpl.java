package com.example.autotraderclone.models;

import com.example.carcomparison.model.ComparableVehicle;
import java.util.ArrayList;
import java.util.List;

public class CarImpl implements ComparableVehicle {
    private String id;
    private String make;
    private String model;
    private int year;
    private double price;
    private int mileage;
    
    private int horsepower;
    private int torque;
    private double acceleration;
    private double fuelEfficiency;
    
    private List<String> features;
    private List<String> safetyFeatures;
    
    public CarImpl(String id, String make, String model, int year, double price, int mileage) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.features = new ArrayList<>();
        this.safetyFeatures = new ArrayList<>();
    }
    
    public void setPerformanceMetrics(int horsepower, int torque, double acceleration, double fuelEfficiency) {
        this.horsepower = horsepower;
        this.torque = torque;
        this.acceleration = acceleration;
        this.fuelEfficiency = fuelEfficiency;
    }
    
    public void addFeature(String feature) {
        this.features.add(feature);
    }
    
    public void addSafetyFeature(String safetyFeature) {
        this.safetyFeatures.add(safetyFeature);
    }
    
    // ComparableVehicle interface implementation
    @Override
    public String getId() { return id; }
    
    @Override
    public String getMake() { return make; }
    
    @Override
    public String getModel() { return model; }
    
    @Override
    public int getYear() { return year; }
    
    @Override
    public double getPrice() { return price; }
    
    @Override
    public int getHorsepower() { return horsepower; }
    
    @Override
    public int getTorque() { return torque; }
    
    @Override
    public double getAcceleration() { return acceleration; }
    
    @Override
    public double getFuelEfficiency() { return fuelEfficiency; }
    
    @Override
    public List<String> getFeatures() { return new ArrayList<>(features); }
    
    @Override
    public List<String> getSafetyFeatures() { return new ArrayList<>(safetyFeatures); }
    
    public int getMileage() { return mileage; }
    
    // Static factory method for sample data
    public static List<CarImpl> getSampleCars() {
        List<CarImpl> cars = new ArrayList<>();
        
        // Sample Car 1: Tesla Model 3
        CarImpl tesla = new CarImpl("1", "Tesla", "Model 3", 2023, 45990.00, 0);
        tesla.setPerformanceMetrics(283, 330, 5.8, 132); // 132 MPGe
        tesla.addFeature("15-inch Touchscreen");
        tesla.addFeature("Autopilot");
        tesla.addFeature("Premium Audio");
        tesla.addSafetyFeature("Automatic Emergency Braking");
        tesla.addSafetyFeature("Lane Departure Warning");
        tesla.addSafetyFeature("360° Cameras");
        cars.add(tesla);
        
        // Sample Car 2: BMW M3
        CarImpl bmw = new CarImpl("2", "BMW", "M3", 2023, 72995.00, 0);
        bmw.setPerformanceMetrics(473, 406, 4.1, 16);
        bmw.addFeature("iDrive 8");
        bmw.addFeature("Harman Kardon Audio");
        bmw.addFeature("Sport Seats");
        bmw.addSafetyFeature("Active Driving Assistant");
        bmw.addSafetyFeature("Parking Assistant");
        bmw.addSafetyFeature("Head-up Display");
        cars.add(bmw);
        
        // Sample Car 3: Toyota Camry
        CarImpl toyota = new CarImpl("3", "Toyota", "Camry", 2023, 25945.00, 0);
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
