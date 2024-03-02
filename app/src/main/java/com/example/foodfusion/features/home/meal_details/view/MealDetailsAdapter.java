package com.example.foodfusion.features.home.meal_details.view;

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
import com.example.foodfusion.model.meal_models.pojos.PojoIngredientWithMeasure;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {

    List<PojoIngredientWithMeasure> ingredientWithMeasuresList;
    Context context;
    String imageUrl;
    OnIngredientClickListener _listener;

    public List<PojoIngredientWithMeasure> getIngredientWithMeasuresList() {
        return ingredientWithMeasuresList;
    }

    public void setIngredientWithMeasuresList(List<PojoIngredientWithMeasure> ingredientWithMeasuresList) {
        this.ingredientWithMeasuresList = ingredientWithMeasuresList;
    }

    public MealDetailsAdapter(Context _context, List<PojoIngredientWithMeasure> ingredientWithMeasuresList, OnIngredientClickListener _listener) {
        this.ingredientWithMeasuresList = ingredientWithMeasuresList;
        this.context = _context;
        this._listener = _listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.ingredients_meal_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoIngredientWithMeasure item = ingredientWithMeasuresList.get(position);
        holder.textViewIngredientMeasureItem.setText(ingredientWithMeasuresList.get(position).getIngredientMeasure());
        holder.textViewIngredientNameItem_mealDetails.setText(ingredientWithMeasuresList.get(position).getIngredientName());
        imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientWithMeasuresList.get(position).getIngredientName() + ".png";
        /*Glide.with(
                        holder.getView().getContext())
                .load(imageUrl)
                .placeholder(R.drawable.molokhia).into(holder.getImageView());
        holder.IngredientConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _listener.onIngredientClickListener(item);
            }
        });*/

        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.molokhia)
                        .error(R.drawable.molokhia))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageViewIngredientImageItem_mealDetails.setImageDrawable(resource);

                        Bitmap bitmap = Bitmap.createBitmap(resource.getIntrinsicWidth(), resource.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        resource.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        resource.draw(canvas);

                        Palette.from(bitmap).generate(palette -> {
                            int dominantColor = palette.getDominantColor(ContextCompat.getColor(context, android.R.color.black));
                            holder.imageViewIngredientImageItem_mealDetails.setBackgroundColor(dominantColor);
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        holder.imageViewIngredientImageItem_mealDetails.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
                    }
                });

    }

    @Override
    public int getItemCount() {
        return ingredientWithMeasuresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewIngredientImageItem_mealDetails;
        private final TextView textViewIngredientNameItem_mealDetails;
        private final TextView textViewIngredientMeasureItem;

        ConstraintLayout IngredientConstraint;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            IngredientConstraint = itemView.findViewById(R.id.IngredientConstraint);
            imageViewIngredientImageItem_mealDetails = itemView.findViewById(R.id.imageViewIngredientImageItem_mealDetails);
            textViewIngredientNameItem_mealDetails = itemView.findViewById(R.id.textViewIngredientNameItem_mealDetails);
            textViewIngredientMeasureItem = itemView.findViewById(R.id.textViewIngredientMeasureItem);
        }

        public ImageView getImageView() {
            return imageViewIngredientImageItem_mealDetails;
        }

        public TextView getTextViewName() {
            return textViewIngredientNameItem_mealDetails;
        }

        public TextView getTextViewMeasure() {
            return textViewIngredientMeasureItem;
        }

        public View getView() {
            return view;
        }


    }
}
