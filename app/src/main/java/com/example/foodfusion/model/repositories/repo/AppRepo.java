package com.example.foodfusion.model.repositories.repo;


import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AppRepo {
    MealsRepository remoteRepo;
    FavAndPlannerRepo localRepo;
    private static AppRepo instance = null;
    private static final String TAG = "AppRepo";

    private AppRepo(MealsRepository remoteRepo, FavAndPlannerRepo localRepo) {
        this.remoteRepo = remoteRepo;
        this.localRepo = localRepo;
    }

    public static synchronized AppRepo getInstance(MealsRepository remoteRepo, FavAndPlannerRepo localRepo) {
        if (instance == null) {
            instance = new AppRepo(remoteRepo, localRepo);
        }
        return instance;
    }

    public Single<RootMeal> getRandomMeal() {
        return remoteRepo.getRandomMeal();
    }

    public Single<RootMeal> getTrendingMeals() {
        return remoteRepo.getTrendingMeals();
    }

    public Single<RootIngredient> getRootIngredients() {
        return remoteRepo.getRootIngredients();
    }

    public Single<RootCategory> getRootCategories() {
        return remoteRepo.getRootCategories();
    }

    public Single<RootArea> getRootAreas() {
        return remoteRepo.getRootAreas();
    }

    public Single<RootMainMeal> getRootMealByIngredient(String id) {
        return remoteRepo.getRootMealByIngredient(id);
    }

    public Single<RootMainMeal> getRootMealByCategory(String name) {
        return remoteRepo.getRootMealByCategory(name);
    }

    public Single<RootMainMeal> getRootMealByCountry(String name) {
        return remoteRepo.getRootMealByCountry(name);
    }

    public Single<RootMeal> searchMealByName(String name) {
        return remoteRepo.searchMealByName(name);
    }

    public Single<RootMeal> getMealById(String id) {
        return remoteRepo.getMealById(id);
    }


    public void refreshPlanner() {
        localRepo.refreshPlanner();
    }

    public void refreshMeals() {
        localRepo.refreshMeals();
    }

    public LiveData<List<PojoMeal>> getFavMealsLiveData() {
        return localRepo.getFavMealsLiveData();
    }

    public LiveData<List<PojoPlanner>> getAllMealsFromPlannerAtDate(String date) {
        return localRepo.getAllMealsFromPlannerAtDate(date);
    }


    public void addToFavorites(PojoMeal meal, OnClickAddListener onClickAddListener) {
        localRepo.addToFavorites(meal, onClickAddListener);
    }

    public void addToPlanner(PojoPlanner planner, OnClickAddListener onClickAddListener) {
        localRepo.addToPlanner(planner, onClickAddListener);
    }


    public void deleteMealFromPlanner(PojoPlanner planner) {
        localRepo.deleteMealFromPlanner(planner);
    }

    public void deleteMealFromFavorites(PojoMeal meal) {
        localRepo.deleteMealFromFavorites(meal);

    }

    public void deleteAllFav() {
        localRepo.deleteAllFav();
    }


    public void deleteAllWeekPlan() {
        localRepo.deleteAllWeekPlan();
    }

    public LiveData<PojoMeal> getMealFromFavById(String idMeal) {
        return localRepo.getMealFromFavById(idMeal);
    }

    public LiveData<PojoPlanner> getMealFromWeekPlanById(String id) {
        return localRepo.getMealFromWeekPlanById(id);
    }

}
