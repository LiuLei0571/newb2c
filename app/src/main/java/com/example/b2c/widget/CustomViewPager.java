package com.example.b2c.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * 2016-09-20
 *
 * @author Administrator
 */
public class CustomViewPager extends ViewPager {
    /**
     * 自定义属性标识是否启动滑动
     */
    private boolean isCanScroll = false;

    private static final int MOVE_LIMITATION = 100;// 触发移动的像素距离
    private float mLastMotionX; // 手指触碰屏幕的最后一次x坐标

    private Scroller mScroller; // 滑动控件

    public CustomViewPager(Context context) {
        super(context);
        init(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCanScroll) {
            return false;
        } else {
            final int action = event.getAction();
            final float x = event.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastMotionX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(x - mLastMotionX) < MOVE_LIMITATION) {
                        snapToScreen(getCurrentItem());
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isCanScroll) {
            return false;
        } else {
            final int action = arg0.getAction();
            final float x = arg0.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastMotionX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            return super.onInterceptTouchEvent(arg0);
        }
    }

    /**
     * 滚动到制定的视图
     *
     * @param whichScreen 视图下标
     */
    public void snapToScreen(int whichScreen) {
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
            invalidate();
        }
    }

    public void setIsCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
