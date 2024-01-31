package com.example.foodfusion.model.repositories.meal_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootMealPreview {
    @SerializedName("meals")
    public List<PojoMealPreview> mealPreviews;
}
