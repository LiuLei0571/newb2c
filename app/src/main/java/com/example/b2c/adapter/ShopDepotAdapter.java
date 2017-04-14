package com.example.b2c.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.ShopDepotResult;
import com.example.b2c.widget.util.Utils;

import java.util.List;

/**
 * 商品详情 选择仓库适配器
 * Created by yy on 2017/2/9.
 */

public class ShopDepotAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<ShopDepotResult> ls;

    public ShopDepotAdapter(Context context, List<ShopDepotResult> ls) {
        this.context = context;
        this.ls = ls;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public ShopDepotResult getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.gridview_depot_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.ItemText);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShopDepotResult bean = ls.get(position);
        if (bean.isChecked()) {
            holder.layout.setBackgroundResource(R.drawable.bg_select_shape);
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.layout.setBackgroundResource(R.drawable.bg_un_select_shape);
            holder.title.setTextColor(Color.GRAY);
        }
        holder.title.setText(Utils.cutNull(bean.getProvinceName())
                + Utils.cutNull(bean.getCityName())
                + Utils.cutNull(bean.getAddress()));
        return convertView;

    }

    public final class ViewHolder {
        public TextView title;
        public LinearLayout layout;
    }

    public void setLs(List<ShopDepotResult> ls) {
        this.ls = ls;
    }

}
