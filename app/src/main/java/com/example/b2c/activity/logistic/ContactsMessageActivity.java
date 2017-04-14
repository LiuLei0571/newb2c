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
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * 联系人信息
 */
public class ContactsMessageActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditText tv_geren_xing;
    private EditText tv_geren_ming;
    private EditText tv_geren_emale;
    private EditText tv_geren_phone;
    private EditText tv_geren_dianhua;
    private EditText tv_jianjie;
    private TextView toolbar_right_text;
    private TextView tv_xing_q;
    private TextView tv_ming_q;
    private TextView tv_youxiang_q;
    private TextView tv_phone_q;
    private TextView tv_dianhua_q;
    private TextView tv_lianxidizhi_q;
    private JSONObject data;

    @Override
    public int getRootId() {
        return R.layout.activity_linkman_message;
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
        tv_geren_xing = (EditText) findViewById(R.id.tv_geren_xing);
        tv_geren_ming = (EditText) findViewById(R.id.tv_geren_ming);
        tv_geren_emale = (EditText) findViewById(R.id.tv_geren_emale);
        tv_geren_phone = (EditText) findViewById(R.id.tv_geren_phone);
        tv_geren_dianhua = (EditText) findViewById(R.id.tv_geren_dianhua);
        tv_jianjie = (EditText) findViewById(R.id.tv_lainxidizhi);
        toolbar_title.setText(mTranslatesString.getCommon_lianxirenxinxi());
        toolbar_right_text.setText(mTranslatesString.getCommon_save());
        toolbar_right_text.setOnClickListener(this);
        tv_xing_q = (TextView) findViewById(R.id.tv_xing_q);
        tv_ming_q = (TextView) findViewById(R.id.tv_ming_q);
        tv_youxiang_q = (TextView) findViewById(R.id.tv_youxiang_q);
        tv_phone_q = (TextView) findViewById(R.id.tv_phone_q);
        tv_dianhua_q = (TextView) findViewById(R.id.tv_dianhua_q);
        tv_lianxidizhi_q = (TextView) findViewById(R.id.tv_lianxidizhi_q);
        tv_xing_q.setText(mTranslatesString.getCommon_xing());
        tv_ming_q.setText(mTranslatesString.getCommon_ming());
        tv_youxiang_q.setText(mTranslatesString.getCommon_email());
        tv_phone_q.setText(mTranslatesString.getCommon_tellphone());
        tv_dianhua_q.setText(mTranslatesString.getCommon_dianhua());
        tv_lianxidizhi_q.setText(mTranslatesString.getCommon_lianxidizhi());
    }
    private JSONObject meta;
    private void initData(){
        new Thread(new Runnable() {



            @Override
            public void run() {
                try {
                    String sExpressToken = UserHelper.getSExpressToken();
                    Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_LINSHI+ "express/deliveryCompanyContact/"+UserHelper.getExpressLoginId()+".htm", true, UserHelper.getExpressID(),sExpressToken );
                    if (response.getStatusCode()== HttpStatus.SC_OK) {
                        String content = response.getContent();
                        JSONObject json = new JSONObject(content);
                        meta = json.getJSONObject("meta");
                        boolean success = meta.getBoolean("success");
                        if (success) {
                            data = json.getJSONObject("data");
                            handler.sendEmptyMessage(100);
                        } else {
                            handler.sendEmptyMessage(200);
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
    private void requestData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String xing = tv_geren_xing.getText().toString().trim();
                String mings = tv_geren_ming.getText().toString().trim();
                String emale = tv_geren_emale.getText().toString().trim();
                String phone = tv_geren_phone.getText().toString().trim();
                String dianhua = tv_geren_dianhua.getText().toString().trim();
                String dizhi = tv_jianjie.getText().toString().trim();
//                if (TextUtils.isEmpty(xing)||TextUtils.isEmpty(mings)||TextUtils.isEmpty(emale)||TextUtils.isEmpty(phone)||
//                        TextUtils.isEmpty(dianhua)||TextUtils.isEmpty(dizhi)){
//                    handler.sendEmptyMessage(200);
//                    return;
//                }else{
                    try {
                    Map<String,String>map=new HashMap<String, String>();
                    map.put("contactFirstName",mings);
                    map.put("contactLastName",xing);
                    map.put("contactEmail",emale);
                    map.put("contactMobile",phone);
                    map.put("contactTel",dianhua);
                    map.put("address",dizhi);
                    map.put("contactProvinceCode",data.getString("ContactProvinceCode"));
                    map.put("contactCityCode",data.getString("ContactCityCode"));
                    map.put("deliveryCompanyId", UserHelper.getExpressLoginId()+"");

                        Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "express/saveDeliveryCompanyContact.htm",
                                map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken()
                        );
                        String content = response.getContent();
                        JSONObject jsonObject=new JSONObject(content);
                         meta = jsonObject.getJSONObject("meta");
                        boolean success = meta.getBoolean("success");
                        if (success){
                            handler.sendEmptyMessage(300);
                        }else{
                            handler.sendEmptyMessage(200);
                        }
                    } catch (NetException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
//                    }
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
                        tv_geren_xing.setText(data.getString("ContactLastName"));
                        tv_geren_ming.setText(data.getString("ContactFirstName"));
                        tv_geren_emale.setText(data.getString("ContactEmail"));
                        tv_geren_phone.setText(data.getString("ContactMobile"));
                        tv_geren_dianhua.setText(data.getString("ContactTel"));
                        tv_jianjie.setText(data.getString("Address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 200:
//                    Toast.makeText(getApplication(),mTranslatesString.getComon_xinxibuwanzheng(),Toast.LENGTH_SHORT).show();
                    try {
                        String errorInfo = meta.getString("errorInfo");
                        Toast.makeText(getApplication(), errorInfo, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 300:
                    Toast.makeText(getApplication(),mTranslatesString.getCommon_baocunchenggong(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_text:
               requestData();
                break;

        }
    }
}
