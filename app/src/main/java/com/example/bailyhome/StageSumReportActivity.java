package com.example.bailyhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bailyhome.adapter.StaSumFirstBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.base.ViewMiddle;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.config.Utility;
import com.example.bailyhome.view.ExpandTabView;
import com.example.bailyhome.view.SumExpandTabView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/17 0017.
 * <p/>
 * 阶段总结报告
 */
public class StageSumReportActivity extends BaseActivity {
    public TextView textHeader, textView;
    private ImageView menuImg, menuImgRight;
    private TextView tvStageName, tvFirstLeft, tvFirstRight, tvSecondLeft, tvSecondRight, tvThirdRight,
            tvFourRight, tvFiveRight, tvSixRight, tvSevenLeft, tvSevenRight, tvEightRight, tvNightLeft,
            tvNightRight, tvStageNameSec, tvTeachevalRight, tvTestscoreRight, tvWorkfinishRight, tvAvgRight;
    private String USERID, USERNAME, stageName;
    private LinearLayout headerLl, ll_exam, ll_test_exam, ll_test_exam_first;
    SumExpandTabView mExpandTabView;
    private ArrayList<String> liststr = new ArrayList<String>();
    private ViewMiddle viewMiddle;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    String url, examUrl, sumStageTestUrl, sumTEvalScoreAvgUrl;
    private ScrollView scrollView;
    String teacherAvaAvg = "";
    //private List<String> classNames = new ArrayList<String>();
    private StaSumFirstBaseAdapter staSumFirstBaseAdapter;
    private ListView lv_testScore, lv_exam_score;
    private LinearLayout layout_title_left_sec;
    private PopupWindow stageNameWindow;
    private ImageView iv_on_off;
    private TextView tv_title;
    private LinearLayout layout_stage_name;
    private RelativeLayout activity_header, my_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_sum_report);
        initView();
        Intent intent = getIntent();
        USERID = getLoginUserSharedPre().getString("USERID", "");
        USERNAME = intent.getStringExtra("USERNAME");
//        USERNAME = "学员-0606001";
        url = Url.baseUrl + Url.stateSumRepUrl;
        examUrl = Url.baseUrl + Url.examscoreUrl; //考试成绩
        sumStageTestUrl = Url.baseUrl + Url.stateTestUrl + Url.sumStateTestUrl; //测试成绩
        // sumStageTestUrl = Url.baseUrl + Url.stateTestUrl;//测试成绩
        sumTEvalScoreAvgUrl = Url.baseUrl + Url.sumTEvalScoreAvgUrl;//平均分

//        liststr = intent.getStringArrayListExtra("liststr");
//        Log.e("list-stasum-", liststr.toString());
        getUrlData(url, "", "1");


    }

    @Override
    public void initView() {
        my_layout = (RelativeLayout) findViewById(R.id.my_layout);
        my_layout.setVisibility(View.VISIBLE);
        activity_header = (RelativeLayout) findViewById(R.id.activity_header);
        activity_header.setVisibility(View.GONE);
        layout_title_left_sec = (LinearLayout) findViewById(R.id.layout_title_left_sec);
        layout_title_left_sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_on_off = (ImageView) findViewById(R.id.iv_on_off);
        iv_on_off.setBackgroundResource(R.mipmap.img_top);
        tv_title = (TextView) findViewById(R.id.tv_title);
        layout_stage_name = (LinearLayout) findViewById(R.id.layout_stage_name);
        layout_stage_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liststr != null && liststr.size() > 0) {
                    //初始化弹出层
                    initWindow();
                }
            }
        });
        tvStageName = (TextView) findViewById(R.id.tv_stageName);
        tvFirstLeft = (TextView) findViewById(R.id.tv_first_left);
        tvFirstRight = (TextView) findViewById(R.id.tv_first_right);
        tvSecondLeft = (TextView) findViewById(R.id.tv_second_left);
        tvSecondRight = (TextView) findViewById(R.id.tv_second_right);
        tvThirdRight = (TextView) findViewById(R.id.tv_third_right);
        tvFourRight = (TextView) findViewById(R.id.tv_four_right);
        tvFiveRight = (TextView) findViewById(R.id.tv_five_right);
        tvSixRight = (TextView) findViewById(R.id.tv_six_right);
        //  tvSevenLeft = (TextView) findViewById(R.id.tv_seven_left);
        //  tvSevenRight = (TextView) findViewById(R.id.tv_seven_right);
        tvEightRight = (TextView) findViewById(R.id.tv_eight_right);
        // tvNightLeft = (TextView) findViewById(R.id.tv_night_left);
        //ll_exam = (LinearLayout) findViewById(R.id.ll_exam);
        //  ll_test_exam = (LinearLayout) findViewById(R.id.ll_test_exam);
        //  ll_test_exam_first = (LinearLayout) findViewById(R.id.ll_test_exam_first);
        lv_testScore = (ListView) findViewById(R.id.lv_testScore);
        lv_exam_score = (ListView) findViewById(R.id.lv_exam_score);
        //tvNightRight = (TextView) findViewById(R.id.tv_night_right);
        tvStageNameSec = (TextView) findViewById(R.id.tv_stageName_sec);
        tvTeachevalRight = (TextView) findViewById(R.id.tv_teacheval_right);
        tvTestscoreRight = (TextView) findViewById(R.id.tv_testscore_right);
        tvWorkfinishRight = (TextView) findViewById(R.id.tv_workfinish_right);
        tvAvgRight = (TextView) findViewById(R.id.tv_avg_right);
        textView = (TextView) findViewById(R.id.textView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textHeader = (TextView) findViewById(R.id.text_header);
    }

    private void initWindow() {
        if (stageNameWindow != null && stageNameWindow.isShowing()) {
            stageNameWindow.dismiss();
        } else {
            View view = getLayoutInflater().inflate(R.layout.window_stage_name, null);
            ListView lv_stage_name = (ListView) view.findViewById(R.id.lv_stage_name);

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.course_name_item, R.id.tv_stage_name, liststr);
            lv_stage_name.setAdapter(adapter);

            lv_stage_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tv_title.setText(liststr.get(position));
                    try {
                        onRefresh(liststr.get(position), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stageNameWindow.dismiss();
                }
            });

            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            stageNameWindow = new PopupWindow(view, (width / 3) * 2,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            iv_on_off.setBackgroundResource(R.mipmap.img_buttom);
            stageNameWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    iv_on_off.setBackgroundResource(R.mipmap.img_top);
                }
            });

            ColorDrawable cd = new ColorDrawable(0b1);
            stageNameWindow.setBackgroundDrawable(cd);
            stageNameWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            //设置半透明
//            WindowManager.LayoutParams params=getWindow().getAttributes();
//            params.alpha=0.7f;
//            getWindow().setAttributes(params);

//            stageNameWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    WindowManager.LayoutParams params=getWindow().getAttributes();
//                    params.alpha=1f;
//                    getWindow().setAttributes(params);
//                }
//            });
//            stageNameWindow.update();
//            stageNameWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//            stageNameWindow.setTouchable(true); // 设置popupwindow可点击
            stageNameWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
            stageNameWindow.setFocusable(true); // 获取焦点
//            stageNameWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

            // 设置popupwindow的位置（相对tvLeft的位置）
//            int topBarHeight = layout_stage_name.getBottom();
            stageNameWindow.showAsDropDown(layout_stage_name, layout_stage_name.getWidth() / 2 - stageNameWindow.getWidth() / 2, 0);

            stageNameWindow.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        stageNameWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }

    public void initExpandView() {
//        mExpandTabView = new SumExpandTabView(StageSumReportActivity.this);
        final LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        headerLl.addView(mExpandTabView, ll);
        //mExpandTabView.setVisibility(View.GONE);

        if (liststr == null || liststr.size() <= 0) {
            liststr.add("暂无数据");
            scrollView.setVisibility(View.GONE);
            textHeader.setVisibility(View.VISIBLE);
            textHeader.setText("阶段总结报告");
            textView.setVisibility(View.VISIBLE);
        }
        tv_title.setText(liststr.get(0));
        viewMiddle = new ViewMiddle(getApplicationContext(), liststr);
        mViewArray.add(viewMiddle);
//        mExpandTabView.setValue(liststr, mViewArray);
        getExamUrldata(examUrl, liststr.get(0), "1");
//        mExpandTabView.setTitle(viewMiddle.getShowText(), 1);
//        mExpandTabView.setBackgroundColor(Color.BLUE);
        initListener();
    }

    /**
     * 平均分
     *
     * @param url
     * @param className
     * @param stageName
     * @param total
     */
    private void getsumTEvalScoreAvgUrldata(String url, final String className, final String stageName, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_8_strVal_pld", USERNAME).addParams("AFM_9_strVal_pld", className).addParams("AFM_10_strVal_pld", stageName).build();
        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    //    Log.e("object--",response.toString());
                    JSONArray array = object.getJSONArray("rows");
                    int length = 0, scoreTotal = 0;
                    if (array != null && array.length() > 0) {
                        length = array.length();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            if (object1.has("DIC_AFM_7")) {
                                teacherAvaAvg = object1.getString("DIC_AFM_7");
                            } else {
                                teacherAvaAvg = "暂无数据";
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTeachevalRight.setText(teacherAvaAvg);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    getProgressDialog().dismiss();
                }
            }
        });
    }

    /**
     * 测试成绩
     *
     * @param url
     * @param className
     * @param stageName
     * @param total
     */
    private void getTestUrldata(String url, final String className, final String stageName, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("fielterMainId", USERID).addParams("AFM_22_strVal_pld", className).addParams("AFM_24_strVal_pld", stageName).build();
//        RequestCall call = OkHttpUtils.getInstance().post().url(url)
//                .addParams("fielterMainId", USERID).addParams("AFM_22_strVal_pld", className).build();

        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Log.e("json-stageSumre-", response.toString());
                    JSONArray array = jsonObject.getJSONArray("rows");
                    final int length = array.length();
                    if (array != null && length > 0) {
//                        ll_test_exam_first.setVisibility(View.VISIBLE);
//                        ll_test_exam.removeAllViews();
                        lv_testScore.setVisibility(View.VISIBLE);
                        String testDate, testScore;
                        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        for (int i = 0; i < length; i++) {
                            JSONObject testObj = array.getJSONObject(i);
                            if (testObj.has("AFM_19")) {
                                Map<String, String> map = new HashMap<String, String>();
                                testDate = testObj.getString("AFM_19").substring(0, 10);
                                map.put("testDate", testDate);
                                if (testObj.has("AFM_2")) {
                                    testScore = testObj.getString("AFM_2");
                                } else {
                                    testScore = "无";
                                }
                                map.put("testScore", testScore);
                                list.add(map);
//                                final String finalTestDate = testDate;
//                                final String finalTestScore = testScore;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        tvSixRight.setText(length + "次");
//                                        staSumFirstBaseAdapter =new StaSumFirstBaseAdapter()
//                                        lv_testScore
//                                        TextView tv = new TextView(StageSumReportActivity.this);
//                                        tv.setMinHeight(30);
//                                        tv.setText(finalTestDate + "取得" + "   " + finalTestScore + "分");
//                                        final LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                        ll_test_exam.addView(tv, ll1);
//                                    }
//                                });
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSixRight.setText(length + "次");
                                if (list != null && list.size() > 0) {
                                    staSumFirstBaseAdapter = new StaSumFirstBaseAdapter(StageSumReportActivity.this, list, "0");
                                    lv_testScore.setAdapter(staSumFirstBaseAdapter);
                                    Utility.setListViewHeightBasedOnChildren(lv_testScore);
                                } else {
                                    lv_testScore.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        tvSixRight.setText("暂无数据");

                        lv_testScore.setVisibility(View.GONE);
                        //  ll_test_exam_first.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 考试成绩
     *
     * @param url
     * @param className
     * @param total
     */
    private void getExamUrldata(String url, final String className, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_8_strVal_pld", USERNAME).addParams("AFM_9_strVal_pld", className).build();
        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("rows");
                    final int length = array.length();
                    if (array != null && length > 0) {
                        // ll_exam.removeAllViews();
                        lv_exam_score.setVisibility(View.VISIBLE);
                        String examDate, examScore;
                        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        Map<String, String> map = null;
                        for (int i = 0; i < length; i++) {
                            JSONObject object = array.getJSONObject(i);
                            if (object.has("AFM_3")) {
                                map = new HashMap<String, String>();
                                examDate = object.getString("AFM_3").substring(0, 10);
                                map.put("testDate", examDate);
                                if (object.has("AFM_4")) {
                                    examScore = object.getString("AFM_4");
                                } else {
                                    examScore = "无";
                                }
                                map.put("testScore", examScore);
                                list.add(map);
//                                final String finalExamDate = examDate;
//                                final String finalExamScore = examScore;
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        tvEightRight.setText(length + "次");
//                                        TextView tv = new TextView(StageSumReportActivity.this);
//                                        tv.setMinHeight(30);
//                                        tv.setText(finalExamDate + "取得" + "   " + finalExamScore + "分");
//                                        final LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                      //  ll_exam.addView(tv, ll);
//                                    }
//                                });
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvEightRight.setText(length + "次");
                                if (list != null && list.size() > 0) {
                                    staSumFirstBaseAdapter = new StaSumFirstBaseAdapter(StageSumReportActivity.this, list, "1");
                                    lv_exam_score.setAdapter(staSumFirstBaseAdapter);
                                    Utility.setListViewHeightBasedOnChildren(lv_exam_score);
                                } else {
                                    lv_exam_score.setVisibility(View.GONE);
                                }
                            }
                        });
                        // tvEightRight.setText((finalObjs.has("AFM_30")) ? (finalObjs.getString("AFM_30") + "次") : "暂无数据");
//                                        tvNightLeft.setText(((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27").substring(0,10)) : "XXX") + "取得");
//                                        tvNightRight.setText((finalObjs.has("AFM_26")) ? (finalObjs.getString("AFM_26") + "分") : "暂无数据");
                    } else {
                        tvEightRight.setText("暂无数据");
                        lv_exam_score.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 总体链接
     *
     * @param url
     * @param className
     * @param total
     */
    private void getUrlData(final String url, final String className, final String total) {
        getProgressDialog().show();
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_41_strVal_pld", USERNAME).addParams("AFM_42_strVal_pld", className).build();
        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("rows");
                    int length = array.length();

                    if (array != null && length > 0) {
                        if (total.equals("1")) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject objs = array.getJSONObject(i);
                                if (objs.has("AFM_25")) {
                                    String clName = objs.getString("AFM_25");
                                    if (!liststr.contains(clName)) {
                                        liststr.add(clName);
                                    }
                                }
                            }
                            if (liststr != null && liststr.size() > 0) {
                                getUrlData(url, liststr.get(0), "2");
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initExpandView();
                                }
                            });
                        } else {
                            Log.e("array.len---", array.length() + "");
                            JSONObject objs = array.getJSONObject(0);
                            //数据条数大于一条时
                            if (length > 1) {
                                int stageGoalFir = 0, stageGoalsec = 0;
                                if (objs.has("AFM_4")) {
                                    stageGoalFir = Integer.parseInt(objs.getString("AFM_4"));
                                }

                                for (int i = 0; i < length; i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    if (objs.has("AFM_4")) {
                                        stageGoalsec = Integer.parseInt(object.getString("AFM_4"));
                                        if (stageGoalFir < stageGoalsec) {
                                            objs = array.getJSONObject(i);
                                            stageGoalFir = stageGoalsec;
                                        }
                                    }
                                }
                            }
                            final JSONObject finalObjs = objs;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        textView.setVisibility(View.GONE);
                                        scrollView.setVisibility(View.VISIBLE);

                                        tvStageName.setText(((finalObjs.has("AFM_3")) ? finalObjs.getString("AFM_3") : "XX阶段") + "总结报告");
                                        tvFirstLeft.setText(((finalObjs.has("AFM_26")) ? finalObjs.getString("AFM_26") : "XXX") + "同学");
                                        tvFirstRight.setText((finalObjs.has("AFM_4")) ? (finalObjs.getString("AFM_4") + "分") : "暂无数据");
                                        tvSecondLeft.setText(((finalObjs.has("AFM_28")) ? (finalObjs.getString("AFM_28").substring(0, 10)) : "XXX"));
                                        tvSecondRight.setText("进行的阶段测评成绩： " + ((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27") + "分") : "暂无数据"));
                                        tvThirdRight.setText(((finalObjs.has("AFM_29")) ? finalObjs.getString("AFM_29") : "暂无") + "课时");
                                        tvFourRight.setText(((finalObjs.has("AFM_30")) ? finalObjs.getString("AFM_30") : "暂无") + "课时");
                                        tvFiveRight.setText((finalObjs.has("AFM_11")) ? (finalObjs.getString("AFM_11") + "次") : "暂无数据");

                                        //   tvSixRight.setText((finalObjs.has("AFM_30")) ? (finalObjs.getString("AFM_30") + "次") : "暂无数据");
                                        //   tvSevenLeft.setText(((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27").substring(0, 10)) : "XXX") + "取得");
                                        //   tvSevenRight.setText((finalObjs.has("AFM_31")) ? (finalObjs.getString("AFM_31") + "分") : "暂无数据");
                                        //    tvEightRight.setText((finalObjs.has("AFM_30")) ? (finalObjs.getString("AFM_30") + "次") : "暂无数据");
//                                        tvNightLeft.setText(((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27").substring(0,10)) : "XXX") + "取得");
//                                        tvNightRight.setText((finalObjs.has("AFM_26")) ? (finalObjs.getString("AFM_26") + "分") : "暂无数据");
                                        if (finalObjs.has("AFM_3")) {
                                            stageName = finalObjs.getString("AFM_3");
                                            tvStageNameSec.setText(stageName);
                                        } else {
                                            stageName = "";
                                            tvStageNameSec.setText("暂无数据");
                                        }
                                        Log.e("classname--", className);
                                        getTestUrldata(sumStageTestUrl, className, stageName, "1");
                                        getsumTEvalScoreAvgUrldata(sumTEvalScoreAvgUrl, className, stageName, "1");
                                        //tvTeachevalRight.setText((finalObjs.has("DIC_AFM_32")) ? finalObjs.getString("DIC_AFM_32") : "暂无数据");
                                        DecimalFormat df = new DecimalFormat("###.00");
                                        Double double1 = Double.valueOf(finalObjs.getString("AFM_34"));
                                        Double double2 = Double.valueOf(finalObjs.getString("AFM_35"));
                                        Double double3 = Double.valueOf(finalObjs.getString("AFM_36"));

                                        tvTestscoreRight.setText((finalObjs.has("AFM_34")) ? (df.format(double1) + "分") : "暂无数据");
                                        tvWorkfinishRight.setText((finalObjs.has("AFM_35")) ? (df.format(double2) + "%") : "暂无数据");
                                        tvAvgRight.setText((finalObjs.has("AFM_36")) ? (df.format(double3) + "分") : "暂无数据");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
//                        for (int i=0;i<array.length();i++){
//                            JSONObject objs = array.getJSONObject(i);
//                            Map<String,String> map = new HashMap<String, String>();
//                        }
                        }
                    } else {
                        textView.setVisibility(View.VISIBLE);
                        textHeader.setText("阶段总结报告");
                        textHeader.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        getProgressDialog().dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initListener() {
//        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
//            @Override
//            public void getValue(String distance, String showText) throws Exception {
//                onRefresh(viewMiddle, showText, distance);
//            }
//        });
    }

    private void onRefresh(String showText, String distance) throws Exception {
        getUrlData(url, showText, "2");
        getExamUrldata(examUrl, showText, "2");
        //  Toast.makeText(StageSumReportActivity.this, showText, Toast.LENGTH_SHORT).show();
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
