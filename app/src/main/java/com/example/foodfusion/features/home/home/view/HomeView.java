package com.example.foodfusion.features.home.home.view;

import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

import java.util.ArrayList;

public interface HomeView {
    void showRandomData(PojoMeal meals);

    void showTrendingMeals(ArrayList<PojoMeal> meals);

}
