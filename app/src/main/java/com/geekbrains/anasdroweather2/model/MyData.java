package com.geekbrains.anasdroweather2.model;

import androidx.navigation.NavController;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.interfaces.Observable;
import com.geekbrains.anasdroweather2.interfaces.Observer;
import com.geekbrains.anasdroweather2.rest.WeatherLoader;
import com.geekbrains.anasdroweather2.ui.home.ImageLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


//Класс с данными, наблюдаемый
public class MyData implements Observable {
    boolean weatherRequestIsDone;

    NavController navController;
    //Переменные для вывода сообщений об исключениях
    private Exception exceptionWhileLoading;
    private int exceptionNameId;
    private int exceptionAdviceId;

    //В этот Хэшмап будем класть все погодные данные int - порядковый номер в ArrayList-е,
    //а массив строк - собственно данные: время, температура, давление, ветер
    //соответствие номеров элементов массива значениям есть в классе Constants
    private HashMap<Integer, String[]> allWeatherDataHashMap;
    //поток, загружающий данные о погоде
    Thread weatherLoaderThread;
    private static MyData instance;
    public List<Observer> observers;
    //узнаем время
    static Date currentDate;
    int currentHour;
    private ArrayList<String> citiesList;
    private int[] lastSearchCitiesArr;
    String currentCity;
    private ImageLoader imageLoader;

    public WeatherLoader getWeatherLoader() {
        return weatherLoader;
    }

    public void setWeatherLoader(WeatherLoader weatherLoader) {
        this.weatherLoader = weatherLoader;
    }

    WeatherLoader weatherLoader;

    //Получим HashMap с погодными данными
    public HashMap<Integer, String[]> getAllWeatherDataHashMap() {
        return allWeatherDataHashMap;
    }

    private MyData() {
        currentCity = "Moscow";
        currentHour = 0;
        observers = new LinkedList<>();
        citiesList = new <String>ArrayList();
        citiesList.add("Moscow");
        citiesList.add("Saint-Petersburg");
        citiesList.add("Kazan");
        citiesList.add("Sochi");
        citiesList.add("Murmansk");
        allWeatherDataHashMap = new HashMap<>();
        this.weatherRequestIsDone = false;
        //пока зададим города тут
        lastSearchCitiesArr = new int[]{R.string.moscow, R.string.kazan, R.string.spb};
        exceptionWhileLoading = null;
        imageLoader = new ImageLoader();
    }

    //сделаем наблюдаемый класс сингл-тоном
    public static MyData getInstance() {
        if (instance == null) {
            instance = new MyData();
        }
//получим текущий час
        currentDate = new Date();
        currentDate.getTime();
//вернём MyData
        return instance;
    }

    //добавить наблюдателя
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Наблюдатель добавлен. Список наблюдателей " + observers.toString());
    }

    //удалить наблюдателя
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Наблюдатель удалён. Список наблюдателей " + observers.toString());
    }

    //уведомить наблюдателей
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateViewData();
        }
    }

    //Высчитать текущий час
    private int takeCurrentHour(Date currentDate) {
        DateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String dateText = hourFormat.format(currentDate);
        currentHour = Integer.parseInt(dateText);
        return currentHour;
    }

    //геттер текущего часа
    public int getCurrentHour() {
        takeCurrentHour(currentDate);
        return currentHour;
    }

    public ArrayList getCitiesList() {
        return citiesList;
    }

    //получим или изменим текущий город
    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    //Получим или изменим navController
    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    //Получим или изменим исключение
    public void setException(Exception exceptionWhileLoading) {
        this.exceptionWhileLoading = exceptionWhileLoading;
    }

    public void setExceptionWhileLoading(Exception exceptionWhileLoading, int exceptionNameId, int exceptionAdviceId) {
        this.exceptionWhileLoading = exceptionWhileLoading;
        this.exceptionNameId = exceptionNameId;
        this.exceptionAdviceId = exceptionAdviceId;
    }

    public Exception getException() {
        return exceptionWhileLoading;
    }

    public int getExceptionNameId() {
        return exceptionNameId;
    }

    public int getExceptionAdviceId() {
        return exceptionAdviceId;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }
}
