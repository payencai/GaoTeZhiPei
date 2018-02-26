package com.yichan.gaotezhipei.lcl.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.activity.EditContactInformActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.ContactInformEntity;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.TimeUtil;
import com.yichan.gaotezhipei.common.view.CommonFragmentPagerAdapter;
import com.yichan.gaotezhipei.common.view.CommonTabBean;
import com.yichan.gaotezhipei.lcl.constant.LCLConstants;
import com.yichan.gaotezhipei.lcl.event.SwitchToNextEvent;
import com.yichan.gaotezhipei.lcl.event.SwitchToPreEvent;
import com.yichan.gaotezhipei.mine.activity.AddressMangeActivity;
import com.yichan.gaotezhipei.mine.entity.AddressItem;
import com.yichan.gaotezhipei.mine.event.ChooseAddressEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/8.
 */

public class LCLFragment extends BaseFragment {


    @BindView(R.id.lcl_detail_vp)
    ViewPager mViewPager;

    @BindView(R.id.lcl_detail_tablayout)
    CommonTabLayout mCommonTabLayout;


    @BindView(R.id.lcl_detail_tv_mail_name_phone)
    TextView mTvMailNamePhone;

    @BindView(R.id.lcl_detail_tv_mail_address)
    TextView mTvMailAddress;

    @BindView(R.id.lcl_detail_tv_pick_name_phone)
    TextView mTvPickNamePhone;

    @BindView(R.id.lcl_detail_tv_pick_address)
    TextView mTvPickAddress;

    @BindView(R.id.lcl_detail_et_cargo_weight)
    EditText mEtCargoWeight;

    @BindView(R.id.lcl_detail_et_cargo_volume)
    EditText mEtCargoVolume;

    @BindView(R.id.lcl_detail_et_cargo_count)
    EditText mEtCargoCount;

    @BindView(R.id.lcl_detail_et_cargo_name)
    EditText mEtCargoName;

    @BindView(R.id.lcl_detail_et_get_cargo_address)
    EditText mEtGetCargoAddr;

    @BindView(R.id.lcl_detail_tv_get_cargo_time)
    TextView mTvGetCargoTime;

    @BindView(R.id.lcl_detail_et_extra_msg)
    EditText mEtExtraMsg;

    @BindView(R.id.lcl_detail_cb_agree)
    CheckBox mCbAgree;

    private List<Fragment> mFragments;

    private ArrayList<CustomTabEntity> mTabList;


    private AddressItem addressItem = null;
    private ContactInformEntity contactInformEntity = null;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lcl;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);

        initFragmentList();
        initTabList();
        initViewPager();
        initTabLayout();
    }

    private void initTabList() {
        mTabList = new ArrayList<>();
        String[] tabNames = getTabNames();
        for (int i = 0; i < tabNames.length; i++) {
            mTabList.add(new CommonTabBean(tabNames[i]));
        }
    }

    private void initViewPager() {
        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(getFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(mTabList.size());
    }

    private void initTabLayout() {
        mCommonTabLayout.setTabData(mTabList);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivePre(SwitchToPreEvent switchToPreEvent) {
        int pre = mViewPager.getCurrentItem() - 1;
        if(pre >= 0) {
            mViewPager.setCurrentItem(pre);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveNext(SwitchToNextEvent switchToNextEvent) {
        int next = mViewPager.getCurrentItem() + 1;
        if(next <= mViewPager.getAdapter().getCount() - 1) {
            mViewPager.setCurrentItem(next);
        }
    }

    protected String[] getTabNames() {
        return new String[]{"小面包车", "中面包车", "小货车", "中货车"};
    }

    protected void initFragmentList() {
        mFragments = new ArrayList<>();
        mFragments.add(new LCLMiniBusFragment());
        mFragments.add(new LCLMiddleBusFragment());
        mFragments.add(new LCLSmallTrackFragment());
        mFragments.add(new LCLMiddleTrackFragment());
    }


    @OnClick({R.id.lcl_detail_tv_get_cargo_time,R.id.lcl_detail_ll_mail_infrom,R.id.lcl_detail_ll_pick_inform,R.id.lcl_detail_btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lcl_detail_tv_get_cargo_time:
                chooseTime();
                break;
            case R.id.lcl_detail_ll_mail_infrom:
                AddressMangeActivity.startActivity(getActivity(), 1);
                break;
            case R.id.lcl_detail_ll_pick_inform:
                EditContactInformActivity.startActivity(getActivity(), 1);
                break;
            case R.id.lcl_detail_btn_apply:
                tryToPostForms();
                break;

            default:
                break;
        }
    }

    private void tryToPostForms() {
        if(!checkForms()) {
            return;
        }

        postForms();
    }

    private void postForms() {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLConstants.URL_ADD_LCL_ORDER)
                .addParams("receiverAddressId", addressItem.getId())
                .addParams("consignee", contactInformEntity.name)
                .addParams("consigneeTelephone", contactInformEntity.phone)
                .addParams("consigneeArea", contactInformEntity.district)
                .addParams("consigneeProvince", contactInformEntity.province)
                .addParams("consigneeCity", contactInformEntity.city)
                .addParams("consigneeAddress", contactInformEntity.detail)
                .addParams("num", mEtCargoCount.getText().toString())
                .addParams("weight", mEtCargoWeight.getText().toString())
                .addParams("volume", mEtCargoVolume.getText().toString())
                .addParams("articleName", mEtCargoName.getText().toString())
                .addParams("pickupAddress", mEtGetCargoAddr.getText().toString())
               .addParams("anticipantCar", ((LCLBaseDetailFragment)mFragments.get(mViewPager.getCurrentItem())).getTypeStr())
                .addParams("anticipantTime", mTvGetCargoTime.getText().toString())
                .addParams("lng1", addressItem.getLng1())
                .addParams("lat1", addressItem.getLat1())
                .addParams("lng2", String.valueOf(contactInformEntity.lng))
                .addParams("lat2", String.valueOf(contactInformEntity.lat)).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("发布需求成功");
                    clearAllInform();
                } else {
                    showToast(response.getMessage());
                }
            }

        });

    }

    private void clearAllInform() {
        mEtCargoWeight.setText("");
        mEtCargoVolume.setText("");
        mEtCargoCount.setText("");
        mEtCargoName.setText("");
        mEtGetCargoAddr.setText("");
        mTvGetCargoTime.setText("请选择时间");
        mTvGetCargoTime.setTextColor(Color.parseColor("#999999"));
        mTvMailNamePhone.setText("寄件人    手机号");
        mTvMailAddress.setText("寄件地址");
        mTvPickNamePhone.setText("收件人    手机号");
        mTvPickAddress.setText("收件地址");
        mEtExtraMsg.setText("");
        addressItem = null;
        contactInformEntity = null;
    }

    private boolean checkForms() {
        if(TextUtils.isEmpty(mEtCargoWeight.getText().toString())
                || TextUtils.isEmpty(mEtCargoVolume.getText().toString())
                || TextUtils.isEmpty(mEtCargoCount.getText().toString())
                || TextUtils.isEmpty(mEtCargoName.getText().toString())
                || TextUtils.isEmpty(mEtGetCargoAddr.getText().toString())
                || "请选择时间".equals(mTvGetCargoTime.getText().toString())
                || addressItem == null
                || contactInformEntity == null) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if(!mCbAgree.isChecked()) {
            showToast("您尚未同意《服务协议》");
            return false;
        } else {
            return true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveChooseAddressEvent(ChooseAddressEvent chooseAddressEvent) {
        if(chooseAddressEvent.addressItem != null) {
            addressItem = chooseAddressEvent.addressItem;
            mTvMailNamePhone.setText(chooseAddressEvent.addressItem.getName() + "    " + chooseAddressEvent.addressItem.getTelephone());
            mTvMailAddress.setText(chooseAddressEvent.addressItem.getAddress());
        } else if(chooseAddressEvent.contactInformEntity != null) {
            contactInformEntity = chooseAddressEvent.contactInformEntity;
            mTvPickAddress.setText(chooseAddressEvent.contactInformEntity.detail);
            mTvPickNamePhone.setText(chooseAddressEvent.contactInformEntity.name + "    " + chooseAddressEvent.contactInformEntity.phone);
        }
    }



    private void chooseTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013,0,1);
        endDate.set(2020,11,31);

        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvGetCargoTime.setTextColor(Color.parseColor("#333333"));;
                mTvGetCargoTime.setText(TimeUtil.getStringByFormat(date, "yyyy-MM-dd HH:mm"));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }



}
