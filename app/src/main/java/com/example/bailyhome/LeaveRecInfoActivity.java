package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/5/18 0018.
 * <p/>
 * 请假详情页
 */
public class LeaveRecInfoActivity extends BaseActivity {
    private TextView textHeader;
    private ImageView menuImg, menuImgRight;
    private TextView tvClassName, tvTeacher, tvAppoitDate, tvClassTime, tvAppoitHour, tvSchool, tvAttendStatus, tvLeaveType, tvLeaveCause, tvLeaveMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_record_info);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        String title = bundle.getString("className");
        textHeader.setText("请假详情");
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvClassName.setText(bundle.getString("className"));
        tvTeacher.setText(bundle.getString("teacher"));
        tvAppoitDate.setText(bundle.getString("orderDate"));
        tvClassTime.setText(bundle.getString("classTime"));
        tvAppoitHour.setText(bundle.getString("orderHour"));
        tvSchool.setText(bundle.getString("school"));
        tvAttendStatus.setText(bundle.getString("leaveState"));
        tvLeaveType.setText(bundle.getString("leaveType"));
        tvLeaveCause.setText(bundle.getString("leaveCause"));
        tvLeaveMemo.setText(bundle.getString("leaveDemo"));
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        menuImg = (ImageView) findViewById(R.id.menu_img);
        tvClassName = (TextView) findViewById(R.id.tv_className);
        tvTeacher = (TextView) findViewById(R.id.tv_teacher);
        tvAppoitDate = (TextView) findViewById(R.id.tv_appoit_date);
        tvClassTime = (TextView) findViewById(R.id.tv_class_time);
        tvAppoitHour = (TextView) findViewById(R.id.tv_appoit_hour);
        tvSchool = (TextView) findViewById(R.id.tv_school);
        tvAttendStatus = (TextView) findViewById(R.id.tv_attend_status);
        tvLeaveType = (TextView) findViewById(R.id.tv_leave_type);
        tvLeaveCause = (TextView) findViewById(R.id.tv_leave_cause);
        tvLeaveMemo = (TextView) findViewById(R.id.tv_leave_memo);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
