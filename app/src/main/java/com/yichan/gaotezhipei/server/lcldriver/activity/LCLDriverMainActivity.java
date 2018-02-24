package com.yichan.gaotezhipei.server.lcldriver.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseListActivity;
import com.yichan.gaotezhipei.base.listener.OnItemSubviewClickListener;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.UserManager;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.home.HomeActivity;
import com.yichan.gaotezhipei.login.util.LoginManager;
import com.yichan.gaotezhipei.logistics.entity.LCLOrderPage;
import com.yichan.gaotezhipei.server.lcldriver.constant.LCLDriverConstants;
import com.yichan.gaotezhipei.server.lcldriver.view.NearbyCargoAdapter;

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
 * Created by ckerv on 2018/1/19.
 */

public class LCLDriverMainActivity extends BaseListActivity {


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.titlebar_btn_left)
    ImageButton mIbLeft;

    @BindView(R.id.titlebar_btn_right)
    ImageButton mIbRight;

    @BindView(R.id.view_no_data)
    View mViewNodata;

    private NearbyCargoAdapter mAdapter;

    private List<LCLOrderPage.BeanListBean> mBeanList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initTitleBar();
    }

    private void initTitleBar() {
        mTvTitle.setText("拼货司机");

        mIbLeft.setVisibility(View.VISIBLE);
        mIbLeft.setImageResource(R.drawable.logout);
        mIbRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_server_lcl_driver_main;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NearbyCargoAdapter(this, mBeanList);
        mAdapter.setOnItemSubviewClickListener(new OnItemSubviewClickListener<LCLOrderPage.BeanListBean>() {
            @Override
            public void onClick(View v, int pos, final LCLOrderPage.BeanListBean model) {
                switch (v.getId()) {
                    case R.id.nearby_cargo_rl_rob:
                        DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认接单吗？", new IDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {
                                if(result == -1) {
                                    handleOrder(1, model.getId());
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
        return mAdapter;
    }

    private void handleOrder(int status, String orderId) {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_DRIVER_UPDATE_ORDER_STATUS)
                .addParams("pdriverOrderId", orderId)
                .addParams("status", String.valueOf(status)).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("接单成功");
                    getDataList(1, true);
                } else {
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
        return R.id.lcl_driver_main_rv;
    }

    @Override
    protected void doLoreMore(int currentPage, int size) {
        getDataList(currentPage, false);
    }

    @Override
    protected void doRefresh(int currentPage, int size) {
        getDataList(currentPage, true);
    }

    @OnClick({R.id.lcl_driver_iv_find_cargo, R.id.lcl_driver_iv_myorder, R.id.lcl_driver_iv_driver_inform,R.id.view_no_data,R.id.titlebar_btn_left, R.id.titlebar_btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lcl_driver_iv_find_cargo:
                startActivity(new Intent(LCLDriverMainActivity.this, NearbyCargoActivity.class));
                break;
            case R.id.lcl_driver_iv_myorder:
                startActivity(new Intent(LCLDriverMainActivity.this, LCLDriverOrderActivity.class));
                break;
            case R.id.lcl_driver_iv_driver_inform:
                startActivity(new Intent(LCLDriverMainActivity.this, DriverInformActivity.class));
                break;
            case R.id.view_no_data:
                getDataList(1, true);
                break;
            case R.id.titlebar_btn_left:
                LoginManager.logout(this, false);
                break;
            case R.id.titlebar_btn_right:
                showChangeRoleDialog();
                break;
            default:
                break;
        }
    }

    private void showChangeRoleDialog() {
        View view = LayoutInflater.from(LCLDriverMainActivity.this).inflate(R.layout.dialog_change_role_has_permisson, null);
        ImageView ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        ivLeft.setImageResource(R.drawable.mine_driver);
        ImageView ivRight = (ImageView) view.findViewById(R.id.iv_right);
        ivRight.setImageResource(R.drawable.mine_demand);
        DialogHelper.showCustomDialog(view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeToDemander();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void changeToDemander() {
        UserManager.getInstance(LCLDriverMainActivity.this).setRoleType("2");
        startActivity(new Intent(LCLDriverMainActivity.this, HomeActivity.class));
        finish();
    }

    public void getDataList(int currentPage, final boolean isRefresh) {
        if(isRefresh) {
            mBeanList.clear();
        }
        PostFormBuilder postFormBuilder = new PostFormBuilder().url(AppConstants.BASE_URL + LCLDriverConstants.URL_GET_AVAILABLE_ORDER);
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
                        showToast("附近货源暂无可接单~");
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

    private void toggleNoDataView(boolean isShow) {
        if(isShow) {
            mViewNodata.setVisibility(View.VISIBLE);
            mMultiLayout.setVisibility(View.GONE);
        } else {
            mViewNodata.setVisibility(View.GONE);
            mMultiLayout.setVisibility(View.VISIBLE);
        }
    }
}
