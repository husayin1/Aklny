package com.example.foodfusion.views.home.home.view;

import com.example.foodfusion.model.repositories.meal_models.PojoCategory;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredient;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.RootMeal;

import java.util.List;

public interface HomeView {
    void showRandomData(List<PojoMeal> meals);

    void showRandomErrorMsg(String error);

    void showCategoriesData(List<PojoCategory> categories);

    void showCategoriesErrorMsg(String error);

    void showIngredientsData(List<PojoIngredient> meals);

    void showIngredientsErrorMsg(String error);
}
