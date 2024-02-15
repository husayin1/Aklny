package com.example.foodfusion.model.repositories.local_repo;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface FavAndPlannerInterface {

    void refreshPlanner();

    void refreshMeals();

    LiveData<List<PojoMeal>> getFavMealsLiveData();

    LiveData<List<PojoPlanner>> getAllMealsFromPlannerAtDate(String date);

    void addToFavorites(PojoMeal meal, OnClickAddListener onClickAddListener);

    void addToPlanner(PojoPlanner planner, OnClickAddListener onClickAddListener);

    void deleteMealFromPlanner(PojoPlanner planner);

    void deleteMealFromFavorites(PojoMeal meal);

    void deleteAllFav();

    void deleteAllWeekPlan();

    LiveData<PojoMeal> getMealFromFavById(String idMeal);

    LiveData<PojoPlanner> getMealFromWeekPlanById(String id);
}
