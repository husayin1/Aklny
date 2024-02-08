package com.example.foodfusion.features.home.mealPlan.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder> {
    private Context context;
    private List<PojoPlanner> meal;
    OnDeleteListener listener;
    public static String TAG = "MealPlanAdapter";
    OnDetailsListener _listener;

    public MealPlanAdapter(List<PojoPlanner> meal, Context context,OnDeleteListener listener, OnDetailsListener _listener) {
        this.meal = meal;
        this.context = context;
        this.listener = listener;
        this._listener=_listener;
    }

    public void setList(List<PojoPlanner> updatedMeals) {
        this.meal = updatedMeals;
    }

    @NonNull
    @Override
    public MealPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.planned_meal, parent, false);
        MealPlanAdapter.ViewHolder vh = new MealPlanAdapter.ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder:  Creating FavoriteMeal");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanAdapter.ViewHolder holder, int position) {
        PojoPlanner pojo = meal.get(position);
        holder.textViewPlannedMealName.setText(meal.get(position).strMeal);
        holder.textViewPlannedMealCountry.setText(meal.get(position).strArea);
        Glide.with(context)
                .load(meal.get(position).strMealThumb)
                .centerCrop()
                .placeholder(R.drawable.molokhia)
                .into(holder.imageViewPlannedMeal);
        holder.imageViewRandomMealPlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: FROM MEAL PLAN ADAPTER");
                listener.onClickListener(pojo);
            }
        });
        holder.carView_planned_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: from MEAL PLAN ADAPTER");
                _listener.onClickDetailsListener(pojo);
            }
        });
        Log.i(TAG, "onBindViewHolder:  Binding Rows");

    }

    @Override
    public int getItemCount() {
        return meal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageViewPlannedMeal;
        public TextView textViewPlannedMealName;
        public TextView textViewPlannedMealCountry;
        public ImageView imageViewRandomMealPlanned;

        public CardView carView_planned_meal;
        public View layout2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout2 = itemView;
            carView_planned_meal = itemView.findViewById(R.id.carView_planned_meal);
            imageViewPlannedMeal = (RoundedImageView) itemView.findViewById(R.id.imageViewPlannedMeal);
            textViewPlannedMealName = (TextView) itemView.findViewById(R.id.textViewPlannedMealName);
            textViewPlannedMealCountry = (TextView) itemView.findViewById(R.id.textViewPlannedMealCountry);
            imageViewRandomMealPlanned = (ImageView) itemView.findViewById(R.id.imageViewRandomMealPlanned);
        }
    }

}

