package com.yichan.gaotezhipei.login.util;

import android.content.Context;

import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.login.entity.UserEntity;

/**
 * Created by ckerv on 2018/2/4.
 */

public class LoginManager {

    public static void saveUserEntity(Context context, UserEntity userEntity) {
        UserManager.getInstance(context).setAccount(userEntity.getAccount());
        UserManager.getInstance(context).setLogin(true);
        UserManager.getInstance(context).setType(userEntity.getType());
        UserManager.getInstance(context).setToken(userEntity.getToken());
    }

    public static void clearUserInfo(Context context) {
        UserManager.getInstance(context).setAccount(null);
        UserManager.getInstance(context).setPassword(null);
        UserManager.getInstance(context).setLogin(false);
        UserManager.getInstance(context).setType(0);
        UserManager.getInstance(context).setToken(null);
    }
}
