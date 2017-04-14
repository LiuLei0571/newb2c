package com.example.b2c.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.b2c.R;
import com.example.b2c.helper.UIHelper;


/**
 * 用途：
 * Created by milk on 16/10/12.
 * 邮箱：649444395@qq.com
 */

public class SendSmsButton extends Button {
    private Paint mPaint;

    public SendSmsButton(Context context) {
        super(context, null);
        init();

    }

    public SendSmsButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();

    }

    public SendSmsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        setPhone("");
    }

    public void setPhone(String phone) {
        boolean enable = !(TextUtils.isEmpty(phone)&&phone.length()==11);
        setState(enable);
    }

    public void setState(boolean state) {
        if (state) {
            setTextColor(UIHelper.getColor(R.color.red));
        } else {
            setTextColor(UIHelper.getColor(R.color.white));
        }
        setEnabled(state);
    }

    public void setState(String str, boolean enable) {
        setText(str);
        setState(enable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth((float) 1.0);
        canvas.drawLine(0, 10, 0, getMeasuredHeight() - 10, mPaint);
    }
}
