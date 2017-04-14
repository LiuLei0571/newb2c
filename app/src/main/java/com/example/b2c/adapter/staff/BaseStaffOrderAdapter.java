package com.example.b2c.adapter.staff;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.logistics.GetStaffOrderResult;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */

public abstract class BaseStaffOrderAdapter extends XRcycleViewAdapterBase {


    public BaseStaffOrderAdapter() {
    }

    private List<GetStaffOrderResult.StaffOrderList> mData;
    private Context context;
    private MobileStaticTextCode mTranslatesString;
    private TextView mTvTitle;
    private TextView mTvExpressId;
    private TextView mTvExpressStatus;
    private ImageView mIvGoods;
    private TextView mTvGoodCount;
    private TextView mTvGoodsMoney;
    private TextView mTvSellerTitle;
    private TextView mTvSellerMessage;
    private TextView mTvSellerMessage1;
    private TextView mTvSellerMessage2;
    private TextView mTvBuyerTitle;
    private TextView mTvBuyerMessage;
    private TextView mTvBuyerMessage1;
    private TextView mTvBuyerMessage2;
    private TextView mBtnRefuse;
    private TextView mBtnConfirm;
    private TextView mBtnGetGoods;
    private TextView tv_orderCode;
    public BaseStaffOrderAdapter(Context context, List<GetStaffOrderResult.StaffOrderList> mData,
                                 MobileStaticTextCode mTranslatesString) {
        this.mData = mData;
        this.context = context;
        this.mTranslatesString = mTranslatesString;
    }
    @Override
    public int getItemPostion() {
        return mData.size();
    }

    @Override
    public int getLayoutID() {
        return R.layout.item_staff_order_detail;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initData(MyViewHolder holder, final int position) {
        mIvGoods.setTag(mData.get(position).getSamplePic());
        mTvTitle.setText(mTranslatesString.getExpress_no()+":");
        mTvBuyerTitle.setText(mTranslatesString.getCommon_buyermessage()+":");
        mTvSellerTitle.setText(mTranslatesString.getCommon_sellermessage()+":");
        mBtnRefuse.setText(mTranslatesString.getCommon_buyerrefuse());
        mBtnConfirm.setText(mTranslatesString.getCommon_receivemoney());
        mBtnGetGoods.setText(mTranslatesString.getCommon_getgoods());
        if (mData != null) {
            mTvExpressId.setText(mData.get(position).getDeliveryNo());
            switch (mData.get(position).getOrderStatus()) {
                case 1:
                    mBtnGetGoods.setVisibility(View.VISIBLE);
                    mBtnRefuse.setVisibility(View.GONE);
                    mBtnConfirm.setVisibility(View.GONE);
                    mTvExpressStatus.setText(mTranslatesString.getCommon_daiquhuo());
                    break;
                case 2:
                    mBtnRefuse.setVisibility(View.VISIBLE);
                    mBtnConfirm.setVisibility(View.VISIBLE);
                    mTvExpressStatus.setText(mTranslatesString.getCommon_yunsongzhong());
                    break;
                case 3:
                    mTvExpressStatus.setText(mTranslatesString.getSeller_homereceived());
                    break;
                case 4:
                    mTvExpressStatus.setText(mTranslatesString.getCommon_yetreject());
                    break;
                default:
                    mTvExpressStatus.setText("");
                    break;
            }
            String s = ConstantS.BASE_PIC_URL + mData.get(position).getSamplePic();
            if (mData.get(position).getSamplePic()!=null
                    &&mData.get(position).getSamplePic().equals(mIvGoods.getTag())){
                ImageHelper.display(s, mIvGoods);
            }else{
                mIvGoods.setBackgroundResource(R.drawable.ic_fail_image);
            }
            tv_orderCode.setText(mTranslatesString.getOrder_no()+":"+mData.get(position).getOrderCode());
//            mImagerLoader.imaheLoader(ConstantS.BASE_PIC_URL + mData.get(position).getActualPayPrice(), mIvGoods, false);
            mTvGoodCount.setText(mData.get(position).getOrderDetailCount()+mTranslatesString.getCommon_chanpinnumber());
            mTvGoodsMoney.setText(mTranslatesString.getCommon_sum()+":" + mData.get(position).getActualPayPrice()+"Ks");
            mTvSellerMessage.setText(mData.get(position).getSellerInfo().getSellerName());
            String sellerMobile = mData.get(position).getSellerInfo().getSellerMobile();
            mTvSellerMessage1.setText(sellerMobile);
            mTvSellerMessage2.setText(mData.get(position).getSellerInfo().getShippingAddress());
            mTvBuyerMessage.setText(mData.get(position).getBuyerInfo().getBuyerName());
            mTvBuyerMessage1.setText(mData.get(position).getBuyerInfo().getBuyerMobile());
            if (!TextUtils.isEmpty(mData.get(position).getBuyerInfo().getPayAddress())) {
                mTvBuyerMessage2.setText(mData.get(position).getBuyerInfo().getReceiveAddress() + "/" + mData.get(position).getBuyerInfo().getPayAddress());
            }else{
                mTvBuyerMessage2.setText(mData.get(position).getBuyerInfo().getReceiveAddress());
            }
                mTvExpressId.setText(mData.get(position).getDeliveryNo());
            mBtnGetGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击正自取货
                    update(mData, 1,mData.get(position).getId());
                }
            });
            mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(mData, 2,mData.get(position).getId());

                }
            });
            mBtnRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(mData, 3,mData.get(position).getId());

                }
            });
        }else{
            return;
        }
    }

    @Override
    public void initView(View viewHolderFactory) {
        mTvTitle = (TextView) viewHolderFactory.findViewById(R.id.tv_express_title);
        mTvExpressId = (TextView) viewHolderFactory.findViewById(R.id.tv_express_id);
        mTvExpressStatus = (TextView) viewHolderFactory.findViewById(R.id.tv_express_status);
        mIvGoods = (ImageView) viewHolderFactory.findViewById(R.id.iv_goods_head);
        mTvGoodCount = (TextView) viewHolderFactory.findViewById(R.id.tv_goods_count);
        mTvGoodsMoney = (TextView) viewHolderFactory.findViewById(R.id.tv_good_money);
        mTvSellerTitle = (TextView) viewHolderFactory.findViewById(R.id.tv_seller_title);
        mTvSellerMessage = (TextView) viewHolderFactory.findViewById(R.id.tv_seller_message);
        mTvSellerMessage1 = (TextView) viewHolderFactory.findViewById(R.id.tv_seller_message1);
        mTvSellerMessage2 = (TextView) viewHolderFactory.findViewById(R.id.tv_seller_message2);
        mTvBuyerTitle = (TextView) viewHolderFactory.findViewById(R.id.tv_buyer_title);
        mTvBuyerMessage = (TextView) viewHolderFactory.findViewById(R.id.tv_buyer_message);
        mTvBuyerMessage1 = (TextView) viewHolderFactory.findViewById(R.id.tv_buyer_message1);
        mTvBuyerMessage2 = (TextView) viewHolderFactory.findViewById(R.id.tv_buyer_message2);
        mBtnRefuse = (TextView) viewHolderFactory.findViewById(R.id.btn_buyer_refuse);
        mBtnConfirm = (TextView) viewHolderFactory.findViewById(R.id.btn_confirm_pay);
        mBtnGetGoods = (TextView) viewHolderFactory.findViewById(R.id.btn_get_goods);
        tv_orderCode = (TextView) viewHolderFactory.findViewById(R.id.tv_orderCode);
    }

    public abstract void update(List<GetStaffOrderResult.StaffOrderList> mData, int type,int id);
}
