package com.geekbrains.anasdroweather2.model;

import android.util.Log;

import androidx.navigation.NavController;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.database.CitiesDatabase;
import com.geekbrains.anasdroweather2.database.City;
import com.geekbrains.anasdroweather2.database.CityDao;
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
    private static MyData instance;
    public List<Observer> observers;
    //узнаем время
    static Date currentDate;
    int currentHour;
    private ArrayList <String> citiesList;


    private HashMap citiesMap;
    String currentCity;
    private ImageLoader imageLoader;
    WeatherLoader weatherLoader;

//    public ArrayList<String> getImgStringsList() {
//        return searchedImgStringsList;
//    }
//
//    public ArrayList<String> getTempStringsList() {
//        return searchedTempStringsList;
//    }

   // public ArrayList<String> getCitiesNamesList() {
//        return searchedCitiesNamesList;
//    }

    //список изображений, температур и имен городов, которые мы искали
    ArrayList<String> searchedImgStringsList;
    ArrayList<String> searchedTempStringsList;
    ArrayList<String> lastLoadingDataList;
   // ArrayList<String> searchedCitiesNamesList;
    //для базы данных
    CitiesDatabase db;
    ArrayList<City> citiesDataList;
    CityDao cityDao;

    public void setWeatherLoader(WeatherLoader weatherLoader) {
        this.weatherLoader = weatherLoader;
    }

    //Получим HashMap с погодными данными
    public HashMap<Integer, String[]> getAllWeatherDataHashMap() {
        return allWeatherDataHashMap;
    }

    //геттеры для получения искомых данных
    public ArrayList<String> getSearchedImgStringsList() {
        return searchedImgStringsList;
    }

    public ArrayList<String> getSearchedTempStringsList() {
        return searchedTempStringsList;
    }

    //public ArrayList<String> getSearchedCitiesNamesList() {
//        return searchedCitiesNamesList;
//    }

    //сеттеры для получения искомых данных
    public void setSearchedImgStringsList(ArrayList<String> searchedImgStringsList) {
        this.searchedImgStringsList = searchedImgStringsList;
    }

    public void setSearchedTempStringsList(ArrayList<String> searchedTempStringsList) {
        this.searchedTempStringsList = searchedTempStringsList;
    }

//    public void setSearchedCitiesNamesList(ArrayList<String> searchedCitiesNamesList) {
//        this.searchedCitiesNamesList = searchedCitiesNamesList;
//    }

    public void setCitiesList(ArrayList<String> citiesList) {
        this.citiesList = citiesList;
    }

    private MyData() {
        currentCity = "Moscow";
        currentHour = 0;
        observers = new LinkedList<>();
        //Получим базу данных
        citiesList = new <String>ArrayList();

        //подключимся к базе данных
        db = App.getInstance().getDatabase();
        cityDao = db.cityDao();
        citiesDataList = new ArrayList<>();

        allWeatherDataHashMap = new HashMap<>();
        this.weatherRequestIsDone = false;
        //пока зададим города тут
        exceptionWhileLoading = null;
        imageLoader = new ImageLoader();

        //Массивы с данными о городах, которые искали
        searchedImgStringsList = new ArrayList<>();
        searchedTempStringsList = new ArrayList<>();
        //lastLoadingDataList = new ArrayList<>();
        //searchedCitiesNamesList = new ArrayList<>();


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

    public ArrayList <String> getCitiesList() {
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

    //метод удаления последнего элемента из массива и сдвига всех элементов
    //используется в классе SearchAdapter
    public ArrayList deleteCopyAddNewList(String newString, ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(newString)) {
                arrayList.remove(i);
                searchedTempStringsList.remove(i);
                searchedImgStringsList.remove(i);
               // lastLoadingDataList.remove(i);
            }
        }
        arrayList.add(newString);
        return arrayList;
    }

    public ArrayList<String> lastToFirst(ArrayList<String> arrayList) {
        String lastItem = arrayList.get(arrayList.size() - 1);
        arrayList.add(0, lastItem);
        arrayList.remove(arrayList.size() - 1);
        return arrayList;
    }

    public ArrayList addToListIfNotExist(ArrayList arrayList, String newString) {
        int count = checkListForExistElement(arrayList, newString);
        if (count == 0) {
            arrayList.add(newString);
        }
        return arrayList;
    }

    //метод выгрузки данных из БД
    public void loadDbDataToMyData() {
        DbLoaderThread dbLoaderThread = new DbLoaderThread(cityDao, citiesDataList);
        dbLoaderThread.start();
        try {
            dbLoaderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("MyData", "ОШИБКА В ПОДОЖДАТЬ ПОТОК dbLoaderThread");
        }
    }

    //получим имена выгруженных городов
    //метод запускается в MainActivity
    public void getCitiesNamesFromDbData() {
        for (int i = 0; i < citiesDataList.size(); i++) {
            citiesList.add(citiesDataList.get(i).cityName);
            searchedTempStringsList.add(citiesDataList.get(i).cityTemp);
            searchedImgStringsList.add(citiesDataList.get(i).imgString);
        }
    }


    //метод добавления города если его ещё не было
    public void addNewCityIfNotExist(String newName) {
        int count = checkListForExistElement(citiesList, newName);
        if (count == 0) {
       //     citiesList.add(newName);
            City city = new City();
            city.cityName = newName;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cityDao.insert(city);
                }
            }).start();
        }
    }

    //метод удаления города из базы данных
    public void deleteCityFromDb(String cityName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                City city = cityDao.getByCityName(cityName);
                cityDao.delete(city);
            }
        }).start();
    }

    //добавляем данные города, создавая новый поток
    public void addCityDataToDb(String cityName, String temp, String lastLoadTime, String imgString) {
        //проверим массив на пустоту
        if (citiesDataList.size() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cityDao.updateCityTempInDb(cityName, temp);
                    cityDao.updateCityLoadTimeInDp(cityName, lastLoadTime);
                    cityDao.updateCityImgInDb(cityName, imgString);
                }
            }).start();
        }
    }

    //проверка массива на повторы элемента и подсчёт, сколько раз он повторился
    private int checkListForExistElement(ArrayList arrayList, String newString) {
        int count = 0;
        System.out.println(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(newString)) {
                count++;
            }
        }
        return count;
    }
}



