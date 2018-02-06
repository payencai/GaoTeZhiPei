package com.yichan.gaotezhipei.common;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/4.
 */

public class TokenInterceptor implements Interceptor {

    public Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //所有请求带上Token
        String token = UserManager.getInstance(context).getToken();
        if(token != null) {
            Request newRequest = chain.request().newBuilder().addHeader("token", token).build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(chain.request());
        }
    }
}
