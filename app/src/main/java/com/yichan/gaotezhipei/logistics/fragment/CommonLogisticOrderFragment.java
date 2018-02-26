package com.yichan.gaotezhipei.logistics.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;
import com.yichan.gaotezhipei.logistics.view.LogisticOrderAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/15.
 */

public class CommonLogisticOrderFragment extends CommonOrderFragment {
    protected List<LogisticsOrderPage.ListBean> mList = new ArrayList<>();
    private LogisticOrderAdapter mAdapter;

    private int mType;

    public CommonLogisticOrderFragment() {

    }
    @SuppressLint({"NewApi", "ValidFragment"})
    public CommonLogisticOrderFragment(int type) {
        this.mType = type;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 15;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new LogisticOrderAdapter(getActivity(), mList);
        mAdapter.setSubviewClickListener(new OnItemSubviewClickListener<LogisticsOrderPage.ListBean>() {
            @Override
            public void onClick(View v, int pos, LogisticsOrderPage.ListBean model) {
                switch (v.getId()) {
                    case R.id.item_btn_right:
                        if(Integer.valueOf(model.getStatus()) == LogisticsContants.TYPE_LOG_ORDER_TO_CONFIRM) {
                            tryToSign(model.getId());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        return mAdapter;
    }

    private void tryToSign(final String orderId) {
        DialogHelper.showConfirmDailog(getActivity().getSupportFragmentManager(), "您确认签收吗?", new IDialogResultListener<Integer>() {
            @Override
            public void onDataResult(Integer result) {
                if(result == -1) {
                    onSign(orderId);
                }
            }
        });
    }

    private void onSign(String orderId) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsContants.URL_DEMAND_SIGN_ORDER)
                .addParams("orderId", orderId).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("签收成功");
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
        if(isRefresh) {
            mList.clear();
        }
        String url = AppConstants.BASE_URL + LogisticsContants.URL_DEMAND_GET_LOG_ORDER;
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(currentPage));
        if(mType != LogisticsContants.TYPE_LOG_ORDER_ALL) {
            params.put("status", String.valueOf(mType));
        }
        url = UrlUtil.formTotalUrl(url, params);
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<LogisticsOrderPage>(call) {

            @Override
            public Result<LogisticsOrderPage> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(LogisticsOrderPage.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                toggleNoDataView(true);
                doRefreshFinish(0);
                doLoadMoreFinish(0);
            }

            @Override
            protected void handleResponse(Result<LogisticsOrderPage> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    //如果获取到的数据不为空，则直接添加进当前数据
                    if(response.getData() != null) {
                        mList.addAll(response.getData().getList());
                    }

                    //判断当前数据
                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        toggleNoDataView(true);
                    }

                    //后续操作
                    if(isRefresh) {
                        doRefreshFinish(response.getData().getList().size());
                    } else {
                        doLoadMoreFinish(response.getData().getList().size());
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


}
