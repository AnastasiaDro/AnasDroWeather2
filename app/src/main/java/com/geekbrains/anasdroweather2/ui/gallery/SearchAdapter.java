package com.geekbrains.anasdroweather2.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.slideshow.MyAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;


import java.util.ArrayList;
import java.util.Observable;

public class SearchAdapter extends RecyclerView.Adapter implements Observer {
    MyData myData;
    ArrayList<String> imgStringsList;
    ArrayList <String> tempStringsList;
    ArrayList <String> citiesNamesList;
    String imgString;
    public SimpleDraweeView weathDraweeView;
    public TextView tempTV;
    public TextView cityNameTV;

    public SearchAdapter() {
        this.myData = MyData.getInstance();
        imgString = null;
        imgStringsList = myData.getImgStringsList();
        tempStringsList = myData.getTempStringsList();
        citiesNamesList = myData.getCitiesNamesList();

    }

    @Override
    public void updateViewData() {
        imgStringsList = myData.getImgStringsList();

        tempStringsList = myData.getTempStringsList();
        citiesNamesList = myData.getCitiesNamesList();
        getItemCount();
        this.notifyDataSetChanged();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleDraweeView weathDraweeView;
        public TextView tempTV;
        public TextView cityNameTV;
        RelativeLayout cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            final NavController navController = myData.getNavController();
            weathDraweeView = itemView.findViewById(R.id.searchWeathImg);
            tempTV = itemView.findViewById(R.id.searchCityTempText);
            cityNameTV = itemView.findViewById(R.id.searchCityNameText);
            cardView = itemView.findViewById(R.id.mySearchCard);


            cardView.setOnClickListener(new View.OnClickListener() {
//            cityNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String selectedCityName = cityNameTV.getText().toString();
                    final String selectedCityTemp = tempTV.getText().toString();
                    final String selectedCityImg = imgString;
                    myData.setCurrentCity(selectedCityName);
                    navController.navigate(R.id.nav_home);
                    myData.notifyObservers();
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        MyViewHolder myViewHolder = new SearchAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SimpleDraweeView draweeView = holder.itemView.findViewById(R.id.searchWeathImg);
        final TextView searchedCityTemp = holder.itemView.findViewById(R.id.searchCityTempText);
        final TextView searchedCityName = holder.itemView.findViewById(R.id.searchCityNameText);
        imgString = imgStringsList.get(position);
        myData.getImageLoader().loadDraweeImage(draweeView, imgString);
        searchedCityTemp.setText(tempStringsList.get(position));
        searchedCityName.setText(citiesNamesList.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesNamesList.size();
    }

}
