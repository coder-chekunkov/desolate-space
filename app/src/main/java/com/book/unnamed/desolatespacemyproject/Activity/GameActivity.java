package com.book.unnamed.desolatespacemyproject.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.book.unnamed.desolatespacemyproject.Messages.BaseMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonBadChooseMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonBadEndMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonChemistMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonGoodChooseMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonGoodEndMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonLevelLeadershipMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ButtonOutput;
import com.book.unnamed.desolatespacemyproject.Messages.ComputerBadMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ComputerMessages;
import com.book.unnamed.desolatespacemyproject.Messages.HeaderMessages;
import com.book.unnamed.desolatespacemyproject.Messages.ImageMessages;
import com.book.unnamed.desolatespacemyproject.Messages.SoundMessages;
import com.book.unnamed.desolatespacemyproject.Messages.TextMessages;
import com.book.unnamed.desolatespacemyproject.Messages.WriterMessages;
import com.book.unnamed.desolatespacemyproject.Parsing.ParsingClass;
import com.book.unnamed.desolatespacemyproject.R;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;

public class GameActivity extends MenuActivity {

    //Создаем LinearLayot:
    LinearLayout mLLContent;
    //Создаем ArrayList:
    ArrayList<BaseMessages> levelMessages;
    //Создаем Число Для Таймера:
    private int mInterval = 1500;
    //Создаем Handler:
    private Handler mHandler;
    //Переменная Для Прогресса:
    private boolean isRunning = true;
    //Переменная Для Handler:
    private int currentMessage = 0;
    //Стандартые Размеры Элементов:
    int size_text = 16;
    int size_space = 5;


    //Переменаая Для "Концовки":
    int End = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Регистрируем LinearLayot:
        mLLContent = (LinearLayout) findViewById(R.id.ll_content);

        //Узнаем Размеры Экрана:
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Маленький Экран:
        if(width<=320&&width>=240 && height<=480&&height>=300){
            size_text = 10;
            size_space = 2;
        }else

            //Нормальный Экран:
            if(width<=1080&&width>=480 && height<=1920&&height>=1920){
                size_text = 20;
                size_space = 5;
            }else

                //Большой Экран:
                if(width<=3000&&width>=1500 && height<=3000&&height>=1800){
                    size_text = 23;
                    size_space = 8;

                    RelativeLayout view = (RelativeLayout) findViewById(R.id.activity_game);
                    view.setBackgroundResource(R.drawable.background_bigpng);
                }


        mHandler = new Handler();

        //Вызываем loadNextLevel:
        startRepeatingTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //"Вспоминаем" переменную Уровня:
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                        progress = preferences.getInt("id", progress);

                        //Работа с Парсингом:
                        levelMessages = ParsingClass.Pars(GameActivity.this, progress);

                        //Создаем Анимацию, для появления Текста:
                        Animation anim = AnimationUtils.loadAnimation(GameActivity.this, R.anim.anim);

                        if(currentMessage < levelMessages.size()){

                            BaseMessages message = levelMessages.get(currentMessage);

                               //Выводим Картинку:
                               if (message instanceof ImageMessages) {
                                   //Создаем и Выводим "текст-пробел":
                                   TextView tv_probel = new TextView(GameActivity.this);
                                   tv_probel.setTextColor(' ');
                                   tv_probel.setTextSize(size_space);
                                   mLLContent.addView(tv_probel);

                                   //Картинка:
                                   ImageMessages imageMessage = (ImageMessages) message;
                                   ImageView image = new ImageView(GameActivity.this);

                                   //Выводим "картинку-сюжет":
                                   String picName = imageMessage.image;
                                   int resId = getResources().getIdentifier(picName, "drawable", getPackageName());
                                   Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
                                   ImageView iv = new ImageView(GameActivity.this);
                                   iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                           ViewGroup.LayoutParams.WRAP_CONTENT));
                                   iv.setImageBitmap(bm);
                                   iv.setScaleType(ImageView.ScaleType.FIT_XY);
                                   iv.setAdjustViewBounds(true);
                                   mLLContent.addView(iv);
                                   iv.startAnimation(anim);
                                   isRunning = true;


                               } else
                                   //Выводим Кнопку:
                                   if (message instanceof ButtonMessages) {
                                       //Создаем и Выводим "текст-пробел":
                                       TextView tv_probel = new TextView(GameActivity.this);
                                       tv_probel.setTextColor(' ');
                                       tv_probel.setTextSize(size_space);
                                       mLLContent.addView(tv_probel);

                                       //Выводим Кнопка:
                                       ButtonMessages buttonMessage = (ButtonMessages) message;
                                       Button btn = new Button(GameActivity.this);
                                       btn.setText(buttonMessage.button);
                                       btn.setTextSize(size_text);
                                       btn.setTextColor(Color.RED);
                                       btn.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                       mLLContent.addView(btn);
                                       btn.startAnimation(anim);


                                       //Ставим Слушателя на Кнопкку "Продолжить":
                                       btn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               progress = progress + 1;

                                               //"Запоминаем" переменную Уровня:
                                               SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                               SharedPreferences.Editor editor = preferences.edit();
                                               editor.putInt("id", progress);
                                               editor.commit();

                                               mLLContent.removeAllViews();

                                               Intent start = new Intent(GameActivity.this, GameActivity.class);
                                               startActivity(start);

                                           }
                                       });
                                       isRunning = true;


                                   } else

                                       //Выводим Текст:
                                       if (message instanceof TextMessages) {
                                           //Создаем и Выводим "текст-пробел":
                                           TextView tv_probel = new TextView(GameActivity.this);
                                           tv_probel.setTextColor(' ');
                                           tv_probel.setTextSize(size_space);
                                           mLLContent.addView(tv_probel);

                                           //Выводим Текст:
                                           TextMessages textMessage = (TextMessages) message;
                                           TextView tv = new TextView(GameActivity.this);
                                           tv.setTextSize(size_text);
                                           tv.setTextColor(Color.WHITE);
                                           tv.setText(textMessage.text);
                                           mLLContent.addView(tv);
                                           tv.startAnimation(anim);
                                           isRunning = true;



                                       } else if (message instanceof HeaderMessages) {
                                           //Создаем и Выводим "текст-пробел":
                                           TextView tv_probel = new TextView(GameActivity.this);
                                           tv_probel.setTextColor(' ');
                                           tv_probel.setTextSize(size_space);
                                           mLLContent.addView(tv_probel);

                                           //Создаем и Выводим "текст-пробел":
                                           TextView tv_probel2 = new TextView(GameActivity.this);
                                           tv_probel2.setTextColor(' ');
                                           tv_probel2.setTextSize(size_space);
                                           mLLContent.addView(tv_probel2);

                                           //Выводим Заголовок:
                                           HeaderMessages headerMessage = (HeaderMessages) message;
                                           TextView hd = new TextView(GameActivity.this);
                                           hd.setText(headerMessage.header);
                                           hd.setTextSize(size_text);
                                           hd.setGravity(CENTER);
                                           hd.setTextColor(Color.WHITE);
                                           mLLContent.addView(hd);
                                           hd.startAnimation(anim);
                                           isRunning = true;


                                       } else if (message instanceof ComputerMessages) {
                                           //Создаем и Выводим "текст-пробел":
                                           TextView tv_probel = new TextView(GameActivity.this);
                                           tv_probel.setTextColor(' ');
                                           tv_probel.setTextSize(size_space);
                                           mLLContent.addView(tv_probel);

                                           //Выводим Текст Компьютера:
                                           ComputerMessages computerMessages = (ComputerMessages) message;
                                           TextView ct = new TextView(GameActivity.this);
                                           ct.setText(computerMessages.textComputer);
                                           ct.setTextSize(size_text);
                                           ct.setTextColor(Color.GREEN);
                                           mLLContent.addView(ct);
                                           ct.startAnimation(anim);
                                           isRunning = true;


                                       } else
                                           //"Плохой" Текст Компьютера:
                                           if (message instanceof ComputerBadMessages) {
                                               //Создаем и Выводим "текст-пробел":
                                               TextView tv_probel = new TextView(GameActivity.this);
                                               tv_probel.setTextColor(' ');
                                               tv_probel.setTextSize(size_space);
                                               mLLContent.addView(tv_probel);

                                               //Выводим Текст Компьютера:
                                               ComputerBadMessages computerBadMessages = (ComputerBadMessages) message;
                                               TextView bct = new TextView(GameActivity.this);
                                               bct.setText(computerBadMessages.badMessages);
                                               bct.setTextSize(size_text);
                                               bct.setTextColor(getResources().getColor(R.color.colorDarkRed));
                                               mLLContent.addView(bct);
                                               bct.startAnimation(anim);
                                               isRunning = true;

                                           } else

                                               //Переход на другой активити:
                                               if (message instanceof ButtonLevelLeadershipMessages) {
                                                   //Создаем и Выводим "текст-пробел":
                                                   TextView tv_probel = new TextView(GameActivity.this);
                                                   tv_probel.setTextColor(' ');
                                                   tv_probel.setTextSize(size_space);
                                                   mLLContent.addView(tv_probel);

                                                   //Выводим Кнопка:
                                                   ButtonLevelLeadershipMessages buttonLevelLeadershipMessages = (ButtonLevelLeadershipMessages) message;
                                                   Button btn = new Button(GameActivity.this);
                                                   btn.setText(buttonLevelLeadershipMessages.buttonLevelLeaderShip);
                                                   btn.setTextSize(size_text);
                                                   btn.setTextColor(Color.RED);
                                                   btn.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                                   btn.startAnimation(anim);
                                                   mLLContent.addView(btn);


                                                   //Ставим Слушателя на Кнопкку "Продолжить":
                                                   btn.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {

                                                           //"Запоминаем" переменную Уровня:
                                                           SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                                           SharedPreferences.Editor editor = preferences.edit();
                                                           editor.putInt("id", progress);
                                                           editor.commit();

                                                           Intent start2 = new Intent(GameActivity.this, LevelLeadership.class);
                                                           startActivity(start2);

                                                       }
                                                   });
                                                   isRunning = true;

                                               } else

                                                   //Переход на другой активити:
                                                   if (message instanceof ButtonOutput) {
                                                       //Создаем и Выводим "текст-пробел":
                                                       TextView tv_probel = new TextView(GameActivity.this);
                                                       tv_probel.setTextColor(' ');
                                                       tv_probel.setTextSize(size_space);
                                                       mLLContent.addView(tv_probel);

                                                       //Выводим Кнопку:
                                                       ButtonOutput buttonoutput = (ButtonOutput) message;
                                                       Button btn = new Button(GameActivity.this);
                                                       btn.setText(buttonoutput.buttonOutput);
                                                       btn.setTextSize(size_text);
                                                       btn.setTextColor(Color.RED);
                                                      btn.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                                       btn.startAnimation(anim);
                                                       mLLContent.addView(btn);


                                                       btn.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               //"Запоминаем" переменную Уровня:
                                                               SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                                               SharedPreferences.Editor editor = preferences.edit();
                                                               editor.putInt("id", progress);
                                                               editor.commit();

                                                               Intent start2 = new Intent(GameActivity.this, LevelDoor.class);
                                                               startActivity(start2);

                                                           }
                                                       });
                                                       isRunning = true;

                                                   } else
                                                       //Слова Автора:
                                                       if (message instanceof WriterMessages) {
                                                           //Создаем и Выводим "текст-пробел":
                                                           TextView tv_probel = new TextView(GameActivity.this);
                                                           tv_probel.setTextColor(' ');
                                                           tv_probel.setTextSize(size_space);
                                                           mLLContent.addView(tv_probel);

                                                           //Создаем и Выводим "слова автора":
                                                           WriterMessages writerMessages = (WriterMessages) message;
                                                           TextView writer = new TextView(GameActivity.this);
                                                           writer.setText(writerMessages.textWriter);
                                                           writer.setTextSize(size_text);
                                                           writer.setTextColor(getResources().getColor(R.color.colorDarkOrange));
                                                           writer.setGravity(CENTER);
                                                           mLLContent.addView(writer);
                                                           writer.startAnimation(anim);
                                                           isRunning = true;
                                                       }

                               if (message instanceof SoundMessages) {
                                   //Создаем и Выводим "текст-пробел":
                                   TextView tv_probel = new TextView(GameActivity.this);
                                   tv_probel.setTextColor(' ');
                                   tv_probel.setTextSize(size_space);
                                   mLLContent.addView(tv_probel);

                                   //Создаем и Выводим текст "Голоса":
                                   SoundMessages soundMessages = (SoundMessages) message;
                                   TextView sound = new TextView(GameActivity.this);
                                   sound.setText(soundMessages.textSound);
                                   sound.setTextSize(size_text);
                                   sound.setTextColor(getResources().getColor(R.color.colorDarkGreen));
                                   mLLContent.addView(sound);
                                   sound.startAnimation(anim);
                                   isRunning = true;
                               }

                               if (message instanceof ButtonChemistMessages) {
                                   //Создаем и Выводим "текст-пробел":
                                   TextView tv_probel = new TextView(GameActivity.this);
                                   tv_probel.setTextColor(' ');
                                   tv_probel.setTextSize(size_space);
                                   mLLContent.addView(tv_probel);

                                   //Создаем и Выводим Кнопку для перехода на другой Activity:
                                   ButtonChemistMessages buttonChemistMessages = (ButtonChemistMessages) message;
                                   Button btnC = new Button(GameActivity.this);
                                   btnC.setText(buttonChemistMessages.buttonChemist);
                                   btnC.setTextSize(size_text);
                                   btnC.setTextColor(Color.RED);
                                   btnC.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                   mLLContent.addView(btnC);
                                   btnC.startAnimation(anim);


                                   btnC.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           //"Запоминаем" переменную Уровня:
                                           SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                           SharedPreferences.Editor editor = preferences.edit();
                                           editor.putInt("id", progress);
                                           editor.commit();

                                           Intent start2 = new Intent(GameActivity.this, LevelFirstAidPost.class);
                                           startActivity(start2);

                                       }
                                   });
                                   isRunning = true;
                               }

                               if (message instanceof ButtonGoodEndMessages) {
                                   //Создаем и Выводим "текст-пробел":
                                   TextView tv_probel = new TextView(GameActivity.this);
                                   tv_probel.setTextColor(' ');
                                   tv_probel.setTextSize(size_space);
                                   mLLContent.addView(tv_probel);

                                   //Создаем и Выводим Кнопку для хорошей концовки:
                                   ButtonGoodEndMessages buttonGoodEndMessages = (ButtonGoodEndMessages) message;
                                   Button btnGE = new Button(GameActivity.this);
                                   btnGE.setText(buttonGoodEndMessages.buttonGoodEnd);
                                   btnGE.setTextSize(size_text);
                                   btnGE.setTextColor(Color.RED);
                                   btnGE.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                   mLLContent.addView(btnGE);
                                   btnGE.startAnimation(anim);
                                   btnGE.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           End = 1;
                                           //"Запоминаем" переменную Уровня:
                                           SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                           SharedPreferences.Editor editor = preferences.edit();
                                           editor.putInt("End", End);
                                           editor.commit();

                                           Intent end = new Intent(GameActivity.this, LevelEnd.class);
                                           startActivity(end);

                                       }
                                   });
                                   isRunning = true;
                               }

                               if (message instanceof ButtonBadEndMessages) {
                                   //Создаем и Выводим Кнопку для плохой концовки:
                                   ButtonBadEndMessages buttonBadEndMessages = (ButtonBadEndMessages) message;
                                   Button btnBE = new Button(GameActivity.this);
                                   btnBE.setText(buttonBadEndMessages.buttonBadEnd);
                                   btnBE.setTextSize(size_text);
                                   btnBE.setTextColor(Color.RED);
                                   btnBE.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                   mLLContent.addView(btnBE);
                                   btnBE.startAnimation(anim);
                                   btnBE.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           End = 2;
                                           //"Запоминаем" переменную Уровня:
                                           SharedPreferences preferences = getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
                                           SharedPreferences.Editor editor = preferences.edit();
                                           editor.putInt("End", End);
                                           editor.commit();

                                           Intent end = new Intent(GameActivity.this, LevelEnd.class);
                                           startActivity(end);
                                       }
                                   });
                                   isRunning = true;
                               }

                            if(message instanceof ButtonGoodChooseMessages){
                                //Создаем и Выводим "текст-пробел":
                                TextView tv_probel = new TextView(GameActivity.this);
                                tv_probel.setTextColor(' ');
                                tv_probel.setTextSize(size_space);
                                mLLContent.addView(tv_probel);

                                //Создаем и Выводим Кнопку для "Правильного" Выбора:
                                ButtonGoodChooseMessages buttonGoodChooseMessages = (ButtonGoodChooseMessages) message;
                                Button btnBC = new Button(GameActivity.this);
                                btnBC.setText(buttonGoodChooseMessages.buttonGoodChoose);
                                btnBC.setTextSize(size_text);
                                btnBC.setTextColor(Color.RED);
                                btnBC.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                mLLContent.addView(btnBC);
                                btnBC.startAnimation(anim);
                                isRunning = false;

                                btnBC.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isRunning = true;
                                        currentMessage++;
                                        startRepeatingTask();
                                    }
                                });


                            }

                            if (message instanceof ButtonBadChooseMessages){
                                //Создаем и Выводим "текст-пробел":
                                TextView tv_probel = new TextView(GameActivity.this);
                                tv_probel.setTextColor(' ');
                                tv_probel.setTextSize(size_space);
                                mLLContent.addView(tv_probel);

                                //Создаем и Выводим Кнопку для "Правильного" Выбора:
                                ButtonBadChooseMessages buttonBadChooseMessages = (ButtonBadChooseMessages) message;
                                Button btnBG = new Button(GameActivity.this);
                                btnBG.setText(buttonBadChooseMessages.buttonBadChoose);
                                btnBG.setTextSize(size_text);
                                btnBG.setTextColor(Color.RED);
                                btnBG.setBackground(context.getResources().getDrawable(R.color.colorTransparent));
                                mLLContent.addView(btnBG);
                                btnBG.startAnimation(anim);
                                isRunning = true;

                                btnBG.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent bad_choose = new Intent(GameActivity.this, Level_Bad_Choose.class);
                                        startActivity(bad_choose);
                                    }
                                });
                            }


                       }else{
                           isRunning = false;

                           TextView tv_probel = new TextView(GameActivity.this);
                           tv_probel.setTextColor(' ');
                           tv_probel.setTextSize(size_space);
                           mLLContent.addView(tv_probel);
                       }
                    }
                });
            } finally {
                if (isRunning) {
                    mHandler.postDelayed(mStatusChecker, mInterval);
                    currentMessage++;
                }

            }
        }
    };


    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }


}
