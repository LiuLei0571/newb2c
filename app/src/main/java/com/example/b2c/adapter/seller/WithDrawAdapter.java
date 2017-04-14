package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.WithdrawDetail;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class WithDrawAdapter extends BaseAdapter<WithdrawDetail> {

    public WithDrawAdapter(Context context) {
        super(context, R.layout.item_withdraw);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        WithdrawDetail withdrawDetail = getItem(position);
        TextView mTvStatus = viewHolderFactory.findViewById(R.id.tv_status);
        TextView mTvTime = viewHolderFactory.findViewById(R.id.tv_time);
        TextView mTvMoney = viewHolderFactory.findViewById(R.id.tv_money);
        if (withdrawDetail != null) {
            mTvMoney.setText(withdrawDetail.getMoney() + mTranslatesString.getCommo_momeny());
            if (withdrawDetail.getStatus() == 0) {
                mTvStatus.setText("待审核");
            } else if (withdrawDetail.getStatus() == 1) {
                mTvStatus.setText("提现成功");
            }
            mTvTime.setText(withdrawDetail.getCreateTime());
        }
    }
}
