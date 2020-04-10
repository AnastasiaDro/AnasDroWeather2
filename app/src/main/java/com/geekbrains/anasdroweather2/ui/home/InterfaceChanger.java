package com.geekbrains.anasdroweather2.ui.home;

import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

public class InterfaceChanger implements Observer {
    MyData myData;
    AppCompatActivity activity;
    ActionBar actionBar;
    int actionBarColor;

    public InterfaceChanger(AppCompatActivity activity) {
        this.activity = activity;
        myData = MyData.getInstance();
        actionBar = activity.getSupportActionBar();
    }

    //автоматически задаём тему, если это разрешено в MyData
    public void setAutoTheme() {
            if (myData.getAutoThemeChanging()){
                if (myData.getCurrentHour()<8 || myData.getCurrentHour()>=19) {
//не получилось менять цвет actionBar-а через ресурсы, поэтому поменяем так
                    activity.setTheme(R.style.MyDarkTheme);
                    actionBarColor = ContextCompat.getColor(activity, R.color.colorMyPrimaryDark);
                } else {
                    activity.setTheme(R.style.MyLightTheme);
                    actionBarColor = ContextCompat.getColor(activity, R.color.colorPrimary);
                }
                actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
            }
        }


    @Override
    public void updateViewData() {
        //заполнить
    }
}
