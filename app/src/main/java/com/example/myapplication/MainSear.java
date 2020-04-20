package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.home.LikedStores;
import com.example.myapplication.ui.home.storeinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainSear extends AppCompatActivity {
    private String title=null;
    RecyclerView recyclerView;
    DatabaseReference mDatabase;
    Context context;
    EditText sear;
    ArrayList<storeinfo> arrayList;
    FirebaseRecyclerOptions<storeinfo> options;
    FirebaseRecyclerAdapter<storeinfo, itemViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sear);
        recyclerView = (RecyclerView) findViewById(R.id.plant_list);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        Intent i = getIntent();
        final  String x=i.getStringExtra("title");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeinfo");
        Query q=FirebaseDatabase.getInstance().getReference().child("storeinfo").orderByChild("typeStore").equalTo(x);
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String description = snapshot.child("typeStore").getValue(String.class);
//                    if(x.equals(description)){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        options=new FirebaseRecyclerOptions.Builder<storeinfo>().setQuery(q, storeinfo.class).build();
        adapter=new FirebaseRecyclerAdapter<storeinfo, itemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final itemViewHolder itemViewHolder, int i, final @NonNull storeinfo model) {

                Picasso.get().load(model.getImage()).into(itemViewHolder.t3, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }


                });

                itemViewHolder.t1.setText(model.getName());
                itemViewHolder.t2.setText(model.getTypeStore());
                final String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

                itemViewHolder.fav_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseDatabase.getInstance().getReference("User").child(userId).child("likes").child(model.getId()).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            FirebaseDatabase.getInstance().getReference("User").child(userId).child("likes").child(model.getId()).removeValue();
                                        }else {

                                            DatabaseReference push = FirebaseDatabase.getInstance().getReference("User").child(userId).
                                                    child("likes").child(model.getId());
                                            String key=push.getKey();
                                            LikedStores likedStores=new LikedStores(key,model.getId(),userId);
                                            push.setValue(likedStores).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(MainSear.this, "Liked", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                    }
                });

                FirebaseDatabase.getInstance().getReference("User").child(userId).child("likes").child(model.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            //Picasso.get().load(R.drawable.ic_fav_on).into(holder.fav_img);
                            itemViewHolder.fav_img.setImageResource(R.drawable.ic_fav_on);
                        }else {

                            // Picasso.get().load(R.drawable.ic_fav_off).into(holder.fav_img);
                            itemViewHolder.fav_img.setImageResource(R.drawable.ic_fav_off);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.card, parent, false);

                return new itemViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        sear=(EditText)findViewById(R.id.search_field);
        sear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(! editable.toString().isEmpty()){
                    search(editable.toString());
                }
                else{
                    search("");
                }
            }
        });

    }

    private void search(String toString) {
        if(toString.isEmpty() || toString==null){
            recyclerView.setAdapter(adapter);
        }else {
            Query firebaseSearchQuery = mDatabase.orderByChild("name").startAt(toString).endAt(toString + "\uf8ff");
            firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        arrayList.clear();
                        for(DataSnapshot dss:dataSnapshot.getChildren()){
                            final storeinfo storeinfo=dss.getValue(com.example.myapplication.ui.home.storeinfo.class);
                            arrayList.add(storeinfo);
                        }
                        OurAdapter ourAdapter=new OurAdapter(getApplicationContext(),arrayList);
                        recyclerView.setAdapter(ourAdapter);
                        ourAdapter.notifyDataSetChanged();
                        if(dataSnapshot.exists()){

                            //Picasso.get().load(R.drawable.ic_fav_on).into(holder.fav_img);
                            // holder.fav_img.setImageResource(R.drawable.ic_fav_on);
                        }else {

                            // Picasso.get().load(R.drawable.ic_fav_off).into(holder.fav_img);
                            //holder.fav_img.setImageResource(R.drawable.ic_fav_off);
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }

}
