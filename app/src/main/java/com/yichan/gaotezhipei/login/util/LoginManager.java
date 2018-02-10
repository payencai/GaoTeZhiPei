package com.yichan.gaotezhipei.login.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.login.activity.DemandLoginActivity;
import com.yichan.gaotezhipei.login.activity.ServerLoginActivity;
import com.yichan.gaotezhipei.login.entity.UserEntity;

/**
 * Created by ckerv on 2018/2/4.
 */

public class LoginManager {

    public static void saveUserEntity(Context context, UserEntity userEntity) {
        UserManager.getInstance(context).setAccount(userEntity.getAccount());
        UserManager.getInstance(context).setLogin(true);
        UserManager.getInstance(context).setId(userEntity.getId());
        UserManager.getInstance(context).setType(userEntity.getType());
        UserManager.getInstance(context).setToken(userEntity.getToken());
    }

    public static void clearUserInfo(Context context) {
        UserManager.getInstance(context).setAccount(null);
        UserManager.getInstance(context).setPassword(null);
        UserManager.getInstance(context).setLogin(false);
        UserManager.getInstance(context).setId(null);
        UserManager.getInstance(context).setType(0);
        UserManager.getInstance(context).setToken(null);
    }

    public static void logout(final AppCompatActivity activity, final boolean skipToDemand) {
        DialogHelper.showConfirmDailog(activity.getSupportFragmentManager(), "您确认退出吗?", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                if(result == -1) {
                    clearUserInfo(activity);
                    if(!skipToDemand) {
                        activity.startActivity(new Intent(activity, ServerLoginActivity.class));
                    } else {
                        activity.startActivity(new Intent(activity, DemandLoginActivity.class));
                    }
                    activity.finish();
                }
            }
        });
    }
}
