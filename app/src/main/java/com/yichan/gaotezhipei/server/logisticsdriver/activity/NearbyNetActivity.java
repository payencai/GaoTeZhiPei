package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetOrderItem;
import com.yichan.gaotezhipei.server.logisticsdriver.view.NearbyNetAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/5.
 */

public class NearbyNetActivity extends BaseListActivity implements PermissionUtils.ApplyPermission {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    View mViewNodata;


    private NearbyNetAdapter mAdapter;

    private List<NetOrderItem> mList = new ArrayList<>();

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明


    private boolean isCanRefresh = false;


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



    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mViewNodata = findViewById(R.id.view_no_data);

        initTitleBar();
        requestPermissions();

    }

    private void requestPermissions() {
        PermissionUtils.setApplyPermission(this);
        PermissionUtils.needPermission(NearbyNetActivity.this, needPermissions, 100);
    }

    @Override
    public void doFailed() {
        finish();
    }

    @Override
    public void doSuccess() {
        initBaiduLocation();
        mLocationClient.start();
    }



    @OnClick({R.id.titlebar_btn_left,R.id.view_no_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.view_no_data:
                getDataList(1, true);
                break;
            default:
                break;
        }
    }

    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);
    }

    private void initTitleBar() {
        mTvTitle.setText("附近网点");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_nearby_net;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyNetAdapter(this, mList);
        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NetDetailActivity.startActivity(NearbyNetActivity.this, mList.get(position));
            }
        });
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.nearby_net_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        if(isCanRefresh) {
            getDataList(currentPage, true);
        } else {

        }
    }

    public void getDataList(int currentPage, final boolean isRefresh) {
        if(isRefresh) {
            mList.clear();
        }
        String url = AppConstants.BASE_URL + LogisticsDriverConstants.URL_GET_NET_ORDER_LIST;

        Map<String,String> params = new HashMap<>();
        params.put("longitude", String.valueOf(lng));
        params.put("latitude", String.valueOf(lat));


        GetRequest getRequest = new GetRequest(UrlUtil.formTotalUrl(url, params), null, null, null);

        RequestCall call = getRequest.build();

        call.doScene(new TokenSceneCallback<List<NetOrderItem>>(call) {

            @Override
            public Result<List<NetOrderItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), NetOrderItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                doRefreshFinish(0);
                doLoadMoreFinish(0);
                toggleNoDataView(true);
            }



            @Override
            protected void handleResponse(Result<List<NetOrderItem>>  response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    //如果获取到的数据不为空，则直接添加进当前数据
                    if(response.getData() != null) {
                        mList.addAll(response.getData());
                    }

                    //判断当前数据
                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("附近暂无网点信息");
                        toggleNoDataView(true);
                    }

                    //后续操作
                    if(isRefresh) {
                        doRefreshFinish(response.getData().size());
                    } else {
                        doLoadMoreFinish(response.getData().size());
                    }

                } else {
                    toggleNoDataView(true);
                    showToast(response.getMessage());
                    doLoadMoreFinish(0);
                    doRefreshFinish(0);
                }
            }


        });
    }

    private void toggleNoDataView(boolean isShow) {
        if(isShow) {
            mViewNodata.setVisibility(View.VISIBLE);
            mMultiLayout.setVisibility(View.GONE);
        } else {
            mViewNodata.setVisibility(View.GONE);
            mMultiLayout.setVisibility(View.VISIBLE);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            lat = location.getLatitude();    //获取纬度信息
            lng = location.getLongitude();    //获取经度信息

            isCanRefresh = true;
            doRefresh(1, 1);
        }
    }
}
