package com.example.foodfusion.features.home.search.view.category;

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
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoCategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<PojoCategory> categories;

    private final OnCategoryClickListener listener;
    public void setCategoryList(List<PojoCategory> categories) {
        this.categories = categories;
    }


    public CategoryAdapter(Context context, List<PojoCategory> categories , OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.search_by_category_item,parent,false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = categories.get(position).getStrCategory();
        holder.textViewCategoryItemName.setText(categories.get(position).getStrCategory());
        Glide.with(context)
                .load(categories.get(position).getStrCategoryThumb())
                .into(holder.imageViewCategoryItem);

        holder.cardViewCategorySearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewCategoryItemName;
        ImageView imageViewCategoryItem;
        CardView cardViewCategorySearchItem;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            textViewCategoryItemName = v.findViewById(R.id.textViewCategoryItemName);
            imageViewCategoryItem = v.findViewById(R.id.imageViewCategoryItem);
            cardViewCategorySearchItem = v.findViewById(R.id.cardViewCategorySearchItem);
        }
    }

}
