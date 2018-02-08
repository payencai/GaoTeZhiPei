package com.yichan.gaotezhipei.trainservice.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichan.gaotezhipei.R;

/**
 * Created by simon on 2018/2/8 0008.
 */

public class CompModuleItem extends LinearLayout {
    private TextView mTitleTxt;

    public CompModuleItem(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.comp_module_item, this, true);
        mTitleTxt = (TextView) view.findViewById(R.id.CompModuleItem_mTitleTxt);
    }

    public void setTitle(String title) {
        mTitleTxt.setText(title);
    }
}
