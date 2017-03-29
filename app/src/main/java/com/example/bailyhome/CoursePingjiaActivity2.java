package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

public class CoursePingjiaActivity2 extends BaseActivity {
    private TextView text_header,tv_class_need_hour,tv_is_on_time,tv_teacher_pingjia_stu,tv_this_class_content,
            tv_teacher_to_home,tv_stu_to_class_teach,tv_stu_to_teacher;
    private Map<String,Object> map;
    private ImageView menu_img;
    public MyApplication myApplication;
    public LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_pingjia2);
        Intent intent=getIntent();
        if (intent!=null){
            SerializableMap serializableMap=(SerializableMap) intent.getSerializableExtra("SerializableMap");
            map=serializableMap.getMap();
        }

        initView();
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);

        if (map!=null) {
            String class_need_hour=(String) map.get("AFM_58");
            String is_on_time=(String) map.get("DIC_AFM_79");
            String teacher_pingjia_stu=(String) map.get("DIC_AFM_80");
            String this_class_content=(String) map.get("AFM_81");
            String teacher_to_home=(String) map.get("AFM_82");
            String stu_to_class_teach=(String) map.get("DIC_AFM_83");
            String stu_to_teacher=(String) map.get("AFM_84");
            tv_class_need_hour.setText(!TextUtils.isEmpty(class_need_hour)&&!class_need_hour.equals("null")?class_need_hour:"");
            tv_is_on_time.setText(!TextUtils.isEmpty(is_on_time)&&!is_on_time.equals("null")?is_on_time:"");
            tv_teacher_pingjia_stu.setText(!TextUtils.isEmpty(teacher_pingjia_stu)&&!teacher_pingjia_stu.equals("null")?teacher_pingjia_stu:"");
            tv_this_class_content.setText(!TextUtils.isEmpty(this_class_content)&&!this_class_content.equals("null")?this_class_content:"");
            tv_teacher_to_home.setText(!TextUtils.isEmpty(teacher_to_home)&&!teacher_to_home.equals("null")?teacher_to_home:"");
            tv_stu_to_class_teach.setText(!TextUtils.isEmpty(stu_to_class_teach)&&!stu_to_class_teach.equals("null")?stu_to_class_teach:"");
            tv_stu_to_teacher.setText(!TextUtils.isEmpty(stu_to_teacher)&&!stu_to_teacher.equals("null")?stu_to_teacher:"未评价");
        }
    }

    public void initView() {
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_header = (TextView) findViewById(R.id.text_header);
        text_header.setText("课程评价");
        tv_class_need_hour=(TextView) findViewById(R.id.tv_class_need_hour);
        tv_is_on_time=(TextView) findViewById(R.id.tv_is_on_time);
        tv_teacher_pingjia_stu=(TextView) findViewById(R.id.tv_teacher_pingjia_stu);
        tv_this_class_content=(TextView) findViewById(R.id.tv_this_class_content);
        tv_teacher_to_home=(TextView) findViewById(R.id.tv_teacher_to_home);
        tv_stu_to_class_teach=(TextView) findViewById(R.id.tv_stu_to_class_teach);
        tv_stu_to_teacher=(TextView) findViewById(R.id.tv_stu_to_teacher);
    }
}
