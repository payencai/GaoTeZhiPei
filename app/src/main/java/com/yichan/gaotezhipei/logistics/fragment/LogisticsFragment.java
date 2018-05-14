package com.yichan.gaotezhipei.logistics.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.DrawableUtil;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.common.view.CategoryAdapter;
import com.yichan.gaotezhipei.common.view.DividerItemDecoration;
import com.yichan.gaotezhipei.common.view.GridLayoutScrollManager;
import com.yichan.gaotezhipei.logistics.activity.ExpressLogisticsActivity;
import com.yichan.gaotezhipei.logistics.activity.ExpressSearchActivity;
import com.yichan.gaotezhipei.logistics.activity.ForbiddenObjActivity;
import com.yichan.gaotezhipei.logistics.view.LogisticsCatAdapter;
import com.yichan.gaotezhipei.navi.sdkdemo.BNDemoGuideActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetOrderItem;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/7.
 */

public class LogisticsFragment extends BaseFragment implements PermissionUtils.ApplyPermission{

//    @BindView(R.id.logistics_iv_map)
//    ImageView mIvMap;
    @BindView(R.id.logistics_rv_category)
    RecyclerView mRvCategories;

    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    public LocationClient mLocationClient = null;
    private LogisticsFragment.MyLocationListener myListener = new LogisticsFragment.MyLocationListener();

    private List<NetOrderItem> mList = new ArrayList<>();

    private List<LatLng> mLatLngs = new ArrayList<>();

    private LinearLayout mLlDetail;

    private CircleImageView mCivPic;

    private TextView mTvName;

    private TextView mTvAddr;

    private TextView mTvDistance;

    private LinearLayout mLlNavigate;

    private LinearLayout mLlCall;

    private NetOrderItem mCurrentOrderItem;

    private double mLocatedLng;

    private double mLocatedLat;

    private String mSDCardPath = null;

    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";


    private final static int authBaseRequestCode = 1;
    private final static int authComRequestCode = 2;

    private boolean hasInitSuccess = false;
    private boolean hasRequestComAuth = false;

    private String authinfo = null;

    private DialogFragment mProgressDialog;

    private List<CommonCategoryItem> mCommonCategoryItems;
    private CategoryAdapter mCategoryAdapter;

    @Override
    public void doFailed() {

    }

    @Override
    public void doSuccess() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        mLocationClient.start();
    }

    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLocatedLat = bdLocation.getLatitude();
            mLocatedLng = bdLocation.getLongitude();
            initMapView(bdLocation);
        }
    }

    private void initMapView(BDLocation bdLocation) {
        // 开启定位图层

        mBaiduMap.setMyLocationEnabled(true);


        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

//
//        百度地图对应缩放级别
//        int[] zoomLevel = { 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6,5, 4, 3 };
//
//        对应级别单位
//        String[] zoomLevelStr = { “10”, “20”, “50”, “100”, “200”, “500”, “1000”,
//          “2000”, “5000”, “10000”, “20000”, “25000”, “50000”, “100000”, “200000”, “500000”, “1000000”, “2000000” }; // 单位/m
//
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(12));


        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);

        mBaiduMap.setMyLocationConfiguration(config);

        getNetWorkDataList(bdLocation.getLongitude(), bdLocation.getLatitude());

        if(initDirs()) {
            initNavi();
        }

        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
    }

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

    private void requestPermissions() {
        PermissionUtils.setApplyPermission(this);
        PermissionUtils.needPermission(getActivity(), needPermissions, 100);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initViews();
        requestPermissions();
        initRecyclerViewCategories();

    }

    private void initViews() {
        mMapView = (MapView) findViewById(R.id.mapview);
        mLlDetail = (LinearLayout) findViewById(R.id.ll_detail);
        mCivPic = (CircleImageView) findViewById(R.id.civ_pic);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvAddr = (TextView) findViewById(R.id.tv_address);
        mTvDistance = (TextView) findViewById(R.id.tv_distance);
        mLlNavigate = (LinearLayout) findViewById(R.id.ll_navigate);
        mLlCall = (LinearLayout) findViewById(R.id.ll_call);
        mLlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentOrderItem != null) {
                    diallPhone(mCurrentOrderItem.getTelephone());
                }
            }
        });
        mLlNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentOrderItem != null) {
                    mProgressDialog = DialogHelper.showProgress(getActivity().getSupportFragmentManager(), "发起导航中...");
                    routeplanToNavi(mLocatedLng, mLocatedLat);
                }
            }
        });
        mBaiduMap = mMapView.getMap();

    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        mMapView.onResume();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        mMapView.onPause();
    }



    private void initRecyclerViewCategories() {
        initCategoryItems();
        mCategoryAdapter = new LogisticsCatAdapter(getActivity(), mCommonCategoryItems);

        GridLayoutScrollManager layoutManager = new GridLayoutScrollManager(getActivity(), 3);
        layoutManager.setCanScroll(false);//设置不能滚动
        mRvCategories.setLayoutManager(layoutManager);

        mRvCategories.addItemDecoration(new DividerItemDecoration(getActivity()));

        mRvCategories.setAdapter(mCategoryAdapter);

        mCategoryAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        getActivity().startActivity(new Intent(getActivity(), ExpressSearchActivity.class));
                        break;
                    case 1:
                        getActivity().startActivity(new Intent(getActivity(), ExpressLogisticsActivity.class));
                        break;
                    case 2:
                        getActivity().startActivity(new Intent(getActivity(), ForbiddenObjActivity.class));
                       break;
//                    case 3:
//                        getActivity().startActivity(new Intent(getActivity(), LogisticOrderActivity.class));
//                        break;
//                    case 4:
//                        getActivity().startActivity(new Intent(getActivity(), ForbiddenObjActivity.class));
//                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void getNetWorkDataList(double lng, double lat) {
        mList.clear();
        String url = AppConstants.BASE_URL + LogisticsDriverConstants.URL_GET_NET_ORDER_LIST;

        Map<String, String> params = new HashMap<>();
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
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }


            @Override
            protected void handleResponse(Result<List<NetOrderItem>> response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {

                    //如果获取到的数据不为空，则直接添加进当前数据
                    if (response.getData() != null) {
                        mList.addAll(response.getData());
                    }

                    //判断当前数据
                    if (mList.size() != 0) {
                        initNetWorkViewToMap();
                    } else {
                        Toast.makeText(getActivity(), "附近暂无网点信息", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void initNetWorkViewToMap() {
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //设置坐标点

        BitmapDescriptor bitmapMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.latticepoint);

        for (int i = 0; i < mList.size(); i++) {
            LatLng latLng = new LatLng(Double.valueOf(mList.get(i).getLatitude()), Double.valueOf(mList.get(i).getLongitude()));
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmapMarker);
            options.add(option);
            mLatLngs.add(latLng);
        }


        //在地图上批量添加
        mBaiduMap.addOverlays(options);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = mLatLngs.indexOf(marker.getPosition());
                if (index != -1) {
                    if(mCurrentOrderItem == mList.get(index) && mLlDetail.getVisibility() == View.VISIBLE) {
                        hideBottomView();
                    } else {
                        mCurrentOrderItem = mList.get(index);
                        showBottomView();
                    }
                }
                return false;
            }
        });
    }

    private void initCategoryItems() {
        mCommonCategoryItems = new ArrayList<>();
        String[] catsTitles = getResources().getStringArray(R.array.logistics_cat_title);
        String[] catsSubTitles = getResources().getStringArray(R.array.logistics_cat_subtitle);
        int[] iconResIds = DrawableUtil.getDrawableResIds(getActivity(), R.array.logistics_cat_icon);
        for (int i = 0; i < catsTitles.length; i++) {
            CommonCategoryItem item = new CommonCategoryItem(iconResIds[i], catsTitles[i], catsSubTitles[i]);
            mCommonCategoryItems.add(item);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_logistics;
    }


    private void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    private void routeplanToNavi(double sLng, double sLat) {
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        sNode = new BNRoutePlanNode(sLng,
                sLat, "起点", null, BNRoutePlanNode.CoordinateType.BD09LL);
        eNode = new BNRoutePlanNode(Double.valueOf(mCurrentOrderItem.getLongitude()),
                Double.valueOf(mCurrentOrderItem.getLatitude()), "终点", null, BNRoutePlanNode.CoordinateType.BD09LL);
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(getActivity(), list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Intent intent = new Intent(getActivity(), BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub

        }
    }

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;



        BaiduNaviManager.getInstance().init(getActivity(), mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
//                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        Toast.makeText(ExpressLogisticsActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
//                Toast.makeText(ExpressLogisticsActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
//                Toast.makeText(ExpressLogisticsActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(getActivity(), "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, null, null);

    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9354030");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    private void showBottomView() {
        mLlDetail.setVisibility(View.VISIBLE);
        CommonImageLoader.displayImage(mCurrentOrderItem.getNetworkPic(), mCivPic, CommonImageLoader.NO_CACHE_OPTIONS);
        mTvName.setText(mCurrentOrderItem.getName());
        mTvAddr.setText(mCurrentOrderItem.getAdress());
        mTvDistance.setText(mCurrentOrderItem.getDistance() + "km");

    }

    private void hideBottomView() {
        mLlDetail.setVisibility(View.GONE);
    }



}
