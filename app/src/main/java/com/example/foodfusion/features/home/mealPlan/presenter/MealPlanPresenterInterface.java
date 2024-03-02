package com.example.foodfusion.features.home.mealPlan.presenter;

import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;

public interface MealPlanPresenterInterface {

    void getPlannedMealsByDate(String date);
    void  removeItemFromPlanner(PojoPlanner meal);

}
