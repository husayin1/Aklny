package com.example.foodfusion.features.home.search.view.country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfusion.R;
import com.example.foodfusion.features.home.search.view.result.SearchType;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoArea;

import java.util.List;

public class CountryFragmentAdapter extends RecyclerView.Adapter<CountryFragmentAdapter.ViewHolder>{
    //adapter for all countries search

    private final Context context;
    private List<PojoArea> areas;

    public void setCountryList(List<PojoArea> areas) {
        this.areas = areas;
        notifyDataSetChanged();
    }

    private OnCountryClickListener listener;

    public CountryFragmentAdapter(Context context, List<PojoArea> areas, OnCountryClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.countries_item, parent, false);
        CountryFragmentAdapter.ViewHolder vh = new CountryFragmentAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryFragmentAdapter.ViewHolder holder, int position) {
        SearchType searchType = new SearchType();
        String name = areas.get(position).getStrArea();
        String image = areas.get(position).getThumbnail();
        holder.textViewCountriesSearch.setText(areas.get(position).getStrArea());
        Glide.with(holder.itemView.getContext()).load(image).placeholder(R.drawable.molokhia).error(R.drawable.molokhia).into(holder.imageViewCountry);
        holder.countriesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCountryClick(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCountriesSearch;
        ConstraintLayout countriesLayout;
        View layout;
        ImageView imageViewCountry;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            imageViewCountry = v.findViewById(R.id.imageViewCountry);
            textViewCountriesSearch = v.findViewById(R.id.textViewCountriesSearch);
            countriesLayout = v.findViewById(R.id.countriesLayout);
        }
    }

}
