package com.example.foodfusion.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;

import java.util.List;

public class MealLocalDataSource implements MealLocalDataSourceInterface {
    private MealDAO dao;
    private static MealLocalDataSource instance = null;
    private LiveData<List<PojoMeal>> storedMeals;

    private MealLocalDataSource(Context context) {
        MealDataBase db = MealDataBase.getInstance(context.getApplicationContext());
        dao = db.getMealDao();
        storedMeals = dao.getAllFavMeals();
    }

    public static MealLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MealLocalDataSource(context);
        }
        return instance;
    }


    @Override
    public void insertMealToFav(PojoMeal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMealToFavorite(meal);
            }
        }).start();
    }

    @Override
    public void deleteMealFromFav(PojoMeal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMealFromFavorite(meal);
            }
        }).start();

    }

    @Override
    public LiveData<List<PojoMeal>> getAllStoredMeals() {
        return storedMeals;
    }

    @Override
    public LiveData<List<PojoPlanner>> getAllPlannedMeals(String date) {
        return dao.getAllPlannerMealsAtDate(date);
    }


    @Override
    public void insertMealToPlanner(PojoPlanner meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMealToPlanner(meal);
            }
        }).start();
    }

    @Override
    public void deleteMealFromPlanner(PojoPlanner meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMealFromPlanner(meal);
            }
        }).start();
    }
}
