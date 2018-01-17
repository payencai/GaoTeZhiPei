package com.yichan.gaotezhipei.mine.fragment;

import android.content.Intent;
import android.view.View;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.util.ActivityAnimationUtil;
import com.yichan.gaotezhipei.login.activity.DemandLoginActivity;
import com.yichan.gaotezhipei.mine.activity.AddressMangeActivity;
import com.yichan.gaotezhipei.mine.activity.BecomeLCLDriverActivity;
import com.yichan.gaotezhipei.mine.activity.FeedbackActivity;
import com.yichan.gaotezhipei.mine.activity.MyMessageActivity;
import com.yichan.gaotezhipei.mine.activity.SettingActivity;

import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/8.
 */

public class MineFragment extends BaseFragment {
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.mine_rl_address_manage,R.id.mine_iv_setting,R.id.mine_tv_setting,R.id.mine_rl_become_lcl_driver,R.id.mine_rl_my_message,R.id.mine_tv_login,R.id.mine_rl_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_rl_address_manage:
                getActivity().startActivity(new Intent(getActivity(), AddressMangeActivity.class));
                break;
            case R.id.mine_iv_setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.mine_tv_setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.mine_rl_become_lcl_driver:
                getActivity().startActivity(new Intent(getActivity(), BecomeLCLDriverActivity.class));
                break;
            case R.id.mine_rl_my_message:
                getActivity().startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            case R.id.mine_tv_login:
                ActivityAnimationUtil.startActivityWithAnim(getActivity(), DemandLoginActivity.class, R.anim.activity_in, 0, null);
                break;
            case R.id.mine_rl_feedback:
                getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            default:
                break;
        }
    }
}
