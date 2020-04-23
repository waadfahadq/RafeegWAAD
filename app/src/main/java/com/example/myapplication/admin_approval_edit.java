package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class admin_approval_edit extends AppCompatActivity {

    View view;
    String nameOfShop;
    String old ;
    String nameOfAvertisment;
    String bId;
    String uid;
    String id ;
    String dayOfAD ;
    String dateOfAD ;
    String UserName ;
    String dis ;
    TextView nameOFShop ;
    TextView nameOfAver;
    TextView adverDes ;
    TextView date;
    TextView dayOfWeek;
    Button delete;
    Button back ;
    String dd ;
    String AvertismentName ;
    private sharedInformation AvertismentInfo;

    private  DatabaseReference myRef2;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = firebaseDatabase.getReference("ApprovalAD");
    DatabaseReference ref2  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref3  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("Advertisment update Information");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_approval_edit);

        nameOFShop = findViewById (R.id.nameOfShop);
        nameOfAver = findViewById (R.id.nameOfAdver);
        adverDes = findViewById (R.id.DesOfAdver);
        date = findViewById (R.id.date);
        dayOfWeek = findViewById (R.id.time);
        delete = findViewById (R.id.button4);
        back = findViewById (R.id.back_btn);
        nameOfShop = getIntent ().getStringExtra ("nameOfShop");
        nameOfAvertisment = getIntent ().getStringExtra ("name");
        bId = getIntent ().getStringExtra ("BID");
        AvertismentInfo = new sharedInformation (this);


        databaseReference1.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                    advertismentInfo advertismentInfo = snapshot.getValue (advertismentInfo.class);
                     AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                    if (nameOfAvertisment.equals (AvertismentName)) {
                        nameOfAver.setText (nameOfAvertisment);
                        AvertismentInfo.setKeyConName (nameOfAvertisment);
                        nameOFShop.setText (nameOfShop);
                        adverDes.setText (advertismentInfo.getDescription ());
                        dis = advertismentInfo.getDescription ();
                        date.setText (advertismentInfo.getDate ());
                        dateOfAD = advertismentInfo.getDate ();
                        dayOfAD = advertismentInfo.getDayOfWeek ();
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
                Intent intent = new Intent(admin_approval_edit.this, ForApproval.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (admin_approval_edit.this);
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من تعديل الاعلان؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface arg0, int arg1) {

                                update();


                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (admin_approval_edit.this);
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage ("تم تعديل الاعلان بنجاح ");
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show ();

                                new Timer ().schedule(new TimerTask () {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(admin_approval_edit.this, for_approval_edit.class));
                                    }
                                },1500);
                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show ();

            }
        });

    }

    void update(){

        DatabaseReference databaseReference12 = firebaseDatabase.getReference("shipowners").child(uid).child("ApprovalAD");

        databaseReference12.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                    AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                    if (singleAdvertisementInfo.oldName.equals (AvertismentName)){

                        ref3 = FirebaseDatabase.getInstance().getReference("shipowners").child(uid).child("ApprovalAD").child (snapshot.getKey());
                        //Update profile
                        HashMap<String , Object> map = new HashMap <>();
                        map.put("nameOfAdvertisment", nameOfAvertisment);
                        map.put("description",dis);
                        map.put("dayOfWeek", dayOfAD);
                        map.put("date",dateOfAD);
                        map.put("id", uid);
                        map.put("shopName",nameOfShop);
                        map.put("username", UserName);
                        ref3.updateChildren(map);

                        //userdrawer.setText(userName.getText().toString());
                        // emaildrawer.setText(email.getText().toString());
                        DatabaseReference retreff = firebaseDatabase.getReference ("ApprovalAD").child (snapshot.getKey());

                        ref3 = FirebaseDatabase.getInstance().getReference("ApprovalAD").child (snapshot.getKey());
                        //Update profile
                        HashMap<String , Object> map1 = new HashMap <>();
                        map1.put("nameOfAdvertisment", nameOfAvertisment);
                        map1.put("description",dis);
                        map1.put("dayOfWeek", dayOfAD);
                        map1.put("date",dateOfAD);
                        map1.put("id", uid);
                        map1.put("shopName",nameOfShop);
                        map1.put("username", UserName);
                        ref3.updateChildren(map1);


                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference1.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                    advertismentInfo advertismentInfo = snapshot.getValue (advertismentInfo.class);
                    String AvertismentName = advertismentInfo.getNameOfAdvertisment ();

                    if (nameOfAvertisment.equals (AvertismentName)) {

                        DatabaseReference retreff2 = firebaseDatabase.getReference ("Advertisment update Information").child (snapshot.getKey());
                        retreff2.removeValue ();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
