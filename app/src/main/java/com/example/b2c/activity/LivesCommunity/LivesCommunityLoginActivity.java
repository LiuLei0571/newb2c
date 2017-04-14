package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.WebViewActivity;
import com.example.b2c.activity.seller.BaseSetCompanyActivity;
import com.example.b2c.activity.seller.HomeActivity;
import com.example.b2c.activity.seller.SelectSellerTypeActivity;
import com.example.b2c.activity.seller.SellerLoginActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.LoginListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.EditTextWithDelete;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LivesCommunityLoginActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditTextWithDelete et_lives_username;
    private EditTextWithDelete et_lives_password;
    private Button btn_lives_login;
    private String tag;
    private OkhttpUtils instance;

    @Override
    public int getRootId() {
        return R.layout.activity_lives_community_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        instance = OkhttpUtils.getInstance();
        initView();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        et_lives_username = (EditTextWithDelete) findViewById(R.id.et_lives_username);
        et_lives_password = (EditTextWithDelete) findViewById(R.id.et_lives_password);
        btn_lives_login = (Button) findViewById(R.id.btn_lives_login);
        btn_lives_login.setText(mTranslatesString.getCommon_login());
        toolbar_title.setText(mTranslatesString.getCommon_login());
        btn_lives_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lives_login:
                    submit();
                break;
        }
    }

    private void submit() {
        String username = et_lives_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, mTranslatesString.getValidate_usernamenotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        String password = et_lives_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, mTranslatesString.getValidate_passwordnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        doLogin(username,password);
    }
    public void doLogin(String loginName, String password) {
        SPHelper.putString(Configs.APPNAME, Configs.DOMAIN + "-" + DateUtils.getTimeStamp());
        final Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("password", Md5Encrypt.md5(password));
        map.put("type", 2+"");
        try {
            instance.doPost(getApplication(), ConstantS.BASE_URL
                    + "user/login.htm", map, false, -1, null, new OkhttpUtils.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    JSONObject meta_json = null;
                    try {
                        meta_json = new JSONObject(result)
                                .getJSONObject("meta");
                        ResponseResult meta = gson.fromJson(meta_json.toString(),
                                ResponseResult.class);
                        if (meta.isSuccess()) {
                            JSONObject data_json = new JSONObject(result)
                                    .getJSONObject("data");
                            Users usersInfo = gson.fromJson(
                                    data_json.toString(), Users.class);
                            usersInfo.setResult(meta);
                            SPHelper.putInt(Configs.USER_TYPE.TYPE, usersInfo.getUserType());
                            if (Configs.USER_TYPE.BUYER == usersInfo.getUserType()) {
                                SPHelper.putBean(Configs.BUYER.INFO, usersInfo);
                            }
                            if (Configs.USER_TYPE.SELLER == usersInfo.getUserType()) {
                                SPHelper.putBean(Configs.SELLER.INFO, usersInfo);
                            }
                            //除了要将返回的信息存入对应的sp中，还要再存一份，因为这个信息要在生活专区里接口中用到，
                            //毕竟生活专区的接口在请求的时候再去判断是哪类的用户太麻烦
                            SPHelper.putBean(Configs.LIVES.INFO, usersInfo);
                            //登录成功要根据点击不同按钮跳转到不同界面
                            if (tag.equals("fabu")){
                                //TODO   点击发布登录，跳转到类目选择界面
                                startActivity(new Intent(getApplication(),LeiMuListActivity.class));
                            }
                            if (tag.equals("fankui")){
                                //如果是点击的反馈或者举报就跳转到他们的界面
                                startActivity(new Intent(getApplication(),JuBaoActivity.class).putExtra("tag","fankui"));
                            }
                            if (tag.equals("jubao")){
                                String id = getIntent().getStringExtra("id");
                                startActivity(new Intent(getApplication(),JuBaoActivity.class).putExtra("tag","jubao")
                                .putExtra("id",id));
                            }
                            if (tag.equals("liaotian")){
                                String liaotianId = getIntent().getStringExtra("liaotianId");
                                //跳转到聊天界面
                                Intent intent=new Intent(getApplication(),WebViewActivity.class);
                                intent.putExtra("url", ConstantS.BASE_CHAT+"chat/chatWithSeller/" + liaotianId + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&appName=" + SPHelper.getString(Configs.APPNAME) + "&loginName=" + UserHelper.getBuyerName()+ "&lang=" + SPHelper.getString(Configs.LANGUAGE));
                                startActivity(intent);
                            }
                            if (tag.equals("me")){
                                //如果是个人中心就讲自己关闭，并且个人中心界面刷新
                                startActivity(new Intent(getApplication(), MyFaBuMessageActivity.class));
                            }
                            dissLoading();
                            finish();
                        } else {
                            //提示登录失败，并且提示原因
                            Toast.makeText(getApplication(),meta.getErrorInfo(),Toast.LENGTH_LONG).show();
                            dissLoading();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailture(Exception e) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }
            });


        } catch (NetException e) {
            Toast.makeText(getApplication(),mTranslatesString.getCommon_neterror(),Toast.LENGTH_LONG).show();
            dissLoading();
            Log.e("doLogin", e.getMessage());
        } finally {
        }
    }
}
