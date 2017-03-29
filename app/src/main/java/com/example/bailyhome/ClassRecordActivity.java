package com.example.bailyhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

public class ClassRecordActivity extends BaseActivity {

    TextView text_header, tv_course_test_score, tv_home_work, tv_commit_home_work_date, tv_finish_percent,
            tv_home_work_score;
    Map<String, Object> map;
    ImageView menu_img;
    public MyApplication myApplication;
    public LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_record);
        Intent intent = getIntent();
        if (intent != null) {
            SerializableMap serializableMap = (SerializableMap) intent.getSerializableExtra("SerializableMap");
            if (serializableMap != null) {
                map = serializableMap.getMap();
            }
        }
        initView();
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
        if (map != null) {
            String firstTestScore = (String) map.get("AFM_68");
            tv_course_test_score.setText(!TextUtils.isEmpty(firstTestScore) ? (firstTestScore + "分") : "");
            String suitangHomeWork = (String) map.get("DIC_AFM_69");
            tv_home_work.setText(!TextUtils.isEmpty(suitangHomeWork) ? suitangHomeWork : "");
            String commitHomeWorkDate = (String) map.get("AFM_87");
            tv_commit_home_work_date.setText(!TextUtils.isEmpty(commitHomeWorkDate) ? commitHomeWorkDate : "");
            String finishPercent = (String) map.get("AFM_72");
            tv_finish_percent.setText(!TextUtils.isEmpty(finishPercent) ? (finishPercent+"%") : "");
            String homeWorkScore = (String) map.get("AFM_74");
            tv_home_work_score.setText(!TextUtils.isEmpty(homeWorkScore) ? (homeWorkScore + "分") : "");
        }
    }

    @Override
    public void initView() {
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_header = (TextView) findViewById(R.id.text_header);
        text_header.setText("课堂记录");
        tv_course_test_score = (TextView) findViewById(R.id.tv_course_test_score);
        tv_home_work = (TextView) findViewById(R.id.tv_home_work);
        tv_commit_home_work_date = (TextView) findViewById(R.id.tv_commit_home_work_date);
        tv_finish_percent = (TextView) findViewById(R.id.tv_finish_percent);
        tv_home_work_score = (TextView) findViewById(R.id.tv_home_work_score);
    }

}
