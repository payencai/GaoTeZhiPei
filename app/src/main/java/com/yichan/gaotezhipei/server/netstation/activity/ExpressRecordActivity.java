package com.yichan.gaotezhipei.server.netstation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.server.netstation.constant.NetStationConstants;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressRecordedOrderPage;
import com.yichan.gaotezhipei.server.netstation.view.ExpressRecordedAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/19.
 */

public class ExpressRecordActivity extends BaseListActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private ExpressRecordedAdapter mAdapter;

    private List<ExpressRecordedOrderPage.ListBean> mList = new ArrayList<>();

    private View mViewNodata;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 15;
        mViewNodata = findViewById(R.id.view_no_data);
        mTvTitle.setText("快件收录");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.view_no_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.view_no_data:
                getDataList(1, true);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_express_record;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ExpressRecordedAdapter(this, mList);
        mAdapter.setSubviewListener(new OnItemSubviewClickListener<ExpressRecordedOrderPage.ListBean>() {
            @Override
            public void onClick(View v, int pos, final ExpressRecordedOrderPage.ListBean model) {
                switch (v.getId()) {
                    case R.id.item_tv_takeon:
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认收录吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    takeOnExpress(model.getId());
                                }
                            }
                        });
                }
            }
        });
        return mAdapter;
    }

    private void takeOnExpress(String orderId) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + NetStationConstants.URL_TAKE_ON_ORDER)
                .addParams("orderId", orderId).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("收录成功");
                    getDataList(1, true);
                }
                else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.express_record_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {
        getDataList(currentPage, false);
    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(currentPage, true);
    }

    private void getDataList(int currentPage, final boolean isReresh) {
        if(isReresh) {
            mList.clear();
        }
        String url = AppConstants.BASE_URL + NetStationConstants.URL_GET_UNARRIVED_ORDERS;
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(currentPage));
        url = UrlUtil.formTotalUrl(url, params);
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<ExpressRecordedOrderPage>(call) {

            @Override
            public Result parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(ExpressRecordedOrderPage.class)
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
            protected void handleResponse(Result<ExpressRecordedOrderPage> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mList.addAll(response.getData().getList());
                    }

                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                        toggleNoDataView(true);
                    }

                    if (isReresh) {
                        doRefreshFinish(response.getData().getList().size());
                    } else {
                        doLoadMoreFinish(response.getData().getList().size());
                    }

                } else {
                    toggleNoDataView(true);
                    showToast(response.getMessage());
                    doRefreshFinish(0);
                    doLoadMoreFinish(0);
                }
            }


        });
    }

    protected void toggleNoDataView(boolean isShow) {
        if(isShow) {
            mViewNodata.setVisibility(View.VISIBLE);
            mMultiLayout.setVisibility(View.GONE);
        } else {
            mViewNodata.setVisibility(View.GONE);
            mMultiLayout.setVisibility(View.VISIBLE);
        }
    }
}
