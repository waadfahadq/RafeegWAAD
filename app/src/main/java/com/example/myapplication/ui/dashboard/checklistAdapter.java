package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class checklistAdapter extends ArrayAdapter<checklistModel> {
    private ArrayList<checklistModel> list;
    private Context context;
    private DatabaseReference mDatabase;
    private SparseBooleanArray mSelectedItemsIds;

    public checklistAdapter(Context context, ArrayList<checklistModel> checklistitems){
        super(context,R.layout.checklist,checklistitems);
        mSelectedItemsIds = new SparseBooleanArray();
        this.list=checklistitems;
        this.context=context;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public int getPosition(@Nullable checklistModel item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checklist, null, true);
            holder = new ViewHolder();
            holder.namTxt = (TextView) convertView.findViewById(R.id.textView3);
//            holder.qu = (TextView) convertView.findViewById(R.id.textView4);
            holder.chk = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final checklistModel model = list.get(position);
        if(model!=null) {
            holder.namTxt.setText(model.getProductname());
//            holder.qu.setText(model.getQuantity());
            if (model.isChecked()) {
                holder.chk.setChecked(true);
                holder.namTxt.setPaintFlags(holder.namTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                holder.chk.setChecked(false);
                holder.namTxt.setPaintFlags(holder.namTxt.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            mDatabase = FirebaseDatabase.getInstance().getReference();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase.child("checkList").child(user.getUid());
            holder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checklistModel model = list.get(position);
                    if (!model.isChecked()) {
                        mDatabase.child("checkList").child(user.getUid()).child(model.getKey()).child("checked").setValue(true);
                    }else{
                        mDatabase.child("checkList").child(user.getUid()).child(model.getKey()).child("checked").setValue(false);
                    }
                }
            });
        }
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);
        return convertView;
    }

    public boolean isItDuplicate(checklistModel chk){
        boolean thereIs =false;
        for(int i = 0; i <list.size();i++){
            if(list.get(i).getKey()==chk.getKey()){
                thereIs =true;
            }
        }
        return thereIs;
    }

    @Override
    public void add(checklistModel chk) {
        Log.e("How many you add",chk.getKey());
        list.add(chk);
//        Collections.reverse(list);
        notifyDataSetChanged();
    }
    @Override
    public void remove(checklistModel chk) {
        // super.remove(object);
        list.remove(chk);
        notifyDataSetChanged();
    }
    public List<checklistModel> getLaptops() {
        return list;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
    public class ViewHolder {
        TextView namTxt,qu;
        CheckBox chk;
    }
}
