package com.android.opencvdemo3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.opencvdemo3.R;

import java.io.FileNotFoundException;

public class HelloActivity extends BaseActivity {

    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);

        picture = (ImageView) findViewById(R.id.get_picture);

        Intent intent = getIntent();
        if (intent != null) {
            /**
             * 如果是通过拍照传来的带有Uri的intent
             * 则解析imageUri显示图片
             */
            if (intent.hasCategory("com.android.opencvdemo3.activity.HelloActivity.FOR_TAKEN_PHOTO")) {
                try {
                    Uri imageUri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(imageUri)); // 把Uri对象以位图形式解析出来
                    picture.setImageBitmap(bitmap); //显示跳转后的图片
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 如果是从相册选到的图片传来的带String形式图片路径的intent
             * 则直接解析imagePath显示图片
             */
            else if (intent.hasCategory("com.android.opencvdemo3.activity.HelloActivity.FOR_CHOSEN_PHOTO")){
                String imagePath = intent.getStringExtra("image_Path");

                if (imagePath != null) {
                /**
                * 将照片解析为Bitmap形式展现
                */
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    picture.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(this, "相册选图失败", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            Toast.makeText(this, "Intent传路径失败", Toast.LENGTH_SHORT).show();
        }
    }
}
