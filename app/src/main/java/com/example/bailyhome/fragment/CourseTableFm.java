package com.example.bailyhome.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.bailyhome.CourseOrder;
import com.example.bailyhome.CourseTableLeaveActivity;
import com.example.bailyhome.MokaoActivity;
import com.example.bailyhome.MokaoJiangjie;
import com.example.bailyhome.R;
import com.example.bailyhome.ZhujiaoYiPeixun;
import com.example.bailyhome.adapter.CourseAdapter;
import com.example.bailyhome.bean.SerializableMap;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.model.OnDataListener;
import com.example.bailyhome.view.CourseGridView;
import com.example.bailyhome.view.CourseView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.WeekDateChaListener;
import noman.weekcalendar.listener.OnDateClickListener;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/12 0012.
 *
 *课程表界面
 */
public class CourseTableFm extends Fragment implements OnDataListener,WeekDateChaListener {
    private WeekCalendar weekCalendar;
    private List<Map<String,Object>> list;
    private List<Map<String,Object>> listQingjia;
    private CourseGridView courseGridView;
    private CourseAdapter courseAdapter;
    private TextView tv_date;
    private DateTime currentDateTime;
    private SharedPreferences sharedPreferences;

    //本周第一天
    private String thisWeekFirstDate;
    //本周最后一天
    private String thisWeekLastDate;
    //左侧显示时间的textview的宽度像素
    private float leftTextViewWidth;
    //左侧显示时间的textview的高度像素
    private float leftTextViewHeight;
    //每节课所占的宽度
    private float courseInfoWidth;
    //每分钟所对应的屏幕高度像素
    private float oneMinWidth;
    //课程表日期的布局
    private RelativeLayout rl_top;
    //选中日期时添加的view
    private View selectView,selectViewButtom;
    //添加课程信息的布局
    private RelativeLayout courseInfoLayout;
    //课程信息的背景布局
    private RelativeLayout layout_course_bg;
    //左侧textview
    private TextView leftTextView;
    //每条横线的高度像素
    private float lineHeight;
    //    int dataIndexMon=0;//记录数据当前周周一的下标
//    int dataIndexSun=0;//记录数据当前周周日的下标
    private int leaveIndex;//请假的下标

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_course,null);
        init(view);
        requestCourseData(Url.baseUrl+Url.courseTable);
        return view;
    }

    private void init(View view){
        rl_top=(RelativeLayout)view.findViewById(R.id.rl_top);
        layout_course_bg=(RelativeLayout) view.findViewById(R.id.layout_course_bg);
        courseInfoLayout=(RelativeLayout)view.findViewById(R.id.layout_course_info);
        leftTextView=(TextView)view.findViewById(R.id.tv_left);
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        leftTextView.measure(w, h);
        leftTextViewWidth =leftTextView.getMeasuredWidth();
        leftTextViewHeight =dip2px(getActivity(),33f);
        lineHeight=dip2px(getActivity(),0.5f);
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int layoutWidth = metric.widthPixels;     // 屏幕宽度（像素）
        courseInfoWidth=(layoutWidth-leftTextViewWidth-lineHeight*2)/7;
        oneMinWidth=leftTextViewHeight/30;
        sharedPreferences=getActivity().getSharedPreferences("userInfo",getActivity().MODE_PRIVATE);
        tv_date=(TextView)view.findViewById(R.id.tv_date);
        currentDateTime=new DateTime();
        int month=currentDateTime.getMonthOfYear();
        if (month < 10) {
            tv_date.setText("0" + month + "\n月");
        } else {
            tv_date.setText(month + "\n月");
        }
        weekCalendar = (WeekCalendar) view.findViewById(R.id.weekCalendar);
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
//                Toast.makeText(getActivity(), "You Selected " + dateTime.toString(), Toast
//                        .LENGTH_SHORT).show();
                String date=dateTime.toString("yyyy-MM-dd HH:mm:ss");
                int week=week(date);
                //左边距
                float marginLeft=((week-1)*courseInfoWidth)+leftTextViewWidth+lineHeight*2;
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams((int)courseInfoWidth,dip2px(getActivity(),2));
                layoutParams.setMargins((int)marginLeft,dip2px(getActivity(),64),0,0);

                RelativeLayout.LayoutParams lpButtom=new RelativeLayout.LayoutParams((int)courseInfoWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                lpButtom.setMargins((int)marginLeft,0,0,0);
                if (selectView == null) {
                    selectView = new View(getContext());
                    selectView.setBackgroundResource(R.color.selectColor);
                } else {
                    rl_top.removeView(selectView);
                }
                selectView.setLayoutParams(layoutParams);
                rl_top.addView(selectView);

                if (selectViewButtom == null) {
                    selectViewButtom = new View(getContext());
                    selectViewButtom.setBackgroundResource(R.color.selectColorButtom);
                } else {
                    layout_course_bg.removeView(selectViewButtom);
                }
                selectViewButtom.setLayoutParams(lpButtom);
                layout_course_bg.addView(selectViewButtom);
            }

        });
        weekCalendar.setWeekDateChaListener(this);

        DateTime midDate=currentDateTime.withDayOfWeek(DateTimeConstants.THURSDAY);
        DateTime startDate=midDate.plusDays(-3);
        DateTime endDate=midDate.plusDays(3);
        thisWeekFirstDate=startDate.toString("yyyy-MM-dd");
        thisWeekLastDate=endDate.toString("yyyy-MM-dd");
    }

    private static final String TAG = "CourseTableFm";
    //请求课程表数据
    private void requestCourseData(String url){
        Log.e(TAG, "requestCourseData: fielterMainId "+sharedPreferences.getString("USERID",""));
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("fielterMainId",sharedPreferences.getString("USERID",""))
                .addParams("limit","1000").build();
//                .addHeader("Cookie", "JSESSIONID="+sharedPreferences.getString("sessionId",""))
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Map<String,Object> response) {
                Log.e(TAG,"response1111===>"+response);
                if (response!=null){
                    list=(List<Map<String,Object>>)response.get("rows");
                    requestLeaveData(Url.baseUrl+Url.leaveRecUrl);
                }
            }
        });
    }

    //请求请假列表数据
    private void requestLeaveData(String url){
        Log.e(TAG, "requestLeaveData: 监测点1");
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("fielterMainId",sharedPreferences.getString("USERID",""))
//                .addParams("sessionId",sharedPreferences.getString("sessionId",""))
                .addHeader("Cookie", "JSESSIONID="+sharedPreferences.getString("sessionId",""))
                .build();
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Map<String,Object> response) {
                Log.e(TAG,"requestLeaveData===>"+response);
                if (response!=null){
                    listQingjia=(List<Map<String,Object>>)response.get("rows");
                    if (list!=null&&listQingjia!=null){
                        leaveIndex=list.size();
                        list.addAll(listQingjia);
                        setCourseDataInTable(leaveIndex);
                    } else if (list!=null&&listQingjia==null) {
                        leaveIndex=list.size();
                        setCourseDataInTable(leaveIndex);
                    } else if (list == null && listQingjia != null) {
                        list = listQingjia;
                        setCourseDataInTable(0);
                    } else {
                        Toast.makeText(getActivity(),"未预约上课",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onGetDataSuccess(String jsonData) {

    }

    @Override
    public void onGetDataError() {

    }

    @Override
    public void onLoading(long total, long current) {

    }

    //将课程信息填写到课表中
    private void setCourseDataInTable(int leaveIndex){
        Log.e(TAG, "setCourseDataInTable: 监测点1");
//        boolean firstData=false;//是否已经知道当前周第一节课
        for (int i=0;i<list.size();i++){
            String date=null;
            String time=null;
            if (i >= leaveIndex) {
                date=list.get(i).get("AFM_5")+"";
                Log.e(TAG, "setCourseDataInTable: date1 "+date);
                time=list.get(i).get("AFM_18")+"";
            } else {
                date=list.get(i).get("AFM_3")+"";
                Log.e(TAG, "setCourseDataInTable: date2 "+date);
                time=list.get(i).get("AFM_49")+"";
            }
            String date1=!date.equals("null")?date.substring(0,10):"";
            Log.e(TAG, "setCourseDataInTable: 监测点2");
            if (!TextUtils.isEmpty(date1)&&!time.equals("null")) {
                String str[]=time.split("-");
                String[] splitLeft = str[0].split(":");
                String stringHour = splitLeft[0];
                String stringMin = splitLeft[1];
                int left=Integer.parseInt(stringHour)*60;
                left+=Integer.parseInt(stringMin);

                String[] splitRight = str[1].split(":");
                String stringHour1 = splitRight[0];
                String stringMin1 = splitRight[1];
                int right=Integer.parseInt(stringHour1)*60;
                right+=Integer.parseInt(stringMin1);

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                long dateLong=0;
                long start=0;
                long end=0;
                try {
                    dateLong = sdf.parse(date).getTime();
                    start=sdf.parse(thisWeekFirstDate).getTime();
                    end=sdf.parse(thisWeekLastDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "setCourseDataInTable: 监测点3");
//                if (!firstData) {
//                    if (dateLong >= start && dateLong <= end) {
//                        firstData=true;
//                        dataIndexMon=i;
//                    }
//                }
                Log.e(TAG, "setCourseDataInTable: dateLong "+dateLong);
                Log.e(TAG, "setCourseDataInTable: start "+start);
                Log.e(TAG, "setCourseDataInTable: end "+end);
                if (dateLong >= start && dateLong <= end) {

                    Log.e(TAG, "setCourseDataInTable: 监测点4");
//                    dataIndexSun=i;
                    int week=week(date);
                    //左边距
                    float marginLeft=((week-1)*courseInfoWidth)+leftTextViewWidth+lineHeight*2;
                    //上边距跨过横线的条数总和的高度像素
                    float topLineSumHeight=((left-480)/30)*lineHeight;
                    //上边距
                    float marginTop=(left-480)*oneMinWidth+topLineSumHeight;
                    CourseView courseView=new CourseView(getContext());
                    //课程高度跨过的横线条数总和的高度像素
                    float chLineSumHeight=((right-left)/30)*lineHeight;
                    //课程高度
                    float courseHeight=(right-left)*oneMinWidth+chLineSumHeight;
                    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams((int)courseInfoWidth,(int)courseHeight);
                    layoutParams.setMargins((int)marginLeft,(int)marginTop,0,0);
                    courseView.getTopRightIv().setAdjustViewBounds(true);
                    courseView.getTopRightIv().setMaxWidth((int)(courseInfoWidth/2));
                    courseView.getTopRightIv().setMaxHeight((int)(courseInfoWidth/2));
                    courseView.setLayoutParams(layoutParams);
                    String peixunState=list.get(i).get("DIC_AFM_11")+"";
                    String courseName=null;
                    if (i >= leaveIndex) {
                        courseName = list.get(i).get("AFM_14") + "";
                    } else {
                        courseName=list.get(i).get("AFM_47")+"";
                    }

                    if (courseName.contains("一对一")) {
//                        courseView.setCourseTypeText("一对一");
                        if (i < leaveIndex) {
                            courseView.setBackgroundResource(R.drawable.couse_bg);
//                            courseView.setCourseTypeTextColor(R.color.course_type_color1);
                        } else {
//                            courseView.setCourseTypeTextColor(R.color.course_type_color5);
                            courseView.setBackgroundResource(R.drawable.course_background_qingjia);
                        }
                    } else if (courseName.contains("助教")) {
//                        courseView.setCourseTypeText("助教");
                        if (i < leaveIndex) {
                            courseView.setBackgroundResource(R.drawable.course_background_zhujiao);
//                            courseView.setCourseTypeTextColor(R.color.course_type_color3);
                        } else {
//                            courseView.setCourseTypeTextColor(R.color.course_type_color5);
                            courseView.setBackgroundResource(R.drawable.course_background_qingjia);
                        }
                    } else if (courseName.contains("模考")) {
//                        courseView.setCourseTypeText("模考");
                        if (i < leaveIndex) {
                            courseView.setBackgroundResource(R.drawable.course_background_mokao);
//                            courseView.setCourseTypeTextColor(R.color.course_type_color4);
                        } else {
//                            courseView.setCourseTypeTextColor(R.color.course_type_color5);
                            courseView.setBackgroundResource(R.drawable.course_background_qingjia);
                        }
                    } else if(courseName.contains("小班")){
//                        courseView.setCourseTypeText("小班");
                        if (i < leaveIndex) {
                            courseView.setBackgroundResource(R.drawable.course_background_xiaoban);
//                            courseView.setCourseTypeTextColor(R.color.course_type_color2);
                        } else {
//                            courseView.setCourseTypeTextColor(R.color.course_type_color5);
                            courseView.setBackgroundResource(R.drawable.course_background_qingjia);
                        }
                    }

                    if (!courseName.equals("null")) {
                        courseView.setCourseNameText(courseName);
                    } else {
                        courseView.setCourseNameText("");
                    }

                    if (i>=leaveIndex) {
                        courseView.setOrderSateText("假");
                    } else if (!peixunState.equals("null")&&peixunState.equals("已预约")) {
                        courseView.setOrderSateText("预");
                    } else if (!peixunState.equals("null")&&peixunState.equals("已培训")) {
                        courseView.setOrderSateText("完");
                    }
                    courseInfoLayout.addView(courseView);
                    final int position=i;
                    courseView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=null;
                            Map<String,Object> map=list.get(position);
                            String courseType=map.get("DIC_AFM_80")+"";
                            Log.i("123","courseType==>"+courseType);
                            if(courseType.equals("模考")||courseType.equals("模拟考位")){//模考
                                intent=new Intent(getActivity(), MokaoActivity.class);
                            }else if(courseType.equals("模考讲解")){//模考讲解
                                intent=new Intent(getActivity(), MokaoJiangjie.class);
                            }else if(courseType.equals("课程")){//一对一
                                intent=new Intent(getActivity(), CourseOrder.class);
                            }else if(courseType.equals("助教")){//助教
                                intent=new Intent(getActivity(), ZhujiaoYiPeixun.class);
                            }else {//请假
                                intent=new Intent(getActivity(), CourseTableLeaveActivity.class);
                            }

                            if (intent!=null) {
                                Bundle bundle=new Bundle();
                                SerializableMap serializableMap=new SerializableMap();
                                serializableMap.setMap(map);
                                bundle.putSerializable("courseData",serializableMap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }
                    });
                }

            }

        }
    }

    //获得星期几
    private int week(String date){
        if (date.equals("null")) {
            return 8;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        if (week==1){
            return 7;
        }else if (week==2){
            return 1;
        }else if (week==3){
            return 2;
        }else if (week==4){
            return 3;
        }else if (week==5){
            return 4;
        }else if (week==6){
            return 5;
        }else if (week==7){
            return 6;
        }else {
            return 8;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void getDate(DateTime dateTime) {
        currentDateTime=dateTime;
        int month=currentDateTime.getMonthOfYear();
        if (month < 10) {
            tv_date.setText("0" + month + "\n月");
        } else {
            tv_date.setText(month + "\n月");
        }
        DateTime midDate=dateTime.withDayOfWeek(DateTimeConstants.THURSDAY);
        DateTime startDate=midDate.plusDays(-3);
        DateTime endDate=midDate.plusDays(3);
        thisWeekFirstDate=startDate.toString("yyyy-MM-dd");
        thisWeekLastDate=endDate.toString("yyyy-MM-dd");
        courseInfoLayout.removeAllViews();
        if (list!=null){
            setCourseDataInTable(leaveIndex);
        }
//        courseAdapter.startAndEndDate(startDate.toString("yyyy-MM-dd"),endDate.toString("yyyy-MM-dd"));
//        courseAdapter.notifyDataSetChanged();
    }

}
