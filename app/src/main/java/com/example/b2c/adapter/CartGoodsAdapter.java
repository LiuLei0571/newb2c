package com.example.b2c.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.CartShopSample;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.NumberUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

/**
 * 购物车店铺下商品层适配器
 */
public class CartGoodsAdapter extends BaseAdapter {
    public Context context;
    private Handler mHandler;
    public HashMap<Integer, Boolean> isSelected;
    private List<CartShopSample> cartShopSamples;
    public static boolean ADD = true, REDUCE = false;
    private ImageLoader loader;

    public CartGoodsAdapter(Context context,
                            List<CartShopSample> ShoppingCartList, Handler mHandler) {
        super();
        this.context = context;
        this.cartShopSamples = ShoppingCartList;
        this.mHandler = mHandler;
        isSelected = new HashMap<>();
        loader = ImageLoader.getInstance();
        initData();
    }

    public void initData() {
        for (int i = 0; i < cartShopSamples.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int getCount() {
        return cartShopSamples.size();
    }

    @Override
    public Object getItem(int position) {
        return cartShopSamples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.cart_goods_item, null);
            viewHolder.btn_add = (Button) convertView
                    .findViewById(R.id.btn_add);
            viewHolder.btn_reduce = (Button) convertView
                    .findViewById(R.id.btn_reduce);
            viewHolder.et_num = (EditText) convertView
                    .findViewById(R.id.et_goods_num);
            viewHolder.cb_select = (CheckBox) convertView
                    .findViewById(R.id.cb_select);
            viewHolder.tv_price = (TextView) convertView
                    .findViewById(R.id.tv_goods_price);
            viewHolder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_goods_name);
            viewHolder.tv_property = (TextView) convertView
                    .findViewById(R.id.tv_goods_property);
            viewHolder.iv_pic = (ImageView) convertView
                    .findViewById(R.id.iv_goods_pic);
            viewHolder.iv_pic.setOnClickListener(new ToSampleDetailListener(
                    position));
            viewHolder.btn_add.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    int num = Integer.valueOf(viewHolder.et_num.getText()
                            .toString());
                    num++;
                    viewHolder.et_num.setText("" + num);
                    cartShopSamples.get(position).setSampleNum(num);
                    if (cartShopSamples.get(position).isChecked()) {
                        mHandler.sendMessage(mHandler.obtainMessage(10,
                                addTotalPrice(position, ADD)));
                    }
                }
            });

            viewHolder.btn_reduce.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    int num = Integer.valueOf(viewHolder.et_num.getText().toString());
                    if (num > 1) {
                        num--;
                        viewHolder.et_num.setText("" + num);
                        cartShopSamples.get(position).setSampleNum(num);
                        if (cartShopSamples.get(position).isChecked()) {
                            mHandler.sendMessage(mHandler.obtainMessage(10,
                                    addTotalPrice(position, REDUCE)));
                        }
                    }
                }
            });
            viewHolder.cb_select.setOnCheckedChangeListener(new SampleSelectListener(position));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_price.setText(Configs.CURRENCY_UNIT
                + cartShopSamples.get(position).getSamplePrice());
        viewHolder.et_num.setText(cartShopSamples.get(position).getSampleNum() + "");
        viewHolder.tv_name.setText(cartShopSamples.get(position)
                .getSampleName());
        loader.displayImage(
                HttpClientUtil.BASE_PIC_URL
                        + cartShopSamples.get(position).getSamplePic(),
                viewHolder.iv_pic);
        if (cartShopSamples.get(position).getProDetail() != null)
            viewHolder.tv_property.setText(cartShopSamples.get(position)
                    .getProDetail());
        else
            viewHolder.tv_property.setVisibility(View.GONE);
        viewHolder.cb_select.setChecked(getIsSelected().get(position));

        return convertView;
    }

    class ViewHolder {
        public Button btn_add;
        public Button btn_reduce;
        public EditText et_num;
        public CheckBox cb_select;
        public TextView tv_price;
        public TextView tv_name, tv_property;
        public ImageView iv_pic;
    }

    /**
     * 计算选中商品的金额
     *
     * @return 返回需要付费的总金额
     */
    private double getTotalPrice(int position) {
        CartShopSample node = cartShopSamples.get(position);
        double totalPrice = 0;
        if (node.isChecked()) {
            totalPrice += node.getSampleNum() * (NumberUtil.GetDoubleValue(node.getSamplePrice()));
        } else {
            totalPrice += node.getSampleNum() * (NumberUtil.GetDoubleValue(node.getSamplePrice()) * (-1));
        }
        return totalPrice;
    }

    // 加减按钮进行价格的增减
    private double addTotalPrice(int position, boolean a_or_r) {
        double addPrice = 0;
        if (a_or_r) {
            addPrice = NumberUtil.GetDoubleValue(cartShopSamples.get(position).getSamplePrice());
        } else {
            addPrice = NumberUtil.GetDoubleValue(cartShopSamples.get(position).getSamplePrice()) * (-1);
        }
        return addPrice;

    }

    /**
     * 判断是否购物车中所有的商品全部被选中
     *
     * @return true所有条目全部被选中 false还有条目没有被选中
     */
    private boolean isAllSelected() {
        boolean flag = true;
        for (int i = 0; i < cartShopSamples.size(); i++) {
            if (!getIsSelected().get(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    class SampleSelectListener implements OnCheckedChangeListener {
        private int position;

        public SampleSelectListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean checked) {
            getIsSelected().put(position, checked);
            cartShopSamples.get(position).setChecked(checked);
            mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice(position)));
        }

    }

    class ToSampleDetailListener implements OnClickListener {
        private int position;

        public ToSampleDetailListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View arg0) {
            int sampleId = cartShopSamples.get(position).getSampleId();
            mHandler.sendMessage(mHandler.obtainMessage(60, sampleId));

        }

    }
}
