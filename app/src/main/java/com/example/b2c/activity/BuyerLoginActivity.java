package com.example.b2c.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.response.logistics.FeacbookLoginBean;
import com.example.b2c.activity.common.OtherUserLoginActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.NotifyHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.LoginListener;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.observer.module.HomeObserver;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SimpleTextWatcher;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 买家登录页面
 */
public class BuyerLoginActivity extends TempBaseActivity implements OnClickListener {

    private Button btn_login;
    private TextView tv_register, tv_forget_psw;
    private EditTextWithDelete et_username, et_password;
    private ImageView iv_eye;
    private boolean eyeIndicator = false;//默认闭眼
    private Bitmap[] LocalBitmaps;

    private String username, password;
    //2.0
    private TextView mTvOtherLogin;
    private ImageView face_book_login;
    private CallbackManager callbackManager;
    private OkhttpUtils instance;
//    private FeacbookLoginBean feacbookLoginBean;
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String emali;
    private String date;
    private String token;
    private ResponseResult result;

    @Override
    public int getRootId() {
        return R.layout.login_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookLgin();
        instance = OkhttpUtils.getInstance();
        initView();
    }
    public void initView() {

        if(getIntent().getIntExtra("backStatus",0) == 1){
            mBack.setVisibility(View.INVISIBLE);
        }
        face_book_login = (ImageView) findViewById(R.id.face_book_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        et_username = (EditTextWithDelete) findViewById(R.id.et_username);
        et_password = (EditTextWithDelete) findViewById(R.id.et_password);
        tv_forget_psw = (TextView) findViewById(R.id.tv_fgt_psw);
        mTvOtherLogin = (TextView) findViewById(R.id.tv_other_login);
        iv_eye = (ImageView) findViewById(R.id.iv_eye);
        LocalBitmaps = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.eye_open), BitmapFactory.decodeResource(getResources(), R.drawable.eye_close)};

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forget_psw.setOnClickListener(this);
        mTvOtherLogin.setOnClickListener(this);
        iv_eye.setOnClickListener(this);
        face_book_login.setOnClickListener(this);
        initText();


    }

    public void initText() {
//        setTitle(mTranslatesString.getCommon_login());
        setTitle(mTranslatesString.getCommon_buyerlogin());
        et_username.setHint(mTranslatesString.getNotice_inputusername());
        et_password.setHint(mTranslatesString.getValidate_inputpassword());
        btn_login.setText(mTranslatesString.getCommon_login());
        tv_register.setText(mTranslatesString.getCommon_registersongjuan());
        tv_forget_psw.setText(mTranslatesString.getCommon_forgetpassword());
        mTvOtherLogin.setText(mTranslatesString.getCommon_otherlogin());
        UIHelper.setClickable(btn_login, et_username, et_password);
        et_password.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                UIHelper.setClickable(btn_login, et_username, et_password);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.tv_register:
                Intent i_register = new Intent(this, RegisterActivity.class);
                i_register.putExtra(Configs.USER_TYPE.TYPE, Configs.USER_TYPE.BUYER);
                startActivity(i_register);
                break;
            case R.id.tv_fgt_psw:
                Intent i_fgt_psw = new Intent(this, ForgetPswActivity.class);
                i_fgt_psw.putExtra(Configs.USER_TYPE.TYPE,Configs.USER_TYPE.BUYER);
                startActivity(i_fgt_psw);
                break;
            case R.id.tv_other_login:
                Intent intent = new Intent(this, OtherUserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_eye:
                if (eyeIndicator) {
                    eyeIndicator = false;
                    et_password.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_eye.setImageBitmap(LocalBitmaps[1]);
                } else {
                    eyeIndicator = true;
                    et_password.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_eye.setImageBitmap(LocalBitmaps[0]);
                }
                break;
            case R.id.face_book_login:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email","user_friends"));
                break;
            default:
                break;
        }
    }

    public void Login() {
        /**
         * 登录之前拼接好时间戳
         */
        SPHelper.putString(Configs.APPNAME, Configs.DOMAIN + "-" + DateUtils.getTimeStamp());
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        if (username.equals("") || password.equals("")) {
            ToastHelper.makeToast(mTranslatesString.getCommon_inputnotnull());
            return;
        }
        showProgressDialog(Waiting);
        rdm.Login(username, password, unLogin, userId, token,0);
        rdm.loginListener = new LoginListener() {
            @Override
            public void onSuccess(int userId, String token, String nickName, int step) {
                if (nickName.equals("0")) {
                    startActivity(new Intent(BuyerLoginActivity.this,MainActivity.class));
                    finish();
                    NotifyHelper.setNotifyData("home", new HomeObserver(0, ""));
                } else {
                    ToastHelper.makeErrorToast(mTranslatesString.getCommon_pleaseinputuserinfo());
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        };
    }
    private void facebookLgin(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoading();
                updateUI(loginResult.getAccessToken());
                //成功后调用接口，将这个id传到后台，如果有就直接登录，没有跳转到发送验证码的页面
            }
            @SuppressWarnings("deprecation")
            @Override
            public void onCancel() {
                //取消授权
                CookieSyncManager.createInstance(BuyerLoginActivity.this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                CookieSyncManager.getInstance().sync();
            }//{HttpStatus: -1, errorCode: 100, errorType: null, errorMessage: Invalid access_token}
            @Override
            public void onError(FacebookException e) {
                if (e instanceof FacebookAuthorizationException){
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                e.getStackTrace();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void updateUI(final AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                         @Override
                         public void onCompleted(JSONObject object, GraphResponse response) {
                                 if (object != null) {
                                     //比如:1565455221565
                                     id = object.optString( "id" );
                                     //比如：Zhang San
                                     firstName = object.optString( "first_name" );
                                     //比如：Zhang San
                                     lastName = object.optString( "last_name" );
                                     //性别：比如 male （男）  female （女）
                                     gender = object.optString("gender");
                                     //邮箱：比如：56236545@qq.com
                                     emali = object.optString("email");
                                     //生日
                                     date = object.optString("birthday");
                                     //zh_CN 代表中文简体
                                     token = accessToken.getToken();
                                        //判断是否有邮箱
//                                     if(TextUtils.isEmpty(emali)){
//                                         //没有邮箱跳转到获取验证码的界面
//                                         Intent intent= new Intent(BuyerLoginActivity.this,GetEmaliNumber.class);
//                                         intent.putExtra("id",id);
//                                         intent.putExtra("firstName",firstName);
//                                         intent.putExtra("lastName",lastName);
//                                         intent.putExtra("gender",gender);
//                                         intent.putExtra("date",date);
//                                         intent.putExtra("token",token);
//                                         startActivity(intent);
//                                         dissLoading();
//                                     }else{
                                         requestLogin(id, emali, firstName, lastName, gender, date,accessToken.getToken());
//                                         requestLogin("155713561611397","7514686972@qq.com","太行","赵","0","",
//                                                 "EAAGIzgcZAvpkBAK8DTI1ph4zVkF4qX1R063NMR1rt6hf68oDkSLwGwYBlmIYFUhbN3xapXMYwZCfH2PgceBTp3CHhw5QfZA9NuoVgliqk6tIZCYRa3yBNWiTvh9lJtJXRBz5jXNZBhDi42wKJoiwJYCxxbPbG1CCbwkCSbU9bf60PhMD7FTej05EZCDewEBRp6HQecN5LO8wZDZD");
//                                     }
                                     }
                             }
                     }) ;

                 Bundle parameters = new Bundle();
                 parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
                 request.setParameters(parameters);
                 request.executeAsync() ;
    }
    private void requestLogin(String thirdId, String emali,
                              final String firstName, final String lastName, String  sex, String birthDay, String input_token){
        SPHelper.putString(Configs.APPNAME,Configs.DOMAIN + "-" + DateUtils.getTimeStamp());
        final Gson gson=new Gson();
        Map<String,String>map=new HashMap<>();
        map.put("thirdId",thirdId);
        map.put("thirdSource","10");
        map.put("email",emali);
        map.put("firstName",firstName);
        map.put("lastName",lastName);
        //0,男   1女
        if (sex.equals("female")){
            map.put("sex","0");
        }else{
            map.put("sex","1");
        }
        map.put("birthday",birthDay);
        map.put("inputToken",input_token);//http://192.168.1.101/remoting/rest/android/
        try {
            instance.doPost(getApplication(), ConstantS.BASE_URL+"user/checkThirdLogin.htm",
                    map, false, -1, null, new OkhttpUtils.MyCallback() {
                        @Override
                        public void onFailture(Exception e) {
                            dissLoading();
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFinish() {
                            dissLoading();
                        }

                        @Override
                        public void onSuccess(String strResult){
                                try {
                                    JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                                    result = gson.fromJson(meta_json.toString(),
                                            ResponseResult.class);
                                    if (result.isSuccess()) {
                                        JSONObject data_json = new JSONObject(strResult)
                                                .getJSONObject("data");
                                        //TODO 登录可以存token的时候有问题
                                        JSONObject res = new JSONObject(data_json.toString())
                                                .getJSONObject("res");
                                        Users usersInfo = gson.fromJson(
                                                res.toString(), Users.class);
                                            usersInfo.setResult(result);
                                            SPHelper.putInt(Configs.USER_TYPE.TYPE, usersInfo.getUserType());

                                            SPHelper.putBean(Configs.BUYER.INFO, usersInfo);

                                            startActivity(new Intent(getApplication(),MainActivity.class));
                                            finish();
                                            NotifyHelper.setNotifyData("home", new HomeObserver(0, ""));
                                        }else{
                                            if (result.getErrorNO()==3){
                                                Intent intent= new Intent(BuyerLoginActivity.this,GetEmaliNumber.class);
                                                intent.putExtra("id",id);
                                                intent.putExtra("firstName",firstName);
                                                intent.putExtra("lastName",lastName);
                                                intent.putExtra("gender",gender);
                                                intent.putExtra("date",date);
                                                intent.putExtra("token",token);
                                                startActivity(intent);
                                                dissLoading();
                                            }
                                            Toast.makeText(getApplication(),result.getErrorInfo(),Toast.LENGTH_LONG).show();
                                        }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }
                    });

        } catch (NetException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //home键
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
