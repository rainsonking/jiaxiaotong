package com.example.bailyhome.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bailyhome.FirstTextActivity;
import com.example.bailyhome.HworkGradeActivity;
import com.example.bailyhome.R;
import com.example.bailyhome.adapter.MyGridAdapter;
import com.example.bailyhome.bean.MyGridBean;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.model.OnDataListener;
import com.example.bailyhome.view.ScoreMyGridView;
import com.example.bailyhome.view.TasksCompletedView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/5/13 0013.
 * <p/>
 * 成绩模块
 */
public class ScoreFragment extends android.support.v4.app.Fragment implements OnDataListener, View.OnClickListener {

    //    private ListView scoreList;
    private ScoreMyGridView gridView;
    private List<MyGridBean> lists = new ArrayList<MyGridBean>();
    public int[] imgs = {R.mipmap.a_stage_information, R.mipmap.b_stage_record,
            R.mipmap.c_stage_evaluate, R.mipmap.d_stage_task,
            R.mipmap.app_transfer};
    public String[] values = {"课首测试", "作业成绩", "阶段测试", "模考成绩", "考试成绩"};
    public TextView courseText, courseTextFirst, tvFirstScore, tvTargetScore, tvNowState, tvClassHour, tvclassHourName;

    public String tvNowGoalScore, tvAlreadyClassHour, tvRemainClassHour;
    //    public List<Map<String, String>> scoreLists = new ArrayList<Map<String, String>>();
//    private String[] scoreLeft = {"初试分数", "最终目标分数", "学员当前处于", "本阶段目标分数", "托福一对一课时", "已上课时", "剩余课时"};
//    public SoreListBaseAdapter soreListBaseAdapter;
    public List<String> listStr;
    private Bundle data;
    private String USERID, USERNAME, className, supClassName;
    private TextView textView;
    private ScrollView scrollView2;
    LinearLayout circleLl, layout,ll_sec;
    public TasksCompletedView completedView;
    public LinearLayout ll_first_text, ll_work_score, ll_stage_text, ll_moke_score, ll_exam_score;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container);
        data = getArguments();
        String dataStr = data.getString("rows");
        USERID = data.getString("USERID");
        USERNAME = data.getString("USERNAME");
        supClassName = data.getString("supClassName");
        if (dataStr != null && dataStr.length() > 0) {
            initScoreAdapter(dataStr, supClassName, "1");
            //initGridView();
        } else {
            textView.setVisibility(View.VISIBLE);
            ll_sec.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
        }
        return view;
    }

    /*初始化adapter Gridview中的*/
    private void initGridView() {
        MyGridBean myGridBean = null;
        for (int i = 0; i < 5; i++) {
            myGridBean = new MyGridBean(imgs[i], values[i]);
            lists.add(myGridBean);
        }
        MyGridAdapter myGridAdapter = new MyGridAdapter(getActivity(), lists);
        gridView.setAdapter(myGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity(), FirstTextActivity.class);
                        intent.putExtra("title", "课首测试");
                        intent.putExtra("Tag", i + "");
                        intent.putExtra("USERID", USERID);
                        intent.putExtra("USERNAME", USERNAME);
                        intent.putExtra("className", className);
                        String urlStr = Url.baseUrl + Url.firstTestUrl;
                        intent.putExtra("url", urlStr);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), HworkGradeActivity.class);
                        intent.putExtra("title", "作业成绩");
                        intent.putExtra("Tag", i + "");
                        intent.putExtra("USERID", USERID);
                        intent.putExtra("USERNAME", USERNAME);
                        intent.putExtra("className", className);
                        urlStr = Url.baseUrl + Url.workScorUrl;
                        intent.putExtra("url", urlStr);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), HworkGradeActivity.class);
                        intent.putExtra("title", "阶段测试");
                        intent.putExtra("Tag", i + "");
                        intent.putExtra("USERID", USERID);
                        intent.putExtra("USERNAME", USERNAME);
                        intent.putExtra("className", className);
                        urlStr = Url.baseUrl + Url.stateTestUrl;
                        intent.putExtra("url", urlStr);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), HworkGradeActivity.class);
                        intent.putExtra("title", "模考成绩");
                        intent.putExtra("Tag", i + "");
                        intent.putExtra("USERID", USERID);
                        intent.putExtra("USERNAME", USERNAME);
                        intent.putExtra("className", className);
                        urlStr = Url.baseUrl + Url.mokeTestUrl;
                        intent.putExtra("url", urlStr);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), FirstTextActivity.class);
                        intent.putExtra("title", "考试成绩");
                        intent.putExtra("USERID", USERID);
                        intent.putExtra("Tag", i + "");
                        intent.putExtra("USERNAME", USERNAME);
                        intent.putExtra("className", className);
                        urlStr = Url.baseUrl + Url.examscoreUrl;
                        intent.putExtra("url", urlStr);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void handleData(String data, String textName) {
        if (data.equals("")) {
            textView.setVisibility(View.VISIBLE);
            ll_sec.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
        } else {
            initScoreAdapter(data, textName, "2");
        }
    }

    /*初始化adapter listview中的*/
    private void initScoreAdapter(String str, String str2, String tag) {
        try {
            JSONObject object = new JSONObject(str);
            JSONArray array = object.getJSONArray("rows");
            if (array.length() > 0) {
                className = str2;
                courseText.setText(className);
                tvclassHourName.setText(className);
                JSONObject jsonobject = array.getJSONObject(0);
                courseTextFirst.setText(jsonobject.getString("AFM_15") + "同学" + jsonobject.getString("AFM_16").substring(0, 10) + "进入服务体系。");
                String firstScore, className1, targetScore, nowState, nowStateStr = "暂无数据", nowGoalScore, classHour, alreadyClassHour, remainClassHour;
                int firstScoreInt = 0, targetScoreInt = 0, nowGoalScoreInt = 0, classHourInt = 0, alreadyClassHourInt = 0, remainClassHourInt = 0;
                for (int i = 0; i < array.length(); i++) {
                    jsonobject = array.getJSONObject(i);
                    className1 = jsonobject.getString("AFM_14");
                    if (str2.equals(className1)) {
                        //初试成绩
                        if (jsonobject.has("AFM_17")) {
                            firstScore = jsonobject.getString("AFM_17");
                            if (firstScore != null && !"".equals(firstScore)) {
                                firstScoreInt += Integer.parseInt(firstScore);
                            } else {
                                firstScoreInt += 0;
                            }
                        } else {
                            firstScoreInt += 0;
                        }
                        //最终目标分数
                        if (jsonobject.has("AFM_18")) {
                            targetScore = jsonobject.getString("AFM_18");
                            if (targetScore != null && !"".equals(targetScore)) {
                                targetScoreInt += Integer.parseInt(targetScore);
                            } else {
                                targetScoreInt += 0;
                            }
                        } else {
                            targetScoreInt += 0;
                        }
                        //当前阶段
                        if (jsonobject.has("AFM_20")) {
                            nowState = jsonobject.getString("AFM_20");
                            if (nowState != null && !"".equals(nowState)) {
                                nowStateStr = nowState;
                            }
                        }
                        //阶段目标分数
                        if (jsonobject.has("AFM_19")) {
                            nowGoalScore = jsonobject.getString("AFM_19");
                            if (nowGoalScore != null && !"".equals(nowGoalScore)) {
                                nowGoalScoreInt += Integer.parseInt(nowGoalScore);
                            } else {
                                nowGoalScoreInt += 0;
                            }
                        } else {
                            nowGoalScoreInt += 0;
                        }
                        //总课时
                        if (jsonobject.has("AFM_21")) {
                            classHour = jsonobject.getString("AFM_21");
                            if (classHour != null && !"".equals(classHour)) {
                                classHourInt += Integer.parseInt(classHour);
                            } else {
                                classHourInt += 0;
                            }
                        } else {
                            classHourInt += 0;
                        }
                        //已上学时
                        if (jsonobject.has("AFM_10")) {
                            alreadyClassHour = jsonobject.getString("AFM_10");
                            if (alreadyClassHour != null && !"".equals(alreadyClassHour)) {
                                alreadyClassHourInt += Integer.parseInt(alreadyClassHour);
                            } else {
                                alreadyClassHourInt += 0;
                            }
                        } else {
                            alreadyClassHourInt += 0;
                        }
                        //剩余学时
                        if (jsonobject.has("AFM_22")) {
                            remainClassHour = jsonobject.getString("AFM_22");
                            if (remainClassHour != null && !"".equals(remainClassHour)) {
                                remainClassHourInt += Integer.parseInt(remainClassHour);
                            } else {
                                remainClassHourInt += 0;
                            }
                        } else {
                            remainClassHourInt += 0;
                        }
                    }
                }

                tvFirstScore.setText(firstScoreInt + "");
                if (targetScoreInt == 0) {
                    tvTargetScore.setText("无");
                } else {
                    tvTargetScore.setText(targetScoreInt + "");
                }

                tvNowState.setText(nowStateStr);
                tvClassHour.setText(classHourInt + "");
                if ("1".equals(tag)) {
                    completedView = new TasksCompletedView(getActivity(), nowGoalScoreInt + "", alreadyClassHourInt + "", remainClassHourInt + "");
                    int progress = getProgressData(classHourInt, alreadyClassHourInt, remainClassHourInt);
                    completedView.setProgress(progress, nowGoalScoreInt + "", alreadyClassHourInt + "", remainClassHourInt + "");
                    final LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, getScreenWidth(getActivity()) / 2);
                    circleLl.addView(completedView, ll);
                } else {
                    int progress = getProgressData(classHourInt, alreadyClassHourInt, remainClassHourInt);
                    completedView.setProgress(progress, nowGoalScoreInt + "", alreadyClassHourInt + "", remainClassHourInt + "");
                }
            } else {
                textView.setVisibility(View.VISIBLE);
                ll_sec.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                scrollView2.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (str != null && str.length() > 0) {
//            try {
//                JSONObject jsonobject = new JSONObject(str);
//                className = jsonobject.getString("AFM_13");
//                courseText.setText(className);
//                courseTextFirst.setText(jsonobject.getString("AFM_14") + "同学" + jsonobject.getString("AFM_15").substring(0, 10) + "进入服务体系。");
//                tvFirstScore.setText("无");
//                if (jsonobject.has("AFM_16")) {
//                    tvFirstScore.setText(jsonobject.getString("AFM_16"));
//                }
//                tvTargetScore.setText("无");
//                if (jsonobject.has("AFM_17")) {
//                    tvTargetScore.setText(jsonobject.getString("AFM_17"));
//                }
//                tvNowState.setText("暂无数据");
//                if (jsonobject.has("AFM_19")) {
//                    tvNowState.setText(jsonobject.getString("AFM_19"));
//                }
//                tvNowGoalScore = "无";
//                if (jsonobject.has("AFM_18")) {
//                    tvNowGoalScore = jsonobject.getString("AFM_18");
//                }
//                tvClassHour.setText("0");
//                if (jsonobject.has("AFM_20")) {
//                    tvClassHour.setText(jsonobject.getString("AFM_20"));
//                }
//                tvAlreadyClassHour = "0";
//                if (jsonobject.has("AFM_10")) {
//                    tvAlreadyClassHour = jsonobject.getString("AFM_10");
//                }
//                tvRemainClassHour = "0";
//                if (jsonobject.has("AFM_21")) {
//                    tvRemainClassHour = jsonobject.getString("AFM_21");
//                }
//
//
//                String classHourStr = tvClassHour.getText().toString();
//                int classHourInt = Integer.parseInt(classHourStr);
//                int tvAlreadyClassHourInt = Integer.parseInt(tvAlreadyClassHour);
//                int tvRemainClassHourInt = Integer.parseInt(tvRemainClassHour);
//                if ("1".equals(tag)) {
//                    completedView = new TasksCompletedView(getActivity(), tvNowGoalScore, tvAlreadyClassHour, tvRemainClassHour);
//                    int progress = getProgressData(classHourInt, tvAlreadyClassHourInt, tvRemainClassHourInt);
//
//                    completedView.setProgress(progress, tvNowGoalScore, tvAlreadyClassHour, tvRemainClassHour);
//                    final LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    circleLl.addView(completedView, ll);
//                } else {
//                    int progress = getProgressData(classHourInt, tvAlreadyClassHourInt, tvRemainClassHourInt);
//                    completedView.setProgress(progress, tvNowGoalScore, tvAlreadyClassHour, tvRemainClassHour);
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            textView.setVisibility(View.VISIBLE);
//            layout.setVisibility(View.GONE);
//            scrollView2.setVisibility(View.GONE);
//        }

//        Map<String, String> map = null;
//        for (int j = 0; j < scoreLeft.length; j++) {
//            map = new HashMap<String, String>();
//            map.put("textItemLeft", scoreLeft[j]);
//            map.put("textItemRight", "");
//            scoreLists.add(map);
//        }
//        soreListBaseAdapter = new SoreListBaseAdapter(scoreLists, getActivity());
//        scoreList.setAdapter(soreListBaseAdapter);
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    private int getProgressData(int classHourInt, int tvAlreadyClassHourInt, int tvRemainClassHourInt) {
        int progress = 0;
        if (classHourInt == 0) {
            progress = 0;
        } else if ((classHourInt != 0) && (tvAlreadyClassHourInt == 0)) {
            progress = 0;
        } else if ((tvRemainClassHourInt == 0) && (classHourInt != 0)) {
            progress = 100;
        } else {
            progress = (tvAlreadyClassHourInt * 100) / classHourInt;
        }
        return progress;
    }


    @NonNull
    private View initView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_score, container, false);

//        scoreList = (ListView) view.findViewById(R.id.score_list);
        gridView = (ScoreMyGridView) view.findViewById(R.id.gridview);
        courseText = (TextView) view.findViewById(R.id.course_text);
        courseTextFirst = (TextView) view.findViewById(R.id.course_text_first);
        tvFirstScore = (TextView) view.findViewById(R.id.tv_first_score);
        tvTargetScore = (TextView) view.findViewById(R.id.tv_target_score);
        tvNowState = (TextView) view.findViewById(R.id.tv_now_state);
        tvclassHourName = (TextView) view.findViewById(R.id.classHourName);
        tvClassHour = (TextView) view.findViewById(R.id.tv_class_hour);
        textView = (TextView) view.findViewById(R.id.textView);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        scrollView2 = (ScrollView) view.findViewById(R.id.scrollView2);
        circleLl = (LinearLayout) view.findViewById(R.id.circle_ll);
        ll_sec = (LinearLayout) view.findViewById(R.id.ll_sec);

        ll_first_text = (LinearLayout) view.findViewById(R.id.ll_first_text);
        ll_work_score = (LinearLayout) view.findViewById(R.id.ll_work_score);
        ll_stage_text = (LinearLayout) view.findViewById(R.id.ll_stage_text);
        ll_moke_score = (LinearLayout) view.findViewById(R.id.ll_moke_score);
        ll_exam_score = (LinearLayout) view.findViewById(R.id.ll_exam_score);
        ll_first_text.setOnClickListener(this);
        ll_work_score.setOnClickListener(this);
        ll_stage_text.setOnClickListener(this);
        ll_moke_score.setOnClickListener(this);
        ll_exam_score.setOnClickListener(this);
        return view;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_first_text:
                Intent intent = new Intent(getActivity(), FirstTextActivity.class);
                intent.putExtra("title", "课首测试");
                intent.putExtra("Tag", 0 + "");
                intent.putExtra("USERID", USERID);
                intent.putExtra("USERNAME", USERNAME);
                intent.putExtra("className", className);
                String urlStr = Url.baseUrl + Url.firstTestUrl;
                intent.putExtra("url", urlStr);
                startActivity(intent);
                break;
            case R.id.ll_work_score:
                intent = new Intent(getActivity(), HworkGradeActivity.class);
                intent.putExtra("title", "作业成绩");
                intent.putExtra("Tag", 1 + "");
                intent.putExtra("USERID", USERID);
                intent.putExtra("USERNAME", USERNAME);
                intent.putExtra("className", className);
                urlStr = Url.baseUrl + Url.workScorUrl;
                intent.putExtra("url", urlStr);
                startActivity(intent);
                break;
            case R.id.ll_stage_text:
                intent = new Intent(getActivity(), HworkGradeActivity.class);
                intent.putExtra("title", "阶段测试");
                intent.putExtra("Tag", 2 + "");
                intent.putExtra("USERID", USERID);
                intent.putExtra("USERNAME", USERNAME);
                intent.putExtra("className", className);
                urlStr = Url.baseUrl + Url.stateTestUrl;
                intent.putExtra("url", urlStr);
                startActivity(intent);
                break;
            case R.id.ll_moke_score:
                intent = new Intent(getActivity(), HworkGradeActivity.class);
                intent.putExtra("title", "模考成绩");
                intent.putExtra("Tag", 3 + "");
                intent.putExtra("USERID", USERID);
                intent.putExtra("USERNAME", USERNAME);
                intent.putExtra("className", className);
                urlStr = Url.baseUrl + Url.mokeTestUrl;
                intent.putExtra("url", urlStr);
                startActivity(intent);
                break;
            case R.id.ll_exam_score:
                intent = new Intent(getActivity(), FirstTextActivity.class);
                intent.putExtra("title", "考试成绩");
                intent.putExtra("USERID", USERID);
                intent.putExtra("Tag", 4 + "");
                intent.putExtra("USERNAME", USERNAME);
                intent.putExtra("className", className);
                urlStr = Url.baseUrl + Url.examscoreUrl;
                intent.putExtra("url", urlStr);
                startActivity(intent);
                break;
        }
    }
}
