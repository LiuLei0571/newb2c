package com.example.b2c.fragment.logistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.OrderBean;
import com.example.b2c.adapter.logistics.MoyOrderAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.helper.indicator.BasePager;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 暂时废弃不用，用来viewpagerintore
 */
@SuppressLint("ValidFragment")
public class LogisticsBaseOrderFragment extends BasePager {
    private XRecyclerView dingdan_xrecycleView;
    private TextView tv_nodata;
    private RelativeLayout empty;
    private SharedPreferences sp;
    //    private JSONObject data;
//    private MyOrderAdapterBean myOrderAdapter;
//    private List<MyOrderAdapterBean.Rows> rows;
    private MoyOrderAdapter moyOrderAdapter;
    //    private OrderBean.Data data;
//    private OrderBean.Meta meta;
    private List<OrderBean.Rows> rows;


    private int tag;
    private LinearLayoutManager recylerViewLayoutManager;
    private OrderBean orderBean;
    private Gson gson;
    private Context context;
    public LogisticsBaseOrderFragment(){}
    public LogisticsBaseOrderFragment(int tag,Context context){
        this.tag=tag;
        this.context=context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        OrderListAdapter orderListAdapter=new OrderListAdapter();
        rows=new ArrayList<>();
        gson = new Gson();
        initData();
        initCliick();
    }
    @Override
    public View initView() {
        View  rootView= View.inflate(context,R.layout.fragment_logistics_new_order,null);
        empty= (RelativeLayout) rootView.findViewById(R.id.empty);
        dingdan_xrecycleView= (XRecyclerView) rootView.findViewById(R.id.dingdan_xrecycleView);
        dingdan_xrecycleView.setLayoutManager(new LinearLayoutManager(context));
//        dingdan_xrecycleView.addItemDecoration(new ListDividerItemDecoration(getActivity(),LinearLayoutManager.HORIZONTAL,2,getResources().getColor(R.color.background)));
        dingdan_xrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下啦刷新
                rows.clear();
                moyOrderAdapter.notifyDataSetChanged();
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
                    dingdan_xrecycleView.loadMoreComplete();
                }
            }
        });
        return rootView;
    }

    @Override
    public void initData() {
        showLoading();
        initDatas(tag,true,0);
    }
    private void initDatas(int code, final boolean isJiazai, int jilu){
        MyHttpUtils instance = MyHttpUtils.getInstance();
        Map<String,String> map=new HashMap<>();
        map.put("deliveryCompanyId", UserHelper.getExpressLoginId()+"");
        map.put("orderStatus",code+"");
        map.put("startRow",jilu+"");
        instance.doPost(ConstantS.BASE_URL_LINSHI + "express/orderList.htm", map,
                true, UserHelper.getExpressID(), UserHelper.getSExpressToken(),
                new MyHttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result, int code) {

                        if (code==200) {
                            JSONObject meta_json = null;
                            try {
                                meta_json = new JSONObject(result)
                                        .getJSONObject("meta");
//                                ResponseResult results =  gson.fromJson(meta_json.toString(),
//                                        ResponseResult.class);
                                if (meta_json.getBoolean("success")) {
                                    JSONObject data_json = new JSONObject(result)
                                            .getJSONObject("data");
                                    orderBean = gson.fromJson(data_json.toString(), OrderBean.class);
                                    rows.addAll(orderBean.getRows());
                                    if (isJiazai) {
                                        if (rows.size() == 0) {
                                            empty.setVisibility(View.VISIBLE);
                                        } else {
                                            moyOrderAdapter = new MoyOrderAdapter(context, rows, mTranslatesString);
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
                        }else{
                            dissLoading();
                        }
                    }

                    @Override
                    public void onFailture() {
                        dissLoading();
                    }
                });

    }
    private void initCliick(){

    }
//    private boolean isVisible;
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
////        Log.d("TAG", "fragment->setUserVisibleHint");
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            lazyLoad();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }
//    protected void lazyLoad() {
//        if (!isVisible) {
//            return;
//        }
//
//        showLoading();
//        initDatas(tag,true,0);
//        isVisible = false;
//    }
//
//    //do something
//    protected void onInvisible() {
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rows!=null){
            rows.clear();
        }
    }
}

