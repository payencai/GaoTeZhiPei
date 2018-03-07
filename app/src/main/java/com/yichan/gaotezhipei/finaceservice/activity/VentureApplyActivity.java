package com.yichan.gaotezhipei.finaceservice.activity;

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
import com.yichan.gaotezhipei.common.util.PhoneUtil;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/9.
 */

public class VentureApplyActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.apply_et_name)
    EditText mEtName;

    @BindView(R.id.apply_et_phone)
    EditText mEtPhone;

    @BindView(R.id.apply_et_id_card)
    EditText mEtIdCard;

    @BindView(R.id.apply_et_addr)
    EditText mEtAddr;

    @BindView(R.id.apply_cb_agree)
    CheckBox mCbAgree;


    private String url;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initVariables();
        initTitleBar();
    }

    private void initVariables() {
        int index = getIntent().getIntExtra("currentIndex", 0);
        if (index == 0) {
            url = "/demander/freeLoan/apply";
        } else if (index == 1) {
            url = "/demander/pettyLoan/apply";
        } else if(index == 2) {
            url = "/demander/ventureLoan/apply";
        }
    }

    private void initTitleBar() {
        mTvTitle.setText("填写相关信息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_venture_apply;
    }

    @OnClick({R.id.titlebar_btn_left, R.id.apply_btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.apply_btn_commit:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {
        if (!checkForms()) {
            return;
        }
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + url)
                .addParams("xName", mEtName.getText().toString())
                .addParams("xIdcard", mEtIdCard.getText().toString())
                .addParams("xTelnum", mEtPhone.getText().toString())
                .addParams("xAdress", mEtAddr.getText().toString()).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("申请成功，请等待审核。");
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }

        });
    }

    private boolean checkForms() {
        if (TextUtils.isEmpty(mEtName.getText().toString())
                || TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mEtIdCard.getText().toString())
                || TextUtils.isEmpty(mEtAddr.getText().toString())) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if (!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("请填写正确格式的手机号");
            return false;
        } else if (!mCbAgree.isChecked()) {
            showToast("您尚未同意《创投贷款协议》");
            return false;
        } else {
            return true;
        }
    }
}
