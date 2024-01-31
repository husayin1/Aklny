package com.example.foodfusion.views.home.meal_details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodfusion.MainActivity;
import com.example.foodfusion.R;
import com.example.foodfusion.localDataSource.MealLocalDataSource;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredientWithMeasure;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;
import com.example.foodfusion.views.home.home.view.HomeAdapter;
import com.example.foodfusion.views.home.meal_details.presenter.MealDetailsPresenter;
import com.example.foodfusion.views.home.meal_details.presenter.MealDetailsPresenterInterface;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MealDetailsFragment extends Fragment implements OnClickListener,MealDetailsView{
    CircleImageView mealDetailsImage;
    TextView textViewMealCateItemDetails;
    TextView textViewMealCountryItemDetails;
    TextView txtViewMealNameItemDetails;
    ImageView imageViewAddToCalendarItemDetails;
    ImageView imageViewAddToFavITemDetails;
    RecyclerView recyclerViewIngredientsItemDetails;
    TextView textViewProcedures;
    YouTubePlayerView youTubePlayerView;
    PojoMeal pojo;
    MealDetailsAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    MealDetailsPresenterInterface mealDetailsPresenterInterface;

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
        mealDetailsPresenterInterface = new MealDetailsPresenter(this, MealLocalDataSource.getInstance(this.getContext()));

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


        adapter = new MealDetailsAdapter(this.getContext(), new ArrayList<>());
        recyclerViewIngredientsItemDetails.setAdapter(adapter);
        adapter.setIngredientWithMeasuresList(getArrayList(pojo));


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String videoMealDetail = getVideoLink(pojo.getStrYoutube());
//                youTubePlayer.loadVideo(videoMealDetail,0);
                youTubePlayer.cueVideo(videoMealDetail, 0);
//                youTubePlayer.pause();
            }
        });

        txtViewMealNameItemDetails.setText(pojo.getStrMeal());
        textViewMealCateItemDetails.setText(pojo.getStrCategory());
        textViewMealCountryItemDetails.setText(pojo.getStrArea());
        textViewProcedures.setText(pojo.getStrInstructions());
        Glide.with(this.getContext())
                .load(pojo.getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.molokhia)
                .into(mealDetailsImage);

        imageViewAddToFavITemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener(pojo);
                Toast.makeText(getContext(), "Added"+pojo.getStrMeal()+" to favorite", Toast.LENGTH_SHORT).show();
                imageViewAddToFavITemDetails.setImageResource(R.drawable.favrepo);
            }
        });
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

    @Override
    public void onClickListener(PojoMeal meal) {
        mealDetailsPresenterInterface.addToFav(meal);
    }
}