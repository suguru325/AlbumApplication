package com.example.yamashita.albumapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final int REQUEST_PERMISSION = 1000;
    final private static String TAG = "MainActivity";
    private int mResourceId;
    private List<Long> mIdList;

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
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setNumColumns(3);

        mIdList = load();
        gridView.setAdapter(new BitmapAdapter(this, mIdList));
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplication(), SubActivity.class);
        intent.putExtra("IMAGEID", mIdList.get(position));
        startActivity(intent);
    }

    private List<Long> load() {
        mIdList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor c = cr.query(uri, null, null, null, null);
        c.moveToFirst();

        for (int i=0; i<c.getCount(); i++) {
            long id = c.getLong(c.getColumnIndexOrThrow("_id"));

            mIdList.add(id);
            c.moveToNext();
        }
        return mIdList;
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
