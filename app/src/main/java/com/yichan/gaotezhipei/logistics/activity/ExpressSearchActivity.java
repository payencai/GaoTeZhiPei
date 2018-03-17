package com.yichan.gaotezhipei.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.esharp.android.common.EConnectionException;
import com.esharp.android.common.EInvalidNetworkException;
import com.esharp.android.common.EResponseErrorException;
import com.esharp.android.common.SpecialAsyncTask;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.AsyncTaskManager;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.KdniaoTrackQueryAPI;
import com.yichan.gaotezhipei.server.netstation.activity.ChooseExpressCompanyActivity;
import com.yichan.gaotezhipei.server.netstation.event.ChooseExpressCompanyEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 快递查询
 * Created by ckerv on 2018/1/12.
 */

public class ExpressSearchActivity extends BaseActivity{

    @BindView(R.id.edt_order_no)
    EditText mEdtOrderNo;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_express_company)
    TextView mTvExpressCompany;

    private String mShipperCode;
    private String mResult = null;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
        initTitleBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    private void initTitleBar() {
        mTvTitle.setText("快递查询");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.btn_search,R.id.ll_express_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btn_search:
                searchExpress();
                break;
            case R.id.ll_express_company:
                chooseExpressCompany();
                break;
            default:
                break;
        }
    }

    private void chooseExpressCompany() {
        startActivity(new Intent(ExpressSearchActivity.this, ChooseExpressCompanyActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(ChooseExpressCompanyEvent event) {
        mShipperCode = event.companyId;
        mTvExpressCompany.setTextColor(Color.parseColor("#333333"));
        mTvExpressCompany.setText(event.companyName);
    }


    private void searchExpress() {
        //已定论：调用公共API查询物流单号（这里待讨论，究竟是调用后台接口查询订单号还是调用公共API查询物流单号）
        //Toast.makeText(ExpressSearchActivity.this, "暂无物流信息，请稍后查询。", Toast.LENGTH_SHORT).show();
        String orderNo = mEdtOrderNo.getText().toString();

//        callApi(this, "ZTOKY", "630878031817");

        if (orderNo == null || "".equals(orderNo)) {
            showToast("请输入订单号！");
        }else if(mShipperCode == null || "".equals(mShipperCode))
            showToast("请选择快递公司！");
        else
            callApi(this, mShipperCode, orderNo);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_express_search;
    }

    //Call Kdniao API
    public void callApi(Context ctx, final String expCode, final String expNo) {
        SpecialAsyncTask<Void, Void, Void> task = new SpecialAsyncTask<Void, Void, Void>(
                ctx) {

            @Override
            protected boolean doOnException(Exception ex) {
                boolean handle = false;

                if (ex instanceof EInvalidNetworkException || ex instanceof EConnectionException || ex instanceof EResponseErrorException) {
                    handle = true;
                }

                return handle;
            }

            @Override
            protected Void doInBackground(Void params)
                    throws EConnectionException, EInvalidNetworkException,
                    EResponseErrorException {
                KdniaoTrackQueryAPI api = KdniaoTrackQueryAPI.getInstance();
                try {
                    //Toast.makeText(ExpressSearchActivity.this, result, Toast.LENGTH_SHORT).show();
                    mResult = api.getOrderTracesByJson(expCode, expNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void doOnSuccess(Void result) {
                Log.d("TAG", ">>>>>>mResult:" + mResult);
                Intent intent = new Intent(ExpressSearchActivity.this, ExpressSearchResultActivity.class);
                intent.putExtra("result", mResult);
                startActivity(intent);
            }
        };
        AsyncTaskManager.executeAsyncTask(hashCode(), task);
    }
}
