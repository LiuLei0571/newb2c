package com.example.b2c.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.MyItemsDialogHelper;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.BuyerOrderDetail;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utility;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 * 申请退货
 */
public class ReturnGoodActivity extends TempBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private BuyerOrderList order;
    private EditTextWithDelete et_text;
    private Button btn_submit;
    private RefundListAdapter refundListAdapter;
    private ListView lv_items;

    private  SettingItemView2 siv_refundReason;

    private int detailId = -1;
    private FrameLayout fl_picEvidence;
    private TextView tv_pic_title;
    private ImageView iv_pic;


    private List<CellText> returnReasons;

    //退货原因
    private String  refundReason = null;
    //退款凭证
    private PickImageDialog mPickImage;
    private String  picEvidence = null;

    @Override
    public int getRootId() {
        return R.layout.activity_return_good;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initView() {
        order = (BuyerOrderList) getIntent().getSerializableExtra("order");
        et_text = (EditTextWithDelete) findViewById(R.id.et_text);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        siv_refundReason = (SettingItemView2) findViewById(R.id.siv_refundReason);
        fl_picEvidence = (FrameLayout) findViewById(R.id.fl_picEvidence);
        tv_pic_title = (TextView) findViewById(R.id.tv_pic_title);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);

        fl_picEvidence.setOnClickListener(this);
        siv_refundReason.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        lv_items = (ListView) findViewById(R.id.lv_items);
        refundListAdapter =new RefundListAdapter(this,order.getOrderDetailList(),detailId);
        lv_items.setAdapter(refundListAdapter);
        Utility.setListViewHeightBasedOnChildren(lv_items);
        lv_items.setOnItemClickListener(this);
        returnReasons = mTranslatesStringList.getRefuseReason();

    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_applybackgood());
        et_text.setHint(mTranslatesString.getCommon_returnshuoming());
        btn_submit.setText(mTranslatesString.getCommon_submitapply());
        tv_pic_title.setText(mTranslatesString.getCommon_uppicevidence());
        siv_refundReason.setTv_doc(mTranslatesString.getCommon_pleaseselect());
        siv_refundReason.setTv_text(mTranslatesString.getCommon_returnreason());
    }

    //提交退货申请
    private void submit() {
        if(detailId <= 0 ){
            ToastHelper.makeToast(mTranslatesString.getCommon_selectgoodshouldback());
            return;
        }
        String text = et_text.getText().toString().trim();
        if (TextUtils.isBlank(text)) {
            ToastHelper.makeToast(mTranslatesString.getCommon_reasonnotnull());
            return;
        }
        if(TextUtils.isBlank(refundReason)){
            ToastHelper.makeToast(mTranslatesString.getCommon_selectrreason());
            return;
        }
        if(TextUtils.isBlank(picEvidence)){
            ToastHelper.makeToast(mTranslatesString.getCommon_uploadpic());
            return;
        }
        Map map = new HashMap();
        map.put("orderDetailId", detailId);
        map.put("picEvidence", picEvidence);
        map.put("refundReason", refundReason);
        map.put("refundExplain", text.toString());
        showLoading();
        rdm.returnGoods(map);
        rdm.mResponseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                finish();
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_picEvidence:
                upLoadPic(R.id.iv_pic);
                break;
            case R.id.siv_refundReason:
                popRefundReason();
                break;
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }

    }

    /**
     * 上传退货凭证
     */
    private void upLoadPic(int id) {
            if (mPickImage != null) {
                mPickImage.destory();
            }
            mPickImage = new PickImageDialog(this, id, mLogisticsDataConnection) {
                @Override
                protected void onImageUpLoad(final  int id, final String url, String url1) {
                    Log.d("onImageUpLoad url", url);
                    Log.d("onImageUpLoad url1", url1);
                    picEvidence = url1;
                    UIHelper.displayImage((ImageView) ReturnGoodActivity.this.findViewById(id), url);
                }
            };
            mPickImage.show();
    }

    /**
     * 选择退货原因
     */
    private void popRefundReason() {
        new MyItemsDialogHelper(this,mTranslatesString.getCommon_returnreason(),returnReasons,new MyItemsDialogHelper.AbstractOnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position) {
                doItemClick(position);
            }
        }).show();
    }

    /**
     * 选择退货原因回调
     * @param position
     */
    private void doItemClick(final int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refundReason = returnReasons.get(position).getValue();
                siv_refundReason.setTv_doc(returnReasons.get(position).getText());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        detailId = order.getOrderDetailList().get(position).getOrderDetailId();
        refundListAdapter.setSelectId(detailId);
        refundListAdapter.notifyDataSetChanged();
    }

    class RefundListAdapter extends BaseAdapter {
        Context context;
        List<BuyerOrderDetail> details;
        int selectId;

        public void setSelectId(int selectId) {
            this.selectId = selectId;
        }

        public RefundListAdapter(Context context, List<BuyerOrderDetail> details, int selectId) {
            this.context = context;
            this.details = details;
            this.selectId = selectId;
        }

        @Override
        public int getCount() {
            return details.size();
        }

        @Override
        public BuyerOrderDetail getItem(int position) {
            return details.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_items_common_item, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                holder.imgView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            BuyerOrderDetail detail = getItem(position);
            if (detail.getOrderDetailId() == selectId) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.main_green));
                holder.imgView.setVisibility(View.VISIBLE);
            } else {
                holder.textView.setTextColor(context.getResources().getColor(R.color.text_dark));
                holder.imgView.setVisibility(View.GONE);
            }
            holder.textView.setText(getItem(position).getSampleName()+" "+getItem(position).getSampleProperty());
            return convertView;
        }
        class ViewHolder {
            public TextView textView;
            public ImageView imgView;
        }
    }
}
