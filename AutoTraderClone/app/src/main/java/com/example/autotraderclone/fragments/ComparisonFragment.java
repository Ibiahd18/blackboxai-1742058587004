package com.example.autotraderclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autotraderclone.R;
import com.example.autotraderclone.models.Car;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComparisonFragment extends Fragment {

    private static final int TYPE_PERFORMANCE = 0;
    private static final int TYPE_FEATURES = 1;
    private static final int TYPE_SAFETY = 2;

    private LinearLayout layoutCarColumns;
    private LinearLayout layoutComparisonTable;
    private View templateComparisonRow;
    private View templateCarColumn;
    private TextView templateSpecValue;
    private List<Car> carsToCompare;
    private int comparisonType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comparison, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        layoutCarColumns = view.findViewById(R.id.layout_car_columns);
        layoutComparisonTable = view.findViewById(R.id.layout_comparison_table);
        templateComparisonRow = view.findViewById(R.id.template_comparison_row);
        templateCarColumn = view.findViewById(R.id.template_car_column);
        templateSpecValue = view.findViewById(R.id.template_spec_value);

        // Get arguments
        Bundle args = getArguments();
        if (args != null) {
            comparisonType = args.getInt("comparison_type", TYPE_PERFORMANCE);
            ArrayList<String> carIds = args.getStringArrayList("car_ids");
            loadCars(carIds);
        }

        // Setup comparison view
        setupComparison();
    }

    private void loadCars(ArrayList<String> carIds) {
        carsToCompare = new ArrayList<>();
        List<Car> allCars = Car.getSampleCars();
        
        for (String id : carIds) {
            for (Car car : allCars) {
                if (car.getId().equals(id)) {
                    carsToCompare.add(car);
                    break;
                }
            }
        }
    }

    private void setupComparison() {
        // Add car columns
        setupCarColumns();

        // Add comparison rows based on type
        switch (comparisonType) {
            case TYPE_PERFORMANCE:
                setupPerformanceComparison();
                break;
            case TYPE_FEATURES:
                setupFeaturesComparison();
                break;
            case TYPE_SAFETY:
                setupSafetyComparison();
                break;
        }
    }

    private void setupCarColumns() {
        layoutCarColumns.removeAllViews();
        
        for (Car car : carsToCompare) {
            View carColumn = getLayoutInflater().inflate(R.layout.fragment_comparison, null)
                    .findViewById(R.id.template_car_column);
            
            TextView carName = carColumn.findViewById(R.id.tv_car_name);
            TextView carPrice = carColumn.findViewById(R.id.tv_car_price);
            
            carName.setText(car.getFullName());
            carPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(car.getPrice()));
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            carColumn.setLayoutParams(params);
            
            layoutCarColumns.addView(carColumn);
        }
    }

    private void setupPerformanceComparison() {
        addComparisonRow("Horsepower", car -> String.format("%d hp", car.getHorsepower()));
        addComparisonRow("0-60 mph", car -> String.format("%.1fs", car.getAcceleration()));
        addComparisonRow("Torque", car -> String.format("%d lb-ft", car.getTorque()));
        addComparisonRow("Fuel Efficiency", car -> String.format("%.1f MPG", car.getFuelEfficiency()));
    }

    private void setupFeaturesComparison() {
        for (Car car : carsToCompare) {
            List<String> allFeatures = car.getFeatures();
            for (String feature : allFeatures) {
                addComparisonRow(feature, c -> c.getFeatures().contains(feature) ? "✓" : "✗");
            }
        }
    }

    private void setupSafetyComparison() {
        for (Car car : carsToCompare) {
            List<String> allSafetyFeatures = car.getSafetyFeatures();
            for (String feature : allSafetyFeatures) {
                addComparisonRow(feature, c -> c.getSafetyFeatures().contains(feature) ? "✓" : "✗");
            }
        }
    }

    private void addComparisonRow(String label, ValueFormatter formatter) {
        View row = getLayoutInflater().inflate(R.layout.fragment_comparison, null)
                .findViewById(R.id.template_comparison_row);
        
        TextView labelView = row.findViewById(R.id.tv_spec_label);
        LinearLayout valuesContainer = row.findViewById(R.id.layout_spec_values);
        
        labelView.setText(label);
        
        for (Car car : carsToCompare) {
            TextView valueView = (TextView) getLayoutInflater().inflate(R.layout.fragment_comparison, null)
                    .findViewById(R.id.template_spec_value);
            
            valueView.setText(formatter.format(car));
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            valueView.setLayoutParams(params);
            
            valuesContainer.addView(valueView);
        }
        
        layoutComparisonTable.addView(row);
    }

    private interface ValueFormatter {
        String format(Car car);
    }
}
