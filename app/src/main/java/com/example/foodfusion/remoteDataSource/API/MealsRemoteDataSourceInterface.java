package com.example.foodfusion.remoteDataSource.API;

import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSourceInterface {

    Single<RootMeal> getRandomMeal();

    Single<RootMeal> getTrendingMeals();

    Single<RootIngredient> getIngredients();

    Single<RootCategory> getCategories();

    Single<RootArea> getAreas();

    Single<RootMainMeal> getMealsByIngredient(String ingredient);

    Single<RootMainMeal> getMealsByCategory(String category);

    Single<RootMainMeal> getMealsByArea(String country);

    Single<RootMeal> searchMealByName(String name);

    Single<RootMeal> getMealById(String id);
}
