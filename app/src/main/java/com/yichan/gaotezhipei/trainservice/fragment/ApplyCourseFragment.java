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
import com.yichan.gaotezhipei.trainservice.entity.OfflineCoursePage;
import com.yichan.gaotezhipei.trainservice.view.OfflineCourseAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/20.
 */

public class ApplyCourseFragment extends CommonLinearListFragment {
    private List<OfflineCoursePage.BeanListBean> mList = new ArrayList<>();
    private OfflineCourseAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mStartPageNum = 1;
        mSize = 8;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new OfflineCourseAdapter(mList, getActivity());
        return mAdapter;
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
            mList.clear();
        }

        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + TrainServiceConstants.URL_GET_OFFLINE_CLASS
        )
                .addParams("page", String.valueOf(currentPage))
                .build();
        call.doScene(new TokenSceneCallback<OfflineCoursePage>(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<OfflineCoursePage> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    if(response.getData().getBeanList() != null) {
                        mList.addAll(response.getData().getBeanList());
                    }

                    if(mList.size() == 0) {
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
                        .beginSubType(OfflineCoursePage.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }
        });
    }
}
