package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class detail extends AppCompatActivity {
    TextView textTitle;
    TextView textDescription;
    ImageView imagedet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String descr = i.getStringExtra("descr");
        String image=i.getStringExtra("image");
        textTitle = findViewById(R.id.detailTitle);
        textDescription=findViewById(R.id.detailDescr);
        imagedet=findViewById(R.id.imageView2de);
        textTitle.setText(title);
        textDescription.setText(descr);
        //imagedet.setImageURI(Uri.parse(image));
    }
}
