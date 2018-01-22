//package com.yichan.gaotezhipei.finaceservice.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.yichan.gaotezhipei.R;
//import com.yichan.gaotezhipei.base.component.BaseActivity;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by ckerv on 2018/1/9.
// */
//
//public class DribeltAssureActivity extends BaseActivity {
//
//    @BindView(R.id.titlebar_tv_title)
//    TextView mTvTitle;
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//        super.init(savedInstanceState);
//        initTitleBar();
//    }
//
//    private void initTitleBar() {
//        mTvTitle.setText("小额担保");
//    }
//
//
//    @OnClick({R.id.titlebar_btn_left,R.id.fc_cat_ll_assure,R.id.fc_cat_ll_loan,R.id.fc_cat_ll_venture,R.id.assure_btn_apply})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.titlebar_btn_left:
//                finish();
//                break;
//            case R.id.fc_cat_ll_assure:
//                startActivity(new Intent(DribeltAssureActivity.this, DribeltAssureActivity.class));
//                break;
//            case R.id.fc_cat_ll_venture:
//                startActivity(new Intent(DribeltAssureActivity.this, VentureActivity.class));
//                break;
//            case R.id.assure_btn_apply:
//                startActivity(new Intent(DribeltAssureActivity.this, VentureApplyActivity.class));
//                break;
//            case R.id.fc_cat_ll_loan:
//                startActivity(new Intent(DribeltAssureActivity.this, FinaceServiceActivity.class));
//                break;
//            default:
//                break;
//        }
//    }
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_driblet_assure;
//    }
//}
