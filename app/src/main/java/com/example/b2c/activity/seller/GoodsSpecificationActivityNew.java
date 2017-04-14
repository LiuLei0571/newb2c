package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.CategoryDetailModule;
import com.example.b2c.net.response.seller.PropertyDetails;
import com.example.b2c.net.response.seller.SampleProInfosModule;
import com.example.b2c.widget.CustomCheckBox;
import com.example.b2c.widget.PriceLayout;
import com.example.b2c.widget.SpecificationLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public class GoodsSpecificationActivityNew extends TempBaseActivity implements CustomCheckBox.OnCheckListener {
    @Bind(R.id.list)
    LinearLayout mLineLayout;

    public static ValueSubmitListener mListener;
    private List<PropertyDetails> mPropertyDetails = new ArrayList<>();
    @Bind(R.id.price)
    LinearLayout mLinePrice;
    protected static List<CategoryDetailModule> listResult = new ArrayList<>();
    private int categoryId;
    protected List<SampleProInfosModule> mInfos = new ArrayList<>();
    protected static List<SampleProInfosModule> mInfosTest = new ArrayList<>();

    protected static List<List<PropertyDetails>> mPropertyList = new ArrayList<>();

    @Override
    public int getRootId() {
        return R.layout.activity_seller_goods_specification_new;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_specifications());
        categoryId = getIntent().getIntExtra("categoryId", -1);
        mLinePrice.removeAllViews();
        if (mPropertyList.size() > 0) {
            setData(listResult);
            reviewPrice(mInfosTest);
            setRightTitle(true);
        } else {
            initData(categoryId);
        }

    }


    public void setRightTitle(boolean isShow) {
        if (isShow) {
            addText(mTranslatesString.getCommon_sure(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mLinePrice.getChildCount(); i++) {
                        mInfos.add(((PriceLayout) mLinePrice.getChildAt(i)).getData());
                    }
                    mListener.onItemClick(mInfos, mPropertyList, listResult );
                    finish();
                }
            });
        }

    }

    private void reviewPrice(List<SampleProInfosModule> mInfos) {
        for (int i = 0; i < mLinePrice.getChildCount(); i++) {
            mInfos.add(((PriceLayout) mLinePrice.getChildAt(i)).getData());
            PriceLayout mItemView = (PriceLayout) mLinePrice.getChildAt(i);
            mItemView.setPrice(mInfos.get(i));
        }
    }

    private void initData(int categoryId) {
        showLoading();
        sellerRdm.getCategoryDetail(categoryId);
        sellerRdm.mPageListListener = new PageListListener<CategoryDetailModule>() {
            @Override
            public void onSuccess(final List<CategoryDetailModule> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() > 0) {
                            setRightTitle(true);
                        }
                        listResult = list;
                        setData(listResult);
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

    public static void getInstance(ReleaseProductActivity mContext, int categoryId, List<List<PropertyDetails>> mPropertyLists, List<CategoryDetailModule> listResults, List<SampleProInfosModule> sampleProInfos, ValueSubmitListener mCallBack) {
        Intent intent = new Intent(mContext, GoodsSpecificationActivityNew.class);
        intent.putExtra("categoryId", categoryId);
        mPropertyList = mPropertyLists;
        listResult = listResults;
        mListener = mCallBack;
        mInfosTest = sampleProInfos;
        mContext.startActivity(intent);
    }

    public void setData(List<CategoryDetailModule> data) {
        for (CategoryDetailModule mData : data) {
            SpecificationLayout mCustomView = new SpecificationLayout(this);
            mCustomView.setData(mData);
            mLineLayout.addView(mCustomView);
        }
    }

    @Override
    public void onItem(PropertyDetails data, int id, String name) {
        List<PriceLayout> mViews = new ArrayList<>();
        mPropertyList.clear();
        boolean flag = false;
        mLinePrice.removeAllViews();

        Map<Integer, List<PropertyDetails>> map = new HashMap<>();
        int position = 0;
        if (mPropertyDetails.size() == 0) {
            mPropertyDetails.add(data);
        } else {
            for (int i = 0; i < mPropertyDetails.size(); i++) {
                if (mPropertyDetails.get(i).getId() == data.getId()) {
                    if (i == 0) {
                        position--;
                    } else {
                        position = i;
                    }
                    break;
                }
            }
            if (position > 0 || position == -1) {
                if (position == -1) {
                    position++;
                }
                mPropertyDetails.remove(position);

            } else {
                mPropertyDetails.add(data);
            }
        }
        //end 获取单选按钮的结果集
        for (PropertyDetails mData : mPropertyDetails) {
            List list = map.get(mData.getPropertyId());
            if (list == null) {
                list = new ArrayList();
            }
            list.add(mData);
            map.put(mData.getPropertyId(), list);
        }
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            mPropertyList.add((List<PropertyDetails>) map.get(key));
        }
        if (mPropertyList.size() > 0) {
            List<PropertyDetails> l1 = mPropertyList.get(0);
            List<PropertyDetails> l2 = null;
            int n = listResult.size();
            if (listResult.size() == 1) {
                if (l1 != null) {
                    for (PropertyDetails mData : l1) {
                        PriceLayout mPriceLayout = new PriceLayout(this);
                        mPriceLayout.setData(mData);
                        mLinePrice.addView(mPriceLayout);
                    }
                }

            } else if (listResult.size() == mPropertyList.size()) {
                if (l1 != null) {
//                    if (n > 2) {
                    reviewData(mPropertyList, mPropertyList.get(0), null);
//                    } else {
//                        for (int i = 0; i < l1.size(); i++) {
//                            if (n > 1) {
//                                l2 = mPropertyList.get(1);
//                                for (int j = 0; j < l2.size(); j++) {
//                                    PriceLayout mPriceLayout = new PriceLayout(this);
//                                    mPriceLayout.setData(l1.get(i), l2.get(j));
//                                    mLinePrice.addView(mPriceLayout);
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }


    public void reviewData(List<List<PropertyDetails>> list, List<PropertyDetails> arr, PropertyDetails test) {

        for (int i = 0; i < list.size(); i++) {
            // 取得当前的数组
            if (i == list.indexOf(arr)) {
                // 迭代数组
                for (PropertyDetails st : arr) {
                    List<PropertyDetails> detailses = new ArrayList<>();
                    if (test != null) {
                        detailses.add(test);
                    }
                    detailses.add(st);
                    if (i < list.size() - 1) {
                        reviewData(list, list.get(i + 1), st);
                    } else if (i == list.size() - 1) {
                        PriceLayout mPriceLayout = new PriceLayout(this);
                        mPriceLayout.setData(detailses);
                        mLinePrice.addView(mPriceLayout);
                    }
                }
            }
        }
    }

    public interface ValueSubmitListener {
        void onItemClick(List<SampleProInfosModule> mInfos, List<List<PropertyDetails>> mPropertyList, List<CategoryDetailModule> listReslut );
    }
}
