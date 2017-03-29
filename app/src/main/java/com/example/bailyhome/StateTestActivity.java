package com.example.bailyhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 阶段测评
 */
public class StateTestActivity extends BaseActivity {
    public TextView textHeader, courseName, teacher, publishTime, workFinishRate, correctTime, testScore;
    private ImageView menuImg, menuImgRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_test);
        initView();
        textHeader.setText("阶段测评");
        Intent intent = getIntent();
        courseName.setText(intent.getStringExtra("className"));
        teacher.setText(intent.getStringExtra("teacher"));
        publishTime.setText(intent.getStringExtra("publishDate"));
        workFinishRate.setText(intent.getStringExtra("classDate"));
        correctTime.setText(intent.getStringExtra("correctDate"));
        String score = intent.getStringExtra("testResult");
        if ((!"".equals(score)) && (score.length() > 0)) {
            testScore.setText(intent.getStringExtra("testResult")+"分");
        }else {
            testScore.setText("无");
        }
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        menuImg = (ImageView) findViewById(R.id.menu_img);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        courseName = (TextView) findViewById(R.id.courseName);
        teacher = (TextView) findViewById(R.id.teacher);
        publishTime = (TextView) findViewById(R.id.publish_time);
        workFinishRate = (TextView) findViewById(R.id.work_finish_rate);
        correctTime = (TextView) findViewById(R.id.correct_time);
        testScore = (TextView) findViewById(R.id.test_score);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
