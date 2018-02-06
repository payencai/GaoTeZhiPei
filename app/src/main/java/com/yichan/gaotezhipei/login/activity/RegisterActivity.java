package com.yichan.gaotezhipei.login.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.callback.UISceneCallback;
import com.google.gson.reflect.TypeToken;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.NetworkUtil;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.login.constant.LoginConstants;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/4.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_et_account)
    EditText mEtAccount;
    @BindView(R.id.register_et_verifycode)
    EditText mEtVerifyCode;
    @BindView(R.id.register_et_password)
    EditText mEtPassword;
    @BindView(R.id.register_et_confirm_password)
    EditText mEtConfirmPassword;

    private String roleType;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        roleType = getIntent().getStringExtra("role_type");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.titlebar_btn_left, R.id.register_tv_get_verifycode,R.id.register_btn_register, R.id.register_tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_tv_login:
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.register_tv_get_verifycode:
                getVerifyCode();
                break;
            case R.id.register_btn_register:
                applyToRegister();
                break;
            default:
                break;
        }
    }

    private void applyToRegister() {
        if(!checkFormFormat()) {
            return;
        }
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LoginConstants.URL_REGISTER)
                .addParams("telephone", mEtAccount.getText().toString())
                .addParams("type", roleType)
                .addParams("code", mEtVerifyCode.getText().toString())
                .addHeader("password", mEtPassword.getText().toString())
                .build();
        call.doScene(new UISceneCallback<Result<String>>() {
            @Override
            public Result<String> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.gsonToBean(response.body().string(), new TypeToken<Result<String>>(){}.getType());
            }

            @Override
            public void onError(Call call, Exception e) {
                if(!NetworkUtil.isNetworkAvailable(RegisterActivity.this)) {
                    showToast("当前设备无可用网络，请检查。");
                } else {
                    showToast("注册失败，请重新再试。");
                }
            }

            @Override
            public void onResponse(Result<String> response) {
                if(response.getResultCode() != Result.SUCCESS_CODE) {
                    showToast(response.getMessage());
                } else {
                    showToast("注册成功，请登录。");
                    finish();
                }
            }

        });
    }

    private boolean checkFormFormat() {
        if(TextUtils.isEmpty(mEtAccount.getText().toString())) {
            showToast("手机号码不能为空");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtAccount.getText().toString())) {
            showToast("请输入正确的手机号码");
            return false;
        } else if(TextUtils.isEmpty(mEtVerifyCode.getText().toString())) {
            showToast("验证码不能为空");
            return false;
        } else if(TextUtils.isEmpty(mEtPassword.getText().toString())) {
            showToast("密码不能为空");
            return false;
        } else if(TextUtils.isEmpty(mEtConfirmPassword.getText().toString())) {
            showToast("验证密码不能为空");
            return false;
        } else if(!mEtPassword.getText().toString().equals(mEtConfirmPassword.getText().toString())) {
            showToast("两次输入的密码不相同，请重新输入");
            return false;
        } else {
            return true;
        }
    }

    private void getVerifyCode() {
        if(TextUtils.isEmpty(mEtAccount.getText().toString())) {
            showToast("手机号码不能为空");
            return;
        } else if(!PhoneUtil.isPhoneNumber(mEtAccount.getText().toString())) {
            showToast("请输入正确的手机号码");
            return;
        }
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LoginConstants.URL_GET_VERIFY_CODE)
                .addParams("telephone", mEtAccount.getText().toString())
                .addParams("demoCode", roleType + AppConstants.REGISTER_CODE).build();
        call.doScene(new UISceneCallback<Result<String>>() {
            @Override
            public Result<String> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.gsonToBean(response.body().string(), new TypeToken<Result<String>>(){}.getType());
            }

            @Override
            public void onError(Call call, Exception e) {
                if(!NetworkUtil.isNetworkAvailable(RegisterActivity.this)) {
                    showToast("当前设备无可用网络，请检查。");
                } else {
                    showToast("获取验证码失败，请重新再试。");
                }
            }

            @Override
            public void onResponse(Result<String> response) {
                if(response.getResultCode() != Result.SUCCESS_CODE) {
                    showToast(response.getMessage());
                } else {
                    showToast("获取验证码成功，请注意接收短信。");
                }
            }

        });
    }
}
