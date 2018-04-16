package com.android.opencvdemo3.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.test.opencvdemo3.R;

public class HelloActivity extends BaseActivity {

    private ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);

        picture = (ImageView) findViewById(R.id.get_picture) ;

        /*Intent intent=getIntent();
        if(intent !=null)
        {
            byte [] bis=intent.getByteArrayExtra("bitmap");
            //重新生成
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get picture", Toast.LENGTH_SHORT).show();
        }*/

    }
}
