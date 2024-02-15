package com.example.foodfusion.features.home.meal_details.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.features.Authentication.AuthenticationActivity;
import com.example.foodfusion.features.home.search.view.result.SearchType;
import com.example.foodfusion.localDataSource.MealLocalDataSource;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.model.repositories.local_repo.OnClickAddListener;
import com.example.foodfusion.model.repositories.meal_models.pojos.MealToMealPlanner;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredientWithMeasure;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.meal_details.presenter.MealDetailsPresenter;
import com.example.foodfusion.features.home.meal_details.presenter.MealDetailsPresenterInterface;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.example.foodfusion.utilities.Connectivity;
import com.example.foodfusion.utilities.ConvertMealToString;
import com.example.foodfusion.utilities.DateFormat;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MealDetailsFragment extends Fragment implements MealDetailsView, OnIngredientClickListener {
    private final static String TAG = "MealDetailsFragment";
    CircleImageView mealDetailsImage;
    TextView textViewMealCateItemDetails, textViewMealCountryItemDetails, txtViewMealNameItemDetails, textViewProcedures;
    ImageView imageViewAddToCalendarItemDetails, imageViewAddToFavITemDetails;
    RecyclerView recyclerViewIngredientsItemDetails;
    YouTubePlayerView youTubePlayerView;
    PojoMeal pojo;
    MealDetailsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    MealDetailsPresenterInterface mealDetailsPresenterInterface;
    Connectivity connectivity;
    boolean isSaved;

    public MealDetailsFragment() {
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
        pojo = MealDetailsFragmentArgs.fromBundle(getArguments()).getTransferMeal();
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealDetailsPresenterInterface = new MealDetailsPresenter(this, FavAndPlannerRepo.getInstance(this.getContext()));
        connectivity = new Connectivity(requireContext());

        mealDetailsImage = view.findViewById(R.id.mealDetailsImage);
        textViewMealCateItemDetails = view.findViewById(R.id.textViewMealCateItemDetails);
        textViewMealCountryItemDetails = view.findViewById(R.id.textViewMealCountryItemDetails);
        txtViewMealNameItemDetails = view.findViewById(R.id.txtViewMealNameItemDetails);
        imageViewAddToCalendarItemDetails = view.findViewById(R.id.imageViewAddToCalendarItemDetails);
        imageViewAddToFavITemDetails = view.findViewById(R.id.imageViewAddToFavITemDetails);
        recyclerViewIngredientsItemDetails = view.findViewById(R.id.recyclerViewIngredientsItemDetails);
        textViewProcedures = view.findViewById(R.id.textViewProcedures);
        recyclerViewIngredientsItemDetails = view.findViewById(R.id.recyclerViewIngredientsItemDetails);
        youTubePlayerView = view.findViewById(R.id.youTubePlayerView);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewIngredientsItemDetails.setLayoutManager(linearLayoutManager);


        adapter = new MealDetailsAdapter(this.getContext(), new ArrayList<>(), this);
        recyclerViewIngredientsItemDetails.setAdapter(adapter);
        adapter.setIngredientWithMeasuresList(getArrayList(pojo));


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String videoMealDetail = getVideoLink(pojo.getStrYoutube());
                youTubePlayer.cueVideo(videoMealDetail, 0);
            }
        });
        String id = pojo.getIdMeal();
        getMealLocal(id);

        txtViewMealNameItemDetails.setText(pojo.getStrMeal());
        textViewMealCateItemDetails.setText(pojo.getStrCategory());
        textViewMealCountryItemDetails.setText(pojo.getStrArea());
        textViewProcedures.setText(formatText(pojo.getStrInstructions()));
        Glide.with(requireContext())
                .load(pojo.getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.molokhia)
                .into(mealDetailsImage);

        imageViewAddToFavITemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivity.isConnectedMobile() || connectivity.isConnectedWifi()) {
                    if (AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
                        if (!isSaved) {
                            mealDetailsPresenterInterface.addToFav(pojo, new OnClickAddListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getContext(), "Added" + pojo.getStrMeal() + " to Saved", Toast.LENGTH_SHORT).show();
                                    imageViewAddToFavITemDetails.setImageResource(R.drawable.saveicon);
                                }

                                @Override
                                public void onFailure(String err) {
                                    Toast.makeText(getContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            new AlertDialog.Builder(view.getContext())
                                    .setTitle("UnSave")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mealDetailsPresenterInterface.removeFromFavorite(pojo);
                                            imageViewAddToFavITemDetails.setImageResource(R.drawable.save);
                                        }
                                    }).setNegativeButton(R.string.no, null).show();
                        }
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.sign_up_for_more_features)
                                .setMessage(R.string.add_your_food_preferences_plan_your_meals_and_more)
                                .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        goToAuthActivity();
                                    }
                                }).setNegativeButton(R.string.cancel, null).show();
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();
                }

            }
        });

        imageViewAddToCalendarItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivity.isConnectedMobile() || connectivity.isConnectedWifi()) {
                    if (AuthenticationFireBaseRepo.getInstance().isAuthenticated()) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        calendar.add(Calendar.DAY_OF_MONTH, 6);
                        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                requireContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        String selectedDay = day + "/" + (month + 1) + "/" + year;
                                        mealDetailsPresenterInterface.addToPlanner(MealToMealPlanner.MealToMealPlanner(pojo, DateFormat.getString(year, month, day), 0), new OnClickAddListener() {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(view.getContext(), "Added Successfully to planner", Toast.LENGTH_SHORT).show();
                                                imageViewAddToCalendarItemDetails.setImageResource(R.drawable.calendar);
                                                Intent intent = new Intent(Intent.ACTION_INSERT)
                                                        .setData(CalendarContract.Events.CONTENT_URI)
                                                        .putExtra(CalendarContract.Events.TITLE, pojo.strMeal)
                                                        .putExtra(CalendarContract.Events.DESCRIPTION, ConvertMealToString.getMealString(pojo));
                                                startActivity(intent);

                                            }

                                            @Override
                                            public void onFailure(String err) {
                                                Toast.makeText(view.getContext(), err, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Toast.makeText(getContext(), "Saved " + pojo.strMeal + " In " + selectedDay, Toast.LENGTH_SHORT).show();
                                        Log.i(TAG, "onDateSet: " + DateFormat.getString(year, month, day));
                                    }
                                }, year, month, day);
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                        datePickerDialog.show();
                    } else {
                        //sign in
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.sign_up_for_more_features)
                                .setMessage(R.string.add_your_food_preferences_plan_your_meals_and_more)
                                .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        goToAuthActivity();
                                    }
                                }).setNegativeButton(R.string.cancel, null).show();
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.please_check_your_internet_connection_and_try_again, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getMealLocal(String id) {
        mealDetailsPresenterInterface.getFavMealById(id).observe(getViewLifecycleOwner(), new Observer<PojoMeal>() {
            @Override
            public void onChanged(PojoMeal pojoMeal) {
                if (pojoMeal != null) {
                    isSaved = true;
                    imageViewAddToFavITemDetails.setImageResource(R.drawable.saveicon);
                } else {
                    isSaved = false;
                    imageViewAddToFavITemDetails.setImageResource(R.drawable.save);
                }
            }
        });
    }

    private void goToAuthActivity() {
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    void makeIngredientsArray(PojoMeal pojo, ArrayList<PojoIngredientWithMeasure> ingredientList) {
        addToList(pojo.strIngredient1, pojo.strMeasure1, ingredientList);
        addToList(pojo.strIngredient2, pojo.strMeasure2, ingredientList);
        addToList(pojo.strIngredient3, pojo.strMeasure3, ingredientList);
        addToList(pojo.strIngredient4, pojo.strMeasure4, ingredientList);
        addToList(pojo.strIngredient5, pojo.strMeasure5, ingredientList);
        addToList(pojo.strIngredient6, pojo.strMeasure6, ingredientList);
        addToList(pojo.strIngredient7, pojo.strMeasure7, ingredientList);
        addToList(pojo.strIngredient8, pojo.strMeasure8, ingredientList);
        addToList(pojo.strIngredient9, pojo.strMeasure9, ingredientList);
        addToList(pojo.strIngredient10, pojo.strMeasure10, ingredientList);
        addToList(pojo.strIngredient11, pojo.strMeasure11, ingredientList);
        addToList(pojo.strIngredient12, pojo.strMeasure12, ingredientList);
        addToList(pojo.strIngredient13, pojo.strMeasure13, ingredientList);
        addToList(pojo.strIngredient14, pojo.strMeasure14, ingredientList);
        addToList(pojo.strIngredient15, pojo.strMeasure15, ingredientList);
        addToList(pojo.strIngredient16, pojo.strMeasure16, ingredientList);
        addToList(pojo.strIngredient17, pojo.strMeasure17, ingredientList);
        addToList(pojo.strIngredient18, pojo.strMeasure18, ingredientList);
        addToList(pojo.strIngredient19, pojo.strMeasure19, ingredientList);
        addToList(pojo.strIngredient20, pojo.strMeasure20, ingredientList);

    }

    void addToList(String strIngredient, String strMeasure, ArrayList<PojoIngredientWithMeasure> ingredientList) {
        if (strIngredient != null && !strIngredient.isEmpty()) {
            String measure;
            measure = strMeasure;
            ingredientList.add(new PojoIngredientWithMeasure(strIngredient, measure));
        }
    }

    ArrayList<PojoIngredientWithMeasure> getArrayList(PojoMeal meal) {
        ArrayList<PojoIngredientWithMeasure> list = new ArrayList<>();
        makeIngredientsArray(meal, list);
        return list;
    }

    public String getVideoLink(String link) {
        if (link != null && link.split("\\?v=").length > 1)
            return link.split("\\?v=")[1];
        else return "";
    }

    private String formatText(String strInstructions) {
        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }

    @Override
    public void onIngredientClickListener(PojoIngredientWithMeasure ingredient) {
        SearchType searchType = new SearchType(SearchType.ingredient, ingredient.getIngredientName());
        MealDetailsFragmentDirections.ActionMealDetailsFragmentToSearchResultFragment action = MealDetailsFragmentDirections.actionMealDetailsFragmentToSearchResultFragment(searchType);
        Navigation.findNavController(requireView()).navigate(action);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
    }
}