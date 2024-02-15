package com.example.foodfusion.features.home.search.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.view.result.SearchType;
import com.example.foodfusion.features.home.search.presenter.SearchPresenter;
import com.example.foodfusion.features.home.search.presenter.SearchPresenterInterface;
import com.example.foodfusion.features.home.search.view.category.CategoryAdapter;
import com.example.foodfusion.features.home.search.view.category.OnCategoryClickListener;
import com.example.foodfusion.features.home.search.view.country.CountryAdapter;
import com.example.foodfusion.features.home.search.view.country.OnCountryClickListener;
import com.example.foodfusion.features.home.search.view.ingredient.IngredientAdapter;
import com.example.foodfusion.features.home.search.view.ingredient.OnIngredientClickListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMainMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.repositories.meal_models.root_pojos.RootMainMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;
import com.example.foodfusion.utilities.Connectivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements OnCountryClickListener, OnCategoryClickListener, OnIngredientClickListener, SearchView {
    RecyclerView countriesRecyclerView, ingredientsRecyclerView, categoriesRecyclerView;
    CountryAdapter countryAdapter;
    CategoryAdapter categoryAdapter;
    IngredientAdapter ingredientAdapter;
    SearchPresenterInterface searchPresenter;
    EditText searchView;
    TextView textViewCountryViewAll, textViewIngredientsViewAll, countrySearch_textView, ingredientSearch_textView, categoriesSearch_textView;
    Connectivity connectivity;
    LottieAnimationView imageViewEmptyList;

    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenter = new SearchPresenter(MealsRepository.getInstance(), this);
        connectivity = new Connectivity(getContext());


        countriesRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        textViewCountryViewAll = view.findViewById(R.id.textViewCountryViewAll);
        textViewIngredientsViewAll = view.findViewById(R.id.textViewIngredientsViewAll);
        //
        countrySearch_textView = view.findViewById(R.id.countrySearch_textView);
        ingredientSearch_textView = view.findViewById(R.id.ingredientSearch_textView);
        categoriesSearch_textView = view.findViewById(R.id.categoriesSearch_textView);
        //

        searchView = view.findViewById(R.id.searchView);
        imageViewEmptyList = view.findViewById(R.id.searchNetwork);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_SearchFragment_to_searchMealFragment);
            }
        });
        textViewCountryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_SearchFragment_to_countryFragment);
            }
        });
        textViewIngredientsViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_SearchFragment_to_ingredientFragment);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        ingredientAdapter = new IngredientAdapter(this.getContext(), new ArrayList<>(), this);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);


        LinearLayoutManager layoutManagerForCategories = new LinearLayoutManager(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        layoutManagerForCategories.setOrientation(RecyclerView.VERTICAL);
        categoriesRecyclerView.setLayoutManager(gridLayoutManager);

        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>(), this);


        categoriesRecyclerView.setAdapter(categoryAdapter);


        LinearLayoutManager layoutManagerForAreas = new LinearLayoutManager(getContext());
        layoutManagerForAreas.setOrientation(RecyclerView.HORIZONTAL);
        countriesRecyclerView.setLayoutManager(layoutManagerForAreas);

        countryAdapter = new CountryAdapter(getContext(), new ArrayList<>(), this);
        countriesRecyclerView.setAdapter(countryAdapter);
        if (connectivity.isConnectedMobile() || connectivity.isConnectedWifi()) {

            imageViewEmptyList.setVisibility(View.GONE);
            countrySearch_textView.setVisibility(View.VISIBLE);
            ingredientSearch_textView.setVisibility(View.VISIBLE);
            categoriesSearch_textView.setVisibility(View.VISIBLE);
            textViewCountryViewAll.setVisibility(View.VISIBLE);
            textViewIngredientsViewAll.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);

            searchPresenter.getAreas();
            searchPresenter.getIngredients();
            searchPresenter.getCategories();
        } else {
            imageViewEmptyList.setVisibility(View.VISIBLE);
            countrySearch_textView.setVisibility(View.GONE);
            ingredientSearch_textView.setVisibility(View.GONE);
            categoriesSearch_textView.setVisibility(View.GONE);
            textViewCountryViewAll.setVisibility(View.GONE);
            textViewIngredientsViewAll.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            Toast.makeText(getContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();

        }

        return view;
    }

    @Override
    public void onCategoryClick(String name) {
        Toast.makeText(getContext(), name + "from search fragment", Toast.LENGTH_SHORT).show();
        SearchType searchType = new SearchType(SearchType.category, name);
        SearchFragmentDirections.ActionSearchFragmentToSearchResultFragment action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onCountryClick(String name) {
        Toast.makeText(getContext(), name + "from search fragment", Toast.LENGTH_SHORT).show();
        SearchType searchType = new SearchType(SearchType.country, name);
        SearchFragmentDirections.ActionSearchFragmentToSearchResultFragment action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onIngredientClick(String name) {
        Toast.makeText(getContext(), name + " from search Fragment", Toast.LENGTH_SHORT).show();
        SearchType searchType = new SearchType(SearchType.ingredient, name);
        SearchFragmentDirections.ActionSearchFragmentToSearchResultFragment action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showIngredientsData(RootIngredient ingredients) {
        ingredientAdapter.setIngredientList(ingredients.ingredients);
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesData(RootCategory categories) {
        categoryAdapter.setCategoryList(categories.categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountriesData(RootArea areas) {
        countryAdapter.setCountryList(areas.areas);
        countryAdapter.notifyDataSetChanged();

    }

    @Override
    public void showSearchedMealData(List<PojoMeal> meals) {

    }

    @Override
    public void showSearchResultData(List<PojoMainMeal> mainMeal) {

    }
}