package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.changelcai.mothership.network.request.GetRequest;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.BottomMenuDialog;
import com.yichan.gaotezhipei.common.util.GsonUtil;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.entity.DemanderInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ikidou.reflect.TypeBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/10 0010.
 */

public class PersonalProfileActivity extends ProfileActivity {
    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;
    @BindView(R.id.imgPerson)
    CircleImageView imgPerson;
    @BindView(R.id.tvNickName)
    TextView mTvNickName;
    @BindView(R.id.tvGender)
    TextView mTvGender;

    private DemanderInfo mDemanderInfo;
    private Bitmap mBitmap;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initData2();
    }

    private void initTitleBar() {
        mTvTitle.setText("个人资料");
    }

    @OnClick({R.id.titlebar_btn_left, R.id.imgPerson,R.id.imgEnter,R.id.tvNickName,R.id.imgNickNameEnter,R.id.tvGender,R.id.imgGenderEnter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.imgPerson:
            case R.id.imgEnter:
                alertPicPanel(1);
                break;
            case R.id.tvNickName:
            case R.id.imgNickNameEnter:
                //set page
                Intent intent = new Intent();
                intent.putExtra("nick", mTvNickName.getText().toString());
                intent.setClass(PersonalProfileActivity.this, PersonalNickSetActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tvGender:
            case R.id.imgGenderEnter:
                alertPicPanel(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                String nickName = data.getExtras().getString("nick");
                Log.d("TAG", ">>>>>>nick:" + nickName);
                //mTvNickName.setText(nickName);
                setNickName(nickName);
            }
        }
    }

    @Override
    protected void buildMenu(BottomMenuDialog.Builder builder, int type) {
        switch (type){
            case 2:
                items1 = new String[] { "男", "女"  };
                builder.setTitle("");
                builder.addMenu(items1[0], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        // set "男"
                        setGender(1);
                    }
                });
                builder.addMenu(items1[1], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        setGender(2);
                    }
                });
                break;

        }
    }

    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
    }

    protected void initData2() {
        String url = AppConstants.BASE_URL + MineConstants.URL_GET_INFO;
        GetRequest getRequest = new GetRequest(url, null, null, null);
        RequestCall call = getRequest.build();
        call.doScene(new TokenSceneCallback<DemanderInfo>(call) {
            @Override
            public Result<DemanderInfo> parseNetworkResponse(Response response) throws IOException {
                Type type = TypeBuilder
                        .newInstance(Result.class)
                        .beginSubType(DemanderInfo.class)
                        .endSubType().build();
                return GsonUtil.gsonToBean(response.body().string(), type);
            }

            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    if(response.getData() != null) {
                        mDemanderInfo = (DemanderInfo)response.getData();
                        Log.d("TAG", ">>>>>>GET:" + mDemanderInfo.getPortraint());
                        //GetBitmapTools.getBitmap(ctx,demanderInfo.getPortraint(),imgPerson,R.drawable.default_icon,R.drawable.default_icon);
                        CommonImageLoader.displayImage(mDemanderInfo.getPortraint(),imgPerson, CommonImageLoader.NO_CACHE_OPTIONS);
                        mTvNickName.setText(mDemanderInfo.getName());
                        mTvGender.setText("1".equals(mDemanderInfo.getSex()) ? "男" : "女");
                    }
                } else {
                    showToast(response.getMessage());
                }
            }


        });

    }

    @Override
    protected void reloadProfile(Bitmap bm) {
//        if(mBitmap != null)
//            mBitmap.recycle();
//        mBitmap = bm.copy(Bitmap.Config.RGB_565, true);
        Log.d("TAG", ">>>>>>reloadProfile BEGIN");
        imgPerson.setImageBitmap(bm);
        Log.d("TAG", ">>>>>>reloadProfile END");
    }

    @Override
    protected void picSelectUpload(String fileName) {

            RequestCall call = new PostFormBuilder()
                    .url(AppConstants.BASE_URL + MineConstants.URL_UPLOAD_IMAGE)
                    .addFile("image", fileName, new File(fileName))
                    .build();
            call.doScene(new TokenSceneCallback(call) {
                @Override
                protected void handleError(String errorMsg, Call call, Exception e) {
                    showToast(errorMsg);
                }

                @Override
                protected void handleResponse(Result response) {
                    if(response.getResultCode() == Result.SUCCESS_CODE) {
                        showToast("上传成功。");
                        Log.d("TAG", ">>>>>>GET:" + response.getData());
                        mDemanderInfo.setPortraint(response.getData().toString());
                        setProfile();
                    } else {
                        showToast(response.getMessage());
                    }
                }


            });

    }

    private void setProfile() {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + MineConstants.URL_UPLOAD_PORTRAINT)
                .addParams("portraintKey", mDemanderInfo.getPortraint())
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("头像已更新。");
                    initData2();
//                    imgPerson.setImageBitmap(mBitmap);
//                    mBitmap.recycle();
//                    mBitmap = null;
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void setGender(final int type) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + MineConstants.URL_UPDATE_SEX)
                .addParams("sex", String.valueOf(type))
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("性别已更新。");
                    mTvGender.setText(items1[type-1]);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }

    private void setNickName(final String name) {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + MineConstants.URL_UPDATE_NAME)
                .addParams("name", name)
                .build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE) {
                    showToast("昵称已更新。");
                    mTvNickName.setText(name);
                } else {
                    showToast(response.getMessage());
                }
            }


        });
    }
}
