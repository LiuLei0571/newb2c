package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.logistics.SettlementListAdapter;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 用途：待结算的列表(部分结算，全部没有结算)
 * * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class SettlementListActivity extends TempBaseActivity implements View.OnClickListener {
    private SettlementListAdapter mAdapter;
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private RelativeLayout empty;
    private XRecyclerView no_jiesuan_xrecycle;


    @Override
    public int getRootId() {
        return R.layout.activity_logistics_settlement_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mAdapter = new SettlementListAdapter(this);
        initView();
        initData();
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        empty = (RelativeLayout) findViewById(R.id.empty);
        no_jiesuan_xrecycle = (XRecyclerView) findViewById(R.id.no_jiesuan_xrecycle);
        no_jiesuan_xrecycle.setLayoutManager(new LinearLayoutManager(this));
        no_jiesuan_xrecycle.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
        toolbar_back.setOnClickListener(this);
    }

    /**
     * 请求网络等
     */
    private void initData(){

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}

