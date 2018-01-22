package com.yichan.gaotezhipei.finaceservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/9.
 */

public class FinaceServiceActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.finace_ll_loan)
    ViewGroup mVgLoan;

    @BindView(R.id.finace_ll_driblet_assure)
    ViewGroup mVgAssure;

    @BindView(R.id.finace_ll_venture)
    ViewGroup mVgVenture;

    ViewGroup mCurrentVg;

    private List<ViewGroup> mContents;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();

        initDisplayContent();
    }

    private void initDisplayContent() {
        mContents = new ArrayList<>();
        mContents.add(mVgLoan);
        mContents.add(mVgAssure);
        mContents.add(mVgVenture);
        mCurrentVg = mVgLoan;
    }

    private void initTitleBar() {
        mTvTitle.setText("金融服务");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_finace;
    }

    @OnClick({R.id.titlebar_btn_left,R.id.fc_cat_ll_assure,R.id.fc_cat_ll_loan,R.id.fc_cat_ll_venture,R.id.finace_btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.fc_cat_ll_loan:
                switchContent(0);
                break;
            case R.id.fc_cat_ll_assure:
                switchContent(1);
                break;
            case R.id.fc_cat_ll_venture:
                switchContent(2);
                break;
            case R.id.finace_btn_apply:
                startActivity(new Intent(FinaceServiceActivity.this, VentureApplyActivity.class));
                break;
            default:
                break;
        }
    }

    private void switchContent(int index) {
        ViewGroup vg = mContents.get(index);
        if(vg == mCurrentVg) {
            return;
        }
        mCurrentVg.setVisibility(View.GONE);
        vg.setVisibility(View.VISIBLE);
        mCurrentVg = vg;
    }

}
