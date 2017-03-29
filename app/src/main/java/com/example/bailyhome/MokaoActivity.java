package com.example.bailyhome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
public class MokaoActivity extends BaseActivity {
    private TextView tv_course_name, tv_kaoti_version, tv_mokao_date, tv_mokao_start_date,tv_mokao_end_date
    ,tv_jiqi,tv_xiaoqu,tv_mokao_state,text_header;
    private RelativeLayout layout_teach_note;
    private LinearLayout layout_mokao_state,layout_title_left;
    //private ImageView menu_img;
    private Map<String,Object> map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mokao);
        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle=intent.getExtras();
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
        tv_kaoti_version = (TextView) findViewById(R.id.tv_kaoti_version);
        tv_mokao_date = (TextView) findViewById(R.id.tv_mokao_date);
        tv_mokao_start_date = (TextView) findViewById(R.id.tv_mokao_start_date);
        tv_mokao_end_date = (TextView) findViewById(R.id.tv_mokao_end_date);
        tv_jiqi = (TextView) findViewById(R.id.tv_jiqi);
        tv_xiaoqu = (TextView) findViewById(R.id.tv_xiaoqu);
        tv_mokao_state = (TextView) findViewById(R.id.tv_mokao_state);
//        layout_teach_note = (RelativeLayout) findViewById(R.id.layout_teach_note);
        layout_mokao_state = (LinearLayout) findViewById(R.id.layout_mokao_state);

        if (map!=null){
            String course_name=(String) map.get("AFM_47");
            String kaoti_version=(String) map.get("DIC_AFM_51");
            String mokao_date=(String) map.get("AFM_3");
            String mokao_start_date=(String) map.get("AFM_7");
            String mokao_end_date=(String) map.get("AFM_8");
            String jiqi=(String) map.get("AFM_53");
            String xiaoqu=(String) map.get("AFM_50");
            tv_course_name.setText(!TextUtils.isEmpty(course_name)?course_name:"");
            text_header.setText(!TextUtils.isEmpty(course_name)?course_name:"课程详情");
            tv_kaoti_version.setText(!TextUtils.isEmpty(kaoti_version)?kaoti_version:"");
            tv_mokao_date.setText(!TextUtils.isEmpty(mokao_date)?mokao_date.substring(0,10):"");
            tv_mokao_start_date.setText(!TextUtils.isEmpty(mokao_start_date)?(mokao_start_date.substring(11,16)):"");
            tv_mokao_end_date.setText(!TextUtils.isEmpty(mokao_end_date)?(mokao_end_date.substring(11,16)):"");
            tv_jiqi.setText(!TextUtils.isEmpty(jiqi)?jiqi:"");
            tv_xiaoqu.setText(!TextUtils.isEmpty(xiaoqu)?xiaoqu:"");
            String peixunState=(String) map.get("DIC_AFM_11");
            tv_mokao_state.setText(!TextUtils.isEmpty(peixunState)?peixunState:"");
//            if (!TextUtils.isEmpty(peixunState)&&!peixunState.equals("已培训")){
////                layout_teach_note.setVisibility(View.INVISIBLE);
//                String mokao_state=(String) map.get("DIC_AFM_11");
//                tv_mokao_state.setText(!TextUtils.isEmpty(mokao_state)?mokao_state:"");
//            }else {
//                layout_mokao_state.setVisibility(View.GONE);
////                layout_teach_note.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent=new Intent(MokaoActivity.this,TeachLogActivity.class);
////                        String testResult=(String) map.get("AFM_54");
////                        String mockFeedback=(String) map.get("AFM_55");
////                        intent.putExtra("testResult",!TextUtils.isEmpty(testResult)?testResult:"");
////                        intent.putExtra("mockFeedback",!TextUtils.isEmpty(mockFeedback)?mockFeedback:"");
////                        startActivity(intent);
////                    }
////                });
//            }
        }
    }
}
