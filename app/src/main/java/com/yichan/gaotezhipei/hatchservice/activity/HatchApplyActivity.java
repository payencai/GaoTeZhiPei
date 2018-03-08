package com.yichan.gaotezhipei.hatchservice.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changelcai.mothership.network.RequestCall;
import com.changelcai.mothership.network.builder.PostFormBuilder;
import com.sevenheaven.segmentcontrol.SegmentControl;
import com.yichan.gaotezhipei.BuildConfig;
import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.base.util.BitmapUtil;
import com.yichan.gaotezhipei.base.util.DialogHelper;
import com.yichan.gaotezhipei.base.util.FileUitlity;
import com.yichan.gaotezhipei.base.util.PhotoUtils;
import com.yichan.gaotezhipei.base.view.BottomMenuDialog;
import com.yichan.gaotezhipei.common.callback.TokenSceneCallback;
import com.yichan.gaotezhipei.common.constant.AppConstants;
import com.yichan.gaotezhipei.common.entity.Result;
import com.yichan.gaotezhipei.common.util.EmailUtil;
import com.yichan.gaotezhipei.common.util.PermissionUtils;
import com.yichan.gaotezhipei.common.util.PhoneUtil;
import com.yichan.gaotezhipei.hatchservice.constant.HatchServiceConstants;
import com.yichan.gaotezhipei.mine.constant.MineConstants;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ckerv on 2018/1/19.
 */

public class HatchApplyActivity extends BaseActivity implements PermissionUtils.ApplyPermission {

    @BindView(R.id.titlebar_tv_title)
    TextView mTvTitle;

    @BindView(R.id.apply_et_company)
    EditText mEtCompany;
    @BindView(R.id.apply_et_contact)
    EditText mEtContact;
    @BindView(R.id.apply_et_phone)
    EditText mEtPhone;
    @BindView(R.id.apply_et_email)
    EditText mEtEmail;
    @BindView(R.id.apply_et_addr)
    EditText mEtAddr;
    @BindView(R.id.apply_et_product_name)
    EditText mEtProductName;
    @BindView(R.id.apply_et_member_count)
    EditText mEtMemberCount;
    @BindView(R.id.apply_sc_schedule)
    SegmentControl mScSchedule;

    @BindView(R.id.iv_plan)
    ImageView mIvPlan;
    @BindView(R.id.ll_upload)
    LinearLayout mLlUpload;


    private static final int IMAGE_REQUEST_CODE = 20000;
    private static final int CAMERA_REQUEST_CODE = 1999;
    private static final int RESULT_REQUEST_CODE = 299;


    private String mCurrentStep = "创意阶段";

    private String photoPath;

    private Uri cropImageUri;

    private String mImageKey;

    private Bitmap mBitmapToUpload;

    private BottomMenuDialog bottomDialog;
    private DialogFragment mProgerssDialog;



    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
        initSegmentControl();
    }

    private void initSegmentControl() {
        mScSchedule.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                switch (index) {
                    case 0:
                        mCurrentStep = "创意阶段";
                        break;
                    case 1:
                        mCurrentStep = "创意计划";
                        break;
                    case 2:
                        mCurrentStep = "已经启动";
                        break;
                    case 3:
                        mCurrentStep = "已经运营";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initTitleBar() {
        mTvTitle.setText("入孵申请表");
    }

    @OnClick({R.id.titlebar_btn_left,R.id.apply_btn_commit,R.id.ll_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_left:
                finish();
                break;
            case R.id.ll_upload:
                alertPicPanel();
                break;
            case R.id.apply_btn_commit:
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

    private void commitAllParams() {
        RequestCall call = new PostFormBuilder()
                .url(AppConstants.BASE_URL + HatchServiceConstants.URL_HATCH_APPLY)
                .addParams("xCompanyName", mEtCompany.getText().toString())
                .addParams("xLinkman", mEtContact.getText().toString())
                .addParams("xTelnum", mEtPhone.getText().toString())
                .addParams("xEmail", mEtEmail.getText().toString())
                .addParams("xAdress", mEtAddr.getText().toString())
                .addParams("xProjectName", mEtProductName.getText().toString())
                .addParams("xTeanNum", mEtMemberCount.getText().toString())
                .addParams("xProjectStatus", mCurrentStep)
                .addParams("xPlanPic", mImageKey).build();
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
                    showToast("申请成功，请等待审核。");
                    if(mProgerssDialog != null) {
                        mProgerssDialog.dismiss();
                    }
                    finish();
                } else {
                    showToast(response.getMessage());
                    if(mProgerssDialog != null) {
                        mProgerssDialog.dismiss();
                    }
                }
            }


        });
    }

    public void startToUpload() {
        if(mProgerssDialog != null) {
            mProgerssDialog.dismiss();
        }
        mProgerssDialog = DialogHelper.showProgress(getSupportFragmentManager(), "上传信息中...", false);
        uploadImageView();
    }

    private void uploadImageView() {
        Bitmap compressedBm = BitmapUtil.compressImage(mBitmapToUpload);//压缩
        File file = BitmapUtil.saveBitmapToFile(HatchApplyActivity.this, compressedBm, mBitmapToUpload.toString());//转文件
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

    private boolean checkForms() {
        if(TextUtils.isEmpty(mEtCompany.getText().toString())
                || TextUtils.isEmpty(mEtContact.getText().toString())
                || TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mEtEmail.getText().toString())
                || TextUtils.isEmpty(mEtAddr.getText().toString())
                || TextUtils.isEmpty(mEtProductName.getText().toString())
                || TextUtils.isEmpty(mEtMemberCount.getText().toString())
                || mBitmapToUpload == null) {
            showToast("信息填写不完整，请检查。");
            return false;
        } else if(!PhoneUtil.isPhoneNumber(mEtPhone.getText().toString())) {
            showToast("请输入正确格式的手机号码");
            return false;
        } else if(!EmailUtil.isEmail(mEtEmail.getText().toString())) {
            showToast("请输入正确格式的电子邮箱");
            return false;
        }
        return true;
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_hatch_apply;
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
        PermissionUtils.needPermission(HatchApplyActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
    }

    @Override
    public void doFailed() {
        Toast.makeText(HatchApplyActivity.this, "拍照权限获取失败，请检查权限管理授权。", Toast.LENGTH_LONG).show();
    }

    @Override
    public void doSuccess() {
        picCamera();
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
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(HatchApplyActivity.this, "com.yichan.gaotezhipei.fileprovider", new File(photoPath)));// 将照片输出为
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
                        Uri newUri = Uri.parse(PhotoUtils.getPath(HatchApplyActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, 300, 300, 299);
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
                        Uri newUri = Uri.parse(PhotoUtils.getPath(HatchApplyActivity.this, cropImageUri));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, 300, 300, 299);
                        // 读取uri所在的图片
                    } else {
                        Toast.makeText(HatchApplyActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    }

    private void setBitmapToImageView(Bitmap bitmap) {
        mBitmapToUpload = bitmap;
        mIvPlan.setVisibility(View.VISIBLE);
        mIvPlan.setImageBitmap(mBitmapToUpload);
        mLlUpload.setVisibility(View.GONE);
    }
}
