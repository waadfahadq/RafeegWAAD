package com.example.myapplication.shopowner.ui.info;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.ForgotPasswordActivity;
import com.example.myapplication.ui.home.storeinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class InfoFragment extends Fragment {


    private InfoViewModel infoViewModel;


    public InfoFragment(){}
    DatabaseReference databaseReference,databaseReference2;
    Query query;
    TextView t1,t2,t3;
    EditText from,to,phone;
    FirebaseAuth firebaseAuth;
    Button save,cancel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                ViewModelProviders.of(this).get(InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info_shopowner, container, false);
        t1 = root.findViewById(R.id.textView10);
        t2 = root.findViewById(R.id.textView7);
        t3 = root.findViewById(R.id.textView8);
        phone =root.findViewById(R.id.phone);
        from = root.findViewById(R.id.from_time);
        to = root.findViewById(R.id.to_time);
        cancel = root.findViewById(R.id.button);
        save = root.findViewById(R.id.button2);
        ImageView logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference2=FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Query query = databaseReference.child("storeinfo").orderByChild("email").equalTo(user.getEmail());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    storeinfo store = dataSnapshot.getValue(storeinfo.class);
                    t1.setText("إسم المتجر: "+store.getName());
                    t2.setText("رقم المتجر: "+String.valueOf(store.getNum()));
                    t3.setText("رقم الدور: "+store.getFloor());
                    phone.setText(store.getPhone());
                    to.setText(store.getTo());
                    from.setText(store.getFrom());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().equals("") || from.getText().toString().equals("") || to.getText().toString().equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("تنبيه")
                            .setMessage("الرجاء إدخال الحقول المطلوبة")
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                }
                            }).show();
                } else {
                    final Query query2 = databaseReference.child("storeinfo").orderByChild("email").equalTo(user.getEmail());
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("from", from.getText().toString());
                    map.put("phone", phone.getText().toString());
                    map.put("to", to.getText().toString());
                    query2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if (dataSnapshot.exists()) {
                                dataSnapshot.getRef().updateChildren(map);
                                Toast.makeText(getContext(),"تم حفظ البيانات بنجاح",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return root;
    }
}