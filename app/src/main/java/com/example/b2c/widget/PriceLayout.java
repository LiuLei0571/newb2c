package com.example.b2c.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.seller.PropertyDetails;
import com.example.b2c.net.response.seller.SampleProInfosModule;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/27.
 * 邮箱：liulei2@aixuedai.com
 */


public class PriceLayout extends LinearLayout {
    private Context context;
    private TextView tvTitle;
    private EditText mEtPrice;
    private EditText mEtAccount;
    private List<PropertyDetails> mList = new ArrayList<>();
    protected MobileStaticTextCode mTranslatesString;
    private String message = "";

    public PriceLayout(Context context) {
        super(context);
        init(context);

    }

    public PriceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public PriceLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

        this.context = context;
        inflate(context, R.layout.temple_price, this);
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        mEtPrice = (EditText) this.findViewById(R.id.et_price);
        mEtAccount = (EditText) this.findViewById(R.id.et_account);
        mEtPrice.setHint(mTranslatesString.getCommon_price());
        mEtAccount.setHint(mTranslatesString.getCommon_stock());
    }

    public void setPrice(SampleProInfosModule data) {
        if (data != null) {
            mEtAccount.setText(data.getAmount() + "");
            mEtPrice.setText(data.getPrice() + "");
        }
    }

    public void setData(PropertyDetails data) {
        if (data != null) {
            tvTitle.setText(data.getName());
            mList.add(data);
        }
    }

    public void setData(PropertyDetails data, PropertyDetails data2) {
        if (data != null && data2 != null) {
            tvTitle.setText(data.getName() + " / " + data2.getName());
            mList.add(data);
            mList.add(data2);
        }
    }

    public void setData(List<PropertyDetails> data) {
        if (data != null) {
            mList = data;
            for (PropertyDetails mItem : data) {
                message = message + mItem.getName() + "/";
            }
            tvTitle.setText(message.substring(0, message.length() - 1));
            message = "";
        }
    }

    public SampleProInfosModule getData() {
        SampleProInfosModule module = new SampleProInfosModule();
        List<Integer> id = new ArrayList<>();
        List<Integer> itemid = new ArrayList<>();
        module.setAmount(mEtAccount.getText().toString().trim());
        module.setPrice(mEtPrice.getText().toString().trim());
        for (PropertyDetails mData : mList) {
            id.add(mData.getPropertyId());
            itemid.add(mData.getId());
        }
        module.setPropertyIds(id);
        module.setProDetailIds(itemid);
        return module;
    }
}
