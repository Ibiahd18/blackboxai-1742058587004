package com.example.autotraderclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private MaterialButton btnBrowseCars;
    private MaterialButton btnCompareCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnBrowseCars = findViewById(R.id.btn_browse_cars);
        btnCompareCars = findViewById(R.id.btn_compare_cars);

        // Setup bottom navigation
        bottomNavigationView.setOnItemSelectedListener(this);

        // Setup button click listeners
        setupClickListeners();
    }

    private void setupClickListeners() {
        btnBrowseCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CarListingActivity.class));
            }
        });

        btnCompareCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CarComparisonActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                // Already on home
                return true;
            case R.id.nav_browse:
                startActivity(new Intent(this, CarListingActivity.class));
                return true;
            case R.id.nav_compare:
                startActivity(new Intent(this, CarComparisonActivity.class));
                return true;
            case R.id.nav_profile:
                // Profile functionality to be implemented
                return true;
        }
        return false;
    }
}
