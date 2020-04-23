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
import com.example.myapplication.admin_portal.ui.Advertisement.ApproveAD;
import java.util.ArrayList;
import java.util.List;

public class ApprovalList extends  RecyclerView.Adapter<ApprovalList.ViewHolder>{

    private static final String TAG= "RecycleView";
    private List<String> NameOfAvertisment = new ArrayList<>();
    private List<String> NameOfShop = new ArrayList<>();
    private ArrayList<String>  bID = new ArrayList<>();
    private sharedInformation sharedInformation;
    private Context context;

    public ApprovalList(List<String> nameOfUser,List<String> NameOfShopon, ArrayList<String> bid, Context con) {
        NameOfAvertisment = nameOfUser;
        NameOfShop = NameOfShopon ;
        bID = bid;
        this.context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_approval_list,parent,false);
        ApprovalList.ViewHolder holder= new ApprovalList.ViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBinViewHolder:called.");
        final String nameOfAvertisment = NameOfAvertisment.get(position);
        final  String nameOfSHop = NameOfShop.get(position);
        final String BID = bID.get(position);
        sharedInformation  = new sharedInformation (context);
        holder.NameOfAvertisment.setText(nameOfAvertisment);
        holder.NameOfShopOn.setText (nameOfSHop);
        holder.paerntlyout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, Admin_Approval_Add.class);
                intent.putExtra("name",nameOfAvertisment);
                intent.putExtra("nameOfShop",nameOfSHop);
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
        TextView NameOfShopOn ;
        RelativeLayout paerntlyout ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameOfAvertisment = itemView.findViewById(R.id.NameOfAdver);
            NameOfShopOn = itemView.findViewById(R.id.NameOfShop);
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


