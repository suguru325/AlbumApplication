package com.example.yamashita.albumapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

public class BitmapAdapter extends ArrayAdapter<Bitmap> {
    private int resourceId;
    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    final private static String TAG = "BitmapAdapter";


    public BitmapAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;

        WindowManager wm = (WindowManager)mContext.getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();

        Point size = new Point();
        disp.getSize(size);

        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }
        CustomImageView view = (CustomImageView) convertView;
        view.setImageBitmap(getItem(position));
        view.setMinimumHeight(mScreenWidth/3);
        view.setMinimumWidth(mScreenWidth/3);
        view.setMaxHeight(mScreenWidth/3);
        view.setMaxWidth(mScreenWidth/3);
        Log.d(TAG, String.valueOf(position));
        return view;
    }
}



