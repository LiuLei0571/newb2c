package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.NotifyHelper;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.response.logistics.FeacbookLoginBean;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.observer.module.HomeObserver;
import com.example.b2c.widget.EditTextWithDelete;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetEmaliNumber extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditTextWithDelete et_facebook_username;
    private EditTextWithDelete et_facebook_password;
    private TextView z_yzm;
    private Button btn_facebook_login;
    private String username;
    private String password;
    private MyHttpUtils instance;
    private Gson gson;
    private TimeCount time;
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String date;
    private String token;
    private ResponseResult result;
    //    private FeacbookLoginBean feacbookLoginBean;

    @Override
    public int getRootId() {
        return R.layout.activity_get_emali_number;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_emali_number);
        instance = MyHttpUtils.getInstance();
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(mTranslatesString.getCommon_bindemail());
        et_facebook_username = (EditTextWithDelete) findViewById(R.id.et_facebook_username);
        et_facebook_password = (EditTextWithDelete) findViewById(R.id.et_facebook_password);
        z_yzm = (TextView) findViewById(R.id.z_yzm);
        z_yzm.setText(mTranslatesString.getCommon_getauthcode());
        btn_facebook_login = (Button) findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setText(mTranslatesString.getCommon_makesure());
        z_yzm.setOnClickListener(this);
        btn_facebook_login.setOnClickListener(this);
        gson = new Gson();
    }
private void initData(){
    Intent intent = getIntent();
    id = intent.getStringExtra("id");
    firstName = intent.getStringExtra("firstName");
    lastName = intent.getStringExtra("lastName");
    gender = intent.getStringExtra("gender");
    date = intent.getStringExtra("date");
    token = intent.getStringExtra("token");
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.z_yzm:
                username = et_facebook_username.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, mTranslatesString.getValidate_usernamenotnull(), Toast.LENGTH_SHORT).show();
                    return;
                }
                requestyanzheng(username);
                break;
            case R.id.btn_facebook_login:
                username = et_facebook_username.getText().toString().trim();
                password=et_facebook_password.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, mTranslatesString.getValidate_usernamenotnull(), Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, mTranslatesString.getCommon_pleaseinputemailcode(), Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestLong(username,password);
                    }
                }).start();

                break;
        }
    }

    /**
     * 发送验证码
     * @param username
     */
  private void  requestyanzheng(String username){
      Map<String,String>map=new HashMap<>();
      map.put("email",username);//ConstantS.BASE_PIC_URL+

      instance.doPost(ConstantS.BASE_PIC_URL+"remoting/rest/ws/user/send-email-code.htm",
              map, false, -1, null, new MyHttpUtils.MyCallback() {
                  @Override
                  public void onSuccess(String result, int code) {
                      if (code==200){
                          FeacbookLoginBean feacbookLoginBean = gson.fromJson(result, FeacbookLoginBean.class);
                          if (feacbookLoginBean.getMeta().isSuccess()) {
                              //构造CountDownTimer对象
                              time = new TimeCount(60000, 1000);
                              time.start();//开始计时
                          }
                          Toast.makeText(getApplication(), feacbookLoginBean.getMeta().getErrorInfo(), Toast.LENGTH_LONG).show();
                      }
                  }

                  @Override
                  public void onFailture() {

                  }
              });
  }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            z_yzm.setText(mTranslatesString.getCommon_chongxinfasong());
            z_yzm.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            z_yzm.setClickable(false);
            z_yzm.setText( millisUntilFinished /1000+"s"+mTranslatesString.getCommon_zaicifasong());
        }
    }
    /**
     * 登录
     */
  private void  requestLong(String name,String yanzheng) {
      SPHelper.putString(Configs.APPNAME, Configs.DOMAIN + "-" + DateUtils.getTimeStamp());
      Map<String, String> map = new HashMap<>();
      map.put("thirdId", id);
      map.put("thirdSource", "10");
      map.put("email", name);
      map.put("code", yanzheng);
      map.put("firstName", firstName);
      map.put("lastName", lastName);
      //0,男   1女
      if (gender.equals("female")) {
          map.put("sex", "0");
      } else {
          map.put("sex", "1");
      }
//237330
      map.put("birthday", date);
      map.put("inputToken", token);
      instance.doPost(ConstantS.BASE_URL + "user/noEmailThirdLogin.htm",
              map, false, -1, null, new MyHttpUtils.MyCallback() {
                  @Override
                  public void onSuccess(String strResult, int code) {
                      try {
                          if (code == 200) {
                              JSONObject meta_json = new JSONObject(strResult)
                                      .getJSONObject("meta");
                              result = gson.fromJson(meta_json.toString(),
                                      ResponseResult.class);
                              if (result.isSuccess()) {
                                  JSONObject data_json = new JSONObject(strResult)
                                          .getJSONObject("data");
                                  JSONObject res = new JSONObject(data_json.toString())
                                          .getJSONObject("res");
                                  Users usersInfo = gson.fromJson(
                                          res.toString(), Users.class);
                                  usersInfo.setResult(result);
                                  SPHelper.putInt(Configs.USER_TYPE.TYPE, usersInfo.getUserType());

                                  SPHelper.putBean(Configs.BUYER.INFO, usersInfo);
                                  dissLoading();
                                  //如果登录成功就进入主界面
                                  startActivity(new Intent(getApplication(), MainActivity.class));
                                  finish();
                                  NotifyHelper.setNotifyData("home", new HomeObserver(0, ""));
                              }
                          } else {
                              dissLoading();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }

                  @Override
                  public void onFailture() {

                  }
              });
  }
}
