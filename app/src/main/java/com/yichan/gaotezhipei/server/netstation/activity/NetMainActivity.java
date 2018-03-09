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
import com.yichan.gaotezhipei.common.util.UrlUtil;
import com.yichan.gaotezhipei.login.util.LoginManager;
import com.yichan.gaotezhipei.server.netstation.constant.NetStationConstants;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressConfirmedItem;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressConfirmedOrderPage;
import com.yichan.gaotezhipei.server.netstation.view.ExpressConfirmedAdapter;

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

public class NetMainActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    @BindView(R.id.titlebar_btn_right)
    ImageButton mIbRight;

    private View mViewNodata;

    private ExpressConfirmedAdapter mAdapter;

    private List<ExpressConfirmedItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mStartPageNum  = 1;
        mSize = 15;
        mViewNodata = findViewById(R.id.view_no_data);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("服务网点");
        mIbLeft.setImageResource(R.drawable.logout_txt);
        mIbRight.setImageResource(R.drawable.setting_txt);
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
        String url = AppConstants.BASE_URL + NetStationConstants.URL_GET_CONFIRMED_ORDER;
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(currentPage));
        url = UrlUtil.formTotalUrl(url, params);
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<ExpressConfirmedOrderPage>(call) {

            @Override
            public Result parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(ExpressConfirmedOrderPage.class)
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
            protected void handleResponse(Result<ExpressConfirmedOrderPage> response) {
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

                    if(isReresh) {
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



    @OnClick({R.id.net_main_iv_new_address, R.id.net_main_iv_express_to_confirm, R.id.net_main_iv_express_record,R.id.view_no_data, R.id.titlebar_btn_left, R.id.titlebar_btn_right})
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
                getDataList(1, true);
                break;
            case R.id.titlebar_btn_left:
                LoginManager.logout(this, false);
                break;
            case R.id.titlebar_btn_right:
                startActivity(new Intent(NetMainActivity.this, NetProfileActivity.class));
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
