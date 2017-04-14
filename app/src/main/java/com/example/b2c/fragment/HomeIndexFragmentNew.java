package com.example.b2c.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.LivesCommunity.LivesCommuntiyHomeActivty;
import com.example.b2c.activity.SearchActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.logistic.LanguageSettingActivity;
import com.example.b2c.adapter.base.TemplateRecyclerAdapterNew;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.listener.ModelTemplateListener;
import com.example.b2c.net.response.ModelPage;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.temple.ITemplateModel;
import com.example.b2c.temple.SimpleTemplateModel;
import com.example.b2c.temple.Templates;
import com.example.b2c.widget.util.Strings;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * 作者：Created by john on 2017/2/3.
 * 邮箱：liulei2@aixuedai.com
 */


public class HomeIndexFragmentNew extends TempleBaseFragment {
    @Bind(R.id.search)
    TextView mSearch;
    @Bind(R.id.title_bar)
    LinearLayout mTitleBar;
    @Bind(R.id.tv_data)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ModelPage> mModelPageList = new ArrayList<>();
    private TemplateRecyclerAdapterNew mAdapter;
    final List<ITemplateModel> list = new ArrayList<>();
    private TextView tv_home_setting;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_index_new;
    }

    @Override
    protected void initView(View rootView) {
        tv_home_setting = (TextView) rootView.findViewById(R.id.tv_home_setting);
        tv_home_setting.setText(mTranslatesString.getConmon_yuyanqiehuan());
        mAdapter = new TemplateRecyclerAdapterNew(this);
        mSearch.setText(mTranslatesString.getCommon_searchsampleshop());
        mRecyclerView.setAdapter(mAdapter);
        final int allColunms = 2;
        mLayoutManager = new GridLayoutManager(getActivity(), allColunms);
        ((GridLayoutManager) mLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int column = allColunms;
                ITemplateModel iTemplateModel = mAdapter.getItem(position);
                String templateType = Strings.EMPTY;
                if (iTemplateModel != null) {
                    Templates templates = iTemplateModel.getTemplate();
                    boolean isOneRow = templates.isInOneRow();

                    if (!isOneRow) {
                        column = 1;
                    }
                    templateType = templates.name();
                }
                return column;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    public void initData() {
        showLoading();
        rdm.initHomePageNew(unLogin, userId, token);
        rdm.mTemplateListener = new ModelTemplateListener() {
            @Override
            public void onSuccess(List<ModelPage> modelPageList, String msg) {
                mModelPageList = modelPageList;
                formatData(mModelPageList);
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
                dissLoading();

            }
        };
    }

    public void formatData(List<ModelPage> data) {
        if (data != null) {
            for (ModelPage modelPage : data) {
                if (modelPage == null) {
                    continue;
                }
                list.add(modelPage);
                if (Strings.TRUE.equals(modelPage.getSeparatorVisible())) {
                    list.add(new SimpleTemplateModel(Templates.LOCAL_DRIVER));
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setDataWithNotify(list);
                    mAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    @OnClick(R.id.title_bar)
    public void onClick() {
        getIntent(getActivity(), SearchActivity.class);
    }

    @OnClick(R.id.iv_lanuage)
    public void onClicks() {
        getIntent(getActivity(), LivesCommuntiyHomeActivty.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        mSearch.setText(mTranslatesString.getCommon_searchsampleshop());
        tv_home_setting.setText(mTranslatesString.getConmon_yuyanqiehuan());
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        list.clear();
        mModelPageList.clear();
    }

}
