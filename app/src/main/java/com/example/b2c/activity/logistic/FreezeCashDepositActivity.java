package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.logistics.DongjieBaozhengjinBean;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.logistics.DongJieListAdapter;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 冻结保证金
 */
public class FreezeCashDepositActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private XRecyclerView recycle_dongjie;
    private View emty;
    private DongjieBaozhengjinBean dongjieBaozhengjinBean;
    private List<DongjieBaozhengjinBean.Rows> rows;
    private DongJieListAdapter dongJieListAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public int getRootId() {
        return R.layout.activity_freeze_cash_deposit;
    }
    private int jilu=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestData(20,0,true);
            }
        }).start();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(mTranslatesString.getCommon_baozhengjindongjie());
        recycle_dongjie = (XRecyclerView) findViewById(R.id.recycle_dongjie);
        emty = findViewById(R.id.empty);
        if (linearLayoutManager==null) {
            linearLayoutManager = new LinearLayoutManager(this);
        }
        recycle_dongjie.setLayoutManager(linearLayoutManager);
        recycle_dongjie.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
    }
    private void initData(){
        rows=new  ArrayList<>();
        recycle_dongjie.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                dongJieListAdapter.notifyDataSetChanged();
                jilu=0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestData(20,0,true);
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
                if (dongjieBaozhengjinBean.isHasNext()){
                jilu=jilu+20;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestData(20,jilu,false);
                    }
                }).start();
            }else{
                recycle_dongjie.loadMoreComplete();
                }}
        });

    }
    private void requestData(int pageSize,int start,boolean isNofly){
        Gson gson=new Gson();
        Map<String,String> params=new HashMap<>();
        params.put("startRow", start + "");
        params.put("pageSize", pageSize + "");
        String ss= UserHelper.getExpressLoginId() + "";
        params.put("deliveryCompanyId", ss);
        try {//ConstantS.BASE_URL_EXPRESS+
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+"express/depositFreezeHistory.htm", params, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode()== HttpStatus.SC_OK){

                String jieguo = response.getContent();
            if (jieguo != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jieguo);
                    JSONObject meta = jsonObject.getJSONObject("meta");
                    if (meta.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String shuju = data.toString();
                        dongjieBaozhengjinBean = gson.fromJson(shuju, DongjieBaozhengjinBean.class);
                        rows.addAll(dongjieBaozhengjinBean.getRows());
                        if (isNofly) {
                            handler.sendEmptyMessage(100);
                        } else {
                            handler.sendEmptyMessage(200);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
        }catch(NetException e){
            e.printStackTrace();
            dissLoading();
            Toast.makeText(getApplication(),mTranslatesString.getCommon_neterror(),Toast.LENGTH_LONG).show();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    dissLoading();
                    if (rows.size()==0){
                        recycle_dongjie.setVisibility(View.GONE);
                        emty.setVisibility(View.VISIBLE);
                    }else {
                        dongJieListAdapter = new DongJieListAdapter(getApplication(), rows, mTranslatesString);
                        recycle_dongjie.setAdapter(dongJieListAdapter);
                    }
                    recycle_dongjie.refreshComplete();
                    recycle_dongjie.loadMoreComplete();
                    break;
                case 200:
                    dongJieListAdapter.notifyDataSetChanged();
                    recycle_dongjie.loadMoreComplete();
                    break;
            }
        }
    };
}
