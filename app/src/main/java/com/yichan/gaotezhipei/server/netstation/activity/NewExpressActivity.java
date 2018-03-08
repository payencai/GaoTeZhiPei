package com.yichan.gaotezhipei.server.netstation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.activity.EditContactInformActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.ContactInformEntity;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.server.netstation.constant.NetStationConstants;
import com.yichan.gaotezhipei.server.netstation.event.ChooseExpressCompanyEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NewExpressActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.new_express_tv_company)
    TextView mTvCompany;
    @BindView(R.id.new_express_et_number)
    EditText mEtNumber;
    @BindView(R.id.new_express_tv_mail_name_phone)
    TextView mTvMailNamePhone;
    @BindView(R.id.new_express_tv_mail_address)
    TextView mTvMailAddr;
    @BindView(R.id.new_express_tv_pick_name_phone)
    TextView mTvPickNamePhone;
    @BindView(R.id.new_express_tv_pick_address)
    TextView mTvPickAddr;
    @BindView(R.id.new_express_et_cargo_weight)
    EditText mEtCargoWeight;
    @BindView(R.id.new_express_et_cargo_volume)
    EditText mEtCargoVolume;
    @BindView(R.id.new_express_et_cargo_count)
    EditText mEtCargoCount;
    @BindView(R.id.new_express_et_cargo_name)
    EditText mEtCargoName;

    private int REQUEST_CODE_MAIL = 1;
    private int REQUEST_CODE_PICK = 2;

    private ContactInformEntity mailContactEntity;
    private ContactInformEntity pickContactEntity;

    private String mExpressCompanyId = null;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
        mTvTitle.setText("新增快件");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @OnClick({R.id.titlebar_btn_left,R.id.new_express_ll_mail, R.id.new_express_ll_pick,R.id.new_express_btn_apply,R.id.new_express_rl_choose_express_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.new_express_ll_mail:
                startEditContactInform(REQUEST_CODE_MAIL);
                break;
            case R.id.new_express_ll_pick:
                startEditContactInform(REQUEST_CODE_PICK);
                break;
            case R.id.new_express_btn_apply:
                tryToPostForms();
                break;
            case R.id.new_express_rl_choose_express_company:
                startActivity(new Intent(NewExpressActivity.this, ChooseExpressCompanyActivity.class));
                break;
            default:
                break;
        }
    }

    private void tryToPostForms() {
        if(!checkForms()) {
            return;
        }
        postForms();
    }

    private void postForms() {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + NetStationConstants.URL_ADD_ORDER)
                .addParams("demanderTelnum", mailContactEntity.phone)
                .addParams("receiverTelnum", pickContactEntity.phone)
                .addParams("orderNumber", mEtNumber.getText().toString())
                .addParams("nameFrom", mailContactEntity.name)
                .addParams("adressFrom",mailContactEntity.detail)
                .addParams("adressFromProvince", mailContactEntity.province)
                .addParams("adressFromCity",mailContactEntity.city)
                .addParams("adressFromDistrict", mailContactEntity.district)
                .addParams("adressFromLongitude", String.valueOf(mailContactEntity.lng))
                .addParams("adressFromLatitude",String.valueOf(mailContactEntity.lat))
                .addParams("nameTo", pickContactEntity.name)
                .addParams("adressTo", pickContactEntity.detail)
                .addParams("adressToProvince", pickContactEntity.province)
                .addParams("adressToCity", pickContactEntity.city)
                .addParams("adressToDistrict", pickContactEntity.district)
                .addParams("adressToLongitude",String.valueOf(pickContactEntity.lng))
                .addParams("adressToLatitude",String.valueOf(pickContactEntity.lat))
                .addParams("goodsName", mEtCargoName.getText().toString())
                .addParams("goodsMass", mEtCargoWeight.getText().toString())
                .addParams("goodsSize",mEtCargoVolume.getText().toString())
                .addParams("goodsQuantity", mEtCargoCount.getText().toString())
                .addParams("logisticsCompanyId", mExpressCompanyId)
                .addParams("logisticsCompanyName", mTvCompany.getText().toString())
                .addParams("paymentMethod", "1").build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("新增快件成功");
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }

        });

    }

    private boolean checkForms() {
        if(TextUtils.isEmpty(mTvCompany.getText().toString())
                || mTvCompany.getText().toString().equals("请输入快递公司")
                || TextUtils.isEmpty(mEtNumber.getText().toString())
                || TextUtils.isEmpty(mTvMailNamePhone.getText().toString())
                || TextUtils.isEmpty(mTvMailAddr.getText().toString())
                || TextUtils.isEmpty(mTvPickNamePhone.getText().toString())
                || TextUtils.isEmpty(mTvPickAddr.getText().toString())
                || TextUtils.isEmpty(mEtCargoWeight.getText().toString())
                || TextUtils.isEmpty(mEtCargoVolume.getText().toString())
                || TextUtils.isEmpty(mEtCargoCount.getText().toString())
                || TextUtils.isEmpty(mEtCargoName.getText().toString())
                || mailContactEntity == null
                || pickContactEntity == null
                || mExpressCompanyId == null) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else {
            return true;
        }
    }

    private void startEditContactInform(int requestCode) {
        Intent intent = new Intent(NewExpressActivity.this, EditContactInformActivity.class);
        if(requestCode == REQUEST_CODE_MAIL && mailContactEntity != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contactEntity", mailContactEntity);
            intent.putExtras(bundle);
        } else if(requestCode == REQUEST_CODE_PICK && pickContactEntity != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contactEntity", pickContactEntity);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_MAIL) {
                mailContactEntity = (ContactInformEntity) data.getSerializableExtra("contactEntity");
                setMailInformToView();
            } else if(requestCode == REQUEST_CODE_PICK) {
                pickContactEntity = (ContactInformEntity) data.getSerializableExtra("contactEntity");
                setPickInformToView();
            }
        }
    }

    private void setMailInformToView() {
        mTvMailNamePhone.setText(mailContactEntity.name + "    " + mailContactEntity.phone);
        mTvMailAddr.setText(mailContactEntity.detail);
    }

    private void setPickInformToView() {
        mTvPickNamePhone.setText(pickContactEntity.name + "    " + pickContactEntity.phone);
        mTvPickAddr.setText(pickContactEntity.detail);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveChooseExpressCompanyEvent(ChooseExpressCompanyEvent event) {
        mTvCompany.setTextColor(Color.parseColor("#333333"));
        mTvCompany.setText(event.companyName);
        mExpressCompanyId = event.companyId;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_express;
    }
}
