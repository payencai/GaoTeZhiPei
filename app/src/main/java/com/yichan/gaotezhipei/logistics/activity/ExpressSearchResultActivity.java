package com.yichan.gaotezhipei.logistics.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.esharp.android.common.EJsonParser;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.logistics.entity.ExpressSearchResp;
import com.yichan.gaotezhipei.logistics.entity.ExpressTraceItem;
import com.yichan.gaotezhipei.logistics.view.ExpressSearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ExpressSearchResultActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    private ExpressSearchResultAdapter mAdapter;
    private String mResult;
    private List<ExpressTraceItem> mExpressTraceItems = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_express_search_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("查询结果");
        mResult = getIntent().getStringExtra("result");
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new ExpressSearchResultAdapter(this, mExpressTraceItems);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.logistic_traces_rvlist;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(true);
    }

    private void getDataList(final boolean isRresh) {
        if (isRresh) {
            mExpressTraceItems.clear();
        }
//        mResult = "{  \"LogisticCode\" : \"630878031817\",  \"ShipperCode\" : \"ZTO\",  \"Traces\" : [ {    \"AcceptStation\" : \"[昆明市]  [西山二未名城分部] 的 杨树妹 (15752802174) 已收件\",    \"AcceptTime\" : \"2018-03-12 15:47:46\"  }, {    \"AcceptStation\" : \"[昆明市]  快件离开 [昆明西山二] 发往 [昆明中转]\",    \"AcceptTime\" : \"2018-03-12 16:41:23\"  }, {    \"AcceptStation\" : \"[昆明市]  快件到达 [昆明中转]\",    \"AcceptTime\" : \"2018-03-12 18:12:19\"  }, {    \"AcceptStation\" : \"[昆明市]  快件离开 [昆明中转] 发往 [佛山中心]\",    \"AcceptTime\" : \"2018-03-12 18:14:15\"  }, {    \"AcceptStation\" : \"[南宁市]  快件到达 [南宁中转]\",    \"AcceptTime\" : \"2018-03-13 20:40:36\"  }, {    \"AcceptStation\" : \"[佛山市]  快件离开 [佛山中心] 发往 [广州大学城]\",    \"AcceptTime\" : \"2018-03-14 08:26:52\"  }, {    \"AcceptStation\" : \"[广州市]  快件到达 [广州大学城]\",    \"AcceptTime\" : \"2018-03-14 14:38:23\"  }, {    \"AcceptStation\" : \"[广州市]  [广州大学城] 的龙俊杰(13690219749) 正在第1次派件, 请保持电话畅通,并耐心等待\",    \"AcceptTime\" : \"2018-03-14 15:15:19\"  }, {    \"AcceptStation\" : \"[广州市]  快件已在 [广州大学城] 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!\",    \"AcceptTime\" : \"2018-03-14 23:46:37\"  } ],  \"State\" : \"3\",  \"EBusinessID\" : \"1261753\",  \"Success\" : true,  \"Reason\" : \"\"}";
        ExpressSearchResp mExpressSearchResp = new ExpressSearchResp();
        EJsonParser parser = new EJsonParser();
        parser.parseJsonToBean(mResult, mExpressSearchResp);
        if(mExpressSearchResp.getSuccess() != null && mExpressSearchResp.getSuccess()) {
            if(mExpressSearchResp.getTraces() != null && mExpressSearchResp.getTraces().size() > 0) {
                mExpressTraceItems.addAll(mExpressSearchResp.getTraces());
                if(mExpressTraceItems.size() > 0) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    //showToast("暂无数据哦");
                    showToast(mExpressSearchResp.getReason());
                }

            } else {
                // Toast("暂无轨迹信息");
                showToast(mExpressSearchResp.getReason());
            }
        } else {
            //无效的输入
            showToast(mExpressSearchResp.getReason());
        }
        if(isRresh) {
            doRefreshFinish(0);
        } else {
            doLoadMoreFinish(0);
        }
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onCick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }
}
