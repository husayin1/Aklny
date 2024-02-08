package com.example.foodfusion.features.home.favorite.view;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;

import java.util.List;

public interface FavoriteView {
    void showData(LiveData<List<PojoMeal>> meal);

    void showErrorMsg(String error);

    void deleteMeal(PojoMeal meal);
}
