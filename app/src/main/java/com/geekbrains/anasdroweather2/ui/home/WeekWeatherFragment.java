package com.geekbrains.anasdroweather2.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WeekWeatherFragment extends Fragment implements FragmentMethods, Observer {


//TextView по дням недели

    //TextView для времени и температур по дням и времени суток
    //первый день
    private TextView firstDayTimeText;
    private TextView fstDayMorTempText;
    private TextView fstDayAftTempText;
    private TextView fstDayEvTempText;
    //второй день
    private TextView scndDayTimeText;
    private TextView sndDayMorTempText;
    private TextView sndDayAftTempText;
    private TextView sndDayEvTempText;
    //третий день
    private TextView thdDayTimeText;
    private TextView thdDayMorTempText;
    private TextView thdDayAftTempText;
    private TextView thdDayEvTempText;

    //индексы для первого дня
    int[] firstDayIndexesArr;
    int fstDayMorKey;
    int fstDayAftKey;
    int fstDayEvKey;

    //массивы для данных первого дня
    private String[] fstDayMorArr;
    private String[] fstDayAftArr;
    private String[] fstDayEvArr;

    //индексы для данных второго дня
    int scndDayMorKey;
    int scndDayAftKey;
    int scndDayEvKey;

    //массивы для данных второго дня
    private String[] scndDayMorArr;
    private String[] scndDayAftArr;
    private String[] scndDayEvArr;

    //индексы для данных третьего дня
    int thrdDayMorKey;
    int thrdDayAftKey;
    int thrdDayEvKey;
    //массивы для данных третьего дня
    private String[] thrdDayMorArr;
    private String[] thrdDayAftArr;
    private String[] thrdDayEvArr;

    //Parser, ищущий индексы для забора данных и данные
    private WeekDataParser weekDataParser;
    private MyData myData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myData = MyData.getInstance();
        myData.registerObserver(this);
        weekDataParser = new WeekDataParser();
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_weather, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        //Температура по дням недели: утро, день, вечер
        //второй день
        firstDayTimeText = view.findViewById(R.id.firstDayTimeText);
        fstDayMorTempText = view.findViewById(R.id.firstDayMorningTempText);
        fstDayAftTempText = view.findViewById(R.id.firstDayAftTempText);
        fstDayEvTempText = view.findViewById(R.id.firstDayEvTempText);

        //второй день
        scndDayTimeText = view.findViewById(R.id.scndDayTimeText);
        sndDayMorTempText = view.findViewById(R.id.scndDayMorningTempText);
        sndDayAftTempText = view.findViewById(R.id.scndDayAftTempText);
        sndDayEvTempText = view.findViewById(R.id.scndDayEvTempText);

        //третий день
        thdDayTimeText = view.findViewById(R.id.trdDayTimeText);
        thdDayMorTempText = view.findViewById(R.id.trdDayMorningTempText);
        thdDayAftTempText = view.findViewById(R.id.trdDayAftTempText);
        thdDayEvTempText = view.findViewById(R.id.trdDayEvTempText);
    }

    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                takeAllIndexesForDaysData();
                getStringsArraysWithDaysData();
                //установим данные для первого дня
                firstDayTimeText.setText(fstDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
                fstDayMorTempText.setText(fstDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                fstDayAftTempText.setText(fstDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                fstDayEvTempText.setText(fstDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                //для второго дня
                scndDayTimeText.setText(scndDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
                sndDayMorTempText.setText(scndDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                sndDayAftTempText.setText(scndDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                sndDayEvTempText.setText(scndDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                //для третьего дня
                thdDayTimeText.setText(thrdDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
                thdDayMorTempText.setText(thrdDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                thdDayAftTempText.setText(thrdDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
                thdDayEvTempText.setText(thrdDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
            }
        });
    }

    //получим индексы, по которым будем выбирать из всех выгруженных данных нужные данные по ближайшим дням
    private void takeAllIndexesForDaysData() {
        int[] daysBeginningTimesArr = weekDataParser.findDayBeginningIndexesArr();
        fstDayMorKey = daysBeginningTimesArr[0] + 3;
        fstDayAftKey = daysBeginningTimesArr[0] + 5;
        fstDayEvKey = daysBeginningTimesArr[0] + 7;

        //индексы для данных второго дня
        scndDayMorKey = daysBeginningTimesArr[1] + 3;
        scndDayAftKey = daysBeginningTimesArr[1] + 5;
        scndDayEvKey = daysBeginningTimesArr[1] + 7;

        //индексы для данных третьего дня
        thrdDayMorKey = daysBeginningTimesArr[2] + 3;
        thrdDayAftKey = daysBeginningTimesArr[2] + 5;
        thrdDayEvKey = daysBeginningTimesArr[2] + 7;
    }

    //получим массивы строк, по которым будем искать значения
    private void getStringsArraysWithDaysData() {
        //массив строк с данными для первого дня
        HashMap<Integer, String[]> allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        fstDayMorArr = allWeatherDataHashMap.get(fstDayMorKey);
        fstDayAftArr = allWeatherDataHashMap.get(fstDayAftKey);
        fstDayEvArr = allWeatherDataHashMap.get(fstDayEvKey);
        //массив строк с данными для второго дня
        scndDayMorArr = allWeatherDataHashMap.get(scndDayMorKey);
        scndDayAftArr = allWeatherDataHashMap.get(scndDayAftKey);
        scndDayEvArr = allWeatherDataHashMap.get(scndDayEvKey);
        //массив строк с данными для третьего дня
        thrdDayMorArr = allWeatherDataHashMap.get(thrdDayMorKey);
        thrdDayAftArr = allWeatherDataHashMap.get(thrdDayAftKey);
        thrdDayEvArr = allWeatherDataHashMap.get(thrdDayEvKey);
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
        setWeatherValuesToTextViews();
    }

    //так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
    // его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myData.removeObserver(this);
    }
}
