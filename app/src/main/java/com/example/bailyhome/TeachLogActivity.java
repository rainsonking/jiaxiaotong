package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 教学日志
 */
public class TeachLogActivity extends BaseActivity {
    private TextView textHeader, testFeedback, testScore;
    private ImageView menuImgRight;
    private LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_log);
        initView();
        textHeader.setText("教学日志");
        Intent intent = getIntent();
        if (intent != null) {
            String mockFeedback = intent.getStringExtra("mockFeedback");
            String testResult = intent.getStringExtra("testResult") + "";
            if ((mockFeedback != null) && (mockFeedback.length() > 0)) {
                testFeedback.setText(mockFeedback);
            } else {
                testFeedback.setText("无内容");
            }
            testScore.setText(!testResult.equals("null") ? (testResult + "分") : "");
        }
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        testFeedback = (TextView) findViewById(R.id.test_feedback);
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
