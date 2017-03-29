package com.example.bailyhome;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bailyhome.adapter.FragmentTabAdapter;
import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.ViewMiddle;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.fragment.CourseFragment;
import com.example.bailyhome.fragment.MoreFragment;
import com.example.bailyhome.fragment.ScoreFragment;
import com.example.bailyhome.view.ExpandTabView;
import com.pgyersdk.update.PgyUpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private RadioGroup radioGroup;
    private Fragment couresFragment;//课程页面
    private Fragment moreFragment;//更多页面
    private Fragment scoreFragment;//成绩页面
    private LinearLayout layout_course_name;
    private List<Fragment> mFragments;//防止fragment容器
    FragmentTabAdapter tabAdapter;
    private TextView toolbarTextview, tv_title;
    NavigationView navigationView;
    ExpandTabView mExpandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewMiddle viewMiddle;
    private ArrayList<String> liststr = new ArrayList<String>();
    private org.json.JSONArray array = null;
    private Bundle scoreBundle = null, moreBundle = null;
    private String USERID, USERNAME, STU_SEX;
    private MyApplication myApplication;
    private RadioGroup radioGroupStudy;//学习模块标题栏切换课程表和课程包
    private String responseStr;
    private RelativeLayout nav_camera, nav_gallery, nav_slideshow, nav_manage, layout;
    private Button nav_exit;
    private PopupWindow stageNameWindow;
    private ImageView iv_on_off;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PgyUpdateManager.register(this);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.logo_left);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        // toggle.setDrawerIndicatorEnabled(false);
        // toggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        drawer.setDrawerListener(toggle);
        //toggle.syncState();


//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        USERID = intent.getStringExtra("USERID");
        USERNAME = intent.getStringExtra("USERNAME");
        STU_SEX = intent.getStringExtra("STU_SEX");
        name.setText(USERNAME);
        initVaule();
        ((RadioButton) radioGroup.findViewById(R.id.radio0)).setChecked(true);// 设置radiogroup的机制

    }

    private void initFragment(String str) {
        couresFragment = new CourseFragment();
        moreFragment = new MoreFragment();
        scoreFragment = new ScoreFragment();
        scoreBundle = new Bundle();
        scoreBundle.putString("rows", str);
        scoreBundle.putString("USERID", USERID);
        scoreBundle.putString("supClassName", liststr.get(0));
        scoreBundle.putString("USERNAME", USERNAME);
        scoreFragment.setArguments(scoreBundle);
        moreBundle = new Bundle();
        moreBundle.putString("USERID", USERID);
        moreBundle.putString("USERNAME", USERNAME);
        moreBundle.putStringArrayList("liststr", liststr);
        moreFragment.setArguments(moreBundle);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(couresFragment);
        mFragments.add(scoreFragment);
        mFragments.add(moreFragment);
        tabAdapter = new FragmentTabAdapter(this, mFragments, R.id.content, radioGroup);

//        mExpandTabView = new ExpandTabView(MainActivity.this);
//        final LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mlayout.addView(mExpandTabView, ll);
//        mExpandTabView.setVisibility(View.GONE);

        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                // TODO Auto-generated method stub
                super.OnRgsExtraCheckedChanged(radioGroup, checkedId, index);
                switch (checkedId) {
                    case R.id.radio0:
                        toolbarTextview.setVisibility(View.GONE);
//                        mExpandTabView.setVisibility(View.GONE);
                        layout_course_name.setVisibility(View.GONE);
                        radioGroupStudy.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radio1:
                        toolbarTextview.setVisibility(View.GONE);
//                        mExpandTabView.setVisibility(View.VISIBLE);
                        layout_course_name.setVisibility(View.VISIBLE);
                        radioGroupStudy.setVisibility(View.GONE);
                        break;
                    case R.id.radio2:
                        toolbarTextview.setVisibility(View.VISIBLE);
                        toolbarTextview.setText("更多");
//                        mExpandTabView.setVisibility(View.GONE);
                        layout_course_name.setVisibility(View.GONE);
                        radioGroupStudy.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    public void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);// 找到radiogroup控件
        toolbarTextview = (TextView) findViewById(R.id.toolbar_textview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        layout_course_name = (LinearLayout) findViewById(R.id.layout_course_name);
        iv_on_off = (ImageView) findViewById(R.id.iv_on_off);
        name = (TextView) findViewById(R.id.name);
        layout_course_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liststr != null && liststr.size() > 0) {
                    initWindow();
                }
            }
        });

        radioGroupStudy = (RadioGroup) findViewById(R.id.book_radioGroup);
        nav_exit = (Button) findViewById(R.id.nav_exit);
        nav_exit.setOnClickListener(this);
        nav_camera = (RelativeLayout) findViewById(R.id.nav_camera);
        nav_camera.setOnClickListener(this);
        nav_gallery = (RelativeLayout) findViewById(R.id.nav_gallery);
        nav_gallery.setOnClickListener(this);
        nav_slideshow = (RelativeLayout) findViewById(R.id.nav_slideshow);
        nav_slideshow.setOnClickListener(this);
        nav_manage = (RelativeLayout) findViewById(R.id.nav_manage);
        nav_manage.setOnClickListener(this);
        layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setOnClickListener(this);

        myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
    }

    private void initVaule() {
        RequestCall call = OkHttpUtils.getInstance().post().url(Url.baseUrl + Url.scoreUrl).addParams("fielterMainId", USERID).build();
        call.execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(MainActivity.this, "系统正在维护中", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object response) {
                try {
                    responseStr = response.toString();
                    JSONObject object = new JSONObject(responseStr);

                    array = object.getJSONArray("rows");
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject classObject = array.getJSONObject(i);
                            String className = classObject.getString("AFM_14");
                            if (!liststr.contains(className)) {
                                liststr.add(className);
                            }
                        }
                        // String str = array.getJSONObject(0).toString();
                        setThreadafterView(responseStr);
                    } else {
                        liststr.add("暂无课程");
                        setThreadafterView("");
                    }
                    tv_title.setText(liststr.get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setThreadafterView(String str) throws JSONException {
        initFragment(str);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                viewMiddle = new ViewMiddle(getApplicationContext(), liststr);
//                mViewArray.add(viewMiddle);
//                mExpandTabView.setValue(liststr, mViewArray);
//                mExpandTabView.setTitle(viewMiddle.getShowText(), 1);
//                mExpandTabView.setBackgroundColor(Color.BLUE);
//                initListener();
//            }
//        });
    }


    private void initListener() {
        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) throws Exception {
//                onRefresh(viewMiddle, showText, distance);
            }
        });
    }

    public void onRefresh(String showText, String distance) throws Exception {
        if (showText.equals("暂无课程")) {
            ((ScoreFragment) mFragments.get(1)).handleData("", "");
        } else {
            // ((ScoreFragment) mFragments.get(1)).handleData(array.getJSONObject(Integer.valueOf(distance)).toString());
            ((ScoreFragment) mFragments.get(1)).handleData(responseStr, showText);
        }
        Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        if (!mExpandTabView.onPressBack()) {
//            finish();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            toScan();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //跳转到扫描二维码界面
public void toScan(){
                Intent intent = new Intent(this, TestScanActivity.class);
            intent.putExtra("USERID", USERID);
            startActivity(intent);
}

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            Intent intent = new Intent(this, StuinfoActivity.class);
//            intent.putExtra("USERNAME",USERNAME);
//            intent.putExtra("USERID", USERID);
//            intent.putExtra("STU_SEX",STU_SEX);
//            startActivity(intent);
//        } else if (id == R.id.nav_gallery) {
//            Intent intent = new Intent(this, LeaveRecActivity.class);
//            intent.putExtra("USERID", USERID);
//            startActivity(intent);
//        } else if (id == R.id.nav_slideshow) {
//            Intent intent = new Intent(this, ReVisitRecActivity.class);
//            intent.putExtra("USERID", USERID);
//            startActivity(intent);
//        } else if (id == R.id.nav_manage) {
//            Intent intent = new Intent(this, ResetPwdActivity.class);
//            intent.putExtra("USERID", USERID);
//            startActivity(intent);
//        } else if (id == R.id.nav_exit) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//            myApplication = (MyApplication) getApplication();
//            myApplication.exitLogin(MainActivity.this);
//            MainActivity.this.finish();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    // 双击退出程序
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                // finish();// 两次按键小于2秒时，退出应用
                myApplication = (MyApplication) getApplication();
                myApplication.exitAppAll(MainActivity.this);
                //MyApplication.getInstance().exitAppAll(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWindow() {
        if (stageNameWindow != null && stageNameWindow.isShowing()) {
            stageNameWindow.dismiss();
        } else {
            View view = getLayoutInflater().inflate(R.layout.window_stage_name, null);
            ListView lv_stage_name = (ListView) view.findViewById(R.id.lv_stage_name);

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.course_name_item, R.id.tv_stage_name, liststr);
            lv_stage_name.setAdapter(adapter);

            lv_stage_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tv_title.setText(liststr.get(position));
                    try {
                        onRefresh(liststr.get(position), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stageNameWindow.dismiss();
                }
            });

            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            stageNameWindow = new PopupWindow(view, (width / 3) * 2,
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
            stageNameWindow.showAsDropDown(layout_course_name, layout_course_name.getWidth() / 2 - stageNameWindow.getWidth() / 2, 0);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, StuinfoActivity.class);
            intent.putExtra("USERNAME", USERNAME);
            intent.putExtra("USERID", USERID);
            intent.putExtra("STU_SEX", STU_SEX);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, LeaveRecActivity.class);
            intent.putExtra("USERID", USERID);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, ReVisitRecActivity.class);
            intent.putExtra("USERNAME", USERNAME);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, ResetPwdActivity.class);
            intent.putExtra("USERID", USERID);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            //关闭所有的activity并清空list
            myApplication = (MyApplication) getApplication();
            myApplication.exitLogin(MainActivity.this);
            MainActivity.this.finish();
//跳到登陆界面
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }
}
