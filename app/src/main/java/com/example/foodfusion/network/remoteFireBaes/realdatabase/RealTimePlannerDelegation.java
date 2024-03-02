package com.example.foodfusion.network.remoteFireBaes.realdatabase;

import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;

import java.util.List;

public interface RealTimePlannerDelegation {

    public void onSuccess(List<PojoPlanner> planner);

    public void onFailure(String message);
}
