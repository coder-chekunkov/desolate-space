package com.book.unnamed.desolatespacemyproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.book.unnamed.desolatespacemyproject.R;

/**
 * Created by unnamed on 05.05.2017.
 */

public class LevelEnd extends MenuActivity {
    //Переменная "Концовки":
    private int End;
    //Все Для Видео:
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //"Вспоминаем" переменную Концовки:
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
        End = preferences.getInt("End", End);

        //"Вспоминаем" переменную Уровня:
        SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
        progress = preferences2.getInt("id", progress);

        //Регистрируем VideoView:
        video = (VideoView) findViewById(R.id.Video);

        //Хорошая Концовка:
        if (End == 1) {
            //Работа с Медиаплеером:
            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.good_end));
            video.setOnCompletionListener(videoListener);
            video.start();
        }
        //Плохая Концовка:
        if (End == 2) {
            //Работа с Медиаплеером:
            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bad_end));
            video.setOnCompletionListener(videoListener2);
            video.start();
        }
    }

    //Что делать, если закончилось видео:
    MediaPlayer.OnCompletionListener videoListener
            = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer arg0) {
           //progress = 1;
           video.resume();
            Intent BackPressed = new Intent(LevelEnd.this, MenuActivity.class);
            startActivity(BackPressed);

        }
    };

    //Что делать, если закончилось видео:
    MediaPlayer.OnCompletionListener videoListener2
            = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer arg0) {
            //progress = 1;
            video.resume();
            Intent BackPressed = new Intent(LevelEnd.this, MenuActivity.class);
            startActivity(BackPressed);
        }
    };
}
