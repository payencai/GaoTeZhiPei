package com.yichan.gaotezhipei.lcl.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.lcl.event.SwitchToNextEvent;
import com.yichan.gaotezhipei.lcl.event.SwitchToPreEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拼货四个子fragment的基类。
 * Created by ckerv on 2018/1/12.
 */

public abstract class LCLBaseDetailFragment extends BaseFragment {

    @BindView(R.id.car_detail_iv_type)
    ImageView mIvCarType;

    @BindView(R.id.car_detail_tv_weight)
    TextView mTvWeight;

    @BindView(R.id.car_detail_tv_size)
    TextView mTvCarSize;

    @BindView(R.id.car_detail_tv_volume)
    TextView mTvCarVolume;

    @BindView(R.id.car_detail_iv_left)
    ImageView mIvLeft;

    @BindView(R.id.car_detail_iv_right)
    ImageView mIvRight;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lcl_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if(isFistFragment()) {
            mIvLeft.setVisibility(View.GONE);
        }
        if(isLastFragment()) {
            mIvRight.setVisibility(View.GONE);
        }
        mTvWeight.setText(getCarWeight());
        mIvCarType.setImageResource(getCarTypeResId());
        mTvCarSize.setText(getCarSize());
        mTvCarVolume.setText(getCarVolume());
    }



    @OnClick({R.id.car_detail_iv_left, R.id.car_detail_iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_detail_iv_left:
                EventBus.getInstance().post(new SwitchToPreEvent());
                break;
            case R.id.car_detail_iv_right:
                EventBus.getInstance().post(new SwitchToNextEvent());
            default:
                break;
        }
    }

    /**
     * 是否是第一个fragment，如果是，则隐藏掉左按钮
     * @return
     */
    protected boolean isFistFragment() {
        return false;
    };

    /**
     * 是否是最后一个fragment，如果是，则隐藏掉右按钮
     * @return
     */
    protected boolean isLastFragment() {
        return false;
    };

    /**
     * @return  汽车类型图片的id
     */
    protected abstract int getCarTypeResId();

    /**
     * @return  汽车大小
     */
    protected abstract String getCarSize();

    /**
     * @return 汽车载货体积
     */
    protected abstract String getCarVolume();

    /**
     * @return 汽车重量
     */
    protected abstract String getCarWeight();

    /**
     * 1为小面包车，2为中面包车，3为小货车，4为中货车
     * @return
     */
    protected abstract int getType();

    protected abstract String getTypeStr();
}
