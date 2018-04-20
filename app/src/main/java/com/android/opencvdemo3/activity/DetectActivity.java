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

/**
 * 用于检测人脸的活动
 * 需要native本地函数
 */
public class DetectActivity extends BaseActivity {

    private ImageView waitPicture;
    private Button originBtn, detectedBtn, comfirmBtn;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        waitPicture = (ImageView) findViewById(R.id.wait_detect);

        originBtn = (Button) findViewById(R.id.original_picture);
        detectedBtn = (Button) findViewById(R.id.detected_picture);
        comfirmBtn = (Button) findViewById(R.id.confirm);

        // 接收HelloActivity传来的待检测图片，存进byte数组
        Intent intent = getIntent();
        if (intent != null) {
            bytes = intent.getByteArrayExtra("wait_picture");
        } else {
            Toast.makeText(this, "Intent传图失败", Toast.LENGTH_SHORT).show();
        }

        // 显示检测前的原图
        originBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 转byte数组为bitmap，显示
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                waitPicture.setImageBitmap(bitmap);
            }
        });


        // 显示检测后的图片
        detectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 点击“人脸检测”就会利用JNI调用OpenCV的人脸检测一系列本地函数
                 * 最后把图片传回来
                 * 再通过ImageView显示
                 */
            }
        });


        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 点击“确认登记”就会请求【人脸注册】API接口功能
                 * 之后再补全
                 */
            }
        });
    }

}
