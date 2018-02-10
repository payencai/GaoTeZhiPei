package com.yichan.gaotezhipei.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changelcai.mothership.component.fragment.dialog.IDialogResultListener;
import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.entity.AddressItem;
import com.yichan.gaotezhipei.mine.event.ChooseAddressEvent;
import com.yichan.gaotezhipei.mine.event.NotifyAddressEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AddressMangeActivity extends BaseActivity {

    public static final int REQUEST_CODE_ADD_ADDRESS = 1;

    @BindView(R.id.address_manage_rv_list)
    RecyclerView mRvList;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.address_manage_no_data)
    View mViewNodata;

    private boolean isNeedRefresh = false;

    private int lastPosition = 0;//位置
    private int lastOffset = 0;//偏移量


    private List<AddressItem> mAddressList = new ArrayList<>();
    private AddressManageAdapter mAdapter;

    private DialogFragment mProgressFragment;

    private int mStartType = 0;//1为普通，2位
    public static final int START_TYPE_NORMAL = 0;



    public static void  startActivity(Context context, int startType) {
        Intent intent = new Intent(context, AddressMangeActivity.class);
        intent.putExtra("startType", startType);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
        mStartType = getIntent().getIntExtra("startType", START_TYPE_NORMAL);
        mTvTitle.setText("地址管理");
        initAddressList();
        initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    private void initAddressList() {
        getAddressList();
    }

    private void getAddressList() {
        mAddressList.clear();
        if(mProgressFragment != null) {
            mProgressFragment.dismiss();
        }
        mProgressFragment = DialogHelper.showProgress(getSupportFragmentManager(), "正在获取收货地址...");
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + MineConstants.URL_GET_ALL_ADDRESS)
                .build();
        call.doScene(new TokenSceneCallback<List<AddressItem>>(call) {

            @Override
            public Result<List<AddressItem>> parseNetworkResponse(Response response) throws IOException {
                return GsonUtil.fromJsonArray(response.body().string(), AddressItem.class);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                mViewNodata.setVisibility(View.VISIBLE);
                mRvList.setVisibility(View.GONE);
                mProgressFragment.dismiss();
            }

            @Override
            protected void handleResponse(Result<List<AddressItem>> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null && response.getData().size() != 0) {
                        mViewNodata.setVisibility(View.GONE);
                        mRvList.setVisibility(View.VISIBLE);
                        mAddressList.addAll(response.getData());
                        initRecyclerView();
                        ((LinearLayoutManager)mRvList.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
                    } else {
                        mViewNodata.setVisibility(View.VISIBLE);
                        mRvList.setVisibility(View.GONE);
                    }
                } else {
                    showToast(response.getMessage());
                    mViewNodata.setVisibility(View.VISIBLE);
                    mRvList.setVisibility(View.GONE);
                }
                mProgressFragment.dismiss();
            }


        });
    }

    private void initRecyclerView() {
        mAdapter = new AddressManageAdapter(AddressMangeActivity.this, mAddressList);
        mRvList.setAdapter(mAdapter);
        mRvList.setLayoutManager(new LinearLayoutManager(AddressMangeActivity.this));
        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                View topView = mRvList.getLayoutManager().getChildAt(0); //获取可视的第一个view
                lastOffset = topView.getTop(); //获取与该view的顶部的偏移量
                lastPosition = mRvList.getLayoutManager().getPosition(topView);  //得到该View的数组位置
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        if(mStartType != START_TYPE_NORMAL) {
            mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    EventBus.getInstance().post(new ChooseAddressEvent(mStartType, mAddressList.get(position)));
                    finish();
                }
            });
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_address_manage;
    }

    @OnClick({R.id.titlebar_btn_left, R.id.address_manage_btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.address_manage_btn_add:
                startActivityForResult(new Intent(AddressMangeActivity.this, AddAddressActivity.class), REQUEST_CODE_ADD_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_ADDRESS) {
            if(resultCode == AddAddressActivity.RESULT_CODE_ADD) {
                AddressItem addressItem = (AddressItem) data.getSerializableExtra("new_address");
                if (addressItem != null) {
//                    if(addressItem.isDefault()) {
//                        setAllOriDatasNotDefault();
//                    }
                    mAddressList.add(0, addressItem);
                    mAdapter.specialUpdate();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(NotifyAddressEvent event) {
        isNeedRefresh = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeedRefresh) {
            getAddressList();
            isNeedRefresh = false;
        }
    }

    private void setAllOriDatasNotDefault() {
        for(int i = 0; i < mAddressList.size(); i++) {
            mAddressList.get(i).setDefaultAddress(2);
        }
    }

    private void updateAddressOnlyDefault(AddressItem addressItem) {
        if(mProgressFragment != null) {
            mProgressFragment.dismiss();
        }
        mProgressFragment = DialogHelper.showProgress(getSupportFragmentManager(), "正在修改收货地址...");
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + MineConstants.URL_UPDATE_ADDRESS)
                .addParams("id", addressItem.getId())
                .addParams("defaultAddress", String.valueOf(addressItem.getDefaultAddress()))
                .build();
        call.doScene(new TokenSceneCallback(call) {

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                mProgressFragment.dismiss();
            }

            @Override
            protected void handleResponse(Result response) {
                if (response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("更新收货地址成功");
                } else {
                    showToast(response.getMessage());
                }
                mProgressFragment.dismiss();
            }


        });

    }

    private void tryToDeleteAddress(AddressItem addressItem) {
        RequestCall call = new PostFormBuilder().url(AppConstants.BASE_URL + MineConstants.URL_DELETE_ADDRESS)
                .addParams("id", addressItem.getId()).build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("删除收货地址成功");
                    getAddressList();
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    class AddressManageAdapter extends MSClickableAdapter<AddressManageAdapter.AddressManageViewHolder> {

        private Context mContext;
        private List<AddressItem> mList;

        public AddressManageAdapter(Context context, List<AddressItem> list) {
            this.mContext = context;
            this.mList = list;
        }


        @Override
        public AddressManageViewHolder onCreateVH(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
            return new AddressManageViewHolder(itemView);
        }


        @Override
        public void onBindVH(final AddressManageViewHolder holder, final int position) {
            holder.nameTv.setText(mList.get(position).getName());
            holder.phoneTv.setText(mList.get(position).getTelephone());
            holder.addressTv.setText(mList.get(position).getAddress());

            holder.defaultCb.setOnCheckedChangeListener(null);

            if(mList.get(position).getDefaultAddress() == 1) {
                holder.defaultTv.setText("默认地址");
                holder.defaultTv.setTextColor(Color.parseColor("#00c1de"));
                holder.defaultCb.setChecked(true);
            } else {
                holder.defaultTv.setText("设为默认");
                holder.defaultTv.setTextColor(Color.BLACK);
                holder.defaultCb.setChecked(false);
            }

            holder.defaultCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        holder.defaultTv.setText("默认地址");
                        holder.defaultTv.setTextColor(Color.parseColor("#00c1de"));
                        setAllOriDatasNotDefault();
                        mList.get(position).setDefaultAddress(1);
                        if (mRvList.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (mRvList.isComputingLayout() == false)) {
                            specialUpdate();
                        }
                    } else {
                        mList.get(position).setDefaultAddress(2);
                        holder.defaultTv.setText("设为默认");
                        holder.defaultTv.setTextColor(Color.BLACK);
                    }
                    updateAddressOnlyDefault(mList.get(position));
                }
            });

            holder.editLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddressMangeActivity.this, AddAddressActivity.class);
                    intent.putExtra(AddAddressActivity.STRAT_TYPE, AddAddressActivity.TYPE_EDIT);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressItem", mList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            holder.deleteLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.showConfirmDailog(getSupportFragmentManager(), "您确认删除改收货地址吗？", new IDialogResultListener<Integer>() {
                        @Override
                        public void onDataResult(Integer result) {
                            if(result == -1) {
                                tryToDeleteAddress(mAddressList.get(position));
                            } else {
                            }
                        }
                    });
                }
            });
        }

        public void specialUpdate() {
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    notifyDataSetChanged();
                }
            };
            handler.post(r);
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }



        class AddressManageViewHolder extends RecyclerView.ViewHolder {

            TextView nameTv;
            TextView phoneTv;
            TextView addressTv;
            CheckBox defaultCb;
            TextView defaultTv;

            LinearLayout editLl;
            LinearLayout deleteLl;

            public AddressManageViewHolder(View itemView) {
                super(itemView);

                nameTv = (TextView) itemView.findViewById(R.id.item_address_tv_name);
                phoneTv = (TextView) itemView.findViewById(R.id.item_address_tv_phone);
                addressTv = (TextView) itemView.findViewById(R.id.item_address_tv_address);
                defaultCb = (CheckBox) itemView.findViewById(R.id.item_address_cb_default);
                defaultTv = (TextView) itemView.findViewById(R.id.item_address_tv_default);
                editLl = (LinearLayout) itemView.findViewById(R.id.item_address_ll_edit);
                deleteLl = (LinearLayout) itemView.findViewById(R.id.item_address_ll_delete);
            }
        }
    }


}
