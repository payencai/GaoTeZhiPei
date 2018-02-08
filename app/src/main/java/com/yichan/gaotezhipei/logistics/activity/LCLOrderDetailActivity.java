package com.yichan.gaotezhipei.logistics.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by ckerv on 2018/2/8.
 */

public class LCLOrderDetailActivity extends BaseActivity {

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
    private TextView tvGetCargoTime;
    private TextView tvGetCargoAddr;

    private VerticalStepView verticalStepView;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initView();
        initTitleBar();
        initStepView();
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

        tvGetCargoTime = (TextView) findViewById(R.id.item_tv_get_cargo_time);
        tvGetCargoAddr = (TextView) findViewById(R.id.item_tv_get_cargo_addr);

        verticalStepView = (VerticalStepView) findViewById(R.id.lcl_order_detail_vsv);
    }

    private void initStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("接已提交定案，等待系统确认");
        stepList.add("您的商品需要从外地调拨，我们会尽快处理，请耐心等待");
        stepList.add("您的商品需要从外地调拨，我们会尽快处理，请耐心等待sasdasdasdas");
        stepList.add("您的商品需要从外地调拨，我们会尽快处理，aaaaaa等待");
        verticalStepView.setStepsViewIndicatorComplectingPosition(0)//设置完成的步数
                .setStepViewTexts(stepList)//总步骤
                .setTextSize(14)
                .reverseDraw(false)
                .setLinePaddingProportion(1)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.step_uncompleted_color))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.step_uncompleted_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this,R.color.step_completed_color))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.step_uncompleted_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.completed_icon))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.uncompleted_icon))//设置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.completed_icon));//设置StepsViewIndicator DefaultIcon

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
        return R.layout.activity_lcl_order_detail;
    }
}
