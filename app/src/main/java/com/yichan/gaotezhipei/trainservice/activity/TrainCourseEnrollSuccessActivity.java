package com.yichan.gaotezhipei.trainservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by simon on 2018/2/9 0009.
 */

public class TrainCourseEnrollSuccessActivity extends BaseActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_train_course_enroll_success;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
    }

    private void initView() {
        mTvTitle.setText("课程报名");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.btnReturn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btnReturn:
                Intent activityIntent = new Intent();
                activityIntent.setClass(TrainCourseEnrollSuccessActivity.this, TrainServiceActivity.class);
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activityIntent);
                break;
            default:
                break;
        }
    }
}
