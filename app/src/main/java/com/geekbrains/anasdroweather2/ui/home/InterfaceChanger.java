package com.geekbrains.anasdroweather2.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.geekbrains.anasdroweather2.MainActivity;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObservable;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;


public class InterfaceChanger implements InterfaceObservable {

    MyData myData;
    AppCompatActivity activity;
    static ActionBar actionBar;
    Toolbar toolbar;
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


        isWind = View.VISIBLE;
        isPressure = View.VISIBLE;
        isAutoThemeChanging = View.VISIBLE;
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
      //  System.out.println("Список наблюдателей интерфейса " + interfaceObservers.toString());
    }

    @Override
    public void removeObserver(InterfaceObserver observer) {
        interfaceObservers.remove(observer);
    }

    @Override
    public void notifyInterfaceObservers() {
        for (InterfaceObserver observer : interfaceObservers) {
            observer.updateInterfaceViewData();
          //  System.out.println("сработал interface notify");
        }
    }

    //автоматически задаём тему, если это разрешено
    public void setAutoTheme(Activity activity, Toolbar toolbar) {
       // System.out.println("isAutoThemeChanging в setAutoTheme " + isAutoThemeChanging);
        if (isAutoThemeChanging == View.VISIBLE) {
            //    if (myData.getCurrentHour()=>8 || myData.getCurrentHour()>=19) {
//не получилось менять цвет actionBar-а через ресурсы, поэтому поменяем так
            if (myData.getCurrentHour() == 8) {
                activity.setTheme(R.style.MyDarkTheme);
                actionBarColor = R.color.colorMyPrimaryDark;
            } else {
                activity.setTheme(R.style.MyLightTheme);

                actionBarColor = ContextCompat.getColor(activity, R.color.colorPrimary);
            }
            toolbar.setBackgroundColor(actionBarColor);
         //   System.out.println("Сработало setTheme");
        }
    }

    //геттер флага возможности смены темы
    public int getIsAutoThemeChanging() {
        return isAutoThemeChanging;
    }

    public void setIsAutoThemeChanging(int isAutoTheme) {
        isAutoThemeChanging = isAutoTheme;
        notifyInterfaceObservers();
    }
}
