package com.example.myapplication.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DetailsActivity;
import com.example.myapplication.R;
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


public class StoreListFragment extends Fragment {


    public StoreListFragment() {
        // Required empty public constructor
    }

    private static final String ARG_PARAM1 = "param1";
    private String category;


    public static StoreListFragment newInstance(String param1) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_PARAM1);

        }
    }
    Button addStore;
    RecyclerView recyclerView;
    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<storeinfo,MyViewHolder> adapter;
    FirebaseRecyclerOptions<storeinfo> options;
    Query query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_store_list, container, false);



        getActivity().setTitle(" قائمة المحلات");

        //addStore=view.findViewById(R.id.addstore);
        recyclerView=view.findViewById(R.id.lv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        query= FirebaseDatabase.getInstance().getReference().child("storeinfo").orderByChild("type").equalTo(category);
        options=new FirebaseRecyclerOptions.Builder<storeinfo>().setQuery(query, storeinfo.class).build();
        adapter=new FirebaseRecyclerAdapter<storeinfo, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final storeinfo model) {
                holder.shopname.setText(model.getName());
                holder.shopnum.setText(String.valueOf(model.getNum()));
                if(isAdded())
                Glide.with(getActivity()).load(model.getImage()).into(holder.image);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(), DetailsActivity.class);
                        intent.putExtra("store",model);
                        intent.putExtra("key",model.getId());
                        startActivity(intent);

                    }
                });

                holder.fav_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseDatabase.getInstance().getReference("users").child(userId).child("likes").child(model.getId()).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    FirebaseDatabase.getInstance().getReference("users").child(userId).child("likes").child(model.getId()).removeValue();
                                }else {

                                    DatabaseReference push = FirebaseDatabase.getInstance().getReference("users").child(userId).
                                            child("likes").child(model.getId());
                                    String key=push.getKey();
                                    LikedStores likedStores=new LikedStores(key,model.getId(),userId);
                                    push.setValue(likedStores).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
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

                FirebaseDatabase.getInstance().getReference("users").child(userId).child("likes").child(model.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            //Picasso.get().load(R.drawable.ic_fav_on).into(holder.fav_img);
                            holder.fav_img.setImageResource(R.drawable.ic_fav_on);
                        }else {

                           // Picasso.get().load(R.drawable.ic_fav_off).into(holder.fav_img);
                            holder.fav_img.setImageResource(R.drawable.ic_fav_off);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.card,parent,false);
                return new MyViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //1
    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView shopname,shopnum;
        public LinearLayout linearLayout;
        ImageView image,fav_img;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname=(TextView)itemView.findViewById(R.id.shopname);
            shopnum=(TextView)itemView.findViewById(R.id.shopnum);
            image=(ImageView) itemView.findViewById(R.id.image);
            fav_img=(ImageView) itemView.findViewById(R.id.fav_img);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.line5);
        }
    }

}
