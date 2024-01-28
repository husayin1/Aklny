package com.example.foodfusion.remoteDataSource.API;

import com.example.foodfusion.model.repositories.meal_models.PojoCategory;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredient;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.RootMeal;

import java.util.ArrayList;
import java.util.List;

public interface NetworkCallBack {
    void onSuccessRandom(List<PojoMeal> meals);
    void onFailureRandom(String errorMsg);
    void onSuccessCategory(List<PojoCategory> categories);
    void onFailureCategory(String errorMsg);
    void onSuccessIngredients(List<PojoIngredient> ingredients);
    void onFailureIngredients(String errorMsg);



}
