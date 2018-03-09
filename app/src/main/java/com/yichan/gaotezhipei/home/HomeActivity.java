package com.yichan.gaotezhipei.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baisoo.citypicker.ChooseCityActivity;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.activity.CommonMultiTabActivity;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.lcl.fragment.LCLFragment;
import com.yichan.gaotezhipei.logistics.fragment.LogisticsFragment;
import com.yichan.gaotezhipei.mine.fragment.MineFragment;
import com.yichan.gaotezhipei.servicecenter.fragment.ServiceCenterFragment;

import java.util.ArrayList;
import java.util.List;



public class HomeActivity extends CommonMultiTabActivity implements PermissionUtils.ApplyPermission {

    private ViewGroup mVgTitle;
    private ImageView mIvRight;
    private TextView mTvTitle;
    private LinearLayout mLlLoc;
    private TextView mTvLoc;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明

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
        initTitleBar();
        initViewPagerListener();
        requestPermissions();
    }

    private void requestPermissions() {
        PermissionUtils.setApplyPermission(this);
        PermissionUtils.needPermission(HomeActivity.this, needPermissions, 100);
    }

    private void initTitleBar() {
        mVgTitle = (ViewGroup) getTitleBar().findViewById(R.id.home_title_bar);
        mIvRight = (ImageView) findViewById(R.id.titlebar_imgbtn_right);
        mTvTitle = (TextView) findViewById(R.id.titlebar_tv_title);
        mIvRight.setVisibility(View.VISIBLE);
        mLlLoc = (LinearLayout) findViewById(R.id.titlebar_ll_loc);
        mTvLoc = (TextView) findViewById(R.id.titlebar_tv_right);
        mIvRight.setImageResource(R.drawable.customservice);
        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("客服功能开发中，敬请期待。");
            }
        });
        mLlLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ChooseCityActivity.class));
            }
        });
    }

    @Override
    protected String[] getTabNames() {
        return HomeConstants.TAB_NAMES;
    }

    @Override
    protected int[] getTabSelectedIcons() {
        return HomeConstants.TAB_SELECTED_ICONS;
    }

    @Override
    protected int[] getTabUnSelectedIcons() {
        return HomeConstants.TAB_UNSELECTED_ICONS;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.home_title_bar;
    }


    private void initViewPagerListener() {
       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
                //setTitleText(getTabNames()[position]);
               if(position == 3) {
                   mVgTitle.setVisibility(View.GONE);
               } else {
                   mVgTitle.setVisibility(View.VISIBLE);
               }
               switch (position) {
                   case 0:
                       mTvTitle.setText("弥勒电子商务公共服务中心");
                       mVgTitle.setVisibility(View.VISIBLE);
                       mIvRight.setVisibility(View.VISIBLE);
                       mLlLoc.setVisibility(View.GONE);
                       break;
                   case 1:
                       mTvTitle.setText("我要拼货");
                       mVgTitle.setVisibility(View.VISIBLE);
                       mIvRight.setVisibility(View.GONE);
                       mLlLoc.setVisibility(View.VISIBLE);
                       break;
                   case 2:
                       mTvTitle.setText("我要寄件");
                       mVgTitle.setVisibility(View.VISIBLE);
                       mIvRight.setVisibility(View.GONE);
                       mLlLoc.setVisibility(View.VISIBLE);
                       break;
                   case 3:
                       mVgTitle.setVisibility(View.GONE);
                       break;
                   default:
                       break;
               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    @Override
    protected int getViewPagerId() {
        return R.id.home_vp_pager;
    }

    @Override
    protected int getCommonTabLayoutId() {
        return R.id.home_bottom_tab_layout;
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ServiceCenterFragment());
        fragments.add(new LCLFragment());
        fragments.add(new LogisticsFragment());
        fragments.add(new MineFragment());
        return fragments;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void doFailed() {
        showToast("应用缺少相应权限，请检查。");
        finish();
    }

    @Override
    public void doSuccess() {
        initBaiduLocation();
        mLocationClient.start();
    }

    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(HomeActivity.this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            lat = location.getLatitude();    //获取纬度信息
            lng = location.getLongitude();    //获取经度信息

            String cityTotal = location.getCity();
            int index = cityTotal.indexOf("市");
            if(index != -1) {
                UserManager.getInstance(HomeActivity.this).setLocatedCity(cityTotal.substring(0, index));
                UserManager.getInstance(HomeActivity.this).setCity(cityTotal.substring(0, index));
                mTvLoc.setText(cityTotal.substring(0, index));
            } else {
                UserManager.getInstance(HomeActivity.this).setLocatedCity(location.getCity());
                UserManager.getInstance(HomeActivity.this).setCity(location.getCity());
                mTvLoc.setText(location.getCity());
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvLoc.setText(UserManager.getInstance(HomeActivity.this).getCity());
    }
}
