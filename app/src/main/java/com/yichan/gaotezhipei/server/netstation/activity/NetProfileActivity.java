package com.yichan.gaotezhipei.server.netstation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.activity.ProfileActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.BottomMenuDialog;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.location.activity.SimpleChooseAddrWebActivity;
import com.yichan.gaotezhipei.server.netstation.constant.NetStationConstants;
import com.yichan.gaotezhipei.server.netstation.entity.NetStationInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class NetProfileActivity extends ProfileActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.imgShop)
    ImageView imgShop;
    @BindView(R.id.tvShopName)
    TextView mTvShopName;
    @BindView(R.id.tvShopAddress)
    TextView mTvShopAddress;

    private NetStationInfo mNetStationInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_netstation_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initData2();
    }

    private void initTitleBar() {
        mTvTitle.setText("门店信息");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.imgShop,R.id.imgEnter,R.id.tvShopName,R.id.imgShopNameEnter,R.id.tvShopAddress,R.id.imgShopAddressEnter})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.imgShop:
            case R.id.imgEnter:
                alertPicPanel(1);
                break;
            case R.id.tvShopName:
            case R.id.imgShopNameEnter:
                //set page
                intent = new Intent();
                intent.putExtra("nick", mTvShopName.getText().toString());
                intent.setClass(NetProfileActivity.this, NetNameSetActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tvShopAddress:
            case R.id.imgShopAddressEnter:
                intent = new Intent(NetProfileActivity.this, SimpleChooseAddrWebActivity.class);
                startActivityForResult(intent, 2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String nickName = data.getExtras().getString("nick");
                Log.d("TAG", ">>>>>>nick:" + nickName);
                setShopName(nickName);
            } else if(requestCode == 2 && resultCode == RESULT_OK) {
                String lat = data.getStringExtra("lat");
                String lng = data.getStringExtra("lng");
                String addrName = data.getStringExtra("addr");
                String heading = data.getStringExtra("name");
                setShopAddress(addrName + heading, lng, lat);

            }
        }
    }

    @Override
    protected void buildMenu(BottomMenuDialog.Builder builder, int type) {
    }

    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
    }

    protected void initData2() {
        String url = AppConstants.BASE_URL + NetStationConstants.URL_GET_INFO;
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<NetStationInfo>(call) {
            @Override
            public Result<NetStationInfo> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(NetStationInfo.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mNetStationInfo = (NetStationInfo)response.getData();
                        Log.d("TAG", ">>>>>>GET:" + mNetStationInfo.getAdress());
                        //GetBitmapTools.getBitmap(ctx,demanderInfo.getPortraint(),imgShop,R.drawable.default_icon,R.drawable.default_icon);
                        CommonImageLoader.displayImage(mNetStationInfo.getPicUrl(),imgShop, CommonImageLoader.NO_CACHE_OPTIONS);
                        mTvShopName.setText(mNetStationInfo.getNetworkName());
                        mTvShopAddress.setText(mNetStationInfo.getAdress());
                    }
                } else {
                    showToast(response.getMessage());
                }
            }


        });

    }

    @Override
    protected void reloadProfile(Bitmap bm) {
        Log.d("TAG", ">>>>>>reloadProfile BEGIN");
        imgShop.setImageBitmap(bm);
        Log.d("TAG", ">>>>>>reloadProfile END");
    }

    @Override
    protected void picSelectUpload(String fileName) {

        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + NetStationConstants.URL_UPLOAD_IMAGE)
                .addFile("image", fileName, new File(fileName))
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("上传成功。");
                    Log.d("TAG", ">>>>>>GET:" + response.getData());
                    mNetStationInfo.setPicKey(response.getData().toString());
                    setProfile();
                } else {
                    showToast(response.getMessage());
                }
            }


        });

    }

    private void setProfile() {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + NetStationConstants.URL_UPLOAD_PORTRAINT)
                .addParams("portrait", mNetStationInfo.getPicKey())
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("门店图片已更新。");
                    initData2();
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void setShopAddress(final String address, final String longitude, final String latitude) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + NetStationConstants.URL_UPDATE_ADDRESS)
                .addParams("adress", address)
                .addParams("longitude", longitude)
                .addParams("latitude", latitude)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("店铺地址已更新。");
                    mTvShopAddress.setText(address);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void setShopName(final String name) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + NetStationConstants.URL_UPDATE_NAME)
                .addParams("name", name)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("店铺名称已更新。");
                    mTvShopName.setText(name);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }
}
