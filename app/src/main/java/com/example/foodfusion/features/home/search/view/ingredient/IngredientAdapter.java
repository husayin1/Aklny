package com.example.foodfusion.features.home.search.view.ingredient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodfusion.R;
import com.example.foodfusion.model.meal_models.pojos.PojoIngredient;
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = ingredients.get(holder.getAbsoluteAdapterPosition()).getStrIngredient();
        holder.textViewIngredientNameItem.setText(ingredients.get(holder.getAbsoluteAdapterPosition()).getStrIngredient());
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + name + "-Small.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.molokhia)
                        .error(R.drawable.molokhia))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageViewIngredientImageItem.setImageDrawable(resource);

                        Bitmap bitmap = Bitmap.createBitmap(resource.getIntrinsicWidth(), resource.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        resource.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        resource.draw(canvas);

                        Palette.from(bitmap).generate(palette -> {
                            int dominantColor = palette.getDominantColor(ContextCompat.getColor(context, android.R.color.black));
                            holder.imageViewIngredientImageItem.setBackgroundColor(dominantColor);
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        holder.imageViewIngredientImageItem.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
                    }
                });

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
        ImageView imageViewIngredientImageItem;
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