package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.widget.SettingItemView3;


/**
 *  买家中心
 *  账户安全页面
 */
public class UserSafeActivity extends TempBaseActivity implements OnClickListener {
    private SettingItemView3 siv_password;
    @Override
    public int getRootId() {
        return R.layout.user_safe_layout;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    public void initView() {
        siv_password = (SettingItemView3) findViewById(R.id.siv_password);
        siv_password.setOnClickListener(this);
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_usersafe());
        siv_password.setTv_text(mTranslatesString.getCommon_modifypassword());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siv_password:
                Intent i_change_psw = new Intent(this, ChangePswActivity.class);
                i_change_psw.putExtra("type",0);
                startActivity(i_change_psw);
                break;
            default:
                break;
        }
    }

}
