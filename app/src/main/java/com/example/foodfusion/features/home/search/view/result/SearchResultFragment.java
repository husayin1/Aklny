package com.example.foodfusion.features.home.search.view.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.presenter.SearchPresenter;
import com.example.foodfusion.features.home.search.presenter.SearchPresenterInterface;
import com.example.foodfusion.features.home.search.view.SearchView;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredient;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchResultFragment extends Fragment implements OnSearchResultClick, SearchView {
    private static final String TAG = " SearchResult ";
    RecyclerView recyclerViewMealsResult;
    TextView textViewSearchResult;
    SearchResultAdapter searchResultAdapter;
    SearchPresenterInterface searchPresenter;
    EditText editTextSearchByMealName2;
    List<PojoMainMeal> meals;


    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        searchPresenter = new SearchPresenter(AppRepo.getInstance(MealsRepository.getInstance(), FavAndPlannerRepo.getInstance(getContext())),this);
        editTextSearchByMealName2 = view.findViewById(R.id.editTextSearchByMealName2);


        SearchType searchType = SearchResultFragmentArgs.fromBundle(getArguments()).getResultMeal();


        textViewSearchResult = view.findViewById(R.id.textViewSearchResult);
        textViewSearchResult.setText(textViewSearchResult.getText()+" "+searchType.getName());
        recyclerViewMealsResult = view.findViewById(R.id.recyclerViewMealsResult);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewMealsResult.setLayoutManager(staggeredGridLayoutManager);

        searchResultAdapter = new SearchResultAdapter(this.getContext(),new ArrayList<>(),this);
        if(searchType.getType()==SearchType.country){
            searchPresenter.getMealByCountry(searchType.getName());
            /*searchPresenter.getMealByCountry(searchType.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMainMeal>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull RootMainMeal rootMainMeal) {
                            searchResultAdapter.setMainMealsList(rootMainMeal.mainMeal);
                            searchResultAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });*/
        }else if(searchType.getType()==SearchType.ingredient){
            searchPresenter.getMealByIngredient(searchType.getName());
            /*searchPresenter.getMealByIngredient(searchType.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMainMeal>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull RootMainMeal rootMainMeal) {
                            searchResultAdapter.setMainMealsList(rootMainMeal.mainMeal);
                            searchResultAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });*/
        }else if(searchType.getType()==SearchType.category){
            searchPresenter.getMealByCategory(searchType.getName());
            /*searchPresenter.getMealByCategory(searchType.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<RootMainMeal>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull RootMainMeal rootMainMeal) {
                            searchResultAdapter.setMainMealsList(rootMainMeal.mainMeal);
                            searchResultAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });*/

        }
        recyclerViewMealsResult.setAdapter(searchResultAdapter);

        return view;
    }

    @Override
    public void onResultClick(String name) {
//        Toast.makeText(getContext(), name+ " from search result fragment", Toast.LENGTH_SHORT).show();
        searchPresenter.getMealById(name).subscribe((rootMeal ,t)->{
            PojoMeal meal = rootMeal.meals.get(0);
            SearchResultFragmentDirections.ActionSearchResultFragmentToMealDetailsFragment action = SearchResultFragmentDirections.actionSearchResultFragmentToMealDetailsFragment(meal);
            Navigation.findNavController(getView()).navigate(action);
        });

    }
    private void searchByMeal(List<PojoMainMeal> mainMeals) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@androidx.annotation.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        editTextSearchByMealName2.addTextChangedListener(new TextWatcher() {
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
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull String s) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                        meals = mainMeals.stream().filter(meal ->
                                meal.strMeal.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: " + meals.size());
                        searchResultAdapter.setMainMealsList(meals);
                        searchResultAdapter.notifyDataSetChanged();
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

    }

    @Override
    public void showSearchResultData(List<PojoMainMeal> mainMeal) {
        if(mainMeal==null){

            searchResultAdapter.setMainMealsList(new ArrayList<>());
            searchResultAdapter.notifyDataSetChanged();
        }else {
            searchResultAdapter.setMainMealsList(mainMeal);
            searchResultAdapter.notifyDataSetChanged();
            searchByMeal(mainMeal);
            searchResultAdapter.notifyDataSetChanged();
        }
    }
}