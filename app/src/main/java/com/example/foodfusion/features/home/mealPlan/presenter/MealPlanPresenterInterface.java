package com.example.foodfusion.features.home.mealPlan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface MealPlanPresenterInterface {

    void getPlannedMealsByDate(String date);
    void  removeItemFromPlanner(PojoPlanner meal);

}
