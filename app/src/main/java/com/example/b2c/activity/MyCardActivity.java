package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.BankCardListAdapter;
import com.example.b2c.net.listener.GetBankCardListListener;
import com.example.b2c.net.response.GetBankCardListDetail;

import java.util.List;

/**
 * 银行卡列表 qiyong
 */
public class MyCardActivity extends TempBaseActivity implements OnClickListener {
    private Button btn_add_card;
    private ListView lv_bankcard;
    private BankCardListAdapter adapter;

    @Override
    public int getRootId() {
        return R.layout.my_card_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public void initView() {
        btn_add_card = (Button) findViewById(R.id.btn_add_card);
        lv_bankcard = (ListView) findViewById(R.id.lv_my_cards);

        btn_add_card.setOnClickListener(this);
        initText();
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_bindcard());
        btn_add_card.setText(mTranslatesString.getCommon_add());
    }

    public void initData() {
        showProgressDialog(Loading);
        rdm.GetBankCardList(isLogin, userId, token);
        rdm.getbankcardlistListener = new GetBankCardListListener() {
            @Override
            public void onSuccess(final List<GetBankCardListDetail> BankCardList) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter = new BankCardListAdapter(MyCardActivity.this,
                                BankCardList);
                        lv_bankcard.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                showAlertDialog(request_failure);
            }


        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_add_card:
                Intent i_add_card = new Intent(this, AddCardActivity.class);
                startActivity(i_add_card);
                finish();
            default:
                break;
        }
    }

}
