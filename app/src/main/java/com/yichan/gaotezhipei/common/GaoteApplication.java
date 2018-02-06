package com.yichan.gaotezhipei.common;

import android.app.Application;
import android.content.Context;

import com.changelcai.mothership.network.core.OkHttpCore;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.base.net.CookieJarHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by ckerv on 2018/1/22.
 */

public class GaoteApplication extends Application {

    public static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        initOkHttpCore();
        initUniversialImageLoader();
    }

    private void initUniversialImageLoader() {
        CommonImageLoader.init(getApplicationContext());
        CommonImageLoader.debug(false);
    }

    private void initOkHttpCore() {
        //System.out.println(getExternalCacheDir().getPath());
        List<Interceptor> applicationInterceptors = new ArrayList<>();
        applicationInterceptors.add(new TokenInterceptor(this));
        File file = getExternalCacheDir();
        String cachePath;
        if (file != null) {
            cachePath = file.getPath();
        } else {
            cachePath = "";
        }
        OkHttpCore.Builder builder = OkHttpCore.getConfigBuilder()
                .cookieJar(CookieJarHelper.createMemoryCookieJar())//开启Cookie内存缓存
                .interceptors(applicationInterceptors)//添加Application拦截器
                //.gzip(true)//Gzip压缩 不能开启，不然请求失败
                .readTimeout(20000)//读取流时间超时
                .writeTimeout(15000)//输出流时间
                .connectTimeout(10000)//连接时间超时
                .cachedDir(cachePath)//缓存路径
                .maxCachedSize(1024 * 1024 * 10)//最大缓存大小
                ;
       builder.build();
    }
}
