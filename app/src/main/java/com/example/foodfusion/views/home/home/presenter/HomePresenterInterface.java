package com.example.foodfusion.views.home.home.presenter;

import com.example.foodfusion.model.repositories.meal_models.PojoMeal;

public interface HomePresenterInterface {
    void getRandomMeals();

    void getCategories();

    void getIngredients();

    void addToFav(PojoMeal meal);
}
