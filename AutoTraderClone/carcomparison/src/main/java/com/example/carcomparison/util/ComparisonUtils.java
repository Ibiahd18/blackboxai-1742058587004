package com.example.carcomparison.util;

import android.content.Context;
import android.content.Intent;

import com.example.carcomparison.model.ComparableVehicle;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Utility class providing helper methods for the comparison module.
 */
public class ComparisonUtils {
    public static final String EXTRA_VEHICLE_IDS = "vehicle_ids";
    public static final String EXTRA_COMPARISON_TYPE = "comparison_type";
    
    public static final int TYPE_PERFORMANCE = 0;
    public static final int TYPE_FEATURES = 1;
    public static final int TYPE_SAFETY = 2;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private static final NumberFormat decimalFormat = NumberFormat.getNumberInstance(Locale.US);

    static {
        decimalFormat.setMaximumFractionDigits(1);
        decimalFormat.setMinimumFractionDigits(1);
    }

    /**
     * Formats price value to currency string
     */
    public static String formatPrice(double price) {
        return currencyFormat.format(price);
    }

    /**
     * Formats decimal values with one decimal place
     */
    public static String formatDecimal(double value) {
        return decimalFormat.format(value);
    }

    /**
     * Creates a shareable comparison text
     */
    public static String createComparisonText(List<ComparableVehicle> vehicles) {
        StringBuilder comparison = new StringBuilder("Vehicle Comparison\n\n");

        for (ComparableVehicle vehicle : vehicles) {
            comparison.append(vehicle.getFullName()).append("\n");
            comparison.append("Price: ").append(formatPrice(vehicle.getPrice())).append("\n");
            comparison.append("Performance:\n");
            comparison.append("• Horsepower: ").append(vehicle.getHorsepower()).append(" hp\n");
            comparison.append("• Torque: ").append(vehicle.getTorque()).append(" lb-ft\n");
            comparison.append("• 0-60 mph: ").append(formatDecimal(vehicle.getAcceleration())).append("s\n");
            comparison.append("• Fuel Efficiency: ").append(formatDecimal(vehicle.getFuelEfficiency())).append(" MPG\n");
            
            comparison.append("\nFeatures:\n");
            for (String feature : vehicle.getFeatures()) {
                comparison.append("• ").append(feature).append("\n");
            }
            
            comparison.append("\nSafety Features:\n");
            for (String feature : vehicle.getSafetyFeatures()) {
                comparison.append("• ").append(feature).append("\n");
            }
            
            comparison.append("\n-------------------\n\n");
        }

        return comparison.toString();
    }

    /**
     * Creates an intent to share comparison results
     */
    public static Intent createShareIntent(List<ComparableVehicle> vehicles) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, createComparisonText(vehicles));
        return shareIntent;
    }

    /**
     * Converts vehicle IDs to ArrayList for intent extras
     */
    public static ArrayList<String> vehicleIdsToArrayList(List<ComparableVehicle> vehicles) {
        ArrayList<String> ids = new ArrayList<>();
        for (ComparableVehicle vehicle : vehicles) {
            ids.add(vehicle.getId());
        }
        return ids;
    }

    /**
     * Determines if a value is the best among a list of values
     */
    public static boolean isBestValue(double value, List<Double> allValues, boolean higherIsBetter) {
        if (allValues == null || allValues.isEmpty()) return false;
        
        double bestValue = higherIsBetter ? 
            findMax(allValues) : findMin(allValues);
        
        return value == bestValue;
    }

    private static double findMax(List<Double> values) {
        double max = Double.MIN_VALUE;
        for (double value : values) {
            if (value > max) max = value;
        }
        return max;
    }

    private static double findMin(List<Double> values) {
        double min = Double.MAX_VALUE;
        for (double value : values) {
            if (value < min) min = value;
        }
        return min;
    }
}
