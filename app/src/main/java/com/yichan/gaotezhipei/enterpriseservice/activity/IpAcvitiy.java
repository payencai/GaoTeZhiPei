package com.yichan.gaotezhipei.enterpriseservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/9.
 */

public class IpAcvitiy extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("知识产权");
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
                Intent intent = new Intent(IpAcvitiy.this, ComissionActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
