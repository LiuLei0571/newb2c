package com.example.b2c.activity.common;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.widget.EditTextWithDelete;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * 作者：Created by john on 2017/2/13.
 * 邮箱：liulei2@aixuedai.com
 */


public class CommonChangeItemActivity extends TempBaseActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_phone)
    EditTextWithDelete etPhone;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    private String fromType;
    private String title;
    private String tvMessage;

    @Override
    public int getRootId() {
        return R.layout.activity_common_change_item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        fromType=getIntent().getStringExtra("fromType");
        title=getIntent().getStringExtra("topMsg");
        tvMessage=getIntent().getStringExtra("tvTitle");
        mBtnSubmit.setText(mTranslatesString.getCommon_sure());

    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
    }
}
