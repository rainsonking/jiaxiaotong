package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/16 0016.
 *
 * 随堂作业界面
 */
public class ZhujiaoYiPeixun extends BaseActivity {
    private TextView tv_course_name, tv_teacher, tv_order_date, tv_class_date,tv_dudao_time,
            tv_xiaoqu,tv_peixun_state,text_header,tv_order_date_left;
    private ImageView menuImgRight;
    private RelativeLayout layout_teach_note;
    private Map<String,Object> map;
    private Bundle bundle;
    private LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail2);
        Intent intent=getIntent();
        if (intent!=null){
            bundle=intent.getExtras();
            if (bundle!=null){
                SerializableMap serializableMap=(SerializableMap) bundle.getSerializable("courseData");
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
        tv_course_name = (TextView) findViewById(R.id.tv_course_name);
        text_header = (TextView) findViewById(R.id.text_header);
        tv_teacher = (TextView) findViewById(R.id.tv_teacher);
        tv_order_date = (TextView) findViewById(R.id.tv_order_date);
        tv_class_date = (TextView) findViewById(R.id.tv_class_date);
        tv_dudao_time = (TextView) findViewById(R.id.tv_dudao_time);
        tv_xiaoqu = (TextView) findViewById(R.id.tv_xiaoqu);
        tv_peixun_state = (TextView) findViewById(R.id.tv_peixun_state);
        tv_order_date_left = (TextView) findViewById(R.id.tv_order_date_left);

        layout_teach_note=(RelativeLayout)findViewById(R.id.layout_teach_note);

        if (map != null) {
            String peixunState=map.get("DIC_AFM_11")+"";
            String course_name=map.get("AFM_47")+"";
            String teacher=map.get("AFM_48")+"";
            String order_date=map.get("AFM_3")+"";
            String class_date=map.get("AFM_49")+"";
            String dudao_time=map.get("AFM_57")+"";
            String xiaoqu=map.get("AFM_50")+"";
            tv_course_name.setText(!course_name.equals("null")?course_name:"");
            text_header.setText(!course_name.equals("null")?course_name:"课程详情");
            tv_teacher.setText(!teacher.equals("null")?teacher:"");
            tv_order_date.setText(!order_date.equals("null")?order_date.substring(0,10):"");
            tv_class_date.setText(!class_date.equals("null")?class_date:"");
            tv_xiaoqu.setText(!xiaoqu.equals("null")?xiaoqu:"");
            tv_peixun_state.setText(!peixunState.equals("null")?peixunState:"");

            if (!peixunState.equals("null") && peixunState.equals("已培训")) {

                tv_order_date_left.setText("上课日期");
                tv_dudao_time.setText(!dudao_time.equals("null")?dudao_time:"");
                layout_teach_note.setVisibility(View.VISIBLE);

                layout_teach_note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ZhujiaoYiPeixun.this, ZhujiaoTeachLogActivity.class);
                        SerializableMap serializableMap = new SerializableMap();
                        serializableMap.setMap(map);
                        intent.putExtra("SerializableMap", serializableMap);
                        startActivity(intent);
                    }
                });
            } else {
                tv_dudao_time.setText("1");
            }
        }


    }
}
