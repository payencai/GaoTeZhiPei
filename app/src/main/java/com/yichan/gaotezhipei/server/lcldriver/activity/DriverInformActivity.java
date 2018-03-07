package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.server.lcldriver.entity.DriverInform;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/19.
 */

public class DriverInformActivity extends BaseActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_plateNum)
    TextView tvPlateNum;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.tv_count)
    TextView tvCount;

    public static final int START_TYPE_LCL = 1;
    public static final int START_TYPE_LOGISTICS = 2;

    public static final String URL_LCL_DRIVER_INFORM = "/pdriver/info/get";
    public static final String URL_LOGISTICS_DRIVER_INFORM = "/wdriver/info/get";

    public int mStartType = START_TYPE_LCL;//1代表拼货司机，2代表物流司机

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartType = getIntent().getIntExtra("startType", START_TYPE_LCL);
        mTvTitle.setText("他的主页");

        initInform();
    }

    public void initInform() {
        String url = null;
        if(mStartType == START_TYPE_LCL) {
            url = AppConstants.BASE_URL + URL_LCL_DRIVER_INFORM;
        } else if(mStartType == START_TYPE_LOGISTICS) {
            url = AppConstants.BASE_URL + URL_LOGISTICS_DRIVER_INFORM;
        }
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<DriverInform>(call) {

            @Override
            public Result<DriverInform> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(DriverInform.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<DriverInform> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    setInformToView(response.getData());
                } else {
                    showToast("获取司机信息失败，请重新再试。");
                }
            }


        });
    }

    public void setInformToView(DriverInform driverInform) {
        CommonImageLoader.displayImage(driverInform.getPortrait(), civAvatar, CommonImageLoader.NO_CACHE_OPTIONS);
        tvName.setText(driverInform.getName());
        tvPhone.setText(driverInform.getTelnum());
        tvPlateNum.setText(driverInform.getPlateNum());
        tvType.setText(driverInform.getType());
        tvVolume.setText(driverInform.getSize());
        tvCount.setText((driverInform.getCount() == null ? "0单" : driverInform.getCount() + "单"));
    }

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, DriverInformActivity.class);
        intent.putExtra("startType", type);
        context.startActivity(intent);
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_driver_information;
    }
}