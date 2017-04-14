package com.example.b2c.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.adapter.ClassifyExpandListAdapter;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.FirstCategoryDetail;
import com.example.b2c.net.response.TransFirstCategory;
import com.example.b2c.net.response.TransSecondCategory;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.RemoteDataConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 */
public class HomeClassifyFragment extends BaseFragment {
    private View mView;
    private TextView tv_classify_title;
    private ExpandableListView elv_classify;
    private List<FirstCategoryDetail> category_list;
    private RelativeLayout mRlytTop;
    private RemoteDataConnection rdmm;
//    @Override
//    protected int getContentViewId() {
//        return R.layout.fragment_classify_layout;
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView =  inflater.inflate(R.layout.fragment_classify_layout, container, false);
        elv_classify = (ExpandableListView) mView.findViewById(R.id.elv_classify);
        tv_classify_title=(TextView)mView.findViewById(R.id.tv_classify_title) ;
        initText();
        return mView;
    }
    //    @Override
//    protected void (View rootView) {
//
//        elv_classify = (ExpandableListView) rootView.findViewById(R.id.elv_classify);
//        tv_classify_title=(TextView)rootView.findViewById(R.id.tv_classify_title) ;
//        initText();
//    }

    public void initText() {
        tv_classify_title.setText(mTranslatesString.getCommon_sampleclassify());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }

    public void initData() {
        showLoading();
        rdmm = RemoteDataConnection.getInstance();
        rdmm.GetCategoryList(unLogin, userId, token);
        rdmm.mPageListListener = new PageListListener<FirstCategoryDetail>() {
            @Override
            public void onFinish() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissLoading();
                    }
                });
            }

            @Override
            public void onSuccess(List<FirstCategoryDetail> list) {
                category_list = list;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(category_list);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showDialogOnUI("Notice", errorInfo);
            }

            @Override
            public void onLose() {
                showErrorToast(requestFailure);
            }

        };

    }

    public void setAdapter(List<FirstCategoryDetail> category_list) {
        ClassifyExpandListAdapter adapter_ = new ClassifyExpandListAdapter(
                getActivity(), TransCategory(), "fragment") {
            @Override
            public void itemClick(String name) {
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoryId", name);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d("HomeClassifyFragment onActivityResult",requestCode+":"+resultCode+":"+data);
        initData();
    }
}
