package com.geekbrains.anasdroweather2.ui.slideshow;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.MainActivity;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter implements Observer {

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
        TextView textCityName = holder.itemView.findViewById(R.id.textCityName);
        textCityName.setText(citiesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }


    //done
    @Override
    public void updateViewData() {
        citiesList = myData.getCitiesList();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public MyViewHolder(final View itemView) {
            super(itemView);
            final TextView textCityName;

            final NavController navController = myData.getNavController();
            textCityName = itemView.findViewById(R.id.textCityName);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String currentCityName = textCityName.getText().toString();
                    myData.setCurrentCity(currentCityName);
                    System.out.println("Текущий город в myData " +myData.getCurrentCity());
                    myData.notifyObservers();
                    navController.navigate(R.id.nav_home);
                }
            });


//            itemView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()){
//                        case MotionEvent.ACTION_DOWN:
//                            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.colorMyPrimaryDark));
//                           final String currentCityName = textView.getText().toString();
//                            myData.setCurrentCity(currentCityName);
//                           System.out.println("Текущий город в myData " +myData.getCurrentCity());
//                           myData.notifyObservers();
//                           break;
//                        case MotionEvent.ACTION_UP:
//                            itemView.setBackgroundResource(0);
//                           // navController.navigate(R.id.nav_home);
//                            break;
//                    }
//
//                    return false;
//                }
//            });


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), Constants.HIDE_CONTEXTMENU_ITEM, 200, R.string.delete);
        }
    }


}

