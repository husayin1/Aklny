package com.example.foodfusion.views.home.favorite.view;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.PojoMeal;

import java.util.List;

public interface FavoriteView {
    void showData(LiveData<List<PojoMeal>> meal);

    void showErrorMsg(String error);

    void deleteMeal(PojoMeal meal);
}
