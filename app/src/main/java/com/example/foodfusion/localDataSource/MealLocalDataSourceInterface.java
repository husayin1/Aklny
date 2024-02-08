package com.example.foodfusion.localDataSource;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;


public interface MealLocalDataSourceInterface {

    void insertMealToFav(PojoMeal meal);
    void deleteMealFromFav(PojoMeal meal);
    LiveData<List<PojoMeal>> getAllStoredMeals();
    LiveData<List<PojoPlanner>> getAllPlannedMeals(String date);
    void insertMealToPlanner(PojoPlanner meal);
    void deleteMealFromPlanner(PojoPlanner meal);
}
