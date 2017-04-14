package com.example.b2c.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.Configs;
import com.example.b2c.dialog.ItemsDialogHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.response.DeliveryFeeDetail;
import com.example.b2c.net.response.ShopWareHouse;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utility;
import com.example.b2c.widget.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.b2c.R.id.siv_deliver_type;

/**
 * 确认订单仓库列表适配器
 */
public class CheckOrderDepotAdapter extends BaseAdapter {
    protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
    private Context context;
    private Handler uiHandler;
    /**
     * 仓库的adapter
     */
    private CheckOrderSampleAdapter[] sample_adapter;
    private List<ShopWareHouse> shopWareHouseList;

    public CheckOrderDepotAdapter(Context context,List<ShopWareHouse> shopWareHouseList,Handler uiHandler) {
        super();
        this.context = context;
        this.uiHandler = uiHandler;
        this.shopWareHouseList = shopWareHouseList;
        this.sample_adapter = new CheckOrderSampleAdapter[shopWareHouseList.size()];
    }

    @Override
    public int getCount() {
        return shopWareHouseList.size();
    }

    @Override
    public ShopWareHouse  getItem(int position) {
        return shopWareHouseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup group) {
        final ShopWareHouse  shopWareHouse = shopWareHouseList.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.check_order_depot_item, null);
            viewHolder = new ViewHolder();
            sample_adapter[position] = new CheckOrderSampleAdapter(context,
                    shopWareHouse.getShoppingCartList());
            viewHolder.tv_depot_name = (TextView) convertView
                    .findViewById(R.id.tv_depot_name);
            viewHolder.lv_check_order_sample = (ListView) convertView
                    .findViewById(R.id.lv_check_order_sample);
            viewHolder.siv_deliver_type = (SettingItemView2) convertView
                    .findViewById(siv_deliver_type);
            viewHolder.siv_deliver_type.setTv_text(mTranslatesString.getCommon_deliverytype());
            viewHolder.siv_deliver_type.setTv_docColor(UIHelper.getColor(R.color.check_order));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_depot_name.setText(Utils.cutNull(shopWareHouse.getProvinceName())+" "
                +Utils.cutNull(shopWareHouse.getCityName())+" "
                +Utils.cutNull(shopWareHouse.getAddress()));
        viewHolder.lv_check_order_sample.setAdapter(sample_adapter[position]);
        Utility.setListViewHeightBasedOnChildren(viewHolder.lv_check_order_sample);
        viewHolder.siv_deliver_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopWareHouseList.get(position).getDeliveryFeeList()==null||shopWareHouseList.get(position).getDeliveryFeeList().size()==0){
                    ToastHelper.makeToast(mTranslatesString.getCommon_addressnotdeliver());
                    return;
                }
                HashMap mapCk =new HashMap();
                List<HashMap<String,String>> deliveryTypes = new ArrayList<>();
                for(DeliveryFeeDetail type :shopWareHouseList.get(position).getDeliveryFeeList()){
                    if(type.isChecked()){
                        mapCk.put(type.getDeliveryCompanyId()+"",type.getDeliveryCompanyName()+"("+Configs.CURRENCY_UNIT+type.getDeliveryFee()+")");
                    }
                    HashMap<String,String>  map =new HashMap();
                    map.put(type.getDeliveryCompanyId()+"",type.getDeliveryCompanyName()+"("+Configs.CURRENCY_UNIT+type.getDeliveryFee()+")");
                    deliveryTypes.add(map);

                }
                new  ItemsDialogHelper(context,mTranslatesString.getCommon_deliverytype(),deliveryTypes,mapCk,new ItemsDialogHelper.AbstractOnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position0) {
                        initData(position);
                        shopWareHouseList.get(position).getDeliveryFeeList().get(position0).setChecked(true);
                        notifyDataSetChanged();
                        if(position>=0){
                            Message  msg = uiHandler.obtainMessage();
                            msg.what = 1;
                            uiHandler.sendMessage(msg);
                        }
                    }
                }).show();
//                new CommonListPopupWindow(context,deliveryTypes,mapCk,new CommonListPopupWindow.CommonCallBack() {
//                    @Override
//                    public void onClick(int selectItem, String value) {
////                        ToastHelper.makeToast("good");
//                        initData(position);
//                        shopWareHouseList.get(position).getDeliveryFeeList().get(selectItem).setChecked(true);
//                        notifyDataSetChanged();
//                }
//                }).show(viewHolder.siv_deliver_type);
            }
        });
        DeliveryFeeDetail check_dfd = null;
        for(DeliveryFeeDetail dfd : shopWareHouse.getDeliveryFeeList()){
            if(dfd.isChecked()){
                check_dfd = dfd;
                break;
            }
        }
        if(check_dfd != null){
            viewHolder.siv_deliver_type.setTv_doc(Configs.CURRENCY_UNIT + check_dfd.getDeliveryFee());
        }else {
            viewHolder.siv_deliver_type.setTv_doc(mTranslatesString.getCommon_pleaseselect());
        }
        return convertView;
    }

    private void initData(int position) {
        for(DeliveryFeeDetail dfd : shopWareHouseList.get(position).getDeliveryFeeList()){
                dfd.setChecked(false);
            }
    }

    class ViewHolder {
        private TextView tv_depot_name;
        private ListView lv_check_order_sample;
        private SettingItemView2 siv_deliver_type;
    }

}
