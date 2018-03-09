package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;
import com.yichan.gaotezhipei.server.logisticsdriver.event.RefreshOrderEvent;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/3/9.
 */

public class LogisticsDriverOrderDetailActivity extends BaseActivity {

    private LogisticsDriverOrderItem mBean;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_order_desc)
    TextView tvOrderDesc;

    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;

    @BindView(R.id.tv_warehouse)
    TextView tvWareHouse;

    @BindView(R.id.tv_warehouse_address)
    TextView tvWareHouseAddr;

    @BindView(R.id.tv_network)
    TextView tvNetWork;

    @BindView(R.id.tv_network_addr)
    TextView tvNetWorkAddr;

    @BindView(R.id.tv_goods_count)
    TextView tvGoodCount;


    @BindView(R.id.tv_get_good_time)
    TextView tvGetGoodTime;

    @BindView(R.id.tv_good_addr)
    TextView tvGoodAddr;


    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.item_btn_left)
    Button btnLeft;

    @BindView(R.id.item_btn_right)
    Button btnRight;

    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;


    public static void startActivity(Context context, LogisticsDriverOrderItem bean) {
        Intent intent = new Intent(context, LogisticsDriverOrderDetailActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mBean = (LogisticsDriverOrderItem) getIntent().getSerializableExtra("bean");

        mTvTitle.setText("订单详情");
        tvWareHouse.setText("运往仓库:" + mBean.getWarehouseName());
        tvWareHouseAddr.setText("仓库地址:" + mBean.getWarehouseAdress());

        tvNetWork.setText("服务网点:" + mBean.getNetworkName());
        tvNetWorkAddr.setText("网点地址:" + mBean.getNetworkAdress());

        tvGoodCount.setText("货物件数:" + mBean.getCount() + "件");

        int type = Integer.valueOf(mBean.getDriverStatus());
        switch (type)  {
            case 2:
                tvOrderDesc.setText("等待服务网点确认交货");
                tvOrderTime.setText(mBean.getUpdateTime());
                tvGetGoodTime.setText("接单时间:" + mBean.getUpdateTime());
                tvGoodAddr.setText("装货地址:" + mBean.getNetworkAdress());
                tvDistance.setText("离网点距离:" + mBean.getDistanceDriver() + "km");
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setText("确认收货");
                break;
            case 3:
                tvOrderDesc.setText("等待运往仓库");
                tvOrderTime.setText(mBean.getUpdateTime());
                tvGetGoodTime.setText("派送时间:" + mBean.getPickTime());
                tvGoodAddr.setText("收货地址:" + mBean.getWarehouseAdress());
                tvDistance.setText("离仓库距离:" + mBean.getDistanceDriver() + "km");
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setText("确认送达");
                break;
            default:
                tvOrderDesc.setText("订单已完成，收货仓库:" + mBean.getWarehouseName());
                tvOrderTime.setText(mBean.getUpdateTime());
                tvGetGoodTime.setText("交货时间:" + mBean.getReachwarehouseTime());
                tvGoodAddr.setText("交货地址:" + mBean.getWarehouseAdress());
                tvDistance.setText("离仓库距离:" + 0 + "km");
                rlBottom.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_logistics_driver_order_detail;
    }

    @OnClick({R.id.titlebar_btn_left,R.id.item_btn_left,R.id.item_btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.item_btn_left:
                finish();
                break;
            case R.id.item_btn_right:
                handleOrderByStatus();
                break;
            default:
                break;
        }
    }

    private void handleOrderByStatus() {
        if (Integer.valueOf(mBean.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_CONFIRM) {
            DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认收货吗？", new IDialogResultListener<Integer>() {
                @Override
                public void onDataResult(Integer result) {
                    if (result == -1) {
                        confirmExpress(mBean.getNetworkId(), mBean.getUpdateTime());
                    }
                }
            });
        } else if (Integer.valueOf(mBean.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_DELEVER) {
            DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认送达吗？", new IDialogResultListener<Integer>() {
                @Override
                public void onDataResult(Integer result) {
                    if (result == -1) {
                        deliverExpress(mBean.getNetworkId(), mBean.getUpdateTime());
                    }
                }
            });
        }
    }


    private void confirmExpress(String networkId, String updateTime) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_CONFIRM_ORDER)
                .addParams("networkId", networkId)
                .addParams("confirmTime", updateTime)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("确认收货成功");
                    EventBus.getInstance().post(new RefreshOrderEvent());
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void deliverExpress(String networkId, String updateTime) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_DILIVER)
                .addParams("networkId", networkId)
                .addParams("pickTime", updateTime)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("确认送达成功");
                    EventBus.getInstance().post(new RefreshOrderEvent());
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }



}
