package com.example.b2c.fragment.livesCommunity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.LivesCommunity.JuBaoActivity;
import com.example.b2c.activity.LivesCommunity.LeiMuListActivity;
import com.example.b2c.activity.LivesCommunity.LeiMuMessageListActivity;
import com.example.b2c.activity.LivesCommunity.LivesCommunityLoginActivity;
import com.example.b2c.activity.LivesCommunity.MessageDetailsActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.adapter.livesCommunity.HomeListAdapter;
import com.example.b2c.adapter.livesCommunity.LeiMuGridViewAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.EventHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.livesCommunity.HomeLeiMu;
import com.example.b2c.net.response.livesCommunity.HomeList;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class LivesHomeFragment extends TempleBaseFragment {


    private XRecyclerView xrecycle_lives_home;
    private GridView lives_home_griaView;
    private OkhttpUtils instance;
    private Gson gson;
    private List<HomeLeiMu.Rows> rowsList;
    private HomeList homeList;
    private TextView tv_fankui;
    private List<HomeList.Rows> rows;
    private LocalBroadcastManager lm;
    private MyGungbo myGungbo;

    public LivesHomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lives_home;
    }
    @Override
    protected void initView(View rootView) {
        xrecycle_lives_home= (XRecyclerView) rootView.findViewById(R.id.xrecycle_lives_home);
        tv_fankui = (TextView) rootView.findViewById(R.id.tv_fankui);
        xrecycle_lives_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrecycle_lives_home.addItemDecoration(new ListDividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.background)));
        //头布局也就是那个gridView
        View headerView=View.inflate(getActivity(),R.layout.lives_home_hander,null);
        lives_home_griaView = (GridView) headerView.findViewById(R.id.lives_home_griaView);
        lives_home_griaView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        TextView tv_tuijian= (TextView) headerView.findViewById(R.id.tv_tuijian_title);
        tv_tuijian.setText(mTranslatesString.getCommon_livesLeimu());
        tv_fankui.setText(mTranslatesString.getCommon_fankui());
        xrecycle_lives_home.addHeaderView(headerView);
        instance = OkhttpUtils.getInstance();
        lm = LocalBroadcastManager.getInstance(getActivity());
        gson = new Gson();
        rows = new ArrayList<>();
        initData();
        initLinster();

    }
    private void initData(){
    //请求类目
    mLogisticsDataConnection.getLivesLeimu(getActivity());
    mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
        @Override
        public void onSuccess(Object data, String successInfo) {
            HomeLeiMu homeLeiMu= (HomeLeiMu) data;
            rowsList = homeLeiMu.getRows();
            //设置类目的适配器
            LeiMuGridViewAdapter leiMuGridViewAdapter=new LeiMuGridViewAdapter(getActivity(),rowsList);
            lives_home_griaView.setAdapter(leiMuGridViewAdapter);
        }
        @Override
        public void onError(int errorNO, String errorInfo) {
            Toast.makeText(getActivity(),errorInfo,Toast.LENGTH_LONG).show();
        }
        @Override
        public void onFinish() {}
        @Override
        public void onLose() {}
    };

    requestTuiJianList(0,true);
}
    /**
     * 监听的方法
     */
    private int jilu;
    private void initLinster(){
        //组册广播
        myGungbo = new MyGungbo();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.plusjun.test.hahaha");
        lm.registerReceiver(myGungbo,filter);
        //下啦刷新  上拉加载
        xrecycle_lives_home.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                jilu=0;
                requestTuiJianList(jilu,true);
            }
            @Override
            public void onLoadMore() {
                if (homeList.isHasNext()){
                    jilu=jilu+20;
                    requestTuiJianList(jilu,false);
                }else{
                    xrecycle_lives_home .noMoreLoading();
                }
            }
        });
        //gridView的点击事件
        lives_home_griaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //跳转到列表
                Intent intent=new Intent(getActivity(),LeiMuMessageListActivity.class);
                intent.putExtra("categoryId",rowsList.get(i).getId()+"");
                intent.putExtra("categoryName",rowsList.get(i).getCategoryName());
                startActivity(intent);
            }
        });
        tv_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserHelper.isLivesLogin()){
                    //跳转到登录界面
                    startActivity(new Intent(getActivity(),LivesCommunityLoginActivity.class).putExtra("tag","fankui"));
                }else{
                    //跳转到反馈
                    Intent intent=new Intent(getActivity(),JuBaoActivity.class);
                    intent.putExtra("tag","fakui");
                    startActivity(intent);
                }


            }
        });



    }
    class MyGungbo extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //接到广播刷新
            jilu=0;
            requestTuiJianList(jilu,true);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.unregisterReceiver(myGungbo);
    }

    private HomeListAdapter homeListAdapter;
    /**
     * //请求推荐的list列表
     * @param startRow   开始页数
     */
    private void requestTuiJianList(int startRow, final boolean isJiazai){
        if (startRow == 0 && homeListAdapter != null) {
            homeListAdapter.notifyDataSetChanged();
            rows.clear();
        }
        Map<String,String>map=new HashMap<>();
        map.put("startRow",startRow+"");
        try {
            instance.doPost(getActivity(), ConstantS.BASE_URL + "life/index/getLatestInfoList.htm",
                    map, false, -1, null, new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            JSONObject meta = null;
                            try {
                                meta = new JSONObject(result).getJSONObject("meta");
                                ResponseResult metaData = gson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    homeList = gson.fromJson(data.toString(), HomeList.class);
                                     rows.addAll(homeList.getRows());
                                    //设置适配器
                                    if (isJiazai) {
                                        homeListAdapter = new HomeListAdapter(getActivity(), rows);
                                        xrecycle_lives_home.setAdapter(homeListAdapter);
                                        xrecycle_lives_home. refreshComplete();
                                    }else{
                                        homeListAdapter.notifyDataSetChanged();
                                        xrecycle_lives_home.loadMoreComplete();
                                    }
                                    //RecycleView的点击事件
                                    homeListAdapter.setOnItemClickListener(new XRcycleViewAdapterBase.OnRecyclerViewItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            startActivity(new Intent(getActivity(), MessageDetailsActivity.class).putExtra("id",rows.get(position).getId()+""));
                                        }
                                    });
                                }else{
                                    Toast.makeText(getActivity(),metaData.getErrorInfo(),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                dissLoading();
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

                        }
                    });
        } catch (NetException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),mTranslatesString.getCommon_neterror(),Toast.LENGTH_LONG).show();
        }

    }
}
