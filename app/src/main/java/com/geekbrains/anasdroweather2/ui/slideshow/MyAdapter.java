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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.MainActivity;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;

import java.util.ArrayList;
import java.util.List;

//public class MyAdapter extends RecyclerView.Adapter implements Observer, Filterable {

public class MyAdapter extends RecyclerView.Adapter implements Observer {
    MyData myData;
    //ArrayList <String> citiesListFull;
    ArrayList <String> citiesList;

    public MyAdapter() {
        this.myData = MyData.getInstance();
        //this.citiesListFull = myData.getCitiesList();
//        citiesList = new ArrayList();
//        citiesList.addAll(citiesListFull);
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
        final TextView textCityName = holder.itemView.findViewById(R.id.textCityName);
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
        TextView textCityName;
        CardView cardView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            final NavController navController = myData.getNavController();
            textCityName = itemView.findViewById(R.id.textCityName);
            cardView = itemView.findViewById(R.id.myLinearCard);
            cardView.setOnCreateContextMenuListener(this);

            textCityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String currentCityName = textCityName.getText().toString();
                    myData.setCurrentCity(currentCityName);
                    System.out.println("Текущий город в myData " + myData.getCurrentCity());
                    myData.notifyObservers();
                    navController.navigate(R.id.nav_home);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), Constants.HIDE_CONTEXTMENU_ITEM, 200, R.string.delete);
        }
    }

    public void deleteItem(int position) {
        myData.getCitiesList().remove(position);
        System.out.println("myData cities list " + myData.getCitiesList().toString());
        myData.notifyObservers();
        notifyDataSetChanged();

    }

//    @Override
//    public Filter getFilter() {
//        return citiesListFilter;
//    }
//
//    private Filter citiesListFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List <String> filteredList = new <String> ArrayList();
//            if (constraint == null || constraint.length() == 0){
//                filteredList.addAll(citiesListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for(String item : citiesListFull) {
//                    if (item.toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            citiesList.clear();
//            citiesList.addAll((List)results.values);
//            notifyDataSetChanged();
//        }
//    };
}

