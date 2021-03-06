package com.geekbrains.anasdroweather2.ui.slideshow;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.rest.WeatherLoader;
import com.geekbrains.anasdroweather2.model.Constants;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter implements Observer {
    MyData myData;
    ArrayList<String> citiesList;
    WeatherLoader weatherLoader;

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
        final TextView textCityName = holder.itemView.findViewById(R.id.searchCityNameTV);
        textCityName.setText(citiesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public void updateViewData() {
        citiesList = myData.getCitiesList();
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textCityName;
        CardView cardView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            final NavController navController = myData.getNavController();
            textCityName = itemView.findViewById(R.id.searchCityNameTV);
            cardView = itemView.findViewById(R.id.myLinearCard);
            cardView.setOnCreateContextMenuListener(this);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String currentCityName = textCityName.getText().toString();
                    myData.setCurrentCity(currentCityName);
                    System.out.println("Текущий город в myData " + myData.getCurrentCity());
                    navController.navigate(R.id.nav_home);
                    myData.notifyObservers();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menu.add(0, this.getAdapterPosition(), 200, R.string.delete);
            menu.add(0, Constants.HIDE_CONTEXTMENU_ITEM, 300, R.string.delete);
        }
    }

    //удалить элемент
    public void deleteItem(int position) {
        //удалить из списка городов в myData
        if (myData.getCitiesList().size() > 1) {
            //удалить из базы данных (создается отдельный поток в методе deleteCityFromDb()
            myData.deleteCityFromDb(myData.getCitiesList().get(position));
            myData.getCitiesList().remove(position);
        } else {
            //удалить из базы данных
            //удалить из базы данных (создается отдельный поток в методе deleteCityFromDb()
            myData.deleteCityFromDb(myData.getCitiesList().get(position));
            myData.getCitiesList().remove(position);
        }
        System.out.println("myData cities list " + myData.getCitiesList().toString());
        myData.notifyObservers();
        notifyDataSetChanged();
    }
}

