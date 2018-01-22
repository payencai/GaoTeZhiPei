package com.yichan.gaotezhipei.trainservice.fragment;

import android.support.v7.widget.RecyclerView;

import com.yichan.gaotezhipei.common.view.CommonLinearListFragment;
import com.yichan.gaotezhipei.trainservice.entity.CourseItem;
import com.yichan.gaotezhipei.trainservice.view.CourseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckerv on 2018/1/20.
 */

public class OnlineCourseFragment extends CommonLinearListFragment {

    private List<CourseItem> mList = new ArrayList<>();
    private CourseListAdapter mAdapter;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new CourseListAdapter(getActivity(), mList, 0);
        return mAdapter;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++) {
            mList.add(new CourseItem());
        }
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }
}
