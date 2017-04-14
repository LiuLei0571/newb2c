package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.BaozhengjinBandong;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.logistics.SettlementListAdapter;
import com.example.b2c.config.ConstantS;
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

public class MoneyVariationActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private XRecyclerView recycle_baozhengjin;
    private View emty;
    private BaozhengjinBandong baozhengjinBandong;
    private List<BaozhengjinBandong.Rows> rowsList;
    private List<BaozhengjinBandong.Rows> shujuList;
    private SettlementListAdapter settlementListAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getRootId() {
        return R.layout.activity_money_variation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rowsList=new ArrayList<>();
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
    private int jilu=0;
    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        recycle_baozhengjin = (XRecyclerView) findViewById(R.id.recycle_baozhengjin);
        emty=findViewById(R.id.empty);
        toolbar_title.setText(mTranslatesString.getCommon_baozhengjinbiandong());
        if (linearLayoutManager==null) {
            linearLayoutManager = new LinearLayoutManager(this);
        }
        recycle_baozhengjin.setLayoutManager(linearLayoutManager);
        recycle_baozhengjin.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
    }
    private void initData(){
        rowsList=new ArrayList<>();
        recycle_baozhengjin.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下啦刷新
                rowsList.clear();
                settlementListAdapter.notifyDataSetChanged();
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
                if (baozhengjinBandong.isHasNext()){
                    //如果还有数据，要先判断是否是
                    jilu=jilu+20;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestData(20,jilu,false);
                        }
                    }).start();
                }else{
                    recycle_baozhengjin.loadMoreComplete();
                }
            }
        });
    }
    private void requestData(int pageSize,int start,boolean isNofly){
        Gson gson=new Gson();
        Map<String,String>params=new HashMap<>();
        params.put("startRow", start + "");
        params.put("pageSize", pageSize + "");
       String ss= UserHelper.getExpressLoginId() + "";
        params.put("deliveryCompanyId", ss);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_EXPRESS+"depositChangeHistory.htm", params, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode()== HttpStatus.SC_OK){

                String jieguo = response.getContent();
            if (jieguo != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jieguo);
                    JSONObject meta = jsonObject.getJSONObject("meta");
                    if (meta.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        baozhengjinBandong = gson.fromJson(data.toString(), BaozhengjinBandong.class);
                        shujuList = (baozhengjinBandong.getRows());
                        rowsList.addAll(shujuList);
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
            }

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    dissLoading();
                    //设置适配器
                    settlementListAdapter = new SettlementListAdapter(getApplication(),rowsList,mTranslatesString);
                    recycle_baozhengjin.setAdapter(settlementListAdapter);
                    recycle_baozhengjin.refreshComplete();
                    recycle_baozhengjin.loadMoreComplete();
                    break;
                case 200:
                    settlementListAdapter.notifyDataSetChanged();
                    recycle_baozhengjin.loadMoreComplete();
                    break;
            }
        }
    };
}
