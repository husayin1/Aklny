package com.example.foodfusion.features.home.search.view.ingredient;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.view.result.SearchType;
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
import io.reactivex.rxjava3.disposables.Disposable;

public class IngredientFragment extends Fragment implements OnIngredientClickListener, SearchView {
    private final String TAG = " Ingredient Fragment ";
    EditText editTextSearchByIngredient;
    RecyclerView searchByIngredientRecyclerView;
    IngredientAdapter ingredientAdapter;
    SearchPresenterInterface searchPresenter;

    List<PojoIngredient> ingredientList;
    private ProgressBar progressLoader;


    public IngredientFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        progressLoader = view.findViewById(R.id.progressLoading);
        ingredientList = new ArrayList<>();
        searchPresenter = new SearchPresenter(AppRepo.getInstance(MealsRepository.getInstance(), FavAndPlannerRepo.getInstance(getContext())), this);
        editTextSearchByIngredient = view.findViewById(R.id.editTextSearchByIngredient);
        searchByIngredientRecyclerView = view.findViewById(R.id.searchByIngredientRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 4);
        searchByIngredientRecyclerView.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(this.getContext(), new ArrayList<>(), this);
        searchPresenter.getIngredients();
        progressLoader.setVisibility(View.VISIBLE);
        searchByIngredientRecyclerView.setAdapter(ingredientAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void searchByIngredient(List<PojoIngredient> ingredients) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        editTextSearchByIngredient.addTextChangedListener(new TextWatcher() {
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
                        ingredientList = ingredients.stream().filter(ingredient ->
                                ingredient.strIngredient.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: " + ingredientList.size());
                        ingredientAdapter.setIngredientList(ingredientList);
                        ingredientAdapter.notifyDataSetChanged();
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
    public void onIngredientClick(String name) {
        editTextSearchByIngredient.setText("");
//        Toast.makeText(getContext(), name + "from ingredient fragment", Toast.LENGTH_SHORT).show();
        SearchType searchType = new SearchType(SearchType.ingredient, name);

        IngredientFragmentDirections.ActionIngredientFragmentToSearchResultFragment action = IngredientFragmentDirections.actionIngredientFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showIngredientsData(RootIngredient ingredients) {
        if (ingredients == null) {
            ingredientAdapter.setIngredientList(new ArrayList<>());
            ingredientAdapter.notifyDataSetChanged();
        } else {
            ingredientList = ingredients.ingredients;
            ingredientAdapter.setIngredientList(ingredientList);
            ingredientAdapter.notifyDataSetChanged();
            searchByIngredient(ingredientList);
            ingredientAdapter.notifyDataSetChanged();
        }


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

    }
}