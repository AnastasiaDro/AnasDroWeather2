package com.geekbrains.anasdroweather2.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;


import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter {
    MyData myData;
    ArrayList<String> imgStringsList;
    ArrayList <String> tempStringsList;
    ArrayList <String> citiesNamesList;


    public SearchAdapter() {
        this.myData = MyData.getInstance();

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleDraweeView weathDraweeView;
        public TextView tempTV;
        public TextView cityNameTV;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            weathDraweeView = itemView.findViewById(R.id.searchWeathImg);
            tempTV = itemView.findViewById(R.id.searchCityTempTV);
            cityNameTV = itemView.findViewById(R.id.searchCityNameTV);
            cardView = itemView.findViewById(R.id.mySearchCard);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
