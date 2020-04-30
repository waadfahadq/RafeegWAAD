package com.example.myapplication.admin_portal.ui.Advertisement;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.Admin_Approval_Add;
import com.example.myapplication.ApprovalList;
import com.example.myapplication.ForApproval;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.billingList_back;
import com.example.myapplication.for_approval_dalete;
import com.example.myapplication.for_approval_edit;

public class ApproveAD extends Fragment {

    Button Add ;
    Button edit ;
    Button delete ;
    private ApproveAdViewModel mViewModel;

    public static ApproveAD newInstance() {
        return new ApproveAD ();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                ViewModelProviders.of(this).get(ApproveAdViewModel.class);
        View root =  inflater.inflate (R.layout.approve_ad_fragment, container, false);
        ImageView logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        Add = root.findViewById (R.id.addAdv);
        edit = root.findViewById (R.id.editAdv);
        delete = root.findViewById (R.id.deleteAdv);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ForApproval.class);
                startActivity(intent);
                ( (Activity)getActivity ()).overridePendingTransition (0,0);
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), for_approval_edit.class);
                startActivity(intent);
                ( (Activity)getActivity ()).overridePendingTransition (0,0);
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), for_approval_dalete.class);
                startActivity(intent);
                ( (Activity)getActivity ()).overridePendingTransition (0,0);
            }

        });


        return root ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
        mViewModel = ViewModelProviders.of (this).get (ApproveAdViewModel.class);
        // TODO: Use the ViewModel
    }

}
