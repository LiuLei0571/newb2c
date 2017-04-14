package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.logistics.MuBanBean;
import com.example.b2c.adapter.logistics.FreightTemplateAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.widget.util.ListDividerItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费模板的列表
 */
public class FreightTemplateListActivity extends TempBaseActivity implements View.OnClickListener {

    private XRecyclerView freight_template_recycle;
    private FreightTemplateAdapter freightTemplateAdapter;
    private MuBanBean muBanBean;
    private List<MuBanBean.Rows> rows;
    private TextView tv_right;

    @Override
    public int getRootId() {
        return R.layout.activity_freight_template;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        showLoading();
        requestData(true,0);
    }

    private void initView() {
        tv_right = (TextView) findViewById(R.id.toolbar_right_text);
        mTitle.setText(mTranslatesString.getCommon_yunfeimuban());
        freight_template_recycle = (XRecyclerView) findViewById(R.id.freight_template_recycle);
        freight_template_recycle.setLayoutManager(new LinearLayoutManager(this));
        freight_template_recycle.addItemDecoration(new ListDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,20,getResources().getColor(R.color.background)));
        tv_right.setText(mTranslatesString.getCommon_add());
        tv_right.setVisibility(View.GONE);
//        tv_right.setOnClickListener(this);
    }

    /**
     * 请求数据
     */
    private int jilu;
        private void initData(){
            rows=new ArrayList<>();
            freight_template_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    //下啦刷新
                    rows.clear();
                    freightTemplateAdapter.notifyDataSetChanged();
                    jilu=0;
                            requestData(true,0);
                }

                @Override
                public void onLoadMore() {
                    if (muBanBean.isHasNext()){
                        //如果还有数据，要先判断是否是
                        jilu=jilu+20;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                requestData(false,jilu);
                            }
                        }).start();
                    }else{
                        freight_template_recycle.loadMoreComplete();
                    }
                }
            });
    }
    private void requestData(final boolean isNofly, final int start){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String>map=new HashMap<String, String>();
                map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
//                map.put("startRow",start+"");
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "express/getDefaultFreightTemplate.htm",
                            map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode()==200){
                        String content = response.getContent();
                        if (content!=null){
                            try {
                                JSONObject jsonObject = new JSONObject(content);
                                JSONObject meta = jsonObject.getJSONObject("meta");
                                Gson gson=new Gson();
                                if (meta.getBoolean("success")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    muBanBean = gson.fromJson(data.toString(), MuBanBean.class);
                                    rows.addAll(muBanBean.getTemplateDetails());
                                    if (isNofly) {
                                        handler.sendEmptyMessage(100);
                                    } else {
                                        handler.sendEmptyMessage(200);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    dissLoading();
                    freightTemplateAdapter = new FreightTemplateAdapter(getApplication(),rows,mTranslatesString);
                    freight_template_recycle.setAdapter(freightTemplateAdapter);
                    freight_template_recycle.refreshComplete();
                    break;
                case 200:
                    freightTemplateAdapter.notifyDataSetChanged();
                    freight_template_recycle.loadMoreComplete();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_right_text:
                startActivity(new Intent(FreightTemplateListActivity.this,FreightTemplateDetailsActivity.class));
                break;
        }
    }
}
