package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainSear;
import com.example.myapplication.Map;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.storeinfo;

public class StoreDeatilsActivity extends AppCompatActivity {
    private LinearLayout goBtn;
    private Toolbar toolbar;
    private TextView title;
    private TextView work_hours;
    private TextView place,rate;
    private TextView phone;
    private ImageView imageView;
    private com.example.myapplication.ui.home.storeinfo st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_deatils);
        toolbar=findViewById(R.id.toolbar);
        title=findViewById(R.id.title);
        work_hours=findViewById(R.id.work_hours);
        place=findViewById(R.id.place);
        goBtn = findViewById(R.id.go);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                //intent.putExtra("title" , secList.get(position).getName());
                startActivity(intent);
            }
        });
        imageView=findViewById(R.id.imageView);
        phone=findViewById(R.id.phone);
        //rate=findViewById(R.id.rating);
        setSupportActionBar(toolbar);
        work_hours=findViewById(R.id.work_hours);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        if(intent.hasExtra("store")){
            st=(storeinfo) intent.getSerializableExtra("store");
            if(st.getName()!=null){
                title.setText(st.getName());
            }else {
                title.setText("Store Info");
            }

            if(st.getImage()!=null){
                Glide.with(this).load(st.getImage()).into(imageView);
            }else {
                Glide.with(this).load(R.drawable.splash).into(imageView);
            }

            if(st.getPhone()!=null){
                phone.setText(st.getPhone());
            }else {
                phone.setText("000000");
            }

            if(st.getEmail()!=null){
                place.setText(st.getEmail());
            }else {
                place.setText("Not Floor");
            }

            if(st.getFrom()!=null&&st.getTo()!=null){
                work_hours.setText(st.getFrom()+" إلى "+st.getTo());
            }else {
                work_hours.setText("Not Floor");
            }
        }

    }

    public void showLoaction(View view) {
    }
}
