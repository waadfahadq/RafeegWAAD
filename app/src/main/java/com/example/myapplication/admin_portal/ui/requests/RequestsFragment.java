package com.example.myapplication.admin_portal.ui.requests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.admin_portal.ui.requests.ExpandableListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestsFragment extends Fragment {

    private RequestsViewModel requestsViewModel;
    private Context context;
    private final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("requests");
    private ExpandableListView list_view;
    List<String> listDataParent;
    HashMap<String, List<requests>> listDataChild;
    public RequestsFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestsViewModel =
                ViewModelProviders.of(this).get(RequestsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_requests, container, false);
        this.context = this.getActivity();
        ImageView logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_view = (ExpandableListView) view.findViewById(R.id.newRequests);
        createListData();

        // Listview Group click listener
        list_view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO GroupClickListener work
                return false;
            }
        });

        // Listview Group expanded listener
        list_view.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO GroupExpandListener work
            }
        });

        // Listview Group collasped listener
        list_view.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                // TODO GroupCollapseListener work
            }
        });

        // Listview on child click listener
        list_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText( context,
                        "wow, this is - "+listDataChild.get(listDataParent.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void createListData() {
        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<String, List<requests>>();

        // Adding child data
        listDataParent.add("طلبات جديدة");
        listDataParent.add("طلبات تعديل");

        // Adding child data List one
        final List<requests> newRequests = new ArrayList<requests>();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                requests request = dataSnapshot.getValue(requests.class);
                Log.e("email",request.getEmail());
                newRequests.add(request);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                requests request = dataSnapshot.getValue(requests.class);
                newRequests.remove(request);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        // Adding child data List two
//        List<requests> fruits  = new ArrayList<requests>();
//        fruits.add("Apples");
//        fruits.add("Bananas");
//        fruits.add("Apricots");
//        fruits.add("Cherries");
//        fruits.add("Elderberry");
//        fruits.add("Dates");
//
//        // Adding child data List three
//        List<String> animals = new ArrayList<String>();
//        animals.add("Dog");
//        animals.add("Cat");
//        animals.add("Elephant");
//        animals.add("horse");

        listDataChild.put(listDataParent.get(0), newRequests); // Header, Child data
//        listDataChild.put(listDataParent.get(1), fruits); // Header, Child data

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        list_view.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}