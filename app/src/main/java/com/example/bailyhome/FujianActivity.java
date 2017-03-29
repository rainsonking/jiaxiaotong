package com.example.bailyhome;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bailyhome.adapter.FujianAdapter;
import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.bean.SerializableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FujianActivity extends BaseActivity {

    TextView tv_fujianName,tv_date,text_header;
    Map<String,Object> map;
    ListView listView;
    ImageView menu_img;
    public MyApplication myApplication;
    public LinearLayout layout_title_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fujian);
        Intent intent=getIntent();
        if (intent!=null) {
            SerializableMap serializableMap=(SerializableMap) intent.getSerializableExtra("SerializableMap");
            map=serializableMap.getMap();
        }
        initView();
        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
        if (map!=null) {
            List<Map<String,Object>> mapList=new ArrayList<>();
            Log.e("mapfujian",map.toString());
            String fujianName=(String)map.get("AFM_88");
            String date=(String)map.get("AFM_87");
            if (!TextUtils.isEmpty(fujianName)) {
                String[] names=fujianName.split(",");
                for (int i=0;i<names.length;i++){
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("name",names[i]);
                    map1.put("date",date);
                    mapList.add(map1);
                }
                listView.setAdapter(new FujianAdapter(FujianActivity.this,mapList));
            }
        }
    }

    public void initView() {
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_header = (TextView) findViewById(R.id.text_header);
        text_header.setText("附件");
        tv_fujianName=(TextView)findViewById(R.id.tv_fujian_name);
        tv_date=(TextView)findViewById(R.id.tv_date);
        listView=(ListView)findViewById(R.id.listView);
    }
}
