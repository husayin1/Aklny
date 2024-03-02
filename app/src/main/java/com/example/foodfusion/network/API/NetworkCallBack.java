package com.example.foodfusion.network.API;

import com.example.foodfusion.model.meal_models.pojos.PojoCategory;
import com.example.foodfusion.model.meal_models.pojos.PojoIngredient;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

import java.util.List;

public interface NetworkCallBack {
    void onSuccessRandom(List<PojoMeal> meals);

    void onFailureRandom(String errorMsg);

    void onSuccessCategory(List<PojoCategory> categories);

    void onFailureCategory(String errorMsg);

    void onSuccessIngredients(List<PojoIngredient> ingredients);

    void onFailureIngredients(String errorMsg);


}
