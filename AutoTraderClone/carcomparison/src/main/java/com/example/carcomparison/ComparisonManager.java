package com.example.carcomparison;

import com.example.carcomparison.model.ComparableVehicle;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that manages the vehicle comparison state and operations.
 * This class serves as the main entry point for the comparison module.
 */
public class ComparisonManager {
    private static ComparisonManager instance;
    private List<ComparableVehicle> selectedVehicles;
    private List<ComparisonStateListener> listeners;
    private static final int MAX_VEHICLES = 3;

    public interface ComparisonStateListener {
        void onVehicleAdded(ComparableVehicle vehicle);
        void onVehicleRemoved(ComparableVehicle vehicle);
        void onComparisonListFull();
        void onComparisonListEmpty();
    }

    private ComparisonManager() {
        selectedVehicles = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public static synchronized ComparisonManager getInstance() {
        if (instance == null) {
            instance = new ComparisonManager();
        }
        return instance;
    }

    public void addStateListener(ComparisonStateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeStateListener(ComparisonStateListener listener) {
        listeners.remove(listener);
    }

    public boolean addVehicle(ComparableVehicle vehicle) {
        if (selectedVehicles.size() >= MAX_VEHICLES) {
            notifyComparisonListFull();
            return false;
        }

        if (!selectedVehicles.contains(vehicle)) {
            selectedVehicles.add(vehicle);
            notifyVehicleAdded(vehicle);
            return true;
        }
        return false;
    }

    public void removeVehicle(ComparableVehicle vehicle) {
        if (selectedVehicles.remove(vehicle)) {
            notifyVehicleRemoved(vehicle);
            if (selectedVehicles.isEmpty()) {
                notifyComparisonListEmpty();
            }
        }
    }

    public List<ComparableVehicle> getSelectedVehicles() {
        return new ArrayList<>(selectedVehicles);
    }

    public boolean isVehicleSelected(ComparableVehicle vehicle) {
        return selectedVehicles.contains(vehicle);
    }

    public boolean canAddMore() {
        return selectedVehicles.size() < MAX_VEHICLES;
    }

    public void clearComparison() {
        selectedVehicles.clear();
        notifyComparisonListEmpty();
    }

    private void notifyVehicleAdded(ComparableVehicle vehicle) {
        for (ComparisonStateListener listener : listeners) {
            listener.onVehicleAdded(vehicle);
        }
    }

    private void notifyVehicleRemoved(ComparableVehicle vehicle) {
        for (ComparisonStateListener listener : listeners) {
            listener.onVehicleRemoved(vehicle);
        }
    }

    private void notifyComparisonListFull() {
        for (ComparisonStateListener listener : listeners) {
            listener.onComparisonListFull();
        }
    }

    private void notifyComparisonListEmpty() {
        for (ComparisonStateListener listener : listeners) {
            listener.onComparisonListEmpty();
        }
    }

    // Comparison helper methods
    public static class ComparisonResult {
        public final String label;
        public final List<String> values;
        public final List<Boolean> isBetter;

        public ComparisonResult(String label, List<String> values, List<Boolean> isBetter) {
            this.label = label;
            this.values = values;
            this.isBetter = isBetter;
        }
    }

    public List<ComparisonResult> comparePerformance() {
        List<ComparisonResult> results = new ArrayList<>();
        if (selectedVehicles.size() < 2) return results;

        // Compare horsepower
        results.add(compareMetric("Horsepower",
                vehicle -> vehicle.getHorsepower(),
                value -> value + " hp",
                true)); // Higher is better

        // Compare torque
        results.add(compareMetric("Torque",
                vehicle -> vehicle.getTorque(),
                value -> value + " lb-ft",
                true)); // Higher is better

        // Compare acceleration
        results.add(compareMetric("0-60 mph",
                vehicle -> vehicle.getAcceleration(),
                value -> value + "s",
                false)); // Lower is better

        // Compare fuel efficiency
        results.add(compareMetric("Fuel Efficiency",
                vehicle -> vehicle.getFuelEfficiency(),
                value -> value + " MPG",
                true)); // Higher is better

        return results;
    }

    private ComparisonResult compareMetric(String label,
                                         MetricExtractor extractor,
                                         ValueFormatter formatter,
                                         boolean higherIsBetter) {
        List<String> values = new ArrayList<>();
        List<Boolean> isBetter = new ArrayList<>();
        
        // Get all values
        List<Double> metrics = new ArrayList<>();
        for (ComparableVehicle vehicle : selectedVehicles) {
            double value = extractor.extract(vehicle);
            metrics.add(value);
            values.add(formatter.format(value));
        }

        // Determine which values are better
        double bestValue = higherIsBetter ? 
            findMax(metrics) : findMin(metrics);

        for (double value : metrics) {
            isBetter.add(value == bestValue);
        }

        return new ComparisonResult(label, values, isBetter);
    }

    private double findMax(List<Double> values) {
        double max = Double.MIN_VALUE;
        for (double value : values) {
            if (value > max) max = value;
        }
        return max;
    }

    private double findMin(List<Double> values) {
        double min = Double.MAX_VALUE;
        for (double value : values) {
            if (value < min) min = value;
        }
        return min;
    }

    @FunctionalInterface
    private interface MetricExtractor {
        double extract(ComparableVehicle vehicle);
    }

    @FunctionalInterface
    private interface ValueFormatter {
        String format(double value);
    }
}
