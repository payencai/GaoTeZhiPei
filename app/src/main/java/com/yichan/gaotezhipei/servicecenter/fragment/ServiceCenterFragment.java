package com.yichan.gaotezhipei.servicecenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.aohanyao.transformer.library.CardPageTransformer;
import com.aohanyao.transformer.library.conf.OnPageTransformerListener;
import com.aohanyao.transformer.library.conf.PageTransformerConfig;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.activity.AgriculturalServiceActivity;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.util.DrawableUtil;
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
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;
import com.yichan.gaotezhipei.servicecenter.view.ServiceCenterCatAdapter;
import com.yichan.gaotezhipei.trainservice.activity.TrainServiceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initAutoScrollViewPager();
        initRecyclerViewCategories();
    }


    private void initAutoScrollViewPager() {
        //TODO 网络数据
        mViewPagerIndicator.setDisplayDots(false);
        Context context = getContext();
        if (context != null) {
            LayoutInflater inflate = LayoutInflater.from(context);
            for (int i = 0; i < 3; i++) {
                View view = inflate.inflate(R.layout.service_center_asvp_layout,
                        null);
                ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
                imageView.setImageResource(R.drawable.service_center_banner);
                mViewPagerIndicator.addViewToViewPager(imageView);
            }
        }

        mViewPagerIndicator.getAutoScrollViewPager().setInterval(5000);
        mViewPagerIndicator.getAutoScrollViewPager().setOffscreenPageLimit(3);
        if (getUserVisibleHint()) {
            mViewPagerIndicator.getAutoScrollViewPager().startAutoScroll();
        }


        mViewPagerIndicator.getAutoScrollViewPager().setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
                .addAnimationType(PageTransformerConfig.ROTATION)//默认动画 default animation rotation  旋转  当然 也可以一次性添加两个  后续会增加更多动画
                .setRotation(0)//旋转角度
                .addAnimationType(PageTransformerConfig.NONE)//默认动画 透明度 暂时还有问题
                .setViewType(PageTransformerConfig.LEFT)//view的类型
                .setOnPageTransformerListener(new OnPageTransformerListener() {
                    @Override
                    public void onPageTransformerListener(View page, float position) {
                        //你也可以在这里对 page 实行自定义动画 cust anim
                    }
                })
                .setTranslationOffset(40)
                .setScaleOffset(100)
                .create());

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
