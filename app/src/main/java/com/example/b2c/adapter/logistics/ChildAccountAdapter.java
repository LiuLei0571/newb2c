package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.listener.logistic.XRecycliViewClick;
import com.example.b2c.net.response.logistics.AccountItemResult;
import com.example.b2c.net.response.seller.AccountItem;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 */

public class ChildAccountAdapter extends XRecyclerView.Adapter<ChildAccountAdapter.MyViewHolder> {

    public ChildAccountAdapter() {
    }

    private Context context;
    private List<AccountItem> accountItemResult;
    public ChildAccountAdapter(Context context, List<AccountItem> accountItemResult) {
        this.context = context;
        this.accountItemResult=accountItemResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_child_account, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String s = accountItemResult.get(position).getFirstName() + accountItemResult.get(position).getLastName();
        holder.tv_username.setText(s);
    }


    @Override
    public int getItemCount() {
        return accountItemResult.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_username;
        public LinearLayout ll_item;
        public MyViewHolder(View view) {
            super(view);
            this.tv_username = (TextView) view.findViewById(R.id.tv_child_username);
            this.ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }
}
