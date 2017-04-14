package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.RPAddressResult;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.HashMap;
import java.util.Map;


/**
 * 买家下单选择地址信息
 */
public class SelectAddressActivity extends TempBaseActivity implements View.OnClickListener{

    private final static  int  REQUESTRECEIVE  =  10;
    private final static  int  REQUESTPAY  =  20;
    private TextView tv_receive_address_label;
    private TextView tv_receive_name;
    private TextView tv_receive_mobile;
    private TextView tv_receive_address;
    private LinearLayout ll_receive_address;
    private TextView tv_pay_address_label;
    private TextView tv_pay_name;
    private TextView tv_pay_mobile;
    private TextView tv_pay_address;
    private TextView cb_isDefault_text;
    private LinearLayout ll_pay_address;


    private CheckBox cb_isDefault;
    private LinearLayout ll_check;


    /**
     * 收款收货
     */
    private ShoppingAddressDetail receive_address,pay_address;
    /**
     * 收款收货id
     */
    private int receive_address_id,pay_address_id;


    @Override
    public int getRootId() {
        return R.layout.activity_select_address;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initUI() {
        if(receive_address != null){
            tv_receive_name.setText(receive_address.getFirstName()+" "+receive_address.getLastName());
            tv_receive_mobile.setText(receive_address.getMobile());
            tv_receive_address.setText(receive_address.getProvinceName()+" "+receive_address.getCityName()+" "+receive_address.getAddress());
        }
        if(pay_address != null){
            tv_pay_name.setText(pay_address.getFirstName()+" "+pay_address.getLastName());
            tv_pay_mobile.setText(pay_address.getMobile());
            tv_pay_address.setText(pay_address.getProvinceName()+" "+pay_address.getCityName()+" "+pay_address.getAddress());
        }else {
            tv_pay_name.setText("未设置");
            tv_pay_mobile.setText("未设置");
            tv_pay_address.setText("未设置");
        }
    }

    private void initData() {
        showLoading();
        Map map = new HashMap();
        map.put("shoppingAddressId",receive_address_id);
        map.put("payAddressId",pay_address_id);
        rdm.getReceivePayAddr(map);
        rdm.mOneDataListener = new OneDataListener<RPAddressResult>() {
            @Override
            public void onSuccess(RPAddressResult data, String successInfo) {
                receive_address = data.getShoppingAddress();
                pay_address = data.getPayAddress();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                    }
                });
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
                ToastHelper.makeErrorToast(request_failure);
            }
        };

    }

    private void initText() {
        setTitle("选择地址");
        tv_receive_address_label.setText("收货地址");
        tv_pay_address_label.setText("收款地址");
        cb_isDefault_text.setText("收货和收款相同");
    }

    private void initView() {
        receive_address_id = getIntent().getIntExtra("rId",-1);
        pay_address_id = getIntent().getIntExtra("pId",-1);
        tv_receive_address_label = (TextView) findViewById(R.id.tv_receive_address_label);
        tv_receive_name = (TextView) findViewById(R.id.tv_receive_name);
        tv_receive_mobile = (TextView) findViewById(R.id.tv_receive_mobile);
        tv_receive_address = (TextView) findViewById(R.id.tv_receive_address);

        ll_receive_address = (LinearLayout) findViewById(R.id.ll_receive_address);
        tv_pay_address_label = (TextView) findViewById(R.id.tv_pay_address_label);
        tv_pay_name = (TextView) findViewById(R.id.tv_pay_name);
        tv_pay_mobile = (TextView) findViewById(R.id.tv_pay_mobile);
        tv_pay_address = (TextView) findViewById(R.id.tv_pay_address);

        ll_pay_address = (LinearLayout) findViewById(R.id.ll_pay_address);
        cb_isDefault = (CheckBox) findViewById(R.id.cb_isDefault);
        cb_isDefault_text = (TextView) findViewById(R.id.cb_isDefault_text);
        ll_check = (LinearLayout) findViewById(R.id.ll_check);
        if(pay_address_id == -1){
            cb_isDefault.setChecked(true);
            ll_pay_address.setVisibility(View.GONE);
        }
        ll_receive_address.setOnClickListener(this);
        ll_pay_address.setOnClickListener(this);
        ll_check.setOnClickListener(this);
        addText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("receiveAddress",receive_address);
                if(!cb_isDefault.isChecked()){
                    intent.putExtra("payAddress",pay_address);
                }
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_receive_address:
                Intent i_r_address = new Intent(SelectAddressActivity.this,AddrSelectActivity.class);
                i_r_address.putExtra("addressType",10);
                startActivityForResult(i_r_address,REQUESTRECEIVE);
                break;
            case R.id.ll_pay_address:
                Intent i_p_address = new Intent(SelectAddressActivity.this,AddrSelectActivity.class);
                i_p_address.putExtra("addressType",20);
                startActivityForResult(i_p_address,REQUESTPAY);
                break;
            case R.id.ll_check:
                if(cb_isDefault.isChecked()){
                    ll_pay_address.setVisibility(View.VISIBLE);
                    cb_isDefault.setChecked(false);
                }else {
                    ll_pay_address.setVisibility(View.GONE);
                    cb_isDefault.setChecked(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTRECEIVE && resultCode== RESULT_OK){
            receive_address_id = data.getIntExtra("addressId",-1);
        }else if(requestCode == REQUESTPAY && resultCode== RESULT_OK){
            pay_address_id = data.getIntExtra("addressId",-1);
        }
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
