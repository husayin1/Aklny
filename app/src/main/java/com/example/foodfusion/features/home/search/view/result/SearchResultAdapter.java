package com.example.foodfusion.features.home.search.view.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMainMeal;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{
    private static final String TAG = "MealResultAdapter";
    private final Context context;
    private List<PojoMainMeal> mainMealsList;
    private OnSearchResultClick listener;

    public List<PojoMainMeal> getMainMealsList() {
        return mainMealsList;
    }


    public SearchResultAdapter(Context context, List<PojoMainMeal> mainMealsList, OnSearchResultClick listener) {
        this.context = context;
        this.mainMealsList = mainMealsList;
        this.listener = listener;
    }

    public void setMainMealsList(List<PojoMainMeal> mainMealsList) {
        this.mainMealsList = mainMealsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.main_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewMainMealName.setText(mainMealsList.get(position).getStrMeal());
        Glide.with(context)
                .load(mainMealsList.get(position).getStrMealThumb())
                .into(holder.imageViewMainMeal);

        holder.cardViewMainMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onResultClick(mainMealsList.get(holder.getAbsoluteAdapterPosition()).getIdMeal());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainMealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMainMealName;
        ImageView imageViewMainMeal;
        CardView cardViewMainMeal;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            textViewMainMealName = v.findViewById(R.id.textViewMainMealName);
            imageViewMainMeal = v.findViewById(R.id.imageViewMainMeal);
            cardViewMainMeal = v.findViewById(R.id.cardViewMainMeal);
        }
    }
}
