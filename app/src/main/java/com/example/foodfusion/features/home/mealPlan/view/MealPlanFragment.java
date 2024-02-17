package com.example.foodfusion.features.home.mealPlan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodfusion.R;
import com.example.foodfusion.features.home.mealPlan.presenter.MealPlanPresenter;
import com.example.foodfusion.features.home.mealPlan.presenter.MealPlanPresenterInterface;
import com.example.foodfusion.localDataSource.MealLocalDataSource;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.meal_models.pojos.MealToMealPlanner;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.example.foodfusion.utilities.Connectivity;
import com.example.foodfusion.utilities.DateFormat;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlanFragment extends Fragment implements MealPlanView,OnDeleteListener,OnDetailsListener{
    private static final String TAG = "MealPlanFragment";
    CalendarView calendarView;
    RecyclerView recyclerViewMealPlan;
    MealPlanAdapter mealPlanAdapter;
    LottieAnimationView imageViewEmptyListMeals;
    LinearLayoutManager layoutManager;
    MealPlanPresenterInterface mealPlanPresenter;
    Connectivity connectivity;
    public MealPlanFragment() {
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
        connectivity = new Connectivity(requireContext());
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        recyclerViewMealPlan = view.findViewById(R.id.recyclerViewMealPlan);
        imageViewEmptyListMeals = view.findViewById(R.id.imageViewEmptyListMeals);
        mealPlanPresenter = new MealPlanPresenter(this, FavAndPlannerRepo.getInstance(getContext()));
        layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mealPlanAdapter = new MealPlanAdapter(new ArrayList<>(),getContext(),this,this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DAY_OF_MONTH,6);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setMaxDate(calendar.getTimeInMillis());
        mealPlanPresenter.getPlannedMealsByDate(DateFormat.getString(year,month,day));
        recyclerViewMealPlan.setLayoutManager(layoutManager);
        recyclerViewMealPlan.setAdapter(mealPlanAdapter);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                mealPlanPresenter.getPlannedMealsByDate(DateFormat.getString(year,month,day));
            }
        });

        return view;
    }

    @Override
    public void showData(List<PojoPlanner> meal) {
        Log.i(TAG, "showData: from meal plan fraggment"+meal.size());
        if(meal.isEmpty()){
            imageViewEmptyListMeals.setVisibility(View.VISIBLE);
            mealPlanAdapter.setList(new ArrayList<>());
            mealPlanAdapter.  notifyDataSetChanged();
        }else{
            imageViewEmptyListMeals.setVisibility(View.GONE);
            mealPlanAdapter.setList(meal);
            Log.i(TAG, "showData: "+meal.get(0).strMeal);
            mealPlanAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteMeal(PojoPlanner meal) {
        Log.i(TAG, "deleteMeal: meal "+meal.strMeal);
        mealPlanPresenter.removeItemFromPlanner(meal);
    }

    @Override
    public void onClickListener(PojoPlanner meal) {
        if(connectivity.isConnectedMobile()||connectivity.isConnectedWifi()){
            deleteMeal(meal);
            mealPlanAdapter.notifyDataSetChanged();
            Toast.makeText(this.getContext(), R.string.removed_item, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(requireContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClickDetailsListener(PojoPlanner planner) {
        MealPlanFragmentDirections.ActionMealPlanFragmentToMealDetailsFragment action = MealPlanFragmentDirections.actionMealPlanFragmentToMealDetailsFragment(MealToMealPlanner.getMealFromMealPlanner(planner));
        Navigation.findNavController(getView()).navigate(action);
    }
}