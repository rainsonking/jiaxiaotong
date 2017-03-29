package com.example.bailyhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bailyhome.adapter.StaSumFirstBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.base.ViewMiddle;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.config.Utility;
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
 * Created by Administrator on 2016/6/7 0007.
 */
public class CouseSumReportActivity extends BaseActivity {
    public TextView textHeader, textView;
    private ImageView  menuImgRight;
    private TextView tvStageName, tvFirstLeft, tvFirstRight, tvSecondLeft, tvSecondRight, tvThirdRight,
            tvFourRight, tvFiveRight, tvSixRight, tvSevenLeft, tvSevenRight, tvEightRight, tvNightLeft,
            tvNightRight, tvStageNameSec, tvTeachevalRight, tvTestscoreRight, tvWorkfinishRight, tvAvgRight;
    private String courseName, stuName, stageName, USERID;
    private LinearLayout headerLl, ll_exam, ll_test_exam, ll_test_exam_first,layout_title_left;
    private ArrayList<String> liststr = new ArrayList<String>();
    String url, examUrl, sumStageTestUrl, sumTEvalScoreAvgUrl;
    private ScrollView scrollView;
    String teacherAvaAvg = "";
    private StaSumFirstBaseAdapter staSumFirstBaseAdapter;
    private ListView lv_testScore, lv_exam_score;
    private RelativeLayout activity_header,my_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_sum_report);
        initView();
        url = Url.baseUrl + Url.stateSumRepUrl;
        examUrl = Url.baseUrl + Url.examscoreUrl;
        sumStageTestUrl = Url.baseUrl + Url.stateTestUrl + Url.sumStateTestUrl;
        sumTEvalScoreAvgUrl = Url.baseUrl + Url.sumTEvalScoreAvgUrl;
        Intent intent = getIntent();
        USERID = getLoginUserSharedPre().getString("USERID", "");
        courseName = intent.getStringExtra("courseName");
        stuName = intent.getStringExtra("stuName");
        stageName = intent.getStringExtra("stageName");
        // Log.e("123", "courseName===>" + courseName + "==stuName==>" + stuName + "==stageName==>" + stageName);
       if(courseName!=null&&stuName!=null&&stageName!=null&&courseName.length()>0&&stageName.length()>0&&stuName.length()>0){
           getUrlData(url, courseName);
           getExamUrldata(examUrl, courseName, "1");
           getTestUrldata(sumStageTestUrl, courseName, stageName, "1");
           getsumTEvalScoreAvgUrldata(sumTEvalScoreAvgUrl, courseName, stageName, "1");
       }else {
           textView.setVisibility(View.VISIBLE);
           scrollView.setVisibility(View.GONE);
       }
    }

    @Override
    public void initView() {
        my_layout=(RelativeLayout) findViewById(R.id.my_layout);
        my_layout.setVisibility(View.GONE);
        activity_header=(RelativeLayout) findViewById(R.id.activity_header);
        activity_header.setVisibility(View.VISIBLE);
        textHeader = (TextView) findViewById(R.id.text_header);
        textHeader.setText("阶段总结报告");
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        headerLl = (LinearLayout) findViewById(R.id.header_ll);
        tvStageName = (TextView) findViewById(R.id.tv_stageName);
        tvFirstLeft = (TextView) findViewById(R.id.tv_first_left);
        tvFirstRight = (TextView) findViewById(R.id.tv_first_right);
        tvSecondLeft = (TextView) findViewById(R.id.tv_second_left);
        tvSecondRight = (TextView) findViewById(R.id.tv_second_right);
        tvThirdRight = (TextView) findViewById(R.id.tv_third_right);
        tvFourRight = (TextView) findViewById(R.id.tv_four_right);
        tvFiveRight = (TextView) findViewById(R.id.tv_five_right);
        tvSixRight = (TextView) findViewById(R.id.tv_six_right);
        //tvSevenLeft = (TextView) findViewById(R.id.tv_seven_left);
        // tvSevenRight = (TextView) findViewById(R.id.tv_seven_right);
        tvEightRight = (TextView) findViewById(R.id.tv_eight_right);
        // tvNightLeft = (TextView) findViewById(R.id.tv_night_left);
        // ll_exam = (LinearLayout) findViewById(R.id.ll_exam);
        // ll_test_exam = (LinearLayout) findViewById(R.id.ll_test_exam);
        //   ll_test_exam_first = (LinearLayout) findViewById(R.id.ll_test_exam_first);
        //  tvNightRight = (TextView) findViewById(R.id.tv_night_right);
        lv_testScore = (ListView) findViewById(R.id.lv_testScore);
        lv_exam_score = (ListView) findViewById(R.id.lv_exam_score);
        tvStageNameSec = (TextView) findViewById(R.id.tv_stageName_sec);
        tvTeachevalRight = (TextView) findViewById(R.id.tv_teacheval_right);
        tvTestscoreRight = (TextView) findViewById(R.id.tv_testscore_right);
        tvWorkfinishRight = (TextView) findViewById(R.id.tv_workfinish_right);
        tvAvgRight = (TextView) findViewById(R.id.tv_avg_right);
        textView = (TextView) findViewById(R.id.textView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void getsumTEvalScoreAvgUrldata(String url, final String className, final String stageName, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_8_strVal_pld", stuName).addParams("AFM_9_strVal_pld", className).addParams("AFM_10_strVal_pld", stageName).build();
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
                    JSONArray array = object.getJSONArray("rows");
                    int length = 0, scoreTotal = 0;
                    if (array != null && array.length() > 0) {
                        length = array.length();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            if (object1.has("DIC_AFM_7")) {
                                teacherAvaAvg = object1.getString("DIC_AFM_7");
                            } else {
                                teacherAvaAvg="暂无数据";
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTeachevalRight.setText(teacherAvaAvg);
                            getProgressDialog().dismiss();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 测试成绩
     * @param url
     * @param className
     * @param stageName
     * @param total
     */
    private void getTestUrldata(String url, final String className, final String stageName, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("fielterMainId", USERID).addParams("AFM_22_strVal_pld", className).addParams("AFM_24_strVal_pld", stageName).build();
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
                        //  ll_test_exam_first.setVisibility(View.VISIBLE);
                        //  ll_test_exam.removeAllViews();
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
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSixRight.setText(length + "次");
                                if (list != null && list.size() > 0) {
                                    staSumFirstBaseAdapter = new StaSumFirstBaseAdapter(CouseSumReportActivity.this, list, "0");
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
                        // ll_test_exam_first.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 考试成绩
     * @param url
     * @param className
     * @param total
     */
    private void getExamUrldata(String url, final String className, final String total) {
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_8_strVal_pld", stuName).addParams("AFM_9_strVal_pld", className).build();
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
                        //  ll_exam.removeAllViews();
                        lv_exam_score.setVisibility(View.VISIBLE);
                        String examDate, examScore;
                        final List<Map<String, String>> list1 = new ArrayList<Map<String, String>>();
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
                                list1.add(map);
                            }

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvEightRight.setText(length + "次");
                                if (list1 != null && list1.size() > 0) {
                                    staSumFirstBaseAdapter = new StaSumFirstBaseAdapter(CouseSumReportActivity.this, list1, "1");
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
//                        tvNightLeft.setText("XXX" + "取得");
//                        tvNightRight.setText("暂无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getUrlData(String url, final String className) {
        getProgressDialog().show();
        RequestCall call = OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_41_strVal_pld", stuName).addParams("AFM_42_strVal_pld", className).build();
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
                    JSONObject objsRes = null;
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objs = array.getJSONObject(i);
                            if (objs.has("AFM_3")) {
                                String stage = objs.getString("AFM_3");
                                if (stageName.equals(stage)) {
                                    objsRes = objs;
                                    break;
                                }
                            }
                        }
                        final JSONObject finalObjs = objsRes;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (finalObjs != null && finalObjs.length() > 0) {
                                        textView.setVisibility(View.GONE);
                                        scrollView.setVisibility(View.VISIBLE);
                                        tvStageName.setText(((finalObjs.has("AFM_3")) ? finalObjs.getString("AFM_3") : "XX阶段") + "总结报告");
                                        tvFirstLeft.setText(((finalObjs.has("AFM_26")) ? (finalObjs.getString("AFM_26")+"同学") : "XXX"));
                                        tvFirstRight.setText((finalObjs.has("AFM_4")) ? (finalObjs.getString("AFM_4") + "分") : "暂无数据");
                                        tvSecondLeft.setText((finalObjs.has("AFM_28")) ? (finalObjs.getString("AFM_28").substring(0, 10)) : "XXX");
                                        tvSecondRight.setText("进行的阶段测评成绩："+((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27") + "分") : "暂无数据"));
                                        tvThirdRight.setText(((finalObjs.has("AFM_29")) ? finalObjs.getString("AFM_29") : "暂无") + "课时");
                                        tvFourRight.setText(((finalObjs.has("AFM_30")) ? finalObjs.getString("AFM_30") : "暂无") + "课时");
                                        tvFiveRight.setText((finalObjs.has("AFM_11")) ? (finalObjs.getString("AFM_11") + "次") : "暂无数据");
                                        // tvSixRight.setText((finalObjs.has("AFM_30")) ? (finalObjs.getString("AFM_30") + "次") : "暂无数据");
                                        //tvSevenLeft.setText(((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27").substring(0, 10)) : "XXX") + "取得");
                                        // tvSevenRight.setText((finalObjs.has("AFM_31")) ? (finalObjs.getString("AFM_31") + "分") : "暂无数据");
                                        // tvEightRight.setText((finalObjs.has("AFM_30")) ? (finalObjs.getString("AFM_30") + "次") : "暂无数据");
                                        // tvNightLeft.setText(((finalObjs.has("AFM_27")) ? (finalObjs.getString("AFM_27").substring(0, 10)) : "XXX") + "取得");
                                        // tvNightRight.setText((finalObjs.has("AFM_26")) ? (finalObjs.getString("AFM_26") + "分") : "暂无数据");
                                        tvStageNameSec.setText(stageName);

                                        DecimalFormat df = new DecimalFormat("###.00");
                                        Double double1 = Double.valueOf(finalObjs.getString("AFM_34"));
                                        Double double2 = Double.valueOf(finalObjs.getString("AFM_35"));
                                        Double double3 = Double.valueOf(finalObjs.getString("AFM_36"));
                                        //  tvTeachevalRight.setText((finalObjs.has("DIC_AFM_32")) ? finalObjs.getString("DIC_AFM_32") : "暂无数据");
                                        tvTestscoreRight.setText((finalObjs.has("AFM_34")) ? (df.format(double1) + "分") : "暂无数据");
                                        tvWorkfinishRight.setText((finalObjs.has("AFM_35")) ? (df.format(double2) + "%") : "暂无数据");
                                        tvAvgRight.setText((finalObjs.has("AFM_36")) ? (df.format(double3) + "分") : "暂无数据");

                                    } else {
                                        textView.setVisibility(View.VISIBLE);
                                        scrollView.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        textView.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
