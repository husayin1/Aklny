package com.example.foodfusion.model.repositories.meal_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootArea {
    @SerializedName("meals")
    public List<PojoArea> areas;

    public RootArea() {

    }

    public List<PojoArea> getCuisines() {
        return areas;
    }

    public void setCuisines(List<PojoArea> cuisines) {
        this.areas = cuisines;
    }
}
