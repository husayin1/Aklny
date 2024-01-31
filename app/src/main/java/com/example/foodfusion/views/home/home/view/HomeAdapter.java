package com.example.foodfusion.views.home.home.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.meal_models.PojoCategory;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredient;
import com.example.foodfusion.model.repositories.meal_models.PojoMeal;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<PojoCategory> categories;
    OnClickListener clickListener;
    Context context;


    public HomeAdapter(Context _context, List<PojoCategory> categories, OnClickListener onClickListener) {
        this.categories = categories;
        this.context = _context;
        clickListener = onClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.home_meal, parent, false);
        Log.i("TAG", "onCreateViewHolder:   Creating Home Meal");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder: " + categories.get(0).getStrCategory());

        holder.textViewName.setText(categories.get(position).strCategory);
        Glide.with(holder.getView().getContext()).load(categories.get(position).strCategoryThumb).placeholder(R.drawable.ic_launcher_foreground).into(holder.getImageView());
        holder.getView().setOnClickListener(v -> {
            clickListener.onClick(categories.get(position));
            Log.i("TAG", "onBindViewHolder: " + position);
        });
    }

    @Override
    public int getItemCount() {

        Log.i("TAG", "getItemCount: " + categories.size());
        return categories.size();
    }

    public void setMeals(List<PojoCategory> ingredients) {
        this.categories = ingredients;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imageViewHomeMeal);
            textViewName = itemView.findViewById(R.id.textViewHomeMealName);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public View getView() {
            return view;
        }


    }
}
