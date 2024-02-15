package com.example.foodfusion.features.home.favorite.presenter;

import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerInterface;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.favorite.view.FavoriteView;

public class FavoritePresenter implements FavoritePresenterInterface {
    private FavoriteView _view;
    private FavAndPlannerInterface _repo;

    public FavoritePresenter(FavoriteView _view, FavAndPlannerInterface _repo) {
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
