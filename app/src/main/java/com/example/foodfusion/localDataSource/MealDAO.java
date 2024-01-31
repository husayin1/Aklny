package com.example.foodfusion.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.RootMeal;

import java.util.List;

@Dao
public interface MealDAO {
    @Query("SELECT * From meals_table")
    LiveData<List<PojoMeal>> getAllFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFavorite(PojoMeal meal);
    @Delete
    void deleteMealFromFavorite(PojoMeal meal);

}
