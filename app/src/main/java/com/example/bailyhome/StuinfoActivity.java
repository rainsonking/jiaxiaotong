package com.example.bailyhome;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.bailyhome.adapter.MyListViewAdpter;
import com.example.bailyhome.adapter.StuInfoAdpter;
import com.example.bailyhome.adapter.StuInfoBaseAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.config.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/18 0018.
 * <p/>
 * 学员信息
 */
public class StuinfoActivity extends BaseActivity {
    private List<Map<String,Object>> mDatas;
    private ListView listView;
    private StuInfoAdpter stuInfoAdpter;
    private TextView textHeader, tvText;
    private ImageView menuImgRight;
    private String USERNAME,STU_SEX;
    private LinearLayout layout_title_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_info);
        initView();
        USERNAME =getIntent().getStringExtra("USERNAME");
        STU_SEX = getIntent().getStringExtra("STU_SEX");
        textHeader.setText("学员信息");
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getProgressDialog().show();
        requestData(Url.baseUrl+Url.stuInfo);
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        listView=(ListView)findViewById(R.id.list);
        listView.setItemsCanFocus(true);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
    }

    //请求本页面下半部分数据
    private void requestData(String url){
        getProgressDialog().show();
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
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
                    mDatas=(List<Map<String,Object>>)response.get("rows");
                    if (mDatas!=null){
                        listQuchong(mDatas);
                    }
                }
            }
        });
    }

    /**
     * List<Map<String,Object>>去掉重复元素并将相同元素的某个属性求和
     */
    public void listQuchong(List<Map<String,Object>> list){
        List<Map<String,Object>> list1=new ArrayList<>();
        for (Map<String,Object> map:list) {
            boolean isContains=containsMap(map,list1);
            if (!isContains){
                //订单类型
                String orderType=(String) map.get("DIC_AFM_20");
                if (!TextUtils.isEmpty(orderType)&&orderType.contains("续报")) {
                    map.put("add_hour",map.get("AFM_21"));
                }
                list1.add(map);
            }
        }
        stuInfoAdpter=new StuInfoAdpter(StuinfoActivity.this,list1,USERNAME,STU_SEX);
        listView.setAdapter(stuInfoAdpter);

        getProgressDialog().dismiss();
    }

    /**
     * 判断List<Map<String,Object>>是否包含对应的map
     */
    private boolean containsMap(Map<String,Object> map,List<Map<String,Object>> list){
        String str=(String)map.get("AFM_19");
        for (Map<String,Object> map1:list){
            //课程名
            String str1=(String) map1.get("AFM_19");
            if (str!=null&&str1!=null&&str.equals(str1)) {
                //总课时
                int allHour1=(int) map.get("AFM_21");
                int allHour2=(int) map1.get("AFM_21");

                //购买课时
                int a=(int) map.get("AFM_22");
                int b=(int) map1.get("AFM_22");
                //已学课时
                int i=(int) map.get("AFM_8");
                int j=(int) map1.get("AFM_8");
                //剩余课时
                int x=(int) map.get("AFM_24");
                int y=(int) map1.get("AFM_24");
                //累加总课时
                map1.put("AFM_21",allHour1+allHour2);
                //累加购买课时
                map1.put("AFM_22",a+b);
                //累加已学课时
                map1.put("AFM_8",i+j);
                //累加剩余课时
                map1.put("AFM_24",x+y);

                //订单类型
                String orderType=(String) map.get("DIC_AFM_20");
                if (!TextUtils.isEmpty(orderType)&&orderType.contains("续报")){
                    //补时课时
                    int m=(int) map1.get("add_hour");
                    map1.put("add_hour",m+allHour1);
                }else if (!TextUtils.isEmpty(orderType)&&orderType.contains("新增")){
                    map1.put("AFM_25",map.get("AFM_25"));
                    map1.put("AFM_28",map.get("AFM_28"));
                    map1.put("AFM_27",map.get("AFM_27"));
                    map1.put("AFM_26",map.get("AFM_26"));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
