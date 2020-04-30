package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class for_approval_edit extends AppCompatActivity {

    FirebaseDatabase database1;
    DatabaseReference retreff ;
    private ArrayList<String> nId = new ArrayList<>();
    private ArrayList<String> nName = new ArrayList<>();
    private ArrayList<String> ShopName = new ArrayList<>();
    private ArrayList<String> DIS = new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_for_approval_edit);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("طلبات تعديل الإعلانات");
        toolbar.setTitleTextColor(Color.BLACK);
        updateAD();
    }
    private void updateAD() {

        database1= FirebaseDatabase.getInstance();
        retreff=database1.getReference("Advertisment update Information");
        retreff.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                    nName.clear(); //Clear your array list before adding new data in it
                }

                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    advertismentInfo Nm = snapshot.getValue(advertismentInfo.class) ;
                    String userId=snapshot.getKey().toString();
                    String name = Nm.getNameOfAdvertisment ();
                    String ShpoName = Nm.getShopName ();
                    String dis = Nm.getDescription ();
                    nName.add(name);
                    ShopName.add (ShpoName);
                    DIS.add (dis);
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
        RecyclerView recyclerView= findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        recyclerView.setHasFixedSize(true);
        ApprovalList_edit myr = new ApprovalList_edit(nName,ShopName,DIS, nId,this);
        recyclerView.setAdapter(myr);
        LinearLayoutManager layoutManager=
                new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
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