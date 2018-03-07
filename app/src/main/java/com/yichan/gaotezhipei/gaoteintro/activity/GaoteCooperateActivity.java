package com.yichan.gaotezhipei.gaoteintro.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.gaoteintro.constant.GaoteIntroConstants;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/11.
 */

public class GaoteCooperateActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.et_contact)
    EditText mEtContact;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_goods_type)
    EditText mEtGoodsType;
    @BindView(R.id.et_marketing_mode)
    EditText mEtMarketingMode;
    @BindView(R.id.et_sop)
    EditText mEtSop;
    @BindView(R.id.et_express)
    EditText mEtExpress;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("我要合作");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btn_commit:
                tryToCommit();
            default:
                break;
        }
    }

    private void tryToCommit() {
        if(!checkForm()) {
            return;
        }

        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + GaoteIntroConstants.URL_COOPERATE)
                .addParams("xPerson", mEtContact.getText().toString())
                .addParams("xTelnum", mEtPhone.getText().toString())
                .addParams("xBusinessCategory", mEtGoodsType.getText().toString())
                .addParams("xMaketingMode", mEtMarketingMode.getText().toString())
                .addParams("xSop", mEtSop.getText().toString())
                .addParams("xLogistics", mEtExpress.getText().toString())
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("申请成功，请等待审核。");
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }

        });
    }

    private boolean checkForm() {
        if(TextUtils.isEmpty(mEtContact.getText().toString())
                || TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mEtGoodsType.getText().toString())
                || TextUtils.isEmpty(mEtMarketingMode.getText().toString())
                || TextUtils.isEmpty(mEtSop.getText().toString())
                || TextUtils.isEmpty(mEtExpress.getText().toString())) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gaote_cooperate;
    }
}
