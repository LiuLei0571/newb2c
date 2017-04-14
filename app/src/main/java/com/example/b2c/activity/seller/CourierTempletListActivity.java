package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.TemplateAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.FreightTemplate;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 快递公司运费模板列表
 */
public class CourierTempletListActivity extends TempBaseActivity {
    private int  companyId;
    @Override
    public int getRootId() {
        return R.layout.activity_courier_templet_list;
    }

    @Bind(R.id.templet_list)
    ListView mTempletList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<FreightTemplate> mFreightTemplateList;
    private TemplateAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = getIntent().getExtras().getInt("id");
        mFreightTemplateList = new ArrayList<>();
        mAdapter = new TemplateAdapter(this);
        setTitle(mTranslatesString.getCommon_yunfeimuban());
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
        mTempletList.setAdapter(mAdapter);
    }

    private void initData(final Boolean refresh) {
        int mPage = 0;


        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }

            mPage = mFreightTemplateList.size();
        }
        sellerRdm.getAllFreightTemplateListById(ConstantS.BASE_URL + "seller/delivery/freightTemplateList.htm", mPage, companyId);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<FreightTemplate>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<FreightTemplate> pageList = (List<FreightTemplate>) list;
                if (refresh) {
                    mFreightTemplateList.clear();
                    mFreightTemplateList = pageList;

                } else {
                    mFreightTemplateList.addAll(pageList);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            if (pageList.isEmpty() && refresh) {
                                mEmpty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mEmpty.setVisibility(View.GONE);

                        }
                        mAdapter.notifyDataSetInvalidated();
                        mTempletList.invalidate();
                        mAdapter.setData(mFreightTemplateList);
                        hasNext = hasNests;
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                    }
                });

            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
                if (refresh) {
                    mRefresh.setRefreshing(false);
                }
                mRefresh.setLoading(false);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(net_error);
                if (refresh) {
                    mRefresh.setRefreshing(false);
                }
                mRefresh.setLoading(false);
            }

        };

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData(true);
    }
}
