package com.example.yamashita.albumapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;


public class ImageTask {
    private CustomImageView mCustomImageView;
    private long mId;
    private Bitmap mBitmap;
    private Context mContext;

    public ImageTask(Context context, CustomImageView customImageView, long id) {
        mCustomImageView = customImageView;
        mId = id;
        mContext = context;

    }

    public void execute() {
        final Handler mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = getBitmapThumbnail(mId);
                if(mBitmap == null) {
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mId == (long) mCustomImageView.getTag()) {
                            mCustomImageView.setImageBitmap(mBitmap);
                        }
                    }
                });
            }
        }).start();
    }

    public Bitmap getBitmapThumbnail(long id) {
        ContentResolver cr = mContext.getContentResolver();
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
        return bitmap;
    }
}
