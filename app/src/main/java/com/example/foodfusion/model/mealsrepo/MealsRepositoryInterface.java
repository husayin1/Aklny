package com.example.foodfusion.model.mealsrepo;

import com.example.foodfusion.model.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.meal_models.root_pojos.RootMainMeal;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepositoryInterface {
    Single<RootMeal> getRandomMeal();

    Single<RootIngredient> getRootIngredients();

    Single<RootCategory> getRootCategories();

    Single<RootArea> getRootAreas();

    Single<RootMainMeal> getRootMealByIngredient(String name);

    Single<RootMainMeal> getRootMealByCategory(String name);

    Single<RootMainMeal> getRootMealByCountry(String name);

    Single<RootMeal> getTrendingMeals();

    Single<RootMeal> searchMealByName(String name);

    Single<RootMeal> getMealById(String id);
}
