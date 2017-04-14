package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.logistic.SettleOrderListActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.logistics.DataItem;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 用途：
 * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class RelevanceAdapter extends XRecyclerView.Adapter<RelevanceAdapter.MyViewHolder> {
    public RelevanceAdapter(){}
    private Context context;
    public RelevanceAdapter(Context context){
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LinearLayout.inflate(context,R.layout.item_child_account,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(View view)
        {
            super(view);
        }
    }

}
