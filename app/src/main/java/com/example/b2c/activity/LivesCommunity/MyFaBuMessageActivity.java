package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.livesCommunity.HomeListAdapter;
import com.example.b2c.adapter.livesCommunity.MyFabuAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.livesCommunity.LivesHomeFragment;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.livesCommunity.HomeList;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的发布列表
 */
public class MyFaBuMessageActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private XRecyclerView recycleView_my_fabu;
    private RelativeLayout empty;
    private Map<String, String> map;
    private List<HomeList.Rows> rows;
    private MyFabuAdapter myFabuAdapter;
    private HomeList homelIst;
    private LocalBroadcastManager lm;
    private MyGungbo myGungbo;

    @Override
    public int getRootId() {
        return R.layout.activity_my_fa_bu_message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData(0,true);
        lm = LocalBroadcastManager.getInstance(this);
        initLinter();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        recycleView_my_fabu = (XRecyclerView) findViewById(R.id.recycleView_my_fabu);
        recycleView_my_fabu.setLayoutManager(new LinearLayoutManager(getApplication()));
        recycleView_my_fabu.addItemDecoration(new ListDividerItemDecoration(getApplication(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.background)));
        empty = (RelativeLayout) findViewById(R.id.empty);

        toolbar_right_text.setText(mTranslatesString.getCommon_fabu());
        toolbar_title.setText(mTranslatesString.getCommon_myfabu());
        map = new HashMap<>();
        rows = new ArrayList<>();

    }
    private int jilu=0;
    private void initLinter(){
        //组册广播
        myGungbo = new MyGungbo();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.plusjun.test.hahaha");
        lm.registerReceiver(myGungbo,filter);
        recycleView_my_fabu.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                jilu=0;
                initData(0,true);
            }

            @Override
            public void onLoadMore() {
                if (homelIst.isHasNext()){
                    jilu=jilu+20;
                    initData(jilu,false);
                }else{
                    recycleView_my_fabu .noMoreLoading();
                }
            }
        });
    }
    private void initData(int startRows, final boolean jiazai){
        showLoading();
        if (startRows==0&&myFabuAdapter!=null){
            myFabuAdapter.notifyDataSetChanged();
            rows.clear();
        }
        map.put("startRow",startRows+"");
        mLogisticsDataConnection.getMyFabuList(getApplication(),map);
        mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
            @Override
            public void onSuccess(Object data, String successInfo) {
                homelIst = (HomeList) data;
                rows.addAll(homelIst.getRows());
                if (jiazai) {
                    if (rows.size() == 0) {
                        recycleView_my_fabu.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        recycleView_my_fabu.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        myFabuAdapter = new MyFabuAdapter(MyFaBuMessageActivity.this, rows, mTranslatesString) {
                            @Override
                            public void caozuo(int type,int position) {
                                if (type==0){
                                    //说明点击是编辑,跳转到发布界面，将该条数据传递过去
                                    startActivity(new Intent(getApplication(),FaBuMessageActivity.class)
                                            .putExtra("informationId",rows.get(position).getId()+"")
                                            .putExtra("tag","bianji"));
                                }else{
                                    //点击的删除，调用删除接口，并且删除集合里的内容，然后刷新列表
                                    deleteItem(position);
                                }
                            }
                        };
                        recycleView_my_fabu.setAdapter(myFabuAdapter);
                        recycleView_my_fabu.refreshComplete();
                    }
                }else{
                    myFabuAdapter.notifyDataSetChanged();
                    recycleView_my_fabu.loadMoreComplete();
                }
                dissLoading();
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                dissLoading();
                ToastHelper.makeToast(errorInfo);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
            dissLoading();
            }
        };
    }
    //调用删除
    private void deleteItem(final int position){
        showLoading();
        //这里可以调用举报的方法，因为这两个方法除了url不一样外别的没什么不同，只是调取成功后的处理不一样
        //在这里调取成功后要删除本地集合中对应的数据，刷新数据
        mLogisticsDataConnection.jubao(getApplication(), ConstantS.BASE_URL+"life/deleteInfo/"+rows.get(position).getId()+".htm",null);
        mLogisticsDataConnection.mNodataListener=new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                //删除集合对应的数据
                rows.remove(position);
                if (rows.size()==0){
                    initData(0,true);
                }else{
                    myFabuAdapter.notifyDataSetChanged();
                }
                lm.sendBroadcast(new Intent().setAction("com.plusjun.test.hahaha"));
                dissLoading();
            }

            @Override
            public void onError(int errorNo, String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                dissLoading();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
dissLoading();
            }
        };
    }
    class MyGungbo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接到广播刷新
            jilu=0;
            initData(jilu,true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lm.unregisterReceiver(myGungbo);
    }
}
