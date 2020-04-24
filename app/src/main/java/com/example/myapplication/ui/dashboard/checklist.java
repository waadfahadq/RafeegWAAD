package com.example.myapplication.ui.dashboard;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myapplication.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.sephiroth.android.library.numberpicker.NumberPicker;
import timber.log.Timber;
import static android.view.View.resolveSizeAndState;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class checklist extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<checklistModel> checklists;
    SwipeMenuListView listViewPosts;
    private FloatingActionButton f1;
    CheckBox mCheckBox;
    checklistModel model;
    checklistAdapter adapter;
    private ActionMode mActionMode;
    View rootView;
    private DatabaseReference mDatabase;
    private ConstraintLayout constraintLayout;

// ...
    public checklist() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checklists = new ArrayList<>();
         rootView = inflater.inflate(R.layout.checklist_activity, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setHasOptionsMenu(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("قائمة التسوق");



        listViewPosts = (SwipeMenuListView) rootView.findViewById(R.id.listViewPosts);
        constraintLayout = rootView.findViewById(R.id.card_view3);
        adapter= new checklistAdapter(getContext(),checklists );
        listViewPosts.setAdapter(adapter);
        listViewPosts.setOnItemClickListener(this);
        listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemSelect(position);
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("checkList").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                checklist.this.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        f1 = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_dialog, null);
        final EditText name = customLayout.findViewById(R.id.editText);
        final NumberPicker n1 = customLayout.findViewById(R.id.numberPicker);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("أدخل معلومات المنتج");
                alert.setView(customLayout);
                alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // create and show the alert dialog
                final AlertDialog dialog1 = alert.create();
                dialog1.show();
                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // send data from the AlertDialog to the Activity
                            if (name.getText().toString().equals("")) {
                                name.setError("الرجاء إدخال اسم المنتج");

                            } else{
                                model = new checklistModel(name.getText().toString(), String.valueOf(n1.getProgress()), false, "");
                                mDatabase.child("checkList").child(user.getUid()).push().setValue(model, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError,
                                                           DatabaseReference databaseReference) {
                                        String uniqueKey = databaseReference.getKey();
                                        mDatabase.child("checkList").child(user.getUid()).child(uniqueKey).child("key").setValue(uniqueKey);
                                        model.setKey(uniqueKey);
                                    }
                                });
                                Log.e("chosen quantity", String.valueOf(n1.getProgress()));
                                dialog1.dismiss();
                            }
                        }
                    });
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((250));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listViewPosts.setMenuCreator(creator);
        listViewPosts.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // I added them Sunday morning
                //Edited by Nada, it's done now
                checklistModel selectedItem = adapter
                                        .getItem(position);
                                mDatabase.child("checkList").child(user.getUid()).child(selectedItem.getKey()).removeValue();
                                adapter.remove(selectedItem);
                                return false;
                            }
        });
        listViewPosts.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        return rootView;
    }


    public void onDataChange(DataSnapshot dataSnapshot)
    {
        checklists.clear();
        for(DataSnapshot children: dataSnapshot.getChildren()){
            checklistModel modelClass=children.getValue(checklistModel.class);
            checklists.add(modelClass);
        }
        Collections.reverse(checklists);
        adapter.notifyDataSetChanged();
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,
                            int position, long id) {
        if (mActionMode == null) {
            /*no items selected, so perform item click actions
             * like moving to next activity */
//            Toast toast = Toast.makeText(getContext(), "Item "
//                            + (position + 1) + ": " + checklists.get(position),
//                    Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//            toast.show();

        } else
            // add or remove selection for current list item
            onListItemSelect(position);
    }
    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);
        boolean hasCheckedItems = adapter.getSelectedCount() > 0;
        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = getActivity().startActionMode(new ActionModeCallback());
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();
//        if (mActionMode != null)
//            mActionMode.setTitle(String.valueOf(adapter
//                    .getSelectedCount()) + " تم إختياره");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator) {
        Log.d(TAG, "onCreateOptionsMenu");
        inflator.inflate(R.menu.check_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflator);
    }
//    private void setLaptopList() {
//        String[] blogs = getResources().getStringArray(R.array.laptops);
//        for (String brand ; String qu : blogs) {
//            checklistModel model= new checklistModel(brand,qu);
//            checklists.add(model);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("حسابي");
                getActivity().onBackPressed();
                break;
            case R.id.delete_form:
                    // there are some selected items, start the actionMode
                    mActionMode = getActivity().startActionMode(new ActionModeCallback());
                    // there no selected items, finish the actionMode
                    if (mActionMode != null)
//                    mActionMode.setTitle(String.valueOf(adapter
//                            .getSelectedCount()) + " تم إختيار");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        listViewPosts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CheckBox checkBox = view.findViewById(R.id.checkBox);
//                checkBox.setVisibility(View.INVISIBLE);
                onListItemSelect(i);
                return true;
            }
        });
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // inflate contextual menu
            mode.getMenuInflater().inflate(R.menu.context_main, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    // retrieve selected items and delete them out
                    SparseBooleanArray selected = adapter
                            .getSelectedIds();
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            checklistModel selectedItem = adapter
                                    .getItem(selected.keyAt(i));
                            Log.e("i",String.valueOf(i));
                            Log.e("Item to be deleted",selectedItem.getKey());
                            mDatabase.child("checkList").child(user.getUid()).child(selectedItem.getKey()).removeValue();
                            adapter.remove(selectedItem);
                        }
                    }
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // remove selection
            adapter.removeSelection();
            mActionMode = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mActionMode != null){
        mActionMode.finish();}
    }
}