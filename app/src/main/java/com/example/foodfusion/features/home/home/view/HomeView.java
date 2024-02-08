package com.example.foodfusion.features.home.home.view;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;

import java.util.ArrayList;

public interface HomeView {
    void showRandomData(PojoMeal meals);
    void showTrendingMeals(ArrayList<PojoMeal> meals);
//    void geMightLikeMeals(ArrayList<PojoMeal> meals);

//    void showRandomErrorMsg(String error);

//    void showCategoriesData(List<PojoCategory> categories);

//    void showCategoriesErrorMsg(String error);

//    void showIngredientsData(List<PojoIngredient> meals);

//    void showIngredientsErrorMsg(String error);
}
