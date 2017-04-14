package com.example.b2c.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.b2c.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class ResizeLayout extends FrameLayout {

    private int mRatioWidth = 1;
    private int mRatioHeight = 1;

    protected ImageLoader mImageLoader;
    protected DisplayImageOptions options;
    public ResizeLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(0)
                .showImageOnFail(R.mipmap.ic_launcher)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
        mImageLoader=ImageLoader.getInstance();
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ResizeLayout, defStyle, 0);

        mRatioWidth = a.getInt(
                R.styleable.ResizeLayout_resizewidth, 1);
        mRatioHeight = a.getInt(
                R.styleable.ResizeLayout_resizeheight,
                1);

        a.recycle();

    }


    public void setRatio(int width, int height) {
        if (this.mRatioHeight == height && this.mRatioWidth == width) {
            return;
        }
        this.mRatioHeight = height;
        this.mRatioWidth = width;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * mRatioHeight / mRatioWidth;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }


}
