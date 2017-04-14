package com.example.b2c.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.ModelPageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片轮播 支持自动 手动循环轮播 手势暂停
 *
 * @author Created by chenxujie on 15-5-21.
 * @author Modify by diaowj on 16-8-25.
 */
public class BannerLayout extends ResizeLayout implements View.OnClickListener {

    private ViewPager mPager = null;
    private LinearLayout mPoints = null;

    private List<ModelPageItem> banners = null;

    private long loopDelayed = 5000;

    // 是否自动滚动
    private boolean isAutoScroll = true;
    //是否停止
    private boolean isStop = false;
    //是否可以滚动
    private boolean canScroll = true;

    private String selectedColor = "#FFFFFF";
    private String normalColor = "#b2FFFFFF";

    public BannerLayout(Context context) {
        super(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        banners = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.banner_pager);
        mPoints = (LinearLayout) findViewById(R.id.banner_points);
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return banners == null ? 0 : banners.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ModelPageItem banner = banners.get(position);

                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mImageLoader.displayImage(ConstantS.BASE_PIC_URL+banner.getItemImage(), imageView,options);
                imageView.setOnClickListener(BannerLayout.this);
                imageView.setTag(R.id.item, banner);
                return imageView;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshPoints(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    // 当滑动到第一张时，让其定位到原集合的最后一张
                    if (mPager.getCurrentItem() == 0) {
                        mPager.setCurrentItem(banners.size() - 2, false);
                    }

                    // 当滑动到最后一张时，让其定位到原集合的第1张
                    if (mPager.getCurrentItem() == banners.size() - 1) {
                        mPager.setCurrentItem(1, false);
                    }
                }
            }
        });
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SwipeRefreshLayout parent = getRefreshLayout();
                if (parent != null) {
                    parent.setEnabled(false);
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (parent != null) parent.setEnabled(true);
                        startImageCycle();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        pauseImageCycle();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        pauseImageCycle();
                        break;
                }
                return false;
            }
        });
    }

    public SwipeRefreshLayout getRefreshLayout() {
        SwipeRefreshLayout swip = null;
        ViewParent parent = getParent();
        while (parent != null) {
            if (parent instanceof SwipeRefreshLayout) {
                swip = (SwipeRefreshLayout) parent;
                break;
            }
            parent = parent.getParent();
        }
        return swip;
    }

    /**
     * 图片轮播
     */
    private void startImageCycle() {
        if (isAutoScroll && canMove()) {
            isStop = false;
            if (mImageTimerTask != null) { //图片滚动任务
                mHandler.removeCallbacks(mImageTimerTask);
            }
            mHandler.postDelayed(mImageTimerTask, loopDelayed);
        }
    }

    /**
     * 只有一张图片时 不轮播
     */
    private boolean canMove() {
        return banners.size() > 3;
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (banners != null && banners.size() > 0) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                if (!isStop) {
                    mHandler.postDelayed(mImageTimerTask, loopDelayed);
                }
            }
        }
    };

    public void setColors(String selectedColor, String normalColor) {
        this.selectedColor = selectedColor;
        this.normalColor = normalColor;
        invalidate();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pauseImageCycle() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    /**
     * 添加圆点
     */
    private void checkPoints(int size) {
        mPoints.removeAllViews();
        if (size == 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            BannerPoint image = new BannerPoint(getContext());
            if (i == 0) {
                image.setColor(selectedColor);
            } else {
                image.setColor(normalColor);
            }
            mPoints.addView(image);
        }
    }

    /**
     * 刷新圆圈指示
     */
    private void refreshPoints(int position) {
        for (int i = 0; i < mPoints.getChildCount(); i++) {
            if (position == 0) {
                position = banners.size() - 2;
            }
            if (position == banners.size() - 1) {
                position = 1;
            }

            if (position - 1 == i) {
                ((BannerPoint) mPoints.getChildAt(i))
                        .setColor(selectedColor);
            } else {
                ((BannerPoint) mPoints.getChildAt(i))
                        .setColor(normalColor);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }

    View.OnClickListener mOnClickListener;

    public void setBanners(List<ModelPageItem> imageList, View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        banners.clear();
        if (imageList != null && imageList.size() > 0) {
            setCanScroll(true);
            banners.addAll(imageList);
            //在集合前后加各加1张,在集合前加原集合最后一张，在集合后加原集合的第一张
            if (imageList.size() >= 2) {
                banners.add(0, banners.get(banners.size() - 1));
                banners.add(banners.get(1));
                mPager.setCurrentItem(1);
                checkPoints(imageList.size());

            } else {
                setCanScroll(false);
                mPager.setCurrentItem(0);

            }

            startImageCycle();
        }
        mPager.getAdapter().notifyDataSetChanged();
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll()) {
            super.scrollTo(x, y);
        }
    }

    /**
     * 设置是否自动滚动
     */
    public void setAutoScroll(boolean isAutoScroll) {
        this.isAutoScroll = isAutoScroll;
    }

    /**
     * 设置图片切换间隔时间
     */
    public void setDelayTime(long delayTime) {
        this.loopDelayed = delayTime;
    }
}
