package com.example.foodfusion.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

import java.util.List;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFavorite(PojoMeal meal);
    @Query("SELECT * From meals_table")
    LiveData<List<PojoMeal>> getAllFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllMealsToFavorite(List<PojoMeal> meals);
    @Query("SELECT * FROM meals_table where idMeal =:idMeal")
    LiveData<PojoMeal> getFavMealById(String idMeal);
    @Delete
    void deleteMealFromFavorite(PojoMeal meal);

    @Query("delete From meals_table")
    void deleteAllMealsFromFavorite();

    @Insert(onConflict =OnConflictStrategy.IGNORE)
    void insertMealToPlanner(PojoPlanner meal);
    @Query("SELECT * FROM planner where date=:date")
    LiveData<List<PojoPlanner>> getAllPlannerMealsAtDate(String date);
    @Query("SELECT * FROM planner")
    LiveData<List<PojoPlanner>> getAllPlannerMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllMealsToPlanner(List<PojoPlanner> planners);
    @Query("SELECT * FROM planner where id=:id")
    LiveData<PojoPlanner> getPlannerMealById(String id);
    @Delete
    void deleteMealFromPlanner(PojoPlanner planner);
    @Query("delete from planner")
    void deleteAllPlannerMeals();




}
