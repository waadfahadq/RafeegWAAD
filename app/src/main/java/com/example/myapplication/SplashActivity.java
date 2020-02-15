package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{



    LinearLayout splashLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);



        splashLayout = (LinearLayout) findViewById(R.id.splashLL);
        splashLayout.setOnClickListener(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        },4000);


    }//end onCreate


    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}//end class

