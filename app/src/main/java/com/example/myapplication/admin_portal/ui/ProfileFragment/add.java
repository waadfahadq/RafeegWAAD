package com.example.myapplication.admin_portal.ui.ProfileFragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.storeinfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class add extends AppCompatActivity {

    TextView write;
    Button enter;
    DatabaseReference ref;
    ImageView image;
    Uri uri;
    EditText nameform,baken,type;
    Spinner num,num2,emailSp;
    ArrayAdapter<String> adapter,adapter2;
    RadioGroup  place_type;
    DatabaseReference requests;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar900);
        place_type = findViewById(R.id.place_type);
        setSupportActionBar(toolbar);
        image=findViewById(R.id.image);
        nameform=findViewById(R.id.nameform);
        num=findViewById(R.id.num2);
        num2=findViewById(R.id.num);
        emailSp=findViewById(R.id.Emailnum);
        String [] array=getResources().getStringArray(R.array.numbers);
        //new
        final List<String> arrayOfEmails = new ArrayList<String>();
        adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayOfEmails);
        adapter2.add("لم يتم إختيار متجر");
        requests = FirebaseDatabase.getInstance().getReference().child("requests");
        requests.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String email = dataSnapshot.child("email").getValue(String.class);
                adapter2.add(email);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        num.setAdapter(adapter);
        num2.setAdapter(adapter);
        emailSp.setAdapter(adapter2);

//        email=findViewById(R.id.email);
        baken=findViewById(R.id.baken);
        // type=findViewById(R.id.type);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(add.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                openImage();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                                Toast.makeText(add.this, "Permmission not granted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        })
                        .check();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("إضافة محل");
        // toolbar.setBackgroundColor(Color.GRAY);

        //toolbar.setTitleTextColor(Color.WHITE);

        // write=(TextView)findViewById(R.id.textView);
        enter=findViewById(R.id.reema);



        ref= FirebaseDatabase.getInstance().getReference().child("storeinfo");

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=nameform.getText().toString().trim();
                //String number=num.getText().toString().trim();
//                final String ownerEmail=email.getText().toString().trim();
                final String baken_number=baken.getText().toString().trim();
                // final String place_type=type.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(add.this, "ادخل اسم المحل", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(TextUtils.isEmpty(number)){
                    Toast.makeText(add.this, "Please Enter number", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(num.getSelectedItemPosition()==0){
                    Toast.makeText(add.this, "اختر رقم المحل", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(num2.getSelectedItemPosition()==0){
                    Toast.makeText(add.this, "اختر رقم الدور", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(emailSp.getSelectedItemPosition()==0){
                    Toast.makeText(add.this, "اختر إيميل المالك", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(baken_number)){
                    Toast.makeText(add.this, "ادخل رقم البيكن", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(TextUtils.isEmpty(place_type)){
                    Toast.makeText(add.this, "Please Enter place type", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(uri ==null){
                    Toast.makeText(add.this, "ادخل صورة المحل", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase.getInstance()
                        .getReference().
                        child("storeinfo").orderByChild("num")
                        .equalTo(Integer.parseInt((String) num.getSelectedItem())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(add.this, "قد تم اختيار هذا الرقم لمحل آخر", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            try {
                                Log.d("MUTEE","getSelectedItem() :"+(String) num.getSelectedItem());

                                uploadPhoto(name,Integer.parseInt((String) num.getSelectedItem()),String.valueOf(emailSp.getSelectedItemPosition()),baken_number,uri);
                            }catch (Exception e){
                                uploadPhoto(name,00000,String.valueOf(emailSp.getSelectedItemPosition()),baken_number,uri);
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

    void openImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }


    private void uploadPhoto(final String name, final int number, final String email, final String baken, Uri uri) {
        Calendar calendar = Calendar.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference media = storage.getReference("photos");
        final StorageReference child = media.child("img" + calendar.getTimeInMillis());
        UploadTask uploadTask = child.putFile(uri);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return child.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();


                    String key = ref.push().getKey();
                    storeinfo storeinfo1=new storeinfo(name,number,email,baken,
                            ((RadioButton)findViewById(place_type.getCheckedRadioButtonId())).getText().toString(),
                            downloadUri.toString(),key,"","",num2.getSelectedItem().toString(),"");
                    ref.child(key).setValue(storeinfo1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(add.this, "تم إضافة المحل بنجاح ", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(add.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                } else {
                    Toast.makeText(add.this, "Upload " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    // Handle failures
                    // ...
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null ) {

            uri = data.getData();
            image.setImageURI(uri);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
