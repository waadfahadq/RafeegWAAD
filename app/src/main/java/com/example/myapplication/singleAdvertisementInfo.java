package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.shopowner.ui.Advertisement.advertisementListBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class singleAdvertisementInfo extends AppCompatActivity {

    View view;
    String nameOfAvertisment;
    String bId;
    TextView nameOfAver;
    TextView time;
    TextView date;
    TextView adverDes ;
    Button edit ;
    Button delete;
    String idOfgAdv ;
    String UserName;
    String shopName ;

    boolean deleteAdv = false;
    boolean editAdv = false;
    private sharedInformation AvertismentInfo;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Advertisment Information");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_advertisement_info);

        nameOfAver = findViewById(R.id.nameOfAdver);
        adverDes = findViewById (R.id.DesOfAdver);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        edit = findViewById (R.id.button4);
        delete = findViewById (R.id.button3);
        nameOfAvertisment = getIntent().getStringExtra("name");
        bId = getIntent().getStringExtra("BID");
        AvertismentInfo = new sharedInformation (this);


        databaseReference.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    advertismentInfo advertismentInfo = snapshot.getValue(advertismentInfo.class);
                    String AvertismentName = advertismentInfo.getNameOfBeacon ();

                    if (nameOfAvertisment.equals(AvertismentName)) {
                        nameOfAver.setText(nameOfAvertisment);
                        AvertismentInfo.setKeyConName(nameOfAvertisment);
                        adverDes.setText (advertismentInfo.getDescription ());
                        date.setText(advertismentInfo.getDate ());
                        time.setText(advertismentInfo.getDayOfWeek ());
                        idOfgAdv = advertismentInfo.getId ();
                        UserName = advertismentInfo.getUsername ();
                        shopName = advertismentInfo.getShopName ();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (advertisementListBack.WaiteForApprovoal == true) {

            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(singleAdvertisementInfo.this);
            myAlertDialog.setTitle("الاعلانات ");
            myAlertDialog.setMessage("لا يمكن تعديل  او حذف الاعلان حتى تتم الموافقة من الادارة  " );
            myAlertDialog.setNeutralButton ("موافق", null);

            myAlertDialog.show();

            edit.setVisibility (View.GONE);
            delete.setVisibility (View.GONE);
        }



        if (ForApproval.forAdd == true) {

            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(singleAdvertisementInfo.this);
            myAlertDialog.setTitle("الاعلانات ");
            myAlertDialog.setMessage("تمت الموافقة على الاعلان، يمكن تعديل أو حذف الاعلان" );
            myAlertDialog.setNeutralButton ("موافق", null);

            myAlertDialog.show();
        }

        edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if (deleteAdv == true) {

                    AlertDialog.Builder myAlertDialog1 = new AlertDialog.Builder (singleAdvertisementInfo.this);
                    myAlertDialog1.setTitle ("الاعلانات ");
                    myAlertDialog1.setMessage ("يوجد طلب مسبق لحذف الاعلان، لا يمكن الحذف أو التعديل الان  ");
                    myAlertDialog1.setNeutralButton ("موافق", null);

                    myAlertDialog1.show ();

                    edit.setVisibility (View.GONE);
                    delete.setVisibility (View.GONE);


                    editAdv = true;

                }

                if (editAdv == false) {

                    Intent intent = new Intent (singleAdvertisementInfo.this, editAd.class);

                    intent.putExtra ("nameOfAd", nameOfAvertisment);
                    String description = adverDes.getText ().toString ();
                    String dateOfAd = date.getText ().toString ();
                    String timeOfAd = time.getText ().toString ();
                    intent.putExtra ("description", description);
                    intent.putExtra ("date", dateOfAd);
                    intent.putExtra ("time", timeOfAd);
                    intent.putExtra ("idOfgAdv", idOfgAdv);
                    intent.putExtra ("UserName", UserName);
                    intent.putExtra ("shopName", shopName);


                    startActivity (intent);

                    finish ();
                }
            }
        });


        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if (editAdv == true) {

                    AlertDialog.Builder myAlertDialog1 = new AlertDialog.Builder (singleAdvertisementInfo.this);
                    myAlertDialog1.setTitle ("الاعلانات ");
                    myAlertDialog1.setMessage ("يوجد طلب مسبق لحذف الاعلان، لا يمكن الحذف أو التعديل الان  ");
                    myAlertDialog1.setNeutralButton ("موافق", null);

                    myAlertDialog1.show ();

                    edit.setVisibility (View.GONE);
                    delete.setVisibility (View.GONE);

                    deleteAdv = true;

                }

                if (deleteAdv == false) {
                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (singleAdvertisementInfo.this);
                    myAlertDialog.setTitle ("الاعلانات ");
                    myAlertDialog.setMessage ("هل أنت متأكد من حذف الاعلان؟");
                    myAlertDialog.setPositiveButton ("نعم",
                            new DialogInterface.OnClickListener () {
                                public void onClick(DialogInterface arg0, int arg1) {

                                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (singleAdvertisementInfo.this);
                                    myAlertDialog.setTitle ("الاعلانات ");
                                    myAlertDialog.setMessage ("تم رفع الطلب للادراة الرجاء الانتظار ");
                                    myAlertDialog.setNeutralButton ("موافق", null);
                                    myAlertDialog.show ();
                                    deleteAdv = true;

                                }
                            });
                    myAlertDialog.setNegativeButton ("إلغاء", null);
                    AlertDialog show = myAlertDialog.show ();

                    //   callIntent(deleteAdv);

                }

            }

        });

    }

    /*
    private void callIntent(boolean deleteAdv) {
        if (deleteAdv == true) {

            Intent intent = new Intent (this, advertisement_list_back.class);
            startActivity (intent);
        }
    }*/
}