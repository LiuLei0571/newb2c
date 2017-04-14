package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.logistics.YiJiesuanBean;
import com.example.b2c.adapter.logistics.LogisticsOrderListAdapter;
import com.example.b2c.adapter.logistics.SettleOrderListAdapter;
import com.example.b2c.adapter.logistics.TrackingNumberAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.logistics.LogisticsOrderDetail;
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
 * 用途：已结算订单列表跟逾期结算的类 * * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class SettleOrderListActivity extends TempBaseActivity {
    private LogisticsOrderListAdapter mAdapter;
    private boolean hasNext = false;
    private List<LogisticsOrderDetail> mOrderLists;
    private int status;
    private TextView toolbar_title;
    private XRecyclerView recycleView_yijiesuan;
    private View emty;
    private String tag;
    private YiJiesuanBean yiJiesuanBean;
    private List<YiJiesuanBean.Rows> rows;
    private SettleOrderListAdapter settleOrderListAdapter;
    private TrackingNumberAdapter trackingNumberAdapter;
    private LinearLayoutManager linearLayoutManager;
    public static  final  String WAIT_SETTLE = "1";//待结算
    public static  final  String DELAY_SETTLE = "2";//逾期结算
    public static  final  String YET_SETTLE = "0";//已结算
    @Override
    public int getRootId() {
        return R.layout.activity_settlement_order_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rows=new ArrayList<>();
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        recycleView_yijiesuan = (XRecyclerView) findViewById(R.id.recycleView_yijiesuan);
        if (linearLayoutManager==null) {
            linearLayoutManager = new LinearLayoutManager(this);
        }
        recycleView_yijiesuan.setLayoutManager(linearLayoutManager);
        recycleView_yijiesuan.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
        emty = findViewById(R.id.empty);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

    }
    private int jilu;
    private void initData(){
        showLoading();
        if (tag.equals("yuqi")){
            //点击逾期跳转过来的
            requestData(DELAY_SETTLE,0,true);
            toolbar_title.setText(mTranslatesString.getCommon_yuqijiesuan());
        }
        if(tag.equals("yesjiesuan")){
            //点击已结算跳转过来对的
            requestData(YET_SETTLE,0,true);
            toolbar_title.setText(mTranslatesString.getCommon_yijiesuan());
        }
        if (tag.equals("nojiesuan")){
            requestData(WAIT_SETTLE,0,true);
            toolbar_title.setText(mTranslatesString.getCommon_tvdaijiesuan());
        }
        recycleView_yijiesuan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                jilu=0;
                if (tag.equals("yuqi")){
                    settleOrderListAdapter.notifyDataSetChanged();
                    //点击逾期跳转过来的
                    requestData(DELAY_SETTLE,0,true);
                }
                if(tag.equals("yesjiesuan")){
                    settleOrderListAdapter.notifyDataSetChanged();
                    //点击已结算跳转过来对的
                    requestData(YET_SETTLE,0,true);
                }
                if (tag.equals("nojiesuan")){
                    trackingNumberAdapter.notifyDataSetChanged();
                    requestData(WAIT_SETTLE,0,true);
                }
            }

            @Override
            public void onLoadMore() {
                if (yiJiesuanBean.isHasNext()){
                    //如果还有数据，要先判断是否是
                    jilu=jilu+20;
                    if (tag.equals("yuqi")){
                        //点击逾期跳转过来的
                        requestData(DELAY_SETTLE,jilu,false);
                    }
                    if(tag.equals("yesjiesuan")){
                        //点击已结算跳转过来对的
                        requestData(YET_SETTLE,jilu,false);
                    }
                    if (tag.equals("nojiesuan")){
                        requestData(WAIT_SETTLE,jilu,false);
                    }
                }else{
                    recycleView_yijiesuan.loadMoreComplete();
                }
            }
        });
    }
    private void requestData(final String biaoji, final int startros, final boolean isNofly){
            if (startros==0){
                rows.clear();
            }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String>map=new HashMap<>();
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
                map.put("status",biaoji);
                map.put("startRow",startros+"");
                Gson gson=new Gson();
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI+"staff/getDeliveryFinanceList.htm", map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode()== HttpStatus.SC_OK) {
                        String content = response.getContent();
                        JSONObject json = new JSONObject(content);
                        JSONObject meta = json.getJSONObject("meta");
                        if (meta.getBoolean("success")) {
                            //说明获取列表成功
                            String data = json.getJSONObject("data").toString();
                            yiJiesuanBean = gson.fromJson(data, YiJiesuanBean.class);
                            rows .addAll(yiJiesuanBean.getRows());
                            if (isNofly) {
                                handler.sendEmptyMessage(100);
                            } else {
                                handler.sendEmptyMessage(200);
                            }
                        }
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                   handler.sendEmptyMessage(300);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(300);
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
                    dissLoading();
                    if (rows.size()==0){
                        emty.setVisibility(View.VISIBLE);
                        recycleView_yijiesuan.setVisibility(View.GONE);
                    }else {
                        if (tag.equals("nojiesuan")){
                            trackingNumberAdapter = new TrackingNumberAdapter(getApplication(), rows, mTranslatesString);
                            recycleView_yijiesuan.setAdapter(trackingNumberAdapter);
                        }else{
                            settleOrderListAdapter = new SettleOrderListAdapter(getApplication(), rows, tag, mTranslatesString);
                            recycleView_yijiesuan.setAdapter(settleOrderListAdapter);
                        }
                        recycleView_yijiesuan.refreshComplete();
                    }
                    break;
                case 200:
                    if (tag.equals("nojiesuan")) {
                        trackingNumberAdapter.notifyDataSetChanged();
                    }else{
                        settleOrderListAdapter.notifyDataSetChanged();
                    }
                    recycleView_yijiesuan.loadMoreComplete();
                    break;
                case 300:
                    dissLoading();
                    break;
            }
        }
    };
}


