package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.common.CategoryAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.CategoryModule;

import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class CategoryActivity extends TempBaseActivity {
    public static ItemClick mListener;
    @Bind(R.id.list)
    ListView mList;
    private CategoryAdapter mAdapter;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_catrgory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle(mTranslatesString.getSeller_productCategory());
        mAdapter = new CategoryAdapter(this) {
            @Override
            public void onItemSelect(CategoryModule district) {
                mListener.onItemClick(district);
                finish();
            }

            @Override
            public void onItemNext(CategoryModule district) {
                setTitle(district.getName());
                initData(district.getId());
            }
        };
        initData(0);
        mList.setAdapter(mAdapter);
    }

    private void initData(int categoryId) {
        showLoading();
        sellerRdm.getCategoryById(categoryId);
        sellerRdm.mPageListListener = new PageListListener<CategoryModule>() {
            @Override
            public void onSuccess(final List<CategoryModule> list) {
                if (list != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
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

    public static void getInstance(TempBaseActivity activity, ItemClick itemClick) {
        mListener = itemClick;
        activity.startActivity(new Intent(activity, CategoryActivity.class));
    }

    public interface ItemClick {
        void onItemClick(CategoryModule value);
    }
}
