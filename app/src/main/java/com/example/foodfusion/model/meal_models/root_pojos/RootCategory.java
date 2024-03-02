package com.example.foodfusion.model.meal_models.root_pojos;

import com.example.foodfusion.model.meal_models.pojos.PojoCategory;

import java.util.ArrayList;

public class RootCategory {
    public ArrayList<PojoCategory> categories;

    public RootCategory() {
    }

    public RootCategory(ArrayList<PojoCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<PojoCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<PojoCategory> categories) {
        this.categories = categories;
    }
}
