package com.example.myapplication.admin_portal.ui.ProfileFragment;

import android.content.Intent;
import android.graphics.Canvas;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.shopowner.shopowner_info;
import com.example.myapplication.ui.home.storeinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


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
        ImageView logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                storeinfo st=adapter.getItem(position);


                direction=ItemTouchHelper.LEFT;
                deleteStore(st.getId(),st.getEmail());
                //Log.d("MUTEE", "onSwiped: "+st.toString());



                System.out.println("---key --- two  --"+st.getId());
                //  MyViewHolder M=new MyViewHolder();









            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                //new RecyclerViewSwipeDecorator.Builder(StoresFragment.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                new RecyclerViewSwipeDecorator.Builder(getContext(),c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(),R.color.RED))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        }).attachToRecyclerView(recyclerView);
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
    private  void  deleteStore(final String key, final String email){
        Log.d("MUTEE", "deleteStore: "+email);
        DatabaseReference deleteStore=FirebaseDatabase.getInstance().getReference("storeinfo").child(key);
        deleteStore.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().
                            getReference().
                            child("shipowners").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            if(dataSnapshot1.exists()){
                                for (DataSnapshot child : dataSnapshot1.getChildren()) {
                                    String key=child.getKey();
                                    final HashMap<String ,Object> map=new HashMap<>();
                                    map.put("active",false);
                                    //map.put("email",dataSnapshot1.child("email").getValue(String.class));
                                    //map.put("name",dataSnapshot1.child("name").getValue(String.class));
                                    FirebaseDatabase.getInstance()
                                            .getReference()
                                            .child("shipowners").child(key).updateChildren(map);
                                    Toast.makeText(getContext(),"لقد تم حذف المحل ومالك المحل ",Toast.LENGTH_LONG).show();

                                }






                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }
}