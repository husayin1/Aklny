package com.example.foodfusion.features.home.meal_details.presenter;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.foodfusion.localDataSource.MealLocalDataSourceInterface;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerInterface;
import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.meal_details.view.MealDetailsView;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;

public class MealDetailsPresenter implements MealDetailsPresenterInterface{
    private MealDetailsView _view;
    private MealLocalDataSourceInterface _localSrc;
    private FavAndPlannerInterface _repo;


    public MealDetailsPresenter(MealDetailsView _view, FavAndPlannerInterface _repo) {
        this._view = _view;
        this._repo=_repo;
    }
    @Override
    public void addToFav(PojoMeal meal,OnClickAddListener onClickAddListener) {
        _repo.addToFavorites(meal,onClickAddListener);

    }


    @Override
    public void addToPlanner(PojoPlanner meal,OnClickAddListener onClickAddListener) {
        _repo.addToPlanner(meal,onClickAddListener);
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
