package com.example.b2c.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.activity.SearchActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.FamousShopAdapter;
import com.example.b2c.config.Types;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.response.BannerAdDetail;
import com.example.b2c.net.response.BigBrandDetail;
import com.example.b2c.net.response.FamousShopDetail;
import com.example.b2c.net.response.HomePageDataListener;
import com.example.b2c.net.response.HomePageDataObject;
import com.example.b2c.net.response.HotCategoryDetail;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.BannerView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * 首页
 */
public class HomeIndexFragment extends TempleBaseFragment implements OnClickListener {
    private View mView;
    private TextView tv_search , tv_home_cross,tv_home_guess;
    private ArrayList<ImageView> list = new ArrayList<ImageView>();
    private ImageHandler handler = new ImageHandler();
    private RelativeLayout rl_search;
    private ImageView[] iv_category = new ImageView[8];
    private ImageView iv_bigbrand_top;
    private ImageView[] iv_bigbrand = new ImageView[3];
    private FamousShopAdapter shop_adapter;
    private ListView listView;
    private ScrollView sv_home;
    private String bigBrandImageHead;
    @Bind(R.id.banner)
    BannerView mBannerView;
    private List<String> bigBrandImageBody;
    private String[] hotCategoryImageList;
    private List<HotCategoryDetail> hotCategoryListparam = new ArrayList<>();
    private List<BigBrandDetail> bigBrandListparam = new ArrayList<>();
    private List<BannerAdDetail> mBannerAdDetailList = new ArrayList<>();
    private List<FamousShopDetail> famousShopList = new ArrayList<>();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public class ImageHandler extends Handler {

        protected static final int MSG_UPDATE_IMAGE = 1;

        protected static final int MSG_KEEP_SILENT = 2;

        protected static final int MSG_BREAK_SILENT = 3;

        protected static final int MSG_PAGE_CHANGED = 4;
        protected static final long MSG_DELAY = 3000;


        private int currentItem = 0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logs.d("LOG_TAG", "receive message" + msg.what);
            if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_KEEP_SILENT:
                    break;
                case MSG_BREAK_SILENT:
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBannerAdDetailList = SPHelper.getBean("bannerList", Types.listBannerAd);
        bigBrandListparam = SPHelper.getBean("bigBrand", Types.listBigBrand);
        hotCategoryListparam = SPHelper.getBean("hotCategory", Types.listHotCategory);
        famousShopList = SPHelper.getBean("famousShop", Types.listFamousShop);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_banner_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();

    }

    @Override
    protected void initView(View mView) {
        rl_search = (RelativeLayout) mView.findViewById(R.id.rl_search);
        rl_search.setOnClickListener(this);
        iv_category[0] = (ImageView) mView.findViewById(R.id.iv_category_pic1);
        iv_category[0].setOnClickListener(this);
        iv_category[1] = (ImageView) mView.findViewById(R.id.iv_category_pic2);
        iv_category[1].setOnClickListener(this);
        iv_category[2] = (ImageView) mView.findViewById(R.id.iv_category_pic3);
        iv_category[2].setOnClickListener(this);
        iv_category[3] = (ImageView) mView.findViewById(R.id.iv_category_pic4);
        iv_category[3].setOnClickListener(this);
        iv_category[4] = (ImageView) mView.findViewById(R.id.iv_category_pic5);
        iv_category[4].setOnClickListener(this);
        iv_category[5] = (ImageView) mView.findViewById(R.id.iv_category_pic6);
        iv_category[5].setOnClickListener(this);
        iv_category[6] = (ImageView) mView.findViewById(R.id.iv_category_pic7);
        iv_category[6].setOnClickListener(this);
        iv_category[7] = (ImageView) mView.findViewById(R.id.iv_category_pic8);
        iv_category[7].setOnClickListener(this);
        tv_home_cross = (TextView)mView.findViewById(R.id.tv_home_cross);


        iv_bigbrand[0] = (ImageView) mView.findViewById(R.id.iv_bigbrand_1);
        iv_bigbrand[0].setOnClickListener(this);
        iv_bigbrand[1] = (ImageView) mView.findViewById(R.id.iv_bigbrand_2);
        iv_bigbrand[1].setOnClickListener(this);
        iv_bigbrand[2] = (ImageView) mView.findViewById(R.id.iv_bigbrand_3);
        iv_bigbrand[2].setOnClickListener(this);
        iv_bigbrand_top = (ImageView) mView.findViewById(R.id.iv_bigbrand);
        iv_bigbrand_top.setOnClickListener(this);

        tv_home_guess = (TextView)mView.findViewById(R.id.tv_home_guess);
        tv_search = (TextView) mView.findViewById(R.id.tv_hot_sample);
        listView = (ListView) mView.findViewById(R.id.lv_famous_shop);
        sv_home = (ScrollView) mView.findViewById(R.id.sv_home);
        listView.setFocusable(false);
        tv_search.setText(mTranslatesString.getCommon_pleaseinputsampleshop());
        setData();
        initText();
    }

    private void initText() {
        tv_home_cross.setText(mTranslatesString.getCommon_bigbrandrecommend());
        tv_home_guess.setText(mTranslatesString.getCommon_farmousshop());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }

    public void setData() {

        if (mBannerAdDetailList != null) {
            initBanner(mBannerAdDetailList);
        }
        if (bigBrandListparam != null) {
            initBigBrand(bigBrandListparam);
        }
        if (famousShopList != null) {
            initFamousShop(famousShopList);
        }
        if (hotCategoryListparam != null) {
            initHotCategory(hotCategoryListparam);
        }
    }

    public void initData() {
        rdm.initHomePage(unLogin, userId, token);
        rdm.mHomeIndexListener = new HomePageDataListener() {
            @Override
            public void onSuccess(final HomePageDataObject homePageData, String errorInfo) {
                if (homePageData != null) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!homePageData.getBannerAdvertisingList().isEmpty()) {
                                    mBannerAdDetailList = homePageData.getBannerAdvertisingList();
                                    SPHelper.putBean("bannerList", mBannerAdDetailList);
                                    initBanner(mBannerAdDetailList);
                                }
                                if (!homePageData.getBigBrandList().isEmpty()) {
                                    bigBrandListparam = homePageData.getBigBrandList();
                                    SPHelper.putBean("bigBrand", bigBrandListparam);
                                    initBigBrand(bigBrandListparam);
                                }
                                if (!homePageData.getFamousShopList().isEmpty()) {
                                    famousShopList = homePageData.getFamousShopList();
                                    SPHelper.putBean("famousShop", famousShopList);
                                    initFamousShop(famousShopList);
                                }
                                if (!homePageData.getHotCategoryList().isEmpty()) {
                                    hotCategoryListparam = homePageData.getHotCategoryList();
                                    SPHelper.putBean("hotCategory", hotCategoryListparam);
                                    initHotCategory(hotCategoryListparam);
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onError(int errorNO,String errorInfo) {
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

    private void initFamousShop(List<FamousShopDetail> famousShopList) {
        shop_adapter = new FamousShopAdapter(mContext, famousShopList, options, animateFirstListener);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(shop_adapter);
                setListViewHeightBasedOnChildren(listView);
            }
        });
    }

    private void initHotCategory(final List<HotCategoryDetail> hotCategoryList) {
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hotCategoryImageList = new String[hotCategoryList.size()];
                    for (int i = 0; i < hotCategoryList.size(); i++) {
                        hotCategoryImageList[i] = HttpClientUtil.BASE_PIC_URL
                                + hotCategoryList.get(i).getPic();
                    }
                    for (int i = 0; i < hotCategoryList.size(); i++) {
                        iv_category[i].setTag(hotCategoryList.get(i).getCategoryId());
                        loader.displayImage(hotCategoryImageList[i],
                                iv_category[i],
                                options,
                                animateFirstListener);
                    }
                }
            });
    }

    private void initBigBrand(final List<BigBrandDetail> bigBrandList) {
        bigBrandImageBody = new ArrayList<String>();
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 4; i++) {
                        if (bigBrandList.get(i).getType() == 1) {
                            bigBrandImageHead = HttpClientUtil.BASE_PIC_URL + bigBrandList.get(i).getPicPath();
                        } else {
                            bigBrandImageBody.add(HttpClientUtil.BASE_PIC_URL + bigBrandList.get(i).getPicPath());
                        }
                    }
                    int j = 0;
                    for (int i = 0; i < 4; i++) {
                        if (bigBrandList.get(i).getType() == 1) {
                            iv_bigbrand_top.setTag(bigBrandList.get(i).getSampleId());
                            loader.displayImage(bigBrandImageHead,
                                    iv_bigbrand_top,
                                    options,
                                    animateFirstListener);
                        } else {
                            iv_bigbrand[j].setTag(bigBrandList.get(i).getSampleId());
                            loader.displayImage(bigBrandImageBody.get(j),
                                    iv_bigbrand[j],
                                    options,
                                    animateFirstListener);
                            j++;
                        }
                    }

                }
            });
    }

    public void initBanner(List<BannerAdDetail> ad_list) {
        mBannerView.setData(ad_list);
    }

    public void clickCategoryFunc(View v) {
        System.out.println(v.getTag().toString());
        Intent intent = new Intent(getActivity(), GoodsListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("categoryType", 0);
        if (v.getTag() != null && !"".equals(v.getTag())) {
            bundle.putString("categoryId", v.getTag().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return;
    }

    public void clickBigbrandFunc(View v) {
        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
        Bundle bundle = new Bundle();

        if (v.getTag() != null && !"".equals(v.getTag())) {
            int sampleId = (Integer) v.getTag();
            if (sampleId != 0) {
                try {
                    bundle.putInt("sampleId", sampleId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    return;
                }
            }
        }
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_search:
                Intent i_2search = new Intent(getActivity(), SearchActivity.class);
                startActivity(i_2search);
                break;
            case R.id.iv_category_pic1:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic2:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic3:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic4:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic5:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic6:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic7:
                clickCategoryFunc(v);
                break;
            case R.id.iv_category_pic8:
                clickCategoryFunc(v);
                break;
            case R.id.iv_bigbrand:
                clickBigbrandFunc(v);
                break;
            case R.id.iv_bigbrand_1:
                clickBigbrandFunc(v);
                break;
            case R.id.iv_bigbrand_2:
                clickBigbrandFunc(v);
                break;
            case R.id.iv_bigbrand_3:
                clickBigbrandFunc(v);
                break;
            default:
                break;
        }
    }


    class ShowImageListener implements ImageLoadingListener {
        private ImageView iv;

        public ShowImageListener(ImageView iv) {
            super();
            this.iv = iv;
        }

        @Override
        public void onLoadingCancelled(String arg0, View arg1) {
        }

        @Override
        public void onLoadingComplete(String arg0, View arg1,
                                      final Bitmap bitmap) {
            // TODO Auto-generated method stub
            if (iv != null && getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        iv.setImageBitmap(bitmap);
                    }
                });

            }
        }

        @Override
        public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

        }

        @Override
        public void onLoadingStarted(String arg0, View arg1) {

        }

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        sv_home.fullScroll(ScrollView.FOCUS_UP);
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

}
