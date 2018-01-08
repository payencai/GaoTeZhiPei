package com.yichan.gaotezhipei.base.component;

import android.os.Bundle;

import com.changelcai.mothership.component.fragment.MSBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ckerv on 2018/1/6.
 */

public abstract class BaseFragment extends MSBaseFragment {

    private Unbinder mUnbinder;
    @Override
    final protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, getContentView());
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        mUnbinder.unbind();
    }
}
