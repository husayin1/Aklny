package com.example.foodfusion.remoteDataSource.API;

import com.example.foodfusion.model.repositories.meal_models.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.RootArea;
import com.example.foodfusion.model.repositories.meal_models.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.RootMeal;
import com.example.foodfusion.model.repositories.meal_models.RootMealPreview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Call<RootMeal> getRandomMeal();

    @GET("categories.php")
    Call<RootCategory> getCategories();

    @GET("list.php?i=list")
    Call<RootIngredient> getIngredients();

    @GET("list.php?a=list")
    Call<RootArea> getAreas();

    @GET("filter.php")
    Call<RootMealPreview> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<RootMealPreview> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<RootMealPreview> getMealsByArea(@Query("a") String cuisine);

    @GET("search.php")
    Call<RootMeal> searchByName(@Query("s") String mealName);

    @GET("lookup.php")
    Call<RootMeal> getMealById(@Query("i") String id);
}
