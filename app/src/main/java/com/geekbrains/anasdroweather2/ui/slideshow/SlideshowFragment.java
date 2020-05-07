package com.geekbrains.anasdroweather2.ui.slideshow;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.ActivMethods;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.Constants;
import com.geekbrains.anasdroweather2.ui.home.InterfaceChanger;

public class SlideshowFragment extends Fragment implements ActivMethods, CompoundButton.OnCheckedChangeListener, OnCreateContextMenuListener {

    private SlideshowViewModel slideshowViewModel;
    private InterfaceChanger interfaceChanger;
    private MyData myData;


    //Тспользуемые View
    SearchView searchView;
    CheckBox windCheckBox;
    CheckBox pressuCheckBox;
    Switch autoThemeSwitch;
    Button addNewBtn;
    AddNewClickListener addNewClickListener;
    //Получим ToolBar для программного изменения его цвета в дальнейшем
    Toolbar toolbar;
    //вынесем отдельно корневой View для доступа к нему
    View root;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MenuItem item;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        interfaceChanger = InterfaceChanger.getInterfaceInstance((AppCompatActivity) this.getActivity());
        init();
        initRecycler(root);
        initClickListeners();
        updateInterfaceChanger();

        return root;
    }

    @Override
    public void init() {
        searchView = root.findViewById(R.id.searchView);
        windCheckBox = root.findViewById(R.id.windCheckBox);
        pressuCheckBox = root.findViewById(R.id.pressuCheckBox);
        autoThemeSwitch = root.findViewById(R.id.autoThemeSwitch);
        addNewBtn = root.findViewById(R.id.addNewCityBtn);
        toolbar = this.getActivity().findViewById(R.id.toolbar);
        registerCheckBoxListeners();
        activateSwitch(autoThemeSwitch);
    }

    private void initClickListeners() {
        addNewClickListener = new AddNewClickListener();
        addNewBtn.setOnClickListener(addNewClickListener);
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
        windCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    interfaceChanger.setWind(View.VISIBLE);
                } else {
                    interfaceChanger.setWind(View.INVISIBLE);
                }
                interfaceChanger.notifyInterfaceObservers();
                System.out.println("isWind в ченджере " + interfaceChanger.getIsWind());
            }
        });
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
                System.out.println("isPressure в ченджере " + interfaceChanger.getIsPressure());
            }

        });
    }

    public void initRecycler(View view) {
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
    public void activateSwitch(Switch mySwitch) {
        if (mySwitch != null) {
            boolean isChecked;
            if (interfaceChanger.getIsAutoThemeChanging() == View.VISIBLE) {
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
            default:
                return super.onContextItemSelected(item);
        }
    }
}