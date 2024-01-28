package com.example.foodfusion.views.home.meal_details.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodfusion.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;

import de.hdodenhof.circleimageview.CircleImageView;

public class MealDetailsFragment extends Fragment {
    CircleImageView mealDetailsImage;
    TextView textViewMealCateItemDetails;
    TextView textViewMealCountryItemDetails;
    TextView txtViewMealNameItemDetails;
    ImageView imageViewAddToCalendarItemDetails;
    ImageView imageViewAddToFavITemDetails;
    RecyclerView recyclerViewIngredientsItemDetails;
    TextView textViewProcedures;
    YouTubePlayer ytPlayer;
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }


}