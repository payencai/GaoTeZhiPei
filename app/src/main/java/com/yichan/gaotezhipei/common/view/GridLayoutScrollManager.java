package com.yichan.gaotezhipei.common.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 可以控制RecyclerView是否能滚动的GridLayoutManager。
 * Created by ckerv on 2018/1/6.
 */

public class GridLayoutScrollManager extends GridLayoutManager {

    private boolean canScroll = true;

    public GridLayoutScrollManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GridLayoutScrollManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public GridLayoutScrollManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollHorizontally() {
        return canScroll && super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return canScroll && super.canScrollVertically();
    }
}
