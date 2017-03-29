package com.example.bailyhome.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.bailyhome.CoursePackageDetail;
import com.example.bailyhome.R;
import com.example.bailyhome.adapter.CourseAdapter;
import com.example.bailyhome.adapter.MyItemRecyclerViewAdapter;
import com.example.bailyhome.adapter.StuInfoAdpter;
import com.example.bailyhome.bean.SerializableMap;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.model.OnDataListener;
import com.example.bailyhome.view.LoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import noman.weekcalendar.WeekDateChaListener;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/12 0012.
 *
 * 课程包界面
 */
public class CoursePackageFm extends Fragment implements OnDataListener,View.OnClickListener{
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Map<String,Object>> mDatas;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_course_package,null);
        init(view);
        requestCoursePackageData(Url.baseUrl+Url.coursePackage);
        return view;

    }

    private void init(View view){
        sharedPreferences=getActivity().getSharedPreferences("userInfo",getActivity().MODE_PRIVATE);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.myTheme,R.color.bg_color,R.color.contents_text);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestCoursePackageData(Url.baseUrl+Url.coursePackage);
            }
        });
    }

    //请求课程包数据
    private void requestCoursePackageData(String url){
        swipeRefreshLayout.setRefreshing(true);
        RequestCall call= OkHttpUtils.getInstance().post().url(url)
                .addParams("fielterMainId",sharedPreferences.getString("USERID",""))
                .build();
        call.execute(new Callback<Map<String,Object>>() {
            @Override
            public Map<String,Object> parseNetworkResponse(Response response) throws Exception {
                return JSON.parseObject(response.body().string(),Map.class);
            }

            @Override
            public void onError(Call call, Exception e) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(Map<String,Object> response) {
                Log.i("123","response===>"+response);
                swipeRefreshLayout.setRefreshing(false);
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
                String orderType=(String) map.get("DIC_AFM_27");
                if (!TextUtils.isEmpty(orderType)&&orderType.contains("续报")) {
                    map.put("add_hour",map.get("AFM_28"));
                }
                list1.add(map);
            }
        }

        mDatas=list1;
        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(list1,CoursePackageFm.this);
        recyclerView.setAdapter(myItemRecyclerViewAdapter);
    }

    /**
     * 判断List<Map<String,Object>>是否包含对应的map
     */
    private boolean containsMap(Map<String,Object> map,List<Map<String,Object>> list){
        String str=map.get("AFM_26")+"";
        for (Map<String,Object> map1:list){
            //课程名
            String str1=map1.get("AFM_26")+"";
            if (!str.equals("null")&&!str1.equals("null")&&str.equals(str1)) {
                //总课时
                int allHour1=(int)((Integer) map.get("AFM_28"));
                int allHour2=(int) map1.get("AFM_28");

                //购买课时
                int a=(int) map.get("AFM_29");
                int b=(int) map1.get("AFM_29");
                //已学课时
                int i=(int) map.get("AFM_8");
                int j=(int) map1.get("AFM_8");
                //剩余课时
                int x=(int) map.get("AFM_31");
                int y=(int) map1.get("AFM_31");
                //累加总课时
                map1.put("AFM_28",allHour1+allHour2);
                //累加购买课时
                map1.put("AFM_29",a+b);
                //累加已学课时
                map1.put("AFM_8",i+j);
                //累加剩余课时
                map1.put("AFM_31",x+y);

                //订单类型
                String orderType=(String) map.get("DIC_AFM_27");
                if (!TextUtils.isEmpty(orderType)&&orderType.contains("续报")){
                    //补时课时
                    int m=(int) map1.get("add_hour");
                    map1.put("add_hour",m+allHour1);
                }else if (!TextUtils.isEmpty(orderType)&&orderType.contains("新增")){
//                    map1.put("AFM_30",map.get("AFM_30"));
//                    map1.put("AFM_31",map.get("AFM_31"));
//                    map1.put("AFM_32",map.get("AFM_32"));
//                    map1.put("AFM_33",map.get("AFM_33"));
                    map1.put("AFM_40",map.get("AFM_40"));//教师
                    map1.put("AFM_36",map.get("AFM_36"));//校区
                    map1.put("AFM_26",map.get("AFM_26"));//课程名称
                    map1.put("AFM_38",map.get("AFM_38"));//阶段名称
                    map1.put("AFM_37",map.get("AFM_37"));//课程id
                    map1.put("AFM_39",map.get("AFM_39"));//阶段id
                    map1.put("AFM_42",map.get("AFM_42"));//当前阶段名称
                    map1.put("AFM_43",map.get("AFM_43"));//当前阶段id
                }
                return true;
            }

        }
        return false;
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
    public void onClick(View v) {
        int position=(int)v.getTag();
        Map<String,Object> map=mDatas.get(position);
        SerializableMap serializableMap=new SerializableMap();
        serializableMap.setMap(map);
        Intent intent=new Intent(getActivity(), CoursePackageDetail.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("SerializableMap",serializableMap);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
