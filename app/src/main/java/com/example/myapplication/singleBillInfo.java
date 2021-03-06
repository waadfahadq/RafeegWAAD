package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.Timer;
import java.util.TimerTask;

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
    Button deleteBill ;
    Button back ;
    String id ;
    private sharedInformation userInfo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth f1 = FirebaseAuth.getInstance();
    String uid = f1.getCurrentUser().getUid();
    DatabaseReference retreff = database.getReference ("User").child (uid).child ("Bill Information");
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
        back = findViewById (R.id.back_btn);
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
                        timeOfbill.setText (Nm.getDayOfWeek ());
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
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(singleBillInfo.this, billingList_back.class);
                startActivity(intent);
                finish();
            }
        });

        deleteBill.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (singleBillInfo.this);
                myAlertDialog.setTitle ("الفواتير ");
                myAlertDialog.setMessage ("هل أنت متأكد من حذف الفاتورة؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface arg0, int arg1) {
                        retreff.addValueEventListener(new ValueEventListener () {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    billInfo Nm = snapshot.getValue(billInfo.class);
                                    final String name = Nm.getNameOfBill ();
                                    if (nameOfBill.equals (name)) {
                                        DatabaseReference retreff = database.getReference ("User").child (uid).child ("Bill Information").child (snapshot.getKey());
                                        retreff.removeValue ();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (singleBillInfo.this);
                                myAlertDialog.setTitle ("الفواتير ");
                                myAlertDialog.setMessage ("تم حذف الفاتورة بنجاح ");
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show ();

                        new Timer ().schedule(new TimerTask () {
                            @Override
                            public void run() {
                                startActivity(new Intent(singleBillInfo.this, billingList_back.class));
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