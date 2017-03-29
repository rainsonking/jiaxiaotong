package com.example.bailyhome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bailyhome.adapter.LeaveRecBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;
import com.example.bailyhome.config.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class CourseTableLeaveActivity extends BaseActivity {
    private TextView text_header, tv_className,tv_teacher,tv_appoit_date,tv_class_time,
            tv_appoit_hour,tv_school,tv_attend_status,tv_leave_type,tv_leave_cause,tv_leave_memo;
//    private ImageView menu_img;
    private Map<String,Object> map;
    private LinearLayout layout_right,layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table_leave);
        Intent intent = getIntent();
        if (intent!=null) {
            Bundle bundle = intent.getExtras();
            SerializableMap serializableMap=(SerializableMap)bundle.getSerializable("courseData");
            if (serializableMap!=null) {
                map=serializableMap.getMap();
            }
        }

        initView();
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseTableLeaveActivity.this.finish();
            }
        });
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
        layout_right = (LinearLayout) findViewById(R.id.layout_right);
        layout_right.setVisibility(View.VISIBLE);
//        layout_right.setImageResource(R.mipmap.leave_top_right);
        layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CourseTableLeaveActivity.this,LeaveRecActivity.class);
                intent.putExtra("USERID", getLoginUserSharedPre().getString("USERID",""));
                startActivity(intent);
            }
        });
        text_header = (TextView) findViewById(R.id.text_header);
        tv_className = (TextView) findViewById(R.id.tv_className);
        tv_teacher = (TextView) findViewById(R.id.tv_teacher);
        tv_appoit_date = (TextView) findViewById(R.id.tv_appoit_date);
        tv_class_time = (TextView) findViewById(R.id.tv_class_time);
        tv_appoit_hour = (TextView) findViewById(R.id.tv_appoit_hour);
        tv_school = (TextView) findViewById(R.id.tv_school);
        tv_attend_status = (TextView) findViewById(R.id.tv_attend_status);
        tv_leave_type = (TextView) findViewById(R.id.tv_leave_type);
        tv_leave_cause = (TextView) findViewById(R.id.tv_leave_cause);
        tv_leave_memo = (TextView) findViewById(R.id.tv_leave_memo);

        if (map!=null) {
            String className=map.get("AFM_14")+"";
            String teacher=map.get("AFM_15")+"";
            String appoit_date=map.get("AFM_5")+"";
            String class_time=map.get("AFM_18")+"";
            String appoit_hour=map.get("AFM_4")+"";
            String school=map.get("AFM_16")+"";
            String attend_status=map.get("DIC_AFM_12")+"";
            String leave_type=map.get("DIC_AFM_7")+"";
            String leave_cause=map.get("AFM_9")+"";
            String leave_memo=map.get("AFM_8")+"";

            tv_className.setText(!className.equals("null")?className:"");
            text_header.setText("请假详情");
            tv_teacher.setText(!teacher.equals("null")?teacher:"");
            tv_appoit_date.setText(!appoit_date.equals("null")?appoit_date.substring(0,10):"");
            tv_class_time.setText(!class_time.equals("null")?class_time:"");
            tv_appoit_hour.setText(!appoit_hour.equals("null")?appoit_hour:"");
            tv_school.setText(!school.equals("null")?school:"");
            tv_attend_status.setText(!attend_status.equals("null")?attend_status:"");
            tv_leave_type.setText(!leave_type.equals("null")?leave_type:"");
            tv_leave_cause.setText(!leave_cause.equals("null")?leave_cause:"");
            tv_leave_memo.setText(!leave_memo.equals("null")?leave_memo:"");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
