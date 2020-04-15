package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.home.storeinfo;
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
    public void onBindViewHolder(@NonNull OurAdapterViewHolder holder, int position) {
        storeinfo storeinfo=arrayList.get(position);
        holder.t1.setText(storeinfo.getName());
        holder.t2.setText(storeinfo.getTypeStore());
        Picasso.get().load(storeinfo.getImage()).into(holder.t3);


    }

    public class OurAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1;
        TextView t2;
        ImageView t3 ;
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
            //fav_img=(ImageView) itemView.findViewById(R.id.fav_img);
        }
    }
}
