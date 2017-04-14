package com.example.b2c.activity.logistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.PicturePreviewActivity;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.HttpClientUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公司信息
 */
public class CompanyMessageActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView tv_kaudi_name;
    private TextView tv_kaudi_name_q;
    private TextView tv_gongsi_name;
    private TextView tv_gongsi_name_q;
    private TextView tv_zhuce_dizhi;
    private TextView tv_zhuce_dizhi_q;
    private TextView tv_zhuce_time;
    private TextView tv_zhuce_time_q;
    private TextView tv_renshu;
    private TextView tv_renshu_q;
    private TextView tv_jianjie;
    private TextView tv_jianjie_q;
    private LinearLayout ll_yingyezhizhao;
    private TextView ll_yingyezhizhao_q;
    private JSONObject data;
    private JSONObject meta;

    @Override
    public int getRootId() {
        return R.layout.activity_company_message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();

    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_kaudi_name = (TextView) findViewById(R.id.tv_kaudi_name);
        tv_kaudi_name_q = (TextView) findViewById(R.id.tv_kaudi_name_q);
        tv_gongsi_name = (TextView) findViewById(R.id.tv_gongsi_name);
        tv_gongsi_name_q = (TextView) findViewById(R.id.tv_gongsi_name_q);
        tv_zhuce_dizhi = (TextView) findViewById(R.id.tv_zhuce_dizhi);
        tv_zhuce_dizhi_q = (TextView) findViewById(R.id.tv_zhuce_dizhi_q);
        tv_zhuce_time = (TextView) findViewById(R.id.tv_zhuce_time);
        tv_zhuce_time_q = (TextView) findViewById(R.id.tv_zhuce_time_q);
        tv_renshu = (TextView) findViewById(R.id.tv_renshu);
        tv_renshu_q = (TextView) findViewById(R.id.tv_renshu_q);
        tv_jianjie = (TextView) findViewById(R.id.tv_jianjie);
        tv_jianjie_q = (TextView) findViewById(R.id.tv_jianjie_q);
        ll_yingyezhizhao = (LinearLayout) findViewById(R.id.ll_yingyezhizhao);
        ll_yingyezhizhao_q = (TextView) findViewById(R.id.ll_yingyezhizhao_q);
        toolbar_title.setText(mTranslatesString.getCommon_gongsixinxi());
        tv_kaudi_name_q.setText(mTranslatesString.getCommon_deliveryname());
        tv_gongsi_name_q.setText(mTranslatesString.getCommon_gongsixinxi());
        tv_zhuce_dizhi_q.setText(mTranslatesString.getCommon_zhucedizhi());
        tv_zhuce_time_q.setText(mTranslatesString.getCommon_zhucetime());
        tv_renshu_q.setText(mTranslatesString.getCommon_gongsinumber());
        tv_jianjie_q.setText(mTranslatesString.getCommon_profile());
        ll_yingyezhizhao_q.setText(mTranslatesString.getCommon_yingyezhizhao());
        ll_yingyezhizhao.setOnClickListener(this);
    }
    private void initData() {
//        Map<String,String>map=new HashMap<>();
//        map.put("deliveryCompanyId", UserHelper.getExpressLoginId()+"");
        try {
            String sExpressToken = UserHelper.getSExpressToken();
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_LINSHI+ "express/deliveryCompanyDetail/"+UserHelper.getExpressLoginId()+".htm", true, UserHelper.getExpressID(),sExpressToken );
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yingyezhizhao:
                //显示营业执照
                //跳转到大图界面
                try {
                    test(ConstantS.BASE_PIC_URL+data.getString("BusinessLicenseAttachment"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 获取网络图片进行大图浏览
     */
    private void test(String url) {
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        intent.putExtra("url", url);
        // intent.putExtra("smallPath", getSmallPath());
        intent.putExtra("indentify", getIdentify());
        this.startActivity(intent);
    }
    private int getIdentify() {
        return getResources().getIdentifier("test", "drawable",
                getPackageName());
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    try {
                        tv_kaudi_name.setText(data.getString("DeliveryName"));
                        tv_gongsi_name.setText(data.getString("CompanyName"));
                        tv_zhuce_dizhi.setText(data.getString("RegAddress"));
                        String regDate1 = data.getString("RegDate");

                        tv_zhuce_time.setText(regDate1);
                        tv_renshu.setText(data.getInt("TotalPersonNum")+"");
                        String introduction = data.getString("Introduction");
                        tv_jianjie.setText(introduction);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 200:
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
