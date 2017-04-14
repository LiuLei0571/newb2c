package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.GoodsSpecificationAdapter;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.CategoryDetailModule;
import com.example.b2c.net.response.seller.PropertyDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class GoodsSpecificationActivity extends TempBaseActivity {
    @Bind(R.id.list)
    ListView list;
    public static ValueSubmitListener mListener;
    GoodsSpecificationAdapter mAdapter;
    private List<CategoryDetailModule> moduleLists = new ArrayList<>();
    private int categoryId;
    public List<CategoryDetailModule> moduleListReturn = new ArrayList<>();
    public List<CategoryDetailModule> moduleListCallback = new ArrayList<>();
    public List<PropertyDetails> propertyDetails = new ArrayList<>();

    @Override
    public int getRootId() {
        return R.layout.activity_seller_goods_specification;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_specifications());
        categoryId = getIntent().getIntExtra("categoryId", -1);
        mAdapter = new GoodsSpecificationAdapter(this) {
            @Override
            public void onListenterData(List<CategoryDetailModule> moduleList) {
                moduleLists = moduleList;

            }
        };
        list.setAdapter(mAdapter);
        addText(mTranslatesString.getCommon_sure(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moduleLists.size() > 1) {
                    mListener.onItemClick(moduleListCallback);

                } else {
                    mListener.onItemClick(moduleLists);

                }
                finish();
            }
        });
        initData(categoryId);
    }

    private void initData(int categoryId) {

        showLoading();
        sellerRdm.getCategoryDetail(1641);
        sellerRdm.mPageListListener = new PageListListener<CategoryDetailModule>() {
            @Override
            public void onSuccess(final List<CategoryDetailModule> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moduleListReturn = list;
                        mAdapter.setData(moduleListReturn);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {

            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {

            }
        };
    }

    public static void getInstance(ReleaseProductActivity mContext, int categoryId, ValueSubmitListener mCallBack) {
        Intent intent = new Intent(mContext, GoodsSpecificationActivity.class);
        intent.putExtra("categoryId", categoryId);
        mListener = mCallBack;
        mContext.startActivity(intent);
    }

    public interface ValueSubmitListener {
        void onItemClick(List<CategoryDetailModule> module);
    }
}
