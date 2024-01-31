package com.example.foodfusion.views.home.favorite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodfusion.R;
import com.example.foodfusion.localDataSource.MealLocalDataSource;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.views.home.favorite.presenter.FavoritePresenter;
import com.example.foodfusion.views.home.favorite.presenter.FavoritePresenterInterface;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteClickListener, FavoriteView {

    RecyclerView recyclerViewFavoriteMeals;
    FavoriteAdapter favoriteAdapter;
    RecyclerView.LayoutManager layoutManager;
    FavoritePresenterInterface favoritePresenterInterface;
    GridLayoutManager gridLayoutManager;
    ImageView imageViewEmptyList;
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
        imageViewEmptyList = view.findViewById(R.id.imageViewEmptyList);
        recyclerViewFavoriteMeals = view.findViewById(R.id.recyclerViewFavoriteMeals);
        favoritePresenterInterface = new FavoritePresenter(this,
                MealLocalDataSource.getInstance(getContext())
        );
        favoriteAdapter = new FavoriteAdapter(new ArrayList<>(), this, getContext());
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
                if(pojo.isEmpty()){
                    imageViewEmptyList.setVisibility(View.VISIBLE);
                }else{
                    imageViewEmptyList.setVisibility(View.GONE);
                }
                if(pojo.size()%2==0){
                    recyclerViewFavoriteMeals.setLayoutManager(gridLayoutManager);
                }else{
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
        deleteMeal(meal);
        favoriteAdapter.notifyDataSetChanged();
        Toast.makeText(this.getContext(), "Removed Item", Toast.LENGTH_SHORT).show();
    }
}