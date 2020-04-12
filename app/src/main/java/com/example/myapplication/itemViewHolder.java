package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.home.storeinfo;

public class itemViewHolder extends RecyclerView.ViewHolder {
    TextView t1;
    TextView t2;
    ImageView t3;
    storeinfo item;

    public itemViewHolder(@NonNull View itemView) {
        super(itemView);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(),detail.class);
//                i.putExtra("title",arrayList.get(getAdapterPosition()).getName());
//                i.putExtra("descr",arrayList.get(getAdapterPosition()).getTypeStore());
//                i.putExtra("image",arrayList.get(getAdapterPosition()).getImage());
//                v.getContext().startActivity(i);
//            }
//        });
        t1=  (TextView)itemView.findViewById(R.id.textTitle);
        t2=  (TextView)itemView.findViewById(R.id.textDesc);
        t3=  (ImageView)itemView.findViewById(R.id.imageViewcard);
    }
}
