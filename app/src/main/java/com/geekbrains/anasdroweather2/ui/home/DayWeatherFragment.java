package com.geekbrains.anasdroweather2.ui.home;

import android.content.Intent;
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
import com.geekbrains.anasdroweather2.weatherData.WeatherParserService;

import java.util.HashMap;

public class DayWeatherFragment extends Fragment implements FragmentMethods, Observer {
    private static final int DAY_FIRST_DATA_KEY_IN_HASHMAP = 1;
    private static final int DAY_SECOND_DATA_KEY_IN_HASHMAP = 2;
    private static final int DAY_THIRD_DATA_KEY_IN_HASHMAP = 3;

    //используемые View
    private TextView fSoonTimeText;
    private TextView sSoonTimeText;
    private TextView thSoonTimeText;
    private TextView fSoonTempText;
    private TextView sSoonTempText;
    private TextView thSoonTempText;

    String[] firstDataArr;
    String[] secondDataArr;
    String[] thirdDataArr;

    String temp;

    private MyData myData;

    public static DayWeatherFragment newInstance() {
        DayWeatherFragment dayWeatherFragment = new DayWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        return dayWeatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
        myData = MyData.getInstance();
        myData.registerObserver(this);
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_weather, container, false);
        findViews(view);
        updateViewData();
        return view;
    }


    @Override
    public void findViews(View view) {
        fSoonTimeText = view.findViewById(R.id.f_soonTextView);
        sSoonTimeText = view.findViewById(R.id.s_soonTextView);
        thSoonTimeText = view.findViewById(R.id.th_soonTextView);
        fSoonTempText = view.findViewById(R.id.f_soonTempText);
        sSoonTempText = view.findViewById(R.id.s_soonTempText);
        thSoonTempText = view.findViewById(R.id.th_soonTempText);
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

    //Ставить текст
    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        try {
            myData.getWeatherLoaderThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                HashMap<Integer, String[]> curHashMap = myData.getAllWeatherDataHashMap();
                System.out.println("Размер HashMap в myData " + myData.getAllWeatherDataHashMap().size());
                try {
                    //Через три часа
                    firstDataArr = curHashMap.get(DAY_FIRST_DATA_KEY_IN_HASHMAP);
                    fSoonTimeText.setText(firstDataArr != null ? firstDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY] : null);
                    temp = firstDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
                    fSoonTempText.setText(temp);
                    //Через 6 часов
                    secondDataArr = curHashMap.get(DAY_SECOND_DATA_KEY_IN_HASHMAP);
                    sSoonTimeText.setText(secondDataArr != null ? secondDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY] : null);
                    temp = secondDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
                    sSoonTempText.setText(temp);
                    //Через 9 часов
                    thirdDataArr = curHashMap.get(DAY_THIRD_DATA_KEY_IN_HASHMAP);
                    thSoonTimeText.setText(thirdDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY]);
                    temp = thirdDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
                    thSoonTempText.setText(temp);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myData.removeObserver(this);
    }
}
