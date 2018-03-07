package com.yichan.gaotezhipei.trainservice.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.common.view.CommonLinearListFragment;
import com.yichan.gaotezhipei.trainservice.constants.TrainServiceConstants;
import com.yichan.gaotezhipei.trainservice.entity.ConsultPage;
import com.yichan.gaotezhipei.trainservice.view.ConsultListAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/7.
 */

public class ConsultFragment extends CommonLinearListFragment<ConsultPage.BeanListBean>{

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 8;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        ConsultListAdapter adapter = new ConsultListAdapter(getContext(), getItemList());
//        adapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                showToast("..." + getItemList().get(position));
//            }
//        });
        return adapter;
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
        if (isRefresh) {
            getItemList().clear();
        }

        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + TrainServiceConstants.URL_GET_CONSULT_LIST)
                .addParams("page", String.valueOf(currentPage))
                .build();
        call.doScene(new TokenSceneCallback<ConsultPage>(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<ConsultPage> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    if(response.getData().getBeanList() != null) {
                        getItemList().addAll(response.getData().getBeanList());
                    }

                    if(getItemList().size() == 0) {
                        toggleNodataView(true);
                    } else {
                        toggleNodataView(false);
                        getAdapter().notifyDataSetChanged();
                    }

                    if(isRefresh) {
                        doRefreshFinish(response.getData().getBeanList().size());
                    } else {
                        doLoadMoreFinish(response.getData().getBeanList().size());
                    }


                } else {
                    showToast(response.getMessage());
                }
            }

            @Override
            public Result parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(ConsultPage.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }
        });
    }

}
