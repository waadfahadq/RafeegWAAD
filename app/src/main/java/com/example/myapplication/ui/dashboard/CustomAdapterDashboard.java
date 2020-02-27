package com.example.myapplication.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class CustomAdapterDashboard extends ArrayAdapter<DashboardViewModel> {

    Context c;
    List<DashboardViewModel> CategoryModels;
    int r;

    public CustomAdapterDashboard(Context c, int resource, List<DashboardViewModel> CategoryModelsr) {
        super(c,resource,CategoryModelsr);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= ((Activity) getContext()).getLayoutInflater().inflate(R.layout.grid_dashboard, parent, false);
        }
        // CategoryModel CategoryModels = new CategoryModel();
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.thing_photo2);
        TextView nameTxt= (TextView) convertView.findViewById(R.id.thing_name2);
        //  ImageButton img1 = (ImageButton) convertView.findViewById(R.id.imageButton5);
        //   ImageButton img2 = (ImageButton) convertView.findViewById(R.id.imageButton6);

        DashboardViewModel cmObej=  this.getItem(position);
        Log.e("category",cmObej.getName());
        nameTxt.setText(cmObej.getName());

        // photoImageView.setVisibility(View.VISIBLE);
        Glide.with(photoImageView.getContext()).load(cmObej.getPhotoUrl()).into(photoImageView);
        return convertView;
    }


}
