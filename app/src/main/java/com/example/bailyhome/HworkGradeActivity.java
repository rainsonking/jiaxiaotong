package com.example.bailyhome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bailyhome.adapter.FragmentTabAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.fragment.FirstTextChartFragment;
import com.example.bailyhome.fragment.HworkGradeTimeFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/16 0016.
 * <p>
 * 作业成绩、阶段测试、模考成绩
 */
public class HworkGradeActivity extends BaseActivity implements View.OnClickListener{
    private TextView textHeader;
    private ImageView  menuImgRight;
    private Fragment hworkGradeTimeFragment;
    private Fragment firstTextChartFragment;
   // List<Fragment> mFragmentList;
   // FragmentTabAdapter tabAdapter;
//    private RadioGroup mFragmentTab;
//    private RadioButton mRadioButton;
    private String tag = "", url;
    private String USERID, USERNAME, className;
    private LinearLayout ll_layout_01, ll_layout_02,layout_title_left;
    private TextView tv1, tv2;
    private RadioButton rb1, rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_text);
        initView();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        textHeader.setText(title);
        tag = intent.getStringExtra("Tag");
        USERID = intent.getStringExtra("USERID");
        USERNAME = intent.getStringExtra("USERNAME");
        className = intent.getStringExtra("className");

        url = intent.getStringExtra("url");
        Log.e("url=hw", url);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initFrament();
    }

    private void initFrament() {
        getProgressDialog().show();

        hworkGradeTimeFragment = new HworkGradeTimeFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("str", tag);
        bundle.putString("url", url);
        bundle.putString("USERID", USERID);
        bundle.putString("USERNAME", USERNAME);
        bundle.putString("className", className);
        firstTextChartFragment = new FirstTextChartFragment();
        getUrlDataFir(bundle);
    }

    public void getUrlDataFir(final Bundle bundle) {
        try {
            switch (tag) {
                case "1":
                    Log.e("taglllllll-url1", url);
                    getUrlData(bundle, url, "AFM_17_strVal_pld", "AFM_16_strVal_pld", className, USERNAME);
                    break;
                case "2":
                    getUrlData(bundle, url, "fielterMainId", "AFM_22_strVal_pld", USERID, className);
                    break;
                case "3":
                    String mokeName = className.substring(0, (className.length() - 3)) + "模考讲解";
                    Log.e("mokeName-", mokeName);
                    getUrlData(bundle, url, "fielterMainId", "AFM_20_strVal_pld", USERID, mokeName);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getUrlData(final Bundle bundle, String urls, String param1, String param2, String val1, String val2) {

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
                bundle.putString("jsonObject", response.toString());
                hworkGradeTimeFragment.setArguments(bundle);
                firstTextChartFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_content, hworkGradeTimeFragment);
                transaction.commit();
                rb1.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb2.setTextColor(getResources().getColor(R.color.score_night));
                ll_layout_01.setOnClickListener(HworkGradeActivity.this);
                ll_layout_02.setOnClickListener(HworkGradeActivity.this);
                rb1.setOnClickListener(HworkGradeActivity.this);
                rb2.setOnClickListener(HworkGradeActivity.this);
                rb1= (RadioButton) findViewById(R.id.rb1);
                rb2= (RadioButton) findViewById(R.id.rb2);
//                mFragmentList = new ArrayList<Fragment>();
//                mFragmentList.add(hworkGradeTimeFragment);
//                mFragmentList.add(firstTextChartFragment);
               // tabAdapter = new FragmentTabAdapter(HworkGradeActivity.this, mFragmentList, R.id.fl_content, mFragmentTab);

            getProgressDialog().dismiss();
            }
        });
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
//        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);
        ll_layout_01 = (LinearLayout) findViewById(R.id.ll_layout_01);
        ll_layout_02 = (LinearLayout) findViewById(R.id.ll_layout_02);
        tv1 = (TextView) findViewById(R.id.tv_text_01);
        tv2 = (TextView) findViewById(R.id.tv_text_02);
        rb1= (RadioButton) findViewById(R.id.rb1);
        rb2= (RadioButton) findViewById(R.id.rb2);
//        mFragmentTab = (RadioGroup) findViewById(R.id.rg_tab);
     //   ((RadioButton) mFragmentTab.findViewById(R.id.rb1)).setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.ll_layout_01:
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.fl_content, hworkGradeTimeFragment);
                rb1.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb2.setTextColor(getResources().getColor(R.color.score_night));
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_layout_02:
                transaction.replace(R.id.fl_content, firstTextChartFragment);
                rb2.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb1.setTextColor(getResources().getColor(R.color.score_night));
                tv2.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.INVISIBLE);
                break;
            case R.id.rb1:
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.fl_content, hworkGradeTimeFragment);
                rb1.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb2.setTextColor(getResources().getColor(R.color.score_night));
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                break;
            case R.id.rb2:
                transaction.replace(R.id.fl_content, firstTextChartFragment);
                rb2.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb1.setTextColor(getResources().getColor(R.color.score_night));
                tv2.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
