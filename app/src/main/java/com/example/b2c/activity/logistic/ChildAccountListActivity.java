package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.adapter.logistics.ZiZhanghaoAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.logistics.AccountItemResult;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
//import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 用途：子账号列表
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 * ModifyTime  16/12/03
 */

public class ChildAccountListActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private XRecyclerView child_account_recycle;
    private RelativeLayout empty;
    private TextView tv_add;
    private List<AccountItemResult.AccountItem> myList;
//    private ChildAccountAdapter childAccountAdapter;
    private ZiZhanghaoAdapter ziZhanghaoAdapter;
    private LinearLayoutManager linearLayoutManager;
    //    private FinshReceiver myIntentFilter;
    @Override
    public int getRootId() {
        return R.layout.activity_logistics_child_account;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
    public void initView() {
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text= (TextView) findViewById(R.id.toolbar_right_text);
        child_account_recycle= (XRecyclerView) findViewById(R.id.child_account_recycle);
        empty= (RelativeLayout) findViewById(R.id.empty);
        tv_add = (TextView) findViewById(R.id.tv_add);
        toolbar_right_text.setText(mTranslatesString.getCommon_add());
        toolbar_title.setText(mTranslatesString.getCommon_zizhanghaoguanli());
        toolbar_right_text.setOnClickListener(this);
//        registerBoradcastReceiver();
    }

    /**
     * 用来记录显示的条数
     */
    private int jilutiaoshu=0;
    public void initData() {
        if (linearLayoutManager==null) {
            linearLayoutManager = new LinearLayoutManager(this);
        }
        child_account_recycle.setLayoutManager(linearLayoutManager);
        child_account_recycle.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));

        myList = new ArrayList<>();
        showLoading();
        requestDaata(20,0,true);
    }
    boolean isHasnest;
    /**
     * 请求服务器
     */
    public void requestDaata(final int pageSize, final int startRow, final boolean isJiazai){
        mLogisticsDataConnection.getAccountListList(ConstantS.BASE_URL_EXPRESS+"childAccountList.htm",startRow,pageSize);
        mLogisticsDataConnection.mPageListHasNextListener=new PageListHasNextListener() {
            @Override
            public void onSuccess(List list, boolean hasNest) {
                if (startRow==0){
                    myList.clear();
                }
                myList.addAll(list);
                isHasnest=hasNest;
                if (isJiazai){
                    handler.sendEmptyMessage(100);
                }else{
                    handler.sendEmptyMessage(200);
                }

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                handler.sendEmptyMessage(300);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {

            }
        };
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    dissLoading();
                    if (myList.size()==0){
                        empty.setVisibility(View.VISIBLE);
                        child_account_recycle.setVisibility(View.GONE);
                    }else {
                        empty.setVisibility(View.GONE);
                        child_account_recycle.setVisibility(View.VISIBLE);
//                        childAccountAdapter = new ChildAccountAdapter(getApplication(),accountItemResult);
                        ziZhanghaoAdapter = new ZiZhanghaoAdapter(getApplication(),myList);
                        child_account_recycle.setAdapter(ziZhanghaoAdapter);
                        initListener();
                    }
                    child_account_recycle.refreshComplete();
                    break;
                case 200:
                    ziZhanghaoAdapter.notifyDataSetChanged();
                    child_account_recycle.loadMoreComplete();
                    break;
                case 300:
                    dissLoading();
                    break;
            }
        }
    };

    /**
     * 各种监听
     */
    public void initListener() {
        child_account_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下啦刷新
//                myList.clear();
//                ziZhanghaoAdapter.notifyDataSetChanged();
                jilutiaoshu=0;
                requestDaata(20,0,true);
            }
            @Override
            public void onLoadMore() {
                //先判断有没有数据
                if (isHasnest){
                    //如果有
                    jilutiaoshu=jilutiaoshu+20;
                    requestDaata(20,jilutiaoshu,false);
                }else {
                    child_account_recycle.loadMoreComplete();
                }
            }
        });
        if (ziZhanghaoAdapter!=null){
            ziZhanghaoAdapter.setOnItemClickListener(new XRcycleViewAdapterBase.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //跳转到详情页面,把该条数据请求下来的传递过去
                    Intent intent = new Intent(ChildAccountListActivity.this, AddAccountActivity.class);
                    intent.putExtra("accountId",myList.get(position).getAccountId()+"");
                    intent.putExtra("loginName",myList.get(position).getLoginName());
                    intent.putExtra("firstName",myList.get(position).getFirstName());
                    intent.putExtra("lastName",myList.get(position).getLastName());
                    intent.putExtra("mobile",myList.get(position).getMobile()+"");
                    intent.putExtra("email",myList.get(position).getEmail());
                    intent.putExtra("remark",myList.get(position).getRemark());
                    intent.putExtra("status",myList.get(position).getStatus()+"");
                    intent.putExtra("add2bianji","item");
//                    startActivity(intent);
                    startActivityForResult(intent,90);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_right_text:
                //跳转到详情界面
                Intent integer=new Intent(getApplication(),AddAccountActivity.class);
                integer.putExtra("add2bianji","add");
                startActivityForResult(integer,90);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 90:
                jilutiaoshu=0;
                requestDaata(20,0,true);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        unregisterReceiver(myIntentFilter);
    }
}
