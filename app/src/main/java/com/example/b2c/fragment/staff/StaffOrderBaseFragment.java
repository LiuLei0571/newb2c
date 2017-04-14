package com.example.b2c.fragment.staff;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.logistic.GuanLianOrderActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.adapter.staff.BaseStaffOrderAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.DialogGetHeadPicture;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.StaffOrderList;
import com.example.b2c.net.response.logistics.GetStaffOrderResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.example.b2c.zxing.MipcaActivityCapture;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static android.app.Activity.RESULT_OK;

/**
 * 用途： * Created by milk on 17/2/3.
 * 邮箱：649444395@qq.com
 */
@SuppressLint("ValidFragment")
public class StaffOrderBaseFragment extends TempleBaseFragment {
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
    //    private ShuXin shuXin;

    public StaffOrderBaseFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_staff_order_base;
    }
    private boolean isReady = false;
    @Override
    protected void initView(View rootView) {
        mOrderLists = new ArrayList<>();
        kuaidi_xrecycleView = (XRecyclerView) rootView.findViewById(R.id.kuaidi_xrecycleView);
        kuaidi_xrecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        kuaidi_xrecycleView.addItemDecoration(new ListDividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.background)));
        empty = (RelativeLayout) rootView.findViewById(R.id.empty);
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
                        kuaidi_xrecycleView.noMoreLoading();
                    }
            }
        });
    }
    public void doGetStaffMyOrder(final int pageNum, final int orderStatus, final boolean isJiazai) {
        rows=new ArrayList<>();
        if (pageNum==0&&baseStaffOrderAdapter!=null){
            rows.clear();
            baseStaffOrderAdapter.notifyDataSetChanged();
        }
        final Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        map.put("startRow", pageNum + "");
        map.put("orderStatus", orderStatus + "");
        OkhttpUtils instance = OkhttpUtils.getInstance();
        try {
            instance.doPost(getActivity(),ConstantS.BASE_URL
                            + "staff/orderList.htm", map, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken(),
                     new OkhttpUtils.MyCallback() {
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

                         @Override
                        public void onSuccess(String strResult){
                            try {
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
                                            srccer();
                                        } else {
                                            baseStaffOrderAdapter.notifyDataSetChanged();
                                            kuaidi_xrecycleView.loadMoreComplete();
                                        }

                                }
                            } catch (JSONException e) {
                                Log.e("doGetMyOrder", e.getMessage());
                            } finally {
                            }
                        }
                    });
        } catch (NetException e) {
          dissLoading();
            e.printStackTrace();
        }
    }
private int myId;//记录点击处的id
    /**
     * 加载成功执行的方法
     */
    private void srccer(){
        if (rows.size() == 0) {
            empty.setVisibility(View.VISIBLE);
            kuaidi_xrecycleView.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            kuaidi_xrecycleView.setVisibility(View.VISIBLE);
            baseStaffOrderAdapter = new BaseStaffOrderAdapter(getActivity(), rows, mTranslatesString) {
                @Override
                public void update(List<GetStaffOrderResult.StaffOrderList> mData, int type, final int id) {
                    myId=id;
                    if (type == 1) {
                        new DialogGetHeadPicture(getActivity(), R.style.progress_dialog,mTranslatesString.getCommon_saomiao(),
                                mTranslatesString.getCommon_shurukuaidinumber(), mTranslatesString.getNotice_cancel()
                        ) {
                            @Override
                            public void saoma() {
                                //进行扫码
                                saceCode(SCANNIN_GREQUEST_CODE);
                            }

                            @Override
                            public void shuru() {
                                //跳转到关联订单
                                Intent integer = new Intent(getActivity(), GuanLianOrderActivity.class);
                                integer.putExtra("tag", "1");
                                integer.putExtra("orderId", id + "");
                                startActivity(integer);
                            }
                        }.show();

                    }
                    if (type == 2) {
                        //确认收款
//                        submit(id + "");
                        getHuo("2",null);
                    }
                    if (type == 3) {
                        //跳转到拒收
                        Intent integer = new Intent(getActivity(), GuanLianOrderActivity.class);
                        integer.putExtra("tag", "2");
                        integer.putExtra("orderId", id + "");
                        startActivity(integer);
                    }
                }
            };
            kuaidi_xrecycleView.setAdapter(baseStaffOrderAdapter);
            kuaidi_xrecycleView.refreshComplete();
        }
        dissLoading();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myGuangbo);
    }
    public void receicer(){
        myGuangbo = new MyGuangbo();
        IntentFilter filter = new IntentFilter();
        filter.addAction("tagg");//只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(myGuangbo, filter);
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
private void saceCode(int requestCode){
//    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED) {
//        //申请WRITE_EXTERNAL_STORAGE权限
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
//                WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//    }
        Intent intent = new Intent();
        intent.setClass(getActivity(), MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, requestCode);

}
    final int SCANNIN_GREQUEST_CODE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCANNIN_GREQUEST_CODE:
                    if(resultCode == RESULT_OK){
                        Bundle bundle = data.getExtras();
                        //显示扫描到的内容
                        String result = bundle.getString("result");
                        getHuo("1",result);
                    }
                    break;
            }
            }
        }
    /**
     * 确认收货，待取货的方法
     */
    private void getHuo(String tag,String conter){
        showLoading();
        Map<String,String>map=new HashMap<String, String>();
        if (tag.equals("1")){
            //说明是取货
            map.put("orderId",myId+"");
            map.put("function","1");
            map.put("deliveryNo",conter);
        }else{
            //确认收货
            map.put("orderId",myId+"");
            map.put("function","2");
        }
        mLogisticsDataConnection.guanlian(map);
        mLogisticsDataConnection.mNodataListener=new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                rows.clear();
                baseStaffOrderAdapter.notifyDataSetChanged();
                doGetStaffMyOrder(0, type, true);
                Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int errorNo, String errorInfo) {
                Toast.makeText(getActivity(), errorInfo, Toast.LENGTH_LONG).show();
                dissLoading();
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
            dissLoading();
            }
        };
    }
}
