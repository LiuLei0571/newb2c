package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.SampleListAdapter;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.SampleListDetail;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品列表页面
 */
public class GoodsListActivity extends TempBaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    ListView lv_list;
    LinearLayout ll_goodList_radio;
    RadioButton rb_by_sold, rb_by_price, rb_by_time;
    RefreshLayout mRefresh;

    //产品适配器
    private SampleListAdapter sample_adapter ;

    private String categoryId, search_name = "";
    private int search_type, shopId;

    private static int  sort_by_price = 1, sort_by_sold = 2, sort_by_time = 3, ascending = 1, descending = 2;
    //默认按时间降序排列
    private int sort_type = sort_by_time , sort_way = descending ;
    //产品容器
    private List<SampleListDetail> sample_list = null;


    private String[] rb_string = null;
    private boolean ascending_sold = true, ascending_price = true,ascending_time = true ,is_search=false;

    boolean hasNext = false;
    int pageNum = 1;


    @Override
    public int getRootId() {
        return R.layout.goods_list_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addImage(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodsListActivity.this, SearchActivity.class));
            }
        });
        sample_adapter = new SampleListAdapter(this);
        sample_list = new ArrayList<>();
        rb_string = new String[]{
                mTranslatesString.getCommon_sellamount() + " ↑",
                mTranslatesString.getCommon_sellamount() + " ↓",
                mTranslatesString.getCommon_price() + " ↑",
                mTranslatesString.getCommon_price() + " ↓",
                mTranslatesString.getCommon_sellingtime() + " ↑",
                mTranslatesString.getCommon_sellingtime() + " ↓"};
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_samplelist());
        rb_by_sold.setText(rb_string[0]);
        rb_by_price.setText(rb_string[2]);
        rb_by_time.setText(rb_string[5]);
    }

    public void initView() {
        rb_by_sold = (RadioButton) findViewById(R.id.rb_by_sold);
        rb_by_price = (RadioButton) findViewById(R.id.rb_by_price);
        rb_by_time = (RadioButton) findViewById(R.id.rb_by_time);
        ll_goodList_radio = (LinearLayout) findViewById(R.id.ll_goodslist_radio);
        lv_list = (ListView) findViewById(R.id.courier_list);
        lv_list.setAdapter(sample_adapter);

        rb_by_sold.setOnClickListener(this);
        rb_by_price.setOnClickListener(this);
        rb_by_time.setOnClickListener(this);

        mRefresh = (RefreshLayout) findViewById(R.id.refresh);

        mRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoadMore() {
                if (hasNext) {
                    initData(false,sort_type,sort_way);
                }
            }

            @Override
            public void onRefresh() {
                initData(true,sort_type,sort_way);
            }
        });
        mRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue);
//        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData(true,sort_type,sort_way);
            isFirstLoad = false;
        }
    }

    public void initData(final boolean refresh ,int sort_type, int sort_way ) {
        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }
        }else {
            pageNum = 1;
        }
        Bundle bundle = getIntent().getExtras();
        categoryId = bundle.getString("categoryId");
        search_name = bundle.getString("search_name");
        search_type = bundle.getInt("search_type");
        shopId = bundle.getInt("shopId");
        is_search= bundle.getBoolean("is_search");
        Logs.d("categoryId",categoryId+"");
        Logs.d("search_name",search_name+"");
        Logs.d("search_type",search_type+"");
        Logs.d("shopId",shopId+"");
        Logs.d("is_search",is_search+"");
        if(is_search && search_type == SearchActivity.SAMPLE){
            Map<String, String> param = new HashMap<>();
            param.put("name", search_name);
            param.put("pageNum", "" + pageNum);
            param.put("sortType", "" + sort_type);
            param.put("sortWay", "" + sort_way);
            Logs.d("search goods param  Map :",param.toString());
            rdm.GetCategorySample(unLogin, param);
        }else if((search_name == null || search_name.equals("")) && shopId == 0){
            Map<String, String> param = new HashMap<>();
            param.put("categoryId", "" + categoryId);
            param.put("pageNum", "" + pageNum);
            param.put("sortType", "" + sort_type);
            param.put("sortWay", "" + sort_way);
            Logs.d("search goods param  Map :",param.toString());
            rdm.GetCategorySample(unLogin, param);
        }else if (shopId != 0 && search_type == SearchActivity.SAMPLE) {// 从店铺详情查看商品列表activity跳转
            Map<String, String> param = new HashMap<>();
            param.put("shopId", shopId + "");
            param.put("pageNum", "" + pageNum);
            param.put("sortType", "" + sort_type);
            param.put("sortWay", "" + sort_way);
            Logs.d("search goods param  Map :",param.toString());
            rdm.GetCategorySample(unLogin, param);
        }
         mbuyerDataConnection.mPageListHasNextListener = new PageListHasNextListener<SampleListDetail>() {
                @Override
                public void onSuccess(List list, final boolean hasNests) {
                    final List<SampleListDetail> pageList = (List<SampleListDetail>) list;
                    if (refresh) {
                        if (sample_list!=null) {
                            sample_list.clear();
                            sample_list = pageList;
                        }
                    } else {
                        sample_list.addAll(pageList);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);

                            if (sample_list.isEmpty()) {
                                    mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
                            sample_adapter.setData(sample_list);
                            Logs.d("sample_adapter",sample_list.size()+"");
                            Logs.d("pageNum",hasNext+"");
                            hasNext = hasNests;
                            if(hasNext){
                                pageNum++;
                            }
                        }
                    });
                }
                @Override
                public void onError(int errorNO, String errorInfo) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);
                        }
                    });


                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onLose() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);
                        }
                    });

                }

            };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_by_sold:
                sort_type = sort_by_sold;
                if (ascending_sold) {
                    rb_by_sold.setText(rb_string[0]);
                    sort_way = ascending;
                    ascending_sold = false;
                    initData(true,sort_type, sort_way);

                } else {
                    rb_by_sold.setText(rb_string[1]);
                    sort_way = descending;
                    ascending_sold = true;
                    initData(true,sort_type, sort_way);
                }
                break;
            case R.id.rb_by_price:
                sort_type = sort_by_price;
                if (ascending_price) {
                    rb_by_price.setText(rb_string[2]);
                    sort_way = ascending;
                    ascending_price = false;
                    initData(true,sort_type, sort_way);
                } else {
                    rb_by_price.setText(rb_string[3]);
                    sort_way = descending;
                    ascending_price = true;
                    initData(true,sort_type, sort_way);
                }
                break;
            case R.id.rb_by_time:
                sort_type = sort_by_time;
                if (ascending_time) {
                    rb_by_time.setText(rb_string[4]);
                    sort_way = ascending;
                    ascending_time = false;
                    initData(true,sort_type, sort_way);
                } else {
                    rb_by_time.setText(rb_string[5]);
                    sort_way = descending;
                    ascending_time = true;
                    initData(true,sort_type, sort_way);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
