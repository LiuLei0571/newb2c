package com.example.b2c.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.BuyerOrderDetail;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.widget.util.Utility;

import java.util.HashMap;
import java.util.Map;


/**
 * 订单评价
 */
public class OrderEvaluateNewActivity extends TempBaseActivity implements View.OnClickListener {

    private ListView ls_evaluate;
    private TextView tv_shop_evaluate;
    private TextView tv_evaluate_description;
    private Button btn_description_reduce;
    private EditText et_description;
    private Button btn_description_add;
    private TextView tv_evaluate_delivery_speed;
    private Button btn_delivery_reduce;
    private EditText et_delivery;
    private Button btn_delivery_add;
    private TextView tv_evaluate_service_attitude;
    private Button btn_attitude_reduce;
    private EditText et_attitude_num;
    private Button btn_attitude_add;
    private Button btn_submit;

    /**
     * 订单实体类
     */
    private BuyerOrderList odr;

    private OrderEvaluateAdapter orderEvaluateAdapter;

    @Override
    public int getRootId() {
        return R.layout.order_evaluate_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_samplecomment());

        tv_shop_evaluate.setText(mTranslatesString.getCommon_evaluategradle());
        tv_evaluate_description.setText(mTranslatesString
                .getCommon_consitancerate());
        tv_evaluate_delivery_speed.setText(mTranslatesString
                .getCommon_deliveryspeed());
        tv_evaluate_service_attitude.setText(mTranslatesString
                .getCommon_serviceatitude());
        btn_submit.setText(mTranslatesString
                .getCommon_submit());
    }

    private void initView() {
        odr = (BuyerOrderList) getIntent().getSerializableExtra("object");
        orderEvaluateAdapter = new OrderEvaluateAdapter(OrderEvaluateNewActivity.this);
        ls_evaluate = (ListView) findViewById(R.id.ls_evaluate);
        ls_evaluate.setAdapter(orderEvaluateAdapter);
        orderEvaluateAdapter.setData(odr.getOrderDetailList());
        Utility.setListViewHeightBasedOnChildren(ls_evaluate);

        tv_shop_evaluate = (TextView) findViewById(R.id.tv_shop_evaluate);
        tv_evaluate_description = (TextView) findViewById(R.id.tv_evaluate_description);
        btn_description_reduce = (Button) findViewById(R.id.btn_description_reduce);
        et_description = (EditText) findViewById(R.id.et_description);
        btn_description_add = (Button) findViewById(R.id.btn_description_add);
        tv_evaluate_delivery_speed = (TextView) findViewById(R.id.tv_evaluate_delivery_speed);
        btn_delivery_reduce = (Button) findViewById(R.id.btn_delivery_reduce);
        et_delivery = (EditText) findViewById(R.id.et_delivery);
        btn_delivery_add = (Button) findViewById(R.id.btn_delivery_add);
        tv_evaluate_service_attitude = (TextView) findViewById(R.id.tv_evaluate_service_attitude);
        btn_attitude_reduce = (Button) findViewById(R.id.btn_attitude_reduce);
        et_attitude_num = (EditText) findViewById(R.id.et_attitude_num);
        btn_attitude_add = (Button) findViewById(R.id.btn_attitude_add);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_description_reduce.setOnClickListener(this);
        btn_description_add.setOnClickListener(this);
        btn_delivery_reduce.setOnClickListener(this);
        btn_delivery_add.setOnClickListener(this);
        btn_attitude_reduce.setOnClickListener(this);
        btn_attitude_add.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int num ;
        switch (v.getId()) {
            case R.id.btn_description_add:
                num = Integer.valueOf(et_description.getText().toString());
                if (num < 5) {
                    num++;
                }
                et_description.setText(num + "");
                break;
            case R.id.btn_description_reduce:
                num = Integer.valueOf(et_description.getText().toString());
                if (num > 1) {
                    num--;
                }
                et_description.setText(num + "");
                break;
            case R.id.btn_attitude_add:
                num = Integer.valueOf(et_attitude_num.getText().toString());
                if (num < 5) {
                    num++;
                }
                et_attitude_num.setText(num + "");
                break;
            case R.id.btn_attitude_reduce:
                num = Integer.valueOf(et_attitude_num.getText().toString());
                if (num > 1) {
                    num--;
                }
                et_attitude_num.setText(num + "");
                break;
            case R.id.btn_delivery_add:
                num = Integer.valueOf(et_delivery.getText().toString());
                if (num < 5) {
                    num++;
                }
                et_delivery.setText(num + "");
                break;
            case R.id.btn_delivery_reduce:
                num = Integer.valueOf(et_delivery.getText().toString());
                if (num > 1) {
                    num--;
                }
                et_delivery.setText(num + "");
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        Map map = new HashMap();
        map.put("orderId",odr.getOrderId());
        JSONArray orderInfo = new JSONArray();
        for (int i = 0; i < odr.getOrderDetailList().size(); i++) {
                JSONObject jsonObject = new JSONObject();
                BuyerOrderDetail detail = odr.getOrderDetailList().get(i);
                jsonObject.put("sampleId",detail.getSampleId());
                jsonObject.put("orderDetailId",detail.getOrderDetailId());
                LinearLayout evaluate_item_layout = (LinearLayout) ls_evaluate.getChildAt(i);
                TextView et_evaluate = (TextView) evaluate_item_layout.findViewById(R.id.et_evaluate);
                String content = et_evaluate.getText().toString();
                jsonObject.put("content",content);
                RadioGroup rg_evaluate = (RadioGroup) evaluate_item_layout.findViewById(R.id.rg_evaluate);
                int radioButtonId = rg_evaluate.getCheckedRadioButtonId();
                switch (radioButtonId){
                    case R.id.rb_good_evaluate:
                        jsonObject.put("type",1);
                        break;
                    case R.id.rb_normal_evaluate:
                        jsonObject.put("type",2);
                        break;
                    case R.id.rb_bad_evaluate:
                        jsonObject.put("type",3);
                        break;
                }
                orderInfo.add(jsonObject);
        }
        map.put("rows",orderInfo.toString());
        map.put("consistentDescription",et_description.getText().toString());
        map.put("serviceAttitude",et_attitude_num.getText().toString());
        map.put("deliverySpeed",et_delivery.getText().toString());
        showLoading();
        rdm.SaveSampleEvaluation(map);
        rdm.responseListener = new ResponseListener() {
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

    class OrderEvaluateAdapter extends BaseAdapter<BuyerOrderDetail> {

        public OrderEvaluateAdapter(Context context) {
            super(context, R.layout.order_evaluate_item);
        }

        @Override
        public void renderView(ViewHolderFactory viewHolderFactory, int position) {
            BuyerOrderDetail od = getItem(position);

            EditText et_evaluate = viewHolderFactory.findViewById(R.id.et_evaluate);
            ImageView iv_sample_pic = viewHolderFactory.findViewById(R.id.iv_sample_pic);
            TextView tv_sample_name = viewHolderFactory.findViewById(R.id.tv_sample_name);

            RadioGroup rg_evaluate = viewHolderFactory.findViewById(R.id.rg_evaluate);
            RadioButton rb_good_evaluate = viewHolderFactory.findViewById(R.id.rb_good_evaluate);
            RadioButton rb_normal_evaluate = viewHolderFactory.findViewById(R.id.rb_normal_evaluate);
            RadioButton rb_bad_evaluate = viewHolderFactory.findViewById(R.id.rb_bad_evaluate);

            et_evaluate.setHint(mTranslatesString.getCommon_inputyourcomment());
            rb_good_evaluate.setText(mTranslatesString.getCommon_goodcomment());
            rb_normal_evaluate.setText(mTranslatesString.getCommon_normalcomment());
            rb_bad_evaluate.setText(mTranslatesString.getCommon_badcomment());

            if (od != null) {
                tv_sample_name.setText(od.getSampleName());
                loader.displayImage(ConstantS.BASE_PIC_URL + od.getSampleImage(), iv_sample_pic, options);
            }
            rg_evaluate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                }
            });
        }
    }

}
