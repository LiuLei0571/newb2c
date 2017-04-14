package com.example.b2c.activity.logistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 待结算
 */
public class NoSttleAccountsActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private Button btn_go_jiesuan;
    private TextView tv_no_kjiesuan;
    private LinearLayout ll_guanliandingdan;
    private TextView tv_one;
    private TextView tv_guanliandingdan;
    private String data;
    private String meta;
    private JSONObject metas;

    @Override
    public int getRootId() {
        return R.layout.activity_no_sttle_accounts;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        btn_go_jiesuan = (Button) findViewById(R.id.btn_go_jiesuan);
        tv_no_kjiesuan = (TextView) findViewById(R.id.tv_no_kjiesuan);
        ll_guanliandingdan = (LinearLayout) findViewById(R.id.ll_guanliandingdan);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_guanliandingdan = (TextView) findViewById(R.id.tv_guanliandingdan);
        toolbar_title.setText(mTranslatesString.getCommon_tvdaijiesuan());
        tv_one.setText(mTranslatesString.getCommon_daijiesuanMoney()+"(ks)");
        tv_guanliandingdan.setText(mTranslatesString.getCommon_guanliandingdan());
        toolbar_back.setOnClickListener(this);
        btn_go_jiesuan.setOnClickListener(this);
        tv_guanliandingdan.setOnClickListener(this);
    }
    private JSONObject datas;

    private void initData(){
        //获取未结算的金额数
        new Thread(new Runnable() {


            @Override
            public void run() {
                Map<String,String>map=new HashMap<String, String>();
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
                map.put("status","1");
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "staff/getDeliveryFinanceTotalAmount.htm", map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode()== HttpStatus.SC_OK){

                        String content = response.getContent();
                    JSONObject jsonObject=new JSONObject(content);
                        meta = jsonObject.getJSONObject("meta").toString();
                        metas = new JSONObject(meta);
                    if (metas.getBoolean("success")){
                        data = jsonObject.getJSONObject("data").toString();
                        datas = new JSONObject(data);

                        handler.sendEmptyMessage(100);

                    }else{
                       handler.sendEmptyMessage(200);
                    }
                } }catch (NetException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.btn_go_jiesuan://去结算
//            startActivity(new Intent(getApplication(),SettlementDetailActivity.class));
                break;
            case R.id.tv_guanliandingdan://关联订单
                Intent intents = new Intent(getApplication(), SettleOrderListActivity.class);
                intents.putExtra("tag", "nojiesuan");
                startActivity(intents);
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    try {
                        tv_no_kjiesuan.setText(datas.getString("totalamount")+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    };
                    break;
                case 200:
                    try {
                        Toast.makeText(getApplication(),metas.getString("errorInfo"),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
