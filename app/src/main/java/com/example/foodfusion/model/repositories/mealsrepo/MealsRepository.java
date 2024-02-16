package com.example.foodfusion.model.repositories.mealsrepo;

import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.remoteDataSource.API.MealsRemoteDataSource;
import com.example.foodfusion.remoteDataSource.API.MealsRemoteDataSourceInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepository implements MealsRepositoryInterface {
    private static final String TAG = "MealsRepo";
    private final MealsRemoteDataSourceInterface mealsRemoteDataSource;
    private Single<RootMeal> randomMeal;
    private Single<RootIngredient> rootIngredient;
    private Single<RootCategory> rootCategory;
    private Single<RootArea> rootArea;
    private Single<RootMainMeal> rootMealByArea;
    private Single<RootMainMeal> rootMealByIngredient;
    private Single<RootMainMeal> rootMealByCategory;
    private Single<RootMeal> trendingMeals;

    private static MealsRepository instance;

    private MealsRepository() {
        this.mealsRemoteDataSource = MealsRemoteDataSource.getInstance();
    }

    public static synchronized MealsRepository getInstance() {
        if (instance == null) {
            instance = new MealsRepository();
        }
        return instance;
    }

    @Override
    public Single<RootMeal> getRandomMeal() {
        if (randomMeal == null) {
            randomMeal = mealsRemoteDataSource.getRandomMeal().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        }
        return randomMeal;
    }

    public Single<RootMeal> getTrendingMeals() {
        if (trendingMeals == null) {
            trendingMeals = mealsRemoteDataSource.getTrendingMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return trendingMeals;
    }
/*
    @Override
    public Single<RootMeal> geMightLikeMeals() {
        if(geMightLikeMeals==null){
            geMightLikeMeals = mealsRemoteDataSource.geMightLikeMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return geMightLikeMeals;
    }*/

    public Single<RootIngredient> getRootIngredients() {
        if (rootIngredient == null) {
            rootIngredient = mealsRemoteDataSource.getIngredients();
        }
        return rootIngredient;
    }

    @Override
    public Single<RootCategory> getRootCategories() {
        if (rootCategory == null) {
            rootCategory = mealsRemoteDataSource.getCategories();
        }
        return rootCategory;
    }

    @Override
    public Single<RootArea> getRootAreas() {
        if (rootArea == null) {
            rootArea = mealsRemoteDataSource.getAreas();
        }
        return rootArea;
    }

    @Override
    public Single<RootMainMeal> getRootMealByIngredient(String id) {
        if (rootMealByIngredient == null) {
            rootMealByIngredient = mealsRemoteDataSource.getMealsByIngredient(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootMealByIngredient;
    }

    @Override
    public Single<RootMainMeal> getRootMealByCategory(String name) {
        if (rootMealByCategory == null) {
            rootMealByCategory = mealsRemoteDataSource.getMealsByCategory(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootMealByCategory;
    }

    @Override
    public Single<RootMainMeal> getRootMealByCountry(String name) {
        if (rootMealByArea == null) {
            rootMealByArea = mealsRemoteDataSource.getMealsByArea(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return rootMealByArea;
    }

    @Override
    public Single<RootMeal> searchMealByName(String name) {
        return mealsRemoteDataSource.searchMealByName(name);
    }

    @Override
    public Single<RootMeal> getMealById(String id) {
        return mealsRemoteDataSource.getMealById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
