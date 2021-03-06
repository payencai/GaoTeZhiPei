package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.DataCleanUtil;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.login.activity.DemandLoginActivity;
import com.yichan.gaotezhipei.login.event.LoginEvent;
import com.yichan.gaotezhipei.login.util.LoginManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/13.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.setting_tv_cache_size)
    TextView mTvCacheSize;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initCacheSize();
    }

    private void initCacheSize() {
        Long size =
                // DataCleanUtil.getFileSizeFromDirectory(file)+
                null;
        try {
            size = DataCleanUtil.getFileSizeFromDirectory(getApplicationContext()
                    .getExternalCacheDir())
                    + DataCleanUtil
                    .getFileSizeFromDirectory(getApplicationContext()
                            .getCacheDir())
                    + DataCleanUtil
                    .getFileSizeFromDirectory(getApplicationContext()
                            .getFilesDir())
                    + DataCleanUtil.getFileSizeFromDirectory(CommonImageLoader.getDiskCache().getDirectory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(size == null) size = 0L;
        mTvCacheSize.setText(DataCleanUtil.getFormatSize(size));
    }

    private void initTitleBar() {
        mTvTitle.setText("设置");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.setting_rl_message_setting,R.id.setting_rl_clear_cache,R.id.setting_rl_about_us,R.id.setting_rl_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.setting_rl_about_us:
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
                break;
            case R.id.setting_rl_message_setting:
                startActivity(new Intent(SettingActivity.this, MessageSettingActivity.class));
                break;
            case R.id.setting_rl_logout:
                logout();
                break;
            case R.id.setting_rl_clear_cache:
                clearDataCache();
                break;
            default:
                break;
        }
    }

    private void clearDataCache() {
        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确定清除缓存吗？", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                if (result == -1) {
                    DataCleanUtil.cleanExternalCache(getApplicationContext());
                    DataCleanUtil.cleanInternalCache(getApplicationContext());
                    DataCleanUtil.cleanFiles(getApplicationContext());
                    CommonImageLoader.cleanDoubleCache();
                    initCacheSize();
                }
            }
        });
    }

    public void logout() {
        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确定退出登录吗？", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                if(result == -1) {
                    LoginManager.clearUserInfo(SettingActivity.this);
                    EventBus.getInstance().post(new LoginEvent());
                    startActivity(new Intent(SettingActivity.this, DemandLoginActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }
}
