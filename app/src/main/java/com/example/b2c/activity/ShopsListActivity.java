package com.example.b2c.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.ShopListAdapter;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.ShopListDetail;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家搜索——店铺列表
 */
public class ShopsListActivity extends TempBaseActivity {

    private ListView courier_list;
    private RefreshLayout mRefresh;

    //搜索名称参数
    private String search_name = "";
    //店铺容器
    private List<ShopListDetail> shop_list = null;
    //店铺适配器
    private ShopListAdapter shop_adapter = null;

    boolean hasNext = false;
    int pageNum = 1;

    @Override
    public int getRootId() {
        return R.layout.activity_shops_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shop_list = new ArrayList<>();
        shop_adapter = new ShopListAdapter(this);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_shoplist());
    }

    private void initView() {
        search_name = getIntent().getExtras().getString("search_name");
        courier_list = (ListView) findViewById(R.id.courier_list);
        courier_list.setAdapter(shop_adapter);
        mRefresh = (RefreshLayout) findViewById(R.id.refresh);
        mRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoadMore() {
                if (hasNext) {
                    initData(false);
                }
            }

            @Override
            public void onRefresh() {
                initData(true);
            }
        });
        mRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData(true);
            isFirstLoad = false;
        }
    }

    private void initData(final boolean refresh) {
        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }
        }else {
            pageNum = 1;
        }
        rdm.GetShopBySearch(unLogin, search_name, pageNum);
        mbuyerDataConnection.mPageListHasNextListener = new PageListHasNextListener<ShopListDetail>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<ShopListDetail> pageList = (List<ShopListDetail>) list;
                if (refresh) {
                    if (shop_list != null) {
                        shop_list.clear();
                        shop_list = pageList;
                    }
                } else {
                    shop_list.addAll(pageList);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        if (shop_list.isEmpty()) {
                            mEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mEmpty.setVisibility(View.GONE);
                        }
                        shop_adapter.setData(shop_list);
                        Logs.d("shop_adapter", shop_list.size() + "");
                        Logs.d("pageNum", hasNext + "");
                        hasNext = hasNests;
                        if (hasNext) {
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
}
