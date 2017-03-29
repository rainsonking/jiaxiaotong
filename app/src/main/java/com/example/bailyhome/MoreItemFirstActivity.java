package com.example.bailyhome;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bailyhome.base.BaseActivity;

/**
 * Created by Administrator on 2016/5/17 0017.
 *
 * 百丽天下论坛 加载界面
 */
public class MoreItemFirstActivity extends BaseActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_more_item_first);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://bbs.bailiedu.com/forum.php");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initView() {

    }
}
