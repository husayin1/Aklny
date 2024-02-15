package com.example.foodfusion.features.home.home.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<PojoMeal> meals;
    OnClickListener clickListener;
    Context context;

    public HomeAdapter(Context _context, List<PojoMeal> meals, OnClickListener onClickListener) {
        this.meals = meals;
        this.context = _context;
        clickListener = onClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.home_meal, parent, false);
        Log.i("TAG", "onCreateViewHolder:   Creating Home Meal");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(meals.get(position).strMeal);
        Glide.with(holder.getView().getContext()).load(meals.get(position).strMealThumb).placeholder(R.drawable.molokhia).into(holder.getImageView());
        holder.getView().setOnClickListener(v -> {
            clickListener.onClick(meals.get(position), holder.view);
            Log.i("TAG", "onBindViewHolder: " + position);
        });
    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + meals.size());
        return meals.size() - 1;
    }

    public void setMeals(List<PojoMeal> meals) {
        this.meals = meals;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imageViewHomeMeal);
            textViewName = itemView.findViewById(R.id.textViewHomeMealName);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public View getView() {
            return view;
        }


    }
}
