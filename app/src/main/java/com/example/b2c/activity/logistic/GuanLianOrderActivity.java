package com.example.b2c.activity.logistic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.zthHttp.MyHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GuanLianOrderActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditText et_guanlian;
    private TextView tv_guanlian;
    private String orderId;
    private JSONObject meta;
    private String tag;

    @Override
    public int getRootId() {
        return R.layout.activity_guan_lian_order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        et_guanlian = (EditText) findViewById(R.id.et_guanlian);
        tv_guanlian = (TextView) findViewById(R.id.tv_guanlian);
        tv_guanlian.setText(mTranslatesString.getCommon_makesure());
        tv_guanlian.setOnClickListener(this);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        orderId = intent.getStringExtra("orderId");
        if (tag.equals("1")) {
            toolbar_title.setText(mTranslatesString.getCommon_guanliandingdan());
            et_guanlian.setHint(mTranslatesString.getCommon_shurukuaidinumber());
        } else {
            et_guanlian.setHint(mTranslatesString.getComm_jushouyuanyin());
            toolbar_title.setText(mTranslatesString.getBuyer_refuse());
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guanlian:
                guanLian(orderId);

                break;
        }
    }
    private void guanLian(String id){
        final String guanlian = et_guanlian.getText().toString().trim();
        if (TextUtils.isEmpty(guanlian)) {
            Toast.makeText(this,mTranslatesString.getCommon_inputnotnull() , Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String>map=new HashMap<String, String>();
        map.put("orderId",id);
        if (tag.equals("1")){
            map.put("function","1");
            map.put("deliveryNo",guanlian);
        }else{
            map.put("function","3");
            map.put("rejectReason",guanlian);
        }
        mLogisticsDataConnection.guanlian(map);
        mLogisticsDataConnection.mNodataListener=new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                    Intent in=new Intent("tagg");
                    sendBroadcast(in);
                    Toast.makeText(getApplication(),success , Toast.LENGTH_SHORT).show();
                    finish();
            }
            @Override
            public void onError(int errorNo, String errorInfo) {
                Toast.makeText(getApplication(),errorInfo , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {}

            @Override
            public void onLose() {}
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
