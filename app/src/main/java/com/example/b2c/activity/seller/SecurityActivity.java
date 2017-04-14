package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b2c.R;
import com.example.b2c.activity.BuyerInfoActivity;
import com.example.b2c.activity.ChangePswActivity;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.logistic.LanguageSettingActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.LogoutListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.BuyerInfo;
import com.example.b2c.widget.SettingItemView2;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class SecurityActivity extends TempBaseActivity {

    private static final  int  RQ_PHONE=10;

    private SettingItemView2 siv_username;
    private SettingItemView2 siv_email;
    private SettingItemView2 siv_phone;
    private SettingItemView2 siv_password;
    private SettingItemView2 siv_language;
    private Button btn_logout;
    private BuyerInfo buyerInfo;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_security;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }
    /**
     * 初始化页面请求
     */
    private void initData() {
        sellerRdm.getSellerInfo(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        sellerRdm.mOneDataListener = new OneDataListener<BuyerInfo>() {
            @Override
            public void onFinish() {
            }
            @Override
            public void onLose() {
            }
            @Override
            public void onSuccess(final BuyerInfo dataEntry, String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI(dataEntry);
                    }
                });

            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }

    private void initUI(BuyerInfo info) {
        buyerInfo = info;
        siv_username.setTv_doc(info.getLoginName());
        siv_email.setTv_doc(info.getEmail());
        siv_phone.setTv_doc(info.getMobile());
        siv_password.setTv_doc("******");
    }

    private void initView() {
        siv_username = (SettingItemView2) findViewById(R.id.siv_username);
        siv_username.hide_RightIv();
        siv_email = (SettingItemView2) findViewById(R.id.siv_email);
        siv_email.hide_RightIv();
        siv_phone = (SettingItemView2) findViewById(R.id.siv_phone);
        siv_password = (SettingItemView2) findViewById(R.id.siv_password);
        siv_language = (SettingItemView2) findViewById(R.id.siv_language);
        siv_language.hide_RightTv();
        btn_logout = (Button) findViewById(R.id.btn_logout);
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_setcenter());
        siv_username.setTv_text(mTranslatesString.getCommon_username());
        siv_email.setTv_text(mTranslatesString.getCommon_email());
        siv_phone.setTv_text(mTranslatesString.getCommon_tellphone());
        siv_password.setTv_text(mTranslatesString.getCommon_password());
        siv_language.setTv_text(mTranslatesString.getCommon_language());
        btn_logout.setText(mTranslatesString.getCommon_exit());
    }

    @OnClick({R.id.siv_phone, R.id.siv_password, R.id.siv_language,R.id.btn_logout})
        protected void getOnClick(View view) {
            switch (view.getId()) {
                case R.id.siv_phone:
                    Intent intent_phone = new Intent(SecurityActivity.this, CommonEditActivity.class);
                    intent_phone.putExtra("title",mTranslatesString.getCommon_tellphone());
                    intent_phone.putExtra("text",buyerInfo.getMobile());
                    startActivityForResult(intent_phone,RQ_PHONE);
                    break;
                case R.id.siv_password:
                    Intent intent = new Intent(SecurityActivity.this, ChangePswActivity.class);
//                    intent.putExtra(Configs.USER_TYPE.TYPE, 1);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    break;
                case R.id.siv_language:
                    startActivity(new Intent(getApplication(),LanguageSettingActivity.class));
                    break;
                case R.id.btn_logout:
                    UserHelper.clearUserLocalAll();
                    loginOut();
                    break;
                default:
                    break;
            }
    }

    /**
     * 修改信息成功回调
     */
    public interface  CallBack{
        void doCallBack();
    }
    /**
     * 修改用户信息
     * @param map
     */
    private void modifyUserInfo(Map map,final BuyerInfoActivity.CallBack callBack) {
        showLoading();
        sellerRdm.updateSellerInfo(map,UserHelper.isSellerLogin(),UserHelper.getSellerID(),UserHelper.getSellerToken());
        sellerRdm.responseListener = new ResponseListener(){
            @Override
            public void onSuccess(String successInfo) {
                ToastHelper.makeToast(successInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.doCallBack();
                    }
                });
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
                dissLoading();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        String text;
        Map map =new HashMap();
        switch (requestCode){
            case RQ_PHONE:
                map.clear();
                text = data.getStringExtra("text").toString();
                if(TextUtils.isBlank(text)){
                    return;
                }
                map.put("mobile",text);
                final String finalText = text;
                modifyUserInfo(map, new BuyerInfoActivity.CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_phone.setTv_doc(finalText);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 用户登出
     */
    public void loginOut() {
        showProgressDialog(Waiting);
        rdm.Logout(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        rdm.logoutListener = new LogoutListener() {
            @Override
            public void onSuccess(boolean isSuccess) {
            }
            @Override
            public void onError(int errorNO, String errorInfo) {

            }
            @Override
            public void onFinish() {
                dismissProgressDialog();
                shipToHome();
            }
            @Override
            public void onLose() {

            }

        };
    }
    /**
     * 跳到首页
     */
    private void shipToHome(){
        Intent intent = new Intent(SecurityActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
