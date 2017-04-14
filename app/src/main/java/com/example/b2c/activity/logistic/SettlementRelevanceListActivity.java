package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.logistics.RelevanceAdapter;
import com.example.b2c.adapter.logistics.SettledHistoryListAdapter;
import com.example.b2c.adapter.logistics.SettlementListAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.logistics.SettlementDetailResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用途：结算记录（包括为结算，已结算），包括所有的订单结算状态
 * Created by milk on 16/11/16.
 * 邮箱：649444395@qq.com
 */

public class SettlementRelevanceListActivity extends TempBaseActivity implements View.OnClickListener {
    private RelevanceAdapter mAdapter;
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private XRecyclerView tv_all_dingdan_state;
    private RelativeLayout empty;
    private RelevanceAdapter relevanceAdapter;
    private List<SettlementDetailResult.SettlementDetail> rows;
    private SettledHistoryListAdapter settlementListAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_settlement_data_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        showLoading();
        requestData(0,true);
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_all_dingdan_state = (XRecyclerView) findViewById(R.id.tv_all_dingdan_state);
        empty = (RelativeLayout) findViewById(R.id.empty);
        toolbar_title.setText(mTranslatesString.getCommon_jiesuanjilu());
        if (linearLayoutManager==null) {
            linearLayoutManager = new LinearLayoutManager(this);
        }
        tv_all_dingdan_state.setLayoutManager(linearLayoutManager);
        tv_all_dingdan_state.addItemDecoration(new DividerItemDecoration(
                getApplication(), DividerItemDecoration.HORIZONTAL));
    }
    private boolean ishasNest;
    private void initData(){
        rows=new ArrayList<>();
        tv_all_dingdan_state.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下啦刷新
                rows.clear();
                settlementListAdapter.notifyDataSetChanged();
                jilu=0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestData(0,true);
                    }
                }).start();
            }
            @Override
            public void onLoadMore() {
                if (isJiazai){
                    //如果还有数据，要先判断是否是
                    jilu=jilu+20;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestData(jilu,false);
                        }
                    }).start();
                }else{
                    tv_all_dingdan_state.loadMoreComplete();
                }
            }
        });
    }
    private int jilu;
    private boolean isJiazai;
    private void requestData(final int start, final boolean ishasNest){
//       mLogisticsDataConnection.getSettleHistoryListRequest(ConstantS.BASE_URL_LINSHI+"settlement/settledHistory.htm",start);
       mLogisticsDataConnection.getSettleHistoryListRequest(ConstantS.BASE_URL_LINSHI+"settlement/settledHistory.htm",start);
        mLogisticsDataConnection.mPageListHasNextListener=  new PageListHasNextListener() {
            @Override
            public void onSuccess(List list, boolean hasNest) {
                 isJiazai=hasNest;
                if (list.size()==0){
                    handler.sendEmptyMessage(300);

                }else {
                    if (ishasNest) {
                        rows.addAll(list);
                        handler.sendEmptyMessage(100);
                    }else{
                        handler.sendEmptyMessage(200);
                    }
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {

            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
                        tv_all_dingdan_state.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        settlementListAdapter = new SettledHistoryListAdapter(getApplication(), rows, mTranslatesString);
                        tv_all_dingdan_state.setAdapter(settlementListAdapter);
                        tv_all_dingdan_state.refreshComplete();
                        tv_all_dingdan_state.loadMoreComplete();
                    }
                    break;
                case 200:
                    settlementListAdapter.notifyDataSetChanged();
                    tv_all_dingdan_state.loadMoreComplete();
                    break;
                case 300:
                    dissLoading();
                    empty.setVisibility(View.VISIBLE);
                    tv_all_dingdan_state.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
