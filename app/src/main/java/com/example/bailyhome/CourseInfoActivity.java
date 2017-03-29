package com.example.bailyhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

public class CourseInfoActivity extends BaseActivity {

    TextView text_header,tv_stage_name,tv_course_name,tv_teacher,tv_order_date,tv_class_date,tv_order_hour,
            tv_xiaoqu,tv_peixun_state;
    Map<String,Object> map;
    ImageView menu_img;
    public MyApplication myApplication;
    private LinearLayout  layout_title_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        initView();
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
        Intent intent=getIntent();
        SerializableMap serializableMap=(SerializableMap) intent.getSerializableExtra("SerializableMap");
        map=serializableMap.getMap();
//        Log.e("map-",map.toString());
        if (map!=null) {
            String stage_name=(String)map.get("AFM_62");
            String course_name=(String)map.get("AFM_53");
            String teacher=(String)map.get("AFM_54");
            String order_date=(String)map.get("AFM_3");
            String class_date=(String)map.get("AFM_55");
           // int order_hour=0;
            String order_hourStr="";
            if (map.containsKey("AFM_58")){
                order_hourStr=String.valueOf(map.get("AFM_58"));
            }
//            if (!TextUtils.isEmpty(order_hourStr) && !order_hourStr.equals("null")) {
//                order_hour=Integer.parseInt(order_hourStr);
//            }
            String xiaoqu=(String)map.get("AFM_56");
            String peixun_state=(String)map.get("DIC_AFM_11");
            tv_stage_name.setText(!TextUtils.isEmpty(stage_name)?stage_name:"");
            tv_course_name.setText(!TextUtils.isEmpty(course_name)?course_name:"");
            text_header.setText(course_name);
            tv_teacher.setText(!TextUtils.isEmpty(teacher)?teacher:"");
            tv_order_date.setText(!TextUtils.isEmpty(order_date)?order_date.substring(0,10):"");
            tv_class_date.setText(!TextUtils.isEmpty(class_date)?class_date:"");
           // tv_order_hour.setText(String.valueOf(order_hour));
            tv_order_hour.setText(order_hourStr);
            tv_xiaoqu.setText(!TextUtils.isEmpty(xiaoqu)?xiaoqu:"");
            tv_peixun_state.setText(!TextUtils.isEmpty(peixun_state)?peixun_state:"");
        }
    }

    @Override
    public void initView() {
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        text_header= (TextView) findViewById(R.id.text_header);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_stage_name=(TextView)findViewById(R.id.tv_stage_name);
        tv_course_name=(TextView)findViewById(R.id.tv_course_name);
        tv_teacher=(TextView)findViewById(R.id.tv_teacher);
        tv_order_date=(TextView)findViewById(R.id.tv_order_date);
        tv_class_date=(TextView)findViewById(R.id.tv_class_date);
        tv_order_hour=(TextView)findViewById(R.id.tv_order_hour);
        tv_xiaoqu=(TextView)findViewById(R.id.tv_xiaoqu);
        tv_peixun_state=(TextView)findViewById(R.id.tv_peixun_state);
    }

}
