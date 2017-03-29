package com.example.bailyhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.bailyhome.base.BaseActivity;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public class LogoActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; //睡时间为3秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                if (TextUtils.isEmpty(userName)||TextUtils.isEmpty(passWord)) {
//                    Intent intent=new Intent();
//                    intent.setClass(WelcomeActivity.this,LoginActivity.class);
//                    WelcomeActivity.this.startActivity(intent);
//                    WelcomeActivity.this.finish();
//
//                } else {
//                    Intent intent=new Intent();
//                    intent.setClass(WelcomeActivity.this,CustomList.class);
//                    WelcomeActivity.this.startActivity(intent);
//                    WelcomeActivity.this.finish();
//                }

                Intent intent = new Intent();
                intent.setClass(LogoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_DISPLAY_LENGHT);
    }

}
