package com.example.autotraderclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autotraderclone.adapters.CarAdapter;
import com.example.autotraderclone.models.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CarListingActivity extends AppCompatActivity implements CarAdapter.OnCarClickListener {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> selectedCars;
    private FloatingActionButton fabCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_listing);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_cars);
        fabCompare = findViewById(R.id.fab_compare);

        // Initialize selected cars list
        selectedCars = new ArrayList<>();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup FAB
        setupFloatingActionButton();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(this, Car.getSampleCars(), this);
        recyclerView.setAdapter(carAdapter);
    }

    private void setupFloatingActionButton() {
        fabCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCars.size() >= 2) {
                    // Start comparison activity with selected cars
                    Intent intent = new Intent(CarListingActivity.this, CarComparisonActivity.class);
                    // Add selected car IDs to intent
                    ArrayList<String> selectedCarIds = new ArrayList<>();
                    for (Car car : selectedCars) {
                        selectedCarIds.add(car.getId());
                    }
                    intent.putStringArrayListExtra("selected_car_ids", selectedCarIds);
                    startActivity(intent);
                }
            }
        });
        updateFabVisibility();
    }

    private void updateFabVisibility() {
        fabCompare.setVisibility(selectedCars.size() >= 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCarSelected(Car car) {
        // Handle car selection for comparison
        if (selectedCars.contains(car)) {
            selectedCars.remove(car);
        } else {
            selectedCars.add(car);
        }
        carAdapter.setSelectedCars(selectedCars);
        updateFabVisibility();
    }

    @Override
    public void onViewDetailsClicked(Car car) {
        // Start CarDetailActivity
        Intent intent = new Intent(this, CarDetailActivity.class);
        intent.putExtra("car_id", car.getId());
        startActivity(intent);
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
