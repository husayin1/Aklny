package com.example.foodfusion.features.home.home.presenter;


import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerInterface;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepositoryInterface;
import com.example.foodfusion.features.home.home.view.HomeView;

import io.reactivex.rxjava3.core.Single;

public class HomePresenter implements HomePresenterInterface {
    private static final String TAG = "HomePresenter";
    private final HomeView homeView;
    private final FavAndPlannerInterface repo;
    private final MealsRepositoryInterface mealsRepository;

    public HomePresenter(HomeView homeView, FavAndPlannerInterface repo, MealsRepositoryInterface mealsRepository) {
        this.homeView = homeView;
        this.repo = repo;
        this.mealsRepository = mealsRepository;
    }

    @Override
    public void getRandomMeal() {
        mealsRepository.getRandomMeal().subscribe((rootMeal, throwable) -> {
            if (rootMeal.meals != null) {
                PojoMeal meal = rootMeal.meals.get(0);
                homeView.showRandomData(meal);
            }
        });
    }


    @Override
    public void getTrendingMeals() {
        mealsRepository.getTrendingMeals().subscribe((rootMeal, t) -> {
            if (rootMeal.meals != null) {
                homeView.showTrendingMeals(rootMeal.meals);
            }
        });
    }

/*
    @Override
    public void geMightLikeMeals() {
        mealsRepository.geMightLikeMeals().subscribe(((rootMeal, throwable) -> {
            if(rootMeal.meals!=null){
                homeView.geMightLikeMeals(rootMeal.meals);
            }
        }));
    }
*/


    @Override
    public void addToFav(PojoMeal meal, OnClickAddListener onClickAddListener) {
        repo.addToFavorites(meal, onClickAddListener);
    }

/*    @Override
    public void onSuccessRandom(List<PojoMeal> meals) {
        homeView.showRandomData(meals);
        Log.i(TAG, "onSuccessRandom:Presenter " + meals.get(0).getStrMeal());
    }

    @Override
    public void onFailureRandom(String errorMsg) {
        homeView.showRandomErrorMsg(errorMsg);
        Log.i(TAG, "onFailureRandom:Presenter " + errorMsg);
    }*/

}
