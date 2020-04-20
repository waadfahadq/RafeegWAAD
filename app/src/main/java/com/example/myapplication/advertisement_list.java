package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class advertisement_list extends  RecyclerView.Adapter<advertisement_list.ViewHolder>{

    private static final String TAG= "RecycleView";
    private ArrayList<String> NameOfAvertisment = new ArrayList<>();
    private ArrayList<String>  bID = new ArrayList<>();
    private sharedInformation sharedInformation;
    private Context context;

    public advertisement_list(ArrayList<String> nameOfUser, ArrayList<String> bid, Context con) {
        NameOfAvertisment = nameOfUser;
        bID = bid;
        this.context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_advertisement_list,parent,false);
        advertisement_list.ViewHolder holder= new advertisement_list.ViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBinViewHolder:called.");
        final String nameOfAvertisment = NameOfAvertisment.get(position);
        final String BID = bID.get(position);
        sharedInformation  = new sharedInformation (context);
        holder.NameOfAvertisment.setText(nameOfAvertisment);
        holder.paerntlyout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, singleAdvertisementInfo.class);
                intent.putExtra("name",nameOfAvertisment);
                intent.putExtra("BID",BID);
                sharedInformation.setKeyConid(BID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return NameOfAvertisment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView NameOfAvertisment ;
        RelativeLayout paerntlyout ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameOfAvertisment = itemView.findViewById(R.id.NameOfAdver);
            paerntlyout = itemView.findViewById(R.id.paernt);
        }
    }
}
