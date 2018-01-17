package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.view.StepView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/14.
 */

public class BecomeLCLDriverActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.lcl_driver_step_view)
    StepView mStepView;

    @BindView(R.id.lcl_driver_vg_step1)
    ViewGroup mVgStep1;

    @BindView(R.id.lcl_driver_vg_step2)
    ViewGroup mVgStep2;

    @BindView(R.id.lcl_driver_vg_step3)
    ViewGroup mVgStep3;

    @BindView(R.id.lcl_driver_btn_operate)
    Button mBtnOperate;

    private int mCurrentStep = 1;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initStepView();
    }

    private void initTitleBar() {
        mTvTitle.setText("实人同行证");
    }

    private void initStepView() {
        List<String> steps = Arrays.asList(new String[]{"个人信息", "车辆信息", "照片信息"});
        mStepView.setSteps(steps);
        mStepView.selectedStep(mCurrentStep);
    }

    @OnClick({R.id.titlebar_btn_left, R.id.lcl_driver_btn_operate, R.id.step2_rl_choose_car_type, R.id.step2_rl_choose_car_plate_color})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.lcl_driver_btn_operate:
                showNextStep();
                break;
            case R.id.step2_rl_choose_car_type:
                startActivity(new Intent(BecomeLCLDriverActivity.this, ChooseCarTypeActivity.class));
                break;
            case R.id.step2_rl_choose_car_plate_color:
                showChooseCarPlateColorDialog();
            default:
                break;
        }
    }

    private void showChooseCarPlateColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BecomeLCLDriverActivity.this);
        View view = View
                .inflate(BecomeLCLDriverActivity.this, R.layout.dialog_choose_car_plate_color, null);
        ImageView ivBlueSign = (ImageView) view.findViewById(R.id.dialog_iv_bluesign);

        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        ivBlueSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BecomeLCLDriverActivity.this, "蓝牌", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void showNextStep() {
        mCurrentStep++;
        switch (mCurrentStep) {
            case 2:
                mVgStep1.setVisibility(View.GONE);
                mVgStep2.setVisibility(View.VISIBLE);
                mStepView.selectedStep(mCurrentStep);
                break;
            case 3:
                mVgStep2.setVisibility(View.GONE);
                mVgStep3.setVisibility(View.VISIBLE);
                mBtnOperate.setText("确认上传");
                mStepView.selectedStep(mCurrentStep);
            default:
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_become_lcl_driver;
    }
}
