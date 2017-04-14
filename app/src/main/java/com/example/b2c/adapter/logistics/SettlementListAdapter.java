package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.BaozhengjinBandong;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 用途：待结算订单的适配
 * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class SettlementListAdapter extends XRecyclerView.Adapter<SettlementListAdapter.ViewHolder> {
    private Context context;
    private List<BaozhengjinBandong.Rows> rowsList;
    private MobileStaticTextCode mTranslatesString;
    public SettlementListAdapter() {
    }

    public SettlementListAdapter(Context context,List<BaozhengjinBandong.Rows> rowsList,MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.rowsList=rowsList;
        this.mTranslatesString=mTranslatesString;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_settlement_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (rowsList.get(position).getType()==1){
            //type是追加
            holder.tv_type.setText(mTranslatesString.getCommon_additionall());
        }else{
            holder.tv_type.setText(mTranslatesString.getCommon_kouchu());
        }
        if (rowsList.get(position).getStatus()==0){
            //待审核
            holder.tv_zhongjian.setText(mTranslatesString.getCommon_daishenhe());
        }else if (rowsList.get(position).getStatus()==1){
            //审核通过
            holder.tv_zhongjian.setText(mTranslatesString.getCommo_shenheyes());
        }else{
            //不通过
            holder.tv_zhongjian.setText(mTranslatesString.getCommon_shenheno());
            holder.tv_baozhengjin_xiamian.setVisibility(View.VISIBLE);
            holder.tv_baozhengjin_xiamian.setText(rowsList.get(position).getAuditFeedback());
        }
        holder.tv_money.setText("Ks:"+rowsList.get(position).getMoney());
        String ss=rowsList.get(position).getCreateTime();
        holder.tv_biandong_time.setText(ss);

    }

    @Override
    public int getItemCount() {
        return rowsList.size();
    }

    public static class ViewHolder extends XRecyclerView.ViewHolder {
        public TextView tv_type;
        public TextView tv_money;
        public TextView tv_zhongjian;
        public TextView tv_baozhengjin_xiamian;
        public TextView tv_biandong_time;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
            this.tv_zhongjian = (TextView) rootView.findViewById(R.id.tv_zhongjian);
            this.tv_baozhengjin_xiamian = (TextView) rootView.findViewById(R.id.tv_baozhengjin_xiamian);
            this.tv_biandong_time = (TextView) rootView.findViewById(R.id.tv_biandong_time);
        }
    }
}
