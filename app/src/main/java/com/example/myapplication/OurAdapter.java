package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.StoreDeatilsActivity;
import com.example.myapplication.ui.home.LikedStores;
import com.example.myapplication.ui.home.storeinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OurAdapter extends RecyclerView.Adapter<OurAdapter.OurAdapterViewHolder>{
    public Context c;
    public ArrayList<storeinfo>arrayList;
    public OurAdapter(Context c, ArrayList<storeinfo>arrayList){
        this.c=c;
        this.arrayList=arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public OurAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new OurAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OurAdapterViewHolder holder, int position) {
        final storeinfo model=arrayList.get(position);
        holder.t1.setText(model.getName());
        holder.t2.setText(model.getTypeStore());
        Picasso.get().load(model.getImage()).into(holder.t3);

        final String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("User").child(userId).child("likes").child(model.getId()).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    FirebaseDatabase.getInstance().getReference("User").child(userId).child("likes").child(model.getId()).removeValue();
                                    Toast.makeText(c, "تم إزالته من المفضلة  ", Toast.LENGTH_SHORT).show();

                                }else {

                                    DatabaseReference push = FirebaseDatabase.getInstance().getReference("User").child(userId).
                                            child("likes").child(model.getId());
                                    String key=push.getKey();
                                    LikedStores likedStores=new LikedStores(key,model.getId(),userId);
                                    push.setValue(likedStores).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(c, "تم إضافته للمفضلة ", Toast.LENGTH_SHORT).show();
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
        // this Mutee Update
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent=new Intent(c, StoreDeatilsActivity.class);
                intent.putExtra("store",model);
                c.startActivity(intent);
            }
        });



    }

    public class OurAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1;
        TextView t2;
        ImageView t3 ;
        ImageView fav_img ;
        public OurAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), detail.class);
                    i.putExtra("title",arrayList.get(getAdapterPosition()).getName());
                    i.putExtra("descr",arrayList.get(getAdapterPosition()).getTypeStore());
                    i.putExtra("image",arrayList.get(getAdapterPosition()).getImage());
                    // i.putExtra("title",storeinfo.getName());
                    // i.putExtra("descr",storeinfo.getTypeStore());
                    v.getContext().startActivity(i);
                }
            });
            t1=  (TextView)itemView.findViewById(R.id.shopname2);
            t2=  (TextView)itemView.findViewById(R.id.shopnum2);
            t3=  (ImageView)itemView.findViewById(R.id.image2);
            fav_img=  (ImageView)itemView.findViewById(R.id.fav_img);
            //fav_img=(ImageView) itemView.findViewById(R.id.fav_img);
        }
    }
}
