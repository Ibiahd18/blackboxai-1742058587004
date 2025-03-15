package com.example.autotraderclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autotraderclone.R;
import com.example.autotraderclone.models.Car;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> cars;
    private List<Car> selectedCars;
    private OnCarClickListener listener;

    public interface OnCarClickListener {
        void onCarSelected(Car car);
        void onViewDetailsClicked(Car car);
    }

    public CarAdapter(Context context, List<Car> cars, OnCarClickListener listener) {
        this.context = context;
        this.cars = cars;
        this.listener = listener;
    }

    public void setSelectedCars(List<Car> selectedCars) {
        this.selectedCars = selectedCars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        
        // Set car details
        holder.tvCarTitle.setText(car.getFullName());
        holder.tvHorsepower.setText(car.getHorsepower() + " hp");
        holder.tvMpg.setText(car.getFuelEfficiency() + " MPG");
        holder.tvAcceleration.setText(car.getAcceleration() + "s 0-60");
        
        // Format and set price
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        holder.tvCarPrice.setText(currencyFormat.format(car.getPrice()));

        // Update card selection state
        boolean isSelected = selectedCars != null && selectedCars.contains(car);
        holder.cardView.setStrokeWidth(isSelected ? 4 : 0);
        holder.cardView.setStrokeColor(context.getColor(R.color.primary));

        // Set button states
        holder.btnAddCompare.setText(isSelected ? "Remove" : "Compare");
        holder.btnAddCompare.setOnClickListener(v -> listener.onCarSelected(car));
        holder.btnViewDetails.setOnClickListener(v -> listener.onViewDetailsClicked(car));

        // Set card click listener
        holder.cardView.setOnClickListener(v -> listener.onViewDetailsClicked(car));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView ivCarImage;
        TextView tvCarTitle;
        TextView tvCarPrice;
        TextView tvHorsepower;
        TextView tvMpg;
        TextView tvAcceleration;
        MaterialButton btnViewDetails;
        MaterialButton btnAddCompare;

        CarViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            ivCarImage = itemView.findViewById(R.id.iv_car_image);
            tvCarTitle = itemView.findViewById(R.id.tv_car_title);
            tvCarPrice = itemView.findViewById(R.id.tv_car_price);
            tvHorsepower = itemView.findViewById(R.id.tv_horsepower);
            tvMpg = itemView.findViewById(R.id.tv_mpg);
            tvAcceleration = itemView.findViewById(R.id.tv_acceleration);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
            btnAddCompare = itemView.findViewById(R.id.btn_add_compare);
        }
    }
}
