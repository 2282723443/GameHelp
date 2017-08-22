package com.wangwei.gamehelp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;

public class NewsWebViewActivity extends BaseActivity {
    String webhttp;
    @BindView(R.id.webview)
    WebView myWebView;
    @BindView(R.id.progress)
    ProgressBar myProgressBar;

    @Override
    public int getLayoutID() {
        return R.layout.activity_news_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("详情");
        showBackBtn();
        webhttp = (String) getIntent().getSerializableExtra("webhttp");
        myProgressBar = (ProgressBar) findViewById(R.id.progress);
        Log.v("tedu", "跳转完成后的" + webhttp);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBlockNetworkImage(false);
        myWebView.loadUrl(webhttp);
        myWebView.setWebViewClient(new myWebViewClient());
        WebSettings setting = myWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        //设置webview支持脚本
        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    myProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    myProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    myProgressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
//          return super.shouldOverrideUrlLoading(view, url);
            myWebView.loadUrl(url);
            return true;
        }


    }

}
