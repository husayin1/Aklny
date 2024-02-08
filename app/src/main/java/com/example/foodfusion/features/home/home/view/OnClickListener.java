package com.example.foodfusion.features.home.home.view;


import android.view.View;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;

public interface OnClickListener {
    void onClick(PojoMeal meal, View view);
}
