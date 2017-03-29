package com.example.bailyhome.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.pgyersdk.crash.PgyCrashManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class MyApplication extends Application {
    Context mContext;
    public static List<Activity> mActivityList = new ArrayList<Activity>();
    private static MyApplication instance;
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {
        super.onCreate();
        CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                //其他配置
                .build();
        PgyCrashManager.register(this);
        OkHttpUtils.getInstance(okHttpClient);
//		 初始化ImageLoader
        initImageLoader(this);

    }

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    private void initImageLoader(Context context) {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3).threadPriority(3).memoryCache(new WeakMemoryCache()).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        ImageLoader.getInstance().init(config);
    }

    // 开启的activity 添加到List集合中
    public void addActivity(Activity activity) {
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity);
        }
    }

    // 遍历所有Activity并finish
    public void exitApp(Context context, boolean isAll) {
        Activity login = null;
        for (Activity activity : mActivityList) {
        }
        mActivityList.clear();
        mActivityList.add(login);
        if (isAll) {
            System.exit(0);
        }
    }

    public void exitLogin(Context context) {
//        Activity login = null;
//        for (Activity activity : mActivityList) {
//        }
//        mActivityList.clear();
//        mActivityList.add(login);
        Activity login = (Activity) context;
        for (Activity activity : mActivityList) {
            if ((activity != null)) {
                activity.finish();
            }
        }
        mActivityList.clear();
       // mActivityList.add(login);
    }

    @SuppressWarnings("deprecation")
    public void exitAppAll(Context context) {
        try {
            for (Activity activity : mActivityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mActivityList.clear();
//            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses("com.example.bailyhome");
            System.exit(0);
        }
    }
    public void exit(){

    }
}
