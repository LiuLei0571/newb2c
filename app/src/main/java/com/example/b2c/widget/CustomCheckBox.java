package com.example.b2c.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.b2c.R;
import com.example.b2c.net.response.seller.PropertyDetails;

/**
 * 用途：
 * 作者：Created by john on 2017/2/20.
 * 邮箱：liulei2@aixuedai.com
 */


public class CustomCheckBox extends LinearLayout {
    CheckBox mCheckBox;
    private PropertyDetails mData;
    private OnCheckListener mListener;
    private int id;
    private String name;

    public CustomCheckBox(Context context) {
        super(context);
        initView(context);
    }


    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if (context instanceof OnCheckListener) {
            mListener = (OnCheckListener) context;
        }
        inflate(context, R.layout.temple_check_box, this);
        mCheckBox = (CheckBox) this.findViewById(R.id.checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mData.setSelect(true);
                } else {
                    mData.setSelect(false);
                }
                mListener.onItem(mData, id, name);

            }
        });
    }

    public void setData(PropertyDetails mData, int id, String name) {
        if (mData != null) {
            this.mData = mData;
            mCheckBox.setText(mData.getName());
            this.id = id;
            this.name = name;
            mCheckBox.setChecked(mData.isSelect());
        }
    }

    public PropertyDetails getData() {
        if (mCheckBox.isChecked()) {
            return mData;
        } else {
            return null;
        }
    }

    public interface OnCheckListener {
        void onItem(PropertyDetails data, int id, String name);
    }
}
