package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.server.lcldriver.activity.DriverInformActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NearbyNetItem;
import com.yichan.gaotezhipei.server.logisticsdriver.view.NearbyNetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/2/5.
 */

public class LogisticsDriverMainActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    private List<NearbyNetItem> mList = new ArrayList<>();

    private NearbyNetAdapter mAdapter;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();

        mStartPageNum = 1;
    }

    private void initTitleBar() {
        mTvTitle.setText("物流司机");
        mIbLeft.setVisibility(View.GONE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_server_logistics_driver;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyNetAdapter(LogisticsDriverMainActivity.this, mList);
        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(LogisticsDriverMainActivity.this, NetDetailActivity.class));
            }
        });
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.logistics_driver_main_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        for(int i = 0; i < 10; i++ ) {
            mList.add(new NearbyNetItem());
        }
//        mStartPageNum = 1;
//        getDataList(mStartPageNum);
        mAdapter.notifyDataSetChanged();
        doRefreshFinish(0);
        doLoadMoreFinish(0);
    }

    public void getDataList(int pageNum) {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_GET_NET_LIST).
                addParams("pageNum", String.valueOf(pageNum)).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {

            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                }
            }

        });
    }

    @OnClick({R.id.logistics_driver_iv_net, R.id.logistics_driver_iv_myorder, R.id.logistics_driver_iv_driver_inform})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logistics_driver_iv_net:
                startActivity(new Intent(LogisticsDriverMainActivity.this, NearbyNetActivity.class));
                break;
            case R.id.logistics_driver_iv_myorder:
                startActivity(new Intent(LogisticsDriverMainActivity.this, LogisticsDriverOrderActivity.class));
                break;
            case R.id.logistics_driver_iv_driver_inform:
                startActivity(new Intent(LogisticsDriverMainActivity.this, DriverInformActivity.class));
                break;
            default:
                break;
        }
    }
}
