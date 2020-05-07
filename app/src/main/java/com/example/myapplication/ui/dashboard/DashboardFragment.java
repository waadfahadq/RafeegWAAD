package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.billingList_back;
import com.example.myapplication.ui.home.FavoriteFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private GridView GridViewIT;
    private CustomAdapterDashboard itCustomAdapterCategory;
    private DashboardViewModel dashboardViewModel;
    private TextView t1;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment__dashboard, container, false);




        t1= (TextView) root.findViewById(R.id.textView);
        t1.setText(MainActivity.username);

        ArrayList<DashboardViewModel> Categories2 = new ArrayList<DashboardViewModel>();
        Categories2.add(new DashboardViewModel("معلومات الحساب", R.drawable.user2));
        Categories2.add(new DashboardViewModel("الفواتير", R.drawable.bill));
        Categories2.add(new DashboardViewModel("الأماكن المفضلة", R.drawable.favlist));
        Categories2.add(new DashboardViewModel("قائمة التسوق", R.drawable.chklist));
        itCustomAdapterCategory = new CustomAdapterDashboard(getActivity(), R.layout.grid_dashboard, Categories2);
        GridViewIT = (GridView) root.findViewById(R.id.gridnew);
        GridViewIT.setAdapter(itCustomAdapterCategory);

        GridViewIT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dashboardViewModel = (DashboardViewModel) itCustomAdapterCategory.getItem(i);
//                dashboardViewModel.CategoryID = dashboardViewModel.getName();
//                dashboardViewModel.inCategoryPage=true;
                //finish();
                Log.e("test",String.valueOf(i));
                if(i==0) {
                    Fragment someFragment = new account();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(account.class.getSimpleName());  // if written, this transaction will be added to backstack
                    transaction.commit();
                }
                if (i == 1){

                    Intent intent = new Intent(getActivity(), billingList_back.class);
                    startActivity(intent);
                    ( (Activity)getActivity ()).overridePendingTransition (0,0);
                }
                if(i==3) {
                    Fragment someFragment = new checklist();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, someFragment); // give your fragment container id in first parameter
                    transaction.addToBackStack(account.class.getSimpleName());  // if written, this transaction will be added to backstack
                    transaction.commit();
                }


                if(i==2) {
                    Fragment someFragment = new checklist();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new FavoriteFragment()); // give your fragment container id in first parameter
                    transaction.addToBackStack(account.class.getSimpleName());  // if written, this transaction will be added to backstack
                    transaction.commit();
                }
//                ((MainActivity) getActivity()).loadFragment(new Home());

                //Intent intent = new Intent(Category.this,MainActivity.class);
                // startActivity(intent);
            }
        });


        return root;
    }

//not working yet
    @Override
    public void onClick(View v) {




    }



}