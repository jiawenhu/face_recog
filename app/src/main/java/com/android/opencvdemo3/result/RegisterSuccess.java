package com.android.opencvdemo3.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.opencvdemo3.activity.BaseActivity;
import com.android.opencvdemo3.activity.BeginActivity;
import com.test.opencvdemo3.R;

public class RegisterSuccess extends BaseActivity {

    private Button backToBegin;
    // private TextView showLogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_success);

        // 从开始界面转过来
        Intent intent = getIntent();
        if (intent.hasCategory("com.android.opencvdemo3.result.RegisterSuccess.FROM_BEGIN")) {

            Log.d("RegisterSuccess", "Test from BeginActivity");

        }

        // 从登记界面转过来
        else if (intent.hasCategory("com.android.opencvdemo3.result.RegisterSuccess.FROM_REGISTER")) {
            /*// 测试注册码log_id是否出现
            String logId = intent.getStringExtra("log_id");
            Log.d("RegisterSuccess", logId);

            showLogId = (TextView) findViewById(R.id.show_log_id);
            showLogId.setText(logId);
            showLogId.setTextSize(40);*/
            Log.d("RegisterSuccess", "Test from RegisterActivity");

        }


        backToBegin = (Button) findViewById(R.id.back_to_begin);
        backToBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuccess.this, BeginActivity.class);
                startActivity(intent);

                /**
                 * 这里应该还需要一个更新数据库的动作？
                 * 或者会在登记的最后一步一并完成
                 */

            }
        });
    }
}
