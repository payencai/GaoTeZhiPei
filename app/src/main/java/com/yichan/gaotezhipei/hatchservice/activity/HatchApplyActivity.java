package com.yichan.gaotezhipei.hatchservice.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.sevenheaven.segmentcontrol.SegmentControl;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EmailUtil;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.hatchservice.constant.HatchServiceConstants;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/19.
 */

public class HatchApplyActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.apply_et_company)
    EditText mEtCompany;
    @BindView(R.id.apply_et_contact)
    EditText mEtContact;
    @BindView(R.id.apply_et_phone)
    EditText mEtPhone;
    @BindView(R.id.apply_et_email)
    EditText mEtEmail;
    @BindView(R.id.apply_et_addr)
    EditText mEtAddr;
    @BindView(R.id.apply_et_product_name)
    EditText mEtProductName;
    @BindView(R.id.apply_et_member_count)
    EditText mEtMemberCount;
    @BindView(R.id.apply_sc_schedule)
    SegmentControl mScSchedule;

    private String mCurrentStep = "创意阶段";


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initSegmentControl();
    }

    private void initSegmentControl() {
        mScSchedule.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                switch (index) {
                    case 0:
                        mCurrentStep = "创意阶段";
                        break;
                    case 1:
                        mCurrentStep = "创意计划";
                        break;
                    case 2:
                        mCurrentStep = "已经启动";
                        break;
                    case 3:
                        mCurrentStep = "已经运营";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initTitleBar() {
        mTvTitle.setText("入孵申请表");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.apply_btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.apply_btn_commit:
                postForms();
                break;
            default:
                break;
        }
    }

    private void postForms() {
        if(!checkForms()) {
            return;
        }
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + HatchServiceConstants.URL_HATCH_APPLY)
                .addParams("xCompanyName", mEtCompany.getText().toString())
                .addParams("xLinkman", mEtContact.getText().toString())
                .addParams("xTelnum", mEtPhone.getText().toString())
                .addParams("xEmail", mEtEmail.getText().toString())
                .addParams("xAdress", mEtAddr.getText().toString())
                .addParams("xProjectName", mEtProductName.getText().toString())
                .addParams("xTeanNum", mEtMemberCount.getText().toString())
                .addParams("xProjectStatus", mCurrentStep).build();
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

    private boolean checkForms() {
        if(TextUtils.isEmpty(mEtCompany.getText().toString())
                || TextUtils.isEmpty(mEtContact.getText().toString())
                || TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mEtEmail.getText().toString())
                || TextUtils.isEmpty(mEtAddr.getText().toString())
                || TextUtils.isEmpty(mEtProductName.getText().toString())
                || TextUtils.isEmpty(mEtMemberCount.getText().toString())) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("请输入正确格式的手机号码");
            return false;
        } else if(!EmailUtil.isEmail(mEtEmail.getText().toString())) {
            showToast("请输入正确格式的电子邮箱");
            return false;
        }
        return true;
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_hatch_apply;
    }
}
