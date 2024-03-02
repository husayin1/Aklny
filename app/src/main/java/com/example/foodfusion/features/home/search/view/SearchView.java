package com.example.foodfusion.features.home.search.view;

import com.example.foodfusion.model.meal_models.pojos.PojoMainMeal;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.meal_models.root_pojos.RootIngredient;

import java.util.List;

public interface SearchView {

    void showIngredientsData(RootIngredient ingredients);

    void showCategoriesData(RootCategory categories);

    void showCountriesData(RootArea areas);

    void showSearchedMealData(List<PojoMeal> meals);

    void showSearchResultData(List<PojoMainMeal> mainMeal);

}
