package com.yichan.gaotezhipei.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.callback.UISceneCallback;
import com.google.gson.reflect.TypeToken;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.ActivityAnimationUtil;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.NetworkUtil;
import com.yichan.gaotezhipei.login.constant.LoginConstants;
import com.yichan.gaotezhipei.login.entity.UserEntity;
import com.yichan.gaotezhipei.login.event.LoginEvent;
import com.yichan.gaotezhipei.login.util.LoginManager;
import com.yichan.gaotezhipei.server.lcldriver.activity.LCLDriverMainActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.activity.LogisticsDriverMainActivity;
import com.yichan.gaotezhipei.server.netstation.activity.NetMainActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 服务方登录
 * Created by ckerv on 2018/1/16.
 */

public class ServerLoginActivity extends BaseActivity {

    @BindView(R.id.login_btn_register)
    Button mBtnRegister;

    @BindView(R.id.login_tv_switch)
    TextView mTvSwitch;

    @BindView(R.id.login_et_account)
    EditText mEtAccount;
    @BindView(R.id.login_et_password)
    EditText mEtPassword;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initViews();
        mEtAccount.setText("456");
        mEtPassword.setText("456");
    }

    private void initViews() {
        mBtnRegister.setText("服务方申请");
        mTvSwitch.setText("需求方登录");
        mBtnRegister.setVisibility(View.GONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_common;
    }

    @OnClick({R.id.login_tv_close, R.id.login_ll_switch, R.id.login_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_tv_close:
                finish();
                break;
            case R.id.login_ll_switch:
                switchToDemandLogin();
                finish();
                break;
//            case R.id.login_btn_register:
//                Intent intent = new Intent(ServerLoginActivity.this, RegisterActivity.class);
//                intent.putExtra("role_type", AppConstants.SERVER_TYPE_CODE);
//                startActivity(intent);
//                break;
            case R.id.login_btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (!checkFormFormat()) {
            return;
        }
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LoginConstants.URL_LOGIN)
                .addParams("telephone", mEtAccount.getText().toString())
                .addParams("type", AppConstants.SERVER_TYPE_CODE)
                .addHeader("password", mEtPassword.getText().toString())
                .build();
        call.doScene(new UISceneCallback<Result<UserEntity>>() {
            @Override
            public Result<UserEntity> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.gsonToBean(response.body().string(), new TypeToken<Result<UserEntity>>() {
                }.getType());
            }

            @Override
            public void onError(Call call, Exception e) {
                if (!NetworkUtil.isNetworkAvailable(ServerLoginActivity.this)) {
                    showToast("当前设备无可用网络，请检查。");
                } else {
                    showToast("登录失败，请重新再试。");
                }
            }

            @Override
            public void onResponse(Result<UserEntity> response) {
                if (response.getResultCode() != Result.SUCCESS_CODE) {
                    showToast(response.getMessage());
                } else {
                    showToast("登录成功");
                    LoginManager.saveUserEntity(ServerLoginActivity.this, response.getData());
                    //先明文存储
                    UserManager.getInstance(ServerLoginActivity.this).setPassword(mEtPassword.getText().toString());
                    UserManager.getInstance(ServerLoginActivity.this).setRoleType(AppConstants.SERVER_TYPE_CODE);
                    EventBus.getInstance().post(new LoginEvent());
                    startActivityByRole(response.getData().getType());
                    finish();
                }
            }

        });
    }

    private void startActivityByRole(int type) {
        Intent intent = new Intent();
        switch (type) {
            case AppConstants.ROLE_TYPE_LCL_DRIVER:
            case AppConstants.ROLE_TYPE_DEMAND_AND_LCL:
                intent.setClass(ServerLoginActivity.this, LCLDriverMainActivity.class);
                break;
            case AppConstants.ROLE_TYPE_LOGISTICS_DRIVER:
                intent.setClass(ServerLoginActivity.this, LogisticsDriverMainActivity.class);
                break;
            case AppConstants.ROLE_TYPE_NET:
                intent.setClass(ServerLoginActivity.this, NetMainActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private boolean checkFormFormat() {
        if (TextUtils.isEmpty(mEtAccount.getText().toString())) {
            showToast("手机号码不能为空");
            return false;
        }
        //TODO 测试账号123/456，先取消对手机格式的判断
//        } else if (!PhoneUtil.isPhoneNumber(mEtAccount.getText().toString())) {
//            showToast("请输入正确的手机号码");
//            return false;
         else if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
            showToast("密码不能为空");
            return false;
        } else {
            return true;
        }
    }

    private void switchToDemandLogin() {
        ActivityAnimationUtil.startActivityWithAnim(this, DemandLoginActivity.class, R.anim.activity_in, 0, null);
    }
}
