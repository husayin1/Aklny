package com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;

import java.util.List;

public interface RealTimeFavoriteDelegation {

    public void onSuccess(List<PojoMeal> meals);

    public void onFailure(String message);
}
