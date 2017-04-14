package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.UpdateImageActivity;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.event.model.DescriptionListEvent;
import com.example.b2c.event.model.ImageListEvent;
import com.example.b2c.helper.EventHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.ImageListResult;
import com.example.b2c.net.response.common.ImageItem;
import com.example.b2c.net.response.common.TranslationResult;
import com.example.b2c.net.response.seller.CategoryDetailModule;
import com.example.b2c.net.response.seller.CategoryModule;
import com.example.b2c.net.response.seller.DescriptionModule;
import com.example.b2c.net.response.seller.PropertyDetails;
import com.example.b2c.net.response.seller.SampleProInfosModule;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.CommonListPopupWindow;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SimpleTextWatcher;
import com.example.b2c.widget.util.Strings;
import com.example.b2c.widget.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

import static com.example.b2c.R.id.iv_select_phone;
import static com.example.b2c.R.id.rlyt_pre;

/**
 * 用途：
 * 作者：Created by john on 2017/2/7.
 * 邮箱：liulei2@aixuedai.com
 */


public class ReleaseProductActivity extends TempBaseActivity {
    @Bind(R.id.iv_title_select_phone)
    ImageView mIvTitleSelectPhone;
    @Bind(rlyt_pre)
    RelativeLayout mRlytPre;
    @Bind(iv_select_phone)
    ImageView mIvSelectPhone;
    @Bind(R.id.rlyt_select)
    RelativeLayout mRlytSelect;
    @Bind(R.id.from_advise)
    EditText mFromAdvise;
    @Bind(R.id.tv_title_category)
    TextView mTvTitleCategory;
    @Bind(R.id.tv_input_category)
    TextView mTvInputCategory;
    @Bind(R.id.receive_address_lyt)
    LinearLayout mReceiveAddressLyt;
    @Bind(R.id.dynamic_title_address)
    TextView mDynamicTitleAddress;
    @Bind(R.id.dynamic_input_address)
    EditTextWithDelete mDynamicInputAddress;
    @Bind(R.id.dynamic_title_code)
    TextView mDynamicTitleCode;
    @Bind(R.id.dynamic_input_code)
    EditTextWithDelete mDynamicInputCode;
    @Bind(R.id.dynamic_title_brand)
    TextView mDynamicTitleBrand;
    @Bind(R.id.dynamic_input_brand)
    EditTextWithDelete mDynamicInputBrand;
    @Bind(R.id.dynamic_title_unit)
    TextView mDynamicTitleUnit;
    @Bind(R.id.dynamic_input_unit)
    EditTextWithDelete mDynamicInputUnit;
    @Bind(R.id.dynamic_title_price)
    TextView mDynamicTitlePrice;
    @Bind(R.id.dynamic_input_price)
    EditTextWithDelete mDynamicInputPrice;
    @Bind(R.id.dynamic_title_weight)
    TextView mDynamicTitleWeight;
    @Bind(R.id.dynamic_input_weight)
    EditTextWithDelete mDynamicInputWeight;
    @Bind(R.id.tv_title_description)
    TextView mTvTitleDescription;
    @Bind(R.id.tv_input_description)
    TextView mTvInputDescription;
    @Bind(R.id.tv_title_warehouse)
    TextView mTvTitleWarehouse;
    @Bind(R.id.tv_input_warehouse)
    TextView mTvInputWarehouse;
    @Bind(R.id.tv_title_distribution)
    TextView mTvTitleDistribution;
    @Bind(R.id.tv_input_distribution)
    TextView mTvInputDistribution;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.tv_limit)
    TextView mTvLimit;
    @Bind(R.id.iv_again_select)
    ImageView mIvAgain;
    @Bind(R.id.tv_title_specification)
    TextView mTvTitleSpecification;
    @Bind(R.id.tv_input_specification)
    TextView mTvInputSpecification;
    @Bind(R.id.dynamic_title_account)
    TextView dynamicTitleAccount;
    @Bind(R.id.dynamic_input_account)
    EditTextWithDelete dynamicInputAccount;
    @Bind(R.id.lyt_account)
    LinearLayout mLytAccount;
    private PickImageDialog mPickImageDialog;
    private List<HashMap<String, String>> value = new ArrayList<>();
    private HashMap<String, String> selected = new HashMap<>();
    private List<TranslationResult> deliveryType;

    private String descriptionValue;
    private String htmlDetail;
    private String shopWarehouseIds;
    private String deliveryTypes;
    private int categoryId = -1;
    public List<SampleProInfosModule> moduleList = new ArrayList<>();
    public List<SampleProInfosModule> moduleList1 = new ArrayList<>();

    public DescriptionModule mDescriptionModule;
    private ArrayList<String> dizhi;
    private ArrayList<Integer> ids;
    //    private ArrayList<Integer> positionList;
    private List<List<PropertyDetails>> mPropertyList = new ArrayList<>();
    private List<CategoryDetailModule> listResult = new ArrayList<>();

    @Override
    public int getRootId() {
        return R.layout.activity_release_product;
    }

    private ImageListResult imageListResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventHelper.register(this);

        initView();
        initData();
    }

    public void initView() {
        setTitle(mTranslatesString.getSeller_releasetitle());
        mFromAdvise.setHint(mTranslatesString.getSeller_goodsname());
        mTvTitleCategory.setText(mTranslatesString.getSeller_productCategory());
        mTvTitleSpecification.setText(mTranslatesString.getCommon_specifications());
        mDynamicTitleAddress.setText(mTranslatesString.getSeller_goodsorigin());
        mDynamicTitleCode.setText(mTranslatesString.getSeller_commodityCode());
        mDynamicTitleBrand.setText(mTranslatesString.getSeller_brand());
        mDynamicTitleUnit.setText(mTranslatesString.getSeller_unit());
        mDynamicTitlePrice.setText(mTranslatesString.getSeller_currentSalesPrices());
        mDynamicTitleWeight.setText(mTranslatesString.getSeller_weight());
        mTvTitleDescription.setText(mTranslatesString.getGoods_message());
        mTvTitleWarehouse.setText(mTranslatesString.getCommon_warehouseAdress());
        mTvTitleDistribution.setText(mTranslatesString.getCommon_deliverytype());
        dynamicTitleAccount.setText(mTranslatesString.getCommon_stock());
        mFromAdvise.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mTvLimit.setText(s.length() + "/60");
            }
        });
        mBtnSubmit.setText(mTranslatesString.getCommon_makesure());
    }

    public void initData() {
        deliveryType = mTranslatesStringList.getDeliveryTypeNew();
        for (TranslationResult result : deliveryType) {
            HashMap map = new HashMap();
            map.put(result.getValue(), result.getText());
            value.add(map);
        }
        if (imageListResult == null) {
            imageListResult = new ImageListResult();
        }
    }

    @OnClick({R.id.receive_specification_lyt, rlyt_pre, R.id.receive_address_lyt, R.id.iv_select_phone, R.id.iv_again_select, R.id.lyt_description, R.id.lyt_warehouse, R.id.lyt_distribution, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case rlyt_pre:
            case R.id.iv_again_select:
                mPickImageDialog = new PickImageDialog(this, 1, "multi") {
                    @Override
                    protected void onImageUpLoad(int id, String url, String url1) {
                        ImageItem module = new ImageItem();
                        module.setUrl(url);
                        List<ImageItem> mList = new ArrayList<>();
                        mList.add(module);
                        if (imageListResult.getSampleDetailImages() != null) {
                            mList.addAll(imageListResult.getSampleDetailImages());
                        }
                        ChooseImageActivity.getInstance(ReleaseProductActivity.this, mList, new ChooseImageActivity.ImageListCallBack() {
                            @Override
                            public void onImageList(List<ImageItem> mList) {
                            }
                        });
                    }
                };
                mPickImageDialog.show();
                break;
            case R.id.receive_specification_lyt://规格
                if (categoryId == -1) {
                    ToastHelper.makeErrorToast(mTranslatesString.getCommon_toastrelease());
                } else {
                    GoodsSpecificationActivityNew.getInstance(this, categoryId, mPropertyList, listResult, moduleList, new GoodsSpecificationActivityNew.ValueSubmitListener() {
                        @Override
                        public void onItemClick(List<SampleProInfosModule> module, List<List<PropertyDetails>> mData, List<CategoryDetailModule> result) {
                            if (module.size() > 0) {
                                mTvInputSpecification.setText(mTranslatesString.getCommon_edited());
                                mLytAccount.setVisibility(View.GONE);
                            }
                            moduleList = module;
                            moduleList1 = module;
                            mPropertyList = mData;
                            listResult = result;
                        }
                    });
                }
                break;
            case R.id.iv_select_phone://商品图片
                Intent intent = new Intent(this, UpdateImageActivity.class);
                intent.putExtra("mListData", (Serializable) imageListResult.getSampleDetailImages());
                intent.putExtra("imageMark", 0);
                startActivityForResult(intent, 1001);
                break;
            case R.id.lyt_description://图文详情

                DescriptionActivity.getInstance(ReleaseProductActivity.this, mDescriptionModule, new DescriptionActivity.ImageListCallBack() {
                    @Override
                    public void onImageList(List<ImageItem> mList) {

                    }
                });
                break;
            case R.id.receive_address_lyt://类目
                CategoryActivity.getInstance(this, new CategoryActivity.ItemClick() {
                    @Override
                    public void onItemClick(CategoryModule module) {
                        mTvInputCategory.setText(Utils.cutNull(module.getName()));
                        mTvInputSpecification.setText("");
                        categoryId = module.getId();
                        moduleList.clear();
                        mPropertyList.clear();
                        listResult.clear();
                    }
                });
                break;

            case R.id.lyt_warehouse://仓库

                Intent intent1 = new Intent(getApplication(), MyDdepotListActivity.class);
                if (ids != null) {
                    intent1.putIntegerArrayListExtra("ids", ids);
                }
                startActivityForResult(intent1, 108);
                break;
            case R.id.lyt_distribution://配送方式

                CommonListPopupWindow pop = new CommonListPopupWindow(this, value, selected, new CommonListPopupWindow.CommonCallBack() {
                    @Override
                    public void onClick(int selectItem, String value) {
                        mTvInputDistribution.setText(Utils.cutNull(value));
                        deliveryTypes = deliveryType.get(selectItem).getValue() + "";
                    }
                });
                pop.show(view);
                break;
            case R.id.btn_submit:
                getData();
                break;
        }
    }

    public void getData() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("name", mFromAdvise.getText().toString());
        parmas.put("categoryId", categoryId + "");
        parmas.put("address", mDynamicInputAddress.getText().toString());
        parmas.put("code", mDynamicInputCode.getText().toString());
        parmas.put("brand", mDynamicInputBrand.getText().toString());
        parmas.put("unit", mDynamicInputUnit.getText().toString());
        parmas.put("price", mDynamicInputPrice.getText().toString());
        parmas.put("weight", mDynamicInputWeight.getText().toString());
        parmas.put("deliveryType", Utils.cutNull(deliveryTypes));
        if (!Strings.EMPTY.equals(dynamicInputAccount.getText().toString())) {
            parmas.put("inventory", Utils.cutNull(dynamicInputAccount.getText().toString()));
        }
        //TODO
        if (ids != null && ids.size() != 0) {
            String strQueueNumList = ListToString(ids);
            parmas.put("shopWarehouseIds", strQueueNumList);
        } else {
            parmas.put("shopWarehouseIds", "");
        }

        parmas.put("sampleDetail", JSON.toJSONString(mDescriptionModule));

        if (imageListResult.getSampleDetailImages() != null) {
            List<String> productUrl = new ArrayList<>();
            for (ImageItem item : imageListResult.getSampleDetailImages()) {
                String url = item.getNewUrl();
                productUrl.add(url);
            }
            parmas.put("sampleImages", JSON.toJSONString(productUrl));
        }
        if (moduleList1.size() > 0) {
            handleModuleList();
            parmas.put("sampleProInfos", JSON.toJSONString(moduleList1));

        }
        showLoading();
        sellerRdm.releaseProduct(parmas);
        sellerRdm.mNodataListener = new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                ToastHelper.makeToast(success);
                finish();
            }

            @Override
            public void onError(int errorNo, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(mTranslatesString.getCommon_neterror());
            }
        };
    }

    private void handleModuleList() {
        if (moduleList != null && moduleList.size() > 0) {
            for (SampleProInfosModule module : moduleList) {
                List<Integer> proIds = module.getPropertyIds();
                String ids = "";
                if (proIds != null && proIds.size() > 0) {
                    for (int i = 0; i < proIds.size() - 1; i++) {
                        ids += proIds.get(i) + ",";
                    }
                    ids += proIds.get(proIds.size() - 1);
                }
                module.setPropertyIdsSTR(ids);
                List<Integer> proDetailIds = module.getProDetailIds();
                String idds = "";
                if (proDetailIds != null && proDetailIds.size() > 0) {
                    for (int i = 0; i < proDetailIds.size() - 1; i++) {
                        idds += proDetailIds.get(i) + ",";
                    }
                    idds += proDetailIds.get(proDetailIds.size() - 1);
                }
                module.setProDetailIdsSTR(idds);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventHelper.unregister(this);

    }

    /**
     * 商品图片
     *
     * @param event
     */
    @Subscribe
    public void onEvent(ImageListEvent event) {

        imageListResult.setSampleDetailImages(event.mListData);
        imageListResult.setSampleDetailMessage(event.name);
        reviewUI();
    }

    /**
     * 商品描述
     *
     * @param event
     */
    @Subscribe
    public void onEvent(final DescriptionListEvent event) {
        if (mDescriptionModule == null) {
            mDescriptionModule = new DescriptionModule();
        }
        mDescriptionModule.setSampleDetailMessage(event.name);
        mDescriptionModule.setSampleDetailImages(event.mListData);
        mDescriptionModule.setLocalUrl(event.localUrl);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDescriptionModule.getSampleDetailImages().size() > 0 || event.name != null) {
                    mTvInputDescription.setText(mTranslatesString.getCommon_edited());
                } else {
                    mTvInputDescription.setText("");
                }
            }
        });
    }

    public void reviewUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (imageListResult.getSampleDetailImages().size() != 0) {
                    mRlytSelect.setVisibility(View.VISIBLE);
                    mRlytPre.setVisibility(View.GONE);
                    loader.displayImage("file:/" + imageListResult.getSampleDetailImages().get(0).getUrl(), mIvSelectPhone, options);
                } else {
                    mRlytSelect.setVisibility(View.GONE);
                    mRlytPre.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (data != null) {
                List<ImageItem> mList;
                mList = (List<ImageItem>) data.getSerializableExtra("mListData");
                if (mList != null) {
                    imageListResult.setSampleDetailImages(mList);
                }
                reviewUI();
            }
        }
        if (requestCode == 108) {
            if (data != null) {
                dizhi = data.getStringArrayListExtra("dizhi");
                Logs.d("dizhi", dizhi.toString());
                ids = data.getIntegerArrayListExtra("ids");
//                positionList = data.getIntegerArrayListExtra("positionList");
                if (ids.size() != 0) {
//                    for (String ss : dizhi) {
//                        mTvInputWarehouse.setText(ss);
//                    }
                    String s = ListToString(dizhi);
                    mTvInputWarehouse.setText(s);
                } else {
                    mTvInputWarehouse.setText("");
                }
            }
        }
    }

    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己 
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i)));
                    sb.append(",");
                } else {
                    sb.append(list.get(i));
                    sb.append(",");
                }
            }

        }
        String s = sb.toString();
        return s.substring(0, s.length() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (moduleList.size() == 0) {
            mLytAccount.setVisibility(View.VISIBLE);
        }
    }
}
