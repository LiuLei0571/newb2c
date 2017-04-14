package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.OrderDetailActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.listener.UpdateOrderStatusListener;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.widget.util.Utility;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {


    private ViewHolder viewHolder;
    private Context context;
    private List<BuyerOrderList> orderList;
    private String total_detail;
    public UpdateOrderStatusListener statusListener;
    private final int WAIT_PAY = 10, WAIT_SEND = 20, WAIT_RECV = 30,
            SUCCESS = 40, CLOSE = 50, CANCEL = 80, ORDER_PAYED = 1,
            ORDER_RECV = 2, ORDER_CANCEL = 3;
    protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

    private String[] status = {"",
            mTranslatesString.getCommon_waitpay(),
            mTranslatesString.getCommon_waitsend(),
            mTranslatesString.getCommon_waitrecv(),
            mTranslatesString.getCommon_done(),
            mTranslatesString.getCommon_closeorder()};
    // private String[] action = { "", "付款", "提醒发货", "确认收货", "评价", "" };
    private String[] action = {"",
            mTranslatesString.getCommon_pay(),
            mTranslatesString.getCommon_noticesend(),
            mTranslatesString.getCommon_received(),
            mTranslatesString.getCommon_makecomments()};

    public OrderListAdapter(Context context, List<BuyerOrderList> orderList) {
        super();
        this.context = context;
        this.orderList = orderList;
    }

    public void clearList() {
        this.orderList.clear();
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public BuyerOrderList getItem(int position) {
        // TODO Auto-generated method stub
        return this.orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.order_shop_list_item,
                    null);
            viewHolder.lv_goods = (ListView) convertView
                    .findViewById(R.id.lv_order_list_goods);
            viewHolder.tv_order_item_status = (TextView) convertView
                    .findViewById(R.id.order_item_status);
            viewHolder.tv_shop_name = (TextView) convertView
                    .findViewById(R.id.order_item_shop_name);
            viewHolder.tv_order_item_total_detail = (TextView) convertView
                    .findViewById(R.id.order_item_total);
            viewHolder.btn_cancel = (Button) convertView
                    .findViewById(R.id.btn_cancel);
            viewHolder.btn_action = (Button) convertView
                    .findViewById(R.id.btn_action);

            if (orderList.get(position).getOrderStatus() == WAIT_PAY) {
                viewHolder.btn_action
                        .setOnClickListener(new OrderStatusButtonListener(
                                position, ORDER_PAYED));
            } else if (orderList.get(position).getOrderStatus() == WAIT_SEND) {

            } else if (orderList.get(position).getOrderStatus() == WAIT_RECV) {
                viewHolder.btn_action
                        .setOnClickListener(new OrderStatusButtonListener(
                                position, ORDER_RECV));
            } else if (orderList.get(position).getOrderStatus() == SUCCESS) {

            }

            Utility.setListViewHeightBasedOnChildren(viewHolder.lv_goods);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 把所有显示内容的处理房在初始化convertview外面，不然缓存会出问题
//        viewHolder.lv_goods.setAdapter(new OrderGoodsAdapter(context, orderList
//                .get(position).getOrderDetails(), orderList.get(position)
//                .getOrderStatus(), orderList.get(position).getShopId()));
//        viewHolder.lv_goods.setOnItemClickListener(new SampleListListener(
//                position));
        int amount = 0;
//        for (int i = 0; i < orderList.get(position).getOrderDetails().size(); i++) {
//            amount += orderList.get(position).getOrderDetails().get(i)
//                    .getAmount();
//        }
        total_detail = amount
                + mTranslatesString.getCommon_amountofsample()
                + " "
                + mTranslatesString.getCommon_deliveryfee()
                + " :¥0.00 " + mTranslatesString.getCommon_sum()
                + ": ¥" + orderList.get(position).getTotalPrice();
        viewHolder.tv_order_item_status.setText(status[orderList.get(position)
                .getOrderStatus() / 10]);
        viewHolder.tv_order_item_total_detail.setText(total_detail);
        viewHolder.tv_shop_name.setText(orderList.get(position).getShopName());

        if (orderList.get(position).getOrderStatus() != WAIT_PAY) {
            viewHolder.btn_cancel.setVisibility(View.GONE);
        } else {
            viewHolder.btn_cancel.setVisibility(View.VISIBLE);
            viewHolder.btn_cancel.setText(mTranslatesString
                    .getCommon_cancelorder());
            viewHolder.btn_cancel
                    .setOnClickListener(new OrderStatusButtonListener(position,
                            ORDER_CANCEL));
        }
        setButton(viewHolder, orderList.get(position).getOrderStatus(),
                position);
        if (orderList.get(position).getOrderStatus() != CANCEL) {// 如果交易状态为取消，则将按钮设置为消失
            viewHolder.btn_action.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_action.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        public ListView lv_goods;
        public TextView tv_shop_name, tv_order_item_status,
                tv_order_item_total_detail;
        public Button btn_cancel, btn_action;
    }

    class SampleListListener implements OnItemClickListener {
        private int position;

        public SampleListListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            Intent i = new Intent(context, OrderDetailActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putInt("orderId", orderList.get(position).getId());
            i.putExtras(bundle);
            context.startActivity(i);
        }
    }

    public void setButton(ViewHolder holder, int orderStatus, int position) {
        switch (orderStatus) {
            case WAIT_PAY:
                holder.btn_action.setText(action[1]);
                break;
            case WAIT_SEND:
                holder.btn_action.setText(action[2]);
                break;
            case WAIT_RECV:
                holder.btn_action.setText(action[3]);
                break;
            case SUCCESS:
                holder.btn_action.setText(action[4]);
                break;
            default:
                break;
        }

    }

    class OrderStatusButtonListener implements OnClickListener {
        private int position, orderStatus;

        public OrderStatusButtonListener(int position, int orderStatus) {
            super();
            this.position = position;
            this.orderStatus = orderStatus;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            statusListener.onClick(position, orderStatus);
        }
    }
}
