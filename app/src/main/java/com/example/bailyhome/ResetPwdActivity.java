package com.example.bailyhome;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bailyhome.application.MyApplication;
import com.example.bailyhome.base.BaseActivity;
import com.example.bailyhome.config.Url;
import com.example.bailyhome.view.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/18 0018.
 * <p/>
 * 重置密码
 */
public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {
    private TextView textHeader;
    private ImageView menuImgRight;
    private EditText edOldpwd, edNewpwd, edConfirmpwd;
    private LinearLayout btnConfirm;
    private LinearLayout btn_commit_enabled;
    private LinearLayout layout_title_left;
    // private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        //  myApplication = (MyApplication) getApplication();
        initView();
        textHeader.setText("重置密码");
        btnConfirm.setOnClickListener(this);
        layout_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        textHeader = (TextView) findViewById(R.id.text_header);
        layout_title_left = (LinearLayout) findViewById(R.id.layout_title_left);
        menuImgRight = (ImageView) findViewById(R.id.menu_img_right);

        edOldpwd = (EditText) findViewById(R.id.edt_old_psw);
        edNewpwd = (EditText) findViewById(R.id.edt_new_paw);
        edConfirmpwd = (EditText) findViewById(R.id.edt_again_new_psw);
        btnConfirm = (LinearLayout) findViewById(R.id.btn_commit);
        btn_commit_enabled = (LinearLayout) findViewById(R.id.btn_commit_enabled);

        edOldpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && edNewpwd.getText().length() > 0 && edConfirmpwd.getText().length() > 0) {
                    btn_commit_enabled.setVisibility(View.GONE);
                    btnConfirm.setVisibility(View.VISIBLE);
                } else {
                    btn_commit_enabled.setVisibility(View.VISIBLE);
                    btnConfirm.setVisibility(View.GONE);

                }
            }
        });

        edNewpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && edOldpwd.getText().length() > 0 && edConfirmpwd.getText().length() > 0) {
                    btn_commit_enabled.setVisibility(View.GONE);
                    btnConfirm.setVisibility(View.VISIBLE);
                } else {
                    btn_commit_enabled.setVisibility(View.VISIBLE);
                    btnConfirm.setVisibility(View.GONE);
                }
            }
        });

        edConfirmpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && edOldpwd.getText().length() > 0 && edNewpwd.getText().length() > 0) {
                    btn_commit_enabled.setVisibility(View.GONE);
                    btnConfirm.setVisibility(View.VISIBLE);
                } else {
                    btn_commit_enabled.setVisibility(View.VISIBLE);
                    btnConfirm.setVisibility(View.GONE);
                }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String oldPsw = edOldpwd.getText().toString();
                String newPsw = edNewpwd.getText().toString();
                String againNewPsw = edConfirmpwd.getText().toString();
                String oldPswLocal = getLoginUserSharedPre().getString("changePassword", "");
                if (TextUtils.isEmpty(oldPsw)) {
                    Toast.makeText(this, "请填写原密码！", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(oldPsw, oldPswLocal)) {
                    Toast.makeText(this, "旧密码错误！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(newPsw)) {
                    Toast.makeText(this, "请填写新密码！", Toast.LENGTH_SHORT).show();
                } else if (!newPsw.matches("^[0-9_a-zA-Z]{6,20}$")) {
                    Toast.makeText(this, "密码格式不正确！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(againNewPsw)) {
                    Toast.makeText(this, "请确认新密码！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.equals(oldPsw, newPsw)) {
                    Toast.makeText(this, "新密码和原密码重复！", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(newPsw, againNewPsw)) {
                    Toast.makeText(this, "新密码和确认密码不一致！", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("是否要修改密码？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestData(Url.baseUrl + Url.changePsw);
                            Log.e("dialog-", Url.changePsw);

                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                break;
            default:
                break;
        }
    }

    private void requestData(String url) {
        if (!hasInternetConnected()) {
            Toast.makeText(this, "当前网络不可用，请检查网络！", Toast.LENGTH_LONG).show();
            return;
        }
        getProgressDialog().show();
        Log.i("123", "test====>" + url);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonData) {
                        getProgressDialog().dismiss();
                        Log.i("123", "jsonData====>" + jsonData);
                        try {
                            JSONObject object = new JSONObject(jsonData);
                            String message = object.getString("message");
                            if (message.equals("密码修改成功")) {
                                edOldpwd.setText("");
                                edNewpwd.setText("");
                                edConfirmpwd.setText("");
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPwdActivity.this);
                                builder.setMessage("修改成功！");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        SharedPreferences.Editor editor = getLoginUserSharedPre().edit();
                                        editor.putString("stuPassword", "");
                                        editor.commit();
                                        //关闭所有的activity
                                        myApplication = (MyApplication) getApplication();
                                        myApplication.exitLogin(ResetPwdActivity.this);
                                        ResetPwdActivity.this.finish();
                                        //退到登陆界面
                                        Intent intent = new Intent();
                                        intent.setClass(ResetPwdActivity.this, LoginActivity.class);
                                        startActivity(intent);


                                    }
                                });

                                builder.create().show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPwdActivity.this);
                                builder.setMessage("修改失败！");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getProgressDialog().dismiss();
                Log.i("123", "jsonDataERROR" + volleyError.toString());
                VolleySingleton.onErrorResponseMessege(ResetPwdActivity.this, volleyError);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String stuId = getLoginUserSharedPre().getString("USERID", "");
                map.put("stuId", stuId);
                map.put("oldPassword", edOldpwd.getText().toString());
                map.put("newPassword", edConfirmpwd.getText().toString());
//                map.put("pageId","1436");
//                map.put("fielterMainId",stuId);
//                map.put("source","1");
                return map;
            }
        };
        VolleySingleton.getVolleySingleton(this.getApplicationContext()).addToRequestQueue(mStringRequest);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
        }

        return super.onOptionsItemSelected(item);
    }

}
