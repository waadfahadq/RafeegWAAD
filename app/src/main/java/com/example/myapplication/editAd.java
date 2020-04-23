package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class editAd extends AppCompatActivity {

    String idOfgAdv ;
    String name ;
    String dis;
    String date ;
    String day;
    String UserName;
    String shopName ;
    EditText nameOfAD;
    EditText disOfAD;
    TextView dateOfAD;
    TextView timeOfAD;
    Button button4;
    boolean edit  = false ;

    String newName ;
    String newDis ;

    DatabaseReference ref2  = FirebaseDatabase.getInstance().getReference();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Advertisment update Information");

    private FirebaseAuth f1 = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_ad);

        nameOfAD = findViewById (R.id.nameOfAdver);
        disOfAD = findViewById (R.id.DesOfAdver);
        dateOfAD = findViewById (R.id.date);
        timeOfAD = findViewById (R.id.time);
        button4 = findViewById (R.id.button4);

        idOfgAdv = getIntent().getStringExtra("idOfgAdv");
        name = getIntent().getStringExtra("nameOfAd");


        dis = getIntent().getStringExtra("description");
        date = getIntent().getStringExtra("date");
        day = getIntent().getStringExtra("time");
        UserName = getIntent().getStringExtra("UserName");
        shopName = getIntent().getStringExtra("shopName");

        nameOfAD.setText (name);
        disOfAD.setText (dis);
        dateOfAD.setText (date);
        timeOfAD.setText (day);


        newName = nameOfAD.getText ().toString ().trim();
        newDis = disOfAD.getText ().toString ().trim();


        button4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(editAd.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من تعديل الاعلان؟");
                myAlertDialog.setPositiveButton("نعم",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                singleAdvertisementInfo.editAdv = true ;
                                singleAdvertisementInfo.canEdit = true ;

                                update();
                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(editAd.this);
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage("تم تعديل الاعلان بنجاح، الرجاء انتظار موافقه الادارة " );
                                myAlertDialog.setNeutralButton ("موافق", null);
                                edit = true;
                               // update();

                                myAlertDialog.show();



                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show();



                new Timer ().schedule(new TimerTask () {
                    @Override
                    public void run() {
                        finish ();
                    }
                },1500);
            }
        });


    }


    void update(){

        String id = myRef.push().getKey();

        ref2 = FirebaseDatabase.getInstance().getReference("Advertisment update Information").child (id);
        //Update profile
        HashMap<String , Object> map = new HashMap <>();
        map.put("nameOfAdvertisment", nameOfAD.getText().toString());
        map.put("description",disOfAD.getText().toString());
        map.put("dayOfWeek", day);
        map.put("date",date);
        map.put("id", idOfgAdv);
        map.put("shopName",shopName);
        map.put("username", UserName);
        ref2.updateChildren(map);
        //userdrawer.setText(userName.getText().toString());
        // emaildrawer.setText(email.getText().toString());


    }
}
