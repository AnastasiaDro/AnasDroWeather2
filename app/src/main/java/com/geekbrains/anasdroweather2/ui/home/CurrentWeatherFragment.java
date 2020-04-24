package com.geekbrains.anasdroweather2.ui.home;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.anasdroweather2.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.rest.WeatherLoader;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class CurrentWeatherFragment extends Fragment implements FragmentMethods, Observer, InterfaceObserver {

    //используемые View
    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView pressureTextView;
    private TextView windTextView;
    private ThermometerView thermometerView;
    private TextView descriptTextView;
    //затем сюда поставлю картинку с облаками/солнцем/дождем
    private ImageView weatherImageView;
    private MyData myData;
    private InterfaceChanger interfaceChanger;
    private WeatherLoader weatherLoader;
    private String windString;
    private String pressureString;
    private String descriptString;

    //номер элемента массива JSON, в котором данные текущей погоды (он всегда первый)
    private static final int CURRENT_DATA_KEY_IN_HASHMAP = 0;

    public static CurrentWeatherFragment newInstance() {
        CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        return currentWeatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
        interfaceChanger = InterfaceChanger.getInterfaceInstance((AppCompatActivity) this.getContext());
        interfaceChanger.registerObserver(this);
        myData = MyData.getInstance();
        myData.registerObserver(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        findViews(view);
        updateInterfaceViewData();
        return view;
    }

    @Override
    public void findViews(View view) {
        cityTextView = view.findViewById(R.id.cityTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        windTextView = view.findViewById(R.id.windTextView);
        pressureTextView = view.findViewById(R.id.pressureTextView);
        thermometerView = view.findViewById(R.id.thermometerView);
        descriptTextView = view.findViewById(R.id.descriptTextView);
        System.out.println();
    }


    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void updateViewData() {
        cityTextView.setText(myData.getCurrentCity());
        setWeatherValuesToTextViews();
    }

    //так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
// его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myData.removeObserver(this);
        interfaceChanger.removeObserver(this);
    }

    @Override
    public void updateInterfaceViewData() {
//В зависимости от сохраненных настроек сделаем ветер и давление видимыми или невидимыми
        windTextView.setVisibility(interfaceChanger.getIsWind());
        pressureTextView.setVisibility(interfaceChanger.getIsPressure());
        if (interfaceChanger.getIsWind() == View.VISIBLE) {
            windTextView.setVisibility(View.VISIBLE);
        } else {
            windTextView.setVisibility(View.INVISIBLE);
        }
        if (interfaceChanger.getIsPressure() == View.VISIBLE) {
            pressureTextView.setVisibility(View.VISIBLE);
        } else {
            pressureTextView.setVisibility(View.INVISIBLE);
        }
    }


    //Ставить текст
    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] dataArr = myData
                        .getAllWeatherDataHashMap()
                        .get(CURRENT_DATA_KEY_IN_HASHMAP);
                try {
                    String currentTemp = dataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY];
                    String forTemp = currentTemp.concat(" \u2103");
                    temperatureTextView.setText(forTemp);
                    windString = getString(R.string.wind);
                    pressureString = getString(R.string.pressure);
                    windString = windString.concat(" " + dataArr[Constants.WIND_KEY_IN_WEATHERDATA_ARRAY]);
                    windTextView.setText(windString);
                    pressureString = pressureString.concat(" " + dataArr[Constants.PRESSURE_KEY_IN_WEATHERDATA_ARRAY]);
                    pressureTextView.setText(pressureString);
                    descriptString = dataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
                    descriptTextView.setText(descriptString);
                    //для изменения цвета полоски в градуснике
                    int temp = Integer.parseInt(currentTemp);
                    compareTemp(temp);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //в зависимости от температуры меняем цвет полоски в градуснике
    private void compareTemp(int currentTemp) {
        thermometerView.changeTempColor(currentTemp);
    }
}
