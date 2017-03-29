package com.example.bailyhome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bailyhome.R;
import com.example.bailyhome.adapter.FragmentTabAdapter;
import com.example.bailyhome.adapter.HworkGradeTimeBaseAdapter;
import com.example.bailyhome.model.OnDataListener;
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
 * Created by Administrator on 2016/5/16 0016.
 * <p/>
 * 成绩中 作业成绩、阶段测试、模考成绩 时间轴
 */
public class HworkGradeTimeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView hworkGradeTimeList;
    private List<Map<String, String>> listData = null;
    private TextView tvText;
    private String tag, jsonObject, url;
    private String USERID, USERNAME, className;
    private SwipeRefreshLayout hworkSwipeRefLl;
    private HworkGradeTimeBaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hwork_grade_time, container, false);
        hworkGradeTimeList = (ListView) view.findViewById(R.id.hwork_grade_time_list);
//        hworkSwipeRefLl = (SwipeRefreshLayout) view.findViewById(R.id.hwork_swipe_ref_ll);
//        hworkSwipeRefLl.setOnRefreshListener(this);
        tvText = (TextView) view.findViewById(R.id.tv_text);
        initData();
        return view;
    }

    public void initData() {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            tag = bundle.getString("str");
            jsonObject = bundle.getString("jsonObject");
            url = bundle.getString("url");
            USERID = bundle.getString("USERID");
            USERNAME = bundle.getString("USERNAME");
            className = bundle.getString("className");
            Log.e("jsonObject-333", jsonObject + "////" + tag);
            handleJsonDatas(jsonObject, "1");
        } else {
            tvText.setVisibility(View.VISIBLE);
            tvText.setText("没有该模块数据");
        }
    }

    public void handleJsonDatas(String str1, String str2) {
        if (str1 != null && str1.length() >= 0) {
            try {
                JSONObject jsonObject1 = new JSONObject(str1);
                Log.e("hwork-response-2-", jsonObject1.toString());
                org.json.JSONArray array = jsonObject1.getJSONArray("rows");
                if (array != null && array.length() > 0) {
                    listData = new ArrayList<Map<String, String>>();
                    if ("1".equals(tag)) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("classDate", (object.has("AFM_12")) ? (object.getString("AFM_12").substring(0,10)) : "");
                            map.put("classHour", (object.has("AFM_13")) ? object.getString("AFM_13") : "");
                            map.put("testResult", (object.has("AFM_4")) ? object.getString("AFM_4") : "");
                            map.put("finishRate", (object.has("AFM_5")) ? object.getString("AFM_5") : "");
                            listData.add(map);
                        }
                    } else if ("2".equals(tag)) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("className", (object.has("AFM_17")) ? object.getString("AFM_17") : "");
                            //上交时间
                            if (object.has("AFM_18")) {
                                map.put("classDate", object.getString("AFM_18"));
                            } else {
                                map.put("classDate", "");
                            }
                            if (object.has("AFM_2")) {
                                map.put("testResult", object.getString("AFM_2"));
                            } else {
                                map.put("testResult", "");
                            }
                            if (object.has("AFM_6")) {
                                map.put("publishDate", object.getString("AFM_6").substring(0,10));//发布时间
                            } else {
                                map.put("publishDate", "");//发布时间
                            }
                            if (object.has("AFM_19")) {
                                map.put("correctDate", object.getString("AFM_19"));//批改时间
                            } else {
                                map.put("correctDate", "");
                            }
                            map.put("teacher", (object.has("AFM_20")) ? object.getString("AFM_20") : "");
                            listData.add(map);
                        }
                    } else if ("3".equals(tag)) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("classDate", (object.has("AFM_17")) ? (object.getString("AFM_17")).substring(0,10) : "");
                            map.put("testResult", (object.has("AFM_18")) ? object.getString("AFM_18") : "");
                            map.put("mockFeedback", (object.has("AFM_19")) ? object.getString("AFM_19") : "");
                            listData.add(map);
                        }
                    }
                    if ("1".equals(str2)) {
                        setAdapterLists(listData);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    tvText.setVisibility(View.VISIBLE);
                    tvText.setText("没有该模块数据");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            tvText.setVisibility(View.VISIBLE);
            tvText.setText("没有该模块数据");
        }
    }

    public void setAdapterLists(List<Map<String, String>> listData) {
        adapter = new HworkGradeTimeBaseAdapter(getActivity(), listData, tag);
        hworkGradeTimeList.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        switch (tag) {
            case "1":
                USERID = "605";
                className = "SSAT一对一";
//                    url = url + "&fielterMainId=" + URLEncoder.encode(USERID, "UTF-8") +"&AFM_19_strVal_pld=" +  URLEncoder.encode(className, "UTF-8");
                url = url + "&fielterMainId=" + USERID + "&AFM_19_strVal_pld=" + className;
                Log.e("taglllllll-url1", url);
                String urls = "http://101.200.120.92:8080/edus_auto/model_ajaxList.do?&tableId=9547&pageId=6106&AFM_19_2_andOr=0&AFM_19_strCond_pld=0&AFM_19_strVal_pld=SSAT一对一&source=1&fielterMainId=605";
                getUrlData(urls, "", "", "", "");
//               Log.e("taglllllll-url1", urls);
//                    getUrlData(bundle, url, "AFM_19_strVal_pld", "fielterMainId", "SSAT一对一", "605");
                break;
            case "2":
                // String urls = "http://101.200.120.92:8080/edus_auto/model_ajaxList.do?&tableId=17655&pageId=6051&AFM_19_2_andOr=0&AFM_19_strCond_pld=0&AFM_19_strVal_pld=ACT%E4%B8%80%E5%AF%B9%E4%B8%80&AFM_20_2_andOr=0&AFM_20_strCond_pld=0&AFM_20_strVal_pld=%E5%AD%A6%E5%91%98_0514001&source=1";
                getUrlData(url, "fielterMainId", "AFM_21_strVal_pld", USERID, className);
                break;
            case "3":
                String mokeName = className.substring(0, (className.length() - 3)) + "模考讲解";
                Log.e("mokeName-", mokeName);
//                String urls = "http://101.200.120.92:8080/edus_auto/model_ajaxList.do?&tableId=9547&pageId=6107&AFM_19_2_andOr=0&AFM_19_strCond_pld=0&AFM_20_2_andOr=0&AFM_20_strCond_pld=0&source=1";
                getUrlData(url, "fielterMainId", "AFM_19_strVal_pld", USERID, mokeName);
                break;
            default:
                break;
        }
        hworkSwipeRefLl.setRefreshing(false);
    }

    public void getUrlData(String urls, String param1, String param2, String val1, String val2) {

        // RequestCall call = OkHttpUtils.getInstance().post().url(urls).build();
//            RequestCall call = OkHttpUtils.getInstance().post().url(urls).addParams("AFM_20_strVal_pld", URLEncoder.encode("学员042061", "UTF-8")).addParams("AFM_19_strVal_pld", URLEncoder.encode("SSAT一对一", "UTF-8")).build();
        RequestCall call = OkHttpUtils.getInstance().post().url(urls).addParams(param1, val1).addParams(param2, val2).build();

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
                Log.e("hwork-response", response.toString());
                handleJsonDatas(response.toString(), "3");
            }
        });
    }
}
