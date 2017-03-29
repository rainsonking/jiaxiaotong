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
import com.example.bailyhome.bean.MyGridBean;
import com.example.bailyhome.bean.SerializableMap;

import java.util.Map;

public class CoursePingjiaActivity extends BaseActivity {
    private TextView tv_class_need_hour,tv_is_on_time,tv_teacher_pingjia_stu,tv_this_class_content,
            tv_teacher_to_home,tv_stu_to_class_teach,tv_stu_to_teacher,text_header;
    private Map<String,Object> map;
    //private ImageView menu_img;
    public MyApplication myApplication;
    public LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_pingjia);
        Intent intent=getIntent();
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
        if (intent!=null){
            Bundle bundle=intent.getExtras();
            if (bundle!=null){
                SerializableMap serializableMap=(SerializableMap) bundle.getSerializable("courseData");
                map=serializableMap.getMap();
            }
        }

        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_header=(TextView) findViewById(R.id.text_header);
        text_header.setText("课程评价");
        tv_class_need_hour=(TextView) findViewById(R.id.tv_class_need_hour);
        tv_is_on_time=(TextView) findViewById(R.id.tv_is_on_time);
        tv_teacher_pingjia_stu=(TextView) findViewById(R.id.tv_teacher_pingjia_stu);
        tv_this_class_content=(TextView) findViewById(R.id.tv_this_class_content);
        tv_teacher_to_home=(TextView) findViewById(R.id.tv_teacher_to_home);
        tv_stu_to_class_teach=(TextView) findViewById(R.id.tv_stu_to_class_teach);
        tv_stu_to_teacher=(TextView) findViewById(R.id.tv_stu_to_teacher);

        if (map!=null) {
            String class_need_hour=map.get("AFM_52")+"";
            String is_on_time=map.get("DIC_AFM_73")+"";
            String teacher_pingjia_stu=map.get("DIC_AFM_74")+"";
            String this_class_content=map.get("AFM_75")+"";
            String teacher_to_home=map.get("AFM_76")+"";
            String stu_to_class_teach=map.get("DIC_AFM_77")+"";
            String stu_to_teacher=map.get("AFM_78")+"";

            tv_class_need_hour.setText(!class_need_hour.equals("null")?class_need_hour:"");
            tv_is_on_time.setText(!is_on_time.equals("null")?is_on_time:"");
            tv_teacher_pingjia_stu.setText(!teacher_pingjia_stu.equals("null")?teacher_pingjia_stu:"");
            tv_this_class_content.setText(!this_class_content.equals("null")?this_class_content:"");
            tv_teacher_to_home.setText(!teacher_to_home.equals("null")?teacher_to_home:"");
            tv_stu_to_class_teach.setText(!stu_to_class_teach.equals("null")?stu_to_class_teach:"");
            tv_stu_to_teacher.setText(!stu_to_teacher.equals("null")?stu_to_teacher:"未评价");
        }
    }

    @Override
    public void initView() {

    }
}
