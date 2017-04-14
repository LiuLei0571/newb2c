package com.example.b2c.activity.logistic;

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
 * 结算账号
 */
public class SettlementAccountNumberActivity extends TempBaseActivity implements View.OnClickListener {


    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private EditText tv_kaihuyinhang;
    private EditText tv_zhihangyinhang;
    private EditText tv_yinhangname;
    private EditText tv_yinhanguser;
    private TextView tv_kaihu;
    private TextView tv_zhihang;
    private TextView tv_kaihuname;
    private TextView tv_kaihunum;
    private JSONObject data;
    private JSONObject meta;

    @Override
    public int getRootId() {
        return R.layout.activity_settlement_account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        toolbar_right_text.setOnClickListener(this);
        tv_kaihuyinhang = (EditText) findViewById(R.id.tv_kaihuyinhang);
        tv_zhihangyinhang = (EditText) findViewById(R.id.tv_zhihangyinhang);
        tv_yinhangname = (EditText) findViewById(R.id.tv_yinhangname);
        tv_yinhanguser = (EditText) findViewById(R.id.tv_yinhanguser);
        toolbar_title.setText(mTranslatesString.getCommon_jiesuanzhanghao());
        toolbar_right_text.setText(mTranslatesString.getCommon_save());
        tv_kaihu = (TextView) findViewById(R.id.tv_kaihu);
        tv_zhihang = (TextView) findViewById(R.id.tv_zhihang);
        tv_kaihuname = (TextView) findViewById(R.id.tv_kaihuname);
        tv_kaihunum = (TextView) findViewById(R.id.tv_kaihunum);
        tv_kaihu.setText(mTranslatesString.getCommon_kaihuyinhang());
        tv_zhihang.setText(mTranslatesString.getCommon_zhihangyinhang());
        tv_kaihuname.setText(mTranslatesString.getCommon_yinhangname());
        tv_kaihunum.setText(mTranslatesString.getCommon_yinhangzhanghu());
    }
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sExpressToken = UserHelper.getSExpressToken();
                    Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_LINSHI+ "express/deliveryCompanyBankDetail/"+UserHelper.getExpressLoginId()+".htm", true, UserHelper.getExpressID(),sExpressToken );
                    if (response.getStatusCode()== HttpStatus.SC_OK) {

                        String content = response.getContent();
                        JSONObject json = new JSONObject(content);
                        meta = json.getJSONObject("meta");
                        boolean success = meta.getBoolean("success");
                        if (success) {
                            data = json.getJSONObject("data");
                            handler.sendEmptyMessage(100);
                        } else {
                           handler.sendEmptyMessage(300);
                        }
                    }
                }  catch (NetException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
                        tv_kaihuyinhang.setText(data.getString("BankName"));
                        tv_zhihangyinhang .setText(data.getString("BankBranchName"));
                        tv_yinhangname .setText(data.getString("BankAccountName"));
                        tv_yinhanguser.setText(data.getString("BankAccount"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 200:
                    Toast.makeText(getApplication(),mTranslatesString.getCommon_baocunchenggong(),Toast.LENGTH_SHORT).show();
                    break;
                case 300:
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
           case  R.id.toolbar_right_text:
               final String kaihuyinhang = tv_kaihuyinhang.getText().toString().trim();
               final String zhihangyinhang = tv_zhihangyinhang.getText().toString().trim();
               final String yinhangname = tv_yinhangname.getText().toString().trim();
               final String yinhanguser = tv_yinhanguser.getText().toString().trim();
               if (TextUtils.isEmpty(kaihuyinhang)||TextUtils.isEmpty(zhihangyinhang)||
                       TextUtils.isEmpty(yinhangname)||TextUtils.isEmpty(yinhanguser)){
                   Toast.makeText(getApplication(),mTranslatesString.getCommon_inputnotnull(),Toast.LENGTH_LONG).show();
                   return;
               }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestData(kaihuyinhang,zhihangyinhang,yinhangname,yinhanguser);
                    }
                }).start();
            break;
        }
    }

    private void requestData(String kaihuyinhang,String zhihangyinhang,String yinhangname,String yinhanguser) {

            try {
                Map<String,String>map=new HashMap<>();
                map.put("bankName",kaihuyinhang);
                map.put("bankBranchName",zhihangyinhang);
                map.put("bankAccountName",yinhangname);
                map.put("bankAccount",yinhanguser);
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");

                Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "express/saveDeliveryCompanyBankDetail.htm",
                        map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                String content = response.getContent();
                JSONObject jsonObject=new JSONObject(content);
                JSONObject meta = jsonObject.getJSONObject("meta");
                boolean success = meta.getBoolean("success");
                if (success){
                    handler.sendEmptyMessage(200);
                }else{
                    handler.sendEmptyMessage(300);
                }
            } catch (NetException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();

        }


    }
}
