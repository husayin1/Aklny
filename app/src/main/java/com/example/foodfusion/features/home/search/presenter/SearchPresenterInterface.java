package com.example.foodfusion.features.home.search.presenter;

import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;

import io.reactivex.rxjava3.core.Single;

public interface SearchPresenterInterface {
    void getIngredients();

    void getCategories();

    void getAreas();

    void getMealByIngredient(String name);

    void getMealByCountry(String name);

    void getMealByCategory(String name);

    void searchMealByName(String name);

    Single<RootMeal> getMealById(String id);

}
