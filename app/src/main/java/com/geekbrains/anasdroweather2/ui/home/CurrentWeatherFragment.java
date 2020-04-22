package com.geekbrains.anasdroweather2.ui.home;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.geekbrains.anasdroweather2.ui.home.Constants;


import com.geekbrains.anasdroweather2.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.weatherData.WeatherLoader;

import static java.lang.Integer.parseInt;

public class CurrentWeatherFragment extends Fragment implements FragmentMethods, Observer, InterfaceObserver {

//используемые View
private TextView cityTextView;
private TextView temperatureTextView;
private TextView pressureTextView;
private TextView windTextView;

private ThermometerView thermometerView;

private ImageView weatherImageView;
private MyData myData;
private InterfaceChanger interfaceChanger;
private WeatherLoader weatherLoader;
private String windString;
private String pressureString;
int currentTemp;

//номер элемента массива JSON, в котором данные текущей погоды (он всегда первый)
    private static final int CURRENT_DATA_KEY_IN_HASHMAP = 0;

    public static CurrentWeatherFragment newInstance(){
        CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
       // args.putInt("placeId", placeId);
       // currentWeatherFragment.setArguments(args);
        Log.d("CurrentWeatherFragment", "Добавили в список Observer-ов");
        return currentWeatherFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
        windString = getString(R.string.wind);
        pressureString = getString(R.string.pressure);

        weatherLoader = new WeatherLoader(getContext());
        interfaceChanger = InterfaceChanger.getInterfaceInstance((AppCompatActivity) this.getContext());
        interfaceChanger.registerObserver(this);
        myData = MyData.getInstance();
        myData.registerObserver(this);
        Log.d("CurrentWeatherFragment", "OnCreate, Added to obsrvers");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
     //   setRetainInstance(true);
//        myData = MyData.getInstance();
//        myData.registerObserver(this);
        Log.d("CurrentWeatherFragment", "OnCreate, Added to obsrvers");

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        findViews(view);
        updateViewData();
        updateInterfaceViewData();

        //выведем ошибку об отсутствии сети, если эта ошибка была при загрузке погодных данных
        if (myData.getExceptionWhileLoading() != null){
            weatherLoader.showExceptionAlert(R.string.connectionError, R.string.adviceConnectonError);
            myData.setExceptionWhileLoading(null);
        }

        return view;
    }


    @Override
    public void findViews(View view) {
        cityTextView = view.findViewById(R.id.cityTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        windTextView = view.findViewById(R.id.windTextView);
        pressureTextView = view.findViewById(R.id.pressureTextView);
        thermometerView = view.findViewById(R.id.thermometerView);
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
        weatherLoader.loadWeatherData();
        try {
            myData.getWeatherLoaderThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setWeatherValuesToTextViews();
    }

//так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
//// его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("Список наблюдателей " + myData.observers.toString());
        myData.removeObserver(this);
//        Toast.makeText(getActivity(), "FirstFragment.onDetach()",
//                Toast.LENGTH_LONG).show();
        Log.d("CurrentWeatherFragment", "removed from myData");

        System.out.println("Список наблюдателей интерфейса " + interfaceChanger.interfaceObservers.toString());
        interfaceChanger.removeObserver(this);
        System.out.println("Список наблюдателей интерфейса " + interfaceChanger.interfaceObservers.toString());

    }





    @Override
    public void updateInterfaceViewData() {
//
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
        public void setWeatherValuesToTextViews(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] dataArr = myData
                        .getAllWeatherDataHashMap()
                        .get(CURRENT_DATA_KEY_IN_HASHMAP);
                String forTemp = dataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY] + " \u2103";
                temperatureTextView.setText(forTemp);
                windString = windString.concat(" "+dataArr[Constants.WIND_KEY_IN_WEATHERDATA_ARRAY]);
                windTextView.setText(windString);
                pressureString = pressureString.concat(" " + dataArr[Constants.PRESSURE_KEY_IN_WEATHERDATA_ARRAY]);
                pressureTextView.setText(pressureString);
//                String forTemp = myData.getCurrentTemp() + " \u2103";
//                temperatureTextView.setText(forTemp);
//                windString = windString.concat(" "+myData.getCurrentWind());
//              //  String forWind = myData.getCurrentWind();
//                windTextView.setText(windString);
//                pressureString = pressureString.concat(" " + myData.getCurrentPressure());
//                pressureTextView.setText(pressureString);
//                currentTemp = parseInt(myData.getCurrentTemp());
//                compareTemp(currentTemp);

            }
        });


        }


        private void compareTemp(int currentTemp){
       thermometerView.changeTempColor(currentTemp);
        }


}
