package com.example.foodfusion.features.home.meal_details.view;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredientWithMeasure;

public interface OnIngredientClickListener {
    void onIngredientClickListener(PojoIngredientWithMeasure ingredient);
}
