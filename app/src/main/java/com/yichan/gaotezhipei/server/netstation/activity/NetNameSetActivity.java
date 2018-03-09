package com.yichan.gaotezhipei.server.netstation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class NetNameSetActivity extends BaseActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.titlebar_tv_right)
    TextView mTvRight;
    @BindView(R.id.edtNickName)
    EditText edtNickName;

    private String mNickName;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_net_name_set;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mNickName = bundle.getString("nick");
        }
        initTitleBar();
        edtNickName.setText(mNickName);
    }

    private void initTitleBar() {
        mTvTitle.setText("设置店铺名称");
        mTvRight.setTextColor(getResources().getColor(R.color.text_blue_color));
    }

    @OnClick({R.id.titlebar_tv_left,R.id.imgNickNameDel,R.id.titlebar_tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_tv_left:
                finish();
                break;
            case R.id.titlebar_tv_right:
                Intent intent = new Intent();
                intent.putExtra("nick", edtNickName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.imgNickNameDel:
                //set page
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                edtNickName.setFocusable(true);
                edtNickName.setFocusableInTouchMode(true);
                edtNickName.setText("");
                edtNickName.requestFocus();
                break;
            default:
                break;
        }
    }
}
