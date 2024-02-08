package com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface RealTimePlannerDelegation {

    public void onSuccess(List<PojoPlanner> planner);

    public void onFailure(String message);
}
