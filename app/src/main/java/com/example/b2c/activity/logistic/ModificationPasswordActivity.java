package com.example.b2c.activity.logistic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.RegularExpression;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Md5Encrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.cacheColorHint;
import static android.R.attr.data;

/**
 * 修改密码
 */
public class ModificationPasswordActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private EditText et_jiumima;
    private EditText et_xinmima;
    private EditText et_querenmima;
    private TextView toolbar_right;
    private JSONObject meta;

    @Override
    public int getRootId() {
        return R.layout.activity_modification_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right = (TextView) findViewById(R.id.toolbar_right_text);
        et_jiumima = (EditText) findViewById(R.id.et_jiumima);
        et_xinmima = (EditText) findViewById(R.id.et_xinmima);
        et_querenmima = (EditText) findViewById(R.id.et_querenmima);
        toolbar_right.setVisibility(View.VISIBLE);
        toolbar_right.setOnClickListener(this);
        toolbar_back.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getCommon_modifypassword());
        toolbar_right.setText(mTranslatesString.getCommon_save());
        et_jiumima.setHint(mTranslatesString.getCommon_inputoldpassword());
        et_xinmima.setHint(mTranslatesString.getCommon_newpassword());
        et_querenmima.setHint(mTranslatesString.getCommon_surepassword());
    }
private void initData(){
    final String jiumima = et_jiumima.getText().toString().trim();
    final String xinmima = et_xinmima.getText().toString().trim();
    String querenmima = et_querenmima.getText().toString().trim();
    if (TextUtils.isEmpty(jiumima)||TextUtils.isEmpty(xinmima)||TextUtils.isEmpty(querenmima)){
        Toast.makeText(getApplication(), mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_SHORT).show();
    return;
    }
    if (!xinmima.equals(querenmima)){
        Toast.makeText(getApplication(), mTranslatesString.getValidate_passworddifference(), Toast.LENGTH_SHORT).show();
        return;
    }
    if (!RegularExpression.isPassword(jiumima)||!RegularExpression.isPassword(xinmima)||!RegularExpression.isPassword(querenmima)){
        //请输入6-15位，字母，数字，下划线
        Toast.makeText(this, mTranslatesString.getCommon_passwordCuowu(), Toast.LENGTH_SHORT).show();
        return;
    }
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Map<String,String>map=new HashMap<>();
                map.put("oldPassword", Md5Encrypt.md5(jiumima));
                map.put("password",Md5Encrypt.md5(xinmima));
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
                Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "express/updatePassword.htm", map,
                        true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                String content = response.getContent();
                JSONObject json=new JSONObject(content);
                meta = json.getJSONObject("meta");
                boolean success = meta.getBoolean("success");
                if (success){
                    handler.sendEmptyMessage(100);
                }else{
                    handler.sendEmptyMessage(300);
                }
            } catch (NetException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }).start();

}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
            finish();
                break;
            case R.id.toolbar_right_text:
                initData();
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    Toast.makeText(getApplication(), mTranslatesString.getCommon_baocunchenggong(), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 300:
                    try {
                        Toast.makeText(getApplication(),meta.getString("errorInfo"),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
