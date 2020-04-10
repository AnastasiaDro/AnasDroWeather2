package com.geekbrains.anasdroweather2.ui.home;

import android.os.Bundle;
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

    private MyData myData;

    public static WeekWeatherFragment newInstance(){
        WeekWeatherFragment weekWeatherFragment = new WeekWeatherFragment();
        Bundle args = new Bundle();
        // args.putInt("placeId", placeId);
        // currentWeatherFragment.setArguments(args);
        return weekWeatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
        myData = MyData.getInstance();
        myData.registerObserver(this);
        Log.d("WeekWeatherFragment", "onCreate, added to obsrvers");
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        myData = MyData.getInstance();
//        myData.registerObserver(this);
//        Log.d("WeekWeatherFragment", "onCreate, added to obsrvers");
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

    //так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
// его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("Список наблюдателей " + myData.observers.toString());
        myData.removeObserver(this);
//        Toast.makeText(getActivity(), "FirstFragment.onDetach()",
//                Toast.LENGTH_LONG).show();
        Log.d("WeekWeatherFragment", "removed from myData");
    }
}
