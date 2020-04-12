package com.geekbrains.anasdroweather2.ui.slideshow;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    MyData myData;
    ArrayList citiesList;

    public MyAdapter() {
        this.myData = MyData.getInstance();
        this.citiesList = myData.getCitiesList();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.textCityName);
        textView.setText(citiesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public MyViewHolder(final View itemView) {
            super(itemView);
            final TextView textView = itemView.findViewById(R.id.textCityName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Связано с выделением
                    int currentPosition = getAdapterPosition();
                    //   adapterPos = currentPosition;

                }
            });
        }
    }

}

