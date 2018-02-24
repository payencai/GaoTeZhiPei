package com.yichan.gaotezhipei.server.netstation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.login.util.LoginManager;
import com.yichan.gaotezhipei.server.netstation.constant.NetStationConstants;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressConfirmedItem;
import com.yichan.gaotezhipei.server.netstation.view.ExpressConfirmedAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/19.
 */

public class NetMainActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    private View mViewNodata;

    private ExpressConfirmedAdapter mAdapter;

    private List<ExpressConfirmedItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mViewNodata = findViewById(R.id.view_no_data);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("服务网点");
        mIbLeft.setImageResource(R.drawable.logout);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_server_net_main;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ExpressConfirmedAdapter(this, mList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.net_main_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(true);
    }

    private void getDataList(boolean isReresh) {
        if(isReresh) {
            mList.clear();
        }
        GetRequest getRequest = new GetRequest(AppConstants.BASE_URL + NetStationConstants.URL_GET_CONFIRMED_ORDER, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<List<ExpressConfirmedItem>>(call) {

            @Override
            public Result parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), ExpressConfirmedItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                doRefreshFinish(0);
                toggleNoDataView(true);
            }

            @Override
            protected void handleResponse(Result<List<ExpressConfirmedItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mList.addAll(response.getData());
                    }

                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                        toggleNoDataView(true);
                    }

                } else {
                    toggleNoDataView(true);
                    showToast(response.getMessage());
                }
                doRefreshFinish(0);

            }


        });
    }



    @OnClick({R.id.net_main_iv_new_address, R.id.net_main_iv_express_to_confirm, R.id.net_main_iv_express_record,R.id.view_no_data, R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.net_main_iv_new_address:
                startActivity(new Intent(NetMainActivity.this, NewExpressActivity.class));
                break;
            case R.id.net_main_iv_express_to_confirm:
                startActivity(new Intent(NetMainActivity.this, ExpressToConfirmActivity.class));
                break;
            case R.id.net_main_iv_express_record:
                startActivity(new Intent(NetMainActivity.this, ExpressRecordActivity.class));
                break;
            case R.id.view_no_data:
                getDataList(true);
                break;
            case R.id.titlebar_btn_left:
                LoginManager.logout(this, false);
                break;
            default:
                break;
        }
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
