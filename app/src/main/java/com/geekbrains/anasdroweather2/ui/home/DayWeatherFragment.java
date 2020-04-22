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

import java.util.HashMap;

public class DayWeatherFragment extends Fragment implements FragmentMethods, Observer {
    private static final int DAY_FIRST_DATA_KEY_IN_HASHMAP = 1;
    private static final int DAY_SECOND_DATA_KEY_IN_HASHMAP = 2;
    private static final int DAY_THIRD_DATA_KEY_IN_HASHMAP = 3;

//используемые View
    private TextView f_soonTimeView;
    private TextView s_soonTimeView;
    private TextView th_soonTimeView;
    private TextView f_soonTempText;
    private TextView s_soonTempText;
    private TextView th_soonTempText;

    String [] firstDataArr;
    String [] secondDataArr;
    String [] thirdDataArr;

    private MyData myData;

    public static DayWeatherFragment newInstance(){
        DayWeatherFragment dayWeatherFragment = new DayWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        return dayWeatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
        myData = MyData.getInstance();
        myData.registerObserver(this);
        Log.d("DayWeatherFragment", "onCreate, added to obsrvers");
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
        f_soonTimeView = view.findViewById(R.id.f_soonTextView);
        s_soonTimeView = view.findViewById(R.id.s_soonTextView);
        th_soonTimeView = view.findViewById(R.id.th_soonTextView);
        f_soonTempText = view.findViewById(R.id.f_soonTempText);
        s_soonTempText = view.findViewById(R.id.s_soonTempText);
        th_soonTempText = view.findViewById(R.id.th_soonTempText);
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
        try {
            myData.getWeatherLoaderThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setWeatherValuesToTextViews();
    }

    //Ставить текст
    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                HashMap <Integer, String[]> curHashMap = myData.getAllWeatherDataHashMap();
                firstDataArr = curHashMap.get(DAY_FIRST_DATA_KEY_IN_HASHMAP);
                f_soonTimeView.setText(firstDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY]);
                f_soonTempText.setText(firstDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY]);

                secondDataArr = curHashMap.get(DAY_SECOND_DATA_KEY_IN_HASHMAP);
                s_soonTimeView.setText(secondDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY]);
                s_soonTempText.setText(secondDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY]);

                thirdDataArr = curHashMap.get(DAY_THIRD_DATA_KEY_IN_HASHMAP);
                th_soonTimeView.setText(thirdDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY]);
                th_soonTempText.setText(thirdDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY]);
//                f_soonTimeView.setText(myData.getF_soonTime());
//                s_soonTimeView.setText(myData.getS_soonTime());
//                th_soonTimeView.setText(myData.getTh_soonTime());
//                f_soonTempText.setText(myData.getF_soonTemp()+ " \u2103");
//                s_soonTempText.setText(myData.getS_soonTemp()+ " \u2103");
//                th_soonTempText.setText(myData.getTh_soonTemp()+ " \u2103");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myData.removeObserver(this);
        Log.d("DayWeatherFragment", "removed from myData");
    }
}
