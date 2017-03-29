package com.example.bailyhome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.bailyhome.adapter.MyListViewAdpter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.view.StagePlanCourseHourView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class StagePlanActivity extends BaseActivity {
    private TextView textHeader;
    private ImageView  menuImgRight;
    private TextView tv_stage_name,tvStartDate,tvEndDate,
            tvClassPinlv,tvPlanStageTestTime,tvOrderHour,tvOutTesTime,
            stageTestContent;
    private String stageId;
    private List<Map<String,Object>> mList;
    private List<Map<String,Object>> mTeachContentList;
    private LinearLayout layoutTeachPlan,layout_course_hour,layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_plan);
        Intent intent=getIntent();
        if (intent!=null) {
            stageId=intent.getStringExtra("stageId");
        }
        initView();
        textHeader.setText("阶段计划");
        requestData(Url.baseUrl+Url.stagePlanTop);
    }

    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_stage_name= (TextView) findViewById(R.id.tv_stage_name);
        tvStartDate= (TextView) findViewById(R.id.tv_start_date);
        tvEndDate= (TextView) findViewById(R.id.tv_end_date);
        tvClassPinlv = (TextView) findViewById(R.id.tv_class_pinlv);
        tvPlanStageTestTime = (TextView) findViewById(R.id.tv_plan_stage_test_time);
        tvOrderHour= (TextView) findViewById(R.id.tv_order_hour);
        tvOutTesTime = (TextView) findViewById(R.id.tv_out_test_time);
        stageTestContent= (TextView) findViewById(R.id.stage_test_content);
        layoutTeachPlan= (LinearLayout) findViewById(R.id.layout_teach_plan);
        layout_course_hour= (LinearLayout) findViewById(R.id.layout_course_hour);
    }

    //上半部分数据
    private void requestData(String url){
        getProgressDialog().show();
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_17_searchEleId",stageId)
                .addParams("fielterMainId",getLoginUserSharedPre().getString("USERID",""))
                .build();
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {
                getProgressDialog().dismiss();
            }

            @Override
            public void onResponse(Map<String,Object> response) {
                Log.i("123","response===>"+response);
                if (response!=null){
                    mList=(List<Map<String,Object>>)response.get("rows");
                    if (mList!=null&&mList.size()>0) {
                        String header=(String)mList.get(0).get("AFM_1");
                        String StartDate=(String)mList.get(0).get("AFM_2");
                        String endDate=(String)mList.get(0).get("AFM_3");
                        String courseName=(String)mList.get(0).get("AFM_14");
                        String planHour=(String)mList.get(0).get("AFM_15");
                        int classPinlv=0;
                        String classPinlvStr=String.valueOf(mList.get(0).get("AFM_6"));
                        if (!TextUtils.isEmpty(classPinlvStr)&&!classPinlvStr.equals("null")) {
                            classPinlv=Integer.parseInt(classPinlvStr);
                        }

                        int planStageTestTime=0;
                        String planStageTestTimeStr=String.valueOf(mList.get(0).get("AFM_7"));
                        if (!TextUtils.isEmpty(planStageTestTimeStr)&&!planStageTestTimeStr.equals("null")) {
                            planStageTestTime=Integer.parseInt(planStageTestTimeStr);
                        }

                        int orderHour=0;
                        String orderHourStr=String.valueOf(mList.get(0).get("AFM_8"));
                        if (!TextUtils.isEmpty(orderHourStr)&&!orderHourStr.equals("null")) {
                            orderHour=Integer.parseInt(orderHourStr);
                        }

                        int outTesTime=0;
                        String outTesTimeStr=String.valueOf(mList.get(0).get("AFM_9"));
                        if (!TextUtils.isEmpty(outTesTimeStr)&&!outTesTimeStr.equals("null")) {
                            outTesTime=Integer.parseInt(outTesTimeStr);
                        }

                        String stageTestContentStr=(String)mList.get(0).get("AFM_10");

                        tv_stage_name.setText(!TextUtils.isEmpty(header)?header:"");
                        tvStartDate.setText(!TextUtils.isEmpty(StartDate)?StartDate.substring(0,10):"");
                        tvEndDate.setText(!TextUtils.isEmpty(endDate)?endDate.substring(0,10):"");


                        String buyType=mList.get(0).get("DIC_AFM_18")+"";

                        if (!TextUtils.isEmpty(courseName)&&!buyType.equals(null)&&!TextUtils.isEmpty(planHour)) {
                            String[] buyTypes=buyType.split(",");
                            String[] planHours=planHour.split(",");
                            for (int i=0;i<planHours.length;i++) {
                                StagePlanCourseHourView stagePlanCourseHourView=new StagePlanCourseHourView(StagePlanActivity.this);
                                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                stagePlanCourseHourView.setLayoutParams(layoutParams);
                                if (courseName.contains("一对一")) {
                                   // stagePlanCourseHourView.setImgText("0"+(i+1));
                                    stagePlanCourseHourView.setImgText((i+1)+"");
                                    stagePlanCourseHourView.setCourseName(courseName.replace("一对一",buyTypes[i]));
                                    stagePlanCourseHourView.setPlanHour(planHours[i]+"课时");
                                    layout_course_hour.addView(stagePlanCourseHourView);
                                    if (i < planHours.length-1) {
                                        TextView view=new TextView(StagePlanActivity.this);
                                        int height=dip2px(StagePlanActivity.this,1);
                                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                                        int margin=dip2px(StagePlanActivity.this,15);
                                        lp.setMargins(margin,0,margin,0);
                                        view.setLayoutParams(lp);
                                        view.setBackgroundResource(R.color.line2);
                                        layout_course_hour.addView(view);
                                    }
                                }
                            }
                        }

                        tvClassPinlv.setText(classPinlv+"次/周");
                        tvPlanStageTestTime.setText(planStageTestTime+"次");
                        tvOrderHour.setText(orderHour+"分");
                        tvOutTesTime.setText(outTesTime+"次");
                        stageTestContent.setText(!TextUtils.isEmpty(stageTestContentStr)?stageTestContentStr:"");

                        requestDataButtom(Url.baseUrl+Url.stagePlanButtom);
                    }
                }
            }
        });
    }

    //下半部分数据
    private void requestDataButtom(String url){
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_11_searchEleId",stageId)
                .addParams("fielterMainId",getLoginUserSharedPre().getString("USERID",""))
                .build();
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {
                getProgressDialog().dismiss();
            }

            @Override
            public void onResponse(Map<String,Object> response) {
                getProgressDialog().dismiss();
                Log.i("123","response===>"+response);
                if (response!=null){
                    mTeachContentList=(List<Map<String,Object>>)response.get("rows");
                    if (mTeachContentList!=null&&mTeachContentList.size()>0) {
                        for (int i=0;i<mTeachContentList.size();i++){
                            TextView tvTitle=new TextView(StagePlanActivity.this);
                            tvTitle.setTextSize(16f);
                            tvTitle.setBackgroundResource(R.drawable.shouke_title_bg);
                            tvTitle.setTextColor(Color.WHITE);
                            String title=(String) mTeachContentList.get(i).get("AFM_3");
                            tvTitle.setText(!TextUtils.isEmpty(title)?title:"");
                            LinearLayout.LayoutParams titleLp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            titleLp.setMargins(10,0,0,0);
                            tvTitle.setLayoutParams(titleLp);
                            layoutTeachPlan.addView(tvTitle);

                            TextView tvContent=new TextView(StagePlanActivity.this);
                            tvContent.setTextSize(14f);
                            tvContent.setTextColor(getResources().getColor(R.color.shouke_text));
                            tvContent.setBackgroundResource(R.color.white);
                            String content=(String) mTeachContentList.get(i).get("AFM_1");
                            String aim=(String) mTeachContentList.get(i).get("AFM_2");
                            String all="";
                            if (!TextUtils.isEmpty(content)&&!TextUtils.isEmpty(aim)) {
                                all="授课目的:"+aim+"\n"+"授课内容:"+content;
                            } else if (!TextUtils.isEmpty(content)&&TextUtils.isEmpty(aim)) {
                                all="授课内容:"+content;
                            } else if (TextUtils.isEmpty(content)&&!TextUtils.isEmpty(aim)) {
                                all="授课目的:"+aim;
                            }
                            tvContent.setText(all);
                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,15,0,10);
                            tvContent.setPadding(15,15,15,15);
                            tvContent.setLayoutParams(layoutParams);
                            layoutTeachPlan.addView(tvContent);
                            if (i<mTeachContentList.size()-1) {
                                TextView line=new TextView(StagePlanActivity.this);
                                LinearLayout.LayoutParams lineLp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                                lineLp.setMargins(0,0,32,20);
                                line.setBackgroundResource(R.color.line2);
                                line.setLayoutParams(lineLp);
                                layoutTeachPlan.addView(line);
                            }

                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

