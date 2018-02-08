package com.yichan.gaotezhipei.trainservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.trainservice.view.CourseOutlineAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class TrainCourseEnrollActivity extends BaseActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_train_course_enroll;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
    }

    private void initView() {
        mTvTitle.setText("课程报名");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.btnApplyForEnroll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btnApplyForEnroll:
                Toast.makeText(this, "Enroll Succeed.", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(TrainCourseEnrollActivity.this, TrainCourseEnrollSuccessActivity.class));
                break;
            default:
                break;
        }
    }
}
