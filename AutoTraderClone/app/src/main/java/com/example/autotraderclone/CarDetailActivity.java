package com.example.autotraderclone;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.autotraderclone.models.Car;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarDetailActivity extends AppCompatActivity {

    private TextView tvCarName;
    private TextView tvCarPrice;
    private TextView tvHorsepower;
    private TextView tvTorque;
    private TextView tvAcceleration;
    private TextView tvFuelEfficiency;
    private LinearLayout layoutFeatures;
    private LinearLayout layoutSafetyFeatures;
    private ExtendedFloatingActionButton fabAddToComparison;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        initializeViews();

        // Get car data
        String carId = getIntent().getStringExtra("car_id");
        loadCarDetails(carId);

        // Setup comparison button
        setupComparisonButton();
    }

    private void initializeViews() {
        tvCarName = findViewById(R.id.tv_car_name);
        tvCarPrice = findViewById(R.id.tv_car_price);
        tvHorsepower = findViewById(R.id.tv_horsepower);
        tvTorque = findViewById(R.id.tv_torque);
        tvAcceleration = findViewById(R.id.tv_acceleration);
        tvFuelEfficiency = findViewById(R.id.tv_fuel_efficiency);
        layoutFeatures = findViewById(R.id.layout_features);
        layoutSafetyFeatures = findViewById(R.id.layout_safety_features);
        fabAddToComparison = findViewById(R.id.fab_add_to_comparison);
    }

    private void loadCarDetails(String carId) {
        // Find car from sample data
        List<Car> cars = Car.getSampleCars();
        for (Car c : cars) {
            if (c.getId().equals(carId)) {
                car = c;
                break;
            }
        }

        if (car != null) {
            displayCarDetails();
        }
    }

    private void displayCarDetails() {
        // Set basic info
        tvCarName.setText(car.getFullName());
        tvCarPrice.setText(NumberFormat.getCurrencyInstance(Locale.US).format(car.getPrice()));

        // Set performance metrics
        tvHorsepower.setText(String.format("Horsepower: %d hp", car.getHorsepower()));
        tvTorque.setText(String.format("Torque: %d lb-ft", car.getTorque()));
        tvAcceleration.setText(String.format("0-60 mph: %.1fs", car.getAcceleration()));
        tvFuelEfficiency.setText(String.format("Fuel Efficiency: %.1f MPG", car.getFuelEfficiency()));

        // Add features
        layoutFeatures.removeAllViews();
        for (String feature : car.getFeatures()) {
            TextView featureView = new TextView(this);
            featureView.setText(String.format("• %s", feature));
            featureView.setTextAppearance(R.style.TextAppearance_CarSpec);
            featureView.setPadding(0, 4, 0, 4);
            layoutFeatures.addView(featureView);
        }

        // Add safety features
        layoutSafetyFeatures.removeAllViews();
        for (String feature : car.getSafetyFeatures()) {
            TextView featureView = new TextView(this);
            featureView.setText(String.format("• %s", feature));
            featureView.setTextAppearance(R.style.TextAppearance_CarSpec);
            featureView.setPadding(0, 4, 0, 4);
            layoutSafetyFeatures.addView(featureView);
        }
    }

    private void setupComparisonButton() {
        fabAddToComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In a real app, this would add the car to a comparison list
                // For now, just show a confirmation message
                Snackbar.make(v, "Added to comparison", Snackbar.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
