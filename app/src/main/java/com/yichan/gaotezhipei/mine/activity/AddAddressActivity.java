package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.yichan.gaotezhipei.common.entity.ContactInformEntity;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.location.activity.ChooseAddressWebActivity;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.entity.AddressItem;
import com.yichan.gaotezhipei.mine.event.NotifyAddressEvent;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/13.
 */

public class AddAddressActivity extends BaseActivity {

    public static final int RESULT_CODE_ADD = 2;

    public static final String STRAT_TYPE = "startType";

    public static final int TYPE_ADD = 1;
    public static final int TYPE_EDIT = 2;

    private int mStartType = TYPE_ADD;
    private AddressItem addressItem;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.add_address_tv_region)
    TextView mTvRegion;

    @BindView(R.id.add_address_et_contact)
    EditText mEtContact;

    @BindView(R.id.add_address_et_phone)
    EditText mEtPhone;

    @BindView(R.id.add_address_et_detail_address)
    EditText mEtDetail;

    @BindView(R.id.add_address_cb_default)
    CheckBox mCbDefault;

    private ContactInformEntity contactInformEntity;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initStartType();
        initTitleBar();
        initView();
    }

    private void initStartType() {
        mStartType = getIntent().getIntExtra(STRAT_TYPE, TYPE_ADD);
        if (mStartType == TYPE_EDIT) {
            addressItem = (AddressItem) getIntent().getSerializableExtra("addressItem");
        }
    }

    private void initTitleBar() {
        if (mStartType == TYPE_ADD) {
            mTvTitle.setText("添加新地址");
        } else {
            mTvTitle.setText("编辑地址");
        }
    }

    private void initView() {
        if (addressItem != null) {
            mEtContact.setText(addressItem.getName());
            mEtPhone.setText(addressItem.getTelephone());
            mTvRegion.setTextColor(Color.parseColor("#333333"));
            mTvRegion.setText(addressItem.getProvince() + addressItem.getCity() + addressItem.getArea());
            mEtDetail.setText(addressItem.getAddress());
            mCbDefault.setChecked((addressItem.getDefaultAddress() == 1 ? true : false));
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @OnClick({R.id.add_address_rv_choose_region, R.id.titlebar_tv_right, R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_rv_choose_region:
                startChooseAddrWebActivity();
                break;
            case R.id.titlebar_tv_right:
                tryToPostForms();
                break;
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }

    }


    private void startChooseAddrWebActivity() {
        Intent intent = new Intent(AddAddressActivity.this, ChooseAddressWebActivity.class);
        startActivityForResult(intent, 1);
    }

    private void tryToPostForms() {
        if (!checkForms()) {
            return;
        }
        postForms();
    }

    private void postForms() {

        PostFormBuilder postFormBuilder = new PostFormBuilder();
        if(mStartType == TYPE_ADD) {
            postFormBuilder.url(AppConstants.BASE_URL + MineConstants.URL_ADD_NEW_ADDRESS);
        } else {
            postFormBuilder.url(AppConstants.BASE_URL + MineConstants.URL_UPDATE_ADDRESS);
            postFormBuilder.addParams("id", addressItem.getId());
        }

        RequestCall call = postFormBuilder
                .addParams("name", mEtContact.getText().toString())
                .addParams("telephone", mEtPhone.getText().toString())
                .addParams("province", (contactInformEntity == null ? addressItem.getProvince() : contactInformEntity.province))
                .addParams("city", (contactInformEntity == null ? addressItem.getCity() : contactInformEntity.city))
                .addParams("area", (contactInformEntity == null ? addressItem.getArea() : contactInformEntity.district))
                .addParams("address", (contactInformEntity == null ? mEtDetail.getText().toString() : contactInformEntity.detail))
                .addParams("defaultAddress", (mCbDefault.isChecked() ? "1" : "2"))
                .addParams("lng1", String.valueOf((contactInformEntity == null ? addressItem.getLng1() : contactInformEntity.lat)))
                .addParams("lat1", String.valueOf((contactInformEntity == null ? addressItem.getLat1() : contactInformEntity.lng)))
                .build();

        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("保存地址成功");
                    EventBus.getInstance().post(new NotifyAddressEvent());
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }

        });


    }


    private boolean checkForms() {
        if (TextUtils.isEmpty(mEtContact.getText().toString())
                || TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mTvRegion.getText().toString())
                || TextUtils.isEmpty(mEtDetail.getText().toString())
                || (contactInformEntity == null && mStartType == TYPE_ADD)) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("信息格式填写有误，请检查。");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        contactInformEntity = (ContactInformEntity) data.getSerializableExtra("contactEntity");
        if (contactInformEntity != null) {
            mTvRegion.setTextColor(Color.parseColor("#333333"));
            mTvRegion.setText(contactInformEntity.province + contactInformEntity.city + contactInformEntity.district);
            mEtDetail.setText(contactInformEntity.detail);
        }
    }
}
