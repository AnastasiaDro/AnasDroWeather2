package com.geekbrains.anasdroweather2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.anasdroweather2.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

public class CurrentWeatherFragment extends Fragment implements FragmentMethods, Observer {

//используемые View
private TextView cityTextView;
private TextView temperatureTextView;
private TextView pressureTextView;
private TextView windTextView;
private ImageView weatherImageView;


    public static CurrentWeatherFragment newInstance(){
        CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
       // args.putInt("placeId", placeId);
       // currentWeatherFragment.setArguments(args);
        MyData myData = MyData.getInstance();
        myData.registerObserver(currentWeatherFragment);
        return currentWeatherFragment;
    }


//создаем View
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        //получаем аргументы назад
//        placeId = getArguments().getInt("placeId");
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
     //   setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        findViews(view);
        return view;
    }


    @Override
    public void findViews(View view) {
        cityTextView = view.findViewById(R.id.cityTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);;
        pressureTextView = view.findViewById(R.id.pressureTextView);;
        windTextView = view.findViewById(R.id.windTextView);;
        weatherImageView = view.findViewById(R.id.weatherImage);;
    }

    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    @Override
    public void updateViewData() {
//заполнить
    }
}
