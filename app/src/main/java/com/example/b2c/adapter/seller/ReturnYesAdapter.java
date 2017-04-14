package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.RefundListDetail;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 卖家退货列表已处理
 * yy
 */
public class ReturnYesAdapter extends BaseAdapter<RefundListDetail> {


    private Context mContext ;
    private Map<String,String> map =null;
    public ReturnYesAdapter(Context context) {
        super(context, R.layout.item_return_yes);
        this.mContext = context;
        initMap();
    }
    private void initMap() {
        map =new HashMap<>();
        for(CellText ct: optionList.getReturnGoodStatus()){
            map.put(ct.getValue(),ct.getText());
        }
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        RefundListDetail detail = getItem(position);
         TextView tv_title_type = viewHolderFactory.findViewById(R.id.tv_title_type);
         TextView tv_code_label=viewHolderFactory.findViewById(R.id.tv_code_label);
         TextView tv_orderCode_label=viewHolderFactory.findViewById(R.id.tv_orderCode_label);
         TextView tv_refundReason_label=viewHolderFactory.findViewById(R.id.tv_refundReason_label);

         TextView tv_code=viewHolderFactory.findViewById(R.id.tv_code);
         TextView tv_orderCode=viewHolderFactory.findViewById(R.id.tv_orderCode);
         TextView tv_refundReason=viewHolderFactory.findViewById(R.id.tv_refundReason);
         ImageView iv_sample_pic=viewHolderFactory.findViewById(R.id.iv_sample_pic);
         TextView tv_sample_name=viewHolderFactory.findViewById(R.id.tv_sample_name);
         TextView tv_sample_detail=viewHolderFactory.findViewById(R.id.tv_sample_detail);
         TextView tv_sample_price=viewHolderFactory.findViewById(R.id.tv_sample_price);
         TextView tv_refundPrice_title=viewHolderFactory.findViewById(R.id.tv_refundPrice_title);
         TextView tv_refundPrice=viewHolderFactory.findViewById(R.id.tv_refundPrice);

        tv_code_label.setText(mTranslatesString.getCommon_returnbianhao());
        tv_orderCode_label.setText(mTranslatesString.getCommon_ordernumber());
        tv_refundReason_label.setText(mTranslatesString.getCommon_reason());
        tv_refundPrice_title.setText(mTranslatesString.getCommon_cash());
        if (detail != null) {
            tv_title_type.setText(map.get(detail.getRefundStatus()+""));
            tv_code.setText(detail.getCode() + "");
            tv_orderCode.setText(detail.getOrderCode());
            for(CellText cell : optionList.getRefuseReason()){
                if(cell.getValue().equals(detail.getRefundReason()+"")){
                    tv_refundReason.setText(Utils.cutNull(cell.getText()));
                    break;
                }
            }
            ImageHelper.display(ConstantS.BASE_PIC_URL+detail.getSamplePic(),iv_sample_pic);
            tv_sample_name.setText(detail.getSampleName());
            if(TextUtils.isEmpty(detail.getSampleProDetail())){
                tv_sample_detail.setText(detail.getSampleProDetail());
            }
            tv_sample_price.setText(Configs.CURRENCY_UNIT +detail.getSamplePrice()+"X"+detail.getSampleNum());
            tv_refundPrice.setText(Utils.cutNull(detail.getTotalPrice()+""));

        }
    }
}
