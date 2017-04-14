package com.example.b2c.adapter.seller;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.CategoryDetailModule;
import com.example.b2c.net.response.seller.PropertyDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public abstract class GoodsSpecificationAdapter extends BaseAdapter<CategoryDetailModule> {
    public List<CategoryDetailModule> moduleList = new ArrayList<>();
    public List<PropertyDetails> propertyDetails = new ArrayList<>();
    GoodsSpecificationDetailAdapter mAdapter;

    public GoodsSpecificationAdapter(Context context) {
        super(context, R.layout.item_goods_specification_list);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        TextView mTv = viewHolderFactory.findViewById(R.id.title);
        GridView mGrid = viewHolderFactory.findViewById(R.id.grid);
        final CategoryDetailModule module = getItem(position);
        if (module != null) {
            mTv.setText(module.getPropertyName());
            mAdapter = new GoodsSpecificationDetailAdapter(getContext()) {
                @Override
                public void itemOnClickListener(PropertyDetails mList) {
                    int result = 0;
                    CategoryDetailModule moduleTest = new CategoryDetailModule();
                    for (int i = 0; i < moduleList.size(); i++) {
                        if (module.getPropertyId() == moduleList.get(i).getPropertyId()) {
                            result++;
                        }
                    }
                    if (mList.isSelect()) {
                        mList.setSelect(false);
                        propertyDetails.remove(mList);
                    } else {
                        mList.setSelect(true);
                        propertyDetails.add(mList);
                    }

                    moduleTest.setPropertyDetails(propertyDetails);
                    moduleTest.setPropertyName(module.getPropertyName());
                    moduleTest.setPropertyId(module.getPropertyId());
                    if (moduleList.size() == 0 || result == 0) {
                        moduleList.add(moduleTest);
                    }

                    mAdapter.notifyDataSetChanged();
                    notifyDataSetChanged();

                    onListenterData(moduleList);
                    Log.d("itemOnClickListener", "itemOnClickListener: " + moduleList.size());
                }
            };
            mAdapter.setData(module.getPropertyDetails());
            mGrid.setAdapter(mAdapter);

        }
    }

    public abstract void onListenterData(List<CategoryDetailModule> moduleList);
}
