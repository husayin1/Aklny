package com.example.foodfusion.model.repositories.local_repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.localDataSource.MealDataBase;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase.RealTimeData;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase.RealTimeFavoriteDelegation;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase.RealTimeInsertDelegation;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase.RealTimePlannerDelegation;

import java.util.List;

public class FavAndPlannerRepo implements FavAndPlannerInterface {
    private final static String TAG = "FavPlannerRepo";
    private MealDataBase dataBase;
    private static RealTimeData realTimeData;
    private static FavAndPlannerRepo repository;

    private FavAndPlannerRepo(Context context) {
        dataBase = MealDataBase.getInstance(context);
    }

    public static synchronized FavAndPlannerRepo getInstance(Context context) {
        realTimeData = new RealTimeData();
        if (repository == null) {
            repository = new FavAndPlannerRepo(context);
        }
        return repository;
    }

    @Override
    public void refreshPlanner() {
        realTimeData.getWeekPlanner(new RealTimePlannerDelegation() {
            @Override
            public void onSuccess(List<PojoPlanner> planner) {
                new Thread(() -> {
                    dataBase.getMealDao().insertAllMealsToPlanner(planner);
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public void refreshMeals() {
        realTimeData.getFavoriteMeals(new RealTimeFavoriteDelegation() {
            @Override
            public void onSuccess(List<PojoMeal> meals) {
                new Thread(() -> {
                    new Thread(() -> dataBase.getMealDao().insertAllMealsToFavorite(meals)).start();
                    Log.d(TAG, "onSuccess: meals");
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public LiveData<List<PojoMeal>> getFavMealsLiveData() {
        return dataBase.getMealDao().getAllFavMeals();
    }

    @Override
    public LiveData<List<PojoPlanner>> getAllMealsFromPlannerAtDate(String date) {
        return dataBase.getMealDao().getAllPlannerMealsAtDate(date);

    }

    @Override
    public void addToFavorites(PojoMeal meal, OnClickAddListener onClickAddListener) {
        realTimeData.addFavMeal(meal, new RealTimeInsertDelegation() {
            @Override
            public void onSuccess() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataBase.getMealDao().insertMealToFavorite(meal);

                    }
                }).start();
                onClickAddListener.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                onClickAddListener.onFailure(message);
            }
        });
    }

    @Override
    public void addToPlanner(PojoPlanner planner, OnClickAddListener onClickAddListener) {
        realTimeData.addPlannerMeal(planner, new RealTimeInsertDelegation() {
            @Override
            public void onSuccess() {
                new Thread(() -> dataBase.getMealDao().insertMealToPlanner(planner)).start();
                onClickAddListener.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                onClickAddListener.onFailure(message);
            }
        });
    }

    @Override
    public void deleteMealFromPlanner(PojoPlanner planner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                realTimeData.deletePlannedMeal(planner.id, ()->dataBase.getMealDao().deleteMealFromPlanner(planner));
            }
        }).start();
    }

    @Override
    public void deleteMealFromFavorites(PojoMeal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                realTimeData.deleteFavMeal(meal.idMeal, () -> dataBase.getMealDao().deleteMealFromFavorite(meal));
            }
        }).start();

    }

    @Override
    public void deleteAllFav() {
        new Thread(() -> {
            dataBase.getMealDao().deleteAllMealsFromFavorite();
        }).start();
    }

    @Override
    public void deleteAllWeekPlan() {
        new Thread(() -> {
            dataBase.getMealDao().deleteAllPlannerMeals();
        }).start();
    }

    @Override
    public LiveData<PojoMeal> getMealFromFavById(String idMeal) {
        return dataBase.getMealDao().getFavMealById(idMeal);
    }

    @Override
    public LiveData<PojoPlanner> getMealFromWeekPlanById(String id) {
        return dataBase.getMealDao().getPlannerMealById(id);
    }
}
