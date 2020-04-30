package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Timer;
import java.util.TimerTask;

public class Admin_Approval_Delete extends AppCompatActivity {

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
    Button delete;
    Button DoNotDelete ;
    private sharedInformation AvertismentInfo;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = firebaseDatabase.getReference("ApprovalAD");
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("Delete Advertisment");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin__approval_delete);
        nameOFShop = findViewById (R.id.nameOfShop);
        nameOfAver = findViewById (R.id.nameOfAdver);
        adverDes = findViewById (R.id.DesOfAdver);
        date = findViewById (R.id.date);
        dayOfWeek = findViewById (R.id.time);
        delete = findViewById (R.id.button3);
        DoNotDelete = findViewById (R.id.button4);
        nameOfShop = getIntent ().getStringExtra ("nameOfShop");
        nameOfAvertisment = getIntent ().getStringExtra ("name");
        bId = getIntent ().getStringExtra ("BID");
        AvertismentInfo = new sharedInformation (this);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("طلبات حذف الإعلانات");
        toolbar.setTitleTextColor(Color.BLACK);
        databaseReference1.addValueEventListener (new ValueEventListener () {
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
        DoNotDelete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Delete.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من رفض حذف الاعلان؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface arg0, int arg1) {
                                singleAdvertisementInfo.deleteAdv = false;
                                singleAdvertisementInfo.editAdv = false;
                                singleAdvertisementInfo.canDelete = true;
                                databaseReference1.addValueEventListener(new ValueEventListener () {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                                            String AvertismentName = advertismentInfo.getNameOfAdvertisment ();
                                            if (nameOfAvertisment.equals (AvertismentName)) {
                                                DatabaseReference retreff2 = firebaseDatabase.getReference ("Delete Advertisment").child (snapshot.getKey());
                                                retreff2.removeValue ();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show ();


            }
        });

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Delete.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من حذف الاعلان؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface arg0, int arg1) {


                                singleAdvertisementInfo.deleteAdv = false;
                                singleAdvertisementInfo.editAdv = false;
                                singleAdvertisementInfo.canDelete = true;
                                myRef.addValueEventListener(new ValueEventListener () {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                                            String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                                            if (nameOfAvertisment.equals (AvertismentName)) {
                                                DatabaseReference retreff = firebaseDatabase.getReference ("ApprovalAD").child (snapshot.getKey());
                                                retreff.removeValue ();
                                                DatabaseReference retreff1 = firebaseDatabase.getReference ("shipowners").child (uid).child (("ApprovalAD")).child (snapshot.getKey());
                                                retreff1.removeValue ();
                                                DatabaseReference retreff2 = firebaseDatabase.getReference ("Delete Advertisment").child (snapshot.getKey());
                                                retreff2.removeValue ();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                                databaseReference1.addValueEventListener(new ValueEventListener () {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                                            String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                                            if (nameOfAvertisment.equals (AvertismentName)) {
                                                DatabaseReference retreff2 = firebaseDatabase.getReference ("Delete Advertisment").child (snapshot.getKey());
                                                retreff2.removeValue ();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });


                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (Admin_Approval_Delete.this);
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage ("تم حذف الاعلان بنجاح ");
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show ();

                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show ();

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();

            // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
