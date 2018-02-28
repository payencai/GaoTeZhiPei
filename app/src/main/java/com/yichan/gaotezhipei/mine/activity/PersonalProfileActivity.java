package com.yichan.gaotezhipei.mine.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yichan.gaotezhipei.R;
import com.yichan.gaotezhipei.common.util.BottomMenuDialog;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initTitleBar();
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
                mTvNickName.setText(nickName);
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
                        mTvGender.setText(items1[0]);
                    }
                });
                builder.addMenu(items1[1], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        mTvGender.setText(items1[1]);
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

    @Override
    protected void reloadProfile(Bitmap bm) {
        Toast.makeText(this, ">>>>>>reloadProfile", Toast.LENGTH_SHORT);
        imgPerson.setImageBitmap(bm);
    }

    @Override
    protected void picSelectUpload(Bitmap mBitmap, String fileName) {

    }
}
