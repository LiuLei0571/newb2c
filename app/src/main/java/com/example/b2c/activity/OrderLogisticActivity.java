package com.example.b2c.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.OrderDeliverResult;
import com.example.b2c.net.response.TimeLine;
import com.example.b2c.widget.util.Utils;

import java.util.List;

/**
 * 买家订单列表查看物流详情
 * 物流详情时光轴
 */
public class OrderLogisticActivity extends TempBaseActivity {

    private int orderId = 0;
    private OrderDeliverResult orderDeliverResult;
    private TimeLineAdapter timeLineAdapter;

    private ListView lv_trace;
    private TextView tv_delivery_name_lb;
    private TextView tv_delivery_name;
    private TextView tv_delivery_no_lb;
    private TextView tv_delivery_no;


    @Override
    public int getRootId() {
        return R.layout.activity_order_logistic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }

    private void initData() {
        showLoading();
        rdm.lookOrderLogistic(orderId);
        rdm.mOneDataListener = new OneDataListener<OrderDeliverResult>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final OrderDeliverResult data, String successInfo) {
                orderDeliverResult = data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_delivery_name.setText(Utils.cutNull(orderDeliverResult.getDeliveryName()));
                        tv_delivery_no.setText(Utils.cutNull(orderDeliverResult.getDeliveryNo()));
                        List<TimeLine> rows = data.getRows();
                        if (data.getOrderStatus() == 3) {
                            rows.add(new TimeLine(mTranslatesString.getCommon_onexpress(), ""));
                        } else if (data.getOrderStatus() == 4) {
                            rows.add(new TimeLine(mTranslatesString.getSeller_homefinish(), ""));
                        }
                        timeLineAdapter = new TimeLineAdapter(OrderLogisticActivity.this, rows);
                        lv_trace.setAdapter(timeLineAdapter);
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);

            }
        };


    }

    private void initText() {
        setTitle(mTranslatesString.getExpress_detail1());
        tv_delivery_name_lb.setText(mTranslatesString.getCommon_deliveryname());
        tv_delivery_no_lb.setText(mTranslatesString.getExpress_no());
    }

    private void initView() {
        orderId = getIntent().getIntExtra("orderId", -1);
        lv_trace = (ListView) findViewById(R.id.lv_trace);
        tv_delivery_name_lb = (TextView) findViewById(R.id.tv_delivery_name_lb);
        tv_delivery_name = (TextView) findViewById(R.id.tv_delivery_name);
        tv_delivery_no_lb = (TextView) findViewById(R.id.tv_delivery_no_lb);
        tv_delivery_no = (TextView) findViewById(R.id.tv_delivery_no);
    }

    class TimeLineAdapter extends BaseAdapter {

        private Context mcontext = null;
        private List<TimeLine> mlist = null;
        private LayoutInflater minflater;

        public TimeLineAdapter(Context context, List<TimeLine> list) {
            this.mcontext = context;
            mlist = list;
            minflater = LayoutInflater.from(mcontext);
        }

        @Override
        public int getCount() {
            if (null != mlist) {
                return mlist.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (null != mlist) {
                return mlist.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold;
            if (convertView == null) {
                viewHold = new ViewHold();
                convertView = minflater.inflate(R.layout.timeline_item, null);
                viewHold.imageView1 = (ImageView) convertView.findViewById(R.id.mgView_logistic_tracking_status);
                viewHold.textView1 = (TextView) convertView.findViewById(R.id.tv_logistic_tracking_address);
                viewHold.textView2 = (TextView) convertView.findViewById(R.id.tv_logistic_tracking_time);
                viewHold.line1 = convertView.findViewById(R.id.View_logistic_tracking_line1);
                convertView.setTag(viewHold);
            } else {
                viewHold = (ViewHold) convertView.getTag();
            }
            if (position == 0) {
                viewHold.line1.setVisibility(View.INVISIBLE);
                viewHold.imageView1.setImageResource(R.drawable.male_click);
            } else {
                viewHold.line1.setVisibility(View.VISIBLE);
                viewHold.imageView1.setImageResource(R.drawable.male_click);
            }
            viewHold.textView1.setText(mlist.get(position).getMaddress());
            viewHold.textView2.setText(mlist.get(position).getMtime());
            return convertView;
        }

        class ViewHold {
            ImageView imageView1;
            View line1;
            TextView textView1;
            TextView textView2;
        }
    }


}
