package com.example.yamashita.albumapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 1000;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        } else {
            Gallery();
        }
    }

    private void Gallery() {
        ArrayList<Bitmap> list = load();
        BitmapAdapter adapter = new BitmapAdapter(getApplicationContext(), R.layout.list_item, list);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
    }

    private ArrayList<Bitmap> load() {
        ArrayList<Bitmap> list = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor c = cr.query(uri, null, null, null, null);
        c.moveToFirst();
        for (int i=0; i<c.getCount(); i++) {
            long id = c.getLong(c.getColumnIndexOrThrow("_id"));
            Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            list.add(bmp);
            c.moveToNext();
        }
        return list;
    }


    // permission関連の処理
    private  void checkPermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED) {
            requestExternalStoRagePermission();
        } else  {
            Gallery();
        }
    }
    private  void requestExternalStoRagePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Gallery();
            } else {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getSupportFragmentManager(), "sample");
            }
        }
    }
}
