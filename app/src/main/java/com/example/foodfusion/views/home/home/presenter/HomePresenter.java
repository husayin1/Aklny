package com.example.foodfusion.views.home.home.presenter;

import android.util.Log;

import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.meal_models.PojoCategory;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredient;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.remoteDataSource.API.MealsRemoteDataSource;
import com.example.foodfusion.remoteDataSource.API.MealsRemoteDataSourceInterface;
import com.example.foodfusion.remoteDataSource.API.NetworkCallBack;
import com.example.foodfusion.views.home.home.view.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomePresenterInterface, NetworkCallBack {
    private static final String TAG = "HomePresenter";
    HomeView homeView;
    MealsRemoteDataSource remoteDataSource;
    MealLocalDataSourceInterface mealLocalDataSourceInterface;

    public HomePresenter(HomeView homeView, MealsRemoteDataSource remoteDataSource, MealLocalDataSourceInterface mealLocalDataSourceInterface) {
        this.homeView = homeView;
        this.remoteDataSource = remoteDataSource;
        this.mealLocalDataSourceInterface = mealLocalDataSourceInterface;
    }

    @Override
    public void getRandomMeals() {
        remoteDataSource.makeRandomCall(this);
    }

    @Override
    public void getCategories() {
        remoteDataSource.makeCategoriesCall(this);
    }

    @Override
    public void getIngredients() {
        remoteDataSource.makeIngredientsCall(this);
    }

    @Override
    public void addToFav(PojoMeal meal) {
        mealLocalDataSourceInterface.insertMealToFav(meal);
    }

    @Override
    public void onSuccessRandom(List<PojoMeal> meals) {
        homeView.showRandomData(meals);
        Log.i(TAG, "onSuccessRandom:Presenter " + meals.get(0).getStrMeal());
    }

    @Override
    public void onFailureRandom(String errorMsg) {
        homeView.showRandomErrorMsg(errorMsg);
        Log.i(TAG, "onFailureRandom:Presenter " + errorMsg);
    }

    @Override
    public void onSuccessCategory(List<PojoCategory> categories) {
        homeView.showCategoriesData(categories);
        Log.i(TAG, "onSuccessCategory:Presenter " + categories.get(0).strCategory);

    }

    @Override
    public void onFailureCategory(String errorMsg) {
        homeView.showCategoriesErrorMsg(errorMsg);
        Log.i(TAG, "onFailureCategory: " + errorMsg);
    }

    @Override
    public void onSuccessIngredients(List<PojoIngredient> ingredients) {
        homeView.showIngredientsData(ingredients);
        Log.i(TAG, "onSuccessTrendingMeals: " + ingredients.get(0).getStrIngredient());
    }

    @Override
    public void onFailureIngredients(String errorMsg) {
        homeView.showIngredientsErrorMsg(errorMsg);
        Log.i(TAG, "onFailureIngredients: " + errorMsg);

    }
}
