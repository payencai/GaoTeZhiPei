package com.yichan.gaotezhipei.enterpriseservice.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EmailUtil;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.enterpriseservice.constant.EnterpriseConstants;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/16.
 */

public class ComissionActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.commission_et_company)
    EditText mEtCompany;
    @BindView(R.id.commission_et_name)
    EditText mEtName;
    @BindView(R.id.commission_et_phone)
    EditText mEtPhone;
    @BindView(R.id.commission_et_email)
    EditText mEtEmail;
    @BindView(R.id.commission_cb_agree)
    CheckBox mCbAgree;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("填写相关信息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_commission;
    }

    @OnClick({R.id.titlebar_btn_left, R.id.commission_btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.commission_btn_apply:
                postForm();
                break;
            default:
                break;
        }
    }

    private void postForm() {
        if(!checkFormFormat()) {
            return;
        }
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + EnterpriseConstants.URL_LICENSE_APPLY)
                .addParams("xCompany", mEtCompany.getText().toString())
                .addParams("xTaxpayer",mEtName.getText().toString())
                .addParams("xTelnum",mEtPhone.getText().toString())
                .addParams("xEmail", mEtEmail.getText().toString()).build();
        call.doScene(new TokenSceneCallback<String>(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<String> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("申请成功，请等待审核。");
                    finish();
                } else {
                    showToast("申请失败，请重新再试。");
                }
            }

        });
    }

    private boolean checkFormFormat() {
        if(TextUtils.isEmpty(mEtCompany.getText().toString())) {
            showToast("公司名称不能为空");
            return false;
        } else if(TextUtils.isEmpty(mEtName.getText().toString())) {
            showToast("纳税人姓名不能为空");
            return false;
        } else if(TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showToast("手机号码不能为空");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("请输入正确的手机号码");
            return false;
        } else if(TextUtils.isEmpty(mEtEmail.getText().toString())) {
            showToast("电子邮箱不能为空");
            return false;
        } else if(!EmailUtil.isEmail(mEtEmail.getText().toString())) {
            showToast("请输入正确格式的电子邮箱");
            return false;
        } else if(!mCbAgree.isChecked()) {
            showToast("您尚未同意《高特智配服务协议》");
            return false;
        } else {
            return true;
        }
    }
}
