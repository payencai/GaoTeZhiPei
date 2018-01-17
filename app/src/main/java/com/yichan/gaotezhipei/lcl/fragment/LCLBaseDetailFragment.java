package com.yichan.gaotezhipei.lcl.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;

import butterknife.BindView;

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
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if(isFistFragment()) {
            mIvLeft.setVisibility(View.GONE);
        }
        if(isLastFragment()) {
            mIvRight.setVisibility(View.GONE);
        }
        mIvCarType.setImageResource(getCarTypeResId());
        mTvCarSize.setText(getCarSize());
        mTvCarVolume.setText(getCarVolume());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lcl_detail;
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
}
