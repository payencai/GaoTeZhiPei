package com.yichan.gaotezhipei.policyadvice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.view.recycler.MultiRecyclerView;
import com.changelcai.mothership.view.recycler.MultiRecyclerViewLayout;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.policyadvice.PolicyAdviceItem;
import com.yichan.gaotezhipei.policyadvice.view.PolicyAdviceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/10.
 */

public class PolicyAdviceActivity extends BaseActivity implements MultiRecyclerViewLayout.MultiRecycleViewListener {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.pa_vp_banner)
    AutoScrollViewPagerWithIndicator mVpBanner;

//    @BindView(R.id.pa_rv_list)
//    MultiRecyclerView mRvList;

    @BindView(R.id.pa_rvl_list)
    MultiRecyclerViewLayout mRvLayout;

    private List<PolicyAdviceItem> mItems = new ArrayList<>();

    private PolicyAdviceAdapter mAdapter;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initBannerViewPager();
        initRecyclerView();
//        initDataList();
    }

    private void initDataList() {
        //TODO 假数据
        for(int i = 0; i < 10; i++) {
            mItems.add(new PolicyAdviceItem("一周企服IIDC:行业云未来：5-1年\n" + "保持双位数增长；万达云服务多\n" + "部门面临解散", null, "环球时报", "2小时前"));
        }
    }

    private void initRecyclerView() {

        mAdapter = new PolicyAdviceAdapter(PolicyAdviceActivity.this, mItems);
        MultiRecyclerView.Builder builder = new MultiRecyclerView.Builder(PolicyAdviceActivity.this)
                .adapter(mAdapter).autoLoadMoreEnable(false).layoutManager(new LinearLayoutManager(PolicyAdviceActivity.this));
        MultiRecyclerView recyclerView = builder.build();
        mRvLayout.initialize(recyclerView);
        mRvLayout.setListener(this);
        mRvLayout.setLoadMoreEnable(true);
        mRvLayout.beginPreRefresh();//开始预刷
    }


    private void initBannerViewPager() {
        LayoutInflater inflate = LayoutInflater.from(PolicyAdviceActivity.this);
        for (int i = 0; i < 3; i++) {
            View view = inflate.inflate(R.layout.service_center_asvp_layout,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
            imageView.setImageResource(R.drawable.pa_banner);
            mVpBanner.addViewToViewPager(imageView);
        }

        mVpBanner.getAutoScrollViewPager().setInterval(3000);
        mVpBanner.getAutoScrollViewPager().setOffscreenPageLimit(3);
        mVpBanner.getAutoScrollViewPager().startAutoScroll();
    }

    private void initTitleBar() {
        mTvTitle.setText("政策咨询");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_policy_advice;
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

//    @Override
//    public void onLoadMore() {
//        for(int i = 0; i < 10; i++) {
//            mItems.add(new PolicyAdviceItem("哈哈哈哈哈哈哈哈哈", null, "环球时报", "2小时前"));
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    @Override
    public void preRefresh() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        initDataList();
        mRvLayout.onRefreshFinish(true);
    }

    @Override
    public void onLoadMore() {
        for(int i = 0; i < 10; i++) {
            mItems.add(new PolicyAdviceItem("哈哈哈" + i, null, "环球时报", "2小时前"));
        }
        mAdapter.notifyDataSetChanged();
        mRvLayout.onLoadMoreFinish(true);
    }
}
