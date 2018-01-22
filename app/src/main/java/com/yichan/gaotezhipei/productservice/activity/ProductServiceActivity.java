package com.yichan.gaotezhipei.productservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.activity.CommonWebViewProgressActivity;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.productservice.constant.ProductServiceConstants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/11.
 */

public class ProductServiceActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.product_vp_banner)
    AutoScrollViewPagerWithIndicator mVpBanner;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initBannerViewPager();
    }

    private void initTitleBar() {
        mTvTitle.setText("产品服务");
    }

    private void initBannerViewPager() {
        mVpBanner.setDisplayDots(false);
        LayoutInflater inflate = LayoutInflater.from(ProductServiceActivity.this);
        for (int i = 0; i < ProductServiceConstants.BANNER_IMAGES.length; i++) {
            View view = inflate.inflate(R.layout.service_center_asvp_layout,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
            imageView.setImageResource(ProductServiceConstants.BANNER_IMAGES[i]);
            mVpBanner.addViewToViewPager(imageView);
        }

        mVpBanner.getAutoScrollViewPager().setInterval(3000);
        mVpBanner.getAutoScrollViewPager().setOffscreenPageLimit(3);
        mVpBanner.getAutoScrollViewPager().startAutoScroll();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product;
    }

    @OnClick({R.id.titlebar_btn_left,R.id.product_cat_ll_gaote_shop, R.id.product_cat_ll_brand_grow,R.id.product_cat_ll_source_product,R.id.prolduct_cat_ll_taobao_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.product_cat_ll_brand_grow:
                startActivity(new Intent(ProductServiceActivity.this, BrandGrowActivity.class));
                break;
            case R.id.product_cat_ll_gaote_shop:
                CommonWebViewProgressActivity.startActivity(ProductServiceActivity.this, "商城","https://shop156991344.taobao.com");
                break;
            case R.id.prolduct_cat_ll_taobao_shop:
                CommonWebViewProgressActivity.startActivity(ProductServiceActivity.this,"云南农溯","http://m.wdwd.com/supplier/sindex/2EE7R?openid=ogdMVswh-cR1rO6KF3JZmujnZY9I");
                break;
            case R.id.product_cat_ll_source_product:
                startActivity(new Intent(ProductServiceActivity.this, SourceProductActivity.class));
                break;
            default:
                break;
        }
    }
}
