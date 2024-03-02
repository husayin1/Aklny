package com.example.foodfusion.features.home.home.view;


import android.view.View;

import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

public interface OnClickListener {
    void onClick(PojoMeal meal, View view);
}
