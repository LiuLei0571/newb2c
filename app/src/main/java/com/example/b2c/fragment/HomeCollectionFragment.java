package com.example.b2c.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.OtherUserLoginActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.Collection_Sample_Adapter;
import com.example.b2c.adapter.Collection_Shop_Adapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.FavoriteSampleListener;
import com.example.b2c.net.listener.FavoriteShopListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.FavoriteSampleDetail;
import com.example.b2c.net.response.FavoriteShopDetail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.b2c.helper.ToastHelper.makeErrorToast;

/**
 * 收藏
 */
public class HomeCollectionFragment extends TempleBaseFragment implements OnClickListener {
    private boolean sample_or_shop = true;//true 表示获取产品列表  false 表示获取店铺列表
    private TextView tv_collection_title;
    private ListView lv_collection;
    private RadioGroup rg_collection;
    private RadioButton rb_sample, rb_shop;
    private LinearLayout mLytLogin;
    private LinearLayout mLytUnLogin;
    private View LoadMore;
    private Button btn_loadmore;
    private int pageNum = 1;

    private Collection_Sample_Adapter sample_adapter;
    private Collection_Shop_Adapter shop_adapter;

    private List<FavoriteSampleDetail> samplelist = new ArrayList<>();
    private List<FavoriteShopDetail> shoplist = new ArrayList<>();

    private static final int SAMPLE_TYPE = 1, SHOP_TYPE = 2, PAGESIZE = 20;

    private Button btn_2login;


    @Override
    protected int getContentViewId() {
        return R.layout.collection_layout;
    }

    @Override
    protected void initView(View mRootView) {
        mLytLogin= (LinearLayout) mRootView.findViewById(R.id.lyt_login);
        mLytUnLogin= (LinearLayout) mRootView.findViewById(R.id.lyt_un_login);
        if (UserHelper.isBuyerLogin()) {
            mLytLogin.setVisibility(View.VISIBLE);
            mLytUnLogin.setVisibility(View.GONE);
        } else {
            mLytLogin.setVisibility(View.GONE);
            mLytUnLogin.setVisibility(View.VISIBLE);
            btn_2login = (Button) mRootView.findViewById(R.id.btn_to_login);
            btn_2login.setText(mTranslatesString.getCommon_tologin());
            btn_2login.setOnClickListener(this);
            return;
        }
        mEmpty = (RelativeLayout) mRootView.findViewById(R.id.empty);
        tv_nodata = (TextView) mRootView.findViewById(R.id.tv_nodata);
        tv_nodata.setText(mTranslatesString.getCommon_nodata());


        tv_collection_title = (TextView) mRootView.findViewById(R.id.tv_collection_title);
        rg_collection = (RadioGroup) mRootView.findViewById(R.id.rg_collection);
        rb_sample = (RadioButton) mRootView.findViewById(R.id.rb_collcetion_goods);
        rb_shop = (RadioButton) mRootView.findViewById(R.id.rb_collcetion_shop);

        lv_collection = (ListView) mRootView.findViewById(R.id.lv_collection);


        LoadMore = View.inflate(getActivity(), R.layout.loadmorelayout, null);
        btn_loadmore = (Button) LoadMore.findViewById(R.id.btn_loadmore);
        btn_loadmore.setText(mTranslatesString.getCommon_loadmore());
        btn_loadmore.setOnClickListener(this);

        rg_collection.setOnCheckedChangeListener(new CollectionChangeListener());
        shop_adapter = new Collection_Shop_Adapter(getActivity(), new UnCollectAdapterListener() {
            @Override
            public void onClick(int position) {
                unCollect(position, SHOP_TYPE);
            }
        });
        sample_adapter = new Collection_Sample_Adapter(getActivity(), new UnCollectAdapterListener() {
            @Override
            public void onClick(int position) {
                unCollect(position, SAMPLE_TYPE);
            }
        });


        initText();

        isPrepared = true;
        lazyLoad();
    }

    //懒加载
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
    }

    private void initData() {
        if (rb_sample.isChecked()) {
            lv_collection.setAdapter(sample_adapter);
            getSampleList(pageNum, true);
        } else {
            lv_collection.setAdapter(shop_adapter);
            getShopList(pageNum, true);
        }
    }

    private void initText() {
        tv_collection_title.setText(mTranslatesString.getCommon_myfavorites());
        rb_sample.setText(mTranslatesString.getCommon_favoritesample());
        rb_shop.setText(mTranslatesString.getCommon_favoriteshop());
    }


    public void getSampleList(int pageNum, boolean isFirstLoad) {
        if (isFirstLoad) {
            samplelist.clear();
        }
        showLoading();
        rdm.GetFavoriteSample(pageNum);
        rdm.favoriteSampleListener = new FavoriteSampleListener() {
            @Override
            public void onSuccess(final List<FavoriteSampleDetail> favoritesampleList) {
                samplelist.addAll(favoritesampleList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (samplelist.isEmpty()) {
                            mEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mEmpty.setVisibility(View.GONE);
                        }
                        if (favoritesampleList.size() == PAGESIZE) {
                            lv_collection.addFooterView(LoadMore);
                        }
                        sample_adapter.setData(samplelist);
                        sample_adapter.notifyDataSetChanged();

                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                makeErrorToast(requestFailure);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                makeErrorToast(requestFailure);
            }
        };

    }

    public void getShopList(int pageNum, boolean isFirstLoad) {
        if (isFirstLoad) {
            shoplist.clear();
        }
        showLoading();
        rdm.GetFavoriteShop(pageNum);
        rdm.favoriteShopListener = new FavoriteShopListener() {
            @Override
            public void onSuccess(final List<FavoriteShopDetail> favoriteshopList) {

                shoplist.addAll(favoriteshopList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (shoplist.isEmpty()) {
                            mEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mEmpty.setVisibility(View.GONE);
                        }
                        if (favoriteshopList.size() == PAGESIZE) {
                            lv_collection.addFooterView(LoadMore);
                        }
                        shop_adapter.setData(shoplist);
                        shop_adapter.notifyDataSetChanged();
                    }
                });
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
                ToastHelper.makeErrorToast(requestFailure);
            }
        };
    }

    public interface UnCollectAdapterListener {
        void onClick(int position);
    }

    class CollectionChangeListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkId) {
            pageNum = 1;
            lv_collection.removeFooterView(LoadMore);
            if (checkId == R.id.rb_collcetion_goods) {
                lv_collection.setAdapter(sample_adapter);
                sample_or_shop = true;
                getSampleList(pageNum, true);
                rb_shop.setChecked(false);
            } else if (checkId == R.id.rb_collcetion_shop) {
                lv_collection.setAdapter(shop_adapter);
                sample_or_shop = false;
                getShopList(pageNum, true);
                rb_sample.setChecked(false);
            }
        }
    }

    /**
     * 取消收藏
     * @param position
     * @param type
     */
    public void unCollect(int position, final int type) {
        showLoading();
        String ids = "";
        if (type == SAMPLE_TYPE)
            ids = samplelist.get(position).getTargetId() + "";
        else if (type == SHOP_TYPE)
            ids = shoplist.get(position).getTargetId() + "";
        rdm.UnCollect(isLogin, userId, token, type, ids);

        final int id = Integer.parseInt(ids);
        rdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                if (type == SAMPLE_TYPE) {
                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Iterator<FavoriteSampleDetail> it = samplelist.iterator();
                            while (it.hasNext()) {
                                FavoriteSampleDetail x = it.next();
                                if (x.getTargetId() == id) {
                                    it.remove();
                                }
                            }
                            sample_adapter.setData(samplelist);
                            sample_adapter.notifyDataSetChanged();

                            if (samplelist.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
                        }
                    });

                } else if (type == SHOP_TYPE) {
                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Iterator<FavoriteShopDetail> it = shoplist.iterator();
                            while (it.hasNext()) {
                                FavoriteShopDetail x = it.next();
                                if (x.getTargetId() == id) {
                                    it.remove();
                                }
                            }
                            shop_adapter.setData(shoplist);
                            shop_adapter.notifyDataSetChanged();

                            if (shoplist.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
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
                ToastHelper.makeErrorToast(requestFailure);
            }
        };
    }

    public void LoadMore() {
        lv_collection.removeFooterView(LoadMore);
        pageNum++;
        if (sample_or_shop) {
            getSampleList(pageNum, false);
        } else {
            getShopList(pageNum, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_login:
                Intent i_to_login = new Intent(mContext, OtherUserLoginActivity.class);
                startActivity(i_to_login);
                break;
            case R.id.btn_loadmore:
                LoadMore();
                break;
            default:
                break;
        }
    }
}