package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 随堂作业界面
 */
public class ClassWorkActivity extends BaseActivity {
    private TextView textHeader, workFinishTime, workFinishRate, workScore;
    private ImageView menuImg, menuImgRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwork);
        initView();
        textHeader.setText("随堂作业");
        Intent intent = getIntent();
        String finishRate = intent.getStringExtra("finishRate");
        String score = intent.getStringExtra("testResult");
        if ((!"".equals(score)) && (score.length() > 0)) {
            workScore.setText(intent.getStringExtra("testResult") + "分");
        } else {
            workScore.setText("无");
        }
        if ((!"".equals(finishRate)) && (finishRate.length() > 0)) {
            workFinishRate.setText(intent.getStringExtra("finishRate") + "%");
        } else {
            workFinishRate.setText("无");
        }
        workFinishTime.setText(intent.getStringExtra("classDate"));
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
        // menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        workFinishTime = (TextView) findViewById(R.id.work_finish_time);
        workFinishRate = (TextView) findViewById(R.id.work_finish_rate);
        workScore = (TextView) findViewById(R.id.work_score);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
