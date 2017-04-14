package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.SettlementDetailResult;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;


/**
 * 用途：结算记录的适配器
 * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class SettledHistoryListAdapter extends XRecyclerView.Adapter<SettledHistoryListAdapter.ViewHolder> {
//    private Context context;
//    private List<DongjieBaozhengjinBean.Rows> rowsList;
//    private MobileStaticTextCode mTranslatesString;
//    public SettledHistoryListAdapter() {
//    }
//
//    /**
//     *
//     * @param context
//     * @param rowsList
//     * @param mTranslatesString
//     */
//    public SettledHistoryListAdapter(Context context, List<DongjieBaozhengjinBean.Rows> rowsList, MobileStaticTextCode mTranslatesString) {
//        this.context = context;
//        this.rowsList=rowsList;
//        this.mTranslatesString=mTranslatesString;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewHolder holder = new ViewHolder(LayoutInflater.from(
//                context).inflate(R.layout.item_settlement_list, parent,
//                false));
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return rowsList.size();
//    }
//
//    public static class ViewHolder extends XRecyclerView.ViewHolder {
//        public TextView tv_type;
//        public TextView tv_money;
//        public TextView tv_zhongjian;
//        public TextView tv_baozhengjin_xiamian;
//        public TextView tv_biandong_time;
//
//        public ViewHolder(View rootView) {
//            super(rootView);
//            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
//            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
//            this.tv_zhongjian = (TextView) rootView.findViewById(R.id.tv_zhongjian);
//            this.tv_baozhengjin_xiamian = (TextView) rootView.findViewById(R.id.tv_baozhengjin_xiamian);
//            this.tv_biandong_time = (TextView) rootView.findViewById(R.id.tv_biandong_time);
//        }
//    }
private Context context;
    private List<SettlementDetailResult.SettlementDetail> rowsList;
    private MobileStaticTextCode mTranslatesString;
    public SettledHistoryListAdapter() {
    }

    /**
     *
     * @param context
     * @param rowsList
     * @param mTranslatesString
     */
    public SettledHistoryListAdapter(Context context, List<SettlementDetailResult.SettlementDetail> rowsList, MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.rowsList=rowsList;
        this.mTranslatesString=mTranslatesString;
    }

    @Override
    public SettledHistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SettledHistoryListAdapter.ViewHolder holder = new SettledHistoryListAdapter.ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_settlement_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SettledHistoryListAdapter.ViewHolder holder, int position) {
        if (rowsList.get(position).getStatus()==0){
            //正在审核
            holder.tv_type.setText(mTranslatesString.getCommon_zhengzaishenhe());
            holder.tv_type.setTextColor(Color.parseColor("#ffc426"));
            holder.tv_zhongjian.setText(mTranslatesString.getCommon_shenqingmoney()+":Ks"+rowsList.get(position).getMoney());
            holder.tv_baozhengjin_xiamian.setText(mTranslatesString.getCommon_picinumber()+":"+rowsList.get(position).getBatchesNo());
            holder.tv_biandong_time.setText(rowsList.get(position).getModifyTime()+"");
        }
        if (rowsList.get(position).getStatus()==2){
            //部分结算
            holder.tv_baozhengjin_zui_xiamian.setVisibility(View.VISIBLE);
            holder.tv_baozhengjin_xiamian.setVisibility(View.VISIBLE);
            holder.tv_type.setTextColor(Color.parseColor("#ffc426"));
            holder.tv_zhongjian.setText(mTranslatesString.getCommon_shijisettlementmoney()+":Ks"+rowsList.get(position).getActualMoney());
            holder.tv_baozhengjin_xiamian.setText(mTranslatesString.getCommon_shenqingmoney()+":Ks"+rowsList.get(position).getMoney());
            holder.tv_baozhengjin_zui_xiamian.setText(mTranslatesString.getCommon_picinumber() + ":" + rowsList.get(position).getBatchesNo());
            holder.tv_biandong_time.setText(rowsList.get(position).getModifyTime()+"");
            if (rowsList.get(position).getActualMoney().equals("")||rowsList.get(position).getActualMoney().equals("0")){
                //如果没有申请金额，就是未结算
                holder.tv_type.setText(mTranslatesString.getCommon_weijiesuan());

            }else {
                //部分支付
                holder.tv_type.setText(mTranslatesString.getCommon_portionzhifu());
            }
        }
        if(rowsList.get(position).getStatus()==1){
            //已结算
            holder.tv_baozhengjin_xiamian.setVisibility(View.VISIBLE);
            holder.tv_biandong_time.setText(rowsList.get(position).getModifyTime());
            holder.tv_type.setText(mTranslatesString.getCommon_yijiesuan());
            holder.tv_type.setTextColor(Color.parseColor("#44B0A0"));
            holder.tv_zhongjian.setText(mTranslatesString.getCommon_settlementmoney()+"Ks:"+rowsList.get(position).getActualMoney());
            holder.tv_baozhengjin_xiamian.setText(mTranslatesString.getCommon_picinumber() + ":" + rowsList.get(position).getBatchesNo());
        }


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
        public TextView tv_baozhengjin_zui_xiamian;
        public TextView tv_biandong_time;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
            this.tv_zhongjian = (TextView) rootView.findViewById(R.id.tv_zhongjian);
            this.tv_baozhengjin_xiamian = (TextView) rootView.findViewById(R.id.tv_baozhengjin_xiamian);
            this.tv_baozhengjin_zui_xiamian = (TextView) rootView.findViewById(R.id.tv_baozhengjin_zui_xiamian);
            this.tv_biandong_time = (TextView) rootView.findViewById(R.id.tv_biandong_time);
        }
    }
}
