package com.yichan.gaotezhipei.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.ActivityAnimationUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务方登录
 * Created by ckerv on 2018/1/16.
 */

public class ServerLoginActivity extends BaseActivity {

    @BindView(R.id.login_btn_register)
    Button mBtnRegister;

    @BindView(R.id.login_tv_switch)
    TextView mTvSwitch;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mBtnRegister.setText("服务方申请");
        mTvSwitch.setText("需求方登录");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_common;
    }

    @OnClick({R.id.login_tv_close, R.id.login_ll_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_close:
                finish();
                break;
            case R.id.login_ll_switch:
                switchToDemandLogin();
                finish();
                break;
            default:
                break;
        }
    }

    private void switchToDemandLogin() {
        ActivityAnimationUtil.startActivityWithAnim(this, DemandLoginActivity.class, R.anim.activity_in, 0, null);
    }
}
