package com.example.foodfusion.features.home.favorite.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import com.example.foodfusion.model.meal_models.pojos.PojoMeal;
import com.example.foodfusion.features.home.meal_details.view.OnClickDetailsListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<PojoMeal> meal;
    private OnFavoriteClickListener listener;
    private OnClickDetailsListener listenerForDetails;

    public static String TAG = "FavoriteAdapter";

    public FavoriteAdapter(List<PojoMeal> meal, OnFavoriteClickListener listener, Context context, OnClickDetailsListener listenerForDetails) {
        this.meal = meal;
        this.listener = listener;
        this.context = context;
        this.listenerForDetails = listenerForDetails;
    }

    public void setList(List<PojoMeal> updatedMeals) {
        this.meal = updatedMeals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.favorite_meal, parent, false);
        FavoriteAdapter.ViewHolder vh = new FavoriteAdapter.ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder:  Creating FavoriteMeal");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoMeal pojo = meal.get(position);
        holder.textViewFavoriteMealName.setText(meal.get(position).getStrMeal());
        holder.textViewFavoriteMealCountry.setText(meal.get(position).getStrArea());
        Glide.with(context)
                .load(meal.get(position).getStrMealThumb())
                .centerCrop()
                .placeholder(R.drawable.molokhia)
                .into(holder.imageViewFavoriteMeal);
        holder.imageViewRandomMealFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete From Saves")
                        .setMessage("Are you sure?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener.onFavoriteClick(pojo);
                            }
                        }).setNegativeButton(R.string.no, null).show();
//                listener.onFavoriteClick(pojo);

            }
        });
        holder.constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerForDetails.onClickListener(pojo);
            }
        });
        Log.i(TAG, "onBindViewHolder:  Binding Rows");

    }

    @Override
    public int getItemCount() {
        return meal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageViewFavoriteMeal;
        public TextView textViewFavoriteMealName;
        public TextView textViewFavoriteMealCountry;
        public ImageView imageViewRandomMealFavorite;

        public CardView constraintLayout2;
        public View layout2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout2 = itemView;
            constraintLayout2 = itemView.findViewById(R.id.carView_favorite_meal);
            imageViewFavoriteMeal = (RoundedImageView) itemView.findViewById(R.id.imageViewFavoriteMeal);
            textViewFavoriteMealName = (TextView) itemView.findViewById(R.id.textViewFavoriteMealName);
            textViewFavoriteMealCountry = (TextView) itemView.findViewById(R.id.textViewFavoriteMealCountry);
            imageViewRandomMealFavorite = (ImageView) itemView.findViewById(R.id.imageViewRandomMealFavorite);
        }
    }

}
