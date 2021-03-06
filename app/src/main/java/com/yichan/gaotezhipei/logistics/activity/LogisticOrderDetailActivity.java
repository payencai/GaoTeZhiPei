package com.yichan.gaotezhipei.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;
import com.yichan.gaotezhipei.logistics.event.RefreshLCLOrderEvent;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static android.R.attr.type;

/**
 * Created by Simon on 2018/3/16.
 */

public class LogisticOrderDetailActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private LogisticsOrderPage.ListBean mBean;

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

    @BindView(R.id.item_btn_left)
    Button btnLeft;
    @BindView(R.id.item_btn_right)
    Button btnRight;
    @BindView(R.id.item_btn_mid)
    Button btnMid;
    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;

    private Context mContext;

    public static void startActivity(Context context, LogisticsOrderPage.ListBean bean) {
        Intent intent = new Intent(context, LogisticOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderdetailbean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mBean = (LogisticsOrderPage.ListBean) getIntent().getSerializableExtra("orderdetailbean");
        mContext = LogisticOrderDetailActivity.this;
        initTitleBar();

        initFirstLine();
        initSecondLine();
        initThirdLine();
        initGoodInform();
        initBottomLayout();
    }

    private void initFirstLine() {
        mTvMailDistrict.setText(mBean.getAdressToDistrict());
        mTvMailProvinceCity.setText(mBean.getAdressToProvince() + " " + mBean.getAdressToCity());
        mTvPickDistrict.setText(mBean.getAdressFromDistrict());
        mTvPickProvinceCity.setText(mBean.getAdressFromProvince() + " " + mBean.getAdressFromCity());
        int type = Integer.valueOf(mBean.getStatus());
        switch (type) {
            case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                mTvStatus.setText("发布中");
                mTvStatusDesc.setText("等待司机接单");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                mTvStatus.setText("待接货");
                mTvStatusDesc.setText("等待司机接货");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                mTvStatus.setText("待发往");
                mTvStatusDesc.setText("运往仓库途中");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                mTvStatus.setText("运输中");
                mTvStatusDesc.setText("等待物流公司取件");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                mTvStatus.setText("派送中");
                mTvStatusDesc.setText("等待用户签收");
                break;
            case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                mTvStatus.setText("已签收");
                mTvStatusDesc.setText("快件已经签收");
                break;
            default:
                break;
        }
    }

    private void initSecondLine() {
        int type = Integer.valueOf(mBean.getStatus());
        switch (type) {
            case LogisticsContants.TYPE_LOG_ORDER_TO_RECEIVER:
                mTvOrderDesc.setText("等待物流司机接单");
                mTvOrderTime.setText(mBean.getTakeorderTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_GET_CARGO:
                mTvOrderDesc.setText("等待物流司机接货");
                mTvOrderTime.setText(mBean.getPickTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_WAREHOUSE:
                mTvOrderDesc.setText("订单正运往仓库，请耐心等候");
                mTvOrderTime.setText(mBean.getReachwarehouseTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TRANSITING:
                mTvOrderDesc.setText("订单到达仓库，等待物流公司取件");
                mTvOrderTime.setText(mBean.getArriveTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM:
                mTvOrderDesc.setText("订单正在派送中");
                mTvOrderTime.setText(mBean.getConfirmTime());
                break;
            case LogisticsContants.TYPE_LOG_ORDER_FINISH:
                mTvOrderDesc.setText("订单已签收，签收人：" + mBean.getDemanderId() + " " + mBean.getDemanderTelnum());
                mTvOrderTime.setText(mBean.getSignTime());
                break;
            default:
                break;
        }
    }

    private void initThirdLine() {
        int type = Integer.valueOf(mBean.getStatus());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                mTvMailNamePhone.setText("寄件人：" + mBean.getDemanderId() + " " + mBean.getDemanderTelnum());
                mTvMailAddr.setText("寄件地址：" + mBean.getAdressFrom());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                mTvMailNamePhone.setText("收件人：" + mBean.getReceiverTelnum());
                mTvMailAddr.setText("收件地址：" + mBean.getAdressTo());
            default:
                break;
        }


    }

    private void initGoodInform() {
        mTvGoodName.setText("货品：" + mBean.getGoodsName());
        mTvGoodWeight.setText("重量：" + mBean.getGoodsMass() + "kg");
        mTvGoodVolume.setText("体积：" + mBean.getGoodsSize() + "m³");
        mTvGoodCount.setText("运费（快递）：￥" + (mBean.getFreight() == null ? "0.00" : mBean.getFreight()));
        mTvCarType.setText("物流公司：" + mBean.getLogisticsCompanyName());
        mTvGetGoodTime.setText("物流单号：" + mBean.getOrderNumber());
        mTvGetGoodAddr.setText("发布时间：" + mBean.getCreateTime());
    }

    private void initBottomLayout() {
        int type = Integer.valueOf(mBean.getStatus());
        if(UserManager.getInstance(mContext).isDemand()) {//需求方
            if(type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE) {
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setText("取消订单");
            }
            else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO) {//待接货
                rlBottom.setVisibility(View.VISIBLE);
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {//待收货
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM) {//待签收
                rlBottom.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("确认签收");
            } else {//已完成
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
            }
            btnMid.setVisibility(View.VISIBLE);
        } else  {
            if (type == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO) {//待接货
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setText("确认接货");
            } else if (type == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {//待送货
                rlBottom.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("确认送达");
            } else {
                rlBottom.setVisibility(View.GONE);
            }
            btnMid.setVisibility(View.GONE);
        }

        btnMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogisticsDetailActivity.startActivity(LogisticOrderDetailActivity.this, 4, mBean);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance(mContext).isDemand()) {
                    DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认取消订单吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                onHandleOrderByUser(2, mBean.getId());
                            }
                        }
                    });
                } else {
                    DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认取消订单吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                onHandleOrderByDriver(4, mBean.getId());
                            }
                        }
                    });
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance(mContext).isDemand()) {
                    if(Integer.valueOf(mBean.getStatus()) == LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM) {
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认签收吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    onHandleOrderByUser(4, mBean.getId());
                                }
                            }
                        });
                    } else {
                        diallPhone(mBean.getDemanderTelnum());
                    }
                } else {
                    if(Integer.valueOf(mBean.getStatus()) == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO)  {
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认接货吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    onHandleOrderByDriver(2, mBean.getId());
                                }
                            }
                        });
                    } else if(Integer.valueOf(mBean.getStatus()) == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认送达吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    onHandleOrderByDriver(3, mBean.getId());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void onHandleOrderByDriver(final int type, String orderId) {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_DRIVER_UPDATE_ORDER_STATUS)
                .addParams("pdriverOrderId", orderId)
                .addParams("status", String.valueOf(type)).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast(toastMsgWhenSuccess(type));
                    EventBus.getInstance().post(new RefreshLCLOrderEvent());
                    finish();
                } else {
                    showToast(response.getMessage());
                }
            }

        });
    }

    private void onHandleOrderByUser(int status, String orderId) {
        if(status == 4) {//需求方确认签收
            RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_FINISH_ORDER_BY_USER)
                    .addParams("pdriverOrderId", orderId).build();
            call.doScene(new TokenSceneCallback(call) {
                @Override
                protected void handleError(String errorMsg, Call call, Exception e) {
                    showToast(errorMsg);
                }

                @Override
                protected void handleResponse(Result response) {
                    if(response.getResultCode() == Result.SUCCESS_CODE) {
                        showToast("签收成功");
                        EventBus.getInstance().post(new RefreshLCLOrderEvent());
                        finish();
                    } else {
                        showToast(response.getMessage());
                    }
                }

            });
        } else if(status == 2) {
            RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_USER_UPDATE_ORDER_STATUS)
                    .addParams("pdriverOrderId", orderId).build();
            call.doScene(new TokenSceneCallback(call) {
                @Override
                protected void handleError(String errorMsg, Call call, Exception e) {
                    showToast(errorMsg);
                }

                @Override
                protected void handleResponse(Result response) {
                    if(response.getResultCode() == Result.SUCCESS_CODE) {
                        showToast(toastMsgWhenSuccess(type));
                        EventBus.getInstance().post(new RefreshLCLOrderEvent());
                        finish();
                    } else {
                        showToast(response.getMessage());
                    }
                }

            });
        }
    }

    private String toastMsgWhenSuccess(int type) {
        if(type == 4) {
            return "取消订单成功";
        } else if(type == 2) {
            return "取货成功";
        } else if(type == 3) {
            return "已送达";
        }
        return "操作成功";
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lcl_order_detail;
    }

    private void initTitleBar() {
        mTvTitle.setText("订单详情");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.fl_arrowright})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.fl_arrowright:
                LogisticsDetailActivity.startActivity(LogisticOrderDetailActivity.this, 4, mBean);
                break;
            default:
                break;
        }
    }

    private void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
