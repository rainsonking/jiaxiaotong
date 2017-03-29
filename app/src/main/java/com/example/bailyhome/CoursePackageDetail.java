package com.example.bailyhome;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.bailyhome.adapter.CoursePackageDetailAdapter;
import com.alibaba.fastjson.JSON;
import com.example.bailyhome.adapter.MyItemRecyclerViewAdapter;
import com.example.bailyhome.adapter.MyListViewAdpter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.view.LoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class CoursePackageDetail extends BaseActivity {

    //    private CoursePackageDetailAdapter coursePackageDetailAdapter;
//    private RecyclerView recyclerView;
    private List<Map<String,Object>> mDatas;
    private ListView listView;
    private MyListViewAdpter myListViewAdpter;
    private PopupWindow stageNameWindow;
    private ImageView iv_on_off;
    private TextView tv_title;
    private LinearLayout layout_stage_name,layout_title_left_third;
    Map<String,Object> map;
    String[] stageIds;//阶段id数组
    String[] stageNames;//阶段名数组
    String AFM_89_searchEleId;//课程id
    String stageName;//阶段名


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_package_detail);

        listView=(ListView)findViewById(R.id.list);
        listView.setItemsCanFocus(true);

        layout_title_left_third= (LinearLayout) findViewById(R.id.layout_title_left_third);
        layout_title_left_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_on_off=(ImageView) findViewById(R.id.iv_on_off);
        iv_on_off.setBackgroundResource(R.mipmap.img_top);
        tv_title=(TextView) findViewById(R.id.tv_title);
        layout_stage_name=(LinearLayout) findViewById(R.id.layout_stage_name);
        layout_stage_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化弹出层
                initWindow();
            }
        });

        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle=intent.getExtras();
            if (bundle!=null) {
                SerializableMap serializableMap=(SerializableMap) bundle.getSerializable("SerializableMap");
                map=serializableMap.getMap();
            }
        }

        if (map != null) {
            String stageName=(String) map.get("AFM_38");
            String stageId=(String) map.get("AFM_39");
            if (!TextUtils.isEmpty(stageId) && !TextUtils.isEmpty(stageName)) {
                stageNames = stageName.split(",");
                stageIds = stageId.split(",");
            }
            AFM_89_searchEleId = map.get("AFM_37") + "";
            String defaultStage=map.get("AFM_42")+"";
            String AFM_91_searchEleId = map.get("AFM_43")+"";
            if (!defaultStage.equals("null")&&!AFM_91_searchEleId.equals("null")) {
                tv_title.setText(defaultStage);
                this.stageName=defaultStage;
            }else if (!TextUtils.isEmpty(stageName)&&!TextUtils.isEmpty(stageId)) {
                tv_title.setText(stageNames[0]);
                this.stageName=stageNames[0];
                AFM_91_searchEleId=stageIds[0];
            } else {
                tv_title.setText("课程详情");
                iv_on_off.setVisibility(View.GONE);
            }
            requestButtomData(Url.baseUrl + Url.coursePackageDetailButtom, AFM_89_searchEleId, AFM_91_searchEleId);
        } else {
            myListViewAdpter=new MyListViewAdpter(CoursePackageDetail.this);
            listView.setAdapter(myListViewAdpter);
        }


    }

    @Override
    public void initView() {

    }

    //请求本页面下半部分数据 AFM_89_searchEleId:课程id AFM_91_searchEleId:阶段id
    private void requestButtomData(String url,String AFM_89_searchEleId,final String AFM_91_searchEleId){
        getProgressDialog().show();
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("AFM_91_searchEleId",AFM_89_searchEleId)
                .addParams("AFM_93_searchEleId",AFM_91_searchEleId)
                .addParams("AFM_92_strVal_pld",getLoginUserSharedPre().getString("USERNAME",""))
                .build();
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
//                String str=response.body().string();
//                String json=str.substring(0,str.indexOf("fieldAliasName")-2)+"}";
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {
                myListViewAdpter = new MyListViewAdpter(CoursePackageDetail.this, mDatas, map);
                myListViewAdpter.setMyData(AFM_91_searchEleId,stageName);
                listView.setAdapter(myListViewAdpter);
                getProgressDialog().dismiss();
            }

            @Override
            public void onResponse(Map<String,Object> response) {
                getProgressDialog().dismiss();
//                Log.i("123","response===>"+response);
                if (response!=null){
                    mDatas=(List<Map<String,Object>>)response.get("rows");
                }
                myListViewAdpter = new MyListViewAdpter(CoursePackageDetail.this, mDatas, map);
                myListViewAdpter.setMyData(AFM_91_searchEleId,stageName);
                listView.setAdapter(myListViewAdpter);
            }
        });
    }

    private void initWindow(){
        if (stageNameWindow != null && stageNameWindow.isShowing()) {
            stageNameWindow.dismiss();
        } else {
            View view=getLayoutInflater().inflate(R.layout.window_stage_name,null);
            ListView lv_stage_name=(ListView)view.findViewById(R.id.lv_stage_name);

            if (stageNames != null && stageIds != null&&!stageNames[0].equals("null")) {
                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.stage_name_item, R.id.tv_stage_name, stageNames);
                lv_stage_name.setAdapter(adapter);
            } else {
                return;
            }

            lv_stage_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (stageIds != null) {
                        requestButtomData(Url.baseUrl+Url.coursePackageDetailButtom,AFM_89_searchEleId,stageIds[position]);
                    }
                    tv_title.setText(stageNames!=null?stageNames[position]:"");
                    stageName=stageNames[position];
                    stageNameWindow.dismiss();
                }
            });

            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            stageNameWindow = new PopupWindow(view, (width/3)*2,
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
            stageNameWindow.showAsDropDown(layout_stage_name, layout_stage_name.getWidth()/2-stageNameWindow.getWidth()/2, 0);

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
}
