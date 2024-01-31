package com.example.foodfusion.views.home.meal_details.presenter;

import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.views.home.favorite.view.FavoriteView;
import com.example.foodfusion.views.home.meal_details.view.MealDetailsView;

public class MealDetailsPresenter implements MealDetailsPresenterInterface{
    private MealDetailsView _view;
    private MealLocalDataSourceInterface _localSrc;


    public MealDetailsPresenter(MealDetailsView _view, MealLocalDataSourceInterface _localSrc) {
        this._view = _view;
        this._localSrc = _localSrc;

    }
    @Override
    public void addToFav(PojoMeal meal) {
        _localSrc.insertMealToFav(meal);
    }
}
