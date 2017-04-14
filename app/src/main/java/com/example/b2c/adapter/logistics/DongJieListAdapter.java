package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.DongjieBaozhengjinBean;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;


/**
 * 用途：待结算订单的适配
 * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class DongJieListAdapter extends XRecyclerView.Adapter<DongJieListAdapter.ViewHolder> {
    private Context context;
    private List<DongjieBaozhengjinBean.Rows> rowsList;
    private MobileStaticTextCode mTranslatesString;
    public DongJieListAdapter() {
    }

    /**
     *
     * @param context
     * @param rowsList
     * @param mTranslatesString
     */
    public DongJieListAdapter(Context context, List<DongjieBaozhengjinBean.Rows> rowsList, MobileStaticTextCode mTranslatesString) {
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

            //保证金变动记录
            if (rowsList.get(position).getType() == 0) {
                //type是追加
                holder.tv_type.setTextColor(Color.parseColor("#44B0A0"));
                holder.tv_type.setText(mTranslatesString.getCommon_dongjie());
            } else {
                holder.tv_type.setTextColor(Color.parseColor("#ffc426"));
                holder.tv_type.setText(mTranslatesString.getCommon_jiedong());
            }
            holder.tv_baozhengjin_xiamian.setVisibility(View.GONE);
            holder.tv_baozhengjin_zui_xiamian.setVisibility(View.GONE);
        if (TextUtils.isEmpty(rowsList.get(position).getRemark())){
            holder.tv_zhongjian.setVisibility(View.GONE);
        }else {
            holder.tv_zhongjian.setText(rowsList.get(position).getRemark());
        }
            holder.tv_money.setText("Ks"+rowsList.get(position).getMoney());
            holder.tv_biandong_time.setText(rowsList.get(position).getCreateTime());

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
        public TextView tv_baozhengjin_zui_xiamian;

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
