package com.example.foodfusion.features.home.home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.features.Authentication.AuthenticationActivity;
import com.example.foodfusion.localDataSource.MealLocalDataSource;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.mealsrepo.MealsRepository;
import com.example.foodfusion.features.home.home.presenter.HomePresenter;
import com.example.foodfusion.features.home.home.presenter.HomePresenterInterface;
import com.example.foodfusion.features.home.home.view.HomeFragmentDirections.*;
import com.example.foodfusion.utilities.Connectivity;


import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.Disposable;

public class HomeFragment extends Fragment implements HomeView, OnClickListener {
    private static final String TAG = "Home Fragment";
    HomePresenterInterface homePresenter;
    CardView cardView;
    ImageView imgView, imageViewRandomMealFavorite;
    TextView meal_name, meal_description,inspired_meal,rest_meals;
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    ConstraintLayout constraintLayoutFragmentHome;
    Connectivity connectivity;
    LottieAnimationView imageViewEmptyList;

    public HomeFragment() {
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        connectivity = new Connectivity(requireContext());

        recyclerView = view.findViewById(R.id.recycelrViewTrendingMeals);
        cardView = view.findViewById(R.id.cardView);
        imgView = view.findViewById(R.id.imgView);
        meal_name = view.findViewById(R.id.textViewMealName);
        meal_description = view.findViewById(R.id.meal_description);
        imageViewEmptyList = view.findViewById(R.id.homeNetwork);
        inspired_meal = view.findViewById(R.id.inspired_meal);
        rest_meals = view.findViewById(R.id.rest_meals);
        imageViewRandomMealFavorite = view.findViewById(R.id.imageViewRandomMealFavorite);

        constraintLayoutFragmentHome = view.findViewById(R.id.constraintLayoutFragmentHome);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        homeAdapter = new HomeAdapter(this.getContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);
        homePresenter = new HomePresenter(this, FavAndPlannerRepo.getInstance(getContext()), MealsRepository.getInstance());


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(connectivity.isConnectedWifi()||connectivity.isConnectedMobile()){
            homePresenter.getRandomMeal();
            homePresenter.getTrendingMeals();
            imageViewEmptyList.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);

            inspired_meal.setVisibility(View.VISIBLE);
            rest_meals.setVisibility(View.VISIBLE);
        }else{
            cardView.setVisibility(View.GONE);
            inspired_meal.setVisibility(View.GONE);
            rest_meals.setVisibility(View.GONE);

            imageViewEmptyList.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void showRandomData(PojoMeal meals) {
        Log.i(TAG, "showData: " + meals);
        Glide.with(this.getContext())
                .load(meals.strMealThumb)
                .centerCrop()
                .placeholder(R.drawable.molokhia)
                .into(imgView);
        meal_name.setText(meals.strMeal);
        meal_description.setText(meals.strArea);
        imageViewRandomMealFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
                    if(connectivity.isConnectedMobile()||connectivity.isConnectedWifi()){
                        homePresenter.addToFav(meals, new OnClickAddListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                        imageViewRandomMealFavorite.setImageResource(R.drawable.saveicon);
                        Toast.makeText(getContext(), "Add " + meals.getStrMeal() + " to favorite", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(requireContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();
                    }
                }else{

                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.sign_up_for_more_features)
                            .setMessage(R.string.add_your_food_preferences_plan_your_meals_and_more)
                            .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToAuthActivity();
                                }
                            }).setNegativeButton(R.string.cancel,null).show();
                }
            }
        });
        Toast.makeText(this.getContext(), "Got Random Data", Toast.LENGTH_SHORT).show();
        constraintLayoutFragmentHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionHomeFragment2ToMealDetailsFragment2 action = HomeFragmentDirections.actionHomeFragment2ToMealDetailsFragment2(meals);
                Navigation.findNavController(view).navigate(action);
                Log.i(TAG, "onClick: we clicked on the card ");
            }
        });
    }

    @Override
    public void showTrendingMeals(ArrayList<PojoMeal> meals) {
        homeAdapter.setMeals(meals);
        homeAdapter.notifyDataSetChanged();
    }
    private void goToAuthActivity() {
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void onClick(PojoMeal meal, View view) {
        Toast.makeText(getContext(), meal.strMeal, Toast.LENGTH_SHORT).show();
        ActionHomeFragment2ToMealDetailsFragment2 actions = HomeFragmentDirections.actionHomeFragment2ToMealDetailsFragment2(meal);
        Navigation.findNavController(getView()).navigate(actions);
        Log.i(TAG, "onClick:FragmentHome clicked on " + meal.strMeal);
    }


}