package com.yichan.gaotezhipei.login.activity;

import android.view.View;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.ActivityAnimationUtil;

import butterknife.OnClick;

/**
 * 需求方登录
 * Created by ckerv on 2018/1/16.
 */

public class DemandLoginActivity extends BaseActivity {


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
                switchToServerLogin();
                finish();
                break;
            default:
                break;
        }
    }

    private void switchToServerLogin() {
        ActivityAnimationUtil.startActivityWithAnim(this, ServerLoginActivity.class, R.anim.activity_in, 0, null);
    }
}
