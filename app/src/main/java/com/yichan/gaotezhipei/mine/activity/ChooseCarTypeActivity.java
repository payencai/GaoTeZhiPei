package com.yichan.gaotezhipei.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sevenheaven.segmentcontrol.SegmentControl;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.mine.event.ChooseCarTypeEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/14.
 */

public class ChooseCarTypeActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.segmentcontrol_car_type)
    SegmentControl segmentControl;

    @BindView(R.id.tv_weight)
    TextView mTvWeight;

    @BindView(R.id.tv_size)
    TextView mTvSize;

    @BindView(R.id.tv_volume)
    TextView mTvVolume;

    private String mChooseCarType = "摩托车";

    private String mChooseCarWeight = "200";
    private String mChooseCarVolume = "0.8";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_car_type;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("车辆类型选择");

        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                switch (index) {
                    case 0:
                        mTvWeight.setText("200公斤");
                        mTvSize.setText("1.9*0.8*1.1米");
                        mTvVolume.setText("0.8方");
                        mChooseCarType = "摩托车";
                        mChooseCarWeight = "200";
                        mChooseCarVolume = "0.8";
                        break;
                    case 1:
                        mTvWeight.setText("450公斤");
                        mTvSize.setText("3.5*1.5*1.5米");
                        mTvVolume.setText("1.6方");
                        mChooseCarType = "小轿车";
                        mChooseCarWeight = "450";
                        mChooseCarVolume = "1.6";
                        break;
                    case 2:
                        mTvWeight.setText("370公斤");
                        mTvSize.setText("3.5*1.2*1.8米");
                        mTvVolume.setText("5.8方");
                        mChooseCarType = "三轮车";
                        mChooseCarWeight = "370";
                        mChooseCarVolume = "5.8";
                        break;
                    case 3:
                        mTvWeight.setText("500公斤");
                        mTvSize.setText("1.8*1.3*1.1米");
                        mTvVolume.setText("2.6方");
                        mChooseCarType = "小面包车";
                        mChooseCarWeight = "500";
                        mChooseCarVolume = "2.6";
                        break;
                    case 4:
                        mTvWeight.setText("1吨");
                        mTvSize.setText("2.7*1.4*1.2米");
                        mTvVolume.setText("4.5方");
                        mChooseCarType = "中面包车";
                        mChooseCarWeight = "1000";
                        mChooseCarVolume = "4.5";
                        break;
                    case 5:
                        mTvWeight.setText("1吨");
                        mTvSize.setText("2.7*1.5*1.7米");
                        mTvVolume.setText("6.9方");
                        mChooseCarType = "小货车";
                        mChooseCarWeight = "1000";
                        mChooseCarVolume = "6.9";
                        break;
                    case 6:
                        mTvWeight.setText("1.8吨");
                        mTvSize.setText("4.2*1.8*1.8米");
                        mTvVolume.setText("13.6方");
                        mChooseCarType = "中货车";
                        mChooseCarWeight = "1800";
                        mChooseCarVolume = "13.6";
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @OnClick({R.id.titlebar_btn_left, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btn_sure:
                chooseCompleted();
            default:
                break;
        }
    }

    private void chooseCompleted() {
        EventBus.getInstance().post(new ChooseCarTypeEvent(mChooseCarType, mTvSize.getText().toString(),
                mChooseCarWeight, mChooseCarVolume));
        finish();
    }
}
