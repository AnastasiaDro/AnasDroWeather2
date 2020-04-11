package com.geekbrains.anasdroweather2.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;
import com.geekbrains.anasdroweather2.ui.home.InterfaceChanger;

public class SlideshowFragment extends Fragment implements ActivMethods, InterfaceObserver {

    private SlideshowViewModel slideshowViewModel;
    private InterfaceChanger interfaceChanger;
    private MyData myData;

    //Тспользуемые View
    SearchView searchView;
    CheckBox windCheckBox;
    CheckBox pressuCheckBox;

    View root;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        root = inflater.inflate(R.layout.fragment_slideshow, container, false);
       // final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        init();
        updateInterfaceViewData();
        return root;
    }


    @Override
    public void init() {
        searchView = root.findViewById(R.id.searchView);
        windCheckBox = root.findViewById(R.id.pressuCheckBox);
        pressuCheckBox = root.findViewById(R.id.pressuCheckBox);
    }

    @Override
    public void updateInterfaceViewData() {
        //в зависимости от настроек интерфейса отобразим чек или не чек при первом запуске
        if (interfaceChanger.getIsPressure() == 1){
            pressuCheckBox.setChecked(true);
        } else {
            pressuCheckBox.setChecked(false);
        }
        if (interfaceChanger.getIsWind() == 1){
            windCheckBox.setChecked(true);
        } else {
            windCheckBox.setChecked(false);
        }
    }




}
