package com.example.b2c.adapter.livesCommunity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.livesCommunity.HomeLeiMu;

import java.util.List;

/**
 * Created by ThinkPad on 2017/3/22.
 */

public class LeiMuGridViewAdapter extends BaseAdapter{
    private Context context;
    private List<HomeLeiMu.Rows>rows;
    public LeiMuGridViewAdapter(Context context, List<HomeLeiMu.Rows> rows){
        this.context=context;
        this.rows=rows;
    }
    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return rows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null || convertView.getTag() == null){
            convertView = View.inflate(context,R.layout.item_home_lives_grid,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.iv_lives_icon);
            holder.textView = (TextView)convertView.findViewById(R.id.tv_lives_name);
            convertView.setTag(holder);
            }else{
            holder = (ViewHolder)convertView.getTag();
            }
        ImageHelper.display(ConstantS.BASE_PIC_URL+rows.get(i).getCategoryPicPath(), holder.imageView);
        holder.textView.setText(rows.get(i).getCategoryName());
//        AppInfo appInfo = listInfo.get(position);
//        holder.imageView.setImageResource(appInfo.rid);
//        holder.textView.setText(appInfo.title);
        return convertView;
    }
    public class ViewHolder{
        ImageView imageView;
        TextView textView;
        }
}
