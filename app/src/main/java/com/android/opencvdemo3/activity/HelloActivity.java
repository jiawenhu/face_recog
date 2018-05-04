package com.android.opencvdemo3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.opencvdemo3.R;

import java.io.ByteArrayOutputStream;

public class HelloActivity extends BaseActivity {

    private ImageView picture;

    private Button registerBtn, recognizeBtn;

    private byte[] bytes;

    private String imagePth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);

        picture = (ImageView) findViewById(R.id.get_picture);

        // 功能操作按钮
        registerBtn = (Button) findViewById(R.id.register);
        recognizeBtn = (Button) findViewById(R.id.recognize);

        Intent intent = getIntent();
        if (intent != null) {
            /**
             * 如果是通过拍照传来的带有byte数组的intent
             * 则转byte数组为bitmap显示图片
             */
            if (intent.hasCategory("com.android.opencvdemo3.activity.HelloActivity.FOR_TAKEN_PHOTO")) {
                bytes = intent.getByteArrayExtra("imageBytes");
                // 转byte数组为bitmap，显示
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                picture.setImageBitmap(bitmap);
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
                    picture.setImageBitmap(bitmap); // 显示图片
                    bytes = bitmap2Bytes(bitmap); // 转bitmap为byte数组，以便Intent传递
                    imagePth = imagePath;

                } else {
                    Toast.makeText(this, "相册选图失败", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            Toast.makeText(this, "Intent传路径失败", Toast.LENGTH_SHORT).show();
        }

        // 人脸检测并，登记人脸信息
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelloActivity.this, DetectActivity.class);
                intent.putExtra("wait_picture", bytes); // 带着待处理图片进入人脸检测活动
                intent.putExtra("imagePath", imagePth); // 把图片路径继续传送
                startActivity(intent);
            }
        });


        // 比对人脸库，进行人脸识别
        recognizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 【人脸识别】API接入
                 */
            }
        });
    }


    // bitmap转byte数组
    private byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}