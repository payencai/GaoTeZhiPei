package com.yichan.gaotezhipei.mine.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.util.ActivityAnimationUtil;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.login.activity.DemandLoginActivity;
import com.yichan.gaotezhipei.login.event.LoginEvent;
import com.yichan.gaotezhipei.mine.activity.AddressMangeActivity;
import com.yichan.gaotezhipei.mine.activity.BecomeLCLDriverActivity;
import com.yichan.gaotezhipei.mine.activity.FeedbackActivity;
import com.yichan.gaotezhipei.mine.activity.MyMessageActivity;
import com.yichan.gaotezhipei.mine.activity.SettingActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/8.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_tv_account)
    TextView mTvAccount;
    @BindView(R.id.mine_tv_login)
    TextView mTvLogin;



    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (!EventBus.getInstance().isRegistered(this))
            EventBus.getInstance().register(this);

        initView();
    }

    private void initView() {
        if(UserManager.getInstance(getActivity()).isLogin()) {
            String accout = UserManager.getInstance(getActivity()).getAccount();
            if(accout != null) {
                mTvAccount.setText(accout);
                mTvLogin.setVisibility(View.GONE);
            } else {
                mTvAccount.setText("Hi,您未登陆");
                mTvLogin.setVisibility(View.VISIBLE);
            }
        } else {
            mTvAccount.setText("Hi,您未登陆");
            mTvLogin.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverLoginEvent(LoginEvent loginEvent) {
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.mine_rl_address_manage,R.id.mine_iv_setting,R.id.mine_tv_setting,R.id.mine_rl_become_lcl_driver,R.id.mine_rl_my_message,R.id.mine_tv_login,R.id.mine_rl_feedback,R.id.mine_rl_change_role})
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
            case R.id.mine_rl_change_role:
                showChangeRoleDialog();
                break;
            default:
                break;
        }
    }

    private void showChangeRoleDialog() {
        //TODO 检查权限
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_role_has_permisson, null);
        DialogHelper.showCustomDialog(view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_role_no_permission, null);
//        final Dialog dialog = DialogHelper.showCustomDialog(view, true);
//        view.findViewById(R.id.no_permission_btn_know).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "知道了", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
    }
}
