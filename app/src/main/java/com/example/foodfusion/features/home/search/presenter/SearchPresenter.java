package com.example.foodfusion.features.home.search.presenter;

import com.example.foodfusion.features.home.home.view.HomeView;
import com.example.foodfusion.features.home.search.view.SearchView;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepositoryInterface;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements SearchPresenterInterface{
    private static final String TAG = "SearchPresenter";

    private MealsRepositoryInterface mealsRepository;
    private SearchView searchView;

    public SearchPresenter(MealsRepositoryInterface mealsRepository,SearchView searchView){
        this.mealsRepository = mealsRepository;
        this.searchView=searchView;
    }
    @Override
    public void getIngredients() {
        mealsRepository.getRootIngredients().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootIngredient>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RootIngredient rootIngredient) {
                        searchView.showIngredientsData(rootIngredient);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    @Override
    public void getCategories() {
        mealsRepository.getRootCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RootCategory>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RootCategory rootCategory) {
                        searchView.showCategoriesData(rootCategory);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void getAreas() {
        mealsRepository.getRootAreas().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((rootArea ,t)->{
                    searchView.showCountriesData(rootArea);
                });
    }

    @Override
    public void getMealByIngredient(String name) {
        mealsRepository.getRootMealByIngredient(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(((mainMeal, throwable) -> {
                    searchView.showSearchResultData(mainMeal);
                }));
    }

    @Override
    public void getMealByCountry(String name) {
        mealsRepository.getRootMealByCountry(name).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(((mainMeal, throwable) -> {
                    searchView.showSearchResultData(mainMeal);
                }));
    }

    @Override
    public void getMealByCategory(String name) {
//        return mealsRepository.getRootMealByCategory(name);
        mealsRepository.getRootMealByCategory(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((mainMeal, throwable) -> {
                    searchView.showSearchResultData(mainMeal);
                });
    }

    @Override
    public void searchMealByName(String name) {
         mealsRepository.searchMealByName(name).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe((rootMeal ,t )->{

                    searchView.showSearchedMealData(rootMeal.meals);
                });
    }
    public Single<RootMeal> getMealById(String id){
        return mealsRepository.getMealById(id);
    }
}
