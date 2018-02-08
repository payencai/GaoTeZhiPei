package com.yichan.gaotezhipei.logistics.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.OrderPageList;
import com.yichan.gaotezhipei.logistics.view.LCLOrderAdapter;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 拼货订单fragment公共基类。与物流订单隔开。
 * Created by ckerv on 2018/1/12.
 */

public class CommonLCLOrderFragment extends CommonOrderFragment {


    private List<OrderPageList.BeanListBean> mBeanList = new ArrayList<>();

    private LCLOrderAdapter mAdapter;

    private int mType;

    public CommonLCLOrderFragment(int type) {
        this.mType = type;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 8;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LCLOrderAdapter(getActivity(), mBeanList);
        return mAdapter;
    }


    @Override
    protected void doLoreMore(int currentPage, int size) {
        getDataList(currentPage, false);
    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(currentPage, true);
    }


    public void getDataList(int currentPage, final boolean isRefresh) {
        if(isRefresh) {
            mBeanList.clear();
        }
        PostFormBuilder postFormBuilder = new PostFormBuilder();

        if(UserManager.getInstance(getActivity()).isDemand()) {
            postFormBuilder.url(AppConstants.BASE_URL + LogisticsContants.URL_DEMAND_GET_ORDER);
        } else {
            postFormBuilder.url(AppConstants.BASE_URL + LCLDriverConstants.URL_GET_ALL_ORDER);
        }

        if(mType != LogisticsContants.TYPE_LCL_ORDER_ALL) {//全部订单，不传参数
            postFormBuilder.addParams("type", String.valueOf(mType));
        }

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
