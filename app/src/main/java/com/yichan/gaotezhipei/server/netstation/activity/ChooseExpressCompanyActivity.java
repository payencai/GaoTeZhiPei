package com.yichan.gaotezhipei.server.netstation.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.request.GetRequest;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.GaoteApplication;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.dao.ExpressCompanyDao;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressCompanyItem;
import com.yichan.gaotezhipei.server.netstation.event.ChooseExpressCompanyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ittiger.indexlist.IndexStickyView;
import cn.ittiger.indexlist.adapter.IndexStickyViewAdapter;
import cn.ittiger.indexlist.listener.OnItemClickListener;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/3/7.
 */

public class ChooseExpressCompanyActivity extends BaseActivity {


    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.indexStickyView)
    IndexStickyView indexStickyView;


    private List<ExpressCompanyItem> mDataList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mTvTitle.setText("选择快递公司");
        initDataList();
    }

    private void initDataList() {
        /*
        GetRequest getRequest = new GetRequest(AppConstants.BASE_URL + "/logisticsCompany/getAll", null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<List<ExpressCompanyItem>>(call) {



            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result<List<ExpressCompanyItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {

                    if(response.getData() != null && response.getData().size() != 0) {
                        mDataList.clear();
                        mDataList.addAll(response.getData());
                        initIndexViews();
                    } else {
                        showToast("获取数据失败，请重新再试。");
                    }

                } else {
                    showToast(response.getMessage());
                }
            }

            @Override
            public Result<List<ExpressCompanyItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), ExpressCompanyItem.class);
            }
        });*/

//        ExpressCompanyItem expressCompanyItem = new ExpressCompanyItem();
//        expressCompanyItem.setId("1");
//        expressCompanyItem.setName("");
//        expressCompanyItem.setIsCancel("0");
//        mDataList.add(expressCompanyItem);
        ExpressCompanyDao dao = new ExpressCompanyDao(GaoteApplication.mApplicationContext);
        mDataList = dao.findList();
        initIndexViews();
    }

    private void initIndexViews() {
        MyIndexStickyViewAdapter adapter = new MyIndexStickyViewAdapter(mDataList);
        indexStickyView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener<ExpressCompanyItem>() {
            @Override
            public void onItemClick(View childView, int position, ExpressCompanyItem item) {
                EventBus.getInstance().post(new ChooseExpressCompanyEvent(item.getName(),item.getCode()));
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_express_company;
    }

    class MyIndexStickyViewAdapter extends IndexStickyViewAdapter<ExpressCompanyItem> {

        public MyIndexStickyViewAdapter(List<ExpressCompanyItem> list) {

            super(list);
        }

        @Override
        public RecyclerView.ViewHolder onCreateIndexViewHolder(ViewGroup parent) {

            View view = LayoutInflater.from(ChooseExpressCompanyActivity.this).inflate(R.layout.item_index, parent, false);
            return new IndexViewHolder(view);
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {

            View view = LayoutInflater.from(ChooseExpressCompanyActivity.this).inflate(R.layout.item_express_company, parent, false);
            return new ContentViewHolder(view);
        }

        @Override
        public void onBindIndexViewHolder(RecyclerView.ViewHolder holder, int position, String indexName) {

            IndexViewHolder indexViewHolder = (IndexViewHolder) holder;
            indexViewHolder.mTextView.setText(indexName);
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position, ExpressCompanyItem itemData) {

            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.mName.setText(itemData.getName());
            CommonImageLoader.displayImage(itemData.getPicUrl(), contentViewHolder.mAvatar, CommonImageLoader.NO_CACHE_OPTIONS);
        }
    }

    class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public IndexViewHolder(View itemView) {

            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        ImageView mAvatar;

        public ContentViewHolder(View itemView) {

            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }

    @OnClick({R.id.titlebar_btn_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            default:
                break;
        }
    }
}
