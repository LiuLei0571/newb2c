package com.example.b2c.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.DepotEntry;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.util.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 卖家发布商品选择仓库地址信息
 */
public class MyDdepotListActivity extends TempBaseActivity {

    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private XRecyclerView rv_xrecyclerview;
    private RelativeLayout empty;
    private List<DepotEntry> mDatas;
    private DepotAdapter mAdapter;
//    private ArrayList<Integer> intList;
    private ArrayList<String> dizhi;
//    private ArrayList<Integer> positi;
    private ArrayList<Integer> ids;
//    private ArrayList<Integer> positionList;

    @Override
    public int getRootId() {
        return R.layout.activity_my_ddepot_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        rv_xrecyclerview = (XRecyclerView) findViewById(R.id.rv_xrecyclerview);
        empty = (RelativeLayout) findViewById(R.id.empty);
        rv_xrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        toolbar_title.setText(mTranslatesString.getCommon_depotlist());
        toolbar_right_text.setText(mTranslatesString.getCommon_sure());

    }

    private void initData() {
        rv_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rv_xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                rv_xrecyclerview.noMoreLoading();
            }
        });
        mDatas = new ArrayList<>();
//        intList = new ArrayList<>();
        dizhi = new ArrayList<>();
//        positionList = new ArrayList<>();
        Intent intent = getIntent();
        ids = intent.getIntegerArrayListExtra("ids");
        if(ids!=null){
            Logs.d("ids",ids.toString());
        }else {
            ids =new ArrayList<>();
        }
        mDatas.clear();
        showLoading();
        sellerRdm.getAllDepotList();
        sellerRdm.mPageListListener = new PageListListener<DepotEntry>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(List<DepotEntry> list) {
                mDatas.addAll(list);
                mAdapter = new DepotAdapter();
                handler.sendEmptyMessage(100);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
        toolbar_right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();//数据是使用Intent返回
                //获取地址信息
                getDiZhi();
                intent.putStringArrayListExtra("dizhi", dizhi);//把返回数据存入Intent
//                intent.putIntegerArrayListExtra("ids", intList);//把返回数据存入Intent
                intent.putIntegerArrayListExtra("ids", ids);//把返回数据存入Intent
//                intent.putIntegerArrayListExtra("positionList", positionList);//把返回数据存入Intent
                MyDdepotListActivity.this.setResult(900, intent);//设置返回数据
                finish();//关闭Activity
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    rv_xrecyclerview.setAdapter(mAdapter);
                    break;
            }
        }
    };

    public void getDiZhi() {
        if(ids.size()>0){
            for (Integer id : ids) {
                for (DepotEntry depot : mDatas) {
                    if(depot.getId() == id){
                        dizhi.add(depot.getProvinceName()+depot.getCityName()+depot.getAddress());
                        continue;
                    }
                }
            }
        }
    }

    class DepotAdapter extends XRcycleViewAdapterBase {
        private TextView tv;
        private CheckBox cb_cangku;
        private LinearLayout ll_genbu;

        @Override
        public int getItemPostion() {
            return mDatas.size();
        }

        @Override
        public int getLayoutID() {
            return R.layout.my_item_depot;
        }

        @Override
        public Context getContext() {
            return getApplication();
        }

        @Override
        public void initData(MyViewHolder holder, final int position) {
            final DepotEntry depotEntry = mDatas.get(position);
            tv.setText(Utils.cutNull(mDatas.get(position).getProvinceName()) + " "
                    + Utils.cutNull(mDatas.get(position).getCityName()) + " "
                    + Utils.cutNull(mDatas.get(position).getAddress()));
//            cb_cangku.setTag(position);
//            if (positi != null && positi.size() != 0) {
//                for (int i = 0; i < positi.size(); i++)
//                    if (position == positi.get(i)) {
//                        cb_cangku.setChecked((positi.contains(position) ? true : false));
//                    } else {
//                        cb_cangku.setChecked(false);
//                    }
//            }
            if(depotEntry!=null && ids!=null){
                if(ids.contains(depotEntry.getId())){
                    cb_cangku.setChecked(true);
                }else {
                    cb_cangku.setChecked(false);
                }
            }
            cb_cangku.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    Logs.d("isChecked",isChecked+"");
                    Logs.d("Tag",cb_cangku.getTag()+"");
//                    if (isChecked) {
//                        if (!intList.contains(cb_cangku.getTag())) {
//                            dizhi.add(mDatas.get(position).getProvinceName() +
//                                    mDatas.get(position).getCityName() +
//                                    mDatas.get(position).getAddress());
//                            intList.add(mDatas.get(position).getId());
//                           positionList.add(position);
//                        }
//                    } else {
//                        if (intList.contains(cb_cangku.getTag())) {
//                            dizhi.remove(mDatas.get(position).getProvinceName() +
//                                    mDatas.get(position).getCityName() +
//                                    mDatas.get(position).getAddress());
//                            intList.remove(mDatas.get(position).getId());
//                            positionList.remove(position);
//                        }
//                    }
                    if(isChecked){
                        if (ids.indexOf(depotEntry.getId()) == -1){
                            ids.add(mDatas.get(position).getId());
                        }
                    }else {
                        if (ids.indexOf(depotEntry.getId()) != -1) {
                            ids.remove(ids.indexOf(depotEntry.getId()));
                        }
                    }
                }
            });

        }

        @Override
        public void initView(View view) {
            tv = (TextView) view.findViewById(R.id.tv_depot_names);
            cb_cangku = (CheckBox) view.findViewById(R.id.cb_cangku);
            ll_genbu = (LinearLayout) view.findViewById(R.id.ll_genbu);

        }
    }
}
