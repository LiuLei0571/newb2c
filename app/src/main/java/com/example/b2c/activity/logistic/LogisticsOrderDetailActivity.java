package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.logistics.OrderDetailBean;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.widget.ListViewForScrollView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class LogisticsOrderDetailActivity extends TempBaseActivity {


    private TextView toolbar_title;
    private TextView tv_title_shangpinxiangqing;
    private ImageView iv_shangpin_tupian;
    private TextView tv_title_name;
    private TextView tv_create_shop;
    private TextView tv_title_delivery_no;
    private TextView tv_create_express_number;
    private TextView tv_chanpin_num;
    private TextView tv_create_money;
    private TextView tv_create_time;
    private TextView tv_title_detail;
    private TextView tv_buyer_detail;
    private TextView tv_detail_phone_maijia;
    private TextView tv_title_detailr;
    private TextView tv_detail_message;
    private TextView tv_shoukuan_phone_detail;
    private TextView tv_detail_shoukuan;
    private TextView tv_title_shouhuo_detail;
    private TextView tv_detail_shouhuo;
    private TextView tv_shouhuo_phone_detail;
    private TextView tv_maijia_shouhuo_detail;
    private TextView tv_express;
    private TextView tv_title_wuliu;
    private ListViewForScrollView list;
    private JSONObject data;
    private OrderDetailBean datas;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_order_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_orderdetail());
        initView();
        initText();
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
    }

    private void initText() {
        tv_title_shangpinxiangqing.setText(mTranslatesString.getCommon_sampledetail());
        tv_title_name.setText(mTranslatesString.getSeller_shopname());
        tv_title_delivery_no.setText(mTranslatesString.getExpress_no());
        tv_title_wuliu.setText(mTranslatesString.getExpress_detail1());
        tv_title_detail.setText(mTranslatesString.getCommon_sellermessage());
        tv_title_detailr.setText(mTranslatesString.getConnom_shoukuandizhi());
        tv_title_shouhuo_detail.setText(mTranslatesString.getCommon_receivelocation());
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_title_shangpinxiangqing = (TextView) findViewById(R.id.tv_title_shangpinxiangqing);
        iv_shangpin_tupian = (ImageView) findViewById(R.id.iv_shangpin_tupian);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_create_shop = (TextView) findViewById(R.id.tv_create_shop);
        tv_title_delivery_no = (TextView) findViewById(R.id.tv_title_delivery_no);
        tv_create_express_number = (TextView) findViewById(R.id.tv_create_express_number);
        tv_chanpin_num = (TextView) findViewById(R.id.tv_chanpin_num);
        tv_create_money = (TextView) findViewById(R.id.tv_create_money);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        tv_title_detail = (TextView) findViewById(R.id.tv_title_detail);
        tv_buyer_detail = (TextView) findViewById(R.id.tv_buyer_detail);
        tv_detail_phone_maijia = (TextView) findViewById(R.id.tv_detail_phone_maijia);
        tv_title_detailr = (TextView) findViewById(R.id.tv_title_detailr);
        tv_detail_message = (TextView) findViewById(R.id.tv_detail_message);
        tv_shoukuan_phone_detail = (TextView) findViewById(R.id.tv_shoukuan_phone_detail);
        tv_detail_shoukuan = (TextView) findViewById(R.id.tv_detail_shoukuan);
        tv_title_shouhuo_detail = (TextView) findViewById(R.id.tv_title_shouhuo_detail);
        tv_detail_shouhuo = (TextView) findViewById(R.id.tv_detail_shouhuo);
        tv_shouhuo_phone_detail = (TextView) findViewById(R.id.tv_shouhuo_phone_detail);
        tv_maijia_shouhuo_detail = (TextView) findViewById(R.id.tv_maijia_shouhuo_detail);
        tv_express = (TextView) findViewById(R.id.tv_express);
        tv_title_wuliu = (TextView) findViewById(R.id.tv_title_wuliu);
        list = (ListViewForScrollView) findViewById(R.id.list);
    }
    private void initData(){

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        Map<String,String>map=new HashMap<>();
//        map.put("orderId",position+"");
        map.put("orderId",position+"");
        String s = UserHelper.getExpressLoginId() + "";
        map.put("deliveryCompanyId",s);//225,263

        try {//
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+"express/orderDetail.htm", map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode()==200){
                String content = response.getContent();
                Gson gson=new Gson();
                if (content != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        JSONObject meta = jsonObject.getJSONObject("meta");
                        if (meta.getBoolean("success")) {
                            data = jsonObject.getJSONObject("data");
                            datas = gson.fromJson(data.toString(), OrderDetailBean.class);
                            handler.sendEmptyMessage(100);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NetException e) {
            dissLoading();
            e.printStackTrace();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    try {
                        dissLoading();
                        if (!TextUtils.isEmpty(datas.getImage())) {
                            ImageHelper.display(ConstantS.BASE_PIC_URL+datas.getImage(), iv_shangpin_tupian);
                        }
                        tv_create_time.setText(datas.getCreateTime());
                        tv_create_shop.setText(datas.getShopName());
                        tv_create_express_number.setText(datas.getDeliveryNo());
                        int amount = data.getInt("amout");
                        tv_chanpin_num.setText(amount + mTranslatesString.getCommon_chanpinnumber());
                        //TODO
                        tv_create_money.setText("Ks:" + String.valueOf(datas.getTotalPrice())+"("+mTranslatesString.getCommon_youhui()+":Ks"+datas.getDiscount()+")");
                        tv_buyer_detail.setText(datas.getShopName());//卖家名字显示店铺名称
                        String sellerMobile = datas.getSellerMobile();
                        tv_detail_phone_maijia.setText(sellerMobile);
                        tv_detail_message.setText(datas.getPayLastName() + datas.getPayFirstName());
                        tv_shoukuan_phone_detail.setText(datas.getPayMobile());
                        tv_detail_shoukuan.setText(datas.getPayAddress());
                        tv_detail_shouhuo.setText(datas.getReceiveLastName() + datas.getReceiveFirstName());
                        tv_shouhuo_phone_detail.setText(datas.getReceiveMobile());
                        tv_maijia_shouhuo_detail.setText(datas.getReceiveAddress());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
