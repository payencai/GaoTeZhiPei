package com.yichan.gaotezhipei.mine.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.logistics.entity.LogisticsOrderPage;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.entity.MyMessageItem;
import com.yichan.gaotezhipei.mine.view.MyMessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/15.
 */

public class MyMessageActivity extends BaseListActivity {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private MyMessageAdapter mAdapter;
    private List<MyMessageItem> mMsgList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvTitle.setText("我的消息");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new MyMessageAdapter(this, mMsgList);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected int getMultiRecyclerViewLayoutId() {
        return R.id.my_message_rv_list;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {

    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(true);
    }


    private void getDataList(final boolean isRresh) {
        if(isRresh) {
            mMsgList.clear();
        }

        GetRequest getRequest = new GetRequest(AppConstants.BASE_URL + MineConstants.URL_GET_NOTICE, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<List<MyMessageItem>>(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }


            @Override
            protected void handleResponse(Result<List<MyMessageItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    if(response.getData() != null) {
                        mMsgList.addAll(response.getData());
                    }

                    if(mMsgList.size() != 0) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast("暂无数据哦");
                    }

                    if(isRresh) {
                        doRefreshFinish(0);
                    } else {
                        doLoadMoreFinish(0);
                    }


                } else {
                    showToast(response.getMessage());
                }
            }

            @Override
            public Result<List<MyMessageItem>> parseNetworkResponse(Response response) throws IOException {

                Result<List<MyMessageItem>> result = new Result();
                String jsonStr = response.body().string();
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                List<MyMessageItem> mList = new ArrayList<>();
                try {
                    jsonObject = new JSONObject(jsonStr);
                    result.setMessage(jsonObject.getString("message"));
                    result.setResultCode(jsonObject.getInt("resultCode"));
                    jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        if(jsonObject1.getInt("type") == 1) {
                            MyMessageItem<LCLOrderPage.BeanListBean> myMessageItem = new MyMessageItem<LCLOrderPage.BeanListBean>();
                            myMessageItem.setType(jsonObject1.getInt("type"));
                            myMessageItem.setCreateTime(jsonObject1.getString("createTime"));
                            LCLOrderPage.BeanListBean bean = (LCLOrderPage.BeanListBean) GsonUtil.gsonToBean(jsonObject1.getString("order"), LCLOrderPage.BeanListBean.class);
                            myMessageItem.setOrder(bean);
                            mList.add(myMessageItem);
                        } else if(jsonObject1.getInt("type") == 2) {
                            MyMessageItem<LogisticsOrderPage.ListBean> myMessageItem = new MyMessageItem<LogisticsOrderPage.ListBean>();
                            myMessageItem.setType(jsonObject1.getInt("type"));
                            myMessageItem.setCreateTime(jsonObject1.getString("createTime"));
                            LogisticsOrderPage.ListBean bean = (LogisticsOrderPage.ListBean) GsonUtil.gsonToBean(jsonObject1.getString("order"), LogisticsOrderPage.ListBean.class);
                            myMessageItem.setOrder(bean);
                            mList.add(myMessageItem);
                        }
                    }

                    result.setData(mList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
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
