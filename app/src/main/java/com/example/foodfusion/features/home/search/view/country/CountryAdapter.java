package com.example.foodfusion.features.home.search.view.country;


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
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoArea;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
//country search adapter
    private Context context;
    private List<PojoArea> areas;
    OnCountryClickListener listener;
    public void setCountryList(List<PojoArea> areas) {
        this.areas = areas;
    }


    public CountryAdapter(Context context, List<PojoArea> areas , OnCountryClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.search_by_country_item,parent,false);
        CountryAdapter.ViewHolder vh = new CountryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        String name = areas.get(position).strArea;
        holder.textViewCountrySearch.setText(areas.get(position).strArea);
        if(areas.get(position).strArea.equals("Unknown")){
            holder.textViewCountrySearch.setText("Palestine");
        }
        String image = areas.get(position).getThumbnail();
        Glide.with(holder.itemView.getContext()).load(image).placeholder(R.drawable.molokhia).error(R.drawable.molokhia).into(holder.imgView);

        holder.countryItemLayout.setOnClickListener(new View.OnClickListener() {
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



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewCountrySearch;
        ConstraintLayout countryItemLayout;
        View layout;
        RoundedImageView imgView;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            imgView = v.findViewById(R.id.imgView);
            textViewCountrySearch = v.findViewById(R.id.textViewCountrySearch);
            countryItemLayout = v.findViewById(R.id.countryItemLayout);
        }
    }
}
