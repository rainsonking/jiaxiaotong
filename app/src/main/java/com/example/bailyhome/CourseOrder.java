package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
public class CourseOrder extends BaseActivity {
    private TextView tv_course_name, tv_teacher, tv_order_date, tv_class_date,
    tv_order_hour,tv_xiaoqu,tv_peixun_state,tv_course_test,tv_home_work,
            tv_course_pingjia,tv_stage_name,text_header,tv_order_date_left;
    private ImageView iv_next_page1, iv_next_page2,iv_next_page3;
    private RelativeLayout layout_course_first_test,layout_course_pingjia,layout_home_work;
    private Map<String,Object> map;
    private Bundle bundle;
    private LinearLayout layout_title_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail1);
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
        tv_stage_name=(TextView) findViewById(R.id.tv_stage_name);
        text_header=(TextView) findViewById(R.id.text_header);
        tv_course_name=(TextView) findViewById(R.id.tv_course_name);
        tv_teacher=(TextView) findViewById(R.id.tv_teacher);
        tv_order_date=(TextView) findViewById(R.id.tv_order_date);
        tv_class_date=(TextView) findViewById(R.id.tv_class_date);
        tv_order_hour=(TextView) findViewById(R.id.tv_order_hour);
        tv_xiaoqu=(TextView) findViewById(R.id.tv_xiaoqu);
        tv_peixun_state=(TextView) findViewById(R.id.tv_peixun_state);
        tv_course_test=(TextView) findViewById(R.id.tv_course_test);
        tv_home_work=(TextView) findViewById(R.id.tv_home_work);
        tv_course_pingjia=(TextView) findViewById(R.id.tv_course_pingjia);
        tv_order_date_left=(TextView) findViewById(R.id.tv_order_date_left);

        iv_next_page1=(ImageView) findViewById(R.id.iv_next_page1);
        iv_next_page2=(ImageView) findViewById(R.id.iv_next_page2);
        iv_next_page3=(ImageView) findViewById(R.id.iv_next_page3);

        if (map != null) {
            String stage_name=(String) map.get("AFM_56");
            String course_name=(String) map.get("AFM_47");
            String teacher=(String)map.get("AFM_48");
            String order_date=(String)map.get("AFM_3");
            String class_date=(String)map.get("AFM_49");
            String order_hour=map.get("AFM_10")+"";
            String xiaoqu=(String)map.get("AFM_50");
            String home_work=(String)map.get("DIC_AFM_63");
            String course_pingjia=(String)map.get("DIC_AFM_64");
            tv_stage_name.setText(!TextUtils.isEmpty(stage_name)?stage_name:"");
            tv_course_name.setText(!TextUtils.isEmpty(course_name)?course_name:"课程详情");
            text_header.setText(!TextUtils.isEmpty(course_name)?course_name:"");
            tv_teacher.setText(!TextUtils.isEmpty(teacher)?teacher:"");
            tv_order_date.setText(!TextUtils.isEmpty(order_date)?order_date.substring(0,10):"");
            tv_class_date.setText(!TextUtils.isEmpty(class_date)?class_date:"");
            tv_order_hour.setText(!order_hour.equals("null")?order_hour:"2");
            tv_xiaoqu.setText(!TextUtils.isEmpty(xiaoqu)?xiaoqu:"");
            tv_home_work.setText(!TextUtils.isEmpty(home_work)?home_work:"未布置");
            tv_course_pingjia.setText(!TextUtils.isEmpty(course_pingjia)?course_pingjia:"未评价");
            String peixunState=(String) map.get("DIC_AFM_11");
            if (!TextUtils.isEmpty(peixunState)&&peixunState.equals("已培训")) {
                tv_order_date_left.setText("上课日期");
                String thisTimeHour=map.get("AFM_52")+"";
                tv_order_hour.setText(!thisTimeHour.equals("null")?thisTimeHour:"2");
            }
            tv_peixun_state.setText(!TextUtils.isEmpty(peixunState)?peixunState:"");

            String course_test_str=map.get("AFM_62")+"";
            tv_course_test.setText(!course_test_str.equals("null")?course_test_str+"分":"未测试");

            String last_is_commit=map.get("DIC_AFM_67")+"";
            String work_finish_rate_str=map.get("AFM_66")+"";
            String work_score_str=map.get("AFM_68")+"";
            String plan_finish=map.get("AFM_69")+"";
            String plan_pigai=map.get("AFM_70")+"";
            String home_work_remark=map.get("AFM_72")+"";

            if (!last_is_commit.equals("null")||!work_finish_rate_str.equals("null")||
                    !work_score_str.equals("null")||!plan_finish.equals("null")||
                    !plan_pigai.equals("null")||!home_work_remark.equals("null")) {

                tv_home_work.setTextColor(getResources().getColor(R.color.yibuzhi));
                iv_next_page2.setVisibility(View.VISIBLE);
                layout_home_work=(RelativeLayout)findViewById(R.id.layout_home_work);
                layout_home_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CourseOrder.this,ClassWorkActivity2.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }

            String class_need_hour=map.get("AFM_52")+"";
            String is_on_time=map.get("DIC_AFM_73")+"";
            String teacher_pingjia_stu=map.get("DIC_AFM_74")+"";
            String this_class_content=map.get("AFM_75")+"";
            String teacher_to_home=map.get("AFM_76")+"";
            String stu_to_class_teach=map.get("DIC_AFM_77")+"";
            String stu_to_teacher=map.get("AFM_78")+"";

            if (!class_need_hour.equals("null")||!is_on_time.equals("null")||
                    !teacher_pingjia_stu.equals("null")||!this_class_content.equals("null")||
                    !teacher_to_home.equals("null")||!stu_to_class_teach.equals("null")||
                    !stu_to_teacher.equals("null")) {

                tv_course_pingjia.setTextColor(getResources().getColor(R.color.yibuzhi));
                iv_next_page3.setVisibility(View.VISIBLE);
                layout_course_pingjia=(RelativeLayout)findViewById(R.id.layout_course_pingjia);
                layout_course_pingjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CourseOrder.this,CoursePingjiaActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }

    }
}
