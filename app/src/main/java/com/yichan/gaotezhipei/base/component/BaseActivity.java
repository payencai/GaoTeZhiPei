package com.yichan.gaotezhipei.base.component;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.changelcai.mothership.component.activity.MSBaseActivity;
import com.yichan.gaotezhipei.base.util.DialogFragmentHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ckerv on 2018/1/6.
 */

public abstract class BaseActivity extends MSBaseActivity {

    private Unbinder mUnbinder;
    private DialogFragment mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this);//使用butterknife
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    protected void showLoading(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = DialogFragmentHelper.showProgress(getSupportFragmentManager(), msg, true);
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
