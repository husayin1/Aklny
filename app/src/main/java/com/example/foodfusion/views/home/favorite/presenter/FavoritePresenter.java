package com.example.foodfusion.views.home.favorite.presenter;

import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.views.home.favorite.view.FavoriteView;

public class FavoritePresenter implements FavoritePresenterInterface {
    private FavoriteView _view;
    private MealLocalDataSourceInterface _localSrc;

    public FavoritePresenter(FavoriteView _view, MealLocalDataSourceInterface _localSrc) {
        this._view = _view;
        this._localSrc = _localSrc;

    }

    @Override
    public void getFavoriteMeals() {
        _view.showData(_localSrc.getAllStoredMeals());
    }

    @Override
    public void removeFromFavorite(PojoMeal meal) {
        _localSrc.deleteMealFromFav(meal);
    }
}
