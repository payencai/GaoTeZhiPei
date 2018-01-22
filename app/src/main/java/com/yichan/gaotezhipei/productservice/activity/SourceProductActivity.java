package com.yichan.gaotezhipei.productservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shizhefei.view.largeimage.LargeImageView;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/22.
 */

public class SourceProductActivity extends BaseActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.source_product_liv)
    LargeImageView mLiv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_source_product;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();

        initLargeImageView();
    }

    private void initLargeImageView() {
        mLiv.setImage(R.drawable.source_product);
        mLiv.setEnabled(false);
    }

    private void initTitleBar() {
        mTvTitle.setText("溯源产品");
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
