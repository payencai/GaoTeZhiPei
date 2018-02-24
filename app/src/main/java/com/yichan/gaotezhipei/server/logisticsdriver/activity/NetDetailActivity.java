package com.yichan.gaotezhipei.server.logisticsdriver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.server.logisticsdriver.constant.LogisticsDriverConstants;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetDetailItem;
import com.yichan.gaotezhipei.server.logisticsdriver.entity.NetOrderItem;
import com.yichan.gaotezhipei.server.logisticsdriver.event.SelectOrderEvent;
import com.yichan.gaotezhipei.server.logisticsdriver.view.NetDetailAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/2/6.
 */

public class NetDetailActivity extends BaseListActivity{

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.net_detail_cb_selectAll)
    CheckBox mCbSelectAll;

    @BindView(R.id.net_detail_tv_cargo_count)
    TextView mTvCount;

    private View mViewNodata;

    private List<NetDetailItem> mList = new ArrayList<>();
    private NetDetailAdapter mAdapter;

    private NetOrderItem mNetworkInform;

    private List<String> mSelectedOrderIds = new ArrayList<>();


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
        mNetworkInform = (NetOrderItem) getIntent().getSerializableExtra("netorderItem");
        if(mNetworkInform != null) {
            mViewNodata = findViewById(R.id.view_no_data);
            initTitleBar();
            initSelectAllCheckBox();
        } else {
            showToast("获取网点信息失败");
            finish();
        }
    }

    private void initSelectAllCheckBox() {
        mCbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    toggleAllData(true);
                } else {
                    toggleAllData(false);
                }
                mAdapter = new NetDetailAdapter(NetDetailActivity.this, mList);
                mMultiRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void toggleAllData(boolean isCheck) {
        for(int i = 0; i < mList.size(); i++) {
            mList.get(i).setCheck(isCheck);
            if(isCheck) {
                if(!mSelectedOrderIds.contains(mList.get(i).getId())) {
                    mSelectedOrderIds.add(mList.get(i).getId());
                }
            }
        }
        if(!isCheck) {
            mSelectedOrderIds.clear();
            mTvCount.setText("0件");
        } else {
            mTvCount.setText(mSelectedOrderIds.size() + "件");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.unregister(this);
    }

    public static void startActivity(Context context, NetOrderItem netOrderItem) {
        Intent intent = new Intent(context, NetDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("netorderItem", netOrderItem);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    private void initTitleBar() {
        mTvTitle.setText(mNetworkInform.getName());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_net_detail;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NetDetailAdapter(NetDetailActivity.this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.net_detail_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(true);
    }

    private void getDataList(boolean isRefresh) {

        if(isRefresh) {
            mList.clear();

            mCbSelectAll.setChecked(false);
            mTvCount.setText("0件");
            mSelectedOrderIds.clear();
        }



        Map<String,String> params = new HashMap<>();
        params.put("networkId", mNetworkInform.getId());

        GetRequest getRequest = new GetRequest(UrlUtil.formTotalUrl(AppConstants.BASE_URL + LogisticsDriverConstants.URL_GET_NET_DETAIL_LIST, params), null, null, null);

        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<List<NetDetailItem>>(call) {

            @Override
            public Result<List<NetDetailItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), NetDetailItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                toggleNoDataView(true);
                doRefreshFinish(0);
            }

            @Override
            protected void handleResponse(Result<List<NetDetailItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    if(response.getData() != null) {
                        mList.addAll(response.getData());
                    }

                    //判断当前数据
                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                        toggleNoDataView(true);
                    }


                } else {
                    showToast(response.getMessage());
                }
                doRefreshFinish(0);
            }


        });
    }

    @OnClick({R.id.titlebar_btn_left,R.id.view_no_data,R.id.net_detail_tv_get_cargo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.view_no_data:
                getDataList(true);
                break;
            case R.id.net_detail_tv_get_cargo:
                DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认揽货吗？", new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        if(result == -1) {
                            getCargo();
                        }
                    }
                });
            default:
                break;
        }
    }

    private void getCargo() {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + LogisticsDriverConstants.URL_TAKE_ORDER)
                .addParams("ids", GsonUtil.GsonString(mSelectedOrderIds)).build();

        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("揽货成功");
                    getDataList(true);
                } else {
                    showToast(response.getMessage());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectOrderEvent selectOrderEvt) {
        if(selectOrderEvt.isSelected) {
            mSelectedOrderIds.add(selectOrderEvt.orderId);
        } else {
            mSelectedOrderIds.remove(selectOrderEvt.orderId);
        }
        mTvCount.setText(mSelectedOrderIds.size() + "件");
    }
}
