package com.yichan.gaotezhipei.enterpriseservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ckerv on 2018/1/9.
 */

public class IpAcvitiy extends BaseActivity {

    protected static final String KEY_URL = "url";


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.ip_webview)
    WebView mWebView;

    private String mUrl;

    public static void startActivity(Context context, String url) {
        Intent i = new Intent(context, IpAcvitiy.class);
        i.putExtra(KEY_URL, url);
        context.startActivity(i);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initVariables();
        initTitleBar();
        initWebView();
        loadUrl();
    }

    protected void initVariables() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(KEY_URL);
    }

    private void initTitleBar() {
        mTvTitle.setText("知识产权");
    }

    private void initWebView() {
        //第三方网页中 如优酷会用到DOMStorage来存储信息,如果不能存储则无法转跳
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
    }

    private void loadUrl() {
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ip;
    }

    @OnClick({R.id.titlebar_btn_left,R.id.ip_btn_commission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.ip_btn_commission:
                ComissionActivity.startActivity(IpAcvitiy.this, 3);
                break;
            default:
                break;
        }
    }
}
