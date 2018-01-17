package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hengyi.wheelpicker.ppw.WheelPickerPopupWindow;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.mine.entity.AddressItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/13.
 */

public class AddAddressActivity extends BaseActivity {

    public static final int RESULT_CODE_ADD = 2;

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


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("添加新地址");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @OnClick({R.id.add_address_rv_choose_region, R.id.titlebar_tv_right, R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_rv_choose_region:
                showRegionPicker();
                break;
            case R.id.titlebar_tv_right:
                //TODO 数据库保存
                //finish();
                setResultToPreActivity(buildNewAddress());
                finish();
                break;
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }

    }

    private AddressItem buildNewAddress() {
        AddressItem addressItem = new AddressItem();
        addressItem.setDefault(mCbDefault.isChecked());
        addressItem.setName(mEtContact.getText().toString());
        addressItem.setPhone(mEtPhone.getText().toString());
        addressItem.setDetailAddr(mTvRegion.getText().toString() + mEtDetail.getText().toString());
        return addressItem;
    }

    private void setResultToPreActivity(AddressItem addressItem) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("new_address", addressItem);//序列化
        intent.putExtras(bundle);//发送数据
        setResult(RESULT_CODE_ADD, intent);
    }

    private void showRegionPicker() {
        WheelPickerPopupWindow wheelPickerPopupWindow = new WheelPickerPopupWindow(AddAddressActivity.this);
        wheelPickerPopupWindow.setListener(new WheelPickerPopupWindow.WheelPickerComfirmListener() {

            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
//                Toast.makeText(AddAddressActivity.this,Province + City + District,Toast.LENGTH_LONG).show();
                mTvRegion.setText(Province + City + District);
            }
        });
        wheelPickerPopupWindow.show();
    }
}
