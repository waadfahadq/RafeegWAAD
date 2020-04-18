package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class fullScreen extends AppCompatActivity {

    ImageView full ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_full_screen);

        full = findViewById (R.id.bill_image);

        Picasso.get().load(singleBillInfo.photo).into(full);
    }
}
