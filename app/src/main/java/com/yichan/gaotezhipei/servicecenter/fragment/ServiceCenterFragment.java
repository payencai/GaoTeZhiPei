package com.yichan.gaotezhipei.servicecenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.util.DrawableUtil;
import com.yichan.gaotezhipei.common.view.AutoScrollViewPagerWithIndicator;
import com.yichan.gaotezhipei.common.view.CategoryAdapter;
import com.yichan.gaotezhipei.common.view.GridLayoutScrollManager;
import com.yichan.gaotezhipei.enterprise.activity.EnterpriseActivity;
import com.yichan.gaotezhipei.servicecenter.constant.ServiceCenterConstants;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;
import com.yichan.gaotezhipei.servicecenter.view.ServiceCenterCatAdapter;

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
        Context context = getContext();
        if (context != null) {
            LayoutInflater inflate = LayoutInflater.from(context);
            for (int i = 0; i < 5; i++) {
                View view = inflate.inflate(R.layout.service_center_asvp_layout,
                        null);
                ImageView imageView = (ImageView) view.findViewById(R.id.service_center_iv_scroll);
                imageView.setImageResource(R.mipmap.ic_launcher);
                mViewPagerIndicator.addViewToViewPager(imageView);
            }
        }

        mViewPagerIndicator.getAutoScrollViewPager().setInterval(5000);
        mViewPagerIndicator.getAutoScrollViewPager().setOffscreenPageLimit(3);
        if (getUserVisibleHint()) {
            mViewPagerIndicator.getAutoScrollViewPager().startAutoScroll();
        }

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
                        Intent intent = new Intent(getActivity(), EnterpriseActivity.class);
                        getActivity().startActivity(intent);
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
