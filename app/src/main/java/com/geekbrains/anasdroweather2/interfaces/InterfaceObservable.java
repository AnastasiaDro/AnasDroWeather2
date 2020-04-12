package com.geekbrains.anasdroweather2.interfaces;

public interface InterfaceObservable {
    //интерфейс наблюдаемого

        //регистрирует наблюдателя
        void registerObserver(InterfaceObserver observer);

        //удаляет наблюдателя
        void removeObserver(InterfaceObserver observer);

        //При изменении данных вызывается метод notifyObservers, который в свою очередь вызывает метод update
        //у всех слушателей, передавая им обновлённые данные
        void notifyInterfaceObservers();



}
