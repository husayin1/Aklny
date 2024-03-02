package com.example.foodfusion.features.home.favorite.presenter;

import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

public interface FavoritePresenterInterface {
    void getFavoriteMeals();

    void removeFromFavorite(PojoMeal meal);

}
