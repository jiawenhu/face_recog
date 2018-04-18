package com.android.opencvdemo3.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.opencvdemo3.activity.BeginActivity;
import com.test.opencvdemo3.R;

public class RecognizeSuccess extends AppCompatActivity {

    private Button backToBegin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognize_success);

        backToBegin = (Button) findViewById(R.id.back_to_begin);
        backToBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizeSuccess.this, BeginActivity.class);
                startActivity(intent);

                /**
                 * 这里应该还需要一个更新数据库的动作？
                 * 或者会在登记的最后一步一并完成
                 */
            }
        });
    }
}
