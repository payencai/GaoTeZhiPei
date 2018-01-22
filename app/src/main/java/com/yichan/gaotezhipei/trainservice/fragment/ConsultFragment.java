package com.yichan.gaotezhipei.trainservice.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.common.view.CommonLinearListFragment;
import com.yichan.gaotezhipei.trainservice.entity.ConsultItem;
import com.yichan.gaotezhipei.trainservice.view.ConsultListAdapter;

/**
 * Created by ckerv on 2018/1/7.
 */

public class ConsultFragment extends CommonLinearListFragment<ConsultItem>{

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        ConsultListAdapter adapter = new ConsultListAdapter(getContext(), getItemList());
        adapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("..." + getItemList().get(position));
            }
        });
        return adapter;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++) {
            getItemList().add(new ConsultItem());
        }
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }

}
