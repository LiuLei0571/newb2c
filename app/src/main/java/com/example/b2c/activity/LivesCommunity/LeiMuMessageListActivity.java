package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.adapter.livesCommunity.HomeListAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.livesCommunity.HomeList;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击类目对应的列表
 */
public class LeiMuMessageListActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private XRecyclerView recycle_lives_message;
    private RelativeLayout empty;
    private List<HomeList.Rows> rows;
    private HomeList homeList;
    private HomeListAdapter homeListAdapter;
    private String categoryId;

    @Override
    public int getRootId() {
        return R.layout.activity_lei_mu_message_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initLinte();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        recycle_lives_message = (XRecyclerView) findViewById(R.id.recycle_lives_message);
        recycle_lives_message.setLayoutManager(new LinearLayoutManager(getApplication()));
        recycle_lives_message.addItemDecoration(new ListDividerItemDecoration(getApplication(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.background)));
        empty = (RelativeLayout) findViewById(R.id.empty);
    }
    private int jilu=0;
    private void initLinte(){
        recycle_lives_message.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                jilu=0;
                requestList(jilu,true);
            }

            @Override
            public void onLoadMore() {
                if (homeList.isHasNext()){
                    jilu=jilu+20;
                    requestList(jilu,false);
                }else{
                    recycle_lives_message .noMoreLoading();
                }
            }
        });

    }
    private void initData(){
        rows = new ArrayList<>();
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        String categoryName = intent.getStringExtra("categoryName");
        toolbar_title.setText(categoryName);
        //请求网络
        requestList(0,true);

    }
    private void requestList( int startRow, final boolean jiazai){
        if (startRow == 0 && homeListAdapter != null) {
            homeListAdapter.notifyDataSetChanged();
            rows.clear();
        }
        Map<String,String>map=new HashMap<>();
        map.put("categoryId",categoryId);
        map.put("startRow",startRow+"");
        mLogisticsDataConnection.getLeimuMessageList(getApplication(),map);
        mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
            @Override
            public void onSuccess(Object data, String successInfo) {
                homeList = (HomeList) data;
                rows.addAll(homeList.getRows());
                if (jiazai){
                    if (rows.size()==0){
                        recycle_lives_message.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        recycle_lives_message.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        homeListAdapter = new HomeListAdapter(LeiMuMessageListActivity.this, rows);
                        recycle_lives_message.setAdapter(homeListAdapter);
                        recycle_lives_message.refreshComplete();
                    }
                }else{
                    homeListAdapter.notifyDataSetChanged();
                    recycle_lives_message.loadMoreComplete();
                }
                if (homeListAdapter!=null){
                    homeListAdapter.setOnItemClickListener(new XRcycleViewAdapterBase.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(getApplication(), MessageDetailsActivity.class).putExtra("id",rows.get(position).getId()+""));
                        }
                    });
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onFinish() {}
            @Override
            public void onLose() {
                dissLoading();
            }
        };
    }
}
