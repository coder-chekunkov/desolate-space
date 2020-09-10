package com.book.unnamed.desolatespacemyproject.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.book.unnamed.desolatespacemyproject.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    //Диалоговое  Окно:
    AlertDialog.Builder ad;
    //Контекст:
    Context context;
    //Кнопки "Начать Игру" и "Продолжить Игру":
    Button ButtonStart, ButtonContinue;
    private AdView mAdView;
    //Переменная для "Прохождения" игры:
    int progress = 1;
    //Переменная Для "AdMob":
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Регистрация Кнопок:
        ButtonStart = (Button) findViewById(R.id.ButtonStart);
        ButtonStart.setOnClickListener(this);
        ButtonContinue = (Button) findViewById(R.id.ButtonContinue);
        ButtonContinue.setOnClickListener(this);


        AdRequest adRequest = new AdRequest.Builder().build();
        //Реклама:
        MobileAds.initialize(this, "ca-app-pub-2428921165694784");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2428921165694784/9322855163");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{
                    Log.d("TAG", "Error");
                }
            }
        });


        //Узнаем Размеры Экрана:
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Большой Экран:
        if(width<=3000&&width>=1500 && height<=3000&&height>=1800){
            RelativeLayout view = (RelativeLayout) findViewById(R.id.activity_main);
            view.setBackgroundResource(R.drawable.desolate_space_menu_big);
        }

        //Элементы Для Диалогового Окна:
        context = MenuActivity.this;
        String title = "Начать Новую Игру:";
        final String message = "Вы уверенны, что хотите начать новую игру?";
        String button1String = "Начать Новую Игру";
        String button2String = "Отмена";

        ad = new AlertDialog.Builder(this);
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setIcon(R.drawable.icon);

        //Первая Кнопка:
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                progress = 1;
                //"Запоминаем" переменную Уровня:
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id", progress);
                editor.commit();

                Intent start = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(start);



            }
        });


        //Вторая Кнопка:
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });

        ad.setCancelable(true);
    }

    @Override
    public void onClick(View view) {
        //"Вспоминаем" переменную Уровня:
        SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
        progress = preferences2.getInt("id", progress);

        //Проверка прогресса игры:
        switch (view.getId()) {
            //Начать Новую Игру:
            case R.id.ButtonStart:
                //Вызываем Диалоговое окно:
               ad.show();
                break;
            //Продолжить Игру:
            case R.id.ButtonContinue:
                if(progress == 1) {
                    //Если Пользователь не приступал к игре:
                    Toast.makeText(this, "Вы ещё не приступали к прохождению игры", Toast.LENGTH_SHORT).show();
                }else{
                    //Продолжить Игру:
                    Intent next = new Intent(this, GameActivity.class);
                    startActivity(next);
                }
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent BackPressed = new Intent(this, MenuActivity.class);
        startActivity(BackPressed);
    }
}

