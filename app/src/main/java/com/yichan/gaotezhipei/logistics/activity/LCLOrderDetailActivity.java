package com.yichan.gaotezhipei.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/2/10.
 */

public class LCLOrderDetailActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private LCLOrderPage.BeanListBean mBean;

    @BindView(R.id.tv_mail_district)
    TextView mTvMailDistrict;
    @BindView(R.id.tv_mail_province_city)
    TextView mTvMailProvinceCity;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_status_desc)
    TextView mTvStatusDesc;
    @BindView(R.id.tv_pick_district)
    TextView mTvPickDistrict;
    @BindView(R.id.tv_pick_province_city)
    TextView mTvPickProvinceCity;


    @BindView(R.id.tv_order_desc)
    TextView mTvOrderDesc;

    @BindView(R.id.tv_order_time)
    TextView mTvOrderTime;

    @BindView(R.id.tv_mail_phone_name)
    TextView mTvMailNamePhone;
    @BindView(R.id.tv_mail_address)
    TextView mTvMailAddr;

    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_weight)
    TextView mTvGoodWeight;
    @BindView(R.id.tv_good_volume)
    TextView mTvGoodVolume;
    @BindView(R.id.tv_good_count)
    TextView mTvGoodCount;
    @BindView(R.id.tv_car_type)
    TextView mTvCarType;
    @BindView(R.id.tv_get_good_time)
    TextView mTvGetGoodTime;
    @BindView(R.id.tv_get_good_addr)
    TextView mTvGetGoodAddr;

    public static void startActivity(Context context, LCLOrderPage.BeanListBean bean) {
        Intent intent = new Intent(context, LCLOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderdetailbean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBean = (LCLOrderPage.BeanListBean) getIntent().getSerializableExtra("orderdetailbean");
        initTitleBar();

        initFirstLine();
        initSecondLine();
        initThirdLine();
        initGoodInform();
    }

    private void initFirstLine() {
        mTvMailDistrict.setText(mBean.getAddress().getArea());
        mTvMailProvinceCity.setText(mBean.getAddress().getProvince() + " " + mBean.getAddress().getCity());
        mTvPickDistrict.setText(mBean.getConsigneeArea());
        mTvPickProvinceCity.setText(mBean.getConsigneeProvince() + " " + mBean.getConsigneeCity());
        int type = Integer.valueOf(mBean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                mTvStatus.setText("待接单");
                mTvStatusDesc.setText("等待司机接单");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                mTvStatus.setText("待接货");
                mTvStatusDesc.setText("等待司机接货");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                if(UserManager.getInstance(LCLOrderDetailActivity.this).isDemand()) {
                    mTvStatus.setText("待收货");
                } else {
                    mTvStatus.setText("待送货");
                }
                mTvStatusDesc.setText("运往签收地址");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                mTvStatus.setText("待签收");
                mTvStatusDesc.setText("等待用户签收");
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                mTvStatus.setText("已完成");
                mTvStatusDesc.setText("该订单已签收");
                break;
            default:
                break;
        }
    }

    private void initSecondLine() {
        int type = Integer.valueOf(mBean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                mTvOrderDesc.setText("您有新的订单要处理");
                mTvOrderTime.setText(mBean.getOrderTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                mTvOrderDesc.setText("您有新的订单要处理");
                mTvOrderTime.setText(mBean.getGetTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                mTvOrderDesc.setText("正在前往收货目的地");
                mTvOrderTime.setText(mBean.getPickupTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                mTvOrderDesc.setText("等待用户签收");
                mTvOrderTime.setText(mBean.getSendTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                mTvOrderDesc.setText("订单已签收，签收人：" + mBean.getConsignee() + " " + mBean.getConsigneeTelephone());
                mTvOrderTime.setText(mBean.getEndTime());
                break;
            default:
                break;
        }
    }

    private void initThirdLine() {
        mTvMailNamePhone.setText("寄件人：" + mBean.getAddress().getName() + " " + mBean.getAddress().getTelephone());
        mTvMailAddr.setText("寄件地址：" + mBean.getAddress().getAddress());

    }

    private void initGoodInform() {
        mTvGoodName.setText("货品：" + mBean.getArticleName());
        mTvGoodWeight.setText("重量：" + mBean.getWeight() + "kg");
        mTvGoodVolume.setText("体积：" + mBean.getVolume() + "m³");
        mTvGoodCount.setText("数量：" + mBean.getNum() + "件");
        mTvCarType.setText("所需车型：" + mBean.getAnticipantCar());
        mTvGetGoodTime.setText("取货时间：" + mBean.getAnticipantTime());
        mTvGetGoodAddr.setText("取货地址：" + mBean.getPickupAddress());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lcl_order_detail;
    }

    private void initTitleBar() {
        mTvTitle.setText("快递查询");
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }
}
