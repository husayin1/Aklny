package com.example.foodfusion.network.API;

import com.example.foodfusion.model.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.meal_models.root_pojos.RootMainMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Single<RootMeal> getRandomMeal();

    @GET("categories.php")
    Single<RootCategory> getCategories();

    @GET("list.php?i=list")
    Single<RootIngredient> getIngredients();

    @GET("list.php?a=list")
    Single<RootArea> getAreas();

    @GET("filter.php")
    Single<RootMainMeal> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<RootMainMeal> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<RootMainMeal> getMealsByArea(@Query("a") String area);

    @GET("search.php")
    Single<RootMeal> searchByName(@Query("s") String name);

    @GET("lookup.php")
    Single<RootMeal> getMealById(@Query("i") String id);
}
