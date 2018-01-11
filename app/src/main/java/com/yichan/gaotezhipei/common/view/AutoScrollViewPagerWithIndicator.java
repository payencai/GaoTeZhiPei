package com.yichan.gaotezhipei.common.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.changelcai.mothership.view.util.ScreenUtil;
import com.yichan.gaotezhipei.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装AutoScrollViewPager和底部圆点Indicator。
 *
 * TODO 进一步封装，可添加自定义属性支持，比如定义底部圆点的样式、大小、间距等属性。--ckerv on 2018/1/6
 *
 * Created by ckerv on 2018/1/6.
 */

public class AutoScrollViewPagerWithIndicator extends RelativeLayout {


    private Context mContext;

    private AutoScrollViewPager mAutoScrollViewPager;
    private PagerAdapter mViewPagerAdapter;
    private LinearLayout mDotsContainer;
    private ImageView[] mIvDots;
    private List<View> mScrollViews;

    private int mCurrentIndex = 0;
    private boolean isDisplayDots = true;

    public AutoScrollViewPagerWithIndicator(Context context) {
        this(context, null);
    }

    public AutoScrollViewPagerWithIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public AutoScrollViewPagerWithIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mAutoScrollViewPager = new AutoScrollViewPager(context, attrs);
        mScrollViews = new ArrayList<>();
        initViewPager(context, attrs, defStyleAttr);
        if(isDisplayDots) {
            initDotsContainer(context, attrs, defStyleAttr);
        }
    }

    private void initViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        mViewPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container,
                                    int position, Object object) {
                if (position < mScrollViews.size()) {
                    container.removeView(mScrollViews.get(position));
                }
            }

            @Override
            public int getCount() {
                if (mScrollViews != null) {
                    return mScrollViews.size();
                }
                return 0;
            }

            @Override
            public Object instantiateItem(View container,
                                          int position) {
                // 实例化添加页卡
                View view = mScrollViews.get(position);
                if(view.getParent() != null) {
                    ((ViewGroup)view.getParent()).removeView(view);
                }
                ((AutoScrollViewPager) container).addView(
                        view, 0);
                return mScrollViews.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        mAutoScrollViewPager.setAdapter(mViewPagerAdapter);
        addView(mAutoScrollViewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        initViewPagerListener();
    }

    private void initViewPagerListener() {
        mAutoScrollViewPager
                .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        if(isDisplayDots) {
                            setCurrentDot(position);
                        }
                    }

                    @Override
                    public void onPageScrolled(int arg0,
                                               float arg1, int arg2) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {

                    }
                });
    }

    private void initDotsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        mDotsContainer = new LinearLayout(context, attrs, defStyleAttr);
        mDotsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mDotsContainer, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mDotsContainer.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, ScreenUtil.dpToPxInt(mContext, 8));
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(CENTER_HORIZONTAL);
    }


    public AutoScrollViewPager getAutoScrollViewPager() {
        return mAutoScrollViewPager;
    }


    public void addViewToViewPager(View view) {
        mScrollViews.add(view);
        mViewPagerAdapter.notifyDataSetChanged();
        if(isDisplayDots) {
            notifyDots();
        }
    }

    private void notifyDots() {

        mIvDots = new ImageView[mViewPagerAdapter.getCount()];

        mDotsContainer.removeAllViews();

        // 循环取得小点图片
        for (int i = 0; i < mScrollViews.size(); i++) {
            mIvDots[i] = new ImageView(mContext);
            mIvDots[i].setPadding(0, 0, 0, 0);
            mIvDots[i].setImageResource(R.drawable.selector_dots);
            mDotsContainer.addView(mIvDots[i], ScreenUtil.dpToPxInt(mContext, 8), ScreenUtil.dpToPxInt(mContext,8));
            mIvDots[i].setEnabled(true);// 都设为灰色
            if(i != mScrollViews.size() - 1) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvDots[i].getLayoutParams();
                params.setMargins(0, 0, ScreenUtil.dpToPxInt(mContext, 10), 0);
            }
        }

        if(mIvDots[mCurrentIndex].isEnabled()) {
            mIvDots[mCurrentIndex].setEnabled(false);
        }

    }

    private void setCurrentDot(int position) {
            if (position < 0 || position > mScrollViews.size() - 1
                    || mCurrentIndex == position || mIvDots == null || mIvDots.length == 0) {
                return;
            }


            mIvDots[position].setEnabled(false);
            mIvDots[mCurrentIndex].setEnabled(true);

            mCurrentIndex = position;

    }

    public boolean isDisplayDots() {
        return isDisplayDots;
    }

    public void setDisplayDots(boolean displayDots) {
        isDisplayDots = displayDots;
    }
}
