package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 随堂作业界面
 */
public class ClassWorkActivity2 extends BaseActivity {
    private TextView tv_last_is_commit, work_finish_rate, work_score, tv_plan_finish,
            tv_plan_pigai, tv_home_work_remark, text_header;
    private ImageView  menuImgRight;
    private Map<String, Object> map;
    private LinearLayout layout_title_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwork2);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableMap serializableMap = (SerializableMap) bundle.getSerializable("courseData");
                map = serializableMap.getMap();
            }
        }
        initView();

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
        text_header.setText("随堂作业");
        tv_last_is_commit = (TextView) findViewById(R.id.tv_last_is_commit);
        work_finish_rate = (TextView) findViewById(R.id.work_finish_rate);
        work_score = (TextView) findViewById(R.id.work_score);
        tv_plan_finish = (TextView) findViewById(R.id.tv_plan_finish);
        tv_plan_pigai = (TextView) findViewById(R.id.tv_plan_pigai);
        tv_home_work_remark = (TextView) findViewById(R.id.tv_home_work_remark);

        if (map != null) {
            String last_is_commit = map.get("DIC_AFM_67") + "";
            String work_finish_rate_str = map.get("AFM_66") + "";
            String work_score_str = map.get("AFM_68") + "";
            String plan_finish = map.get("AFM_69") + "";
            String plan_pigai = map.get("AFM_70") + "";
            String home_work_remark = map.get("AFM_72") + "";
            tv_last_is_commit.setText(!last_is_commit.equals("null") ? last_is_commit : "否");
            work_finish_rate.setText(!work_finish_rate_str.equals("null") ? (work_finish_rate_str + "%") : "");
            work_score.setText(!work_score_str.equals("null") ? (work_score_str + "分") : "0");
            tv_plan_finish.setText(!plan_finish.equals("null") ? plan_finish.substring(0, 10) : "");
            tv_plan_pigai.setText(!plan_pigai.equals("null") ? plan_pigai.substring(0, 10) : "");
            tv_home_work_remark.setText(!home_work_remark.equals("null") ? home_work_remark : "");
        }
    }
}
