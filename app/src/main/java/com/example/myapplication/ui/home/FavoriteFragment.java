package com.example.myapplication.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.MainSear;
import com.example.myapplication.R;
import com.example.myapplication.StoreDeatilsActivity;
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



public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private RecyclerView fav_list;
    private Query query;
    private FirebaseRecyclerOptions<LikedStores> options;
    private FirebaseRecyclerAdapter<LikedStores,MyViewHolder> adapter;
    private String user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
    ImageView back ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_favorite, container, false);
        back=view.findViewById(R.id.back_btn2);

        fav_list=view.findViewById(R.id.fav_list);
        fav_list.setLayoutManager(new LinearLayoutManager(getContext()));
        query= FirebaseDatabase.getInstance().getReference("User").child(user_id).child("likes");

        options=new FirebaseRecyclerOptions.Builder<LikedStores>().setQuery(query,LikedStores.class).build();
        adapter=new FirebaseRecyclerAdapter<LikedStores, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final LikedStores likedStores) {
                Log.d("MUTEE","onBindViewHolder");
                storeinfo storeinfo;
                FirebaseDatabase.getInstance().getReference().child("storeinfo").
                        child(likedStores.getStoreId()).
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()){
                                    Log.d("MUTEE","dataSnapshot.exists()");
                                    FirebaseDatabase.getInstance().getReference("User").child(user_id).child("likes").child(likedStores.getStoreId()).removeValue();
                                }else {
                                    Log.d("MUTEE","onDataChange");
                                    final storeinfo model1=dataSnapshot.getValue(storeinfo.class);

                                    holder.shopname.setText(model1.getName());
                                    holder.shopnum.setText(String.valueOf(model1.getNum()));
                                    if(isAdded())
                                        Glide.with(getContext()).load(model1.getImage()).into(holder.image);
                                    // this Mutee Update
                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent   intent=new Intent(getActivity(), StoreDeatilsActivity.class);
                                            intent.putExtra("store",model1);
                                            startActivity(intent);
                                        }
                                    });
                                    holder.fav_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            FirebaseDatabase.getInstance().getReference("User").child(user_id).child("likes").child(model1.getId()).
                                                    addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                FirebaseDatabase.getInstance().getReference("User").child(user_id).
                                                                        child("likes").child(model1.getId()).removeValue();
                                                                Toast.makeText(getContext(), "تم إزالته من المفضلة ", Toast.LENGTH_SHORT).show();

                                                            }else {

                                                                DatabaseReference push = FirebaseDatabase.getInstance().getReference("User").child(user_id).
                                                                        child("likes").child(model1.getId());
                                                                String key=push.getKey();
                                                                LikedStores likedStores=new LikedStores(key,model1.getId(),user_id);
                                                                push.setValue(likedStores).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            Toast.makeText(getContext(), "تم إضافته للمفضلة", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }
                                                                });
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                           // Toast.makeText(getContext(), databaseError.getMessage() +" 22", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                        }
                                    });

                                    FirebaseDatabase.getInstance().
                                            getReference("User")
                                            .child(user_id).child("likes")
                                            .child(likedStores.getStoreId()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){

                                                //
                                                holder.fav_img.setImageResource(R.drawable.ic_fav_on);
                                            }else {

                                                //
                                                holder.fav_img.setImageResource(R.drawable.ic_fav_off);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getContext(), databaseError.getMessage() +" 33", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("MUTEE","onCancelled 11");

                            }
                        });

                // this Mutee Update



            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.card,parent,false);
                return new FavoriteFragment.MyViewHolder(view);
            }
        };
        fav_list.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent n= new Intent(getActivity(), DashboardViewModel.class);
                //startActivity(n);
                getActivity().onBackPressed();




            }
        });

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
