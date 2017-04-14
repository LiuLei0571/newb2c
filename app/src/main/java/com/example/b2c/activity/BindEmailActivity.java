package com.example.b2c.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

import butterknife.Bind;

/**
 * 绑定邮箱
 */
public class BindEmailActivity extends TempBaseActivity implements OnClickListener {

    @Bind(R.id.btn_check_email)
    Button btn_check_email;

    @Bind(R.id.et_bind_email)
    EditText et_bind_email;

    @Override
    public int getRootId() {
        return R.layout.bind_email_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    public void initView() {
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_bindemail());
        et_bind_email.setHint(mTranslatesString.getCommon_pleaseinputemail());
        btn_check_email.setText(mTranslatesString.getCommon_bindemail());

    }

    @Override
    public void onClick(View v) {

    }
}
