package com.example.foodfusion.features.home.search.view.mealname;

import android.content.Context;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodfusion.R;
import com.example.foodfusion.features.home.home.view.HomeAdapter;
import com.example.foodfusion.features.home.home.view.HomeFragmentDirections;
import com.example.foodfusion.features.home.home.view.OnClickListener;
import com.example.foodfusion.features.home.search.presenter.SearchPresenter;
import com.example.foodfusion.features.home.search.presenter.SearchPresenterInterface;
import com.example.foodfusion.features.home.search.view.SearchView;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoArea;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMainMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;
import com.example.foodfusion.model.repositories.repo.AppRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SearchMealFragment extends Fragment implements SearchView, OnClickListener {
    private static final String TAG = "SearchMealFragment";
    EditText editTextSearchByMealName;
    RecyclerView searchByMealNameRecyclerView;
    SearchPresenterInterface searchPresenter;

    SearchMealAdapter searchAdapter;
    LottieAnimationView search_empty;
    public SearchMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_meal, container, false);
        searchPresenter = new SearchPresenter(AppRepo.getInstance(MealsRepository.getInstance(), FavAndPlannerRepo.getInstance(getContext())), this);
        editTextSearchByMealName = view.findViewById(R.id.editTextSearchByMealName);
        searchByMealNameRecyclerView = view.findViewById(R.id.searchByMealNameRecyclerView);
        editTextSearchByMealName.requestFocus();
        editTextSearchByMealName.requestFocus(R.id.editTextSearchByMealName);
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editTextSearchByMealName, InputMethodManager.SHOW_IMPLICIT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        searchAdapter = new SearchMealAdapter(this.getContext(), new ArrayList<>(), this);
        searchByMealNameRecyclerView.setLayoutManager(gridLayoutManager);
        searchByMealNameRecyclerView.setAdapter(searchAdapter);
        search_empty = view.findViewById(R.id.search_empty);
        editTextSearchByMealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchPresenter.searchMealByName(charSequence+"");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }
/*

    private void searching(List<PojoMeal> meals) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        editTextSearchByMealName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                emitter.onNext(s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                })
                .debounce(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                        Log.d(TAG, "onNext: " + s);
                        List<PojoMeal> mealList = meals.stream().filter(item ->
                                item.strMeal.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: " + mealList.size());
                        searchAdapter.setMeals(mealList);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }
*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showIngredientsData(RootIngredient ingredients) {

    }

    @Override
    public void showCategoriesData(RootCategory categories) {

    }

    @Override
    public void showCountriesData(RootArea areas) {

    }

    @Override
    public void showSearchedMealData(List<PojoMeal> meals) {
        if(meals!=null){
            searchAdapter.setMeals(meals);
            searchAdapter.notifyDataSetChanged();
            search_empty.setVisibility(View.GONE);
        }else{
            searchAdapter.setMeals(new ArrayList<>());
            searchAdapter.notifyDataSetChanged();
            search_empty.setVisibility(View.VISIBLE);
        }
//        searching(meals);
    }

    @Override
    public void showSearchResultData(List<PojoMainMeal> mainMeal) {

    }


    @Override
    public void onClick(PojoMeal meal, View view) {
//        Toast.makeText(getContext(), meal.getStrMeal() + " from search meal fragment", Toast.LENGTH_SHORT).show();
        SearchMealFragmentDirections.ActionSearchMealFragmentToMealDetailsFragment action = SearchMealFragmentDirections.actionSearchMealFragmentToMealDetailsFragment(meal);
        Navigation.findNavController(getView()).navigate(action);
    }
}