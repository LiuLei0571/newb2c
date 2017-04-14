package com.example.b2c.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.ViewHelpers;
import com.example.b2c.net.response.seller.CategoryDetailModule;
import com.example.b2c.net.response.seller.PropertyDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/20.
 * 邮箱：liulei2@aixuedai.com
 */


public class SpecificationLayout extends LinearLayout {
    public CategoryDetailModule moduleList;
    public List<PropertyDetails> mItemList = new ArrayList<>();
    TextView tvTitle;
    LinearLayout mItem;
    private Context context;

    public SpecificationLayout(Context context) {
        super(context);
        init(context);
    }


    public SpecificationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public SpecificationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public void init(Context context) {
        this.context = context;
        inflate(context, R.layout.temple_specification, this);
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        mItem = (LinearLayout) this.findViewById(R.id.item);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        ViewHelpers.unbind(this);
        super.onDetachedFromWindow();
    }

    public void setData(CategoryDetailModule mData) {
        moduleList = mData;
        if (mData != null) {
            tvTitle.setText(mData.getPropertyName());
            for (PropertyDetails mItemData : mData.getPropertyDetails()) {
                CustomCheckBox mBox = new CustomCheckBox(context);
                mBox.setData(mItemData,mData.getPropertyId(),mData.getPropertyName());
                mItem.addView(mBox);
            }
        }
    }

    public CategoryDetailModule getData() {
        for (int i = 0; i < mItem.getChildCount(); i++) {
            CustomCheckBox customCheckBox = (CustomCheckBox) mItem.getChildAt(i);
            if (customCheckBox.getData() != null) {
                mItemList.add(customCheckBox.getData());
            }
        }
        moduleList.setPropertyDetails(mItemList);
        return moduleList;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        ViewHelpers.saveInstanceState(this, super.onSaveInstanceState());
        return super.onSaveInstanceState();
    }
}
