package com.example.yamashita.albumapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;


public class BitmapAdapter extends ArrayAdapter<Bitmap> {
    private int resourceId;
    final private static String TAG = "BitmapAdapter";


    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }
        CustomImageView view = (CustomImageView) convertView;
        view.setImageBitmap(getItem(position));
        Log.d(TAG, String.valueOf(position));
        return view;
    }
}



