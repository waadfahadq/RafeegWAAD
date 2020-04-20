package com.example.myapplication.shopowner.ui.Advertisement;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.advertisement_list;
import com.example.myapplication.advertismentInfo;
import com.example.myapplication.shopowner.MainActivityShopowner;
import com.example.myapplication.shopowner.ui.info.InfoViewModel;
import com.example.myapplication.shopowner.ui.profile.ProfileViewModel;
import com.example.myapplication.ui.dashboard.ForgotPasswordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class advertisementListBack extends Fragment {

    private AdvertisementListBackViewModel advertisementListBack;
    private static final String TAG = "Activity";
    private ArrayList<String> nId = new ArrayList<>();
    private ArrayList<String> nName = new ArrayList<>();
    FirebaseDatabase database1;
    DatabaseReference retreff ;
    String i;

    String nameOfAdver ;
    String desOfAdver ;
    String id ;
    String UserName;
    String date ;
    String dayOfWeek ;
    String shopName ;
    EditText nameOfAdvertisement;
    EditText AdDescription;
    Button cancel;
    Button save;
    String uid;
    boolean check = false;
    public static boolean WaiteForApprovoal = false ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;
    String userid = user.getUid();
    //Datebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Advertisment Information");
    private FirebaseAuth f1 = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shipowners");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        advertisementListBack =
                ViewModelProviders.of(this).get(AdvertisementListBackViewModel.class);
        View root = inflater.inflate(R.layout.advertisement_list_back_fragment, container, false);

        nameOfAdvertisement = root.findViewById (R.id.avertisementName);
        AdDescription = root.findViewById (R.id.editText2);
        cancel = root.findViewById (R.id.cancel);
        save = root.findViewById (R.id.save);

        // save

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tocheckDataEntered()){

                    check = true;

                }

                if (check == true){

                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
                    myAlertDialog.setTitle("الاعلانات ");
                    myAlertDialog.setMessage("هل أنت متأكد من إضافة الاعلان؟");
                    myAlertDialog.setPositiveButton("نعم",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    nameOfAdvertisement.getText ().clear ();
                                    AdDescription.getText ().clear ();

                                    uid = f1.getCurrentUser().getUid();
                                    id = myRef.push().getKey();

                                    advertismentInfo  advertismentInfo = new advertismentInfo (uid,UserName,nameOfAdver,desOfAdver, date, dayOfWeek,shopName);

                                    myRef.child(id).setValue(advertismentInfo);


                                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
                                    myAlertDialog.setTitle("الاعلانات ");
                                    myAlertDialog.setMessage("تم رفع الاعلان بنجاح، الرجاء انتظار موافقه الادارة " );
                                    myAlertDialog.setNeutralButton ("موافق", null);
                                    WaiteForApprovoal = true ;
                                    myAlertDialog.show();

                                }
                            });
                    myAlertDialog.setNegativeButton ("إلغاء", null);
                    myAlertDialog.show();



                }
                else
                    Toast.makeText(getActivity(),"العملية خاطئة حاول مرة أخرى!",Toast.LENGTH_LONG).show();


            }



        });

        // cancel

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
                myAlertDialog.setTitle("الاعلانات ");
                myAlertDialog.setMessage("هل أنت متأكد من إنهاء عملية إضافة الاعلان؟");
                myAlertDialog.setPositiveButton("نعم",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                nameOfAdvertisement.getText ().clear ();
                                AdDescription.getText ().clear ();

                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity ());
                                myAlertDialog.setTitle("الاعلانات ");
                                myAlertDialog.setMessage("تم إلغاء رفع الاعلان بنجاح " );
                                myAlertDialog.setNeutralButton ("موافق", null);

                                myAlertDialog.show();

                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show();

            }
        });


        Ename();
        return root;
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean tocheckDataEntered (){


        id = "nall";
        nameOfAdver  = nameOfAdvertisement.getText().toString().trim();
        desOfAdver = AdDescription.getText().toString().trim();
        UserName = "nall";
        shopName = "nall";

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserName = dataSnapshot.child("email").getValue().toString ();
                shopName = dataSnapshot.child("name").getValue().toString ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // to get current day and date
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat simpleDateFormat ;

        // time
        //  simpleDateFormat  = new SimpleDateFormat ("hh:mm:ss");
        //  time = simpleDateFormat.format (calendar.getTime ());
        // date

        simpleDateFormat = new SimpleDateFormat ("dd-MM-yyyy");
        date = simpleDateFormat.format (calendar.getTime ());

        Calendar calendar1 = Calendar.getInstance();
        int day = calendar1.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayOfWeek = "SUNDAY" ;
                break;
            case Calendar.MONDAY:
                dayOfWeek = "MONDAY" ;
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "TUESDAY" ;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "WEDNESDAY" ;
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "THURSDAY" ;
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "FRIDAY" ;
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "SATURDAY" ;
                break;
        }

        // check name of bill
        if (isEmpty(nameOfAdvertisement)){
            nameOfAdvertisement.setError("يجب ادخال اسم للاعلان");
            return false;
        }
        // check name of bill
        if (isEmpty(AdDescription)){

            AdDescription.setError("يجب ادخال وصف للاعلان");
            return false;
        }
        // name + image true



        return true;
    }

    private void Ename() {

        database1= FirebaseDatabase.getInstance();
        retreff=database1.getReference("Advertisment Information");
        retreff.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    advertismentInfo Nm = snapshot.getValue(advertismentInfo.class) ;

                    String userId=snapshot.getKey().toString();
                    String name = Nm.getNameOfAdvertisment ();
                    nName.add(name);
                    nId.add(userId);
                    inRecycle ();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inRecycle (){

        RecyclerView recyclerView= (RecyclerView) getView().findViewById(R.id.recycler_view);
        advertisement_list myr = new advertisement_list(nName,nId,getContext ());
        recyclerView.setAdapter(myr);
        recyclerView.setLayoutManager(new LinearLayoutManager (getContext ()));
    }


}