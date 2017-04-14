package com.jcodecraeer.xrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;

import java.util.Date;

public class ArrowRefreshFooterer extends LinearLayout implements BaseRefreshHeader {

	private LinearLayout mContainer;
	private ImageView mArrowImageView;
//	private SimpleViewSwitcher mProgressBar;
	private TextView mStatusTextView;
	private int mState = STATE_NORMAL;

	private TextView mHeaderTimeView;

    private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
    private AnimationDrawable loadongAnimation;
    private Context context;
    private boolean elastic = false;
    private boolean noDelay = false;

	private static final int ROTATE_ANIM_DURATION = 180;

	public int mMeasuredHeight;

	public ArrowRefreshFooterer(Context context) {
		super(context);
        this.context = context;
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ArrowRefreshFooterer(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context = context;
		initView();
	}

	private void initView() {
		// 初始情况，设置下拉刷新view高度为0
		mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.listview_foot_recycle, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
		this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

		addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.listview_header_arrow);
        loadongAnimation = (AnimationDrawable) mArrowImageView.getDrawable();
		mStatusTextView = (TextView)findViewById(R.id.refresh_status_textview);

        //init the progress view
//		mProgressBar = (SimpleViewSwitcher)findViewById(R.id.listview_header_progressbar);
//        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(getContext());
//        progressView.setIndicatorColor(0xffB5B5B5);
//        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
//        mProgressBar.setView(progressView);


//		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
//				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mRotateUpAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
//		mRotateUpAnim.setFillAfter(true);
//		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
//				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mRotateDownAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
//		mRotateDownAnim.setFillAfter(true);
		mHeaderTimeView = (TextView)findViewById(R.id.last_refresh_time);
		measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();
	}

    public void setElastic(boolean elastic) {
        this.elastic = elastic;
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            mContainer.getChildAt(i).setVisibility(GONE);
        }
    }

    public void setNoDelay() {
        noDelay = true;
    }

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
//            mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
//            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
//            progressView.setIndicatorColor(0xffB5B5B5);
//            progressView.setIndicatorId(style);
//            mProgressBar.setView(progressView);
        }
    }

    public void setArrowImageView(int resid){
        mArrowImageView.setImageResource(resid);
    }

	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {	// 显示进度
//			mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
            loadongAnimation.start();
//			mProgressBar.setVisibility(View.VISIBLE);
		} else if(state == STATE_DONE) {
            mArrowImageView.setVisibility(View.INVISIBLE);
            loadongAnimation.stop();
//            mProgressBar.setVisibility(View.INVISIBLE);
        } else {	// 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
            loadongAnimation.stop();
//			mProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
//                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mStatusTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
//                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case     STATE_REFRESHING:
                mStatusTextView.setText(R.string.refreshing);
                break;
            case    STATE_DONE:
                mStatusTextView.setText(R.string.refresh_done);
                break;
            default:
		}
		
		mState = state;
	}

    public int getState() {
        return mState;
    }

    @Override
	public void refreshComplete(){
        mHeaderTimeView.setText(friendlyTime(new Date()));
        setState(STATE_DONE);
        int delay = 200;
        if (noDelay){
            delay = 0;
        }
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, delay);
	}

	public void setVisibleHeight(int height) {
		if (height < 0) height = 0;
		LayoutParams lp = (LayoutParams) mContainer .getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
		return lp.height;
	}

    @Override
    public void onMove(float delta) {
        if(getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                ViewGroup.LayoutParams layoutParams = mArrowImageView.getLayoutParams();
                if ((int) delta + getVisibleHeight() <= dip2px(context, 50)) {
                    layoutParams.width = (int) delta + getVisibleHeight();
                    layoutParams.height = (int) delta + getVisibleHeight();
                }
                mArrowImageView.setLayoutParams(layoutParams);
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                }else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if(getVisibleHeight() > mMeasuredHeight &&  mState < STATE_REFRESHING){
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <=  mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        int delay = 500;
        if (elastic){
            delay = 0;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, delay);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);

        if(ct == 0) {
            return "刚刚";
        }

        if(ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60,1) + "分钟前";
        }
        if(ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            return day + "天前";
        }
        if(ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}