package com.example.myapplication.admin_portal.ui.ProfileFragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.storeinfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Calendar;

public class add extends AppCompatActivity {

    TextView write;
    Button enter;
    DatabaseReference ref;
    ImageView image;
    Uri uri;
    EditText nameform,email,baken,type;
    Spinner num;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar900);
        setSupportActionBar(toolbar);
        image=findViewById(R.id.image);
        nameform=findViewById(R.id.nameform);
        num=findViewById(R.id.num);
        String [] array=getResources().getStringArray(R.array.numbers);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        num.setAdapter(adapter);

        email=findViewById(R.id.email);
        baken=findViewById(R.id.baken);
        type=findViewById(R.id.type);

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

                String name=nameform.getText().toString().trim();
                //String number=num.getText().toString().trim();
                String ownerEmail=email.getText().toString().trim();
                String baken_number=baken.getText().toString().trim();
                String place_type=type.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(add.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(TextUtils.isEmpty(number)){
                    Toast.makeText(add.this, "Please Enter number", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(TextUtils.isEmpty(ownerEmail)){
                    Toast.makeText(add.this, "Please Enter ownerEmail", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(baken_number)){
                    Toast.makeText(add.this, "Please Enter baken_number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(place_type)){
                    Toast.makeText(add.this, "Please Enter place type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(uri ==null){
                    Toast.makeText(add.this, "Please Enter Select photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Log.d("MUTEE","getSelectedItem() :"+(String) num.getSelectedItem());
                    uploadPhoto(name,Integer.parseInt((String) num.getSelectedItem()),ownerEmail,baken_number,place_type,uri);
                }catch (Exception e){
                    uploadPhoto(name,00000,ownerEmail,baken_number,place_type,uri);
                }




            }
        });
    }

    void openImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }


    private void uploadPhoto(final String name, final int number, final String email, final String baken, final String type, Uri uri) {
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
                    storeinfo storeinfo1=new storeinfo(name,number,email,baken,type,downloadUri.toString(),key);
                    ref.child(key).setValue(storeinfo1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(add.this, "Place Saved", Toast.LENGTH_SHORT).show();
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


}
