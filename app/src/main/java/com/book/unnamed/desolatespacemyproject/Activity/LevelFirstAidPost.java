package com.book.unnamed.desolatespacemyproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.book.unnamed.desolatespacemyproject.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by unnamed on 02.05.2017.
 */

public class LevelFirstAidPost extends MenuActivity {

    private Button buttonTime;
    private TextView textTime;

    private static final long TIME = 25;
    private long currentTime = 0;
    private volatile boolean isPressed = false;
    private volatile boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstaidpost);

        textTime = (TextView) findViewById(R.id.time2);

        buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonTime.setOnClickListener(this);

        //"Вспоминаем" переменную Уровня:
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
        progress = preferences.getInt("id", progress);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                currentTime++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textTime.setText("Найдите Аптечку за " + String.valueOf(TIME - currentTime) + " секунд");
                    }

                });
                if (currentTime >= TIME) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent try_again = new Intent(LevelFirstAidPost.this, You_Lost_FirstAidPost.class);
                            startActivity(try_again);
                        }
                    });
                    cancel();   //Помогите! Я НИХУЯ не понимаю....
                }
                if (isPressed) {
                    isSuccess = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress = progress + 2;

                            //"Запоминаем" переменную Уровня:
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("id", progress);
                            editor.commit();

                            Intent start = new Intent(LevelFirstAidPost.this, GameActivity.class);
                            startActivity(start);
                        }
                    });
                    cancel();
                }



            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTime:
                isPressed = true;
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent BackPressed = new Intent(this, MenuActivity.class);
        startActivity(BackPressed);
    }
}
