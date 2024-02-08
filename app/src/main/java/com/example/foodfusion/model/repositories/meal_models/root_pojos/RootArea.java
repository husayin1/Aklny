package com.example.foodfusion.model.repositories.meal_models.root_pojos;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoArea;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootArea {
    @SerializedName("meals")
    public List<PojoArea> areas;

    public RootArea() {

    }

    public List<PojoArea> getAreas() {
        return areas;
    }

    public void setAreas(List<PojoArea> areas) {
        this.areas = areas;
    }
}
