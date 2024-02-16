package com.example.foodfusion.features.home.search.view.ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.view.result.SearchType;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoIngredient;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final Context context;
    private List<PojoIngredient> ingredients;

    private OnIngredientClickListener listener;

    public List<PojoIngredient> getingredientList() {
        return ingredients;
    }

    public void setIngredientList(List<PojoIngredient> ingredientList) {
        this.ingredients = ingredientList;
        notifyDataSetChanged();
    }

    public IngredientAdapter(Context context, List<PojoIngredient> ingredients, OnIngredientClickListener listener) {
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.search_by_ingredient_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = ingredients.get(holder.getAbsoluteAdapterPosition()).getStrIngredient();
        holder.textViewIngredientNameItem.setText(ingredients.get(holder.getAbsoluteAdapterPosition()).getStrIngredient());
        Glide
                .with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + name + "-Small.png").placeholder(R.drawable.molokhia).error(R.drawable.molokhia).into(holder.imageViewIngredientImageItem);
        holder.ingredientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIngredientClick(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIngredientNameItem;
        RoundedImageView imageViewIngredientImageItem;
        ConstraintLayout ingredientLayout;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            textViewIngredientNameItem = v.findViewById(R.id.textViewIngredientNameItem);
            imageViewIngredientImageItem = v.findViewById(R.id.imageViewIngredientImageItem);
            ingredientLayout = v.findViewById(R.id.ingredientLayout);
        }
    }
}