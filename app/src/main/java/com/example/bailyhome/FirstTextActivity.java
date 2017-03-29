package com.example.bailyhome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.bailyhome.adapter.FragmentTabAdapter;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.fragment.FirstTextChartFragment;
import com.example.bailyhome.fragment.FirstTextTimeFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/14 0014.
 * <p/>
 * 课首测试、考试成绩
 */
public class FirstTextActivity extends BaseActivity implements View.OnClickListener {
    private TextView textHeader;
    private ImageView  menuImgRight;
    private android.app.Fragment firstTextTimeFragment;
    private Fragment firstTextChartFragment;
    // List<Fragment> mFragmentList;
    //FragmentTabAdapter tabAdapter;
    //private RadioGroup mFragmentTab;

    private String tag = "", url;
    private String USERID, USERNAME, className;
    private FrameLayout fl_content;
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
        tag = intent.getStringExtra("Tag");
        textHeader.setText(title);
        url = intent.getStringExtra("url");
        USERID = intent.getStringExtra("USERID");
        USERNAME = intent.getStringExtra("USERNAME");
        className = intent.getStringExtra("className");
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
        firstTextTimeFragment = new FirstTextTimeFragment();
        firstTextChartFragment = new FirstTextChartFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("str", tag);
        bundle.putString("url", url);
        bundle.putString("USERID", USERID);
        bundle.putString("USERNAME", USERNAME);
        bundle.putString("className", className);
        getUrlDataFir(bundle);
//        handleUrlData();
    }

    public void handleUrlData() {
        new Thread() {
            public void run() {
                String respon = httpUrlConnection(url);
                try {
                    Looper.prepare();
                    if ("1".equals(respon)) {
                        Toast.makeText(FirstTextActivity.this, "网络链接失败！", Toast.LENGTH_LONG).show();
                    } else if ("0".equals(respon)) {
                        Toast.makeText(FirstTextActivity.this, "网络链接异常！", Toast.LENGTH_LONG).show();
                    } else {
                        JSONObject jsonObject = new JSONObject(respon);
                        org.json.JSONArray array = jsonObject.getJSONArray("rows");
                        if (array != null && array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                            }
                        }
                    }
//                    if (respon.equals("1"))
//                    {
//                        respon = respon + "！马上跳转到登陆界面...";
//                        System.out.println(sp.getString(Constant.PASSWORD, ""));
//
//                        Toast.makeText(ResetPwdActivity.this, respon, Toast.LENGTH_SHORT).show();
//                        if ("".equals(sp.getString(Constant.PASSWORD, "")))
//                        {
//                            new Thread()
//                            {
//                                public void run()
//                                {
//                                    try
//                                    {
//                                        Thread.sleep(1000);
//
//                                        myApplication = (MyApplication) getApplication();
//                                        myApplication.exitApp(mContext, false);
//
//                                    } catch (InterruptedException e)
//                                    {
//
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            }.start();
//
//                        }

//                    } else
//                    {
//                        Toast.makeText(ResetPwdActivity.this, respon, Toast.LENGTH_SHORT).show();
//                    }
                    Looper.loop();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    private String httpUrlConnection(String urlString) {
        String response = "";
        try {
//            String data = Constant.pageId + "=3531&" + Constant.tableId + "=448&" + Constant.mainId + "=" + stuId + "&" + Constant.t0_au_448_3531_32522 + "=" + newpwd;
            // String urlPath = urlString + userName + "&stuPassword=" + pwd;
            String urlS = "http://101.200.120.92:8080/edus_auto/model_ajaxList.do?&tableId=9547&pageId=6106&AFM_18_2_andOr=0&AFM_18_strCond_pld=0&AFM_18_strVal_pld=%E9%99%88%E5%98%89%E7%90%B3&AFM_19_2_andOr=0&AFM_19_strCond_pld=0&AFM_19_strVal_pld=%E9%9B%85%E6%80%9D%E4%B8%80%E5%AF%B9%E4%B8%80&source=1";
            // 建立连接
            URL url = new URL(urlS);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
//            String data = "AFM_18_strVal_pld=" + URLEncoder.encode("陈嘉琳", "UTF-8") + "&AFM_19_strVal_pld=" + URLEncoder.encode("雅思一对一", "UTF-8");
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求的头
//            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            // 设置请求的头
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            // setDoInput的默认值就是true
            // 获取输出流
            OutputStream os = urlConnection.getOutputStream();
//            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                response = result;
                Log.e("url--result", response);
            } else {
                response = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "0";
        }
        return response;
    }

    public void getUrlDataFir(final Bundle bundle) {
        try {
            if ("0".equals(tag)) {
                getUrlData(bundle, url, "fielterMainId", "AFM_15_strVal_pld", USERID, className);
            } else if ("4".equals(tag)) {
                getUrlData(bundle, url, "AFM_8_strVal_pld", "AFM_9_strVal_pld", USERNAME, className);
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
                Log.e("hwork-response-rrr", response.toString());
                bundle.putString("jsonObject", response.toString());
                firstTextTimeFragment.setArguments(bundle);
                firstTextChartFragment.setArguments(bundle);
//                mFragmentList = new ArrayList<Fragment>();
//                mFragmentList.add(firstTextTimeFragment);
//                mFragmentList.add(firstTextChartFragment);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_content, firstTextTimeFragment);
                transaction.commit();
                rb1.setTextColor(getResources().getColor(R.color.font_selector_score));
                rb2.setTextColor(getResources().getColor(R.color.score_night));
                ll_layout_01.setOnClickListener(FirstTextActivity.this);
                ll_layout_02.setOnClickListener(FirstTextActivity.this);
                rb1.setOnClickListener(FirstTextActivity.this);
                rb2.setOnClickListener(FirstTextActivity.this);
                getProgressDialog().dismiss();
                //  tabAdapter = new FragmentTabAdapter(FirstTextActivity.this, mFragmentList, R.id.fl_content, mFragmentTab);
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
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);

//        mFragmentTab = (RadioGroup) findViewById(R.id.rg_tab);
//        ((RadioButton) mFragmentTab.findViewById(R.id.rb1)).setChecked(true);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.ll_layout_01:
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.fl_content, firstTextTimeFragment);
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
                transaction.replace(R.id.fl_content, firstTextTimeFragment);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return super.getProgressDialog();
    }
}
