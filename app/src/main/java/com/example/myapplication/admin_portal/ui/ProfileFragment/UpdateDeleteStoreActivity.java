package com.example.myapplication.admin_portal.ui.ProfileFragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
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
import java.util.HashMap;

public class UpdateDeleteStoreActivity extends AppCompatActivity {

    EditText name,email,type,baken;
    ImageView image;
    Spinner number;
    Button update;
    String key;
    ArrayAdapter<String> adapter;
    private Uri uri;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_store);
        Toolbar toolbar = findViewById(R.id.toolbar800);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("معلومات المحل");
        toolbar.setTitleTextColor(Color.WHITE);


        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        email=findViewById(R.id.email);
        type=findViewById(R.id.type);
        baken=findViewById(R.id.baken);
        image=findViewById(R.id.image);
        ref= FirebaseDatabase.getInstance().getReference().child("storeinfo");

        String [] array=getResources().getStringArray(R.array.numbers);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        number.setAdapter(adapter);

        Intent intent=getIntent();
        if(intent.hasExtra("store")){
            storeinfo storeinfo= (com.example.myapplication.ui.home.storeinfo) intent.getSerializableExtra("store");
            name.setText(storeinfo.getName());
            number.setSelection(adapter.getPosition(String.valueOf(storeinfo.getNum())));


            email.setText(storeinfo.getEmail());
            type.setText(storeinfo.getTypeStore());
            baken.setText(storeinfo.getBeaconID());
            key=intent.getStringExtra("key");
            Glide.with(this).load(storeinfo.getImage()).into(image);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UpdateDeleteStoreActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                openImage();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                                Toast.makeText(UpdateDeleteStoreActivity.this, "Permmission not granted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        })
                        .check();
            }
        });



        update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String store_name=name.getText().toString().trim();
                //String number=num.getText().toString().trim();
                String ownerEmail=email.getText().toString().trim();
                String baken_number=baken.getText().toString().trim();
                String place_type=type.getText().toString().trim();

                if(TextUtils.isEmpty(store_name)){
                    Toast.makeText(UpdateDeleteStoreActivity.this, "ادخل اسم المحل", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(TextUtils.isEmpty(number)){
                    Toast.makeText(add.this, "Please Enter number", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if(TextUtils.isEmpty(ownerEmail)){
                    Toast.makeText(UpdateDeleteStoreActivity.this, "ادخل ايميل المالك", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(baken_number)){
                    Toast.makeText(UpdateDeleteStoreActivity.this, "ادخل رقم البيكن", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(place_type)){
                    Toast.makeText(UpdateDeleteStoreActivity.this, "ادخل نوع المحل ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(uri ==null){

                    //update
                    HashMap<String,Object>map=new HashMap<>();
                    map.put("beaconID",baken_number);
                    map.put("email",ownerEmail);
                    map.put("name",store_name);
                    map.put("num",Integer.parseInt((String) number.getSelectedItem()));
                    map.put("typeStore",place_type);

                    ref.child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(UpdateDeleteStoreActivity.this, "لقد تم تحديث المحل بنجاح ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    // upload new photo and update
                    uploadPhoto(store_name,Integer.parseInt((String) number.getSelectedItem()),ownerEmail,baken_number,place_type,uri);
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

                    HashMap<String,Object>map=new HashMap<>();
                    map.put("beaconID",baken);
                    map.put("email",email);
                    map.put("name",name);
                    map.put("num",number);
                    map.put("typeStore",type);
                    map.put("image",downloadUri.toString());

                    ref.child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(UpdateDeleteStoreActivity.this, "لقد تم تحديث المحل بنجاح  ", Toast.LENGTH_SHORT).show();
                        }
                    });




                } else {
                    Toast.makeText(UpdateDeleteStoreActivity.this, "Upload " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

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
