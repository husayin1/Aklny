package com.example.foodfusion.views.home.favorite.presenter;

import com.example.foodfusion.model.repositories.meal_models.PojoMeal;

public interface FavoritePresenterInterface {
    void getFavoriteMeals();

    void removeFromFavorite(PojoMeal meal);
}
