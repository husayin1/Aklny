package com.example.foodfusion.localDataSource;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.RootMeal;

import java.util.List;


public interface MealLocalDataSourceInterface {

    void insertMealToFav(PojoMeal meal);
    void deleteMealFromFav(PojoMeal meal);
    LiveData<List<PojoMeal>> getAllStoredMeals();
}
