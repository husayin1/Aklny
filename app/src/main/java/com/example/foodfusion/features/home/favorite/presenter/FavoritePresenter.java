package com.example.foodfusion.features.home.favorite.presenter;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.favorite.view.FavoriteView;
import com.example.foodfusion.model.repositories.repo.AppRepo;

public class FavoritePresenter implements FavoritePresenterInterface {
    private FavoriteView _view;
    private AppRepo _repo;

    public FavoritePresenter(FavoriteView _view, AppRepo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getFavoriteMeals() {
        _view.showData(_repo.getFavMealsLiveData());
    }

    @Override
    public void removeFromFavorite(PojoMeal meal) {
        _repo.deleteMealFromFavorites(meal);
    }
}
