package com.yichan.gaotezhipei.logistics.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.fragment.CommonOrderFragment;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.logistics.activity.LCLOrderDetailActivity;
import com.yichan.gaotezhipei.logistics.constant.LogisticsContants;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.logistics.view.LCLOrderAdapter;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 拼货订单fragment公共基类。与物流订单隔开。
 * Created by ckerv on 2018/1/12.
 */

public class CommonLCLOrderFragment extends CommonOrderFragment {


    private List<LCLOrderPage.BeanListBean> mBeanList = new ArrayList<>();

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
        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LCLOrderDetailActivity.startActivity(getActivity(), mBeanList.get(position));
            }
        });
        mAdapter.setOnItemSubviewClickListener(new OnItemSubviewClickListener<LCLOrderPage.BeanListBean>() {
            @Override
            public void onClick(View v, int pos, LCLOrderPage.BeanListBean model) {
                if(!UserManager.getInstance(getActivity()).isDemand()) {
                    handleOrderByDriver(v, pos, model);
                } else {
                    //TODO 需求方处理，另外的接口
                    handleOrderByUser(v, pos, model);
                }
            }
        });
        return mAdapter;
    }

    private void handleOrderByUser(View v, int pos, final LCLOrderPage.BeanListBean model) {
        switch ((v.getId())) {
            case R.id.item_btn_right:
                if(Integer.valueOf(model.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_CONFIRM) {
                    DialogHelper.showConfirmDailog(getFragmentManager(), "您确认签收吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                onHandleOrderByUser(4, model.getId());
                            }
                        }
                    });
                }
        }
    }

    private void onHandleOrderByUser(int status, String orderId) {
        if(status == 4) {//需求方确认签收
            RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_FINISH_ORDER_BY_USER)
                    .addParams("pdriverOrderId", orderId).build();
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
    }

    private void handleOrderByDriver(View v, int pos, final LCLOrderPage.BeanListBean model) {
        switch (v.getId()) {
            case R.id.item_btn_left:
                DialogHelper.showConfirmDailog(getFragmentManager(), "您确认取消订单吗？", new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        if(result == -1) {
                            onHandleOrderByDriver(4, model.getId());
                        }
                    }
                });
                break;
            case R.id.item_btn_right:
                if(Integer.valueOf(model.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_GET_CARGO)  {
                    DialogHelper.showConfirmDailog(getFragmentManager(), "您确认接货吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                onHandleOrderByDriver(2, model.getId());
                            }
                        }
                    });
                } else if(Integer.valueOf(model.getType()) == LogisticsContants.TYPE_LCL_ORDER_TO_RECEIVE_CARGO) {
                    DialogHelper.showConfirmDailog(getFragmentManager(), "您确认送达吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                onHandleOrderByDriver(3, model.getId());
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void onHandleOrderByDriver(final int type, String orderId) {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_DRIVER_UPDATE_ORDER_STATUS)
                .addParams("pdriverOrderId", orderId)
                .addParams("status", String.valueOf(type)).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast(toastMsgWhenSuccess(type));
                    getDataList(1, true);
                } else {
                    showToast(response.getMessage());
                }
            }

        });
    }

    private String toastMsgWhenSuccess(int type) {
        if(type == 4) {
            return "取消订单成功";
        } else if(type == 2) {
            return "取货成功";
        } else if(type == 3) {
            return "已送达";
        }
        return "操作成功";
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
            postFormBuilder.url(AppConstants.BASE_URL + LogisticsContants.URL_DEMAND_GET_LCL_ORDER);
        } else {
            postFormBuilder.url(AppConstants.BASE_URL + LCLDriverConstants.URL_GET_ALL_ORDER);
        }

        if(mType != LogisticsContants.TYPE_LCL_ORDER_ALL) {//全部订单，不传参数
            postFormBuilder.addParams("type", String.valueOf(mType));
        }

        postFormBuilder.addParams("page", String.valueOf(currentPage));
        RequestCall call = postFormBuilder.build();
        call.doScene(new TokenSceneCallback<LCLOrderPage>(call) {

            @Override
            public Result<LCLOrderPage> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(LCLOrderPage.class)
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
            protected void handleResponse(Result<LCLOrderPage> response) {
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
                        showToast("暂无数据哦~");
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


    @OnClick({R.id.common_order_nodata})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_order_nodata:
                getDataList(1, true);
                break;
        }
    }


}
