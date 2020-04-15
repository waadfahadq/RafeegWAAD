package com.example.myapplication;

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
    ImageView fav_img;
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
        t1=  (TextView)itemView.findViewById(R.id.shopname);
        t2=  (TextView)itemView.findViewById(R.id.shopnum);
        t3=  (ImageView)itemView.findViewById(R.id.image);
        fav_img=  (ImageView)itemView.findViewById(R.id.fav_img);
    }
}
