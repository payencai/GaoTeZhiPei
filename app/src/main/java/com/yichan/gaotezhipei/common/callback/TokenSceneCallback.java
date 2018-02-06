package com.yichan.gaotezhipei.common.callback;


import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.callback.UISceneCallback;
import com.google.gson.reflect.TypeToken;
import com.yichan.gaotezhipei.common.GaoteApplication;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.NetworkUtil;
import com.yichan.gaotezhipei.login.constant.LoginConstants;
import com.yichan.gaotezhipei.login.entity.UserEntity;
import com.yichan.gaotezhipei.login.util.LoginManager;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/4.
 */

public abstract class TokenSceneCallback<T> extends UISceneCallback<Result<T>> {

    private RequestCall call;


    public TokenSceneCallback(RequestCall call) {
        this.call = call;
    }

    @Override
    public Result<T> parseNetworkResponse(Response response) throws IOException {
        return GsonUtil.gsonToBean(response.body().string(), new TypeToken<Result<T>>(){}.getType());
    }

    @Override
    public void onResponse(Result<T> response) {
        // Token 过期，刷新
        if(response.getResultCode() == Result.TOKEN_EXPIRE_CODE) {
            refreshToken();
        } else {
            handleResponse(response);
        }
    }

    @Override
    public void onError(Call call, Exception e) {
        if(!NetworkUtil.isNetworkAvailable(GaoteApplication.mApplicationContext)) {
            handleError("当前设备无可用网络，请检查。", call, e);
        } else {
            handleError("发生未知错误", call, e);
        }
    }

    protected abstract void handleError(String errorMsg, Call call, Exception e);

    protected abstract void handleResponse(Result<T> response);

    private void refreshToken() {
        if(GaoteApplication.mApplicationContext != null) {
            String account = UserManager.getInstance(GaoteApplication.mApplicationContext).getAccount();
            final String password = UserManager.getInstance(GaoteApplication.mApplicationContext).getPassword();
            final String roleType = UserManager.getInstance(GaoteApplication.mApplicationContext).getRoleType();
            final RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LoginConstants.URL_LOGIN)
                    .addParams("telephone", account)
                    .addParams("type", roleType)
                    .addHeader("password", password)
                    .build();
            call.doScene(new UISceneCallback<Result<UserEntity>>() {
                @Override
                public Result<UserEntity> parseNetworkResponse(Response response) throws IOException {
                    return GsonUtil.gsonToBean(response.body().string(), new TypeToken<Result<UserEntity>>(){}.getType());
                }

                @Override
                public void onError(Call call, Exception e) {
                    if(!NetworkUtil.isNetworkAvailable(GaoteApplication.mApplicationContext)) {
                        handleError("当前设备无可用网络，请检查。", call, e);
                    } else {
                        handleError("登录信息已过期,请重新登录。", call, new Exception("登录信息已过期,请重新登录。"));
                    }
                }

                @Override
                public void onResponse(Result<UserEntity> response) {
                    //登录成功
                    if(response.getResultCode() == Result.SUCCESS_CODE) {
                        LoginManager.saveUserEntity(GaoteApplication.mApplicationContext, response.getData());
                        //先明文存储
                        UserManager.getInstance(GaoteApplication.mApplicationContext).setPassword(password);
                        UserManager.getInstance(GaoteApplication.mApplicationContext).setRoleType(roleType);
                        //反射处理原请求
                        handleCallFieldExecuted();
                        //重新执行原请求
                        TokenSceneCallback.this.call.doScene(TokenSceneCallback.this);
                    } else {
                        handleError("登录信息已过期,请重新登录。" , call.getCall(), new Exception("登录信息已过期,请重新登录。"));
                    }
                }

            });
        }
    }

    /**
     * 通过反射将原请求的 executed 变量置为false，以便重新执行
     */
    private void handleCallFieldExecuted() {
        try {
            Field field = call.getCall().getClass().getDeclaredField("executed");
            if(field != null) {
                field.setAccessible(true);
                field.setBoolean(call.getCall(), false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
