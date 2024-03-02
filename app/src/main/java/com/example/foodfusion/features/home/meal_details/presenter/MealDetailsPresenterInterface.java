package com.example.foodfusion.features.home.meal_details.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.local_repo.OnClickAddListener;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;

public interface MealDetailsPresenterInterface {
    void addToFav(PojoMeal meal, OnClickAddListener onClickAddListener);

    void addToPlanner(PojoPlanner meal, OnClickAddListener onClickAddListener);

    public LiveData<PojoMeal> getFavMealById(String id);

    void removeFromFavorite(PojoMeal meal);

}
