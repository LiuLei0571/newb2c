package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

/**
 * 用途：申请结算金额
 * * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class SettlementDetailActivity extends TempBaseActivity implements View.OnClickListener {


    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private EditText tv_jiesuan_money;
    private EditText tv_jiesuan_note;
    private Button btn_jiesuan_login;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_settlement_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_jiesuan_money = (EditText) findViewById(R.id.tv_jiesuan_money);
        tv_jiesuan_note = (EditText) findViewById(R.id.tv_jiesuan_note);
        btn_jiesuan_login = (Button) findViewById(R.id.btn_jiesuan_login);
        toolbar_title.setText("结算金额");
        toolbar_back.setOnClickListener(this);
        btn_jiesuan_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:

                break;
            case R.id.btn_jiesuan_login:

                break;
        }
    }

    private void submit() {
        // validate
        String money = tv_jiesuan_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, "请输入您要缴纳的保证金额", Toast.LENGTH_SHORT).show();
            return;
        }

        String note = tv_jiesuan_note.getText().toString().trim();
        if (TextUtils.isEmpty(note)) {
            Toast.makeText(this, "备注(选填)", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
