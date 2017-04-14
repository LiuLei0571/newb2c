package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.ShoppingCartDetail;
import com.example.b2c.net.util.HttpClientUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 确认订单产品适配器
 */
public class CheckOrderSampleAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder viewHolder;
    private List<ShoppingCartDetail> ShoppingCartList;
    private ImageLoader loader;

    public CheckOrderSampleAdapter(Context context,
                                   List<ShoppingCartDetail> list) {
        super();
        this.context = context;
        ShoppingCartList = list;
        loader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ShoppingCartList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.check_order_sample_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_sample_pic = (ImageView) convertView
                    .findViewById(R.id.iv_check_order_sample_item_pic);
            viewHolder.tv_sample_name = (TextView) convertView
                    .findViewById(R.id.tv_check_order_sample_item_name);
            viewHolder.tv_sample_price = (TextView) convertView
                    .findViewById(R.id.tv_check_order_sample_item_price);
            viewHolder.tv_sample_num = (TextView) convertView
                    .findViewById(R.id.tv_check_order_sample_item_num);
            viewHolder.tv_sample_property = (TextView) convertView
                    .findViewById(R.id.tv_check_order_sample_item_property);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_sample_name.setText(ShoppingCartList.get(position)
                .getSampleName());
        viewHolder.tv_sample_num.setText("x "
                + ShoppingCartList.get(position).getSampleNum());
        viewHolder.tv_sample_price.setText(Configs.CURRENCY_UNIT
                + ShoppingCartList.get(position).getSamplePrice());
        viewHolder.tv_sample_property.setText(ShoppingCartList.get(position)
                .getProDetail());
        loader.displayImage(
                HttpClientUtil.BASE_PIC_URL
                        + ShoppingCartList.get(position).getSamplePic(),
                viewHolder.iv_sample_pic);
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_sample_pic;
        public TextView tv_sample_name, tv_sample_price, tv_sample_num,
                tv_sample_property;
    }
}
