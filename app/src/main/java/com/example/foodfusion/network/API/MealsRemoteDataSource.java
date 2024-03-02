package com.example.foodfusion.network.API;

import com.example.foodfusion.model.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.meal_models.root_pojos.RootMainMeal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource implements MealsRemoteDataSourceInterface {
    private static final String TAG = "MealsRemoteDataSource";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealsRemoteDataSource client = null;
    private final MealService mealService;

    private MealsRemoteDataSource() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealsRemoteDataSource getInstance() {
        if (client == null) {
            client = new MealsRemoteDataSource();
        }
        return client;
    }

    @Override
    public Single<RootMeal> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @Override
    public Single<RootMeal> getTrendingMeals() {
        Random random = new Random();
        char c = (char) (random.nextInt(26) + 'A');
        return mealService.searchByName(String.valueOf(c));
    }

    @Override
    public Single<RootCategory> getCategories() {
        return mealService.getCategories();
    }

    @Override
    public Single<RootIngredient> getIngredients() {
        return mealService.getIngredients();
    }

    @Override
    public Single<RootArea> getAreas() {
        return mealService.getAreas();
    }

    @Override
    public Single<RootMainMeal> getMealsByIngredient(String ingredient) {
        return mealService.getMealsByIngredient(ingredient);
    }

    @Override
    public Single<RootMainMeal> getMealsByCategory(String category) {
        return mealService.getMealsByCategory(category);
    }

    @Override
    public Single<RootMainMeal> getMealsByArea(String country) {
        return mealService.getMealsByArea(country);
    }

    @Override
    public Single<RootMeal> searchMealByName(String name) {
        return mealService.searchByName(name);
    }

    public Single<RootMeal> getMealById(String id) {
        return mealService.getMealById(id);
    }

/*@Override
    public void makeRandomCall(NetworkCallBack networkCallBack) {
        Call<RootMeal> call = mealService.getRandomMeal();
        call.enqueue(new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call, Response<RootMeal> response) {
                Log.i(TAG, "onResponse:RandomMeal " + response.body().meals);
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessRandom(response.body().meals);
                    Log.i(TAG, "onResponse: random " + response.body().meals.get(0).getStrMeal());
                } else {
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {
                networkCallBack.onFailureRandom(t.getMessage());
                Log.i(TAG, "onFailure: randomfailure " + t.getMessage());
            }
        });
        Log.i(TAG, "makeNetworkCall: execute enqueue random NetworkCall");
    }*/

/*
    @Override
    public void makeCategoriesCall(NetworkCallBack networkCallBack) {
        Call<RootCategory> call = mealService.getCategories();
        call.enqueue(new Callback<RootCategory>() {
            @Override
            public void onResponse(Call<RootCategory> call, Response<RootCategory> response) {
                Log.i(TAG, "onResponse:Category " + response.body().categories);
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessCategory(response.body().categories);
                    Log.i(TAG, "onResponse: categories " + response.body().categories.get(0).strCategoryDescription);
                } else {
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RootCategory> call, Throwable t) {
                networkCallBack.onFailureCategory(t.getMessage());
                Log.i(TAG, "onFailure: categoryfailure " + t.getMessage());

            }
        });
        Log.i(TAG, "makeNetworkCall: execute enqueue category NetworkCall");
    }*/

/*@Override
    public void makeIngredientsCall(NetworkCallBack networkCallBack) {
        Call<RootIngredient> call = mealService.getIngredients();
        call.enqueue(new Callback<RootIngredient>() {
            @Override
            public void onResponse(Call<RootIngredient> call, Response<RootIngredient> response) {
                Log.i(TAG, "onResponse:Ingredients " + response.body().ingredients);
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessIngredients(response.body().ingredients);
                    Log.i(TAG, "onResponse: ingredients " + response.body().ingredients.get(0).strIngredient);
                } else {
                    Log.i(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RootIngredient> call, Throwable t) {
                networkCallBack.onFailureIngredients(t.getMessage());
                Log.i(TAG, "onFailure: ingredientsfailure " + t.getMessage());
            }
        });
        Log.i(TAG, "makeNetworkCall: execute enqueue ingredient NetworkCall");
    }*/
}
