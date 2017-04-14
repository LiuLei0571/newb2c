package com.example.b2c.adapter.seller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.translate.CellText;

import java.util.List;

/**
 * Created by yy on 2017/1/25.
 */

public class CommonItemAdapter extends BaseAdapter {

    Context context;
    List<CellText> lists;

    public CommonItemAdapter(Context context, List<CellText> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public CellText getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new  ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.common_item, null, false);
            holder.selected = (ImageView) convertView.findViewById(R.id.iv_selected);
            holder.noSelected = (ImageView) convertView.findViewById(R.id.iv_no_selected);
            holder.itemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder)convertView.getTag();
        }
        CellText temp = getItem(position);
        holder.itemName.setText(temp.getText());
        if (temp.isChecked() == true) {
            holder.selected.setVisibility(View.VISIBLE);
            holder.noSelected.setVisibility(View.INVISIBLE);
        } else {
            holder.selected.setVisibility(View.INVISIBLE);
            holder.noSelected.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView itemName;
        public ImageView selected;
        public ImageView noSelected;
    }
}
