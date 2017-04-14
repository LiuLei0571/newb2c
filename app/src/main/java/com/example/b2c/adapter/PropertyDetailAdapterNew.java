package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.ProDetail;
import com.example.b2c.net.util.Logs;

import java.util.List;

public class PropertyDetailAdapterNew extends BaseAdapter {
    private Context context;
    private List<ProDetail> proDetailList;

    public PropertyDetailAdapterNew(Context context, List<ProDetail> proDetailList) {
        super();
        this.context = context;
        this.proDetailList = proDetailList;
    }

    @Override
    public int getCount() {
        return proDetailList.size();
    }

    @Override
    public ProDetail getItem(int arg0) {
        return proDetailList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup group) {
        final ViewHolder viewHolder;
        final ProDetail proDetail= getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.property_detail_item,
                    null);
            viewHolder.ll_property = (LinearLayout) convertView
                    .findViewById(R.id.ll_property);
            viewHolder.cb_property_label = (CheckBox) convertView
                    .findViewById(R.id.cb_property_label);
            viewHolder.cb_property_name = (TextView) convertView
                    .findViewById(R.id.cb_property_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Logs.d("cb_property_label",proDetail.isChecked()+"");
        viewHolder.cb_property_label.setChecked(proDetail.isChecked());
        viewHolder.cb_property_name.setText(proDetail.getName());
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_property;
        CheckBox cb_property_label;
        TextView cb_property_name;
    }

}
