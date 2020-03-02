package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.ui.home.storeinfo;

public class DetailsActivity extends AppCompatActivity {

    TextView name,number,email,type,baken;
    ImageView image;
    Button delete;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        email=findViewById(R.id.email);
        type=findViewById(R.id.type);
        baken=findViewById(R.id.baken);
        image=findViewById(R.id.image);


        Intent intent=getIntent();
        if(intent.hasExtra("store")){
            storeinfo storeinfo= (com.example.myapplication.ui.home.storeinfo) intent.getSerializableExtra("store");
            name.setText("name : "+"\n"+storeinfo.getName());
            try {
                number.setText("number :"+"\n"+String.valueOf(storeinfo.getNum()));
            }catch (Exception e){
                number.setText("number :"+"\n"+String.valueOf(storeinfo.getNum()));
            }

            email.setText("email :"+"\n"+storeinfo.getEmail());
            type.setText("type :  "+"\n"+storeinfo.getTypeStore());
            baken.setText("baken ID :  "+"\n"+storeinfo.getBeaconID());
            key=intent.getStringExtra("key");
            Glide.with(this).load(storeinfo.getImage()).into(image);
        }




    }

}
