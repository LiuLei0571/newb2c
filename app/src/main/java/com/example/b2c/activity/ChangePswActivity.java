package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.seller.SellerLoginActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.widget.EditTextWithDelete;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;


/***
 * 买家/卖家 修改密码
 */
public class ChangePswActivity extends TempBaseActivity{

    private int userType = 0;
    private EditTextWithDelete et_check_old;
    private EditTextWithDelete et_input_new;
    private EditTextWithDelete et_check_new;
    private Button btn_submit;

    @Override
    public int getRootId() {
        return R.layout.change_psw_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    public void initView() {
        userType = getIntent().getIntExtra("type",0);
        et_check_old = (EditTextWithDelete) findViewById(R.id.et_check_old);
        et_input_new = (EditTextWithDelete) findViewById(R.id.et_input_new);
        et_check_new = (EditTextWithDelete) findViewById(R.id.et_check_new);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_modifypassword());
        et_check_old.setHint(mTranslatesString.getCommon_inputoldpassword());
        et_input_new.setHint(mTranslatesString.getValidate_inputpassword());
        et_check_new.setHint(mTranslatesString.getValidate_inputsurepassword());
        btn_submit.setText(mTranslatesString.getCommon_makesure());
    }

    /**
     * 修改密码
     */
    public void changePsw() {
        //参数验证
        if(UIHelper.checkEdit(et_check_old,et_input_new,et_check_new)){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return;
        }
        if(!et_input_new.getText().toString().equals(et_check_new.getText().toString())){
            ToastHelper.makeToast(mTranslatesString.getValidate_passworddifference());
            return;
        }
        showLoading();
        Map map = new HashMap();
        map.put("oldPassword", Md5Encrypt.md5(et_check_old.getText().toString()));
        map.put("newPassword",Md5Encrypt.md5(et_input_new.getText().toString()));
        map.put("confirmPass",Md5Encrypt.md5(et_check_new.getText().toString()));

        if(userType == 0){
            rdm.ChangePassword(UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken(),map);
        }else if(userType == 1){
            rdm.ChangePassword(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken(),map);
        }
        rdm.responseListener = new ResponseListener() {
            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                UserHelper.clearUserLocalAll();
                if(userType == 0){
                    Intent intent = new Intent(ChangePswActivity.this,BuyerLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("fromTab", 0);
                    intent.putExtra("backStatus",1);
                    startActivity(intent);

                }else if(userType == 1){
                    Intent intent = new Intent(ChangePswActivity.this, SellerLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("fromTab", 0);
                    intent.putExtra("backStatus",1);
                    startActivity(intent);

                }
                finish();

//                NotifyHelper.setNotifyData(ObserverTypes.BUYERLOGIN.getType(), new BaseObserver() {
//                    @Override
//                    public String getType() {
//                        return ObserverTypes.BUYERLOGIN.getType();
//                    }
//                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }

    @OnClick(R.id.btn_submit)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                changePsw();
                break;
        }
    }






}
