package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class checklistAdapter extends ArrayAdapter<checklistModel> {
    private List<checklistModel> list;
    private Context context;
    private SparseBooleanArray mSelectedItemsIds;

    public checklistAdapter(Context context, List<checklistModel> checklistitems){
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
        ViewHolder holder;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checklist, null, true);
            holder = new ViewHolder();
            holder.namTxt = (TextView) convertView.findViewById(R.id.textView3);
            holder.qu = (TextView) convertView.findViewById(R.id.textView4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final checklistModel model = list.get(position);
        holder.namTxt.setText(model.getProductname());
        holder.qu.setText(model.getQuantity());
        notifyDataSetChanged();
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);
        return convertView;
    }
    @Override
    public void add(checklistModel chk) {
        list.add(chk);
        notifyDataSetChanged();
        Toast.makeText(context, list.toString(), Toast.LENGTH_LONG).show();
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
    private class ViewHolder {
        TextView namTxt,qu;
    }
}
