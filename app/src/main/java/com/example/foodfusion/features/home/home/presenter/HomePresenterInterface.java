package com.example.foodfusion.features.home.home.presenter;

import com.example.foodfusion.model.local_repo.OnClickAddListener;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

public interface HomePresenterInterface {
    void getRandomMeal();

    void getTrendingMeals();

    void addToFav(PojoMeal meal, OnClickAddListener onClickAddListener);
}
