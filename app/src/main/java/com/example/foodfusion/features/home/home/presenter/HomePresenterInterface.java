package com.example.foodfusion.features.home.home.presenter;

import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenterInterface {
    void getRandomMeal();
    void getTrendingMeals();
//    void geMightLikeMeals();

    void addToFav(PojoMeal meal, OnClickAddListener onClickAddListener);
}
