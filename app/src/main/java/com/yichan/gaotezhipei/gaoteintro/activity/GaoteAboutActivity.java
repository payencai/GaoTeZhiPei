package com.yichan.gaotezhipei.gaoteintro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/11.
 */

public class GaoteAboutActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("关于高特智配");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.gaote_about_btn_cooperate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.gaote_about_btn_cooperate:
                startActivity(new Intent(GaoteAboutActivity.this, GaoteCooperateActivity.class));
            default:
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gaote_about;
    }
}
