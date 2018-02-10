package com.yichan.gaotezhipei.mine.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.base.component.BaseActivity;
import com.yichan.gaotezhipei.common.util.BottomMenuDialog;
import com.yichan.gaotezhipei.common.util.FileUtil;
import com.yichan.gaotezhipei.common.util.SpTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by simon on 2018/2/10 0010.
 */

public abstract class ProfileActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 20000;
    String photoPath;
    private static final int CAMERA_REQUEST_CODE = 1999;
    private static final int RESULT_REQUEST_CODE = 299;
    private static String path = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + Environment.getExternalStorageDirectory().getPath();
    String fileName;
    private File cameraFile;

    protected Context ctx;
    protected final int REQUES_CODE = 0;
    protected final int codeLogout = 11;
    protected final int codeWithdrawCash = 12;
    protected final int codeAddHeadPortraint = 13;
    protected Dialog dialog;
    protected boolean isMethodBank = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        dialog = new Dialog(this, R.style.DialogStyleNoTitle);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    protected abstract void initView();
    protected abstract void initData();
//    protected abstract void onRequestSucceed(int what, HttpJsonClient client);
    protected abstract void buildMenu(BottomMenuDialog.Builder builder, int type);
    protected abstract void reloadProfile(Bitmap bm);
    //上传图片到后台
    protected abstract void picSelectUpload(Bitmap mBitmap, String fileName);

    protected String[] items1;
    protected BottomMenuDialog bottomDialog;
    protected void alertPicPanel(int type){
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        switch (type){
            case 1:
                items1 = new String[] { "拍照", "从相册选择"  };
                builder.setTitle("");
                builder.addMenu(items1[0], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        //拍照
                        picCamera();
                    }
                });
                builder.addMenu(items1[1], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        //本地选择
                        picSelect();
                    }
                });
                break;
        }
        buildMenu(builder, type);
        bottomDialog = builder.create();
        bottomDialog.show();
    }

    //拍照
    private void picCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 图片存储的上级目录
        File parent = FileUtil.getInstance(ctx).makeDir("gaotezhipei");
        // 判断有无SD卡
        photoPath = parent.getPath() + File.separator + System.currentTimeMillis() + ".jpeg";
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));// 将照片输出为
        cameraFile = new File(photoPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));// 将照片输出为
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
    //本地选择
    private void picSelect() {
        Intent intentG = new Intent(Intent.ACTION_PICK);
        intentG.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentG, IMAGE_REQUEST_CODE);
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);//设置为不返回数据
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    @SuppressWarnings("deprecation")
    private void getImageToView(Bitmap bm) {
        if (bm != null) {
            Bitmap photo = bm;
            //imgPerson.setImageBitmap(bm);
            reloadProfile(bm);
            setPicToView(photo);
        }
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String picName= "headicon";
        fileName = path + picName + ".jpg";// 图片名字
        SpTools.setBoolean(getApplicationContext(), picName, true);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //上传图片到后台
        picSelectUpload(mBitmap, fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null ){
            Uri uri;
            switch (requestCode){
                case IMAGE_REQUEST_CODE://本地相册选择图片
                    if (data != null) {
                        uri = data.getData();
                        if (!TextUtils.isEmpty(uri.getAuthority())) {
                            Cursor cursor = getContentResolver().query(uri,
                                    new String[] { MediaStore.Images.Media.DATA },null, null, null);
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
                        startPhotoZoom(Uri.fromFile(new File(photoPath)));
                    }else{
                        Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    uri = data.getData();
                    if(uri==null)
                        break;
                    Bitmap cropBitmap= getBitmapFromUri(uri, this);
                    getImageToView(cropBitmap);
                    break;
                case CAMERA_REQUEST_CODE://拍照
                    Log.d(TAG, ">>>>>>CAMERA_REQUEST_CODE");
                    uri = Uri.fromFile(cameraFile);
                    if(uri==null)
                        break;
                    if (FileUtil.isSDCardExist()) {
                        Log.d(TAG, ">>>>>>CAMERA_REQUEST_CODE.startPhotoZoom");
                        // 读取uri所在的图片
                        startPhotoZoom(uri);
                    } else {
                        Toast.makeText(ctx,"未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context mContext)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
