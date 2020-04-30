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
import java.util.List;

public class ApprovalList_edit extends  RecyclerView.Adapter<ApprovalList_edit.ViewHolder>{

    private static final String TAG= "RecycleView";
    private List<String> NameOfAvertisment = new ArrayList<>();
    private List<String> NameOfShop = new ArrayList<>();
    private List<String> Dis = new ArrayList<>();
    private ArrayList<String>  bID = new ArrayList<>();
    private sharedInformation sharedInformation;
    private Context context;

    public ApprovalList_edit(List<String> nameOfUser,List<String> NameOfShopon, List<String> DIS, ArrayList<String> bid, Context con) {
        NameOfAvertisment = nameOfUser;
        NameOfShop = NameOfShopon ;
        Dis = DIS;
        bID = bid;
        this.context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_approval_list,parent,false);
        ApprovalList_edit.ViewHolder holder= new ApprovalList_edit.ViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBinViewHolder:called.");
        final String nameOfAvertisment = NameOfAvertisment.get(position);
        final  String nameOfSHop = NameOfShop.get(position);
        final  String DISC = Dis.get (position);
        final String BID = bID.get(position);
        sharedInformation  = new sharedInformation (context);
        holder.NameOfAvertisment.setText(nameOfAvertisment);
        holder.NameOfShopOn.setText (nameOfSHop);
        holder.paerntlyout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, admin_approval_edit.class);
                intent.putExtra("name",nameOfAvertisment);
                intent.putExtra("nameOfShop",nameOfSHop);
                intent.putExtra("dis",DISC);
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


