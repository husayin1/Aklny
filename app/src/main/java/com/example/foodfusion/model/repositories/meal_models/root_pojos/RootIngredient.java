package com.example.foodfusion.model.repositories.meal_models.root_pojos;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredient;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class RootIngredient {
    @SerializedName("meals")
    public List<PojoIngredient> ingredients;

    public List<PojoIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<PojoIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
