package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.logistics.AccessBean;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.helper.gunlun.LoopView;
import com.example.b2c.helper.gunlun.OnItemSelectedListener;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费模板详情
 */
public class FreightTemplateDetailsActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView toolbar_right_text;

    private EditText tv_zhongliang1;
    private EditText tv_zhongliang2;
    private EditText tv_tianshu;
    private EditText tv_feiyong;
    private TextView tv_delete_muban;
    private AccessBean accessBean;
    private List<AccessBean.CodeList> codeList;
    private List<String> dizhi;
    private List<String> youbianList;
    private TextView tv_title_fahuoprovince;
    private TextView tv_fahuo_province;
    private LinearLayout ll_fahuo_province;
    private TextView tv_title_fahuocity;
    private TextView tv_fahuo_city;
    private LinearLayout ll_fahuo_city;
    private TextView tv_title_shouhuoprovince;
    private TextView tv_shouhuo_province;
    private LinearLayout ll_shouhuo_province;
    private TextView tv_title_shouhuocity;
    private TextView tv_shouhuo_city;
    private LinearLayout ll_shouhuo_city;
    private AlertDialog aa;
    private Response response;

    @Override
    public int getRootId() {
        return R.layout.activity_freight_template_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        dizhi = new ArrayList<>();
        youbianList = new ArrayList<>();
        //请求所有省市接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestAccess(0,null);
            }
        }).start();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        tv_zhongliang1 = (EditText) findViewById(R.id.tv_zhongliang1);
        tv_zhongliang2 = (EditText) findViewById(R.id.tv_zhongliang2);
        tv_tianshu = (EditText) findViewById(R.id.tv_tianshu);
        tv_feiyong = (EditText) findViewById(R.id.tv_feiyong);
        tv_delete_muban = (TextView) findViewById(R.id.tv_delete_muban);
        toolbar_right_text.setText(mTranslatesString.getCommon_save());
        toolbar_title.setText(mTranslatesString.getCommon_yunfeimuban());
        toolbar_right_text.setOnClickListener(this);
        tv_title_fahuoprovince = (TextView) findViewById(R.id.tv_title_fahuoprovince);
        tv_fahuo_province = (TextView) findViewById(R.id.tv_fahuo_province);
        ll_fahuo_province = (LinearLayout) findViewById(R.id.ll_fahuo_province);
        ll_fahuo_province.setOnClickListener(this);
        tv_title_fahuocity = (TextView) findViewById(R.id.tv_title_fahuocity);
        tv_fahuo_city = (TextView) findViewById(R.id.tv_fahuo_city);
        ll_fahuo_city = (LinearLayout) findViewById(R.id.ll_fahuo_city);
        ll_fahuo_city.setOnClickListener(this);
        tv_title_shouhuoprovince = (TextView) findViewById(R.id.tv_title_shouhuoprovince);
        tv_shouhuo_province = (TextView) findViewById(R.id.tv_shouhuo_province);
        ll_shouhuo_province = (LinearLayout) findViewById(R.id.ll_shouhuo_province);
        ll_shouhuo_province.setOnClickListener(this);
        tv_title_shouhuocity = (TextView) findViewById(R.id.tv_title_shouhuocity);
        tv_shouhuo_city = (TextView) findViewById(R.id.tv_shouhuo_city);
        ll_shouhuo_city = (LinearLayout) findViewById(R.id.ll_shouhuo_city);
        ll_shouhuo_city.setOnClickListener(this);
    }

    /**
     * 获取所有省市接口
     */
    private void requestAccess(int tag,String code) {
        try {
            if (code==null) {
                response = HttpClientUtil.doPost(ConstantS.BASE_URL + "common/listAllProvince.htm", null, false, 0, null);
            }else{
                Map<String,String> map=new HashMap<>();
                map.put("code",code);
                response=HttpClientUtil.doPost(ConstantS.BASE_URL + "common/listAllProvince.htm", map, false, 0, null);
            }
                int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                Gson gson = new Gson();
                String content = response.getContent();
                JSONObject json = new JSONObject(content);
                JSONObject meta = json.getJSONObject("meta");
                boolean success = meta.getBoolean("success");
                if (success) {
                    JSONObject data = json.getJSONObject("data");
                    accessBean = gson.fromJson(data.toString(), AccessBean.class);
                    codeList = accessBean.getCodeList();
                    for (int i = 0; i < codeList.size(); i++) {
                        dizhi.add(codeList.get(i).getText());
                        youbianList.add(codeList.get(i).getValue());
                    }
                    if (code!=null) {
                        //TODO  已经获取到所有的省放到dizhi的集合中，下面就是发送handler显示dialog
                       if (tag==2){//市
                           handler.sendEmptyMessage(200);
                       }if (tag==3){
                            //收货市
                            handler.sendEmptyMessage(300);
                        }
                        if (tag==4){
                            handler.sendEmptyMessage(400);
                        }

                    }
                } else {
                    handler.sendEmptyMessage(100);
                }
            }
        } catch (NetException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    showMyDiolag(2,dizhi,youbianList,tv_fahuo_city);
                    break;
                case 300:
                    showMyDiolag(3,dizhi,youbianList,tv_shouhuo_province);
                    break;
                case 400:
                    showMyDiolag(4,dizhi,youbianList,tv_shouhuo_city);
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_text:
                //保存
                save();
                break;
            case R.id.ll_fahuo_province:
                showMyDiolag(1,dizhi,youbianList,tv_fahuo_province);
                break;
            case R.id.ll_fahuo_city:
                if (fahuoprovinceNumber==null){
                    Toast.makeText(getApplication(),mTranslatesString.getCommon_inputnotnull(),Toast.LENGTH_SHORT).show();
                    return;
                }
                requestAccess(2,fahuoprovinceNumber);
                break;
            case R.id.ll_shouhuo_province:
                showMyDiolag(3,dizhi,youbianList,tv_fahuo_province);
                break;
            case R.id.ll_shouhuo_city:
                if (fahuoprovinceNumber==null){
                    Toast.makeText(getApplication(),mTranslatesString.getCommon_inputnotnull(),Toast.LENGTH_SHORT).show();
                    return;
                }
                requestAccess(4,fahuoprovinceNumber);
                break;
        }
    }
    private void save(){
        String tianshu = tv_tianshu.getText().toString().trim();
        String tv_zhongliang1s = tv_zhongliang1.getText().toString().trim();
        String tv_zhongliang2s = tv_zhongliang2.getText().toString().trim();
        if (TextUtils.isEmpty(tianshu)) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tv_zhongliang1s)) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tv_zhongliang2s)) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        String feiyong = tv_feiyong.getText().toString().trim();
        if (TextUtils.isEmpty(feiyong)) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String ,String>map=new HashMap<>();
        map.put("shippingCityCode",fahuoCityNumber);
        map.put("receiveProvinceCode",fahuoprovinceNumber);
        map.put("receiveCityCode",shouhuocityNumbre);
        map.put("shippingProvinceCode",shouhuoprovinceNumber);
        map.put("fee",feiyong);
        map.put("maxWeight",tv_zhongliang2s);
        map.put("minWeight",tv_zhongliang1s);
        map.put("deliveryTimeCost",tianshu);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "express/addDefaultFreightTemplate.htm",
                    map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken()
            );
            if (response.getStatusCode()==200){

            }
        } catch (NetException e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        // validate

    }
    /**
     * 显示选择器的对话框
     * @pama title 对话框的标题
     */
    //记录滚轮的位置
    private int position;
    //记录滚动处的文字
    private String fahuoprovinceNumber;
    private String fahuoCityNumber;
    //记录滚动处的编号
    private String shouhuoprovinceNumber;
    private String shouhuocityNumbre;
    public void showMyDiolag(final int tag, final List<String>titles, final List<String>numbers, final TextView tv)
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle(title);
        final View inflate = LayoutInflater.from(this).inflate(R.layout.adapter_gun_lun, null);
        final LoopView gunlunOne= (LoopView) inflate.findViewById(R.id.one);
        final LoopView gunlunTwo= (LoopView) inflate.findViewById(R.id.two);
        final LoopView gunlunTHree= (LoopView) inflate.findViewById(R.id.three);
        TextView tv_dialog_queding= (TextView) inflate.findViewById(R.id.tv_dialog_queding);
        TextView tv_dialog_quxiao= (TextView) inflate.findViewById(R.id.tv_dialog_quxiao);
        gunlunOne.setVisibility(View.GONE);
        gunlunTwo.setVisibility(View.VISIBLE);
        gunlunTHree.setVisibility(View.GONE);
        gunlunTwo.setItems(titles);
        gunlunTwo.setInitPosition(0);
        gunlunTwo.setTextSize(30);
        gunlunTwo.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position = index;
                fahuoprovinceNumber=numbers.get(index);
            }
        });
        tv_dialog_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fahuoprovinceNumber==null){
                    //说明没有滑动
                    tv.setText(titles.get(0));
//                    fahuoprovinceNumber=numbers.get(0);
//                    provinceName=
                    if (tag==1){
                        //说明是点击省,而且是发货地
                        fahuoprovinceNumber=numbers.get(0);
                    }if (tag==2){
                        //说明是点击省,而且是发货地
                        fahuoCityNumber=numbers.get(0);
                    }if (tag==3){
                        shouhuoprovinceNumber=numbers.get(0);
                    }if(tag==4){
                        shouhuocityNumbre=numbers.get(0);
                    }
                }else{
                    tv.setText(titles.get(position));
                    if (tag==1){
                        //说明是点击省,而且是发货地
                        fahuoprovinceNumber=numbers.get(position);
                    }if (tag==2){
                        //说明是点击省,而且是发货地
                        fahuoCityNumber=numbers.get(position);
                    }if (tag==3){
                        shouhuoprovinceNumber=numbers.get(position);
                    }if(tag==4){
                        shouhuocityNumbre=numbers.get(position);
                    }
                }

                aa.dismiss();
            }
        });
        tv_dialog_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=0;
                fahuoCityNumber=null;
                fahuoprovinceNumber=null;
                shouhuoprovinceNumber=null;
                shouhuoprovinceNumber=null;
                aa.dismiss();
            }
        });
        builder.setView(inflate);
        aa = builder.show();
        numbers.clear();
        titles.clear();
    }

}
