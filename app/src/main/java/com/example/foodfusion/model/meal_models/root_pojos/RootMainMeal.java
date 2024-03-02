package com.example.foodfusion.model.meal_models.root_pojos;

import com.example.foodfusion.model.meal_models.pojos.PojoMainMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootMainMeal {
    @SerializedName("meals")
    public List<PojoMainMeal> mainMeal;
}
