package com.geekbrains.anasdroweather2.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.geekbrains.anasdroweather2.R;

public class ThermometerView extends View {
    //цвет термометра
    private int thermColor = Color.GRAY;
    //цвет уровня температуры
    private int tempColor = Color.GREEN;

    //Начинаем рисовать градусник
    //Трубочка
    private RectF thermomRect = new RectF();
    //хвостик
    private RectF tailRect = new RectF();
    //изображение уровня градусов
    private Rect tempLevel = new Rect();



    //Краска термометра
    private Paint thermomPaint;
    //Краска температуры
    private Paint tempPaint;

    // Ширина элемента
    private int width = 0;
    // Высота элемента
    private int height = 0;

    //Уровень температуры
    private int level = 50;

    // Константы
    // Отступ элементов
    private final static int padding = 5;
    // Скругление углов палки градусника
    private final static int round = 5;
    // Ширина хвоста градусника
    private final static int headWidth = 30;
    //вычесть высоту
    private final static int headHeight = 60;


    public ThermometerView(Context context) {
        super(context);
        init();
    }

    // Вызывается при добавлении элемента в макет
    // AttributeSet attrs - набор параметров, указанных в макете для этого
    // элемента
    public ThermometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    // Вызывается при добавлении элемента в макет с установленными стилями
    // AttributeSet attrs - набор параметров, указанных в макете для этого
    // элемента
    // int defStyleAttr - базовый установленный стиль
    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    // Вызывается при добавлении элемента в макет с установленными стилями
    // AttributeSet attrs - набор параметров, указанных в макете для этого
    // элемента
    // int defStyleAttr - базовый установленный стиль
    // int defStyleRes - ресурс стиля, если он не был определён в предыдущем параметре
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }


    // Инициализация атрибутов пользовательского элемента из xml
    @SuppressLint("ResourceAsColor")
    private void initAttr(Context context, AttributeSet attrs){

        // При помощи этого метода получаем доступ к набору атрибутов.
        // На выходе - массив со значениями
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThermometerView, 0, 0);

        // Чтобы получить какое-либо значение из этого массива,
        // надо вызвать соответствующий метод и передать в этот метод имя
        // ресурса, указанного в файле определения атрибутов (attrs.xml)
        thermColor = typedArray.getColor(R.styleable.ThermometerView_thermColor, Color.GRAY);

        // Вторым параметром идёт значение по умолчанию. Оно будет подставлено,
        // если атрибут не будет указан в макете
        tempColor = typedArray.getColor(R.styleable.ThermometerView_tempColor, Color.parseColor("#81D4FA"));
        level = typedArray.getInteger(R.styleable.ThermometerView_level, 100);

// В конце работы дадим сигнал, что массив со значениями атрибутов
        // больше не нужен. Система в дальнейшем будет переиспользовать этот
        // объект, и мы больше не получим к нему доступ из этого элемента
        typedArray.recycle();
    }


        // Начальная инициализация полей класса
    private void init() {
        thermomPaint = new Paint();
        thermomPaint.setColor(thermColor);
        thermomPaint.setStyle(Paint.Style.FILL);
        tempPaint = new Paint();
        tempPaint.setColor(tempColor);
        tempPaint.setStyle(Paint.Style.FILL);
    }

    // Когда Android создаёт пользовательский экран, ещё не известны размеры
    // элемента, потому что используются расчётные единицы измерения: чтобы
    // элемент выглядел одинаково на разных устройствах, размер элемента
    // рассчитывается с учётом размера экрана, его разрешения и плотности
    // пикселей. Каждый раз при отрисовке экрана возникает необходимость
    // изменить размер элемента. Если нам надо нарисовать свой элемент,
    // переопределяем этот метод и задаём новые размеры элементов внутри View

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Получаем реальные ширину и высоту
        width = w - getPaddingLeft() - getPaddingRight();
        height = h - getPaddingTop() - getPaddingBottom();
                // Отрисовка градусника
        thermomRect.set(padding*2,
                padding+headHeight,
                width - padding*2 ,
                height - padding);

        tailRect.set(padding*6,
                padding,
                width -padding*6,
                height - thermomRect.height());

        tempLevel.set(7 * padding,
                (int)((height - 2 * padding - headWidth)*((double)level/(double)100)),
                width- padding*7,
                height - 4 * padding);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(thermomRect, round, round, thermomPaint);
        canvas.drawRect(tempLevel, tempPaint);
        canvas.drawRoundRect(tailRect, round, round, thermomPaint);

    }


    public void changeTempColor(int currentTemp) {
        if (currentTemp >=3 && currentTemp < 10) {
            tempColor = Color.parseColor("#81D4FA");
            level = 70;
        }
        if (currentTemp < 3) {
            tempColor = Color.parseColor("#3700B3");
            level = 90;
        }
        if (currentTemp >= 10) {
            tempColor = Color.parseColor("#e34234");
            level = 10;
        }
        tempPaint.setColor(tempColor);
        invalidate();

    }


}
