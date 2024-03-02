package com.example.foodfusion.features.home.meal_details.presenter;


import androidx.lifecycle.LiveData;

import com.example.foodfusion.model.local_repo.OnClickAddListener;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.meal_details.view.MealDetailsView;
import com.example.foodfusion.model.meal_models.pojos.PojoPlanner;
import com.example.foodfusion.model.repo.AppRepo;

public class MealDetailsPresenter implements MealDetailsPresenterInterface {
    private MealDetailsView _view;
    private AppRepo _repo;


    public MealDetailsPresenter(MealDetailsView _view, AppRepo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void addToFav(PojoMeal meal, OnClickAddListener onClickAddListener) {
        _repo.addToFavorites(meal, onClickAddListener);

    }


    @Override
    public void addToPlanner(PojoPlanner meal, OnClickAddListener onClickAddListener) {
        _repo.addToPlanner(meal, onClickAddListener);
    }

    @Override
    public LiveData<PojoMeal> getFavMealById(String id) {
        return _repo.getMealFromFavById(id);
    }

    @Override
    public void removeFromFavorite(PojoMeal meal) {
        _repo.deleteMealFromFavorites(meal);
    }

}
