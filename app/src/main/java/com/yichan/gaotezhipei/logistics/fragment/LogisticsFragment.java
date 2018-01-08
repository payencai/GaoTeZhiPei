package com.yichan.gaotezhipei.logistics.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseFragment;
import com.yichan.gaotezhipei.common.util.DrawableUtil;
import com.yichan.gaotezhipei.common.view.CategoryAdapter;
import com.yichan.gaotezhipei.common.view.DividerItemDecoration;
import com.yichan.gaotezhipei.common.view.GridLayoutScrollManager;
import com.yichan.gaotezhipei.logistics.view.LogisticsCatAdapter;
import com.yichan.gaotezhipei.servicecenter.entity.CommonCategoryItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ckerv on 2018/1/7.
 */

public class LogisticsFragment extends BaseFragment {

    @BindView(R.id.logistics_iv_map)
    ImageView mIvMap;
    @BindView(R.id.logistics_rv_category)
    RecyclerView mRvCategories;

    private List<CommonCategoryItem> mCommonCategoryItems;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initMapView();
        initRecyclerViewCategories();

    }

    private void initMapView() {
        mIvMap.setImageResource(R.mipmap.ic_launcher);
    }


    private void initRecyclerViewCategories() {
        initCategoryItems();
        mCategoryAdapter = new LogisticsCatAdapter(getActivity(), mCommonCategoryItems);

        GridLayoutScrollManager layoutManager = new GridLayoutScrollManager(getActivity(), 2);
        layoutManager.setCanScroll(false);//设置不能滚动
        mRvCategories.setLayoutManager(layoutManager);

        mRvCategories.addItemDecoration(new DividerItemDecoration(getActivity()));

        mRvCategories.setAdapter(mCategoryAdapter);

        mCategoryAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("呵呵 " + position);
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
}
