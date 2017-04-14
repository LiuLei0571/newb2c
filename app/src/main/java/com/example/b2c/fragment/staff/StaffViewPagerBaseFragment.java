package com.example.b2c.fragment.staff;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.logistic.GuanLianOrderActivity;
import com.example.b2c.activity.staff.StaffHomeActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.adapter.staff.BaseStaffOrderAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.helper.indicator.BasePager;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.StaffOrderList;
import com.example.b2c.net.response.logistics.GetStaffOrderResult;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
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
 * 相当于StaffOrderBaseFragment
 */
@SuppressLint("ValidFragment")
public class StaffViewPagerBaseFragment extends BasePager {
    private BaseAdapter mAdapter;
    protected boolean hasNext = false;
    protected List<StaffOrderList> mOrderLists;
    private int type;
    private List<GetStaffOrderResult.StaffOrderList> rows;
    private XRecyclerView kuaidi_xrecycleView;
    private RelativeLayout empty;
    private BaseStaffOrderAdapter baseStaffOrderAdapter;
    private GetStaffOrderResult getStaffOrderResult;
    private JSONObject meta;
    private LinearLayoutManager linearLayoutManager;
    private MyGuangbo myGuangbo;
    private MobileStaticTextCode mTranslatesString;
    //    private ShuXin shuXin;
private Context context;
    public StaffViewPagerBaseFragment(int type,Context context) {
        super();
        this.type = type;
        this.context=context;
    }

    @Override
    public View initView() {
      View  rootView= View.inflate(context,R.layout.fragment_staff_order_base,null);
        mOrderLists = new ArrayList<>();
        kuaidi_xrecycleView = (XRecyclerView) rootView.findViewById(R.id.kuaidi_xrecycleView);
        kuaidi_xrecycleView.setLayoutManager(new LinearLayoutManager(context));
        empty = (RelativeLayout) rootView.findViewById(R.id.empty);
        return rootView;
    }

    @Override
    public void initData() {
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        receicer();
        initLick();
        showLoading();
        doGetStaffMyOrder(0, type, true);
    }
    private int jilu;

    private void initLick() {
        kuaidi_xrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                baseStaffOrderAdapter.notifyDataSetChanged();
                jilu=0;
                doGetStaffMyOrder(0, type, true);
            }

            @Override
            public void onLoadMore() {
                if (getStaffOrderResult.isHasNext()) {
                    //如果还有数据，要先判断是否是
                    jilu = jilu + 20;
                    doGetStaffMyOrder(jilu, type, false);
                } else {
                    baseStaffOrderAdapter.notifyDataSetChanged();
                    kuaidi_xrecycleView.noMoreLoading();

                }
            }
        });
    }
    public void doGetStaffMyOrder(final int pageNum, final int orderStatus, final boolean isJiazai) {
        rows=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<String, String>();
                map.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
                map.put("startRow", pageNum + "");
                map.put("orderStatus", orderStatus + "");
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "staff/orderList.htm", map, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode() == HttpStatus.SC_OK) {
                        String strResult = response.getContent();
                        JSONObject meta_json = new JSONObject(strResult)
                                .getJSONObject("meta");
                        ResponseResult result = gson.fromJson(meta_json.toString(),
                                ResponseResult.class);
                        if (result.isSuccess()) {
                            JSONObject data_json = new JSONObject(strResult)
                                    .getJSONObject("data");
                            getStaffOrderResult = gson.fromJson(
                                    data_json.toString(), GetStaffOrderResult.class);
                            rows.addAll(getStaffOrderResult.getRows());
                            if (isJiazai) {
                                handler.sendEmptyMessage(100);
                            } else {
                                handler.sendEmptyMessage(200);
                            }
                        }
                    }
                } catch (NetException e) {
                    Log.e("doGetMyOrder", e.getMessage());
                } catch (JSONException e) {
                    Log.e("doGetMyOrder", e.getMessage());
                } finally {
                }
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (rows.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        kuaidi_xrecycleView.setVisibility(View.GONE);
                    } else {
                        baseStaffOrderAdapter = new BaseStaffOrderAdapter(context, rows, mTranslatesString) {
                            @Override
                            public void update(List<GetStaffOrderResult.StaffOrderList> mData, int type, int id) {
                                if (type == 1) {
                                    //跳转到关联订单
                                    Intent integer = new Intent(context, GuanLianOrderActivity.class);
                                    integer.putExtra("tag", "1");
                                    integer.putExtra("orderId", id + "");
                                    context.startActivity(integer);
                                }
                                if (type == 2) {
                                    //确认收款
                                    submit(id + "");
                                }
                                if (type == 3) {
                                    //跳转到拒收
                                    Intent integer = new Intent(context, GuanLianOrderActivity.class);
                                    integer.putExtra("tag", "2");
                                    integer.putExtra("orderId", id + "");
                                    context.startActivity(integer);
                                }
                            }
                        };
                        kuaidi_xrecycleView.setAdapter(baseStaffOrderAdapter);
                        kuaidi_xrecycleView.refreshComplete();
                    }
                    dissLoading();
                    break;
                case 200:
                    baseStaffOrderAdapter.notifyDataSetChanged();
                    kuaidi_xrecycleView.loadMoreComplete();
                    break;
                case 300:
                    try {
                        Toast.makeText(context, meta.getString("errorInfo"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 400:
                    try {
                        rows.clear();
                        baseStaffOrderAdapter.notifyDataSetChanged();
                        doGetStaffMyOrder(0, type, true);
                        Toast.makeText(context, meta.getString("errorInfo"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    //确认收货
    private void submit(final String orderId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("orderId", orderId);
                map.put("function", "2");

                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "staff/confirmOrder.htm", map,
                            true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    String content = response.getContent();
                    JSONObject json = new JSONObject(content);
                    meta = json.getJSONObject("meta");
                    boolean success = meta.getBoolean("success");
                    if (success) {
                        handler.sendEmptyMessage(400);
                    } else {
                        handler.sendEmptyMessage(300);
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(myGuangbo);
    }
    public void receicer(){
        myGuangbo = new MyGuangbo();
        IntentFilter filter = new IntentFilter();
        filter.addAction("tagg");//只有持有相同的action的接受者才能接收此广播
        context.registerReceiver(myGuangbo, filter);
    }


    public class MyGuangbo extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (rows!=null){
                rows.clear();
                jilu=0;
                doGetStaffMyOrder(0, type, true);
            }
        }
    }
}
