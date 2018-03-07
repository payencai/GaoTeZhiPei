package com.yichan.gaotezhipei.agriculturalservice.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.agriculturalservice.constant.AgriculturalServiceConstants;
import com.yichan.gaotezhipei.agriculturalservice.entity.AgriculturalShopPage;
import com.yichan.gaotezhipei.agriculturalservice.view.AgriculturalShopAdapter;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;

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
 * Created by ckerv on 2018/1/12.
 */

public class AgriculturalServiceActivity extends BaseListActivity {


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private View mViewNodata;


    private AgriculturalShopAdapter mAdapter;

    private List<AgriculturalShopPage.BeanListBean> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 8;
        mViewNodata = findViewById(R.id.view_no_data);
        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("农资服务");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_agricultural;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new AgriculturalShopAdapter(AgriculturalServiceActivity.this, mList);
        mAdapter.setSubviewClickListener(new OnItemSubviewClickListener<AgriculturalShopPage.BeanListBean>() {
            @Override
            public void onClick(View v, int pos, AgriculturalShopPage.BeanListBean model) {
                diallPhone(model.getTelephone());
            }
        });
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(AgriculturalServiceActivity.this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.agricultural_rv_list;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {
        getDataList(currentPage, false);
    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(currentPage, true);
    }

    private void getDataList(int currentPage, final boolean isRefresh) {
        if(isRefresh) {
            mList.clear();
        }
        String url = AppConstants.BASE_URL + AgriculturalServiceConstants.URL_GET_SHOPS;
        RequestCall call = new PostFormBuilder().url(url)
                .addParams("page", String.valueOf(currentPage))
                .build();
        call.doScene(new TokenSceneCallback<AgriculturalShopPage>(call) {

            @Override
            public Result parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(AgriculturalShopPage.class)
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
            protected void handleResponse(Result<AgriculturalShopPage> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mList.addAll(response.getData().getBeanList());
                    }

                    if(mList.size() != 0) {
                        toggleNoDataView(false);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦~");
                        toggleNoDataView(true);
                    }

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



    @OnClick({R.id.titlebar_btn_left,R.id.view_no_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.view_no_data:
                getDataList(1, true);
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

    private void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
