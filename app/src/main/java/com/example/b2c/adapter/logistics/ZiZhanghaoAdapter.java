package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.net.response.logistics.AccountItemResult;

import java.util.List;

/**
 * 测试的适配器
 */

public class ZiZhanghaoAdapter extends XRcycleViewAdapterBase {
    public TextView tv_username;
    public LinearLayout ll_item;
    public ZiZhanghaoAdapter() {
    }

    private Context context;
    private List<AccountItemResult.AccountItem> accountItemResult;
    public ZiZhanghaoAdapter(Context context, List<AccountItemResult.AccountItem> accountItemResult) {
        this.context = context;
        this.accountItemResult=accountItemResult;
    }
    @Override
    public int getItemPostion() {
        return accountItemResult.size();
    }

    @Override
    public int getLayoutID() {
        return R.layout.item_child_account;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initData(MyViewHolder holder, int position) {
        String s = accountItemResult.get(position).getFirstName() + accountItemResult.get(position).getLastName();
        tv_username.setText(s);
    }

    @Override
    public void initView(View itemView) {
        this.tv_username = (TextView) itemView.findViewById(R.id.tv_child_username);
        this.ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
    }
}
