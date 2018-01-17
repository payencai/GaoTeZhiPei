package com.yichan.gaotezhipei.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.mine.entity.AddressItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ckerv on 2018/1/12.
 */

public class AddressMangeActivity extends BaseActivity {

    public static final int REQUEST_CODE_ADD_ADDRESS = 1;

    @BindView(R.id.address_manage_rv_list)
    RecyclerView mRvList;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;


    private List<AddressItem> mAddressList;
    private AddressManageAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        mTvTitle.setText("地址管理");

        initAddressList();
        initRecyclerView();
    }

    private void initAddressList() {
        mAddressList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            mAddressList.add(new AddressItem("吴彦祖", "13249462956","云南省昆明市西山区前卫西路世纪半岛橄榄谷7栋7-6号", false));
        }
    }

    private void initRecyclerView() {
        mAdapter = new AddressManageAdapter(AddressMangeActivity.this, mAddressList);
        mRvList.setAdapter(mAdapter);
        mRvList.setLayoutManager(new LinearLayoutManager(AddressMangeActivity.this));
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
                    if(addressItem.isDefault()) {
                        setAllOriDatasNotDefault();
                    }
                    mAddressList.add(0, addressItem);
                    mAdapter.specialUpdate();
                }
            }
        }
    }

    private void setAllOriDatasNotDefault() {
        for(int i = 0; i < mAddressList.size(); i++) {
            mAddressList.get(i).setDefault(false);
        }
    }

    class AddressManageAdapter extends RecyclerView.Adapter<AddressManageAdapter.AddressManageViewHolder> {

        private Context mContext;
        private List<AddressItem> mList;

        public AddressManageAdapter(Context context, List<AddressItem> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override
        public AddressManageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
            return new AddressManageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AddressManageViewHolder holder, final int position) {
            holder.nameTv.setText(mList.get(position).getName());
            holder.phoneTv.setText(mList.get(position).getPhone());
            holder.addressTv.setText(mList.get(position).getDetailAddr());
            holder.defaultCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        holder.defaultTv.setText("默认地址");
                        holder.defaultTv.setTextColor(Color.parseColor("#00c1de"));
                        setAllOriDatasNotDefault();
                        mList.get(position).setDefault(true);
                        if (mRvList.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (mRvList.isComputingLayout() == false)) {
                            specialUpdate();
                        }
                    } else {
                        holder.defaultTv.setText("设为默认");
                        holder.defaultTv.setTextColor(Color.BLACK);
                    }
                }
            });
            holder.defaultCb.setChecked(mList.get(position).isDefault());
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

            public AddressManageViewHolder(View itemView) {
                super(itemView);

                nameTv = (TextView) itemView.findViewById(R.id.item_address_tv_name);
                phoneTv = (TextView) itemView.findViewById(R.id.item_address_tv_phone);
                addressTv = (TextView) itemView.findViewById(R.id.item_address_tv_address);
                defaultCb = (CheckBox) itemView.findViewById(R.id.item_address_cb_default);
                defaultTv = (TextView) itemView.findViewById(R.id.item_address_tv_default);
            }
        }
    }
}
