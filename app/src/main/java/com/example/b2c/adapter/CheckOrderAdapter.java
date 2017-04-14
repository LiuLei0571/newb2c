package com.example.b2c.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.CartShopDetail;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.widget.util.Utility;

import java.util.List;

/**
 * 确认订单适配器
 */
public class CheckOrderAdapter extends BaseAdapter {
    protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
    private Context context;
    private Handler uiHandler;
    /**
     * 仓库的adapter
     */
    private CheckOrderDepotAdapter[] depot_adapter;
    private List<CartShopDetail> cart_list;

    public CheckOrderAdapter(Context context, List<CartShopDetail> list,Handler uiHandler) {
        super();
        this.context = context;
        this.uiHandler = uiHandler;
        cart_list = list;
        depot_adapter = new CheckOrderDepotAdapter[cart_list.size()];
    }

    @Override
    public int getCount() {
        return cart_list.size();
    }

    @Override
    public CartShopDetail  getItem(int position) {
        return cart_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.check_order_item, null);
            viewHolder = new ViewHolder();
            depot_adapter[position] = new CheckOrderDepotAdapter(context,cart_list.get(position).getShopWareHouseList(),uiHandler);
            viewHolder.lv_check_order_depot = (ListView) convertView
                    .findViewById(R.id.lv_check_order_depot);
            viewHolder.tv_shop_name = (TextView) convertView
                    .findViewById(R.id.tv_shop_name);
            viewHolder.tv_remark = (TextView) convertView
                    .findViewById(R.id.tv_remark);
            viewHolder.et_remark = (EditText) convertView
                    .findViewById(R.id.et_remark);
            viewHolder.tv_remark.setText(mTranslatesString.getCommon_buyerremark());
            viewHolder.et_remark.setHint(mTranslatesString.getCommon_leavemessage());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_shop_name.setText(cart_list.get(position).getShopName());
        viewHolder.lv_check_order_depot.setAdapter(depot_adapter[position]);
        Utility.setListViewHeightBasedOnChildren(viewHolder.lv_check_order_depot);

        return convertView;
    }

    class ViewHolder {
        private ListView lv_check_order_depot;
        private EditText  et_remark;
        private TextView tv_shop_name,tv_remark;
    }

}
