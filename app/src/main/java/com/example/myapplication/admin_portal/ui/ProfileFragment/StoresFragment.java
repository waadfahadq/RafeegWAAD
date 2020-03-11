package com.example.myapplication.admin_portal.ui.ProfileFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.storeinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StoresFragment extends Fragment {


    private StoresViewModel storesViewModel;


    public StoresFragment(){}

    Button addStore;
    RecyclerView recyclerView;
    //String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<storeinfo, MyViewHolder> adapter;
    FirebaseRecyclerOptions<storeinfo> options;
    Query query;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storesViewModel =
                ViewModelProviders.of(this).get(StoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stores, container, false);
        addStore=root.findViewById(R.id.addstore);
        recyclerView=root.findViewById(R.id.lv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), add.class));
            }
        });


        query= FirebaseDatabase.getInstance().getReference().child("storeinfo");
        options=new FirebaseRecyclerOptions.Builder<storeinfo>().setQuery(query, storeinfo.class).build();
        adapter=new FirebaseRecyclerAdapter<storeinfo, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final StoresFragment.MyViewHolder holder, final int position, @NonNull final storeinfo model) {
                holder.shopname.setText(model.getName());
                holder.shopnum.setText(String.valueOf(model.getNum()));
                if(isAdded())
                    Glide.with(getActivity()).load(model.getImage()).into(holder.image);
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(), UpdateDeleteStoreActivity.class);
                        intent.putExtra("store",model);
                        intent.putExtra("key",model.getId());
                        startActivity(intent);

                    }
                });



            }

            @NonNull
            @Override
            public StoresFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getActivity()).inflate(R.layout.card,parent,false);
                return new MyViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        return root;
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
            fav_img.setVisibility(View.GONE);
        }
    }
}