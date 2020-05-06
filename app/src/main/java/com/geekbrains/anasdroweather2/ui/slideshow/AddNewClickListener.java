package com.geekbrains.anasdroweather2.ui.slideshow;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

public class AddNewClickListener implements View.OnClickListener {
    private String myNewString;
    private MyData myData;

    public AddNewClickListener() {
        this.myData = MyData.getInstance();
    }

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        final EditText input = new EditText(context);
        createInputDialog(context, input);
    }

    //делаем диалог с юзером для добавления нового значения в отображаемый массив
    protected void createInputDialog(Context context, final EditText input) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enterCity);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myNewString = input.getText().toString();
                addCityToMyData(myNewString);
                myData.setCurrentCity(myNewString);
                //myData.notifyObservers();
                myData.getNavController().navigate(R.id.nav_home);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    //добавляет новую строку в базу данных
    public void addCityToMyData(String myNewString) {
        //myData.getCitiesList().add(myNewString);
        myData.addNewCityIfNotExist(myNewString);
        //обновляем MyData и уведомляем слушателей
        myData.notifyObservers();
    }

}
