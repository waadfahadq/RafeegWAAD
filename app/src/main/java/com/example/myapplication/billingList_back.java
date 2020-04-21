package com.example.myapplication;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.shopowner.ui.profile.profileFragment;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.dashboard.account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class billingList_back extends AppCompatActivity {

    private static final String TAG = "Activity";
    private ArrayList<String> nId = new ArrayList<>();
    private ArrayList<String> nName = new ArrayList<>();
    private ArrayList<String> Image = new ArrayList<>();
    FirebaseDatabase database1;
    DatabaseReference retreff ;
    String nameOfBills ;
    String id ;
    String idu ;
    String UserName;
    String date ;
    EditText nameOFBill;
    Button cancel;
    Button save;
    Button back ;
    ImageView billImage ;
    Uri billImageUri ;
    String uid;
    boolean haveBill = false ;
    String dayOfWeek ;
    //Datebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Bill Information");
    private  DatabaseReference myRef1;
    StorageReference storage = FirebaseStorage.getInstance().getReference ();
    private FirebaseAuth f1 = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseUser user ;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_billing_list_back);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        billImage = findViewById (R.id.bill_image);
        nameOFBill = findViewById (R.id.billName);
        cancel = findViewById (R.id.cancel);
        save = findViewById (R.id.save);
        back = findViewById (R.id.back_btn);

        // Choose Image
        billImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                billImage.setBackgroundColor (80000000);
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(billingList_back.this);
                myAlertDialog.setTitle("اختر صورة ");
                myAlertDialog.setMessage("اختر صورة من الكاميراأو المكتبة الصور  ");
                myAlertDialog.setPositiveButton(getResources().getString(R.string.gallery),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);
                            }
                        });
                myAlertDialog.setNegativeButton (getResources().getString(R.string.camera),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 100);//zero can be replaced with any action code
                            }
                        });
                myAlertDialog.setNeutralButton ("إلغاء", null);
                myAlertDialog.show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish ();
            }
        });


        // Save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tocheckDataEntered()){ }
            }
        });

        // Cancel
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ( billImageUri != null && nameOFBill != null){
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (billingList_back.this);
                myAlertDialog.setTitle ("الفواتير ");
                myAlertDialog.setMessage ("هل أنت متأكد من إنهاء عملية إضافة الفاتورة؟");
                myAlertDialog.setPositiveButton ("نعم",
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface arg0, int arg1) {
                                billImage.setImageResource (android.R.color.transparent);
                                nameOFBill.getText ().clear ();
                                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder (billingList_back.this);
                                myAlertDialog.setTitle ("الفواتير ");
                                myAlertDialog.setMessage ("تم إلغاء رفع الفاتورة بنجاح ");
                                myAlertDialog.setNeutralButton ("موافق", null);
                                myAlertDialog.show ();
                            }
                        });
                myAlertDialog.setNegativeButton ("إلغاء", null);
                myAlertDialog.show ();

            }
            }
        });

        Bname();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean tocheckDataEntered (){

        nameOfBills  = nameOFBill.getText().toString().trim();
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserName = dataSnapshot.child("email").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // to get current time and date
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat simpleDateFormat ;

        int day = calendar.get(Calendar.DAY_OF_WEEK);

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

        // date
        simpleDateFormat = new SimpleDateFormat ("dd-MM-yyyy");
        date = simpleDateFormat.format (calendar.getTime ());

        // check name of bill
        if (isEmpty(nameOFBill)){
            nameOFBill.setError("يجب ادخال اسم للفاتورة");
            return false;
        }

        // check choose image
        if ( billImageUri != null && nameOFBill != null){
            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(billingList_back.this);
            myAlertDialog.setTitle("الفواتير ");
            myAlertDialog.setMessage("هل أنت متأكد من إضافة الفاتورة؟");
            myAlertDialog.setPositiveButton("نعم",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            billImage.setImageResource(android.R.color.transparent);
                            nameOFBill.getText ().clear ();

                            final StorageReference storageRef = storage.child ("Bill Image").child (  System.currentTimeMillis () + "."+ getFileExtension (billImageUri));
                            storageRef.putFile (billImageUri).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri> () {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            haveBill = true ;
                                            Uri downloadUrl = uri;
                                            uid = f1.getCurrentUser().getUid();
                                            id = myRef.push().getKey();
                                            myRef1 = database.getReference("User").child(uid).child("Bill Information");
                                            billInfo  infoOfBill = new billInfo(uid,UserName,downloadUrl.toString (), nameOfBills,dayOfWeek, date);
                                            myRef1.child(id).setValue(infoOfBill);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener () {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(billingList_back.this,"العملية خاطئة حاول مرة أخرى!",Toast.LENGTH_LONG).show();
                                }
                            });
                            AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(billingList_back.this);
                            myAlertDialog.setTitle("الفواتير ");
                            myAlertDialog.setMessage("تم رفع الفاتورة بنجاح " );
                            myAlertDialog.setNeutralButton ("موافق", null);
                            myAlertDialog.show();

                            billImage.setImageResource(android.R.color.transparent);
                            nameOFBill.getText ().clear ();
                        }
                    });
            myAlertDialog.setNegativeButton ("إلغاء", null);
            myAlertDialog.show();
        }
        else {
            Toast.makeText(billingList_back.this,"الرجاء اختيار صورة!",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private String getFileExtension (Uri uri){
       ContentResolver cR = getContentResolver ();
       MimeTypeMap mime = MimeTypeMap.getSingleton ();
       return mime.getExtensionFromMimeType (cR.getType (uri));
   }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ||  requestCode ==  100 && resultCode == RESULT_OK && data != null  && data.getData ()!= null){
            billImageUri=data.getData();
            billImage.setImageURI(billImageUri);
        }
    }

    private void Bname() {


        if (haveBill == false) {
            database1 = FirebaseDatabase.getInstance ();
            uid = f1.getCurrentUser().getUid();
            retreff = database1.getReference ("User").child (uid).child ("Bill Information");
            retreff.addValueEventListener (new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                        nName.clear(); //Clear your array list before adding new data in it
                        Image.clear(); //Clear your array list before adding new data in it
                    }
                    for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                        billInfo Nm = snapshot.getValue (billInfo.class);
                        String billId = snapshot.getKey ().toString ();
                        String name = Nm.getNameOfBill ();
                        String url = Nm.getUrl ();
                        idu = Nm.getId ();
                        nName.add (name);
                        Image.add (url);
                        nId.add (billId);
                        inRecycle ();

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void inRecycle (){
        RecyclerView recyclerView= findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        recyclerView.setHasFixedSize(true);
        billingList myr = new billingList(nName,Image,nId,this);
        recyclerView.setAdapter(myr);

        LinearLayoutManager layoutManager=
                new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

    }


}