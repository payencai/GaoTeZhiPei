package com.yichan.gaotezhipei.mine.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.yichan.gaotezhipei.BuildConfig;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.BitmapUtil;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.base.util.FileUitlity;
import com.yichan.gaotezhipei.base.util.PhotoUtils;
import com.yichan.gaotezhipei.base.view.BottomMenuDialog;
import com.yichan.gaotezhipei.base.view.StepView;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EventBus;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.mine.constant.MineConstants;
import com.yichan.gaotezhipei.mine.event.ChooseCarTypeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/14.
 */

public class BecomeLCLDriverActivity extends BaseActivity implements PermissionUtils.ApplyPermission {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.lcl_driver_step_view)
    StepView mStepView;

    @BindView(R.id.lcl_driver_vg_step1)
    ViewGroup mVgStep1;

    @BindView(R.id.lcl_driver_vg_step2)
    ViewGroup mVgStep2;

    @BindView(R.id.lcl_driver_vg_step3)
    ViewGroup mVgStep3;

    @BindView(R.id.lcl_driver_btn_operate)
    Button mBtnOperate;

    private static final int IMAGE_REQUEST_CODE = 20000;
    private static final int CAMERA_REQUEST_CODE = 1999;
    private static final int RESULT_REQUEST_CODE = 299;



    //第一步
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;

    //第二步
    @BindView(R.id.tv_car_color)
    TextView mTvCarColor;
    @BindView(R.id.et_car_number)
    EditText mEtCarNum;
    @BindView(R.id.tv_car_type)
    TextView mTvCarType;

    //第三步
    @BindView(R.id.iv_img1)
    ImageView ivImg1;
    @BindView(R.id.iv_img2)
    ImageView ivImg2;
    @BindView(R.id.iv_img3)
    ImageView ivImg3;
    @BindView(R.id.iv_img4)
    ImageView ivImg4;

    private BottomMenuDialog bottomDialog;



    private String mStrCarColor;
    private String mStrCarType;
    private String mStrCarSize;
    private String mStrCarWeight;
    private String mStrCarVolume;

    private String photoPath;

    Uri cropImageUri;



    private int mCurrentStep = 1;

    private int currentIvIndex = 1;

    private HashMap<Integer,Bitmap> mImgToUpload = new HashMap<>();

    private HashMap<Integer,String> mKeyMaps = new HashMap<>();

    private DialogFragment mProgerssDialog;


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        EventBus.getInstance().register(this);
        initTitleBar();
        initStepView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);

    }

    private void initTitleBar() {
        mTvTitle.setText("实人同行证");
    }

    private void initStepView() {
        List<String> steps = Arrays.asList(new String[]{"个人信息", "车辆信息", "照片信息"});
        mStepView.setSteps(steps);
        mStepView.selectedStep(mCurrentStep);
    }

    @OnClick({R.id.titlebar_btn_left, R.id.lcl_driver_btn_operate, R.id.step2_rl_choose_car_type, R.id.step2_rl_choose_car_plate_color,R.id.rl_upload1,R.id.rl_upload2,R.id.rl_upload3,R.id.rl_upload4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.lcl_driver_btn_operate:
                if(mCurrentStep < 3) {
                    showNextStep();
                } else {
                    startToUpload();
                }
                break;
            case R.id.step2_rl_choose_car_type:
                startActivity(new Intent(BecomeLCLDriverActivity.this, ChooseCarTypeActivity.class));
                break;
            case R.id.step2_rl_choose_car_plate_color:
                showChooseCarPlateColorDialog();
                break;
            case R.id.rl_upload1:
                currentIvIndex = 1;
                alertPicPanel();
                break;
            case R.id.rl_upload2:
                currentIvIndex = 2;
                alertPicPanel();
                break;
            case R.id.rl_upload3:
                currentIvIndex = 3;
                alertPicPanel();
                break;
            case R.id.rl_upload4:
                currentIvIndex = 4;
                alertPicPanel();
                break;
            default:
                break;
        }
    }

    private void showChooseCarPlateColorDialog() {
        View view = View
                .inflate(BecomeLCLDriverActivity.this, R.layout.dialog_choose_car_plate_color, null);
        final Dialog dialog = DialogHelper.showCustomDialog(view, true);
        view.findViewById(R.id.dialog_iv_bluesign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrCarColor = "蓝牌";
                dialog.dismiss();
                mTvCarColor.setTextColor(Color.parseColor("#333333"));
                mTvCarColor.setText(mStrCarColor);
            }
        });
        view.findViewById(R.id.dialog_iv_yellowsign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrCarColor = "黄牌";
                dialog.dismiss();
                mTvCarColor.setTextColor(Color.parseColor("#333333"));
                mTvCarColor.setText(mStrCarColor);
            }
        });
        view.findViewById(R.id.dialog_iv_greensign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrCarColor = "绿牌";
                dialog.dismiss();
                mTvCarColor.setTextColor(Color.parseColor("#333333"));
                mTvCarColor.setText(mStrCarColor);
            }
        });
        view.findViewById(R.id.dialog_iv_yellowgreensign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrCarColor = "黄绿牌";
                dialog.dismiss();
                mTvCarColor.setTextColor(Color.parseColor("#333333"));
                mTvCarColor.setText(mStrCarColor);
            }
        });
    }


    private void showNextStep() {
        if(!checkCanShowNextStep(mCurrentStep)) {
            return;
        }
        mCurrentStep++;
        switch (mCurrentStep) {
            case 2:
                mVgStep1.setVisibility(View.GONE);
                mVgStep2.setVisibility(View.VISIBLE);
                mStepView.selectedStep(mCurrentStep);
                break;
            case 3:
                mVgStep2.setVisibility(View.GONE);
                mVgStep3.setVisibility(View.VISIBLE);
                mBtnOperate.setText("确认上传");
                mStepView.selectedStep(mCurrentStep);
            default:
                break;
        }
    }

    private boolean checkCanShowNextStep(int currentStep) {
        if(currentStep == 1) {
            if(TextUtils.isEmpty(mEtName.getText().toString())
                    || TextUtils.isEmpty(mEtPhone.getText().toString())
                    || TextUtils.isEmpty(mEtIdCard.getText().toString())) {
                showToast("信息填写不完整，请检查。");
                return false;
            }
            return true;
        } else if(currentStep == 2) {
            if(TextUtils.isEmpty(mStrCarColor)
                    || TextUtils.isEmpty(mEtCarNum.getText().toString())
                    || TextUtils.isEmpty(mStrCarType)) {
                showToast("信息填写不完整，请检查。");
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_become_lcl_driver;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveChooseCarType(ChooseCarTypeEvent chooseCarTypeEvent) {
        mStrCarType = chooseCarTypeEvent.carType;
        mStrCarSize = chooseCarTypeEvent.carSize;
        mStrCarWeight = chooseCarTypeEvent.carWeight;
        mStrCarVolume = chooseCarTypeEvent.carVolume;
        mTvCarType.setTextColor(Color.parseColor("#333333"));
        mTvCarType.setText(mStrCarType);

    }


    private void alertPicPanel() {
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(this);
        builder.setTitle("上传头像");
        builder.addMenu("选择本地照片", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                //本地选择
                picSelect();
            }
        });
        builder.addMenu("拍照", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();

                requestPermission();
            }
        });

        bottomDialog = builder.create();
        bottomDialog.show();
    }

    private void requestPermission() {
        PermissionUtils.setApplyPermission(this);
        PermissionUtils.needPermission(BecomeLCLDriverActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
    }




    //本地选择
    private void picSelect() {
        Intent intentG = new Intent(Intent.ACTION_PICK);
        intentG.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentG, IMAGE_REQUEST_CODE);
    }

    //拍照
    private void picCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // 图片存储的上级目录
        File parent = FileUitlity.getInstance(this).makeDir("gaote");
        // 判断有无SD卡
        photoPath = parent.getPath() + File.separator + System.currentTimeMillis() + ".jpeg";
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));// 将照片输出为
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(BecomeLCLDriverActivity.this, "com.yichan.gaotezhipei.fileprovider", new File(photoPath)));// 将照片输出为
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE://本地相册选择图片
                    if (data != null) {
                        Uri uri = data.getData();
                        if (!TextUtils.isEmpty(uri.getAuthority())) {
                            Cursor cursor = getContentResolver().query(uri,
                                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                            if (null == cursor) {
                                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            cursor.moveToFirst();
                            photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                            cursor.close();
                        } else {
                            photoPath = uri.getPath();
                        }
                        cropImageUri = Uri.fromFile(new File(photoPath));
                        if(photoPath.startsWith("/"))
                            photoPath = "file://" + photoPath;
                        startPhotoZoom(Uri.parse(photoPath));
                    } else {
                        Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        if(cropImageUri != null) {
                            Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                            setBitmapToImageView(bitmap);
                        }
                    }
                    break;
            }
        } else {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE://拍照
                    if (com.yichan.gaotezhipei.base.util.FileUtil.isSDCardExist()) {
                        cropImageUri = Uri.fromFile(new File(photoPath));
                        Uri newUri = Uri.parse(PhotoUtils.getPath(BecomeLCLDriverActivity.this, cropImageUri));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, 300, 300, 299);
                        // 读取uri所在的图片
                    } else {
                        Toast.makeText(BecomeLCLDriverActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    }

    /**
     * 保存裁剪之后的图片数据
     */
    @SuppressWarnings("deprecation")
    private void setBitmapToImageView(Bitmap bm) {
        switch (currentIvIndex) {
            case 1:
                ivImg1.setImageBitmap(bm);
                mImgToUpload.put(0, bm);
                break;
            case 2:
                ivImg2.setImageBitmap(bm);
                mImgToUpload.put(1, bm);
                break;
            case 3:
                ivImg3.setImageBitmap(bm);
                mImgToUpload.put(2, bm);
                break;
            case 4:
                ivImg4.setImageBitmap(bm);
                mImgToUpload.put(3, bm);
                break;
            default:
                break;
        }
    }

    public void startToUpload() {
        if(mImgToUpload.size() != 4) {
            showToast("图片不全");
            return;
        }
        if(mProgerssDialog != null) {
            mProgerssDialog.dismiss();
        }
        mProgerssDialog = DialogHelper.showProgress(getSupportFragmentManager(), "上传信息中...", false);
        uploadImageView(0);
    }

    private void uploadImageView(final int index) {
        Bitmap compressedBm = BitmapUtil.compressImage(mImgToUpload.get(index));//压缩
        File file = BitmapUtil.saveBitmapToFile(BecomeLCLDriverActivity.this, compressedBm, mImgToUpload.get(index).toString());//转文件
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
                if(response.getData() != null) {
                    mKeyMaps.put(index, response.getData());
                    if(mKeyMaps.size() == 4) {
                        commitAllInform();
                    } else if(mKeyMaps.size() < 4) {
                        int nextIndex = index + 1;
                        uploadImageView(nextIndex);
                    }
                } else {
                    showToast((response.getMessage() == null ? "图片上传失败" : response.getMessage()));
                }
            }

        });
    }

    private void commitAllInform() {
        PostFormBuilder postFormBuilder = new PostFormBuilder().url(AppConstants.BASE_URL + MineConstants.URL_COMMIT_LCL_DRIVER_INFORM);
        postFormBuilder.addParams("name", mEtName.getText().toString());
        postFormBuilder.addParams("idcardNo", mEtIdCard.getText().toString());
        postFormBuilder.addParams("number", mEtCarNum.getText().toString());
        postFormBuilder.addParams("typeId", mStrCarType);
        postFormBuilder.addParams("sizeId", mStrCarSize);
        postFormBuilder.addParams("color", mStrCarColor);
        postFormBuilder.addParams("carryingCapacity", mStrCarWeight);
        postFormBuilder.addParams("interspace", mStrCarVolume);
        postFormBuilder.addParams("idcardFrontImgKey", mKeyMaps.get(0));
        postFormBuilder.addParams("idcardBackImgKey",mKeyMaps.get(1));
        postFormBuilder.addParams("licenseFrontImgKey",mKeyMaps.get(2));
        postFormBuilder.addParams("licenseBackImgKey", mKeyMaps.get(3));
        postFormBuilder.addParams("telephone", mEtPhone.getText().toString());

        RequestCall call = postFormBuilder.build();
        call.doScene(new TokenSceneCallback(call) {
            @Override
            protected void handleError(String errorMsg, Call call, Exception e) {
                showToast(errorMsg);
                mProgerssDialog.dismiss();
            }

            @Override
            protected void handleResponse(Result response) {
                if(response.getResultCode() == Result.SUCCESS_CODE)  {
                    showToast("申请成功，请等待审核。");
                    finish();
                } else {
                    showToast(response.getMessage());
                }
                mProgerssDialog.dismiss();
            }


        });

    }


    @Override
    public void doFailed() {
        Toast.makeText(BecomeLCLDriverActivity.this, "拍照权限获取失败，请检查权限管理授权。", Toast.LENGTH_LONG).show();
    }

    @Override
    public void doSuccess() {
        picCamera();
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("return-data", false);//设置为不返回数据
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
}
