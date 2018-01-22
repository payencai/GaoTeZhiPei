package com.yichan.gaotezhipei.hatchservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.hatchservice.constant.HatchServiceConstants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/10.
 */

public class HatchServiceActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.hatch_vp_banner)
    AutoScrollViewPagerWithIndicator mVpBanner;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initBannerViewPager();
    }

    private void initTitleBar() {
        mTvTitle.setText("众创孵化");
    }

    private void initBannerViewPager() {
        mVpBanner.setDisplayDots(false);
        LayoutInflater inflate = LayoutInflater.from(HatchServiceActivity.this);
        for (int i = 0; i < HatchServiceConstants.BANNER_IMAGES.length; i++) {
            View view = inflate.inflate(R.layout.service_center_asvp_layout,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
            imageView.setImageResource(HatchServiceConstants.BANNER_IMAGES[i]);
            mVpBanner.addViewToViewPager(imageView);
        }

        mVpBanner.getAutoScrollViewPager().setInterval(3000);
        mVpBanner.getAutoScrollViewPager().setOffscreenPageLimit(3);
        mVpBanner.getAutoScrollViewPager().startAutoScroll();
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_hatch;
    }

    @OnClick({R.id.titlebar_btn_left, R.id.hatch_cat_ll_intro, R.id.hatch_cat_ll_employment, R.id.hatch_cat_ll_entrepreneurial,R.id.hatch_cat_ll_artdesign,R.id.hatch_cat_ll_hatchproject,R.id.hatch_btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.hatch_cat_ll_intro:
                startActivity(new Intent(HatchServiceActivity.this, HatchIntroActivity.class));
                break;
            case R.id.hatch_cat_ll_employment:
                startActivity(new Intent(HatchServiceActivity.this, EmploymentActivity.class));
                break;
            case R.id.hatch_cat_ll_entrepreneurial:
                startActivity(new Intent(HatchServiceActivity.this, EntrepreneurialActivity.class));
                break;
            case R.id.hatch_cat_ll_artdesign:
                startActivity(new Intent(HatchServiceActivity.this, ArtDesignActivity.class));
                break;
            case R.id.hatch_cat_ll_hatchproject:
                startActivity(new Intent(HatchServiceActivity.this, HatchProjectActivity.class));
                break;
            case R.id.hatch_btn_apply:
                startActivity(new Intent(HatchServiceActivity.this, HatchApplyActivity.class));
                break;
            default:
                break;
        }
    }
}
