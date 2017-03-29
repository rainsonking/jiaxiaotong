package com.example.bailyhome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bailyhome.R;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class StagePlanCourseHourView extends RelativeLayout{
    TextView iv_img,tv_course_name,tv_plan_hour;

    public StagePlanCourseHourView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.stage_plan_course_hour, this);
        iv_img=(TextView)findViewById(R.id.iv_img);
        tv_course_name=(TextView)findViewById(R.id.tv_course_name);
        tv_plan_hour=(TextView)findViewById(R.id.tv_plan_hour);
    }

    public void setImgText(String text){
        iv_img.setText(text);
    }

    public void setCourseName(String name){
        tv_course_name.setText(name);
    }

    public void setPlanHour(String hour){
        tv_plan_hour.setText(hour);
    }
}
