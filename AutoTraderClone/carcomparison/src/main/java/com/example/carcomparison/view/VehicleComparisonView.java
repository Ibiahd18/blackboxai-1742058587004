package com.example.carcomparison.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.carcomparison.ComparisonManager;
import com.example.carcomparison.R;
import com.example.carcomparison.model.ComparableVehicle;
import com.example.carcomparison.util.ComparisonUtils;

import java.util.List;

/**
 * Custom view that displays a comparison table for vehicles.
 * This view can be easily integrated into any layout and handles the visualization
 * of vehicle comparisons.
 */
public class VehicleComparisonView extends LinearLayout {
    private LinearLayout headerContainer;
    private LinearLayout contentContainer;
    private int comparisonType;
    private int betterValueColor;
    private int worseValueColor;
    private int neutralValueColor;

    public VehicleComparisonView(Context context) {
        super(context);
        init(context, null);
    }

    public VehicleComparisonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VehicleComparisonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);

        // Load attributes
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VehicleComparisonView);
            comparisonType = a.getInt(R.styleable.VehicleComparisonView_comparisonType, 
                ComparisonUtils.TYPE_PERFORMANCE);
            betterValueColor = a.getColor(R.styleable.VehicleComparisonView_betterValueColor, 
                ContextCompat.getColor(context, R.color.better_value));
            worseValueColor = a.getColor(R.styleable.VehicleComparisonView_worseValueColor, 
                ContextCompat.getColor(context, R.color.worse_value));
            neutralValueColor = a.getColor(R.styleable.VehicleComparisonView_neutralValueColor, 
                ContextCompat.getColor(context, R.color.neutral_value));
            a.recycle();
        }

        // Initialize containers
        headerContainer = new LinearLayout(context);
        headerContainer.setOrientation(HORIZONTAL);
        addView(headerContainer);

        contentContainer = new LinearLayout(context);
        contentContainer.setOrientation(VERTICAL);
        addView(contentContainer);
    }

    public void setComparisonType(int type) {
        this.comparisonType = type;
        refreshComparison();
    }

    public void refreshComparison() {
        List<ComparableVehicle> vehicles = ComparisonManager.getInstance().getSelectedVehicles();
        if (vehicles.size() < 2) {
            setVisibility(GONE);
            return;
        }

        setVisibility(VISIBLE);
        setupHeader(vehicles);
        setupContent(vehicles);
    }

    private void setupHeader(List<ComparableVehicle> vehicles) {
        headerContainer.removeAllViews();

        // Add label column
        TextView labelHeader = new TextView(getContext());
        labelHeader.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        labelHeader.setText("Specification");
        headerContainer.addView(labelHeader);

        // Add vehicle columns
        for (ComparableVehicle vehicle : vehicles) {
            TextView vehicleHeader = new TextView(getContext());
            vehicleHeader.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
            vehicleHeader.setText(vehicle.getFullName());
            headerContainer.addView(vehicleHeader);
        }
    }

    private void setupContent(List<ComparableVehicle> vehicles) {
        contentContainer.removeAllViews();

        List<ComparisonManager.ComparisonResult> results;
        switch (comparisonType) {
            case ComparisonUtils.TYPE_PERFORMANCE:
                results = ComparisonManager.getInstance().comparePerformance();
                break;
            // Add other comparison types as needed
            default:
                return;
        }

        for (ComparisonManager.ComparisonResult result : results) {
            addComparisonRow(result);
        }
    }

    private void addComparisonRow(ComparisonManager.ComparisonResult result) {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(HORIZONTAL);
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Add label
        TextView labelView = new TextView(getContext());
        labelView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        labelView.setText(result.label);
        row.addView(labelView);

        // Add values
        for (int i = 0; i < result.values.size(); i++) {
            TextView valueView = new TextView(getContext());
            valueView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
            valueView.setText(result.values.get(i));
            valueView.setTextColor(result.isBetter.get(i) ? betterValueColor : worseValueColor);
            row.addView(valueView);
        }

        contentContainer.addView(row);
    }
}
