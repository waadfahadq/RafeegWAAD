package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Admin_Approval_Add extends AppCompatActivity {

    View view;
    String nameOfShop;
    String nameOfAvertisment;
    String bId;
    String uid;
    String id ;
    String UserName ;
    TextView nameOFShop ;
    TextView nameOfAver;
    TextView adverDes ;
    TextView date;
    TextView dayOfWeek;
    Button Add ;
    Button delete;
    Button back ;
    boolean deleteAd = false ;
    private sharedInformation AvertismentInfo;
    public static boolean canDeleteAndEdit = false ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = firebaseDatabase.getReference("Advertisment Information");
    private final DatabaseReference ApprovalAD = firebaseDatabase.getReference("ApprovalAD");
    private  DatabaseReference myRef1;
    DatabaseReference databaseReference = firebaseDatabase.getReference("Advertisment Information");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin__approval);

        nameOFShop = findViewById (R.id.nameOfShop);
        nameOfAver = findViewById (R.id.nameOfAdver);
        adverDes = findViewById (R.id.DesOfAdver);
        date = findViewById (R.id.date);
        dayOfWeek = findViewById (R.id.time);
        Add = findViewById (R.id.button3);
        delete = findViewById (R.id.button4);
        back = findViewById (R.id.back_btn);

        nameOfShop = getIntent ().getStringExtra ("nameOfShop");
        nameOfAvertisment = getIntent ().getStringExtra ("name");
        bId = getIntent ().getStringExtra ("BID");
        AvertismentInfo = new sharedInformation (this);


        databaseReference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                    advertismentInfo advertismentInfo = snapshot.getValue (advertismentInfo.class);
                    String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                    if (nameOfAvertisment.equals (AvertismentName)) {
                        nameOfAver.setText (nameOfAvertisment);
                        AvertismentInfo.setKeyConName (nameOfAvertisment);
                        nameOFShop.setText (nameOfShop);
                        adverDes.setText (advertismentInfo.getDescription ());
                        date.setText (advertismentInfo.getDate ());
                        dayOfWeek.setText (advertismentInfo.getDayOfWeek ());
                        uid = advertismentInfo.getId ();
                        UserName = advertismentInfo.getUsername ();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Approval_Add.this, ForApproval.class);
                startActivity(intent);
                finish();
            }
        });

        Add.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Add.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من قبول الاعلان؟");
                myAlertDialog.setPositiveButton("نعم",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                id = myRef.push().getKey();
                                String nameOfAD = nameOfAver.getText ().toString ().trim ();
                                String DesOfAD = adverDes.getText ().toString ().trim ();
                                String dateOgAD = date.getText ().toString ().trim ();
                                String dayOfAD = dayOfWeek.getText ().toString ().trim ();
                                String nameOfShop = nameOFShop.getText ().toString ().trim ();
                                myRef1 = firebaseDatabase.getReference("shipowners").child(uid).child("ApprovalAD");
                                advertismentInfo  advertismentInfo = new advertismentInfo (uid,UserName,nameOfAD,DesOfAD, dateOgAD, dayOfAD,nameOfShop);
                                myRef1.child(id).setValue(advertismentInfo);
                                ApprovalAD.child(id).setValue(advertismentInfo);
                                deleteAd = true ;
                                canDeleteAndEdit = true;
                                if(deleteAd == true){

                                    myRef.addValueEventListener(new ValueEventListener () {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                                                String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                                                if (nameOfAvertisment.equals (AvertismentName)) {
                                                    DatabaseReference retreff = firebaseDatabase.getReference ("Advertisment Information").child (snapshot.getKey());
                                                    retreff.removeValue ();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                }



                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Add.this);
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage("تم قبول الاعلان بنجاح " );
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show();

                                new Timer ().schedule(new TimerTask () {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Admin_Approval_Add.this, ForApproval.class));
                                    }
                                },1500);
                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show();



            }
        });

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Add.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من رفض الاعلان؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface arg0, int arg1) {
                                myRef.addValueEventListener(new ValueEventListener () {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                                            String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                                            if (nameOfAvertisment.equals (AvertismentName)) {
                                                DatabaseReference retreff = firebaseDatabase.getReference ("Advertisment Information").child (snapshot.getKey());
                                                retreff.removeValue ();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Add.this);
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage ("تم رفض الاعلان بنجاح ");
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show ();

                                new Timer ().schedule(new TimerTask () {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Admin_Approval_Add.this, ForApproval.class));
                                    }
                                },1500);
                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show ();

            }
        });

    }
}
