package com.example.foodfusion.views.home.meal_details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.model.repositories.meal_models.PojoIngredientWithMeasure;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {

    List<PojoIngredientWithMeasure> ingredientWithMeasuresList;
    Context context;
    String imageUrl;

    public List<PojoIngredientWithMeasure> getIngredientWithMeasuresList() {
        return ingredientWithMeasuresList;
    }

    public void setIngredientWithMeasuresList(List<PojoIngredientWithMeasure> ingredientWithMeasuresList) {
        this.ingredientWithMeasuresList = ingredientWithMeasuresList;
    }

    public MealDetailsAdapter(Context _context, List<PojoIngredientWithMeasure> ingredientWithMeasuresList) {
        this.ingredientWithMeasuresList = ingredientWithMeasuresList;
        this.context = _context;
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
        holder.textViewIngredientMeasureItem.setText(ingredientWithMeasuresList.get(position).getIngredientMeasure());
        holder.textViewIngredientNameItem_mealDetails.setText(ingredientWithMeasuresList.get(position).getIngredientName());
        imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientWithMeasuresList.get(position).getIngredientName() + ".png";
        Glide.with(
                        holder.getView().getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return ingredientWithMeasuresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RoundedImageView imageViewIngredientImageItem_mealDetails;
        private final TextView textViewIngredientNameItem_mealDetails;
        private final TextView textViewIngredientMeasureItem;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
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
