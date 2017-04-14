package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 结算中心
 */
public class SettleAccountsCenterActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView kj1;
    private TextView toolbar_right_text;
    private Button btn_shenqing;
    private TextView tv_baozhengjin;
    private TextView tv_dongjie;
    private TextView tv_dongjie_title;
    private TextView tv_keyong;
    private TextView tv_keyong_title;
    private LinearLayout tv_daijiesuan;
    private LinearLayout tv_yuqijiesuan;
    private LinearLayout tv_yijiesuan;
    private JSONObject data;
    private JSONObject meta;
    private TextView tv_title_daijiesuan;
    private TextView tv_title_yuqijiesuan;
    private TextView tv_title_yijiesuan;

    @Override
    public int getRootId() {
        return R.layout.activity_settle_accounts_center;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        kj1 = (TextView) findViewById(R.id.jkl);
        kj1.setText(mTranslatesString.getCommon_zongbaozhengjin()+":");
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        btn_shenqing = (Button) findViewById(R.id.btn_shenqing);
        tv_baozhengjin = (TextView) findViewById(R.id.tv_baozhengjin);
        tv_dongjie = (TextView) findViewById(R.id.tv_dongjie);
        tv_dongjie_title = (TextView) findViewById(R.id.tv_dongjie_title);
        tv_keyong = (TextView) findViewById(R.id.tv_keyong);
        tv_keyong_title = (TextView) findViewById(R.id.tv_keyong_title);
        tv_daijiesuan = (LinearLayout) findViewById(R.id.tv_daijiesuan);
        tv_yuqijiesuan = (LinearLayout) findViewById(R.id.tv_yuqijiesuan);
        tv_yijiesuan = (LinearLayout) findViewById(R.id.tv_yijiesuan);
        btn_shenqing.setText(mTranslatesString.getCommon_shenqing() + "(Ks)");
        btn_shenqing.setOnClickListener(this);
        toolbar_right_text.setText(mTranslatesString.getCommon_mingxi());
        toolbar_title.setText(mTranslatesString.getCommon_zichanzhongxin());
        tv_daijiesuan.setOnClickListener(this);
        tv_yuqijiesuan.setOnClickListener(this);
        tv_yijiesuan.setOnClickListener(this);
        toolbar_right_text.setOnClickListener(this);
        tv_keyong_title.setText(mTranslatesString.getCommon_keyong() + ":");
        tv_dongjie_title.setText(mTranslatesString.getCommo_dongjiebaozhengjin() + ":");
        tv_title_daijiesuan = (TextView) findViewById(R.id.tv_title_daijiesuan);
        tv_title_yuqijiesuan = (TextView) findViewById(R.id.tv_title_yuqijiesuan);
        tv_title_yijiesuan = (TextView) findViewById(R.id.tv_title_yijiesuan);
        tv_title_daijiesuan.setText(mTranslatesString.getCommon_tvdaijiesuan());
        tv_title_yuqijiesuan.setText(mTranslatesString.getCommon_yuqijiesuan());
        tv_title_yijiesuan.setText(mTranslatesString.getCommon_yijiesuan());
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
                try {
                    Response response = HttpClientUtil.doPost(ConstantS.BASE_URL_LINSHI + "staff/getDeliveryTotalBond.htm", map,
                            true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
                    if (response.getStatusCode() == HttpStatus.SC_OK) {

                        String content = response.getContent();
                        JSONObject json = new JSONObject(content);
                        meta = json.getJSONObject("meta");
                        boolean success = meta.getBoolean("success");
                        if (success) {
                            data = json.getJSONObject("data");
                            handler.sendEmptyMessage(100);
                        } else {
                            handler.sendEmptyMessage(200);
                        }
                    }
                } catch (NetException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    try {
                        tv_baozhengjin.setText(data.getString("depositTotalAsset") + "");
                        tv_keyong.setText(data.getString("dynamicAsset") + "");
                        tv_dongjie.setText(data.getString("freezeAsset") + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 200:
                    try {
                        Toast.makeText(getApplication(), meta.getString("errorInfo"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.btn_shenqing://申请
                startActivity(new Intent(getApplication(), ApplyMoneyActivity.class));
                break;
            case R.id.tv_daijiesuan://待结算
                startActivity(new Intent(getApplication(), NoSttleAccountsActivity.class));

                break;
            case R.id.tv_yuqijiesuan://逾期结算
                Intent intent = new Intent(getApplication(), SettleOrderListActivity.class);
                intent.putExtra("tag", "yuqi");
                startActivity(intent);
                break;
            case R.id.tv_yijiesuan://已结算
                startActivity(new Intent(getApplication(), SettleOrderListActivity.class).putExtra("tag", "yesjiesuan"));

                break;
            case R.id.toolbar_right_text://明细
                startActivity(new Intent(getApplication(), PropertyDetailActivity.class));
                break;
        }
    }
}
