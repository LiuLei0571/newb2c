package com.example.b2c.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.b2c.helper.UIHelper;

/**
 * Created by renjianan on 2016/11/17.
 */

public class BannerPoint extends View {

    Paint mPaint;
    private int RADIUS = UIHelper.dip2px(getContext(), 3.4f);

    public BannerPoint(Context context) {
        this(context, null);
    }

    public BannerPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(UIHelper.dip2px(getContext(), 14), UIHelper.dip2px(getContext(), 7));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(RADIUS, RADIUS, RADIUS, mPaint);
    }

    public void setColor(String color) {
        if (!checkColor(color))
            return;
        mPaint.setColor(UIHelper.getColor(color));
        invalidate();
    }

    public boolean checkColor(String color) {
        if (TextUtils.isEmpty(color))
            return false;
        return color.matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    }
}
