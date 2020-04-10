package com.geekbrains.anasdroweather2.interfaces;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public interface FragmentMethods {
    //находит кнопки и т.п.
    void findViews(View view);
    //вставляет фрагмент
    public void postFragment(AppCompatActivity activity);
}

