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
public class ZhujiaoTeachLogActivity extends BaseActivity {
    private TextView tv_dudao_time, tv_dudao_content, tv_is_on_time, tv_dudao_score,
            tv_dudao_feedback,text_header;
    private ImageView menu_img, menuImgRight;
    private Map<String,Object> map;
    private LinearLayout layout_title_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhujiao_teach_log);
        Intent intent=getIntent();
        if (intent!=null){
            SerializableMap serializableMap=(SerializableMap) intent.getSerializableExtra("SerializableMap");
            if (serializableMap!=null){
                map=serializableMap.getMap();
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
        text_header.setText("教学日志");
        tv_dudao_time = (TextView) findViewById(R.id.tv_dudao_time);
        tv_dudao_content = (TextView) findViewById(R.id.tv_dudao_content);
        tv_is_on_time = (TextView) findViewById(R.id.tv_is_on_time);
        tv_dudao_score = (TextView) findViewById(R.id.tv_dudao_score);
        tv_dudao_feedback = (TextView) findViewById(R.id.tv_dudao_feedback);

        if (map!=null) {
            String dudao_time=map.get("AFM_57")+"";
            String dudao_content=map.get("DIC_AFM_58")+"";
            String is_on_time=map.get("DIC_AFM_59")+"";
            String dudao_score=map.get("AFM_60")+"";
            String dudao_feedback=map.get("AFM_61")+"";
            tv_dudao_time.setText(!dudao_time.equals("null")?dudao_time:"暂无数据");
            tv_dudao_content.setText(!dudao_content.equals("null")?dudao_content:"暂无数据");
            tv_is_on_time.setText(!is_on_time.equals("null")?is_on_time:"暂无数据");
            tv_dudao_score.setText(!dudao_score.equals("null")?dudao_score:"暂无数据");
            tv_dudao_feedback.setText(!dudao_feedback.equals("null")?dudao_feedback:"暂无数据");
        }
    }
}
