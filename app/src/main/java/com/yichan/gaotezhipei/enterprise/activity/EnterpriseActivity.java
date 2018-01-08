package com.yichan.gaotezhipei.enterprise.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.enterprise.constant.EnterpriseConstants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/8.
 */

public class EnterpriseActivity extends BaseActivity {


    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbtnLeft;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.ep_vp_banner)
    AutoScrollViewPagerWithIndicator mVpBanner;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initBannerViewPager();
    }

    private void initTitleBar() {
        mIbtnLeft.setVisibility(View.VISIBLE);
        mIbtnLeft.setImageResource(R.drawable.back);
        mTvTitle.setText("企业办理");
    }

    private void initBannerViewPager() {
        mVpBanner.setDisplayDots(false);
        LayoutInflater inflate = LayoutInflater.from(EnterpriseActivity.this);
        for (int i = 0; i < EnterpriseConstants.BANNER_IMAGES.length; i++) {
            View view = inflate.inflate(R.layout.service_center_asvp_layout,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
            imageView.setImageResource(EnterpriseConstants.BANNER_IMAGES[i]);
            mVpBanner.addViewToViewPager(imageView);
        }

        mVpBanner.getAutoScrollViewPager().setInterval(3000);
        mVpBanner.getAutoScrollViewPager().setOffscreenPageLimit(3);
        mVpBanner.getAutoScrollViewPager().startAutoScroll();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_enterprise;
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
}
