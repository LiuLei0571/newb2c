package com.example.b2c.activity.logistic;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.GetEmaliNumber;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.RegularExpression;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.logistics.FeacbookLoginBean;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码
 */
public class LogisticForgetPasswordActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditText password_user_name;
    private EditText password_emila;
    private EditText change_validation;
    private TextView b_u_cv;
    private EditText xin_password;
    private EditText queding_password;
    private Button back_pw_ok;
    private MyHttpUtils instance;
    private TimeCount time;
    private ResponseResult jieguo;

    @Override
    public int getRootId() {
        return R.layout.activity_logistic_forget_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        password_user_name = (EditText) findViewById(R.id.password_user_name);
        password_emila = (EditText) findViewById(R.id.password_emila);
        change_validation = (EditText) findViewById(R.id.change_validation);
        b_u_cv = (TextView) findViewById(R.id.b_u_cv);
        xin_password = (EditText) findViewById(R.id.xin_password);
        queding_password = (EditText) findViewById(R.id.queding_password);
        back_pw_ok = (Button) findViewById(R.id.back_pw_ok);
        back_pw_ok.setOnClickListener(this);
        password_user_name.setHint(mTranslatesString.getCommon_pleaseinputloginname());
        password_emila.setHint(mTranslatesString.getCommon_pleaseinputemail());
        change_validation.setHint(mTranslatesString.getCommon_pleaseinputemailcode());
        xin_password.setHint(mTranslatesString.getCommon_hintpassword());
        queding_password.setHint(mTranslatesString.getValidate_inputsurepassword());
        toolbar_title.setText(mTranslatesString.getCommon_modifypassword());
        b_u_cv.setText(mTranslatesString.getCommon_getauthcode());
        b_u_cv.setOnClickListener(this);
        instance = MyHttpUtils.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_pw_ok:
                submit();


                //请求修改密码
                break;
            case R.id.b_u_cv:
                String emila = password_emila.getText().toString().trim();
                if (TextUtils.isEmpty(emila)) {
                    Toast.makeText(this,mTranslatesString.getCommon_pleaseinputemail(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (RegularExpression.isEmail(emila)){
                    requestyanzheng(emila);
                }else{
                    Toast.makeText(this,mTranslatesString.getNotice_pleaseinputcorrectemail(), Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
        }
    }
    /**
     * 发送验证码
     * @param username
     */
    private void  requestyanzheng(String username){
        Map<String,String> map=new HashMap<>();
        map.put("email",username);//ConstantS.BASE_PIC_URL+

        instance.doPost(ConstantS.BASE_PIC_URL+"remoting/rest/ws/user/send-email-code.htm",
                map, false, -1, null, new MyHttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result, int code) {
                        Gson gson=new Gson();
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
            b_u_cv.setText(mTranslatesString.getCommon_chongxinfasong());
            b_u_cv.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            b_u_cv.setClickable(false);
            b_u_cv.setText( millisUntilFinished /1000+"s"+mTranslatesString.getCommon_zaicifasong());
        }
    }
    //修改密码
    private void submit() {

        String name = password_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputloginname(), Toast.LENGTH_SHORT).show();
            return;
        }

        String emila = password_emila.getText().toString().trim();
        if (TextUtils.isEmpty(emila)) {
            Toast.makeText(this,mTranslatesString.getCommon_pleaseinputemail(), Toast.LENGTH_SHORT).show();
            return;
        }

        String validation = change_validation.getText().toString().trim();
        if (TextUtils.isEmpty(validation)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputemailcode(), Toast.LENGTH_SHORT).show();
            return;
        }

        String password = xin_password.getText().toString().trim();
        if (!TextUtils.isEmpty(password)) {
            Toast.makeText(this, mTranslatesString.getCommon_hintpassword(), Toast.LENGTH_SHORT).show();
            return;
        }

        String queren_password = queding_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, mTranslatesString.getValidate_inputsurepassword(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!queren_password.equals(password)){
            Toast.makeText(this, mTranslatesString.getValidate_passworddifference(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegularExpression.isPassword(queren_password)||!RegularExpression.isPassword(queren_password)){
            //请输入6-15位，字母，数字，下划线
            Toast.makeText(this, mTranslatesString.getCommon_passwordCuowu(), Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        final Gson gson=new Gson();
        Map<String,String>map=new HashMap<>();
        map.put("loginName",name);
        map.put("email",emila);
        map.put("emailCode",validation);
        map.put("newPassword", Md5Encrypt.md5(password));
        map.put("confirmPassword",Md5Encrypt.md5(queren_password));
        instance.doPost(ConstantS.BASE_URL + "express/forgetPassword.htm", map,
                false, -1, null, new MyHttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result, int code) {
                        if (code==200){
                            JSONObject meta_json = null;
                            try {
                                meta_json = new JSONObject(result).getJSONObject("meta");
                                jieguo = gson.fromJson(meta_json.toString(),
                                         ResponseResult.class);
                                if (jieguo.isSuccess()){
                                    handler.sendEmptyMessage(100);
                                }else{
                                    handler.sendEmptyMessage(200);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            dissLoading();
                        }
                    }

                    @Override
                    public void onFailture() {

                    }
                }
        );
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    Toast.makeText(getApplication(),jieguo.getErrorInfo(),Toast.LENGTH_LONG).show();
                    dissLoading();
                    finish();
                    break;
                case 200:
                    dissLoading();
                    Toast.makeText(getApplication(),jieguo.getErrorInfo(),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
