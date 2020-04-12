package com.geekbrains.anasdroweather2.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.geekbrains.anasdroweather2.interfaces.InterfaceObservable;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

import java.util.LinkedList;
import java.util.List;


public class InterfaceChanger implements InterfaceObservable {

    MyData myData;
    AppCompatActivity activity;
    static ActionBar actionBar;
    int actionBarColor;

    //видно ли ветер и давление
    private int isWind;
    private int isPressure;
    //возможность смены темы
    //флаг автоматической смены темы
    private int isAutoThemeChanging;

    private static InterfaceChanger interfaceInstance;
    public List<InterfaceObserver> interfaceObservers;


    private InterfaceChanger(AppCompatActivity activity) {
        this.activity = activity;
        myData = MyData.getInstance();
        actionBar = activity.getSupportActionBar();
          isWind = View.VISIBLE;
         isPressure = View.VISIBLE;
         isAutoThemeChanging = 1;
        interfaceObservers = new LinkedList<>();
    }

    public static InterfaceChanger getInterfaceInstance(AppCompatActivity activity) {
        if (interfaceInstance == null) {
            interfaceInstance = new InterfaceChanger(activity);
        }
        return interfaceInstance;
    }


    public int getIsWind() {
        return isWind;
    }

    public void setWind(int wind) {
        isWind = wind;
    }

    public int getIsPressure() {
        return isPressure;
    }

    public void setPressure(int pressure) {
        isPressure = pressure;
    }


    @Override
    public void registerObserver(InterfaceObserver observer) {
        interfaceObservers.add(observer);
    }

    @Override
    public void removeObserver(InterfaceObserver observer) {
        interfaceObservers.remove(observer);
    }

    @Override
    public void notifyInterfaceObservers() {
        for (InterfaceObserver observer : interfaceObservers) {
            observer.updateInterfaceViewData();
            System.out.println("сработал interface notify");
        }
    }

    //автоматически задаём тему, если это разрешено
    public void setAutoTheme(Activity activity) {
        if (isAutoThemeChanging == 1){
            System.out.println("время = " + myData.getCurrentHour());
            if (myData.getCurrentHour()<8 || myData.getCurrentHour()>=11) {
//не получилось менять цвет actionBar-а через ресурсы, поэтому поменяем так
                activity.setTheme(R.style.MyDarkTheme);
                actionBarColor = ContextCompat.getColor(activity, R.color.colorMyPrimaryDark);
            } else {
                activity.setTheme(R.style.MyLightTheme);
                actionBarColor = ContextCompat.getColor(activity, R.color.colorPrimary);
            }
          //  actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        }
    }

    //геттер флага возможности смены темы
    public int getIsAutoThemeChanging() {
        return isAutoThemeChanging;
    }

    public void setAutoThemeChanging(int isAutoTheme) {
        isAutoThemeChanging = isAutoTheme;
    }
    }
