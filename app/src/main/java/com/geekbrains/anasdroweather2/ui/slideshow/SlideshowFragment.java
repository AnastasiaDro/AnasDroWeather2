package com.geekbrains.anasdroweather2.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;
import com.geekbrains.anasdroweather2.ui.home.CurrentWeatherFragment;
import com.geekbrains.anasdroweather2.ui.home.InterfaceChanger;

import org.w3c.dom.ls.LSOutput;

public class SlideshowFragment extends Fragment implements ActivMethods {

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
        interfaceChanger = InterfaceChanger.getInterfaceInstance((AppCompatActivity) this.getActivity());
        init();
        updateInterfaceChanger();

        return root;
    }


    @Override
    public void init() {
        searchView = root.findViewById(R.id.searchView);
        windCheckBox = root.findViewById(R.id.windCheckBox);
        pressuCheckBox = root.findViewById(R.id.pressuCheckBox);
        registerCheckBoxListeners();
    }


    public void updateInterfaceChanger() {
        //в зависимости от настроек интерфейса отобразим чек или не чек при первом запуске
        if (interfaceChanger.getIsPressure() == View.VISIBLE) {
            pressuCheckBox.setChecked(true);
        } else {
            pressuCheckBox.setChecked(false);
        }
        if (interfaceChanger.getIsWind() == View.VISIBLE) {
            windCheckBox.setChecked(true);
        } else {
            windCheckBox.setChecked(false);
        }
    }

    //листенеры для чекбоксов
    private void registerCheckBoxListeners() {
        //windCheckBox

        windCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    interfaceChanger.setWind(View.VISIBLE);
                } else {
                    interfaceChanger.setWind(View.INVISIBLE);
                }
                interfaceChanger.notifyInterfaceObservers();
                System.out.println("isWind в ченджере "+ interfaceChanger.getIsWind());

            }
        });

        //pressuCheckBox
        pressuCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    interfaceChanger.setPressure(View.VISIBLE);
                } else {
                    interfaceChanger.setPressure(View.INVISIBLE);
                }
                interfaceChanger.notifyInterfaceObservers();
                System.out.println("");
                System.out.println("isPressure в ченджере "+ interfaceChanger.getIsPressure());
            }

        });

    }

}