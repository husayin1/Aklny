package com.example.foodfusion.network.remoteFireBaes.realdatabase;

import com.example.foodfusion.model.meal_models.pojos.PojoMeal;

import java.util.List;

public interface RealTimeFavoriteDelegation {

    public void onSuccess(List<PojoMeal> meals);

    public void onFailure(String message);
}
