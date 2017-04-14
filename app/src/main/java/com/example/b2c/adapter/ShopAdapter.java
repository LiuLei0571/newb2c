package com.example.b2c.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.CartShopCell;
import com.example.b2c.widget.util.Utility;

import java.util.HashMap;
import java.util.List;

/**
 * 购物车适配器
 */
public class ShopAdapter extends BaseAdapter {
    private List<CartShopCell> cart_list;
    private ViewHolder viewHolder;
    private CartGoodsAdapter[] goods_adapter;
    public Context context;
    private boolean flag = true;// 全选或取消
    public HashMap<Integer, Boolean> isSelected;
    private Handler mHandler;

    public ShopAdapter(Context context, List<CartShopCell> cart_list, Handler mHandler) {
        super();
        this.context = context;
        this.cart_list = cart_list;
        this.mHandler = mHandler;
        isSelected = new HashMap<>();
        goods_adapter = new CartGoodsAdapter[cart_list.size()];
        initData();
    }

    public void initData() {
        for (int i = 0; i < cart_list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int getCount() {
        return cart_list.size();
    }

    @Override
    public CartShopCell getItem(int arg0) {
        return cart_list.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.cart_shop_item, null);
            viewHolder.lv_goods = (ListView) convertView
                    .findViewById(R.id.lv_goods);
            viewHolder.cb_shop_select = (CheckBox) convertView
                    .findViewById(R.id.cb_shop_all);
            viewHolder.tv_shop_name = (TextView) convertView
                    .findViewById(R.id.tv_shop_name);
            goods_adapter[position] = new CartGoodsAdapter(context, cart_list
                    .get(position).getSamples(), handler);
            viewHolder.lv_goods.setAdapter(goods_adapter[position]);
            Utility.setListViewHeightBasedOnChildren(viewHolder.lv_goods);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cb_shop_select.setChecked(getIsSelected().get(position));
        viewHolder.tv_shop_name.setText(cart_list.get(position).getShopName());
        viewHolder.cb_shop_select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                selectedAll(position, checked);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ListView lv_goods;
        private CheckBox cb_shop_select;
        private TextView tv_shop_name;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { // 更改选中商品的总价格
                double price = (double) msg.obj;
                mHandler.sendMessage(mHandler.obtainMessage(10, price));

            } else if (msg.what == 11) {
                // 所有列表中的商品全部被选中，让全选按钮也被选中
                // flag记录是否全被选中
                flag = (Boolean) msg.obj;
//              viewHolder.cb_shop_select.setChecked(flag);
            } else if (msg.what == 60) {
                int goodId = (int) msg.obj;
                mHandler.sendMessage(mHandler.obtainMessage(60, goodId));
            }
        }
    };

    // 全选或全取消
    private void selectedAll(int position, boolean checked) {
        for (int i = 0; i < cart_list.get(position).getSamples().size(); i++) {
            goods_adapter[position].getIsSelected().put(i, checked);
        }
        goods_adapter[position].notifyDataSetChanged();
    }

    public void setAllShopChecked(boolean checked) {
        for (int i = 0; i < cart_list.size(); i++) {
            selectedAll(i, checked);
        }
    }

    private boolean isAllSelected() {
        boolean flag = true;
        for (int i = 0; i < cart_list.size(); i++) {
            if (!getIsSelected().get(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
