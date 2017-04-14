package com.example.b2c.fragment.logistics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.logistic.ChildAccountListActivity;
import com.example.b2c.activity.logistic.FreightTemplateListActivity;
import com.example.b2c.activity.logistic.LogisticsCompanyMessageActivity;
import com.example.b2c.activity.logistic.SettingActivity;
import com.example.b2c.activity.logistic.SettleAccountsCenterActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.ZTHBaseFragment;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.widget.CircleImg;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 用途：
 * Created by milk on 16/11/11.
 * 邮箱：649444395@qq.com
 */

public class HomeMinFragment extends ZTHBaseFragment implements View.OnClickListener {
    private ImageButton toolbar_back;
    private TextView toolbar_right_text;
    private ImageView yuan_image;
    private TextView tv_mine_logisttics_nicheng;
    private TextView tv_mine_logisttics_name;
    private TextView tv_mine_logisttics_gongsi;
    private TextView jiesuanzhongxin;
    private TextView zizhanghaoguanli;
    private TextView wuliugongsi;
    private TextView yunfeimoban;
    private RelativeLayout rl_logistics_jiesuan;
    private RelativeLayout rl_logistics_zizhanghao_message;
    private RelativeLayout rl_logistics_wuliuxinxi;
    private RelativeLayout rl_logistics_yunfei;
    private JSONObject meta;
    private JSONObject data;
    private TextView title;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_logistics_mine;
    }

    @Override
    public void initView(View view) {
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText(mTranslatesString.getLogistic_accountmanage());
        toolbar_back = (ImageButton) view.findViewById(R.id.toolbar_back);
        toolbar_right_text = (TextView) view.findViewById(R.id.toolbar_right_text);
        yuan_image = (ImageView) view.findViewById(R.id.yuan_image);
        tv_mine_logisttics_nicheng = (TextView) view.findViewById(R.id.tv_mine_logisttics_nicheng);
        tv_mine_logisttics_name = (TextView) view.findViewById(R.id.tv_mine_logisttics_name);
        tv_mine_logisttics_gongsi = (TextView) view.findViewById(R.id.tv_mine_logisttics_gongsi);
        jiesuanzhongxin = (TextView) view.findViewById(R.id.jiesuanzhongxin);
        zizhanghaoguanli = (TextView) view.findViewById(R.id.zizhanghaoguanli);
        wuliugongsi = (TextView) view.findViewById(R.id.wuliugongsi);
        yunfeimoban = (TextView) view.findViewById(R.id.yunfeimoban);
        rl_logistics_jiesuan = (RelativeLayout) view.findViewById(R.id.rl_logistics_jiesuan);
        rl_logistics_zizhanghao_message = (RelativeLayout) view.findViewById(R.id.rl_logistics_zizhanghao_message);
        rl_logistics_wuliuxinxi = (RelativeLayout) view.findViewById(R.id.rl_logistics_wuliuxinxi);
        rl_logistics_yunfei = (RelativeLayout) view.findViewById(R.id.rl_logistics_yunfei);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        toolbar_back.setVisibility(View.GONE);
        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_text.setText(mTranslatesString.getCommon_setting());
        toolbar_right_text.setOnClickListener(this);
        rl_logistics_jiesuan.setOnClickListener(this);
        rl_logistics_zizhanghao_message.setOnClickListener(this);
        rl_logistics_wuliuxinxi.setOnClickListener(this);
        rl_logistics_yunfei.setOnClickListener(this);
        //请求网络
        jiesuanzhongxin.setText(mTranslatesString.getCommon_zichanzhongxin());
        zizhanghaoguanli.setText(mTranslatesString.getCommon_zizhanghaoguanli());
        wuliugongsi.setText(mTranslatesString.getCommon_yunfeimuban());
        yunfeimoban.setText(mTranslatesString.getCommon_gongsixinxi());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                requestData();
//            }
//        }).start();
        myrequestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_text://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.rl_logistics_jiesuan://结算中心
                startActivity(new Intent(getActivity(), SettleAccountsCenterActivity.class));
                break;
            case R.id.rl_logistics_zizhanghao_message://子账号管理
                startActivity(new Intent(getActivity(), ChildAccountListActivity.class));
                break;
            case R.id.rl_logistics_wuliuxinxi://物流公司信息
                startActivity(new Intent(getActivity(),LogisticsCompanyMessageActivity.class));
                break;
            case R.id.rl_logistics_yunfei://运费模板
                startActivity(new Intent(getActivity(),FreightTemplateListActivity.class));
                break;
        }
    }
//    private void requestData() {
//        try {
//            String sExpressToken = UserHelper.getSExpressToken();
//            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL_LINSHI+ "express/deliveryCompanyDetail/"+UserHelper.getExpressLoginId()+".htm", true, UserHelper.getExpressID(),sExpressToken );
//            if (response.getStatusCode()== HttpStatus.SC_OK) {
//                String content = response.getContent();
//                JSONObject json = new JSONObject(content);
//                meta = json.getJSONObject("meta");
//                boolean success = meta.getBoolean("success");
//                if (success) {
//                    data = json.getJSONObject("data");
//                    handler.sendEmptyMessage(100);
//                } else {
//                    handler.sendEmptyMessage(200);
//                }
//            }
//        }  catch (NetException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    private void myrequestData(){
        MyHttpUtils instance = MyHttpUtils.getInstance();
        instance.doGet(ConstantS.BASE_URL_LINSHI + "express/deliveryCompanyDetail/" + UserHelper.getExpressLoginId() + ".htm", true, UserHelper.getExpressID(),
                UserHelper.getSExpressToken(), new MyHttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result,int code) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(result);
                            meta = json.getJSONObject("meta");
                            boolean success = meta.getBoolean("success");
                            if (success) {
                                data = json.getJSONObject("data");
                                handler.sendEmptyMessage(100);
                            } else {
                                handler.sendEmptyMessage(200);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailture() {

                    }
                });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    try {
                        String introduction = data.getString("Introduction");
                        if (introduction.equals("null")){
                            tv_mine_logisttics_nicheng.setText(mTranslatesString.getCommon_zanweishezhi());
                        }else {
                            tv_mine_logisttics_nicheng.setText(data.getString("Introduction"));
                        }

                        if (data.getString("DeliveryName").equals("null")){
                            tv_mine_logisttics_nicheng.setText(mTranslatesString.getCommon_zanweishezhi());
                        }else {
                            tv_mine_logisttics_name.setText(data.getString("DeliveryName"));
                        }
                        if (data.getString("CompanyName").equals("null")){
                            tv_mine_logisttics_nicheng.setText(mTranslatesString.getCommon_zanweishezhi());
                        }else {
                            tv_mine_logisttics_gongsi.setText(data.getString("CompanyName"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 200:
                    try {
                        Toast.makeText(getActivity(), meta.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
