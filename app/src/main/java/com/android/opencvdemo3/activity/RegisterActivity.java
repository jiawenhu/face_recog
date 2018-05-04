package com.android.opencvdemo3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.opencvdemo3.face.faceRegister;
import com.test.opencvdemo3.R;

public class RegisterActivity extends BaseActivity {

    private String TAG = "RegisterActivity";

    private EditText getId, getInfo;
    private Button enterBtn;

    private String facePath, faceId, faceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        getId = (EditText) findViewById(R.id.face_id);
        getInfo = (EditText) findViewById(R.id.face_info);
        enterBtn = (Button) findViewById(R.id.enter_to_register);

        // 获取Intent里的图片路径
        Intent intent = getIntent();
        facePath = intent.getStringExtra("show_path");

        Log.d(TAG, "Path = " + facePath);


        // 确认键确认注册
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceId = getId.getText().toString();
                faceInfo = getInfo.getText().toString();
                Log.d(TAG, "ID = " + faceId);
                Log.d(TAG, "Info = " + faceInfo);

                // 把这个Id,Info传给人脸注册类, 得到注册结果
                faceRegister.register(facePath, faceId, faceInfo);

                Intent intent1 = new Intent("com.android.opencvdemo3.result.RegisterSuccess.ACTION_START");
                intent1.addCategory("com.android.opencvdemo3.result.RegisterSuccess.FROM_REGISTER");
                startActivity(intent1);

                /*long log_id = parseJSON(result);
                String logId = Long.toString(log_id);

                if (log_id != 0) {
                    Log.d("RegisterActivity", logId);
                    Intent intent1 = new Intent("com.android.opencvdemo3.activity.RegSuccess.ACTION_START");
                    intent1.addCategory("com.android.opencvdemo3.activity.RegSuccess.FROM_REGISTER");
                    intent1.putExtra("log_id", logId);
                    startActivity(intent1);
                } else {
                    Toast.makeText(RegisterActivity.this, "登记失败！", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }
    /**
     *  解析JSON类型的返回值
      */
    /*private static long parseJSON(String result) {
        long re = 0;
        try {
            JSONObject jo = new JSONObject(result);
            re = jo.getLong("log_id");
            // String err = jo.getString("error_code");
            Log.d("RegisterActivity", "注册码为：" + re);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return re;
    }*/
}
