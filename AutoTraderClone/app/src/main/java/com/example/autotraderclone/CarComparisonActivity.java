package com.example.autotraderclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.autotraderclone.fragments.ComparisonFragment;
import com.example.autotraderclone.models.Car;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CarComparisonActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ExtendedFloatingActionButton fabShare;
    private List<Car> carsToCompare;

    private static final int PERFORMANCE_PAGE = 0;
    private static final int FEATURES_PAGE = 1;
    private static final int SAFETY_PAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_comparison);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get selected cars
        ArrayList<String> selectedCarIds = getIntent().getStringArrayListExtra("selected_car_ids");
        loadSelectedCars(selectedCarIds);

        // Initialize views
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        fabShare = findViewById(R.id.fab_share);

        // Setup ViewPager
        setupViewPager();

        // Setup share button
        setupShareButton();
    }

    private void loadSelectedCars(ArrayList<String> selectedCarIds) {
        carsToCompare = new ArrayList<>();
        List<Car> allCars = Car.getSampleCars();
        
        for (String id : selectedCarIds) {
            for (Car car : allCars) {
                if (car.getId().equals(id)) {
                    carsToCompare.add(car);
                    break;
                }
            }
        }
    }

    private void setupViewPager() {
        ComparisonPagerAdapter pagerAdapter = new ComparisonPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case PERFORMANCE_PAGE:
                    tab.setText(R.string.performance);
                    break;
                case FEATURES_PAGE:
                    tab.setText(R.string.features);
                    break;
                case SAFETY_PAGE:
                    tab.setText(R.string.safety);
                    break;
            }
        }).attach();
    }

    private void setupShareButton() {
        fabShare.setOnClickListener(v -> shareComparison());
    }

    private void shareComparison() {
        StringBuilder comparison = new StringBuilder();
        comparison.append("Car Comparison:\n\n");

        for (Car car : carsToCompare) {
            comparison.append(car.getFullName()).append("\n");
            comparison.append("Price: $").append(String.format("%,.2f", car.getPrice())).append("\n");
            comparison.append("Horsepower: ").append(car.getHorsepower()).append(" hp\n");
            comparison.append("0-60 mph: ").append(car.getAcceleration()).append("s\n");
            comparison.append("Fuel Efficiency: ").append(car.getFuelEfficiency()).append(" MPG\n\n");
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, comparison.toString());
        startActivity(Intent.createChooser(shareIntent, "Share Comparison"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ComparisonPagerAdapter extends FragmentStateAdapter {
        public ComparisonPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            ComparisonFragment fragment = new ComparisonFragment();
            Bundle args = new Bundle();
            args.putInt("comparison_type", position);
            ArrayList<String> carIds = new ArrayList<>();
            for (Car car : carsToCompare) {
                carIds.add(car.getId());
            }
            args.putStringArrayList("car_ids", carIds);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 3; // Performance, Features, Safety
        }
    }
}
