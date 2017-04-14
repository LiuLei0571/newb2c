package com.example.b2c.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.ProDetail;
import com.example.b2c.net.response.SamplePro;
import com.example.b2c.net.util.Logs;

import java.util.List;

public class PropertyItemAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private Context context;
    private List<SamplePro> pro_list;
    private Handler handler;

    private String proIds = "";
    private String prodIds = "";

    public PropertyItemAdapter(Context context, final List<SamplePro> pro_list, Handler handler) {
        super();
        this.context = context;
        this.pro_list = pro_list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return pro_list.size();
    }

    @Override
    public SamplePro getItem(int arg0) {
        return pro_list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup group) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pop_property_item, null);
            viewHolder = new ViewHolder();
            viewHolder.lv_property_detail = (GridView) convertView.findViewById(R.id.gv_property_detail);
            viewHolder.tv_property_name = (TextView) convertView.findViewById(R.id.tv_property_type_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_property_name.setText(pro_list.get(position).getProName());
        final PropertyDetailAdapterNew adapter = new PropertyDetailAdapterNew(context, pro_list.get(position).getProDetailList());
        viewHolder.lv_property_detail.setAdapter(adapter);
        viewHolder.lv_property_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int sposition, long id) {
                List<ProDetail> proDetailList = pro_list.get(position).getProDetailList();
                for (ProDetail proDetail : proDetailList) {
                    proDetail.setChecked(false);
                }
                proDetailList.get(sposition).setChecked(true);
                adapter.notifyDataSetChanged();
                boolean bool = canHandle();
                if(bool){
                    Message message =handler.obtainMessage();
                    Bundle bundle = message.getData();
                    Logs.d("after proIds",proIds);
                    Logs.d("after prodIds",prodIds);
                    bundle.putString("proIds",proIds);
                    bundle.putString("prodIds",prodIds);
                    message.what =1077;
                    handler.sendMessage(message);
                }
            }
        });
        return convertView;
    }

    private boolean canHandle() {
        boolean bool = true;
        proIds = "" ;
        prodIds = "" ;
        for (SamplePro sp : pro_list) {
            proIds += sp.getProId()+",";
            boolean flag = false;
            for (ProDetail pd : sp.getProDetailList()) {
                if(pd.isChecked()){
                    flag = true;
                    prodIds += pd.getProDetailId()+",";
                    break;
                }
            }
            if(!flag){
                bool = false;
                break;
            }
        }
        Logs.d("before proIds",proIds);
        Logs.d("before prodIds",prodIds);
        if(proIds.length()>0){
            proIds = proIds.substring(0,proIds.lastIndexOf(","));
        }
        if(prodIds.length()>0){
            prodIds = prodIds.substring(0,prodIds.lastIndexOf(","));
        }

        return bool;
    }

    class ViewHolder {
        GridView lv_property_detail;
        TextView tv_property_name;
    }
}
