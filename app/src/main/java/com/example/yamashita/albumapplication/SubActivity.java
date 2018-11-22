package com.example.yamashita.albumapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        long imageId = intent.getLongExtra("IMAGEID", 0);

        ImageView imageView = findViewById(R.id.imageView_Sub);

        ContentResolver cr = getContentResolver();
        Uri bmpUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
        Cursor c = cr.query(bmpUri, null, null, null, null);
        c.moveToFirst();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr, bmpUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }
}
