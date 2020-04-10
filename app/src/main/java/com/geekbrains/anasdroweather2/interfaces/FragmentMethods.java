package com.geekbrains.anasdroweather2.interfaces;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.anasdroweather2.ui.home.CurrentWeatherFragment;

public interface FragmentMethods {
    //находит кнопки и т.п.
    void findViews(View view);
    //вставляет фрагмент
    public void postFragment(AppCompatActivity activity, int placeId);

}

