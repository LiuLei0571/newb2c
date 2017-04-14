package com.example.b2c.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.b2c.R;
import com.example.b2c.adapter.ClassifyExpandListAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.FirstCategoryDetail;
import com.example.b2c.net.response.TransFirstCategory;
import com.example.b2c.net.response.TransSecondCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 */
public class ClassifyActivity extends TempBaseActivity {
    private ExpandableListView elv_classify;
    private List<FirstCategoryDetail> category_list;
    public static ItemClick mListener;

    @Override
    public int getRootId() {
        return R.layout.activity_classify_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {

        elv_classify = (ExpandableListView) findViewById(R.id.elv_classify);
        initText();
        initData();
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_sampleclassify());
    }

    public void initData() {
        showLoading();
        rdm.GetCategoryList(unLogin, userId, token);
        rdm.mPageListListener = new PageListListener<FirstCategoryDetail>() {
            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissLoading();
                    }
                });
            }

            @Override
            public void onSuccess(List<FirstCategoryDetail> list) {
                category_list = list;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(category_list);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast("网络异常");

            }
        };
    }

    public void setAdapter(List<FirstCategoryDetail> category_list) {
        ClassifyExpandListAdapter adapter_ = new ClassifyExpandListAdapter(
                this, TransCategory(), "activity") {
            @Override
            public void itemClick(String name) {
                mListener.onItemClick(name);
                finish();
            }
        };
        elv_classify.setAdapter(adapter_);
    }

    public List<TransFirstCategory> TransCategory() {// 由于分类使用的是二级扩展目录，而传回的是三级目录，所以将三级目录转换为二级目录进行显示，同时将目录的值也做复制
        TransCategory transCategory = new TransCategory();
        for (int i = 0; i < category_list.size(); i++) {
            TransFirstCategory temp_first = new TransFirstCategory();
            temp_first.setId(category_list.get(i).getId());
            temp_first.setName(category_list.get(i).getName());
            temp_first.setIdPath(category_list.get(i).getIdPath());
            temp_first.setCategoryLevel(category_list.get(i).getCategoryLevel());
            for (int j = 0; j < category_list.get(i).getSecondCategoryList()
                    .size(); j++) {
                TransSecondCategory temp_second = new TransSecondCategory();
                temp_second.setId(category_list.get(i).getSecondCategoryList()
                        .get(j).getId());
                temp_second.setName(category_list.get(i)
                        .getSecondCategoryList().get(j).getName());
                temp_second.setCategoryLevel(category_list.get(i)
                        .getSecondCategoryList().get(j).getCategoryLevel());
                temp_first.getSecondCategoryList().add(temp_second);
                for (int k = 0; k < category_list.get(i)
                        .getSecondCategoryList().get(j).getThirdCategoryList()
                        .size(); k++) {
                    TransSecondCategory temp_second_ = new TransSecondCategory();
                    temp_second_.setId(category_list.get(i)
                            .getSecondCategoryList().get(j)
                            .getThirdCategoryList().get(k).getId());
                    temp_second_.setName(category_list.get(i)
                            .getSecondCategoryList().get(j)
                            .getThirdCategoryList().get(k).getName());
                    temp_second_.setCategoryLevel(category_list.get(i)
                            .getSecondCategoryList().get(j)
                            .getThirdCategoryList().get(k).getCategoryLevel());
                    temp_first.getSecondCategoryList().add(temp_second_);
                }
            }
            transCategory.getFirstCategoryList().add(temp_first);
        }
        return transCategory.getFirstCategoryList();
    }

    public static void getInstance(TempBaseActivity activity, ItemClick itemClick) {
        mListener = itemClick;
        activity.startActivity(new Intent(activity, ClassifyActivity.class));
    }


    class TransCategory {
        private ArrayList<TransFirstCategory> firstCategoryList = new ArrayList<TransFirstCategory>();

        public ArrayList<TransFirstCategory> getFirstCategoryList() {
            return firstCategoryList;
        }

        public void setFirstCategoryList(
                ArrayList<TransFirstCategory> firstCategoryList) {
            this.firstCategoryList = firstCategoryList;
        }
    }

    public interface ItemClick {
        void onItemClick(String value);
    }
}
