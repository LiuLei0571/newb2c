package com.example.b2c.temple;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.GoodsListActivity;
import com.example.b2c.activity.IAct;
import com.example.b2c.activity.ShopHomesActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.ModelPageItem;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


/**
 * Created by milk on 2016/10/27.
 */

public abstract class BaseTemplate<M extends ITemplateModel, VH extends TemplateBaseHolder>
        implements ITemplate<M, VH> {
    private IAct act;
    private View.OnClickListener mOnClick;
    private ModelPageItem data;
    protected ImageLoader mImageLoader;
    protected DisplayImageOptions options;
    protected MobileStaticTextCode mTranslatesString;
    protected Intent intent;
    protected Bundle bundle;

    public View.OnClickListener getOnClick() {
        if (mOnClick == null) {
            mOnClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object item = v.getTag(R.id.item);
                    String type = "";
                    if (item != null && item instanceof ModelPageItem) {
                        type = ((ModelPageItem) item).getItemType();
                        data = ((ModelPageItem) item);
                    }
                    if (!TextUtils.isEmpty(type) && data != null) {
                        switch (type) {
                            case "goodsList":
                                intent = new Intent(act.getActivity(), GoodsListActivity.class);
                                bundle = new Bundle();
                                bundle.putString("categoryId", data.getItemGoodsList() + "");
                                intent.putExtras(bundle);
                                break;
                            case "goodsDetail":
                                intent = new Intent(act.getActivity(), GoodsDetailActivity.class);
                                bundle = new Bundle();
                                bundle.putInt("sampleId", data.getItemGoodsDetailId());
                                intent.putExtras(bundle);
                                break;
                            case "shopHome":
                                intent = new Intent(act.getActivity(), ShopHomesActivity.class);
                                bundle = new Bundle();
                                bundle.putInt("shopId", data.getItemStoreId());
                                intent.putExtras(bundle);
                                break;
                        }
                        act.getActivity().startActivity(intent);
                    }
                }
            };
        }
        return mOnClick;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.mOnClick = listener;
    }

    public BaseTemplate(IAct act) {
        this.act = act;
        mImageLoader = ImageLoader.getInstance();
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(0)
                .showImageOnFail(R.drawable.ic_fail_image)
                .showImageForEmptyUri(R.drawable.ic_no_image)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
    }

    @Override
    public void onResume(IAct act) {

    }

    @Override
    public void onPause(IAct act) {

    }

    public IAct getAct() {
        return act;
    }

    @Override
    public void bindCustomViewHolder(VH holder, M model) {

    }

}
