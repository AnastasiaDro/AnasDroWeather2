package com.geekbrains.anasdroweather2.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.anasdroweather2.interfaces.FragmentMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;

public class CurrentWeatherFragment extends Fragment implements FragmentMethods, Observer, InterfaceObserver {

//используемые View
private TextView cityTextView;
private TextView temperatureTextView;
private TextView pressureTextView;
private TextView windTextView;
private ImageView weatherImageView;
private MyData myData;
InterfaceChanger interfaceChanger;

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
        interfaceChanger = InterfaceChanger.getInterfaceInstance((AppCompatActivity) this.getContext());
        interfaceChanger.registerObserver(this);
        myData = MyData.getInstance();
        myData.registerObserver(this);
        Log.d("CurrentWeatherFragment", "OnCreate, Added to obsrvers");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
     //   setRetainInstance(true);
//        myData = MyData.getInstance();
//        myData.registerObserver(this);
        Log.d("CurrentWeatherFragment", "OnCreate, Added to obsrvers");

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        findViews(view);
        //получим информацию о видимости температуры и давления

        return view;
    }


    @Override
    public void findViews(View view) {
        cityTextView = view.findViewById(R.id.cityTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        windTextView = view.findViewById(R.id.windTextView);
        pressureTextView = view.findViewById(R.id.pressureTextView);
        weatherImageView = view.findViewById(R.id.weatherImage);
        cityTextView.setText(myData.getCurrentCity());
        updateInterfaceViewData();
        System.out.println();
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
        cityTextView.setText(myData.getCurrentCity());

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
        public void setWeatherValuesToTextViews(String currentTemp, String current){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                temperatureTextView.setText(myData.getCurrentTemp());
                windTextView.setText(myData.getCurrentWind());
                pressureTextView.setText(myData.getCurrentPressure());
            }
        });


        }



}
