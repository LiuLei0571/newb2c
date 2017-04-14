package com.example.b2c.fragment.staff;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.staff.ExpressInfo;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.widget.util.Utils;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 用途：
 * 作者：Created by john on 2017/2/13.
 * 邮箱：liulei2@aixuedai.com
 */

public class StaffInfoActivity extends TempBaseActivity implements View.OnClickListener {
//    @Bind(R.id.tv_user)
//    SettingItemView2 mTvUser;
//    @Bind(R.id.tv_family)
//    SettingItemView2 mTvFamily;
//    @Bind(R.id.tv_last)
//    SettingItemView2 mTvLast;
//    @Bind(R.id.tv_phone)
//    SettingItemView2 mTvPhone;
//    @Bind(R.id.tv_email)
//    SettingItemView2 mTvEmail;
    private Intent intent;
    private TextView toole_right;
    private TextView et_user;
    private EditText et_family;
    private EditText et_last;
    private EditText et_phone;
    private TextView et_email;

    private TextView mTvUser;
    private TextView mTvFamily;
    private TextView mTvLast;
    private TextView mTvPhone;
    private TextView mTvEmail;
    private JSONObject meta;


    @Override
    public int getRootId() {
        return R.layout.activity_staff_info_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mTvUser= (TextView) findViewById(R.id.tv_user);
        mTvFamily= (TextView) findViewById(R.id.tv_family);
        mTvLast= (TextView) findViewById(R.id.tv_last);
        mTvPhone= (TextView) findViewById(R.id.tv_phone);
        mTvEmail= (TextView) findViewById(R.id.tv_email);
        setTitle(mTranslatesString.getCommon_basicinfo());
        mTvUser.setText(mTranslatesString.getCommon_username());
        mTvFamily.setText(mTranslatesString.getCommon_xing());
        mTvLast.setText(mTranslatesString.getCommon_ming());
        mTvPhone.setText(mTranslatesString.getCommon_tellphone());
        mTvEmail.setText(mTranslatesString.getCommon_email());
        toole_right = (TextView) findViewById(R.id.toolbar_right_text);
        toole_right.setOnClickListener(this);
        et_user = (TextView) findViewById(R.id.et_user);
        et_family = (EditText) findViewById(R.id.et_family);
        et_last = (EditText) findViewById(R.id.et_last);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (TextView) findViewById(R.id.et_email);
        toole_right.setText(mTranslatesString.getCommon_save());
        initData();

    }

    private void initData() {
        showLoading();
        mLogisticsDataConnection.getExpressUserInfo(ConstantS.BASE_URL + "staff/getUserInfo.htm");
        mLogisticsDataConnection.mOneDataListener = new OneDataListener<ExpressInfo>() {
            @Override
            public void onSuccess(final ExpressInfo data, String successInfo) {
                if (data != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_email.setText(Utils.cutNull(data.getEmail()));
                            et_phone.setText(Utils.cutNull(data.getMobile()));
                            et_user.setText(Utils.cutNull(data.getLoginName()));
                            et_family.setText(Utils.cutNull(data.getLastName()));
                            et_last.setText(Utils.cutNull(data.getFirstName()));
                        }
                    });
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {

            }
        };
    }

    @OnClick({R.id.toolbar_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:
                submit();
                break;

        }
//        startActivity(intent);
    }

    private void submit() {
        // validate


        final String family = et_family.getText().toString().trim();
        if (TextUtils.isEmpty(family)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputuserinfo(), Toast.LENGTH_SHORT).show();
            return;
        }

        final String last = et_last.getText().toString().trim();
        if (TextUtils.isEmpty(last)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputuserinfo(), Toast.LENGTH_SHORT).show();
            return;
        }
        final String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputuserinfo(), Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String>map=new HashMap<String, String>();
                map.put("mobile",phone);
                map.put("firstName",last);
                map.put("lastName",family);
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "staff/updateUserInfo.htm",
                            map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode()== HttpStatus.SC_OK) {
                        String jieguo = response.getContent();
                        if (jieguo != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(jieguo);
                                meta = jsonObject.getJSONObject("meta");
                                handler.sendEmptyMessage(100);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    try {
                        Toast.makeText(getApplication(), meta.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };
}
