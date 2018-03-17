package com.yichan.gaotezhipei.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyz.step.FlowViewVertical;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by ckerv on 2018/2/8.
 */

public class LogisticsDetailActivity extends BaseActivity {

    private TextView mTvTitle;
    private TextView tvOrderTime;
    private RelativeLayout rlStatus;
    private TextView tvStatus;
    private TextView tvMailDistrict;
    private TextView tvMailProvinceCity;
    private TextView tvDistance;
    private TextView tvPickDistrict;
    private TextView tvPickProvinceCity;
    private TextView tvCargoName;
    private TextView tvCargoInform;
    private TextView tvExpressCompany;
    private TextView tvExpressNumber;

    private FlowViewVertical verticalStepView;


    private int mStartType;//1为需求方物流，2为物流司机方物流，3为拼货订单物流

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mContext = LogisticsDetailActivity.this;

        initView();
        initTitleBar();
        initVariables();
    }


    public static void startActivity(Context context, int type, LogisticsOrderPage.ListBean bean) {
        Intent intent = new Intent(context, LogisticsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int type, LogisticsDriverOrderItem bean) {
        Intent intent = new Intent(context, LogisticsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int type, LCLOrderPage.BeanListBean bean) {
        Intent intent = new Intent(context, LogisticsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }


    private void initVariables() {
        mStartType = getIntent().getIntExtra("type", 1);
        if (mStartType == 1) {
            setViewByDemandLogis();
        } else if(mStartType == 2) {
            setViewByLogisDriver();
        } else if(mStartType == 3) {
            setViewByLCLOrder();
        } else if(mStartType == 4) {
            setViewByLogisticOrder();
        }
    }

    private void setViewByLCLOrder() {
        LCLOrderPage.BeanListBean bean = (LCLOrderPage.BeanListBean) getIntent().getSerializableExtra("bean");
        if(bean != null) {
            tvOrderTime.setText(bean.getOrderTime());
            int type = Integer.valueOf(bean.getType());
            switch (type) {
                case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                    tvStatus.setText("待接单");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待接货");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待收货");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_orangle));
                    tvStatus.setText("待签收");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean, type);
                    break;
                default:
                    break;
            }
            tvMailDistrict.setText(bean.getAddress().getArea());
            tvMailProvinceCity.setText(bean.getAddress().getProvince() + " " + bean.getAddress().getCity());
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistance())) + "km");
            tvPickDistrict.setText(bean.getConsigneeArea());
            tvPickProvinceCity.setText(bean.getConsigneeProvince() + " " + bean.getConsigneeCity());

            tvCargoName.setText(bean.getArticleName() + ":");
            tvCargoInform.setText(bean.getNum() + "件 " + bean.getWeight() + "kg " + bean.getVolume() + "m³");

            tvExpressCompany.setText("取货时间:" + bean.getAnticipantTime());
            tvExpressNumber.setText("取货地址:" + bean.getPickupAddress());
        }
    }

    private void setViewByLogisticOrder() {
        LogisticsOrderPage.ListBean bean = (LogisticsOrderPage.ListBean) getIntent().getSerializableExtra("bean");
        if(bean != null) {
            tvOrderTime.setText(bean.getTakeorderTime());
            int type = Integer.valueOf(bean.getStatus());
            switch (type) {
                case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                    tvStatus.setText("待接单");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待接货");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待发往仓库");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("运输中");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_orangle));
                    tvStatus.setText("待签收");
                    initStepView(bean, type);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean, type);
                    break;
                default:
                    break;
            }
            tvMailDistrict.setText(bean.getAdressToDistrict());
            tvMailProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceDriver())) + "km");
            tvPickDistrict.setText(bean.getAdressFromDistrict());
            tvPickProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());

            tvCargoName.setText(bean.getGoodsName() + ":");
            tvCargoInform.setText(bean.getGoodsQuantity() + "件 " + bean.getGoodsMass() + "kg " + bean.getGoodsSize() + "m³");

            tvExpressCompany.setText("取货时间:" + bean.getPickTime());
            tvExpressNumber.setText("取货地址:" + bean.getAdressFrom());
        }
    }

    private void setViewByLogisDriver() {
        LogisticsDriverOrderItem bean = (LogisticsDriverOrderItem) getIntent().getSerializableExtra("bean");
        if(bean != null) {
            tvOrderTime.setText(bean.getTakeorderTime());
            int type = Integer.valueOf(bean.getDriverStatus());
            switch (type)  {
                case 2:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                    tvStatus.setText("待确认");
                    initStepView(bean, type);
                    break;
                case 3:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待送达");
                    initStepView(bean, type);
                    break;
                default:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean, type);
                    break;

            }

            tvMailDistrict.setText(bean.getNetworkName());
            tvMailProvinceCity.setVisibility(View.GONE);
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceDriver())) + "km");
            tvPickDistrict.setText(bean.getWarehouseAdress());
            tvPickProvinceCity.setVisibility(View.GONE);

            tvCargoName.setText("货物件数:");
            tvCargoInform.setText(bean.getCount() + "件 ");

            tvExpressCompany.setText("网点地址:" + bean.getNetworkAdress());
        }
    }

    private void setViewByDemandLogis() {
        LogisticsOrderPage.ListBean bean = (LogisticsOrderPage.ListBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            tvOrderTime.setText(bean.getCreateTime());
            switch (Integer.valueOf(bean.getStatus())) {
                case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_green));
                    tvStatus.setText("待接单");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_red));
                    tvStatus.setText("待接货");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待发往");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_light_blue));
                    tvStatus.setText("运输中");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_TRANSITING);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_orangle));
                    tvStatus.setText("待签收");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM);
                    break;
                case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                    rlStatus.setBackground(mContext.getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean, LogisticsContants.TYPE_LOG_ORDER_FINISH);
                    break;
                default:
                    break;
            }
            tvMailDistrict.setText(bean.getAdressFromDistrict());
            tvMailProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceTotal())) + "km");
            tvPickDistrict.setText(bean.getAdressToDistrict());
            tvPickProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());

            tvCargoName.setText(bean.getGoodsName() + ":");
            tvCargoInform.setText(bean.getGoodsQuantity() + "件 " + bean.getGoodsMass() + "kg " + bean.getGoodsSize() + "m³");

            tvExpressCompany.setText("物流公司:" + bean.getLogisticsCompanyName());
            tvExpressNumber.setText("物流单号:" + bean.getOrderNumber());

        }
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.titlebar_tv_title);
        tvOrderTime = (TextView) findViewById(R.id.item_tv_order_time);
        rlStatus = (RelativeLayout) findViewById(R.id.item_rl_status);
        tvStatus = (TextView) findViewById(R.id.item_tv_status);

        tvMailDistrict = (TextView) findViewById(R.id.item_tv_mail_district);
        tvMailProvinceCity = (TextView) findViewById(R.id.item_tv_mail_province_city);
        tvDistance = (TextView) findViewById(R.id.item_tv_distance);
        tvPickDistrict = (TextView) findViewById(R.id.item_tv_pick_district);
        tvPickProvinceCity = (TextView) findViewById(R.id.item_tv_pick_province_city);

        tvCargoName = (TextView) findViewById(R.id.item_tv_cargo_name);
        tvCargoInform = (TextView) findViewById(R.id.item_tv_cargo_inform);

        tvExpressCompany = (TextView) findViewById(R.id.item_tv_express_company);
        tvExpressNumber = (TextView) findViewById(R.id.item_tv_express_number);

        verticalStepView = (FlowViewVertical) findViewById(R.id.lcl_order_detail_vsv);
    }

    private void initStepView(LCLOrderPage.BeanListBean bean, int status) {
        List<String> stepList = new ArrayList<>();

        switch (status) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                stepList.add("货物已送达目的地，等待签收中\n" + bean.getSendTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                stepList.add("货物已送达目的地，等待签收中\n" + bean.getSendTime());
                stepList.add("快件已签收，签收人：" + bean.getConsignee() + "，感谢您使用高特App，期待再次为您服务！\n" + bean.getEndTime());
                break;
            default:
                break;
        }

        verticalStepView.setProgress(stepList.size() - 1, stepList.size(), stepList.toArray(new String[0]), null);
    }

    private void initStepView(LogisticsDriverOrderItem bean, int status) {
        List<String> stepList = new ArrayList<>();
        switch (status)  {
            case 2:
                stepList.add("揽货成功，等待网点确认\n" + bean.getTakeorderTime());
                break;
            case 3:
                stepList.add("揽货成功，等待网点确认\n" + bean.getTakeorderTime());
                stepList.add("网点已确认，等待送达\n" + bean.getPickTime());
                break;
            default:
                stepList.add("揽货成功，等待网点确认\n" + bean.getTakeorderTime());
                stepList.add("网点已确认，等待送达\n" + bean.getPickTime());
                stepList.add("货物已送达，订单完成\n" + bean.getReachwarehouseTime());
                break;
        }

        verticalStepView.setProgress(stepList.size() - 1, stepList.size(), stepList.toArray(new String[0]), null);
    }

    private void initStepView(LogisticsOrderPage.ListBean bean, int status) {
        List<String> stepList = new ArrayList<>();

        switch (status) {
            case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());

                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());
                stepList.add("快件已送达，等待用户签收中\n" + bean.getArriveTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());
                stepList.add("快件已送达，等待用户签收\n" + bean.getArriveTime());
                stepList.add("快件已签收，签收人：" + bean.getNameTo() + ",感谢您使用高特App，期待再次为您服务！\n" +
                        bean.getSignTime());
                break;
            default:
                break;
        }



        verticalStepView.setProgress(stepList.size() - 1, stepList.size(), stepList.toArray(new String[0]), null);

    }

    private void initTitleBar() {
        mTvTitle.setText("查看物流");
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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_logistics_detail;
    }
}
