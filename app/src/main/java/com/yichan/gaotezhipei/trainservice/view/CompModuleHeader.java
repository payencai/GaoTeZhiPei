package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class CompModuleHeader extends LinearLayout {
    private ImageView mLeftImg;
    private TextView mTitleTxt;
    private TextView mMoreBtn;
    private CompModuleHeaderListener mCompModuleHeaderListener;

    public CompModuleHeader(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.comp_module_header, this, true);
        mLeftImg = (ImageView)view.findViewById(R.id.CompModuleHeader_leftImg);
        mTitleTxt = (TextView) view.findViewById(R.id.CompModuleHeader_mTitleTxt);
        mMoreBtn = (TextView) view.findViewById(R.id.CompModuleHeader_mMoreBtn);
    }

    public void setHeaderTitle(String title) {
        mTitleTxt.setText(title);
    }

    public void setLeftImgHidden(boolean hidden){
        if(hidden){
            mLeftImg.setVisibility(View.GONE);
        }else{
            mLeftImg.setVisibility(View.VISIBLE);
        }
    }

    public void setMoreBtnTitle(String title) {
        mMoreBtn.setText(title);
    }

    public void setMoreBtnHidden(boolean hidden){
        if(hidden){
            mMoreBtn.setVisibility(View.GONE);
        }else{
            mMoreBtn.setVisibility(View.VISIBLE);
        }
    }

    public void onMoreButtonClick(View sender) {
        if (mCompModuleHeaderListener != null) {
            mCompModuleHeaderListener.onMoreButtonClick(CompModuleHeader.this);
        }
    }

    public void setCompModuleHeaderListener(CompModuleHeaderListener mCompModuleHeaderListener) {
        this.mCompModuleHeaderListener = mCompModuleHeaderListener;
    }

    public interface CompModuleHeaderListener {
        public void onMoreButtonClick(CompModuleHeader moduleHeader);
    }
}
