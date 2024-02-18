package com.example.foodfusion.features.home.home.presenter;


import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;

import com.example.foodfusion.features.home.home.view.HomeView;
import com.example.foodfusion.model.repositories.repo.AppRepo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements HomePresenterInterface {
    private static final String TAG = "HomePresenter";
    private final HomeView homeView;
//    private final FavAndPlannerInterface repo;
//    private final MealsRepositoryInterface mealsRepository;
    private final AppRepo _repo;

    public HomePresenter(HomeView homeView, AppRepo _repo) {
        this.homeView = homeView;
        this._repo=_repo;
    }

    @Override
    public void getRandomMeal() {
        _repo.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<RootMeal>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull RootMeal rootMeal) {
                if (rootMeal.meals != null) {
                    PojoMeal meal = rootMeal.meals.get(0);
                    homeView.showRandomData(meal);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }


    @Override
    public void getTrendingMeals() {
        _repo.getTrendingMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<RootMeal>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull RootMeal rootMeal) {
                                if (rootMeal.meals != null) {
                                    homeView.showTrendingMeals(rootMeal.meals);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

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
        _repo.addToFavorites(meal, onClickAddListener);
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
