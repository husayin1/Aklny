package com.example.foodfusion.features.home.home.view;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;

import java.util.ArrayList;

public interface HomeView {
    void showRandomData(PojoMeal meals);

    void showTrendingMeals(ArrayList<PojoMeal> meals);

}
