package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.widget.EditTextWithDelete;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class SearchHistoryActivity extends TempBaseActivity {
    @Bind(R.id.toolbar_back)
    ImageButton mToolbarBack;
    @Bind(R.id.toolbar_title)
    EditTextWithDelete mToolbarTitle;
    @Bind(R.id.btn_search)
    Button mBtnSearch;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_search_hostory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBtnSearch.setText(mTranslatesString.getCommon_search());
        mToolbarTitle.setText(mTranslatesString.getCommon_pleaseinputsearch());
        mToolbarTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnSearch.setEnabled(true);
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void onClick() {
        Intent intent = new Intent(this, LogisticsOrderDetailActivity.class);
        intent.putExtra("number", mToolbarTitle.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.toolbar_back)
    public void onClick1() {
        finish();
    }
}
