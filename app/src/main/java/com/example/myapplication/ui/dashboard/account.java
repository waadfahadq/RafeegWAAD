package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account extends Fragment {
    Button b1;
    ImageView back ;
    private FirebaseUser user;
    EditText em;

    public account() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setHasOptionsMenu(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("الملف الشخصي");
        b1 = rootView.findViewById(R.id.button);
        back = rootView.findViewById(R.id.back_btnProfile);
        em = rootView.findViewById(R.id.email_edit_text);
        user = FirebaseAuth.getInstance().getCurrentUser();
        em.setText(user.getEmail());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getActivity(),ForgotPasswordActivity.class);
                startActivity(pass);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home :
//                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("حسابي");
//                getActivity().onBackPressed();
//                break;
//            default :
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}