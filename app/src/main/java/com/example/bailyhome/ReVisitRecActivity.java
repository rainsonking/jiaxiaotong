package com.example.bailyhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bailyhome.adapter.ReVisitRecBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.view.SelPopWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/18 0018.
 * <p/>
 * 回访记录列表
 */
public class ReVisitRecActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView textHeader, tvText, tvStr1, tvStr2, tvRestate, tvResRes;
    private LinearLayout layout_right, ll_menu_img_right;
    private ListView listView;
    private List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
    private ReVisitRecBaseAdapter adapter;
    private String USERID, reVstate, reVres;//回访阶段、回访结果;
    private SwipeRefreshLayout revisitSwipeRefreshLl;
    private LinearLayout layout_title_left;
    private int oldLeg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisit_record);
        initView();
        textHeader.setText("回访记录");

        ll_menu_img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param1 = tvStr1.getText().toString();
                String param2 = tvStr2.getText().toString();
                String val1 = tvRestate.getText().toString();
                String val2 = tvResRes.getText().toString();
                SelPopWindow addPopWindow = new SelPopWindow(ReVisitRecActivity.this, param1, param2, val1, val2);
                addPopWindow.showPopupWindow(ll_menu_img_right);

            }
        });
        Intent intent = getIntent();
        USERID = intent.getStringExtra("USERNAME");
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        reVstate = "";
        reVres = "";
        getProgressDialog().show();
        getUrlData(reVstate, reVres, "1");
    }

    public void setTvStr12(final String str1, final String str2, final String reVstate, final String reVres) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStr1.setText(str1);
                tvStr2.setText(str2);
                tvRestate.setText(reVstate);
                tvResRes.setText(reVres);
            }
        });
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        ll_menu_img_right = (LinearLayout) findViewById(R.id.ll_menu_img_right);
        ll_menu_img_right.setVisibility(View.VISIBLE);

        listView = (ListView) findViewById(R.id.listview);
        tvText = (TextView) findViewById(R.id.tv_text);
        tvStr1 = (TextView) findViewById(R.id.tv_str1);
        tvStr2 = (TextView) findViewById(R.id.tv_str2);
        tvRestate = (TextView) findViewById(R.id.tv_restate);
        tvResRes = (TextView) findViewById(R.id.tv_reres);
        revisitSwipeRefreshLl = (SwipeRefreshLayout) findViewById(R.id.revisit_swipe_refresh_ll);
        revisitSwipeRefreshLl.setOnRefreshListener(this);
    }

    public void getUrlData(String reVstate, String reVres, final String resource) {
        RequestCall call = OkHttpUtils.getInstance().post().url(Url.baseUrl + Url.reVisitRecUrl)
                .addParams("AFM_16_strVal_pld", USERID).addParams("AFM_1strCond_dic", reVstate).addParams("AFM_6strCond_dic", reVres)
                .build();
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
                Log.e("reson", resource.toString());
                lists.clear();
                handleJsonData(response, lists, resource);
            }
        });
    }

    public void handleJsonData(Object response, List<Map<String, String>> list, String resource) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            org.json.JSONArray jsonArray = jsonObject.getJSONArray("rows");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("className", (object.has("AFM_15")) ? (object.getString("AFM_15")) : null);
                    map.put("reVisitPsn", (object.has("DIC_AFM_5")) ? (object.getString("DIC_AFM_5")) : null);
                    map.put("reVisitDate", (object.has("AFM_2")) ? (object.getString("AFM_2").substring(0, 10)) : null);
                    map.put("reVisitTech", (object.has("AFM_12")) ? (object.getString("AFM_12")) : null);
                    map.put("reVisitRes", (object.has("DIC_AFM_6")) ? (object.getString("DIC_AFM_6")) : null);
                    map.put("reVisitRec", (object.has("AFM_7")) ? (object.getString("AFM_7")) : null);
                    map.put("resolvMethod", (object.has("AFM_8")) ? (object.getString("AFM_8")) : null);
                    map.put("followReVisit", (object.has("AFM_9")) ? (object.getString("AFM_9")) : null);
                    map.put("reVisitStage", (object.has("DIC_AFM_1")) ? (object.getString("DIC_AFM_1")) : null);
                    list.add(map);
                }
                oldLeg = list.size();
                if ("1".equals(resource)) {
                    setAdapterItem(list);
                } else {
                    adapter.notifyDataSetChanged();
                    tvText.setVisibility(View.GONE);
                }
            } else {
                if (oldLeg > 0) {
                    adapter.notifyDataSetChanged();
                }
                getProgressDialog().dismiss();
                tvText.setVisibility(View.VISIBLE);
                tvText.setText("暂时没有数据记录");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getProgressDialog().dismiss();
    }

    public void setAdapterItem(final List<Map<String, String>> list) {
        adapter = new ReVisitRecBaseAdapter(getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, String> map = list.get(i);
                Intent intent = new Intent(getContext(), ReVisitRecInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("className", map.get("className"));
                bundle.putString("reVisitPsn", map.get("reVisitPsn"));
                bundle.putString("reVisitDate", map.get("reVisitDate").substring(0, 10));
                bundle.putString("reVisitTech", map.get("reVisitTech"));
                bundle.putString("reVisitRes", map.get("reVisitRes"));
                bundle.putString("reVisitRec", map.get("reVisitRec"));
                bundle.putString("resolvMethod", map.get("resolvMethod"));
                bundle.putString("followReVisit", map.get("followReVisit"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
        getUrlData(reVstate, reVres, "3");
        revisitSwipeRefreshLl.setRefreshing(false);
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
