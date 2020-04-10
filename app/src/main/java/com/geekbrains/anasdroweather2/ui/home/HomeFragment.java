package com.geekbrains.anasdroweather2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.model.MyData;

public class HomeFragment extends Fragment implements ActivMethods {

//класс Model
    MyData myData;
//места для моих фрагментов
    int currentWeathPlaceId;
    int dayWeathPlaceId;
    int weekWeathPlaceId;

//Мои фрагменты
    CurrentWeatherFragment curWeathFragment;
    DayWeatherFragment dayWeathFragment;
    WeekWeatherFragment weekWeatherFragment;

    InterfaceChanger interfaceChanger;
    AppCompatActivity mainActivity;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //получим Активити
        mainActivity = (AppCompatActivity)this.getActivity();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        myData = MyData.getInstance();
        init();
//создаём изменитель интерфейса
        interfaceChanger = new InterfaceChanger(mainActivity);
//изменяем тему, если это возможно, в соответствии с текущим временем
        interfaceChanger.setAutoTheme();


        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
          //      textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void init() {
        currentWeathPlaceId = R.id.currentWeatherFrame;
        dayWeathPlaceId = R.id.dayWeatherFrame;
        weekWeathPlaceId = R.id.weekWeatherFrame;

        curWeathFragment = new CurrentWeatherFragment(currentWeathPlaceId);
        curWeathFragment.postFragment(mainActivity);
        Log.d("MainActivity", "Posted CurrentWeatherFragment");

        dayWeathFragment = new DayWeatherFragment(dayWeathPlaceId);
        dayWeathFragment.postFragment(mainActivity);
        Log.d("MainActivity", "Posted DayWeatherFragment");

        weekWeatherFragment = new WeekWeatherFragment(weekWeathPlaceId);
        weekWeatherFragment.postFragment(mainActivity);
        Log.d("MainActivity", "Posted WeekWeatherFragment");
    }
}
