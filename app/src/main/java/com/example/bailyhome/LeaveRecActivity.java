package com.example.bailyhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.bailyhome.adapter.LeaveRecBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
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
public class  LeaveRecActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView textHeader, tvText;
    private ImageView  menuImgRight;
    private ListView listView;
    private LeaveRecBaseAdapter adapter;
    private List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
    private String USERID;
    private SwipeRefreshLayout swipeRefreshLl;
    private LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_record);
        initView();
        textHeader.setText("请假记录");
        Intent intent = getIntent();
        USERID = intent.getStringExtra("USERID");
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveRecActivity.this.finish();
            }
        });
        getProgressDialog().show();
        getUrlData("1");
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
//        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        listView = (ListView) findViewById(R.id.listview);
        tvText = (TextView) findViewById(R.id.tv_text);
        swipeRefreshLl = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_ll);
        swipeRefreshLl.setOnRefreshListener(this);
//        swipeRefreshLl.setColorSchemeColors(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
     //   swipeRefreshLl.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.red));
//        swipeRefreshLl.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    public void getUrlData(final String str1) {
        RequestCall call = OkHttpUtils.getInstance().post().url(Url.baseUrl + Url.leaveRecUrl).addParams("fielterMainId", USERID).build();
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
                getJsonDatas(response, str1);
            }
        });
    }

    public void getJsonDatas(Object response, String str1) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            org.json.JSONArray jsonArray = jsonObject.getJSONArray("rows");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("className", (object.has("AFM_14")) ? (object.getString("AFM_14")) : "");
                    map.put("teacher", (object.has("AFM_15")) ? (object.getString("AFM_15")) : "");
                    map.put("school", (object.has("AFM_16")) ? (object.getString("AFM_16")) : "");
                    map.put("orderHour", (object.has("AFM_4")) ? (object.getString("AFM_4")) : "");
                    map.put("orderDate", (object.has("AFM_5")) ? (object.getString("AFM_5").substring(0, 10)) : "");
                    map.put("attendDesc", (object.has("AFM_17")) ? (object.getString("AFM_17")) : "");
                    map.put("leaveType", (object.has("DIC_AFM_7")) ? (object.getString("DIC_AFM_7")) : "");
                    map.put("leaveDemo", (object.has("AFM_8")) ? (object.getString("AFM_8")) : "");
                    map.put("leaveCause", (object.has("AFM_9")) ? (object.getString("AFM_9")) : "");
                    map.put("classTime", (object.has("AFM_18")) ? (object.getString("AFM_18")) : "");
                    map.put("leaveState", (object.has("DIC_AFM_12")) ? (object.getString("DIC_AFM_12")) : "");
                    map.put("stuName", (object.has("AFM_19")) ? (object.getString("AFM_19")) : "");
                    lists.add(map);
                }
                if ("3".equals(str1)){
                    adapter.notifyDataSetChanged();
                }else {
                    getAdapterLists(lists);
                }
            } else {
                getProgressDialog().dismiss();
                tvText.setVisibility(View.VISIBLE);
                tvText.setText("该模块暂时没有数据记录！！！");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAdapterLists(final List<Map<String, String>> list) {
        adapter = new LeaveRecBaseAdapter(LeaveRecActivity.this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, String> map = lists.get(i);
                Intent intent = new Intent(getContext(), LeaveRecInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("className", map.get("className"));
                bundle.putString("teacher", map.get("teacher"));
                bundle.putString("school", map.get("school"));
                bundle.putString("orderHour", map.get("orderHour"));
                bundle.putString("orderDate", map.get("orderDate"));
                bundle.putString("attendDesc", map.get("attendDesc"));
                bundle.putString("leaveType", map.get("leaveType"));
                bundle.putString("leaveDemo", map.get("leaveDemo"));
                bundle.putString("leaveCause", map.get("leaveCause"));
                bundle.putString("classTime", map.get("classTime"));
                bundle.putString("leaveState", map.get("leaveState"));
                bundle.putString("stuName", map.get("stuName"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getProgressDialog().dismiss();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onRefresh() {
       lists.clear();
        getUrlData("3");
        swipeRefreshLl.setRefreshing(false);
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
