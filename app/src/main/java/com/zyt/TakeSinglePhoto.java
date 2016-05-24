package com.zyt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 拍单张照片
 * Created by Administrator on 2016/5/24.
 */
public class TakeSinglePhoto extends AppCompatActivity {
    Button btnTakePicture;
    ImageView imgPreview;
    private Uri imageUri = null;
    private static final int TAKE_A_PICTURE = 123;
    String dir = Environment.getExternalStorageDirectory().toString() + File.separator + "imgtest";
    String srcName = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_single_photo);
        btnTakePicture = (Button) findViewById(R.id.btn_take);
        imgPreview = (ImageView) findViewById(R.id.img_preview);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = new DateFormat().format("yyyyMMdd_hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + ".jpg";
                File f = new File(dir);
                if (!f.exists()) {
                    f.mkdirs();
                }
                srcName = dir + File.separator + name;
                imageUri = Uri.parse("file:///" + srcName);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_A_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_A_PICTURE:
                   Bitmap bitmap = null;
                    try {
                       bitmap = BitmapUtils.getBitmapFormUri(this, imageUri, imgPreview);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgPreview.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}
