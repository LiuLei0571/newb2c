package com.example.b2c.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.net.response.BuyerOrderList;


/**
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */

public abstract class AllOrderAdapter extends BaseAdapter<BuyerOrderList> {
    public AllOrderAdapter(Context context) {
        super(context, R.layout.item_buyer_order_all);
    }
    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        final BuyerOrderList orderCell = getItem(position);
        ListView mList = viewHolderFactory.findViewById(R.id.list);
        TextView mTvName = viewHolderFactory.findViewById(R.id.tv_shop_name);
        TextView mTvStatus = viewHolderFactory.findViewById(R.id.tv_status_name);

        TextView mtv_sum = viewHolderFactory.findViewById(R.id.tv_sum);
        TextView mTvExpressMoney = viewHolderFactory.findViewById(R.id.tv_express_money);

        TextView tv_actualPay = viewHolderFactory.findViewById(R.id.tv_actualPay);
        TextView tv_discount = viewHolderFactory.findViewById(R.id.tv_discount);

//      TextView mBtnContact = viewHolderFactory.findViewById(R.id.btn_contact_seller);
//      TextView mBtnSaoQa = viewHolderFactory.findViewById(R.id.btn_sao_qa);
        TextView mBtnRemind = viewHolderFactory.findViewById(R.id.btn_remind_seller);
        TextView mBtnSeeExp= viewHolderFactory.findViewById(R.id.btn_check_express);
//        TextView mBtnreject= viewHolderFactory.findViewById(R.id.btn_reject);
        TextView mBtnApplyRet= viewHolderFactory.findViewById(R.id.btn_apply_return);
        TextView mBtnCancel= viewHolderFactory.findViewById(R.id.btn_cancel_order);
        TextView btn_evaluate= viewHolderFactory.findViewById(R.id.btn_evaluate);
        BuyerOrderDetailAdapter mAdapter = new BuyerOrderDetailAdapter(getContext());
        mAdapter.setData(orderCell.getOrderDetailList());
        mList.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mList);
        mList.setFocusable(false);
        mList.setEnabled(false);
        mList.setPressed(false);
        mList.setClickable(false);
        if (orderCell != null) {
            mTvName.setText(orderCell.getShopName());
            mtv_sum.setText(mTranslatesString.getCommon_sum()+Configs.CURRENCY_UNIT + orderCell.getTotalPrice());
            mTvExpressMoney.setText("("+mTranslatesString.getCommon_freight()+" "+Configs.CURRENCY_UNIT + orderCell.getDeliveryFee() + ")");

            tv_actualPay.setText(mTranslatesString.getCommon_actualPrice()+Configs.CURRENCY_UNIT + orderCell.getActualPayPrice());
            tv_discount.setText("("+mTranslatesString.getCommon_youhui()+Configs.CURRENCY_UNIT + orderCell.getDiscount()+")");
// mBtnContact.setText(mTranslatesString.getCommon_contactseller());
//          mBtnSaoQa.setText(mTranslatesString.getCommon_saomareceivegoods());
            mBtnRemind.setText(mTranslatesString.getCommon_remindseller());
            mBtnSeeExp.setText(mTranslatesString.getCommon_examineexpress());
            mBtnApplyRet.setText(mTranslatesString.getCommon_applybackgood());
            mBtnCancel.setText(mTranslatesString.getCommon_cancelorder());
            btn_evaluate.setText(mTranslatesString.getCommon_comment());
//            mBtnreject.setText(mTranslatesString.getCommon_applyreject());
            if (orderCell.getOrderStatus() == 10) {
                mTvStatus.setText(mTranslatesString.getCommon_waitsend());
                mTvStatus.setVisibility(View.VISIBLE);
                //待发货
                mBtnRemind.setVisibility(View.VISIBLE);
                mBtnCancel.setVisibility(View.VISIBLE);
                mBtnApplyRet.setVisibility(View.GONE);
                mBtnSeeExp.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
//                mBtnreject.setVisibility(View.GONE);
            } else if  (orderCell.getOrderStatus() == 20) {
                mTvStatus.setText(mTranslatesString.getSeller_homesendtitle());
                mTvStatus.setVisibility(View.VISIBLE);
                //已发货
                mBtnSeeExp.setVisibility(View.VISIBLE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                mBtnApplyRet.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
//                mBtnreject.setVisibility(View.VISIBLE);
            }else if  (orderCell.getOrderStatus() == 30) {
                //已签收
                mTvStatus.setText(mTranslatesString.getSeller_homereceived());
                mTvStatus.setVisibility(View.VISIBLE);
                mBtnApplyRet.setVisibility(View.VISIBLE);
                mBtnSeeExp.setVisibility(View.GONE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                if(orderCell.getHasEvaluated()==0){
                    btn_evaluate.setVisibility(View.VISIBLE);
                }else {
                    btn_evaluate.setVisibility(View.GONE);
                }
//                mBtnreject.setVisibility(View.GONE);
            }else if  (orderCell.getOrderStatus() == 40) {
                mTvStatus.setText(mTranslatesString.getCommon_yetcancel());
                mTvStatus.setVisibility(View.VISIBLE);
                //已取消
                mBtnSeeExp.setVisibility(View.GONE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                mBtnApplyRet.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
//                mBtnreject.setVisibility(View.GONE);
            }else if (orderCell.getOrderStatus() == 45){
                mTvStatus.setText(mTranslatesString.getCommon_sellercancel());
                mTvStatus.setVisibility(View.VISIBLE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                mBtnApplyRet.setVisibility(View.GONE);
                mBtnSeeExp.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
            } else if (orderCell.getOrderStatus() == 50) {
                //拒收
                mTvStatus.setText(mTranslatesString.getCommon_yetreject());
                mTvStatus.setVisibility(View.VISIBLE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                mBtnApplyRet.setVisibility(View.GONE);
                mBtnSeeExp.setVisibility(View.GONE);
//                mBtnreject.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
            } else {
                mTvStatus.setVisibility(View.INVISIBLE);
                mBtnRemind.setVisibility(View.GONE);
                mBtnCancel.setVisibility(View.GONE);
                mBtnApplyRet.setVisibility(View.GONE);
                mBtnSeeExp.setVisibility(View.GONE);
                btn_evaluate.setVisibility(View.GONE);
//                mBtnreject.setVisibility(View.GONE);
            }
//            mBtnSaoQa.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), CaptureActivity.class);
//                    intent.putExtra("userType", UserHelper.getBuyerFromLocal().getUserType());
//                    intent.putExtra("orderId", orderList.getOrderId());
//                    getContext().startActivity(intent);
//                }
//            });
//            mBtnContact.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), WebViewActivity.class);
//                    intent.putExtra("url", "http://115.29.167.40:81/chat/chatWithSeller/" + Utils.cutNull( orderList.getSellerId()) + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&loginName=" + UserHelper.getBuyerName() + "&plat=android&sellerId");
//                    getContext().startActivity(intent);
//                }
//            });

            //提醒卖家发货
            mBtnRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   remind(orderCell);
                }
            });
            //取消订单
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogHelper.BtnTv deleteBtn = new DialogHelper.BtnTv(mTranslatesString.getCommon_makesure(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancel(orderCell);
                        }
                    });
                    DialogHelper.BtnTv cancelBtn = new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    DialogHelper.showDialog(context, mTranslatesString.getNotice_noticename(), mTranslatesString.getCommon_sure()+"?", Gravity.CENTER, deleteBtn, cancelBtn);
                }
            });
            //查看物流
            mBtnSeeExp.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    seeExp(orderCell);
                }
            });
            //申请拒收
//            mBtnreject.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    reject(orderCell);
//                }
//            });
            //申请退货
            mBtnApplyRet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnGood(orderCell);
                }
            });

            btn_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(orderCell.getHasEvaluated()==0){
                        evaluate(orderCell);
                    }
                }
            });
        }
    }

    public abstract void remind(BuyerOrderList order);
    public abstract void cancel(BuyerOrderList order);
    public abstract void seeExp(BuyerOrderList order);
//    public abstract void reject(BuyerOrderList order);
    public abstract void returnGood(BuyerOrderList order);
    public abstract void evaluate(BuyerOrderList order);
}
