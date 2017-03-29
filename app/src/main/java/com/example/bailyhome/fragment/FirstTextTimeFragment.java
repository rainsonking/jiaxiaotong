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

import com.example.bailyhome.R;
import com.example.bailyhome.adapter.FirstTextTimeBaseAdapter;
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
 * 成绩中 课首测试、考试成绩 时间轴
 */
public class FirstTextTimeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView firstTextTimeList;
    private List<Map<String, String>> listData = null;
    private TextView tvText;
    private String tag, jsonObject, url;
    private String USERID, USERNAME, className;
    private SwipeRefreshLayout firstSwipeRefLl;
    private FirstTextTimeBaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_text_time, container, false);
        firstTextTimeList = (ListView) view.findViewById(R.id.first_text_time_list);
        tvText = (TextView) view.findViewById(R.id.tv_text);
//        firstSwipeRefLl = (SwipeRefreshLayout) view.findViewById(R.id.first_swipe_ref_ll);
//        firstSwipeRefLl.setOnRefreshListener(this);
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
            handleJsonObj(jsonObject, "1");
        } else {
            tvText.setVisibility(View.VISIBLE);
            tvText.setText("没有该模块数据");
        }
    }

    public void handleJsonObj(String jsonObj, String str1) {
        if (jsonObj != null && jsonObj.length() >= 0) {
            try {
                JSONObject jsonObject1 = new JSONObject(jsonObj);
                org.json.JSONArray array = jsonObject1.getJSONArray("rows");
                if (array != null && array.length() > 0) {
                    listData = new ArrayList<Map<String, String>>();
                    if ("0".equals(tag)) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            if (object.has("AFM_2")) {
                                map.put("classDate", object.getString("AFM_2").substring(0,10));
                            } else {
                                map.put("classDate", "");
                            }
                            if (object.has("AFM_10")) {
                                map.put("classHour", object.getString("AFM_10"));
                            } else {
                                map.put("classHour", "");
                            }
                            if (object.has("AFM_12")) {
                                map.put("testResult", object.getString("AFM_12"));
                            } else {
                                map.put("testResult", "");
                            }
                            listData.add(map);
                        }
                    } else if ("4".equals(tag)) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            if (object.has("AFM_3")) {
                                map.put("classDate", object.getString("AFM_3").substring(0,10));
                            } else {
                                map.put("classDate", "");
                            }
                            if (object.has("AFM_4")) {
                                map.put("testResult", object.getString("AFM_4"));
                            } else {
                                map.put("testResult", "");
                            }
                            listData.add(map);
                        }
                    }
                    if ("1".equals(str1)) {
                        adapter = new FirstTextTimeBaseAdapter(listData, getActivity(), tag);
                        firstTextTimeList.setAdapter(adapter);
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

    @Override
    public void onRefresh() {
        if ("0".equals(tag)) {
            getUrlData(url, "fielterMainId", "AFM_14_strVal_pld", USERID, className);

        } else if ("4".equals(tag)) {
            getUrlData(url, "AFM_7_strVal_pld", "AFM_8_strVal_pld", USERNAME, className);
        }
        firstSwipeRefLl.setRefreshing(false);
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
                Log.e("hwork-response-rrr", response.toString());
                handleJsonObj(response.toString(),"3");
            }
        });
    }
}
