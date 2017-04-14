package com.example.b2c.adapter.livesCommunity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.livesCommunity.HomeList;

import java.util.List;

/**
 * 首页listView的适配
 */

public class HomeListAdapter extends XRcycleViewAdapterBase {
    private ImageView iv_lives_list;
    private TextView tv_list_title;
    private TextView tv_lives_time;

    public HomeListAdapter() {
    }

    private Context context;
    private List<HomeList.Rows> rows;
    public HomeListAdapter(Context context, List<HomeList.Rows> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public int getItemPostion() {
        return rows.size();
    }

    @Override
    public int getLayoutID() {
        return R.layout.item_lives_community;
    }

    @Override
    public Context getContext() {
        return context;
    }
//http://192.168.1.105/upload/goodsImage/20170323/14902511428941.JPEG
    @Override
    public void initData(MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(rows.get(position).getDefaultPic())){
            String s = ConstantS.BASE_PIC_URL + rows.get(position).getDefaultPic();
            ImageHelper.display(ConstantS.BASE_PIC_URL+rows.get(position).getDefaultPic(),iv_lives_list);
        }
        tv_list_title.setText(rows.get(position).getTitle());
        tv_lives_time.setText(rows.get(position).getCreateTime());
    }

    @Override
    public void initView(View itemView) {
         iv_lives_list= (ImageView) itemView.findViewById(R.id.iv_lives_list);
          tv_list_title= (TextView) itemView.findViewById(R.id.tv_list_title);
          tv_lives_time =(TextView) itemView.findViewById(R.id.tv_lives_time);
    }
}
