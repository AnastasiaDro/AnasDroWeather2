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
    //четвертый день
//    private TextView frthDayTimeText;
//    private TextView frthDayMorTempText;
//    private TextView frthAftTempText;
//    private TextView frthDayEvTempText;

    //индексы для первого дня
    int [] firstDayIndexesArr;
    int fstDayMorKey;
    int fstDayAftKey;
    int fstDayEvKey;
    //массивы для данных первого дня
    private ArrayList <String> fstDayMorArrayList;
    private String[] fstDayAftArrayList;
    private ArrayList <String> fstDayEvArrayList;


    //индексы для данных второго дня
    int [] secondDayIndexesArr;
    int scndDayMorKey;
    int scndDayAftKey;
    int scndDayEvKey;
    //массивы для данных второго дня
    private ArrayList <String> scndDayMorArrayList;
    private ArrayList <String> scndDayAftArrayList;
    private ArrayList <String> scndDayEvArrayList;


    //индексы для данных третьего дня
    int [] thirdDayIndexesArr;
    int thrdDayMorKey;
    int thrdDayAftKey;
    int thrdDayEvKey;
    //массивы для данных третьего дня
    private ArrayList <String>  thrdDayMorArrayList;
    private ArrayList <String> thrdDayAftArrayList;
    private ArrayList <String> thrdDayEvArrayList;





    private WeekDataParser weekDataParser;
    private MyData myData;

    HashMap <Integer, String[]> dataForTextViewsHashMap = new HashMap();

    public static WeekWeatherFragment newInstance() {
        WeekWeatherFragment weekWeatherFragment = new WeekWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        return weekWeatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
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

        //четвертый день
//        frthDayTimeText = view.findViewById(R.id.fourthTimeText);
//        frthDayMorTempText = view.findViewById(R.id.fourthDayMorningTempText);
//        frthAftTempText = view.findViewById(R.id.fourthDayAftTempText);
//        frthDayEvTempText = view.findViewById(R.id.fourthDayEvTempText);
    }

    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                takeAllIndexesForDaysData();
                getStringsArraysWithDaysData();
                //установим данные для первого дня
                fstDayMorTempText.setText(fstDayMorArrayList.get(Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY));
                fstDayAftTempText.setText(fstDayAftArrayList[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY]);
              //  fstDayEvTempText.setText(fstDayEvArrayList.get(Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY));
            }
        });



    }

    //получим индексы, по которым будем выбирать из всех выгруженных данных нужные данные по ближайшим дням
    private void takeAllIndexesForDaysData(){
        int [] daysBeginningTimesArr = weekDataParser.findDayBeginningIndexesArr();
        fstDayMorKey = daysBeginningTimesArr[0]+3;
        System.out.println("fstDayMorKey РАВЕН "+  fstDayMorKey);
        fstDayAftKey = daysBeginningTimesArr[0]+5;
        fstDayEvKey  = daysBeginningTimesArr[0]+7;
        //индексы для данных второго дня

        scndDayMorKey = daysBeginningTimesArr[1]+3;
        scndDayAftKey = daysBeginningTimesArr[1]+5;
        scndDayEvKey = daysBeginningTimesArr[1]+7;

        //индексы для данных третьего дня

        thrdDayMorKey = daysBeginningTimesArr[2]+3;
        thrdDayAftKey = daysBeginningTimesArr[2]+5;
        thrdDayEvKey = daysBeginningTimesArr[2]+7;
    }

    //получим массивы строк, по которым будем искать значения
    private void getStringsArraysWithDaysData(){
        //массив строк с данными для первого дня
        HashMap <Integer, String[]> allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        if (allWeatherDataHashMap == null){
            System.out.println("allWeatherDataHashMap NULL");
        } else {
            System.out.println("allWeatherDataHashMap" + allWeatherDataHashMap.entrySet());
        }
        fstDayMorArrayList = new ArrayList<>();
        fstDayMorArrayList.addAll(Arrays.asList(allWeatherDataHashMap.get(fstDayMorKey)));


         fstDayAftArrayList = allWeatherDataHashMap.get(fstDayAftKey);
//        fstDayEvArr = allWeatherDataHashMap.get(fstDayEvKey);
//        //массив строк с данными для второго дня
//        scndDayMorArr = allWeatherDataHashMap.get(scndDayMorKey);
//        scndDayAftArr = allWeatherDataHashMap.get(scndDayAftKey);
//        scndDayEvArr = allWeatherDataHashMap.get(scndDayEvKey);
//        //массив строк с данными для третьего дня
//        thrdDayMorArr = allWeatherDataHashMap.get(thrdDayMorKey);
//        thrdDayAftArr = allWeatherDataHashMap.get(thrdDayAftKey);
//        thrdDayAftArr = allWeatherDataHashMap.get(thrdDayEvKey);
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
