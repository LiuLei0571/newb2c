package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.logistic.LogisticsOrderDetailActivity;
import com.example.b2c.net.response.logistics.OrderBean;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by ThinkPad on 2017/2/13.
 */

public class MoyOrderAdapter extends XRecyclerView.Adapter<MoyOrderAdapter.ViewHolder> implements View.OnClickListener {
    private TextView tv_buyer_phone;
    private TextView tv_shouhuo_phone;
    private TextView tv_shoukuan_phone;

    public MoyOrderAdapter() {
    }

    private Context context;
    private List<OrderBean.Rows> rows;
//    private int tag;
    private MobileStaticTextCode mTranslatesString;

    public MoyOrderAdapter(Context context, List<OrderBean.Rows> rows, MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.rows = rows;
//        this.tag = tag;
        this.mTranslatesString = mTranslatesString;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_logistics_order, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.tv_title_order_no.setText(mTranslatesString.getDelivery_no()+":");
        holder.tv_title_buyer.setText(mTranslatesString.getCommon_sellermessage()+":");
        holder.tv_title_seller.setText(mTranslatesString.getConnom_shoukuandizhi()+":");
        holder.tv_title_shouhuo.setText(mTranslatesString.getCommon_receivelocation()+":");
        holder.tv_kan_div.setText(mTranslatesString.getCommon_selectdetail());
        if (rows.get(position).getOrderStatus().equals("1")) {
            //新订单
            holder.tv_title_time_q.setText(mTranslatesString.getSeller_homenewtitle());
            holder.tv_title_time_q.setBackgroundResource(R.drawable.bg_tyep1);
            holder.ll_yundanhao.setVisibility(View.GONE);
//            holder.ll_paidan.setBackgroundResource(R.drawable.bg_tyep2);
            holder.tv_order_detail.setVisibility(View.GONE);
//            holder.tv_order_detail.setText(mTranslatesString.getCommon_paidan());
        }
        if (rows.get(position).getOrderStatus().equals("2")) {
            //运动中
            holder.tv_title_time_q.setText(mTranslatesString.getCommon_yunsongzhong());
            holder.tv_order_no.setText(rows.get(position).getDeliveryNo());
            holder.tv_title_time_q.setBackgroundResource(R.drawable.bg_tyep2);

//            holder.ll_paidan.setBackgroundResource(R.drawable.bg_tyep2);
            holder.tv_order_detail.setVisibility(View.GONE);
//            holder.tv_order_detail.setText(mTranslatesString.getCommon_paidan());
        }
        if (rows.get(position).getOrderStatus().equals("3")) {
            //已签收
            holder.ll_yunhuoyuan.setVisibility(View.VISIBLE);
            holder.tv_title_time_q.setText(mTranslatesString.getSeller_homereceived());
            holder.tv_title_time_q.setBackgroundResource(R.drawable.bg_tyep3);
            holder.tv_order_detail.setTextColor(context.getResources().getColor(R.color.orangered));
            holder.tv_order_detail.setText(mTranslatesString.getCommon_maijiafukuan());
            holder.tv_order_no.setText(rows.get(position).getDeliveryNo());
            holder.tv_yunhuo_title.setText(mTranslatesString.getCommon_yumhuoyuan()+":");
            if (TextUtils.isEmpty(rows.get(position).getDeliveryAccountLateName())) {
                holder.tv_yunhuo_no.setText("");
            }else{
                holder.tv_yunhuo_no.setText(rows.get(position).getDeliveryAccountLateName() +
                        rows.get(position).getDeliveryAccountFirstName() + "");
            }
        }
        if (rows.get(position).getOrderStatus().equals("4")) {
            //拒收
                holder.tv_title_time_q.setText(mTranslatesString.getCommon_yetreject());
            holder.tv_title_time_q.setBackgroundResource(R.drawable.bg_tyep3);
            holder.tv_order_detail.setTextColor(context.getResources().getColor(R.color.orangered));
            holder.tv_order_detail.setText(mTranslatesString.getBuyer_refuse());
            holder.tv_order_no.setText(rows.get(position).getDeliveryNo());
            holder.tv_yunhuo_title.setText(mTranslatesString.getCommon_yumhuoyuan()+":");
            if (TextUtils.isEmpty(rows.get(position).getDeliveryAccountLateName())) {
                holder.tv_yunhuo_no.setText("");
            }else{
                holder.tv_yunhuo_no.setText(rows.get(position).getDeliveryAccountLateName() +
                        rows.get(position).getDeliveryAccountFirstName() + "");
            }
        }
        holder.tv_order_code.setText(mTranslatesString.getOrder_no()+":"+rows.get(position).getCode());
        holder.tv_order_time.setText(rows.get(position).getModifyTime());
        holder.tv_buyer_message.setText(rows.get(position).getSellerName());
        holder.tv_maijia_div.setText("");
        holder.tv_buyer_phone.setText(rows.get(position).getSellerMobile());
        holder.tv_seller_message.setText(rows.get(position).getPayFirstName()+rows.get(position).getPayLastName());
        holder.tv_shoukuan_phone.setText(rows.get(position).getPayMobile());
        holder.tv_shoukuan_div.setText(rows.get(position).getPayAddress());
        holder.tv_buyer_shouhuo.setText(rows.get(position).getReceiveFirsName()+rows.get(position).getReceiveLastName());
        holder.tv_shouhuo_phone.setText(rows.get(position).getReceiveMobile());
        holder.tv_maijia_shouhuo.setText(rows.get(position).getReceiveAddress());
        holder.tv_yunfei.setText("Ks  "+rows.get(position).getActualPayPrice()+"("+mTranslatesString.getCommon_freight()+":ks"+
        rows.get(position).getDeliveryFee()+")");
        holder.tv_kan_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到详情
                Intent intent = new Intent(context, LogisticsOrderDetailActivity.class);
//                int itemCount = getItemCount()-1;
                int id = rows.get(position).getId();
                intent.putExtra("position",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
             this.mOnItemClickListener = listener;
         }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
                        //注意这里使用getTag方法获取数据
                        mOnItemClickListener.onItemClick(v,getItemCount());
                   }

    }

    public static interface OnRecyclerViewItemClickListener {
           void onItemClick(View view , int position);
         }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title_time_q;
        public TextView tv_order_time;
        public TextView tv_kan_div;
        public TextView tv_order_money;
        public TextView tv_title_order_no;
        public TextView tv_order_no;
        public TextView tv_title_buyer;
        public TextView tv_buyer_message;
        public TextView tv_maijia_div;
        public TextView tv_title_seller;
        public TextView tv_seller_message;
        public TextView tv_shoukuan_div;
        public TextView tv_title_shouhuo;
        public TextView tv_buyer_shouhuo;
        public TextView tv_maijia_shouhuo;
        public TextView tv_yunfei;
        public TextView tv_order_detail;
        public LinearLayout ll_yundanhao;
        public TextView tv_buyer_phone;
        public TextView tv_shouhuo_phone;
        public TextView tv_shoukuan_phone;
        public LinearLayout ll_yunhuoyuan;
        public LinearLayout ll_paidan;
        public TextView tv_yunhuo_title;
        public TextView tv_yunhuo_no;
        public TextView tv_order_code;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tv_title_time_q = (TextView) rootView.findViewById(R.id.tv_title_time_q);
            this.tv_order_time = (TextView) rootView.findViewById(R.id.tv_order_time);
            this.tv_kan_div = (TextView) rootView.findViewById(R.id.tv_kan_div);
            this.tv_order_money = (TextView) rootView.findViewById(R.id.tv_order_money);
            this.tv_title_order_no = (TextView) rootView.findViewById(R.id.tv_title_order_no);
            this.tv_order_no = (TextView) rootView.findViewById(R.id.tv_order_no);
            this.tv_title_buyer = (TextView) rootView.findViewById(R.id.tv_title_buyer);
            this.tv_buyer_message = (TextView) rootView.findViewById(R.id.tv_buyer_message);
            this.tv_maijia_div = (TextView) rootView.findViewById(R.id.tv_maijia_div);
            this.tv_title_seller = (TextView) rootView.findViewById(R.id.tv_title_seller);
            this.tv_seller_message = (TextView) rootView.findViewById(R.id.tv_seller_message);
            this.tv_shoukuan_div = (TextView) rootView.findViewById(R.id.tv_shoukuan_div);
            this.tv_title_shouhuo = (TextView) rootView.findViewById(R.id.tv_title_shouhuo);
            this.tv_buyer_shouhuo = (TextView) rootView.findViewById(R.id.tv_buyer_shouhuo);
            this.tv_maijia_shouhuo = (TextView) rootView.findViewById(R.id.tv_maijia_shouhuo);
            this.tv_yunfei = (TextView) rootView.findViewById(R.id.tv_yunfei);
            this.tv_order_detail = (TextView) rootView.findViewById(R.id.tv_order_detail);
            this.ll_yundanhao = (LinearLayout) rootView.findViewById(R.id.ll_yundanhao);
            this.ll_yunhuoyuan = (LinearLayout) rootView.findViewById(R.id.ll_yunhuoyuan);
            this.ll_paidan = (LinearLayout) rootView.findViewById(R.id.ll_paidan);
            this.tv_buyer_phone = (TextView) rootView.findViewById(R.id.tv_buyer_phone);
            this.tv_shouhuo_phone = (TextView) rootView.findViewById(R.id.tv_shouhuo_phone);
            this.tv_shoukuan_phone = (TextView) rootView.findViewById(R.id.tv_shoukuan_phone);
            this.tv_yunhuo_title = (TextView) rootView.findViewById(R.id.tv_yunhuo_title);
            this.tv_yunhuo_no = (TextView) rootView.findViewById(R.id.tv_yunhuo_no);
            this.tv_order_code = (TextView) rootView.findViewById(R.id.tv_order_code);
        }
    }
}
