package com.example.foodfusion.features.home.mealPlan.view;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface MealPlanView {
    void showData(List<PojoPlanner> meal);

    void deleteMeal(PojoPlanner meal);
}
