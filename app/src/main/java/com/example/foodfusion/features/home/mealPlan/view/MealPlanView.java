package com.example.foodfusion.features.home.mealPlan.view;

import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface MealPlanView {
    void showData(List<PojoPlanner> meal);

    void deleteMeal(PojoPlanner meal);
}
