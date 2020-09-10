package com.book.unnamed.desolatespacemyproject.Parsing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.book.unnamed.desolatespacemyproject.Activity.MenuActivity;
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
import com.book.unnamed.desolatespacemyproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by unnamed on 08.04.2017.
 */

public class ParsingClass extends MenuActivity {


    public static ArrayList<BaseMessages> Pars(Context context, int progress) {
        int parserNumber = 0;

        ArrayList<BaseMessages> messages = new ArrayList<>();

        //"Вспоминаем" переменную Уровня:
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("SAVE", MODE_PRIVATE);
        progress = preferences.getInt("id", progress);

        //Начало Парсинга:
        try {
            Resources res = context.getResources();

            //Выбираем xml-файл:
            XmlPullParser xpp = res.getXml(R.xml.story);

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (!"level".equals(xpp.getName()) || xpp.getAttributeValue(null, "id") == null) {
                    xpp.next();
                    continue;
                } else {
                    if (Integer.valueOf(xpp.getAttributeValue(null, "id")) != progress) {
                        xpp.next();
                        continue;
                    }
                }

                while (!(xpp.getEventType() == XmlPullParser.END_TAG && "level".equals(xpp.getName()))) {
                    switch (xpp.getEventType()) {
                        // Начало тэга:
                        case XmlPullParser.START_TAG:
                            //Если имя тэга - "header", то:
                            if (xpp.getName().equals("header")) {
                                parserNumber = 1;
                            } else
                                //Если имя тэга - "message", то:
                                if (xpp.getName().equals("message")) {
                                    parserNumber = 2;
                                } else
                                    //Если имя тэга - "picture", то:
                                    if (xpp.getName().equals("picture")) {
                                        parserNumber = 3;
                                    } else
                                        //Если имя тэга - "button", то:
                                        if (xpp.getName().equals("button")) {
                                            parserNumber = 4;
                                        }else
                                        //Если имя тэга - "message_computer", то:
                                        if (xpp.getName().equals("message_computer")){
                                            parserNumber = 5;
                                        }else
                                        //Если имя тэга - "message_computer_bad", то:
                                        if (xpp.getName().equals("message_computer_bad")){
                                            parserNumber = 6;
                                        }else
                                        //Если имя тэга -"button_leadership", то:
                                        if (xpp.getName().equals("button_leadership")){
                                            parserNumber = 7;
                                        }else
                                        //Если имя тэга -"button_output", то:
                                        if(xpp.getName().equals("button_output")){
                                            parserNumber = 8;
                                        }else
                                        //Если имя тэга -"writer", то:
                                        if(xpp.getName().equals("writer")){
                                            parserNumber = 9;
                                        }else
                                            //Если имя тэга -"sound", то:
                                        if(xpp.getName().equals("sound")){
                                            parserNumber = 10;
                                        }else
                                            //Если имя тэга -"button_chemist", то:
                                            if(xpp.getName().equals("button_chemist")){
                                            parserNumber = 11;
                                        }else
                                        //Если имя тэга -"button_good_end", то:
                                        if(xpp.getName().equals("button_good_end")) {
                                            parserNumber = 12;
                                        }else
                                        //Если имя тэга -"button_bad_end", то:
                                        if(xpp.getName().equals("button_bad_end")) {
                                            parserNumber = 13;
                                        }else
                                        //Если имя тэга -"button_good_choose", то:
                                        if(xpp.getName().equals("button_good_choose")){
                                            parserNumber = 14;
                                        }else
                                            //Если имя тэга -"button_bad_choose", то:
                                            if(xpp.getName().equals("button_bad_choose")){
                                                parserNumber = 15;
                                            }



                            break;
                        // Содержимое тэга:
                        case XmlPullParser.TEXT:
                            //Заголовок:
                            if (parserNumber == 1) {
                                HeaderMessages headerMessages = new HeaderMessages();
                                headerMessages.header = xpp.getText();
                                messages.add(headerMessages);
                            }

                            //Текст:
                            if (parserNumber == 2) {
                                TextMessages textMessage = new TextMessages();
                                textMessage.text = xpp.getText();
                                messages.add(textMessage);
                            }
                            //Картинка:
                            if (parserNumber == 3) {
                                ImageMessages imageMessage = new ImageMessages();
                                imageMessage.image = xpp.getText();
                                messages.add(imageMessage);
                            }
                            //Кнопка:
                            if (parserNumber == 4) {
                                ButtonMessages buttonMessage = new ButtonMessages();
                                buttonMessage.button = xpp.getText();
                                messages.add(buttonMessage);
                            }
                            //Текст Компьютера:
                            if (parserNumber == 5){
                                ComputerMessages computerMessages = new ComputerMessages();
                                computerMessages.textComputer = xpp.getText();
                                messages.add(computerMessages);
                            }
                            //"Плохой Текст для Компьютера:
                            if(parserNumber == 6){
                                ComputerBadMessages computerBadMessages = new ComputerBadMessages();
                                computerBadMessages.badMessages = xpp.getText();
                                messages.add(computerBadMessages);
                            }
                            //Кнопка для Перехода На другой Activity:
                            if (parserNumber == 7){
                                ButtonLevelLeadershipMessages buttonLevelLeadershipMessages = new ButtonLevelLeadershipMessages();
                                buttonLevelLeadershipMessages.buttonLevelLeaderShip = xpp.getText();
                                messages.add(buttonLevelLeadershipMessages);
                            }
                            //Кнопка для Перехода На другой Activity:
                            if(parserNumber == 8){
                                ButtonOutput buttonOutput = new ButtonOutput();
                                buttonOutput.buttonOutput = xpp.getText();
                                messages.add(buttonOutput);
                            }
                            //Слова Автора:
                            if(parserNumber == 9){
                                WriterMessages writerMessages = new WriterMessages();
                                writerMessages.textWriter = xpp.getText();
                                messages.add(writerMessages);
                            }
                            //Слова "Голоса"
                            if(parserNumber == 10){
                                SoundMessages soundMessages = new SoundMessages();
                                soundMessages.textSound = xpp.getText();
                                messages.add(soundMessages);
                            }
                            //Кнопка Для Перехода на Другой Activity:
                            if(parserNumber == 11){
                                ButtonChemistMessages buttonChemistMessages = new ButtonChemistMessages();
                                buttonChemistMessages.buttonChemist = xpp.getText();
                                messages.add(buttonChemistMessages);
                            }
                            //Кнопка Для "Хорошей Концовки":
                            if(parserNumber == 12){
                                ButtonGoodEndMessages buttonGoodEndMessages = new ButtonGoodEndMessages();
                                buttonGoodEndMessages.buttonGoodEnd = xpp.getText();
                                messages.add(buttonGoodEndMessages);
                            }
                            //Кнопка Для "Плохой Концовки":
                            if(parserNumber == 13){
                                ButtonBadEndMessages buttonBadEndMessages = new ButtonBadEndMessages();
                                buttonBadEndMessages.buttonBadEnd = xpp.getText();
                                messages.add(buttonBadEndMessages);
                            }
                            //Кнопка "Правильного" Выбора:
                            if(parserNumber == 14){
                                ButtonGoodChooseMessages buttonGoodChooseMessages = new ButtonGoodChooseMessages();
                                buttonGoodChooseMessages.buttonGoodChoose = xpp.getText();
                                messages.add(buttonGoodChooseMessages);
                            }
                            //Кнопка "Не Правильного" Выбора:
                            if(parserNumber == 15){
                                ButtonBadChooseMessages buttonBadChooseMessages = new ButtonBadChooseMessages();
                                buttonBadChooseMessages.buttonBadChoose = xpp.getText();
                                messages.add(buttonBadChooseMessages);
                            }
                            break;
                        default:
                            break;
                    }
                    // Следующий элемент:
                    xpp.next();
                }
                xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}

