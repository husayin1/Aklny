package com.example.foodfusion.features.home.favorite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodfusion.R;
import com.example.foodfusion.model.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.favorite.presenter.FavoritePresenter;
import com.example.foodfusion.features.home.favorite.presenter.FavoritePresenterInterface;
import com.example.foodfusion.features.home.meal_details.view.OnClickDetailsListener;
import com.example.foodfusion.model.mealsrepo.MealsRepository;
import com.example.foodfusion.model.repo.AppRepo;
import com.example.foodfusion.utilities.Connectivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteClickListener, FavoriteView, OnClickDetailsListener {

    RecyclerView recyclerViewFavoriteMeals;
    FavoriteAdapter favoriteAdapter;
    RecyclerView.LayoutManager layoutManager;
    FavoritePresenterInterface favoritePresenterInterface;
    GridLayoutManager gridLayoutManager;
    LottieAnimationView imageViewEmptyList;
    Connectivity connectivity;

    public FavoriteFragment() {
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        connectivity = new Connectivity(requireContext());
        imageViewEmptyList = view.findViewById(R.id.imageViewEmptyList);
        recyclerViewFavoriteMeals = view.findViewById(R.id.recyclerViewFavoriteMeals);
        favoritePresenterInterface = new FavoritePresenter(this,
                AppRepo.getInstance(MealsRepository.getInstance(),FavAndPlannerRepo.getInstance(getContext()))
        );
        favoriteAdapter = new FavoriteAdapter(new ArrayList<>(), this, getContext(), this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewFavoriteMeals.setLayoutManager(layoutManager);

        recyclerViewFavoriteMeals.setAdapter(favoriteAdapter);
        favoritePresenterInterface.getFavoriteMeals();
        return view;
    }

    @Override
    public void showData(LiveData<List<PojoMeal>> meal) {

        meal.observe(this, new Observer<List<PojoMeal>>() {
            @Override
            public void onChanged(List<PojoMeal> pojo) {
                if (pojo.isEmpty()) {
                    imageViewEmptyList.setVisibility(View.VISIBLE);
                } else {
                    imageViewEmptyList.setVisibility(View.GONE);
                }
                if (pojo.size() % 2 == 0) {
                    recyclerViewFavoriteMeals.setLayoutManager(gridLayoutManager);
                } else {
                    recyclerViewFavoriteMeals.setLayoutManager(layoutManager);
                }
                favoriteAdapter.setList(pojo);
                favoriteAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteMeal(PojoMeal meal) {
        favoritePresenterInterface.removeFromFavorite(meal);
    }

    @Override
    public void onFavoriteClick(PojoMeal meal) {
        if (connectivity.isConnectedMobile() || connectivity.isConnectedWifi()) {
            deleteMeal(meal);
            favoriteAdapter.notifyDataSetChanged();
            Toast.makeText(this.getContext(), R.string.removed_item, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickListener(PojoMeal meal) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action = FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(meal);
        Navigation.findNavController(getView()).navigate(action);
    }
}