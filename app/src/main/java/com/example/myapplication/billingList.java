package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class billingList extends  RecyclerView.Adapter<billingList.ViewHolder>{

    private static final String TAG= "RecycleView";
    private List<String> NameOfbill = new ArrayList<>();
    private List<String> Image = new ArrayList<>();
    private ArrayList<String>  billID = new ArrayList<>();
    private com.example.myapplication.sharedInformation sharedInformation;
    private Context context;


    public billingList(List<String> NameOfbill, List<String> Image, ArrayList<String> billID, Context con) {
        this.NameOfbill = NameOfbill;
        this.Image = Image;
        this.billID = billID;
        this.context = con;
    }

    public billingList(List<String> NameOfbill) {
        this.NameOfbill = NameOfbill;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_billing_list,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "OnBinViewHolder:called.");
        final String billName = NameOfbill.get(position);
        final String ImageBill = Image.get (position);
        final String billId = billID.get(position);
        sharedInformation  = new sharedInformation (context);
        holder.NameOfbill1.setText(billName);
        Picasso.get ().load (Uri.parse (ImageBill)).into (holder.ImageBill);
        holder.paerntlyout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent (context, singleBillInfo.class);
                intent.putExtra("name",billName);
                intent.putExtra("bId",billId);
                sharedInformation.setKeyConid(billId);
                context.startActivity(intent);
            }
        });

       if(singleBillInfo.deleteFromList == true){
        holder.paerntlyout.setVisibility(View.GONE);

       }
    }

    @Override
    public int getItemCount() {
        return NameOfbill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView NameOfbill1 ;
        ImageView ImageBill ;
        RelativeLayout paerntlyout ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameOfbill1 = itemView.findViewById(R.id.NameOfbill);
            ImageBill = itemView.findViewById (R.id.image);
            paerntlyout = itemView.findViewById(R.id.paernt);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
