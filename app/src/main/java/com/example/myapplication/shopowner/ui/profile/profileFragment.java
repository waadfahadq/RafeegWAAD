package com.example.myapplication.shopowner.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.shopowner.MainActivityShopowner;
import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.ForgotPasswordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class profileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    Button b1,b2;
    private FirebaseUser user;
    EditText usr,em;
    FirebaseAuth firebaseAuth;

    public profileFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_shopowner, container, false);
        b1 = root.findViewById(R.id.button);
        em = root.findViewById(R.id.email_edit_text);
        usr= root.findViewById(R.id.username_edit_text);
        user = FirebaseAuth.getInstance().getCurrentUser();
        em.setText(user.getEmail());
        usr.setText(MainActivityShopowner.username);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(pass);
            }
        });
        ImageView logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });


        return root;
    }
}