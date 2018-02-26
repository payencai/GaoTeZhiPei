package com.yichan.gaotezhipei.servicecenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.activity.AgriculturalServiceActivity;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.activity.CommonWebViewActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.DrawableUtil;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.common.view.CategoryAdapter;
import com.yichan.gaotezhipei.common.view.GridLayoutScrollManager;
import com.yichan.gaotezhipei.enterpriseservice.activity.EnterpriseActivity;
import com.yichan.gaotezhipei.finaceservice.activity.FinaceServiceActivity;
import com.yichan.gaotezhipei.gaoteintro.activity.GaoteIntroActivity;
import com.yichan.gaotezhipei.hatchservice.activity.HatchServiceActivity;
import com.yichan.gaotezhipei.policyadvice.activity.PolicyAdviceActivity;
import com.yichan.gaotezhipei.productservice.activity.ProductServiceActivity;
import com.yichan.gaotezhipei.servicecenter.constant.ServiceCenterConstants;
import com.yichan.gaotezhipei.servicecenter.entity.BannerImageItem;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;
import com.yichan.gaotezhipei.servicecenter.view.ServiceCenterCatAdapter;
import com.yichan.gaotezhipei.trainservice.activity.TrainServiceActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/6.
 */

public class ServiceCenterFragment extends BaseFragment {


    @BindView(R.id.service_center_vp)
    AutoScrollViewPagerWithIndicator mViewPagerIndicator;

    @BindView(R.id.service_center_rv_category)
    RecyclerView mRvCategories;

    private List<CommonCategoryItem> mCommonCategoryItems;

    private CategoryAdapter mCategoryAdapter;

    private List<BannerImageItem> mBannerImgs = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        requestBannerImages();
        initRecyclerViewCategories();
    }

    private void requestBannerImages() {
        mBannerImgs.clear();
        RequestCall requestCall = new PostFormBuilder().url(AppConstants.BASE_URL + ServiceCenterConstants.URL_GET_BANNER_IMAGES).build();
        requestCall.doScene(new TokenSceneCallback<List<BannerImageItem>>(requestCall) {

            @Override
            public Result<List<BannerImageItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), BannerImageItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<List<BannerImageItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mBannerImgs.addAll(response.getData());
                    }

                    if(mBannerImgs.size() != 0) {
                        initAutoScrollViewPager();
                    } else {
                        showToast("暂无首页轮播图数据");
                    }
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }




    private void initAutoScrollViewPager() {
        //TODO 网络数据
        mViewPagerIndicator.setDisplayDots(true);
        mViewPagerIndicator.setIndicatoeResId(R.drawable.selector_indicator);
        Context context = getContext();
        if (context != null) {
            LayoutInflater inflate = LayoutInflater.from(context);
            for (int i = 0; i < mBannerImgs.size(); i++) {
                View view = inflate.inflate(R.layout.service_center_asvp_layout,
                        null);
                ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
                CommonImageLoader.displayImage(mBannerImgs.get(i).getImage(), imageView, CommonImageLoader.DOUBLE_CACHE_OPTIONS);
                mViewPagerIndicator.addViewToViewPager(imageView);
//                if(i == 1) {
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(getActivity(), HatchServiceActivity.class));
//                        }
//                    });
//                }
            }
        }

        mViewPagerIndicator.getAutoScrollViewPager().setInterval(5000);
        mViewPagerIndicator.getAutoScrollViewPager().setOffscreenPageLimit(3);
        if (getUserVisibleHint()) {
            mViewPagerIndicator.getAutoScrollViewPager().startAutoScroll();
        }


//        mViewPagerIndicator.getAutoScrollViewPager().setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
//                .addAnimationType(PageTransformerConfig.ROTATION)//默认动画 default animation rotation  旋转  当然 也可以一次性添加两个  后续会增加更多动画
//                .setRotation(0)//旋转角度
//                .addAnimationType(PageTransformerConfig.NONE)//默认动画 透明度 暂时还有问题
//                .setViewType(PageTransformerConfig.LEFT)//view的类型
//                .setOnPageTransformerListener(new OnPageTransformerListener() {
//                    @Override
//                    public void onPageTransformerListener(View page, float position) {
//                        //你也可以在这里对 page 实行自定义动画 cust anim
//                    }
//                })
//                .setTranslationOffset(40)
//                .setScaleOffset(100)
//                .create());

    }

    private void initRecyclerViewCategories() {
        initCategoryItems();
        mCategoryAdapter = new ServiceCenterCatAdapter(getActivity(), mCommonCategoryItems);

        GridLayoutScrollManager layoutManager = new GridLayoutScrollManager(getActivity(), 3);
        layoutManager.setCanScroll(false);//设置不能滚动
        mRvCategories.setLayoutManager(layoutManager);

        //mRvCategories.addItemDecoration(new DividerItemDecoration(getActivity()));

        mRvCategories.setAdapter(mCategoryAdapter);

        mCategoryAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        getActivity().startActivity(new Intent(getActivity(), EnterpriseActivity.class));
                        break;
                    case 1:
                        getActivity().startActivity(new Intent(getActivity(), FinaceServiceActivity.class));
                        break;
                    case 2:
                        getActivity().startActivity(new Intent(getActivity(), PolicyAdviceActivity.class));
                        break;
                    case 3:
                        getActivity().startActivity(new Intent(getActivity(), HatchServiceActivity.class));
                        break;
                    case 4:
                        getActivity().startActivity(new Intent(getActivity(), TrainServiceActivity.class));
                        break;
                    case 5:
                        getActivity().startActivity(new Intent(getActivity(), ProductServiceActivity.class));
                        break;
                    case 6:
                        getActivity().startActivity(new Intent(getActivity(), GaoteIntroActivity.class));
                        break;
                    case 7:
                        getActivity().startActivity(new Intent(getActivity(), AgriculturalServiceActivity.class));
                        break;
                    case 8:
                        CommonWebViewActivity.startActivity(getActivity(), "数据中心", "http://120.79.176.228/gaote-web/records_center.html");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void initCategoryItems() {
        mCommonCategoryItems = new ArrayList<>();
        String[] categoryTitles = getResources().getStringArray(R.array.service_center_cat_title);
        int[] iconResIds = DrawableUtil.getDrawableResIds(getActivity(), R.array.service_center_cat_icon);
        for (int i = 0; i < ServiceCenterConstants.CATEGORY_NUMS; i++) {
            CommonCategoryItem item = new CommonCategoryItem(iconResIds[i], categoryTitles[i]);
            mCommonCategoryItems.add(item);
        }
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_service_center;
    }
}
