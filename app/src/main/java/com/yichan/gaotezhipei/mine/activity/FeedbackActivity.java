package com.yichan.gaotezhipei.mine.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.view.recycler.MSClickableAdapter;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;
import com.ckev.chooseimagelibrary.base.img.view.ChooseImageActivity;
import com.ckev.chooseimagelibrary.base.img.view.ImageDetailActivity;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.base.view.KeyRadioGroupV1;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.view.FeedbackDisplayImageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/17.
 */

public class FeedbackActivity extends BaseActivity implements OnImageSelectedFinishedListener, OnImageSelectedChangeListener {


    @BindView(R.id.feed_back_rv_display)
    RecyclerView mRvDisplay;

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.rg_type)
    KeyRadioGroupV1 mRgType;
    @BindView(R.id.et_contact)
    EditText mEtContact;

    private FeedbackDisplayImageAdapter mAdapter;

    private List<String> mDatas;

    private DialogFragment mProgerssDialog;

    private String mImageKey;

    private String mImagePath;

    private String mFeedBackType;


    /**
     * 可选择的图片数量
     */
    private int mSelectNum;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initVariables();
        initRadioGroup();
        initDisplayImageViews();
    }

    private void initRadioGroup() {
        mRgType.setOnCheckedChangeListener(new KeyRadioGroupV1.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(KeyRadioGroupV1 group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        mFeedBackType = "程序bug";
                        break;
                    case R.id.rb_2:
                        mFeedBackType = "功能建议";
                        break;
                    case R.id.rb_3:
                        mFeedBackType = "内容意见";
                        break;
                    case R.id.rb_4:
                        mFeedBackType = "广告问题";
                        break;
                    case R.id.rb_5:
                        mFeedBackType = "网络问题";
                        break;
                    case R.id.rb_6:
                        mFeedBackType = "其他";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initVariables() {
        mSelectNum = 1;
        mDatas = new ArrayList<>();
        mDatas.add("android.resource://com.yichan.gaotezhipei/drawable/mine_add");
    }

    private void initDisplayImageViews() {
        mAdapter = new FeedbackDisplayImageAdapter(this, R.layout.item_feed_back_display_img, mDatas, mSelectNum);
        mRvDisplay.setLayoutManager(new GridLayoutManager(this, 4));
        mRvDisplay.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MSClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.get(position).equals("android.resource://com.yichan.gaotezhipei/drawable/mine_add")) {
                    ChooseImageActivity.startActivity(FeedbackActivity.this, mSelectNum);
                } else {
                    ImageDetailActivity.startActvitiy(FeedbackActivity.this, mDatas.get(position));
                }
            }
        });
    }

    private void initTitleBar() {
        mTvTitle.setText("意见反馈");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.btn_commit:
                postForms();
                break;
            default:
                break;
        }
    }

    private void postForms() {
        if(!checkForms()) {
            return;
        }
        startToUpload();
    }

    private boolean checkForms() {
        if(TextUtils.isEmpty(mEtComment.getText().toString())
                || mImagePath == null
                || mFeedBackType == null
                || TextUtils.isEmpty(mEtContact.getText().toString())) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else {
            return true;
        }
    }

    public void startToUpload() {
        if(mProgerssDialog != null) {
            mProgerssDialog.dismiss();
        }
        mProgerssDialog = DialogHelper.showProgress(getSupportFragmentManager(), "上传信息中...", false);
        uploadImageView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onFinish(List<String> selectedImages) {

        mDatas.addAll(0, selectedImages);
        //处理已选择的图片数量超过可选择图片数量的逻辑
        if(mDatas.size() - 1 >= mSelectNum) {
            for(int i = mDatas.size() - 1; i >= mSelectNum; i--)
                mDatas.remove(i);
        }
        mRvDisplay.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onImageSelectedChange(List<String> selectedImages) {
        mImagePath = selectedImages.get(0);
    }


    private void uploadImageView() {
        File file = new File(mImagePath);
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + MineConstants.URL_UPLOAD_IMAGE)
                .addFile("image", "img.png", file).build();
        call.doScene(new TokenSceneCallback<String>(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                mProgerssDialog.dismiss();
            }

            @Override
            protected void handleResponse(Result<String> response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    mImageKey = response.getData();
                    commitAllParams();
                } else {
                    showToast((response.getMessage() == null ? "图片上传失败" : response.getMessage()));
                    if(mProgerssDialog != null) {
                        mProgerssDialog.dismiss();
                    }
                }

            }

        });
    }

    private void commitAllParams() {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + MineConstants.URL_FEEDBACK_APPLY)
                .addParams("type", mFeedBackType)
                .addParams("suggestion", mEtComment.getText().toString())
                .addParams("contactWay", mEtContact.getText().toString())
                .addParams("picKey", mImageKey)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                if(mProgerssDialog != null) {
                    mProgerssDialog.dismiss();
                }
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("反馈成功");
                    if(mProgerssDialog != null) {
                        mProgerssDialog.dismiss();
                    }
                    finish();
                } else {
                    if(mProgerssDialog != null) {
                        mProgerssDialog.dismiss();
                    }
                    showToast(response.getMessage());
                }
            }


        });
    }
}
