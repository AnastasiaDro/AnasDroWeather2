package com.geekbrains.anasdroweather2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.model.MyData;

public class DayWeatherFragment extends Fragment implements FragmentMethods, Observer {

//используемые View
    private TextView morningTextView;
    private TextView afternoonTextView;
    private TextView eveningTextView;
    private TextView morningTempText;
    private TextView afternoonTempText;
    private TextView eveningTempText;



    public static DayWeatherFragment newInstance(){
        DayWeatherFragment dayWeatherFragment = new DayWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        MyData myData = MyData.getInstance();
        myData.registerObserver(dayWeatherFragment);
        return dayWeatherFragment;
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
      //  setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_day_weather, container, false);
        findViews(view);
        return view;
    }


    @Override
    public void findViews(View view) {
        morningTextView = view.findViewById(R.id.morningTextView);
        afternoonTextView = view.findViewById(R.id.afternoonTextView);
        eveningTextView = view.findViewById(R.id.eveningTextView);
        morningTempText = view.findViewById(R.id.morningTempText);
        afternoonTempText = view.findViewById(R.id.afternoonTempText);
        eveningTempText = view.findViewById(R.id.eveningTempText);
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
