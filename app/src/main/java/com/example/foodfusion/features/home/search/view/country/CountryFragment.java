package com.example.foodfusion.features.home.search.view.country;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.view.result.SearchType;
import com.example.foodfusion.features.home.search.presenter.SearchPresenter;
import com.example.foodfusion.features.home.search.presenter.SearchPresenterInterface;
import com.example.foodfusion.features.home.search.view.SearchView;
import com.example.foodfusion.model.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.meal_models.pojos.PojoArea;
import com.example.foodfusion.model.meal_models.pojos.PojoMainMeal;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.meal_models.root_pojos.RootArea;
import com.example.foodfusion.model.meal_models.root_pojos.RootCategory;
import com.example.foodfusion.model.meal_models.root_pojos.RootIngredient;
import com.example.foodfusion.model.mealsrepo.MealsRepository;
import com.example.foodfusion.model.repo.AppRepo;

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

public class CountryFragment extends Fragment implements OnCountryClickListener, SearchView {
    //all countries
    private final String TAG = " Country Fragment ";
    RecyclerView searchByCountryRecyclerView;
    CountryFragmentAdapter countryFragmentAdapter;
    SearchPresenterInterface searchPresenter;
    EditText editTextSearchByCountry;

    public CountryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        searchPresenter=new SearchPresenter(AppRepo.getInstance(MealsRepository.getInstance(), FavAndPlannerRepo.getInstance(getContext())),this);

        editTextSearchByCountry = view.findViewById(R.id.editTextSearchByCountry);
        searchByCountryRecyclerView = view.findViewById(R.id.searchByCountryRecyclerView);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        searchByCountryRecyclerView.setLayoutManager(layoutManager);

        countryFragmentAdapter = new CountryFragmentAdapter(this.getContext(),new ArrayList<>(),this);
        searchPresenter.getAreas();
        searchByCountryRecyclerView.setAdapter(countryFragmentAdapter);


        return view;
    }
    private void searchByArea(List<PojoArea> areas) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        editTextSearchByCountry.addTextChangedListener(new TextWatcher() {
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
                        Log.d(TAG, "onNext: "+Thread.currentThread().getName());
                        List<PojoArea> areaList = areas.stream().filter(area ->
                                area.strArea.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(TAG, "onNext: "+areaList.size());
                        countryFragmentAdapter.setCountryList(areaList);
                        countryFragmentAdapter.notifyDataSetChanged();
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
    public void onCountryClick(String name) {
        editTextSearchByCountry.setText("");
//        Toast.makeText(getContext(), name+" from country fragment", Toast.LENGTH_SHORT).show();
        SearchType searchType = new SearchType(SearchType.country,name);
        CountryFragmentDirections.ActionCountryFragmentToSearchResultFragment action= CountryFragmentDirections.actionCountryFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showIngredientsData(RootIngredient ingredients) {

    }

    @Override
    public void showCategoriesData(RootCategory categories) {
    }

    @Override
    public void showCountriesData(RootArea areas) {
        countryFragmentAdapter.setCountryList(areas.areas);
        countryFragmentAdapter.notifyDataSetChanged();
        searchByArea(areas.areas);
    }

    @Override
    public void showSearchedMealData(List<PojoMeal> meals) {

    }

    @Override
    public void showSearchResultData(List<PojoMainMeal> mainMeal) {

    }

}