package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.MuBanBean;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 运费模板的适配
 */

public class FreightTemplateAdapter extends XRecyclerView.Adapter<FreightTemplateAdapter.ViewHolder> {

    public FreightTemplateAdapter() {
    }

    private Context context;
private MobileStaticTextCode mTranslatesString;
    private List<MuBanBean.Rows> rows;
    public FreightTemplateAdapter(Context context,List<MuBanBean.Rows> rows,MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.mTranslatesString=mTranslatesString;
        this.rows=rows;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_freight_template, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_fahuo.setText(mTranslatesString.getCommon_fahuo());
        holder.tv_shouhuo.setText(mTranslatesString.getCommon_shouhuodi());
        holder.tv_yunfei_zhongliang.setText(mTranslatesString.getCommon_zhongliang());
        holder.tv_yunfei_tianshu.setText(mTranslatesString.getCommon_tianshu());
        holder.tv_yunfei_feiyong.setText(mTranslatesString.getCommon_feiyong());
        holder.tv_freight_bianji.setText(mTranslatesString.getCommon_edit());
        holder.tv_zhongliangzhi.setText(rows.get(position).getMinWeight()+"-"+rows.get(position).getMaxWeight()+"kg");
        holder.tv_tianshuzhi.setText(rows.get(position).getDeliveryTimeCost());
        holder.tv_feiyongzhi.setText("Ks "+rows.get(position).getFee());
        holder.tv_fahuodizhi.setText(rows.get(position).getShippingCityName());
        holder.tv_shouhuodizhi.setText(rows.get(position).getReceiveCityName());

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }


    public static class ViewHolder extends XRecyclerView.ViewHolder {
        public TextView tv_fahuo;
        public TextView tv_shouhuo;
        public TextView tv_freight_bianji;
        public TextView tv_fahuodizhi;
        public TextView tv_shouhuodizhi;
        public TextView tv_yunfei_zhongliang;
        public TextView tv_zhongliangzhi;
        public TextView tv_yunfei_tianshu;
        public TextView tv_tianshuzhi;
        public TextView tv_yunfei_feiyong;
        public TextView tv_feiyongzhi;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tv_fahuo = (TextView) rootView.findViewById(R.id.tv_fahuo);
            this.tv_shouhuo = (TextView) rootView.findViewById(R.id.tv_shouhuo);
            this.tv_freight_bianji = (TextView) rootView.findViewById(R.id.tv_freight_bianji);
            this.tv_fahuodizhi = (TextView) rootView.findViewById(R.id.tv_fahuodizhi);
            this.tv_shouhuodizhi = (TextView) rootView.findViewById(R.id.tv_shouhuodizhi);
            this.tv_yunfei_zhongliang = (TextView) rootView.findViewById(R.id.tv_yunfei_zhongliang);
            this.tv_zhongliangzhi = (TextView) rootView.findViewById(R.id.tv_zhongliangzhi);
            this.tv_yunfei_tianshu = (TextView) rootView.findViewById(R.id.tv_yunfei_tianshu);
            this.tv_tianshuzhi = (TextView) rootView.findViewById(R.id.tv_tianshuzhi);
            this.tv_yunfei_feiyong = (TextView) rootView.findViewById(R.id.tv_yunfei_feiyong);
            this.tv_feiyongzhi = (TextView) rootView.findViewById(R.id.tv_feiyongzhi);
        }
    }
}
