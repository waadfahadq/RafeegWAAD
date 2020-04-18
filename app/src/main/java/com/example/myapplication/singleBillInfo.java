package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class singleBillInfo extends AppCompatActivity {

    static String photo = null;
    View view;
    String nameOfBill;
    String UserName;
    String bId;
    TextView NameOfBill;
    TextView dateOfbill;
    TextView timeOfbill;
    ImageView ImageOfBill;
    private String fullScreenInd;
    Button deleteBill ;
    static boolean deleteFromList = false;
    String id ;

    private sharedInformation userInfo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference retreff = database.getReference("Bill Information");
    String key = retreff.child("Bill Information").push().getKey();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_billing_info);

        NameOfBill = findViewById(R.id.nameOFBill);
        dateOfbill = findViewById(R.id.dateOfbill);
        timeOfbill = findViewById(R.id.timeOfbill);
        ImageOfBill = findViewById (R.id.bill_image);
        deleteBill = findViewById (R.id.button3);


        nameOfBill = getIntent().getStringExtra("name");
        bId = getIntent().getStringExtra("bId");
        userInfo = new sharedInformation (this);


        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                UserName = dataSnapshot.child("email").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        retreff.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    billInfo Nm = snapshot.getValue(billInfo.class);
                    final String name = Nm.getNameOfBill ();


                        if (nameOfBill.equals (name)) {
                            NameOfBill.setText (nameOfBill);
                            userInfo.setKeyConName (nameOfBill);
                            dateOfbill.setText (Nm.getDate ());
                            timeOfbill.setText (Nm.getTime ());
                            ImageOfBill.setBackgroundColor (80000000);
                            Picasso.get ().load (Nm.getUrl ()).into (ImageOfBill);
                            photo = Nm.getUrl ();

                        }
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ImageOfBill.setOnClickListener (new View.OnClickListener () {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(singleBillInfo.this, fullScreen.class);
        startActivity(intent);

        finish();
    }
        });

        deleteBill.setOnClickListener (new View.OnClickListener () {
                                           @Override
                                           public void onClick(View view) {

                                               retreff.addValueEventListener(new ValueEventListener () {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                           billInfo Nm = snapshot.getValue(billInfo.class);
                                                           final String name = Nm.getNameOfBill ();
                                                           if (nameOfBill.equals (name)) {

                                                               DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference ("Bill Information").child ( snapshot.getKey());
                                                               databaseRef.removeValue ();


                                                               // deleteFromList = true;
                                                               Intent intent = new Intent(singleBillInfo.this, billingList_back.class);
                                                               startActivity(intent);

                                                               finish();

                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                   }
                                               });
                                           }
                                       });

    }

    }
