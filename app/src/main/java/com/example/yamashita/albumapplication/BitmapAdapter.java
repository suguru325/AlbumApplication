package com.example.yamashita.albumapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public class BitmapAdapter extends BaseAdapter {

    private List<Long> mIdList;
    private LayoutInflater mInflater;
    private Context mContext;

    static class ViewHolder {
        CustomImageView civ_image;
    }

    public BitmapAdapter(Context context, List<Long> uriList) {
        mIdList = uriList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        convertView = mInflater.inflate(R.layout.list_item, null);
        CustomImageView customImageView = (CustomImageView) convertView.findViewById(R.id.customImageView);
        customImageView.setTag(mIdList.get(position));
        new ImageTask(mContext, customImageView, mIdList.get(position)).execute();
        return convertView;

//        if(convertView == null) {
//            convertView = mInflater.inflate(R.layout.list_item, null);
//
//            holder = new ViewHolder();
//            holder.civ_image = (CustomImageView) convertView.findViewById(R.id.customImageView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.civ_image.setTag(mIdList.get(position));
//        new ImageTask(mContext, holder.civ_image, mIdList.get(position)).execute();
//        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mIdList.size();
    }
}



