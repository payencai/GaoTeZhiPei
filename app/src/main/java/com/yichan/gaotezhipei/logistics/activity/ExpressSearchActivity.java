package com.yichan.gaotezhipei.logistics.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.server.netstation.activity.ChooseExpressCompanyActivity;
import com.yichan.gaotezhipei.server.netstation.event.ChooseExpressCompanyEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递查询
 * Created by ckerv on 2018/1/12.
 */

public class ExpressSearchActivity extends BaseActivity{

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_express_company)
    TextView mTvExpressCompany;



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
        mTvExpressCompany.setTextColor(Color.parseColor("#333333"));
        mTvExpressCompany.setText(event.companyName);
    }


    private void searchExpress() {
        //TODO 这里待讨论，究竟是调用后台接口查询订单号还是调用公共API查询物流单号
        Toast.makeText(ExpressSearchActivity.this, "暂无物流信息，请稍后查询。", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_express_search;
    }
}
