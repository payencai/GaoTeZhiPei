package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.logistics.entity.OrderPageList;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;
import com.yichan.gaotezhipei.server.lcldriver.view.NearbyCargoAdapter;

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
 * Created by ckerv on 2018/1/19.
 */

public class NearbyCargoActivity extends BaseListActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.view_no_data)
    View mViewNodata;

    private List<OrderPageList.BeanListBean> mBeanList = new ArrayList<>();

    private NearbyCargoAdapter mAdapter;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 8;
        initTitleBar();
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

    public void getDataList(int currentPage, final boolean isRefresh) {
        PostFormBuilder postFormBuilder = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_GET_AVAILABLE_ORDER);
        postFormBuilder.addParams("page", String.valueOf(currentPage));
        RequestCall call = postFormBuilder.build();
        call.doScene(new TokenSceneCallback<OrderPageList>(call) {

            @Override
            public Result<OrderPageList> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(OrderPageList.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                doRefreshFinish(0);
                doLoadMoreFinish(0);
                toggleNoDataView(true);
            }



            @Override
            protected void handleResponse(Result<OrderPageList> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    //如果获取到的数据不为空，则直接添加进当前数据
                    if(response.getData().getBeanList() != null) {
                        mBeanList.addAll(response.getData().getBeanList());
                    }

                    //判断当前数据
                    if(mBeanList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        toggleNoDataView(true);
                    }

                    //后续操作
                    if(isRefresh) {
                        doRefreshFinish(response.getData().getBeanList().size());
                    } else {
                        doLoadMoreFinish(response.getData().getBeanList().size());
                    }

                } else {
                    toggleNoDataView(true);
                    showToast(response.getMessage());
                    doLoadMoreFinish(0);
                    doRefreshFinish(0);
                }
            }


        });

    }

    private void initTitleBar() {
        mTvTitle.setText("货源信息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_nearby_cargo;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyCargoAdapter(this, mBeanList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.nearby_cargo_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {
        getDataList(currentPage, false);
    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(currentPage, true);
    }

    private void toggleNoDataView(boolean isShow) {
        if(isShow) {
            mViewNodata.setVisibility(View.VISIBLE);
            mMultiLayout.setVisibility(View.GONE);
        } else {
            mViewNodata.setVisibility(View.GONE);
            mMultiLayout.setVisibility(View.VISIBLE);
        }
    }
}
