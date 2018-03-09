package com.yichan.gaotezhipei.server.logisticsdriver.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.server.logisticsdriver.activity.LogisticsDriverOrderDetailActivity;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.LogisticsDriverOrderItem;
import com.yichan.gaotezhipei.server.logisticsdriver.event.RefreshOrderEvent;
import com.yichan.gaotezhipei.server.logisticsdriver.view.LogisticsDriverOrderAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/5.
 */

public class CommonLogisticsDriverOrderFragment extends CommonOrderFragment {

    private List<LogisticsDriverOrderItem> mBeanList = new ArrayList<>();
    private LogisticsDriverOrderAdapter mAdapter;

    private int mType;

    public CommonLogisticsDriverOrderFragment() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if(!EventBus.getInstance().isRegistered(this)) {
            EventBus.getInstance().register(this);
        }
        mStartPageNum = 1;
        mSize = 15;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getInstance().isRegistered(this)) {
            EventBus.getInstance().unregister(this);
        }
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public CommonLogisticsDriverOrderFragment(int type) {
        mType = type;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LogisticsDriverOrderAdapter(getActivity(), mBeanList);
        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogisticsDriverOrderDetailActivity.startActivity(getActivity(), mBeanList.get(position));
            }
        });
        mAdapter.setSubviewClickListener(new OnItemSubviewClickListener<LogisticsDriverOrderItem>() {
            @Override
            public void onClick(View v, int pos, final LogisticsDriverOrderItem model) {
                switch (v.getId()) {
                    case R.id.item_btn_bottom:
                        if (Integer.valueOf(model.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_CONFIRM) {
                            DialogHelper.showConfirmDailog(getFragmentManager(), "您确认收货吗？", new IDialogResultListener<Integer>() {
                                @Override
                                public void onDataResult(Integer result) {
                                    if (result == -1) {
                                        confirmExpress(model.getNetworkId(), model.getUpdateTime());
                                    }
                                }
                            });
                        } else if (Integer.valueOf(model.getDriverStatus()) == LogisticsDriverConstants.TYPE_TO_DELEVER) {
                            DialogHelper.showConfirmDailog(getFragmentManager(), "您确认送达吗？", new IDialogResultListener<Integer>() {
                                @Override
                                public void onDataResult(Integer result) {
                                    if (result == -1) {
                                        deliverExpress(model.getNetworkId(), model.getUpdateTime());
                                    }
                                }
                            });
                        }

                }
            }
        });
        return mAdapter;
    }

    private void confirmExpress(String networkId, String updateTime) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_CONFIRM_ORDER)
                .addParams("networkId", networkId)
                .addParams("confirmTime", updateTime)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("确认收货成功");
                    getDataList(1, true);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void deliverExpress(String networkId, String updateTime) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_DILIVER)
                .addParams("networkId", networkId)
                .addParams("pickTime", updateTime)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("确认送达成功");
                    getDataList(1, true);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
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
        if (isRefresh) {
            mBeanList.clear();
        }
        String url = AppConstants.BASE_URL + LogisticsDriverConstants.URL_GET_MY_ORDER;

        Map<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(currentPage));

        if (mType != LogisticsDriverConstants.TYPE_ALL) {
            params.put("status", String.valueOf(mType));
        }

        url = UrlUtil.formTotalUrl(url, params);

        GetRequest getRequest = new GetRequest(url, null, null, null);

        RequestCall call = getRequest.build();

        call.doScene(new TokenSceneCallback<List<LogisticsDriverOrderItem>>(call) {

            @Override
            public Result<List<LogisticsDriverOrderItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), LogisticsDriverOrderItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                doRefreshFinish(0);
                doLoadMoreFinish(0);
                toggleNoDataView(true);
            }


            @Override
            protected void handleResponse(Result<List<LogisticsDriverOrderItem>> response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {

                    //如果获取到的数据不为空，则直接添加进当前数据
                    if (response.getData() != null) {
                        mBeanList.addAll(response.getData());
                    }

                    //判断当前数据
                    if (mBeanList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                        toggleNoDataView(true);
                    }

                    //后续操作
                    if (isRefresh) {
                        doRefreshFinish(response.getData().size());
                    } else {
                        doLoadMoreFinish(response.getData().size());
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

    @OnClick({R.id.common_order_nodata})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_order_nodata:
                getDataList(1, true);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveRefreshEvent(RefreshOrderEvent refreshLCLOrderEvent) {
        if(isVisible()) {
            getDataList(1, true);
        }
    }
}
