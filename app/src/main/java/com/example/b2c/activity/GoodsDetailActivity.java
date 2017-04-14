package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.WebViewActivity;
import com.example.b2c.adapter.GoodDetailBannerAdapter;
import com.example.b2c.adapter.PropertyItemAdapter;
import com.example.b2c.adapter.ShopDepotAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.DialogUtils;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.SampleDetailListener;
import com.example.b2c.net.response.SampleDescription;
import com.example.b2c.net.response.SampleDetailResult;
import com.example.b2c.net.response.SampleImageDetail;
import com.example.b2c.net.response.ShopDepotResult;
import com.example.b2c.net.response.sampleSKUDetail;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.MyGridView;
import com.example.b2c.widget.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 商品详情页面
 */
public class GoodsDetailActivity extends TempBaseActivity implements OnClickListener {

    private static final int  RQ_CHECK = 110;

    /**
     * 商品详情返回值
     */
    private SampleDetailResult detailResult;

    private List<ShopDepotResult> shopWarehouseList;

    private ViewPager viewPager;
    ArrayList<String> ImageUrls;
    GoodDetailBannerAdapter adapter;
    int currentItem = 0;
    ScheduledExecutorService executorService;
    Handler handler;
    private PropertyItemAdapter pro_adapter;


    /**
     * 商品详情页
     */
    private ImageView iv_collect_star;
    private TextView tv_sample_name, tv_sample_price,
            tv_description_label, tv_description_text,
            tv_attitude_label, tv_attitude_text,
            tv_speed_label, tv_speed_text,
            tv_comment, tv_detail,
            tv_shop_name;
    private LinearLayout ll_shop_info, ll_comment, ll_detail;
    private Button btn_to_cart, btn_buy_now;
    private View pop_layout;
    /**
     * 立即购买，加入购物车弹出层
     */
    private TextView tv_pop_title,
            tv_pop_sample_name, tv_pop_sample_price, tv_pop_sample_amount, tv_depot_label,
            tv_pop_buyamount;
    private ImageView iv_pop_close, iv_pop_sample_pic, iv_xuanzhuan;
    private ListView lv_pop_property;

    private MyGridView mgv_depot;
    private ShopDepotAdapter shopDepotAdapter;

    private EditText et_num;
    private Button btn_num_add, btn_num_reduce, btn_pop_check;

    private Intent intent;
    private Bundle bundle;


    private int sampleId, isFavorite = 0, sampleSKUId = -1, num = 1;
    private String symbol = Configs.CURRENCY_UNIT, httpDetail = "";
    private boolean buy_or_cart = false;// true代表点击立即购买，false代表加入购物车
    private WebView wv_goods_detials;

    private boolean isZhankai;


    //
    private String proIds = null;
    private String prodIds = null;


    @Override
    public int getRootId() {
        return R.layout.goods_detail_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        bundle = intent.getExtras();
        sampleId = bundle.getInt("sampleId");
        initView();
        initData();

        addImage(R.drawable.ic_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://115.29.167.40:81/chat/chatWithSeller/1?userId=156&token=d0ca2afc21076409a20134216cb5c721&userType=0&loginName=maijia&plat=android
                ///chat/chatWithSeller/{sellerId}?userId=&token=&userType&appName=&loginName=
                if (UserHelper.isBuyerLogin()) {
                    //买家登录
                    Intent intent = new Intent(GoodsDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("url", ConstantS.BASE_CHAT+"chat/chatWithSeller/" + detailResult.getSellerId() + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&appName=" + SPHelper.getString(Configs.APPNAME) + "&loginName=" + UserHelper.getBuyerName()+ "&lang=" + SPHelper.getString(Configs.LANGUAGE));
                    startActivity(intent);
                } else {
                    //买家未登录
                    ToastHelper.makeToast(mTranslatesString.getCommon_tologin());
                }
                return;
            }
        });

    }

    @Override
    protected void onStart() {
        if (executorService != null && executorService.isShutdown()) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);
        }
        super.onStart();
    }

    public void initView() {
        wv_goods_detials = (WebView) findViewById(R.id.wv_goods_detials);
        wv_goods_detials.getSettings().setUseWideViewPort(true);
        wv_goods_detials.getSettings().setLoadWithOverviewMode(true);

        viewPager = (ViewPager) findViewById(R.id.banner_viewpager);
        ImageUrls = new ArrayList<>();
        shopWarehouseList = new ArrayList<>();

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);

        tv_sample_name = (TextView) findViewById(R.id.tv_sample_name);
        tv_sample_price = (TextView) findViewById(R.id.tv_sample_price);
        iv_collect_star = (ImageView) findViewById(R.id.iv_collect_star);
        iv_xuanzhuan = (ImageView) findViewById(R.id.iv_xuanzhuan);

        ll_shop_info = (LinearLayout) findViewById(R.id.ll_shop_info);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_description_label = (TextView) findViewById(R.id.tv_description_label);
        tv_description_text = (TextView) findViewById(R.id.tv_description_text);
        tv_attitude_label = (TextView) findViewById(R.id.tv_attitude_label);
        tv_attitude_text = (TextView) findViewById(R.id.tv_attitude_text);
        tv_speed_label = (TextView) findViewById(R.id.tv_speed_label);
        tv_speed_text = (TextView) findViewById(R.id.tv_speed_text);

        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        tv_comment = (TextView) findViewById(R.id.tv_comment);

        ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
        tv_detail = (TextView) findViewById(R.id.tv_detail);

        btn_to_cart = (Button) findViewById(R.id.btn_to_cart);
        btn_buy_now = (Button) findViewById(R.id.btn_buy_now);


        pop_layout = findViewById(R.id.pop_layout);

        tv_pop_title = (TextView) pop_layout.findViewById(R.id.tv_pop_title);

        iv_pop_close = (ImageView) pop_layout.findViewById(R.id.iv_pop_close);
        iv_pop_sample_pic = (ImageView) pop_layout.findViewById(R.id.iv_pop_sample_pic);

        tv_pop_sample_name = (TextView) pop_layout.findViewById(R.id.tv_pop_sample_name);
        tv_pop_sample_price = (TextView) pop_layout.findViewById(R.id.tv_pop_sample_price);
        tv_pop_sample_amount = (TextView) pop_layout.findViewById(R.id.tv_pop_sample_amount);


        lv_pop_property = (ListView) pop_layout.findViewById(R.id.lv_pop_property);
        mgv_depot = (MyGridView) pop_layout.findViewById(R.id.mgv_depot);

        tv_pop_buyamount = (TextView) pop_layout.findViewById(R.id.tv_pop_buyamount);
        tv_depot_label = (TextView) pop_layout.findViewById(R.id.tv_depot_label);
        et_num = (EditText) pop_layout.findViewById(R.id.et_num);
        btn_num_add = (Button) pop_layout.findViewById(R.id.btn_num_add);
        btn_num_reduce = (Button) pop_layout.findViewById(R.id.btn_num_reduce);

        btn_pop_check = (Button) pop_layout.findViewById(R.id.btn_pop_check);

        iv_collect_star.setOnClickListener(this);
        ll_shop_info.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_detail.setOnClickListener(this);
        btn_to_cart.setOnClickListener(this);
        btn_buy_now.setOnClickListener(this);

        iv_pop_close.setOnClickListener(this);
        btn_num_add.setOnClickListener(this);
        btn_num_reduce.setOnClickListener(this);
        btn_pop_check.setOnClickListener(this);


        initText();
    }


    public void initText() {
        setTitle(mTranslatesString.getCommon_sampledetail());
        btn_to_cart.setText(mTranslatesString.getCommon_addtocart());
        btn_buy_now.setText(mTranslatesString.getCommon_buynow());

        tv_description_label.setText(mTranslatesString.getCommon_consitancerate());
        tv_attitude_label.setText(mTranslatesString.getCommon_serviceatitude());
        tv_speed_label.setText(mTranslatesString.getCommon_deliveryspeed());
        tv_comment.setText(mTranslatesString.getCommon_samplecomment());
        tv_detail.setText(mTranslatesString.getCommon_imagetextdetail());

        tv_depot_label.setText(mTranslatesString.getCommon_depot());
        tv_pop_buyamount.setText(mTranslatesString.getCommon_buyamount());
        btn_pop_check.setText(mTranslatesString.getCommon_makesure());
    }

    public void initBanner(List<SampleImageDetail> list) {
        if (list != null && list.size() > 0) {
            for (SampleImageDetail detail : list) {
                String image=ConstantS.BASE_PIC_URL + detail.getImagePath();
                ImageUrls.add(image);
            }
        }
        adapter = new GoodDetailBannerAdapter(GoodsDetailActivity.this, ImageUrls);
        viewPager.setAdapter(adapter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Logs.d("GoodsDetailActivity", "receive msg");
                switch (msg.what) {
                    case 1077:
                        Bundle b = msg.getData();
                        proIds = b.getString("proIds");
                        prodIds = b.getString("prodIds");
                        initPop();
                        break;
                    case 1066:
                        viewPager.setCurrentItem(currentItem);
                        break;
                }

            }
        };
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currentItem = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initPop() {
        Logs.d("gooddtail", proIds);
        Logs.d("gooddtail", prodIds);
        List<sampleSKUDetail> ls = detailResult.getSampleSKUList();
        boolean flag = false;
        for (sampleSKUDetail cell : ls) {
            if (cell.getPropertyIds().equals(proIds) && cell.getProDetailIds().equals(prodIds)) {
                tv_pop_sample_price.setText(Configs.CURRENCY_UNIT + cell.getPrice());
                tv_pop_sample_amount.setText(mTranslatesString.getCommon_stock() + cell.getAmount());
                flag = true;
            }
        }
        if (!flag) {
            tv_pop_sample_price.setText(Configs.CURRENCY_UNIT + "0");
            tv_pop_sample_amount.setText(mTranslatesString.getCommon_stock() + "0");
        }
    }

    public void initData() {
        showLoading();
        rdm.GetSampleDetail(unLogin, sampleId + "");
        rdm.sampledetailListener = new SampleDetailListener() {
            @Override
            public void onSuccess(final SampleDetailResult result) {
                detailResult = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isFavorite = detailResult.getIsFavorite();
//                        httpDetail = result.getHtmlDetail();
                        initTextHttpDetail(result);
                        initBanner(result.getSampleImageList());
                        initSurface();
                        loader.displayImage(ConstantS.BASE_PIC_URL + getDefaultPic(detailResult.getSampleImageList()), iv_pop_sample_pic, options);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                showAlertTextDialog(request_failure);
            }
        };
    }

    private void initTextHttpDetail(SampleDetailResult result) {
        StringBuilder sb = new StringBuilder();
        //拼接一段HTML代码
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>欢迎你</title>");
        sb.append("</head>");
        sb.append("<body>");
        SampleDescription sd = result.getSampleDescription();
        if (sd != null && sd.getSampleDetailMessage() != null) {
            sb.append("<div style=\"text-align:center; width:100% ;font-size:30;\">" + sd.getSampleDetailMessage() + "</div>");
        }
//        sb.append("<img src = \"https://gss0.bdstatic.com/5eR1dDebRNRTm2_p8IuM_a/res/img/richanglogo168_24.png\" /><br/>");
//        sb.append("<img src = \"http://115.29.172.200/upload/sample/info/20170227/1488188544519.jpeg\" /><br/>");
//        sb.append("<img src = \"http://115.29.172.200/upload/sample/20170228/1488248173110.jpg\" /><br/>");
        if (sd != null && sd.getSampleDetailImageList() != null && sd.getSampleDetailImageList().size() > 0) {
            for (String s : sd.getSampleDetailImageList()) {
                sb.append("<div style=\"text-align:center; width:100% \"> <img  width=\"80%\" src=\"" + ConstantS.BASE_PIC_URL + s + "\"/></div><br/>");
            }
        }
        sb.append("</body>");
        sb.append("</html>");
//        if(sd != null && sd.getSampleDetailImageList()!=null && sd.getSampleDetailImageList().size()>0){
//            for (String s : sd.getSampleDetailImageList()) {
//                httpDetail += "<img src="+ConstantS.BASE_PIC_URL+s+"/>"+"<br/>";
//            }
//        }
        httpDetail += sb.toString();
    }

    private String getDefaultPic(List<SampleImageDetail> sampleImageList) {
        if (sampleImageList != null) {
            for (SampleImageDetail detail : sampleImageList) {
                if (detail.getIsDefault() == 1) {
                    return Utils.cutNull(detail.getImagePath());
                }
            }
        }
        return "";
    }

    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (viewPager) {
                Logs.d("currentItem: " + currentItem);
                currentItem = (currentItem + 1) % ImageUrls.size();
                handler.obtainMessage().what = 1066;
                handler.obtainMessage().sendToTarget(); //
            }
        }

    }

    public void initSurface() {
        tv_sample_name.setText(detailResult.getSampleName());
        tv_sample_price.setText(symbol + detailResult.getPrice());
        tv_shop_name.setText(detailResult.getShopName());
        tv_description_text.setText("" + detailResult.getShopConsistentDescription());
        tv_attitude_text.setText("" + detailResult.getShopServiceAttitude());
        tv_speed_text.setText("" + detailResult.getShopDeliverySpeed());

        tv_pop_sample_name.setText(detailResult.getSampleName());
        tv_pop_sample_price.setText(symbol + detailResult.getPrice());
        tv_pop_sample_amount.setText(mTranslatesString.getCommon_sumstock() + detailResult.getSampleSKU());
        if (isFavorite == 1) {
            iv_collect_star.setImageResource(R.drawable.collected_star);
        } else {
            iv_collect_star.setImageResource(R.drawable.collect_star);
        }
        if (detailResult.getSampleProList() != null && detailResult.getSampleProList().size() > 0) {
            pro_adapter = new PropertyItemAdapter(this, detailResult.getSampleProList(), handler);
            lv_pop_property.setAdapter(pro_adapter);
        }

        if (detailResult.getShopWarehouseList() != null && detailResult.getShopWarehouseList().size() > 0) {
            shopWarehouseList.addAll(detailResult.getShopWarehouseList());
            //默认选中一个
            if (shopWarehouseList.get(0) != null) {
                shopWarehouseList.get(0).setChecked(true);
            }
            shopDepotAdapter = new ShopDepotAdapter(this, shopWarehouseList);
            mgv_depot.setAdapter(shopDepotAdapter);
            mgv_depot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (ShopDepotResult sw : shopWarehouseList) {
                        sw.setChecked(false);
                    }
                    shopWarehouseList.get(position).setChecked(true);
                    shopDepotAdapter.setLs(shopWarehouseList);
                    shopDepotAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    /**
     * 查找属性库存id
     *
     * @return
     */
    public int MatchSKU() {
        String SKUId = "";
        int id = -1;
        for (int i = 0; i < detailResult.getSampleProList().size(); i++) {
            for (int j = 0; j < detailResult.getSampleProList().get(i)
                    .getProDetailList().size(); j++) {
                if (detailResult.getSampleProList().get(i).getProDetailList()
                        .get(j).isChecked())
                    SKUId += detailResult.getSampleProList().get(i)
                            .getProDetailList().get(j).getProDetailId();
            }
            SKUId += ",";
        }
        SKUId = SKUId.substring(0, SKUId.length() - 1);
        for (int i = 0; i < detailResult.getSampleSKUList().size(); i++) {
            if (detailResult.getSampleSKUList().get(i).getProDetailIds().equals(SKUId)) {
                id = detailResult.getSampleSKUList().get(i).getId();
                break;
            }
        }
        return id;
    }

    /**
     * 加入购物车
     */
    public void postShoppingCart() {
        Map map = new HashMap();
        num = Integer.valueOf(et_num.getText().toString());
        int shopWarehouseId = MatchDepotId();
        if (detailResult.getSampleProList().size() > 0) {// 如果产品有属性列表，则进行属性id上传
            sampleSKUId = MatchSKU();
            if (sampleSKUId == -1) {
                ToastHelper.makeToast(mTranslatesString.getCommon_pleaseselecttype());
                return;
            } else {
                showProgressDialog(Waiting);
                map.put("sampleId", sampleId);
                map.put("sampleSKUId", sampleSKUId);
                map.put("num", num);
                map.put("shopWarehouseId", shopWarehouseId);
                rdm.PostShoppingCart(map);
            }
        } else {
            showProgressDialog(Waiting);
            map.put("sampleId", sampleId);
            map.put("num", num);
            map.put("shopWarehouseId", shopWarehouseId);
            rdm.PostShoppingCart(map);
        }

        rdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                dismissPoplayout();
                ToastHelper.makeToast(errorInfo);
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);

            }

        };
    }

    public void dismissPoplayout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pop_layout.setVisibility(View.GONE);
                unLockScreen();
            }
        });
    }

    /**
     * 增加产品数量
     */
    public void add_num() {
        int Num = Integer.valueOf(et_num.getText().toString());
        Num++;
        et_num.setText("" + Num);
        num = Num;
    }

    /**
     * 减少产品数量
     */
    public void reduce_num() {
        int Num = Integer.valueOf(et_num.getText().toString());
        if (Num > 1) {
            Num--;
            et_num.setText("" + Num);
            num = Num;
        }
    }

    public void collect() {
        if (isFavorite == 0) {
            rdm.Collect(isLogin, userId, token, sampleId, 1);
        } else {
            showAlertTextDialog(mTranslatesString.getCommon_favoriteyet());
        }
        rdm.responseListener = new ResponseListener() {

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(String errorInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isFavorite == 0) {
                            iv_collect_star.setImageResource(R.drawable.collected_star);
                            isFavorite = 1;
                        }
                        showAlertTextDialog(mTranslatesString.getNotice_favoritesuccess());
                    }
                });
            }

            @Override
            public void onError(int errorNO, final String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void onLose() {
                showAlertDialog(request_failure);
            }
        };
    }

    /**
     * 匹配出仓库的id
     *
     * @return
     */
    private int MatchDepotId() {
        for (ShopDepotResult sdr : detailResult.getShopWarehouseList()) {
            if (sdr.isChecked()) {
                return sdr.getId();
            }
        }
        return 0;
    }

    /**
     * 立即购买
     */
    public void Jump2Settlement() {
        Intent i2settlement = new Intent(this, CheckOrderActivity.class);
        Bundle bundle = new Bundle();
        if (detailResult.getSampleProList().size() > 0) {// 如果产品有属性列表，则进行属性id上传
            sampleSKUId = MatchSKU();
            if (sampleSKUId == -1) {
                ToastHelper.makeToast(mTranslatesString.getCommon_pleaseselecttype());
                return;
            }
        }
        int shopWarehouseId = MatchDepotId();
        bundle.putInt("sampleId", sampleId);
        bundle.putInt("sampleSKUId", sampleSKUId);
        bundle.putInt("num", num);
        bundle.putInt("shopWarehouseId", shopWarehouseId);
        i2settlement.putExtras(bundle);
        startActivityForResult(i2settlement,RQ_CHECK);
    }

    /**
     * 跳入登录页
     */
    public void Jump2Login() {
        Intent i = new Intent(this, BuyerLoginActivity.class);
        startActivity(i);
//        if (MainActivity.instance != null) {
//            MainActivity.instance.finish();
//        }
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shop_info:
                Intent i_2_shop = new Intent(this, ShopHomesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("shopId", detailResult.getShopId());
                i_2_shop.putExtras(bundle);
                startActivity(i_2_shop);
                break;
            case R.id.btn_buy_now:
                buy_or_cart = true;
                pop_layout.setVisibility(View.VISIBLE);
                tv_pop_title.setText(mTranslatesString.getCommon_buynow());
                lockScreen();
                break;
            case R.id.btn_to_cart:
                buy_or_cart = false;
                pop_layout.setVisibility(View.VISIBLE);
                tv_pop_title.setText(mTranslatesString.getCommon_addtocart());
                lockScreen();
                break;
            case R.id.btn_pop_check:
//                ToastHelper.makeToast("isLogin");
                if (isLogin) {
                    if (buy_or_cart) {
                        Jump2Settlement();
                    } else {
                        postShoppingCart();
                    }
                } else {
                    Jump2Login();
                }
                break;
            case R.id.iv_pop_close:
                pop_layout.setVisibility(View.GONE);
                unLockScreen();
                break;
            case R.id.btn_num_add:
                add_num();
                break;

            case R.id.btn_num_reduce:
                reduce_num();
                break;
            case R.id.iv_collect_star:
                if (isLogin) {
                    collect();
                } else {
                    showAlertDialog(mTranslatesString.getCommon_tologin());
                }
                break;
            case R.id.ll_comment:
                Intent i_2_comments = new Intent(this, GoodsCommentsActivity.class);
                Bundle bundle_2_comment = new Bundle();
                bundle_2_comment.putInt("sampleId", sampleId);
                i_2_comments.putExtras(bundle_2_comment);
                startActivity(i_2_comments);
                break;
            case R.id.ll_detail:
                if (!Utils.cutNull(httpDetail).equals("")) {
                    wv_goods_detials.loadDataWithBaseURL(null, httpDetail, "text/html", "utf-8", null);
                    if (!isZhankai) {
                        wv_goods_detials.setVisibility(View.VISIBLE);
                    } else {
                        wv_goods_detials.setVisibility(View.GONE);
                    }
                    isZhankai = !isZhankai;
                } else {
                    ToastHelper.makeToast(mTranslatesString.getCommon_nownogooddetail());
                }
                break;
            default:
                break;
        }
    }

    private void lockScreen() {
        ll_shop_info.setEnabled(false);
        ll_comment.setEnabled(false);
        ll_detail.setEnabled(false);
        ll_shop_info.setClickable(false);
        ll_comment.setClickable(false);
        ll_detail.setClickable(false);
    }

    private void unLockScreen() {
        ll_shop_info.setEnabled(true);
        ll_comment.setEnabled(true);
        ll_detail.setEnabled(true);
        ll_shop_info.setClickable(true);
        ll_comment.setClickable(true);
        ll_detail.setClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //去下单并且resultCode=1099 代表没有收货地址
        if(requestCode == RQ_CHECK && resultCode == 1099){
            String errorInfo = data.getStringExtra("error");
            final DialogUtils dialogUtils = new DialogUtils(GoodsDetailActivity.this);
            dialogUtils.myshowDialog(errorInfo,mTranslatesString.getCommon_makesure(),mTranslatesString.getNotice_cancel());

            dialogUtils.setMyOnClickListener(new DialogUtils.MyOnClickListener() {
                @Override
                public void onZuoClickListener() {
                    //管理收货地址页面
                    Intent i_manage_cas_addr = new Intent(GoodsDetailActivity.this, MyAddrActivity.class);
                    Bundle  bundle2 =new Bundle();
                    bundle2.putInt("addressType",10);
                    i_manage_cas_addr.putExtras(bundle2);
                    startActivity(i_manage_cas_addr);
                    if(dialogUtils.aa!=null){
                        dialogUtils.aa.dismiss();
                    }
                }
                @Override
                public void onYouClickListener() {
                    if(dialogUtils.aa!=null){
                        dialogUtils.aa.dismiss();
                    }
                }
            });

        }
    }
}
