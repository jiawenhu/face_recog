package com.test.opencvdemo3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class JniActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button showBtn, processBtn;

    static {//加载so库
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        imageView = (ImageView) findViewById(R.id.imageView);
        showBtn = (Button) findViewById(R.id.show);
        showBtn.setOnClickListener(this);
        processBtn = (Button) findViewById(R.id.process);
        processBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == showBtn) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            imageView.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            getEdge(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    //获得Canny边缘
    native void getEdge(Object bitmap);
}
