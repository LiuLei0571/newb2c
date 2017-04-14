package com.example.b2c.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.b2c.R;
import com.example.b2c.helper.UIHelper;

/**
 * 用途：
 * Created by milk on 16/10/8.
 * 邮箱：649444395@qq.com
 */


public class EditTextWithDelete extends EditText implements TextWatcher, View.OnFocusChangeListener {

    private Drawable deleteIcon;

    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public EditTextWithDelete(Context context) {
        super(context);
        init();
    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        Drawable drawable = UIHelper.getDrawable(R.drawable.ic_warning);
        drawable.setBounds(0, 0, 50, 50);
        setError(error, drawable);
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        deleteIcon = getCompoundDrawables()[2];
        if (deleteIcon == null) {
            deleteIcon = getResources().getDrawable(R.drawable.ic_delete);
        }

        deleteIcon.setBounds(0, 0, deleteIcon.getIntrinsicWidth(), deleteIcon.getIntrinsicHeight());

        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (deleteIcon != null && length() > 0 && event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight())) &&
                    (x < (getWidth() - getPaddingRight()));
            Rect rect = deleteIcon.getBounds();
            int height = rect.height();
            int y = (int) event.getY();
            int distance = (getHeight() - height) / 2;
            boolean isInnerHeight = (y > distance) && (y < (distance + height));

            if (isInnerWidth && isInnerHeight) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
            if(mOnAfterTextChange != null){
                mOnAfterTextChange.doOnAfterTextChange();
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (mOnNewFocusChange != null) {
            mOnNewFocusChange.onFocusChange(v, hasFocus);
        }
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? deleteIcon : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private OnNewFocusChange mOnNewFocusChange;

    public interface OnNewFocusChange {
        void onFocusChange(View v, boolean hasFocus);
    }

    public void setOnNewFocusChange(OnNewFocusChange OnNewFocusChange) {
        mOnNewFocusChange = OnNewFocusChange;
    }

    private  OnAfterTextChange mOnAfterTextChange;

    /**
     * editTextChange之后要做的事情
     */
    public  interface OnAfterTextChange{
        void doOnAfterTextChange();
    }

    public void setmOnAfterTextChange(OnAfterTextChange mOnAfterTextChange) {
        this.mOnAfterTextChange = mOnAfterTextChange;
    }
}