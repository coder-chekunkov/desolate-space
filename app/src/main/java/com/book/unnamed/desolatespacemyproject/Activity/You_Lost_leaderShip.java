package com.book.unnamed.desolatespacemyproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.book.unnamed.desolatespacemyproject.R;

/**
 * Created by unnamed on 09.04.2017.
 */

public class You_Lost_leaderShip extends MenuActivity{

    Button button_try_again2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_leadership);

        button_try_again2 = (Button) findViewById(R.id.button_try_again2);
        button_try_again2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_try_again2:
                Intent try_again = new Intent(this, LevelLeadership.class);
                startActivity(try_again);
                break;
        }
    }




    @Override
    public void onBackPressed(){
        Intent BackPressed = new Intent(this, MenuActivity.class);
        startActivity(BackPressed);
    }

}

