package com.example.b2c.fragment.logistics;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.net.response.logistics.OrderBean;
import com.example.b2c.adapter.logistics.MoyOrderAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 用途：新的订单
 * * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */
@SuppressLint("ValidFragment")
public class QuanBuFragment extends TempleBaseFragment{

    private XRecyclerView dingdan_xrecycleView;
    private TextView tv_nodata;
    private RelativeLayout empty;
    private SharedPreferences sp;
    private MoyOrderAdapter moyOrderAdapter;
    private List<OrderBean.Rows> rows;


    private int tag;
    private LinearLayoutManager recylerViewLayoutManager;
    private OrderBean orderBean;
    private Gson gson;

    public QuanBuFragment(){}
    public QuanBuFragment(int tag){
        this.tag=tag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        OrderListAdapter orderListAdapter=new OrderListAdapter();
        rows=new ArrayList<>();
        gson = new Gson();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_logistics_new_order;
    }

    @Override
    protected void initView(View rootView) {
        empty= (RelativeLayout) rootView.findViewById(R.id.empty);
        dingdan_xrecycleView= (XRecyclerView) rootView.findViewById(R.id.dingdan_xrecycleView);
        dingdan_xrecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dingdan_xrecycleView.addItemDecoration(new ListDividerItemDecoration(getActivity(),LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
        dingdan_xrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下啦刷新
//                rows.clear();
//                moyOrderAdapter.notifyDataSetChanged();
                jilu=0;
                initDatas(tag,true,0);
            }
            private int jilu;
            @Override
            public void onLoadMore() {
                if (orderBean.isHasNext()){
                    //如果还有数据，要先判断是否是
                    jilu=jilu+20;
                    initDatas(tag,false,jilu);
                }else{
                    dingdan_xrecycleView.noMoreLoading();
                }
            }
        });
    }
    private void initData(){
        showLoading();
        initDatas(tag,true,0);
    }
private void initDatas(final int code, final boolean isJiazai, final int jilu) {
    if (jilu == 0 && moyOrderAdapter != null) {
        rows.clear();
        moyOrderAdapter.notifyDataSetChanged();
    }
    Map<String, String> map = new HashMap<>();
    map.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
    map.put("orderStatus", code + "");
    map.put("startRow", jilu + "");
    OkhttpUtils instance = OkhttpUtils.getInstance();
    try {
        instance.doPost(getActivity(), ConstantS.BASE_URL_LINSHI + "express/orderList.htm", map,
                true, UserHelper.getExpressID(), UserHelper.getSExpressToken(), new OkhttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        JSONObject meta_json = null;
                            try {
                                meta_json = new JSONObject(result)
                                        .getJSONObject("meta");
                                ResponseResult results = gson.fromJson(meta_json.toString(),
                                        ResponseResult.class);
                                if (results.isSuccess()) {
                                    JSONObject data_json = new JSONObject(result)
                                            .getJSONObject("data");
                                    orderBean = gson.fromJson(
                                            data_json.toString(), OrderBean.class);
                                    rows.addAll(orderBean.getRows());
                                    if (isJiazai) {
                                        if (rows.size() == 0) {
                                            empty.setVisibility(View.VISIBLE);
                                        } else {
                                            moyOrderAdapter = new MoyOrderAdapter(getActivity(), rows, mTranslatesString);
                                            dingdan_xrecycleView.setAdapter(moyOrderAdapter);
                                            dingdan_xrecycleView.refreshComplete();
                                        }
                                        dissLoading();
                                    } else {
                                        moyOrderAdapter.notifyDataSetChanged();
                                        dingdan_xrecycleView.loadMoreComplete();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }

                    @Override
                    public void onFailture(Exception e) {
                        dissLoading();
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {
                            dissLoading();
                    }
                }
//                new OkhttpUtils.MyCallback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        handler.sendEmptyMessage(300);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                        if (response.code() == 200) {
//                            String result = response.body().string();
//                            JSONObject meta_json = null;
//                            try {
//                                meta_json = new JSONObject(result)
//                                        .getJSONObject("meta");
//                                ResponseResult results = gson.fromJson(meta_json.toString(),
//                                        ResponseResult.class);
//                                if (results.isSuccess()) {
//                                    JSONObject data_json = new JSONObject(result)
//                                            .getJSONObject("data");
//                                    orderBean = gson.fromJson(
//                                            data_json.toString(), OrderBean.class);
//                                    rows.addAll(orderBean.getRows());
//                                    if (isJiazai) {
//                                        handler.sendEmptyMessage(100);
//                                    } else {
//                                        handler.sendEmptyMessage(200);
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            handler.sendEmptyMessage(300);
//                        }
//                    }
//                }
        );
    } catch (NetException e) {
        e.printStackTrace();
        dissLoading();
    }
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rows!=null){
            rows.clear();
        }
    }
}

