package com.yichan.gaotezhipei.productservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/11.
 */

public class BrandGrowActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("品牌培育");
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
        return R.layout.activitiy_brand_grow;
    }
}
