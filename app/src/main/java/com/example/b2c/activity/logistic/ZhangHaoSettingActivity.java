package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.cacheColorHint;
import static android.R.attr.data;

public class ZhangHaoSettingActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private TextView et_yonghuming;
    private TextView et_emall;
    private TextView tv_phone;
    private LinearLayout ll_modification_phone;
    private TextView tv_password;
    private LinearLayout ll_modification_password;
    private TextView tv_my_yonghuming;
    private TextView tv_my_youxiang;
    private TextView tv_my_shouji;
    private TextView tv_mima;
    private JSONObject data;
    private JSONObject meta;

    @Override
    public int getRootId() {
        return R.layout.activity_zhang_hao_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        et_yonghuming = (TextView) findViewById(R.id.et_yonghuming);
        et_emall = (TextView) findViewById(R.id.et_emall);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        ll_modification_phone = (LinearLayout) findViewById(R.id.ll_modification_phone);
        tv_password = (TextView) findViewById(R.id.tv_password);
        ll_modification_password = (LinearLayout) findViewById(R.id.ll_modification_password);
        ll_modification_password.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getCommon_usernamesetting());

        tv_my_yonghuming = (TextView) findViewById(R.id.tv_my_yonghuming);
        tv_my_youxiang = (TextView) findViewById(R.id.tv_my_youxiang);
        tv_my_shouji = (TextView) findViewById(R.id.tv_my_shouji);
        tv_mima = (TextView) findViewById(R.id.tv_mima);
        tv_my_yonghuming.setText(mTranslatesString.getCommon_username());
        tv_my_youxiang.setText(mTranslatesString.getCommon_email());
        tv_my_shouji.setText(mTranslatesString.getCommon_tellphone());
        tv_mima.setText(mTranslatesString.getCommon_modifypassword());

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_LINSHI + "express/getDeliveryAccount/" + UserHelper.getExpressLoginId() + ".htm",
                            true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    String content = response.getContent();
                    if (response.getStatusCode()== HttpStatus.SC_OK) {

                        JSONObject json = new JSONObject(content);
                        meta = json.getJSONObject("meta");
                        boolean success = meta.getBoolean("success");
                        if (success) {
                            data = json.getJSONObject("data");
                            handler.sendEmptyMessage(100);
                        } else {
                            handler.sendEmptyMessage(200);
                        }
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
Handler handler=new Handler()
{
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 100:
                try {
                    et_yonghuming.setText(data.getString("loginName"));
                    et_emall.setText(data.getString("email"));
                    tv_phone.setText(data.getString("mobile"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 200:
                try {
                    Toast.makeText(getApplication(), meta.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
};
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.ll_modification_password:
                //修改密码
                startActivity(new Intent(getApplication(), ModificationPasswordActivity.class));
                break;
        }
    }
}
