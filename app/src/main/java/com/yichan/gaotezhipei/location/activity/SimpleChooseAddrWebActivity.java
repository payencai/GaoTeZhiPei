package com.yichan.gaotezhipei.location.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.entity.ContactInformEntity;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.location.view.WebViewMod;

/**
 * Created by ckerv on 2018/2/6.
 */

public class SimpleChooseAddrWebActivity extends AppCompatActivity implements PermissionUtils.ApplyPermission {


    private WebViewMod mWebView;
    private TextView mTvTitle;


    private String mUrl = "http://120.79.176.228/gaote-web/map/index.html";

    private String mPoiName;
    private String mPoiAddress;

    private double lat;
    private double lng;


    /**
     * 需要进行检测的权限数组
     */
    protected static String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private DialogFragment mProgressDialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestPermissions();
    }

    private void requestPermissions() {
        PermissionUtils.setApplyPermission(this);
        PermissionUtils.needPermission(SimpleChooseAddrWebActivity.this, needPermissions, 100);
    }

    @Override
    public void doFailed() {
        finish();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.titlebar_tv_title);
        mTvTitle.setText("选择地址");
        findViewById(R.id.titlebar_btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();//返回上一页面
                } else {
                    setResult(RESULT_CANCELED, new Intent());
                    finish();
                }
            }
        });
        mWebView = (WebViewMod) findViewById(R.id.choose_addr_web_wv);
        initWebView();
    }

    private void initWebView() {
        // 启用 JavaScript 支持
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 添加一个客户端到视图上
        // 让 WebView 自己处理所有的 URL 请求
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setWebViewClient(new MyWebViewClient());//这个是设置对webview的点击截取

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
        //启用数据库
        mWebView.getSettings().setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        mWebView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        mWebView.getSettings().setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
            }
        });


        // 如果仅仅只是加载页面，如下即可(不需要 WebViewClient 的对象)：
        mWebView.loadUrl(mUrl);
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //根据返回的url分割字符串，
//            http://www.test.com/?loc={"module":"locationPicker","latlng":{"lat":24.8778,"lng":102.83508},"poiaddress":"云南省昆明市呈贡区级行政中心综合服务楼","poiname":"昆明市级行政中心综合服务楼","cityname":"昆明市"}
            if (url.contains("locationPicker")) {
//                int index = url.indexOf("loc");
//                String jsonStr = url.substring(index + "loc=".length(), url.length());
//                WebLocResultEntity webLocResultEntity = new Gson().fromJson(Uri.decode(jsonStr), WebLocResultEntity.class);
//                lat = webLocResultEntity.getLatlng().getLat();
//                lng = webLocResultEntity.getLatlng().getLng();
//                mPoiName = webLocResultEntity.getPoiname();
//                mPoiAddress = webLocResultEntity.getPoiaddress();
//                reverseGeoParse(webLocResultEntity.getLatlng().getLat(), webLocResultEntity.getLatlng().getLng());

                String[] strings = url.split(",");
                String[] strings1 = strings[1].split(":");
                String lat = strings1[strings1.length-1];
                String[] strings2 = strings[2].split(":");
                String lng = strings2[strings2.length-1].substring(0,strings2[strings2.length-1].length()-1);
                String[] strings3 = strings[3].split(":");
                String addr = Uri.decode(strings3[1]);
                addr = addr.substring(1,addr.length()-1);
                String[] strings4 = strings[4].split(":");
//                String addrName = Uri.decode(strings4[1]);
                String addrName = strings4.length>=2 ? Uri.decode(strings4[1]) : Uri.decode(strings[4]);
                addrName = addrName.substring(1,addrName.length()-1);
                Intent intent = new Intent();
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("addr",addr);
                intent.putExtra("name",addrName);

                SimpleChooseAddrWebActivity.this.setResult(RESULT_OK,intent);
                SimpleChooseAddrWebActivity.this.finish();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
        }

    }

    private void reverseGeoParse(double lat, double lng) {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = DialogHelper.showProgress(getSupportFragmentManager(), "", false);
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if(mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(SimpleChooseAddrWebActivity.this, "没有检索到结果，请重新再试。", Toast.LENGTH_SHORT).show();
                }
                ReverseGeoCodeResult.AddressComponent addressComponent = reverseGeoCodeResult.getAddressDetail();
                if(mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                if (addressComponent != null) {
                    postBackDatas(addressComponent);
                    finish();
                } else {
                    Toast.makeText(SimpleChooseAddrWebActivity.this, "没有检索到结果，请重新再试。", Toast.LENGTH_SHORT).show();
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(lat, lng)));
    }

    private void postBackDatas(ReverseGeoCodeResult.AddressComponent addressComponent) {
        String province = addressComponent.province;
        String city = addressComponent.city;
        String district = addressComponent.district;
        String detailAddr = null;
        if (mPoiName.equals("我的位置")) {
            detailAddr = province + city + district + mPoiAddress;
        } else {
            detailAddr = mPoiAddress + mPoiName;
        }
        ContactInformEntity locationEntity = new ContactInformEntity();
        locationEntity.lat = lat;
        locationEntity.lng = lng;
        locationEntity.detail = detailAddr;
        locationEntity.province = province;
        locationEntity.city = city;
        locationEntity.district = district;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactEntity", locationEntity);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    /**
     * 改写物理按键——返回的逻辑
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void doSuccess() {
        setContentView(R.layout.activity_choose_addr_web);
        initView();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(SimpleChooseAddrWebActivity.this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
