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

    static {//加载so库【这个库是利用java类生成的头文件写成的cpp源文件生成的】
        System.loadLibrary("com_test_opencvdemo3_JniActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);//布局文件是这个

        //活动构建好之后，定义布局文件里，以下控件的意义
        imageView = (ImageView) findViewById(R.id.imageView);

        showBtn = (Button) findViewById(R.id.show);
        showBtn.setOnClickListener(this);//直接在本活动里就有监听器

        processBtn = (Button) findViewById(R.id.process);
        processBtn.setOnClickListener(this);//同上
    }

    @Override
    public void onClick(View v) {
        //如果点击键是show
        if (v == showBtn) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            imageView.setImageBitmap(bitmap);
        }

        //如果点击键不是show，即点击键是process
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
            getEdge(bitmap);//这个方法，在Java类里声明，在C库里定义
            imageView.setImageBitmap(bitmap);
        }
    }

    //获得Canny边缘【用的是本地库中的方法，在这里声明，在本地库（cpp）里定义】
    native void getEdge(Object bitmap);
}
