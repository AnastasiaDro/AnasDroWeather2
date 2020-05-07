package com.geekbrains.anasdroweather2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.geekbrains.anasdroweather2.interfaces.InterfaceObserver;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.ui.home.InterfaceChanger;
import com.geekbrains.anasdroweather2.rest.WeatherLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.geekbrains.anasdroweather2.model.Constants.APP_PREFERENCES;
import static com.geekbrains.anasdroweather2.model.Constants.APP_PREFERENCES_IS_AUTOTHEME;
import static com.geekbrains.anasdroweather2.model.Constants.APP_PREFERENCES_IS_PRESSURE;
import static com.geekbrains.anasdroweather2.model.Constants.APP_PREFERENCES_IS_WIND;
import static com.geekbrains.anasdroweather2.model.Constants.APP_PREFERENCES_LAST_SEARCHED_CITY;

public class MainActivity extends AppCompatActivity implements InterfaceObserver {
    private AppBarConfiguration mAppBarConfiguration;

    //сохранение настроек интерфейса
    private SharedPreferences mSettings;
    int isWind;
    int isPressure;
    int isAutoTheme;
    String lastSearchedCity;
    private InterfaceChanger interfaceChanger;
    private MyData myData;
    NavController navController;
    WeatherLoader weatherLoader;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //инициализиируем Fresco
        Fresco.initialize(this);
        //подключаемся к классу интерфейса
        interfaceChanger = InterfaceChanger.getInterfaceInstance(this);
        myData = MyData.getInstance();
        weatherLoader = new WeatherLoader(this);
        //выгрузим погодные данные
        weatherLoader.loadWeatherData();
        //работа с сохраненными настройками
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        myData.setNavController(navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //автонастройка темы
        //TODO
        interfaceChanger.setAutoTheme(this, toolbar);
        //выгрузка базы данных в myData
        myData.loadDbDataToMyData();
        //выгрузка имен из полученных данных
        myData.getCitiesNamesFromDbData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myData.setCurrentCity(query);
                myData.addNewCityIfNotExist(query);
                System.out.println("второй запуск loadWeatherData, temp =");
                navController.navigate(R.id.nav_home);
                menu.close();
                searchView.clearFocus();
                //Почему не работает?!?!
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_IS_WIND, interfaceChanger.getIsWind());
        editor.putInt(APP_PREFERENCES_IS_PRESSURE, interfaceChanger.getIsPressure());
        editor.putInt(APP_PREFERENCES_IS_AUTOTHEME, interfaceChanger.getIsAutoThemeChanging());
        editor.putString(APP_PREFERENCES_LAST_SEARCHED_CITY, myData.getCurrentCity());
        System.out.println("OnPause" + "interfaceChanger.getIsAutoThemeChanging()" + interfaceChanger.getIsAutoThemeChanging());
        editor.apply();
    }

    //обработка нажатий на пункты optionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //переходим на фрагмент настроек
                navController.navigate(R.id.nav_slideshow);
                return true;
            case R.id.toTheSearchPage:
                //переходим на фрагмент поиска
                navController.navigate(R.id.nav_gallery);
                //если нажали на поиск
            case R.id.app_bar_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_IS_WIND)) {
            // Получаем число из настроек
            isWind = mSettings.getInt(APP_PREFERENCES_IS_WIND, 1);
            interfaceChanger.setWind(isWind);
        }
        if (mSettings.contains(APP_PREFERENCES_IS_PRESSURE)) {
            // Получаем число из настроек
            isPressure = mSettings.getInt(APP_PREFERENCES_IS_PRESSURE, 1);
            interfaceChanger.setPressure(isPressure);
        }
        if (mSettings.contains(APP_PREFERENCES_IS_AUTOTHEME)) {
            // Получаем число из настроек
            isAutoTheme = mSettings.getInt(APP_PREFERENCES_IS_AUTOTHEME, 1);
            interfaceChanger.setIsAutoThemeChanging(isAutoTheme);
        }
        if (mSettings.contains(APP_PREFERENCES_LAST_SEARCHED_CITY)){
            //получаем название города из настроек
            myData.setCurrentCity(mSettings.getString(APP_PREFERENCES_LAST_SEARCHED_CITY, getString(R.string.noData)));
            myData.notifyObservers();
        }
    }

    @Override
    public void updateInterfaceViewData() {
        isWind = interfaceChanger.getIsWind();
        isPressure = interfaceChanger.getIsPressure();
        isAutoTheme = interfaceChanger.getIsAutoThemeChanging();
    }

    public WeatherLoader getWeatherLoader() {
        return weatherLoader;
    }
}
