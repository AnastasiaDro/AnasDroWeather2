package com.geekbrains.anasdroweather2.ui.gallery;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.geekbrains.anasdroweather2.R;

public class SearchAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        //Элементы в recyclerView
        SimpleDraweeView searchDraweeView;
        TextView searchCityTemp;
        TextView searchCityName;
        LinearLayout searchCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            searchDraweeView = itemView.findViewById(R.id.searchWeathImg);
            searchCityTemp = itemView.findViewById(R.id.searchCityTempTV);
            searchCityName = itemView.findViewById(R.id.searchCityNameTV);
            searchCard = itemView.findViewById(R.id.mySearchCard);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
