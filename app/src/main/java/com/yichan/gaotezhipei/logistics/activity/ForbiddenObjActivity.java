package com.yichan.gaotezhipei.logistics.activity;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.view.util.ScreenUtil;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.ScreenPlusUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/3/7.
 */

public class ForbiddenObjActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.forbidden_obj_ssiv)
    SubsamplingScaleImageView mSsiv;



    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initImageView();

    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_forbidden_obj;
    }

    private void initImageView() {
        mSsiv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        mSsiv.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
        int sWidth = ScreenPlusUtil.getScreenWidth(this);
        int sHeight = (int) (ScreenPlusUtil.getScreenHeight(this) - ScreenPlusUtil.getStatusBarHeight(this) - ScreenPlusUtil.getNavigationBarHeight(this) - ScreenUtil.dpToPx(ForbiddenObjActivity.this, (float) 45.0));
        float scaleF = 0.0F;
        if(sWidth > sHeight) {
            scaleF = ((float)sHeight / (float) sWidth);
        } else {
            scaleF = ((float)sWidth / (float) sHeight);
        }
        mSsiv.setParallelLoadingEnabled(true);
        mSsiv.setMinScale(scaleF);
        mSsiv.setImage(ImageSource.resource(R.drawable.forbidden_obj),  new ImageViewState(scaleF, new PointF(0, 0), 0));

    }

    private void initTitleBar() {
        mTvTitle.setText("违禁物品");
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
