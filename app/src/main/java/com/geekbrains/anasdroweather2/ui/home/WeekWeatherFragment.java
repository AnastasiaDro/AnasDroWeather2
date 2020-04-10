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

import com.geekbrains.anasdroweather.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather.interfaces.Observer;

public class WeekWeatherFragment extends Fragment implements FragmentMethods, Observer {

//TextView по дням недели

//TextView для названия дней
    TextView firstDayTextView;
    TextView scndDayTextView;
    TextView trdDayTextView;
    TextView fourthDayTextView;
    TextView fifthfDayTextView;
    TextView sixDayTextView;
    TextView svnDayTextView;

//TextView для температур по дням
    TextView firstDayTempText;
    TextView scndDayTempText;
    TextView trdDayTempText;
    TextView fourthDayTempText;
    TextView fifthDayTempText;
    TextView sixDayTempText;
    TextView svnDayTempText;

//Переменные для работы
    private int placeId;
    private MyData myData;

    //Конструктор
    public WeekWeatherFragment (int placeId) {
        this.placeId = placeId;
        myData = MyData.getInstance();
        myData.registerObserver(this);
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_week_weather, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        //название дней недели
        firstDayTextView = view.findViewById(R.id.firstDayTextView);
        scndDayTextView = view.findViewById(R.id.scndDayTextView);
        trdDayTextView = view.findViewById(R.id.trdDayTextView);
        fourthDayTextView = view.findViewById(R.id.fourthDayTextView);
        fifthfDayTextView = view.findViewById(R.id.fifthfDayTextView);
        sixDayTextView = view.findViewById(R.id.sixDayTextView);
        svnDayTextView = view.findViewById(R.id.svnDayTextView);

        //Температура по дням недели
        firstDayTempText = view.findViewById(R.id.firstDayTempText);
        scndDayTempText = view.findViewById(R.id.scndDayTempText);
        trdDayTempText = view.findViewById(R.id.trdDayTempText);
        fourthDayTempText = view.findViewById(R.id.fourthDayTempText);
        fifthDayTempText = view.findViewById(R.id.fifthDayTempText);
        sixDayTempText = view.findViewById(R.id.sixDayTempText);
        svnDayTempText = view.findViewById(R.id.svnDayTempText);

    }

    @Override
    public void postFragment(AppCompatActivity activity) {
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
