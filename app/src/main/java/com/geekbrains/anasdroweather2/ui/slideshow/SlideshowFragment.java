package com.geekbrains.anasdroweather2.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.MainActivity;
import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;
import com.geekbrains.anasdroweather2.ui.home.CurrentWeatherFragment;
import com.geekbrains.anasdroweather2.ui.home.InterfaceChanger;

import org.w3c.dom.ls.LSOutput;

public class SlideshowFragment extends Fragment implements ActivMethods, CompoundButton.OnCheckedChangeListener {

    private SlideshowViewModel slideshowViewModel;
    private InterfaceChanger interfaceChanger;
    private MyData myData;


    //Тспользуемые View
    SearchView searchView;
    CheckBox windCheckBox;
    CheckBox pressuCheckBox;
    Switch autoThemeSwitch;

    Toolbar toolbar;

    View root;

    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
        initRecycler(root);
        updateInterfaceChanger();

        return root;
    }

    @Override
    public void init() {
        searchView = root.findViewById(R.id.searchView);
        windCheckBox = root.findViewById(R.id.windCheckBox);
        pressuCheckBox = root.findViewById(R.id.pressuCheckBox);
        autoThemeSwitch= root.findViewById(R.id.autoThemeSwitch);
        toolbar = this.getActivity().findViewById(R.id.toolbar);
        registerCheckBoxListeners();
        activateSwitch(autoThemeSwitch);
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

    public void initRecycler(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForSlideShow);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }


//TODO
    public void activateSwitch(Switch mySwitch){
        if (mySwitch != null){
            boolean isChecked;
            if (interfaceChanger.getIsAutoThemeChanging() == View.VISIBLE){
                isChecked = true;
            } else {
                isChecked = false;
            }
            mySwitch.setChecked(isChecked);
            mySwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            interfaceChanger.setIsAutoThemeChanging(View.VISIBLE);
        } else {
            interfaceChanger.setIsAutoThemeChanging(View.INVISIBLE);
        }
        interfaceChanger.setAutoTheme(this.getActivity(), toolbar);
        System.out.println(View.VISIBLE);
        System.out.println("onCheckedChanged isAutoThemeChenging " + interfaceChanger.getIsAutoThemeChanging());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case Constants.HIDE_CONTEXTMENU_ITEM:
                myAdapter.deleteItem(item.getGroupId());
                return true;
            default:   return super.onContextItemSelected(item);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
//
//        //TODO

//        //Костыли
//        final MyAdapter mainAdapter = new MyAdapter();
//        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                myAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }



//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case Constants.HIDE_CONTEXTMENU_ITEM:
//
//
//
//            //           myData.getCitiesList().remove(myAdapter);
////                myData.notifyObservers();
//                return true;
//        }
//        return true;


}