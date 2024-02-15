package com.example.foodfusion.localDataSource;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

@Database(entities = {PojoMeal.class, PojoPlanner.class}, version = 1, exportSchema = false)
public abstract class MealDataBase extends RoomDatabase {
    private static MealDataBase instance = null;

    public abstract MealDAO getMealDao();

    public static synchronized MealDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MealDataBase.class, "mealsdb").build();
        }
        return instance;
    }
}
