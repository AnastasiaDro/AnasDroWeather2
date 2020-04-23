package com.geekbrains.anasdroweather2.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.rest.WeatherLoader;

public class HomeFragment extends Fragment implements ActivMethods {

    //класс Model
    MyData myData;
    InterfaceChanger interfaceChanger;
    //места для моих фрагментов
    private int currentWeathPlaceId;
    private int dayWeathPlaceId;
    private int weekWeathPlaceId;

    //Мои фрагменты
    private CurrentWeatherFragment curWeathFragment;
    private DayWeatherFragment dayWeathFragment;
    private WeekWeatherFragment weekWeatherFragment;
    private AppCompatActivity mainActivity;
    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //получим Активити
        mainActivity = (AppCompatActivity) this.getActivity();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        myData = MyData.getInstance();
        //загружаем данные
        WeatherLoader weatherLoader = new WeatherLoader(getContext());
        weatherLoader.loadWeatherData();
        init();

//создаём изменитель интерфейса
        interfaceChanger = InterfaceChanger.getInterfaceInstance(mainActivity);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void init() {
        currentWeathPlaceId = R.id.currentWeatherFrame;
        dayWeathPlaceId = R.id.dayWeatherFrame;
        weekWeathPlaceId = R.id.weekWeatherFrame;
        curWeathFragment = CurrentWeatherFragment.newInstance();
        curWeathFragment.postFragment(mainActivity, currentWeathPlaceId);
        dayWeathFragment = DayWeatherFragment.newInstance();
        dayWeathFragment.postFragment(mainActivity, dayWeathPlaceId);
        weekWeatherFragment = WeekWeatherFragment.newInstance();
        weekWeatherFragment.postFragment(mainActivity, weekWeathPlaceId);
    }

    private void checkExceptions(WeatherLoader weatherLoader) {
        if (myData.getException() != null) {
            weatherLoader.showExceptionAlert();
            myData.setException(null);
        }
    }
}
