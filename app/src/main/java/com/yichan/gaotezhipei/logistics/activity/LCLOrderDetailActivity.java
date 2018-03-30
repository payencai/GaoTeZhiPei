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
import com.yichan.gaotezhipei.logistics.event.RefreshLCLOrderEvent;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static android.R.attr.type;

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

    @BindView(R.id.item_btn_left)
    Button btnLeft;
    @BindView(R.id.item_btn_right)
    Button btnRight;
    @BindView(R.id.item_btn_mid)
    Button btnMid;
    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;

    private Context mContext;

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
        mContext = LCLOrderDetailActivity.this;
        initTitleBar();

        initFirstLine();
        initSecondLine();
        initThirdLine();
        initGoodInform();
        initBottomLayout();
    }

    private void initFirstLine() {
        mTvMailDistrict.setText(mBean.getAddress().getArea());
        mTvMailProvinceCity.setText(mBean.getAddress().getProvince() + " " + mBean.getAddress().getCity());
        mTvPickDistrict.setText(mBean.getConsigneeArea());
        mTvPickProvinceCity.setText(mBean.getConsigneeProvince() + " " + mBean.getConsigneeCity());
        int type = Integer.valueOf(mBean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                mTvStatus.setText("发布中");
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
                mTvOrderDesc.setText("您的订单开始处理");
                mTvOrderTime.setText(mBean.getOrderTime());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                mTvOrderDesc.setText("司机前往接货，请耐心等候");
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
        int type = Integer.valueOf(mBean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                mTvMailNamePhone.setText("寄件人：" + mBean.getAddress().getName() + " " + mBean.getAddress().getTelephone());
                mTvMailAddr.setText("寄件地址：" + mBean.getAddress().getAddress());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                mTvMailNamePhone.setText("收件人：" + mBean.getConsignee() + " " + mBean.getConsigneeTelephone());
                mTvMailAddr.setText("收件地址：" + mBean.getConsigneeAddress());
            default:
                break;
        }


    }

    private void initGoodInform() {
        mTvGoodName.setText("货品：" + mBean.getArticleName());
        mTvGoodWeight.setText("重量：" + mBean.getWeight() + "kg");
        mTvGoodVolume.setText("体积：" + mBean.getVolume() + "m³");
        mTvGoodCount.setText("数量：" + mBean.getNum() + "件");
        mTvCarType.setText("所需车型：" + mBean.getAnticipantCar() + getSizeByCar(mBean.getAnticipantCar()));
        int type = Integer.valueOf(mBean.getType());
        switch (type) {
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE:
                mTvGetGoodTime.setText("下单时间：" + mBean.getOrderTime());
                mTvGetGoodAddr.setText("取货地址：" + mBean.getPickupAddress());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO:
                mTvGetGoodTime.setText("取货时间：" + mBean.getAnticipantTime());
                mTvGetGoodAddr.setText("取货地址：" + mBean.getPickupAddress());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO:
                mTvGetGoodTime.setText("取货时间：" + mBean.getGetTime());
                mTvGetGoodAddr.setText("取货地址：" + mBean.getPickupAddress());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM:
                mTvGetGoodTime.setText("派送时间：" + mBean.getSendTime());
                mTvGetGoodAddr.setText("收货地址：" + mBean.getConsigneeAddress());
                break;
            case LogisticsContants.TYPE_LCL_ORDER_TO_FINISH:
                mTvGetGoodTime.setText("签收时间：" + mBean.getEndTime());
                mTvGetGoodAddr.setText("收货地址：" + mBean.getConsigneeAddress());
                break;
        }
    }

    private String getSizeByCar(String car) {
        if(car == null) {
            return "";
        } else if(car.equals("摩托车")) {
            return "(1.9*0.8*1.1)米";
        } else if(car.equals("小轿车")) {
            return "(3.5*1.5*1.5)米";
        } else if(car.equals("三轮车")){
            return "(3.5*1.2*1.8)米";
        } else if(car.equals("小面包车")) {
            return "(1.8*1.3*1.1)米";
        } else if(car.equals("中面包车")) {
            return "(2.7*1.4*1.2)米";
        } else if(car.equals("小货车")) {
            return "(2.7*1.5*1.7)米";
        } else if(car.equals("中货车")) {
            return "(4.2*1.8*1.8)米";
        } else {
            return "";
        }
    }

    private void initBottomLayout() {
        int type = Integer.valueOf(mBean.getType());
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
                LogisticsDetailActivity.startActivity(LCLOrderDetailActivity.this, 3, mBean);
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
                    if(Integer.valueOf(mBean.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM) {
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认签收吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    onHandleOrderByUser(4, mBean.getId());
                                }
                            }
                        });
                    } else {
                        diallPhone(mBean.getDriverTelephone());
                    }
                } else {
                    if(Integer.valueOf(mBean.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO)  {
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认接货吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    onHandleOrderByDriver(2, mBean.getId());
                                }
                            }
                        });
                    } else if(Integer.valueOf(mBean.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {
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
                LogisticsDetailActivity.startActivity(LCLOrderDetailActivity.this, 3, mBean);
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
