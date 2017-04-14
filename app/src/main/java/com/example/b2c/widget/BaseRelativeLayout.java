package com.example.b2c.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.b2c.R;
import com.example.b2c.helper.ViewHelpers;
import com.example.b2c.widget.impl.IWidgetView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 用途：
 * Created by milk on 16/9/30.
 * 邮箱：649444395@qq.com
 */

public abstract class BaseRelativeLayout extends RelativeLayout implements IWidgetView {
    protected ImageLoader mImageLoader;
    protected DisplayImageOptions options;

    public BaseRelativeLayout(Context context) {
        this(context, null);
        init(context,null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context,null);

    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,null);

    }
    public void init(Context context, AttributeSet attrs){
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(0)
                .showImageOnFail(R.mipmap.ic_launcher)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
        mImageLoader=ImageLoader.getInstance();
    }

    @Override
    public void beforeViewBind(View view) {

    }@Override
    protected void onFinishInflate() {
        int rootViewId = getRootLayoutId();
        if (rootViewId != -1) {
            beforeViewBind(this);
            inflate(getContext(), getRootLayoutId(), this);
            ViewHelpers.bind(this);
            afterViewBind(this);
        }
        super.onFinishInflate();

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return ViewHelpers.saveInstanceState(this, super.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ViewHelpers.restoreInstanceState(this, state));
    }

    public String getString(int stringId) {
        return getContext().getString(stringId);
    }
}
