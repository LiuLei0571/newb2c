package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 用途：申请保证金
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 */

public class ApplyMoneyActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView tv_zuidijine;
    private EditText tv_money;
    private EditText tv_note;
    private Button btn_login;
    private String edu;
    private String money;
    private String note;
    private JSONObject meta_json;
    private JSONObject data;
    private JSONObject meta;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_apply_money;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_zuidijine = (TextView) findViewById(R.id.tv_zuidijine);
        tv_money = (EditText) findViewById(R.id.tv_money);
        tv_note = (EditText) findViewById(R.id.tv_note);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_money.setHint(mTranslatesString.getCommon_addcashdeposit());
        tv_note.setHint(mTranslatesString.getApply_remark());

        toolbar_title.setText(mTranslatesString.getLogistic_applypromisecash());
        btn_login.setText(mTranslatesString.getCommon_submit());
    }
    private void initData(){
        requestZuidi();

    }
   private void requestZuidi(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_EXPRESS + "getMinApplyDeposit.htm", false, -1, "jinqiao@geH123");
                   String content = response.getContent();
                   if (response.getStatusCode()== HttpStatus.SC_OK){
                       try {
                           meta_json = new JSONObject(content)
                                   .getJSONObject("meta");
                           String jieguo = meta_json.toString();
                           JSONObject s=new JSONObject(jieguo);
                           boolean success = (boolean) s.get("success");
                           if (success){
                               JSONObject data = new JSONObject(content)
                                       .getJSONObject("data");
                               String s1 = data.toString();
                               JSONObject jsonObject=new JSONObject(s1);
                               edu =jsonObject.getString("minApplyDeposit");
                               handler.sendEmptyMessage(100);
                           }else{
                               handler.sendEmptyMessage(200);
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               } catch (NetException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }
    private  void requestData() {
        submit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String>map=new HashMap<>();
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
                map.put("money",money);
                map.put("remark",note);
                try {
                    //TODO 404
                    Response response = HttpClientUtil.doPost("http://115.29.172.200/remoting/rest/ws/express/applyRechargeDeposit.htm", map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if(response.getStatusCode()==200){
                        String content = response.getContent();
                        if (content != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(content);
                                meta = jsonObject.getJSONObject("meta");
                                if (meta.getBoolean("success")) {
                                    handler.sendEmptyMessage(300);
                                }
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
                    tv_zuidijine.setText(mTranslatesString.getCommon_zuidibaozhengjin()+":"+edu);
                    break;
                case 200:
                    try {
                        Toast.makeText(getApplication(), meta_json.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 300:
                        Toast.makeText(getApplication(), mTranslatesString.getCommon_sgenqingyes(), Toast.LENGTH_SHORT).show();
                        finish();
                    finish();
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //提交申请
                requestData();
                break;
        }
    }

    private void submit() {
        // validate
        money = tv_money.getText().toString().trim();
        note = tv_note.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, mTranslatesString.getCommon_dayubaozhengjin(), Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
