package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;

/**
 * Created by simon on 2018/2/8 0008.
 */

public class CompModuleItem extends LinearLayout {
    private ImageView mLeftImg;
    private TextView mTitleTxt;
    private TextView mMoreTxt;

    public CompModuleItem(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.comp_module_item, this, true);
        mLeftImg = (ImageView)view.findViewById(R.id.CompModuleItem_mLeftImg);
        mTitleTxt = (TextView) view.findViewById(R.id.CompModuleItem_mTitleTxt);
        mMoreTxt = (TextView) view.findViewById(R.id.CompModuleItem_mMoreTxt);
    }

    public void setTitle(String title) {
        mTitleTxt.setText(title);
    }

    public void setMore(String more) {
        mMoreTxt.setText(more);
        mMoreTxt.setVisibility(View.VISIBLE);
    }

    public void setStatus(boolean isSelected) {
        if(isSelected) {
            mLeftImg.setImageResource(R.drawable.redtriangle);
            mTitleTxt.setTextColor(Color.parseColor("#FF0000"));
            mMoreTxt.setTextColor(Color.parseColor("#FF0000"));
        } else {
            mLeftImg.setImageResource(R.drawable.greytriangle);
            mTitleTxt.setTextColor(Color.parseColor("#666666"));
            mMoreTxt.setTextColor(Color.parseColor("#666666"));
        }
    }

    public void setLeftImgHidden(boolean hidden){
        if(hidden){
            mLeftImg.setVisibility(View.GONE);
        }else{
            mLeftImg.setVisibility(View.VISIBLE);
        }
    }
}
