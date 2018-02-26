package com.yichan.gaotezhipei.policyadvice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MultiRecyclerView;
import com.changelcai.mothership.view.recycler.MultiRecyclerViewLayout;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.policyadvice.constant.PolicyConstants;
import com.yichan.gaotezhipei.policyadvice.entity.PolicyArticlePageList;
import com.yichan.gaotezhipei.policyadvice.entity.PolicyBannerItem;
import com.yichan.gaotezhipei.policyadvice.view.PolicyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

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

    private List<PolicyArticlePageList.BeanListBean> mItems = new ArrayList<>();

    private List<PolicyBannerItem> mBannerImages = new ArrayList<>();

    private PolicyAdviceAdapter mAdapter;

    private int mPageSize = 8;

    private int mCurrentPage = 1;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        requestBannerImages();
        initRecyclerView();
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
        mVpBanner.setDisplayDots(true);
        mVpBanner.setIndicatoeResId(R.drawable.selector_indicator);
        LayoutInflater inflate = LayoutInflater.from(PolicyAdviceActivity.this);
        for (int i = 0; i < mBannerImages.size(); i++) {
            View view = inflate.inflate(R.layout.service_center_asvp_layout,
                    null);
            ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
            CommonImageLoader.displayImage(mBannerImages.get(i).getImage(), imageView, CommonImageLoader.DOUBLE_CACHE_OPTIONS);
            mVpBanner.addViewToViewPager(imageView);
        }

        mVpBanner.getAutoScrollViewPager().setInterval(3000);
        mVpBanner.getAutoScrollViewPager().setOffscreenPageLimit(3);
        mVpBanner.getAutoScrollViewPager().startAutoScroll();
    }

    private void requestBannerImages() {
        mBannerImages.clear();
        RequestCall requestCall = new PostFormBuilder().url(AppConstants.BASE_URL + PolicyConstants.URL_GET_BANNER_IMGS).build();
        requestCall.doScene(new TokenSceneCallback<List<PolicyBannerItem>>(requestCall) {

            @Override
            public Result<List<PolicyBannerItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), PolicyBannerItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<List<PolicyBannerItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mBannerImages.addAll(response.getData());
                    }

                    if(mBannerImages.size() != 0) {
                        initBannerViewPager();
                    } else {
                        showToast("暂无轮播图数据");
                    }
                } else {
                    showToast(response.getMessage());
                }
            }


        });
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

    @Override
    public void preRefresh() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        getArticleDataList(mCurrentPage, true);
    }

    private void getArticleDataList(int currentPage, final boolean isRefresh) {
        if(isRefresh) {
            mItems.clear();
        }

        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + PolicyConstants.URL_GET_ARTICLES)
                .addParams("page", String.valueOf(currentPage)).build();
        call.doScene(new TokenSceneCallback<PolicyArticlePageList>(call) {

            @Override
            public Result<PolicyArticlePageList> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(PolicyArticlePageList.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<PolicyArticlePageList> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null && response.getData().getBeanList() != null) {
                        mItems.addAll(response.getData().getBeanList());
                    }

                    if(mItems.size() != 0) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                    }
                    if(isRefresh) {
                        if(response.getData().getBeanList().size() < mPageSize) {
                            mRvLayout.onRefreshFinish(false);
                            mRvLayout.setLoadMoreEnable(false);
                        } else {
                            mRvLayout.onRefreshFinish(true);
                        }
                    } else {
                        if(response.getData().getBeanList().size() < mPageSize) {
                            mRvLayout.onLoadMoreFinish(false);
                            mRvLayout.setLoadMoreEnable(false);
                        } else {
                            mRvLayout.onLoadMoreFinish(true);
                        }
                    }
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        getArticleDataList(mCurrentPage, false);
    }
}
