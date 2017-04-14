package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.GetBankListListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.BankListDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家 添加 银行卡
 */
public class AddCardActivity extends TempBaseActivity implements OnClickListener {
    private Spinner sp_bank;
    private EditText et_name, et_card_num, et_mobile;
    private CheckBox cb_bank_isdefault;
    private TextView tv_delete, tv_holder_name, tv_bank,
            tv_card_num, tv_tel;
    private Button btn_bank_check;
    private List<String> bankList = new ArrayList<String>();
    private int bankId, isDefault;

    @Override
    public int getRootId() {
        return R.layout.bind_card_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        justDo();
    }

    private void justDo() {
    }

    public void initView() {
        sp_bank = (Spinner) findViewById(R.id.sp_bank);
        et_name = (EditText) findViewById(R.id.et_holder_name);
        et_card_num = (EditText) findViewById(R.id.et_card_num);
        et_mobile = (EditText) findViewById(R.id.et_reserved_num);
        btn_bank_check = (Button) findViewById(R.id.btn_bank_check);
        cb_bank_isdefault = (CheckBox) findViewById(R.id.cb_bank_isdefault);
        tv_holder_name = (TextView) findViewById(R.id.tv_holder_name);
        tv_delete = (TextView) findViewById(R.id.tv_bank_delete);
        tv_bank = (TextView) findViewById(R.id.tv_bank);
        tv_card_num = (TextView) findViewById(R.id.tv_card_num);
        tv_tel = (TextView) findViewById(R.id.tv_add_card_tel);
        tv_delete.setVisibility(View.GONE);

        btn_bank_check.setOnClickListener(this);
        cb_bank_isdefault.setOnCheckedChangeListener(new IsDefaultListener());
        initText();
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_add());
        tv_holder_name.setText(mTranslatesString
                .getCommon_holdername());
        tv_bank.setText(mTranslatesString.getCommon_bank());
        tv_card_num.setText(mTranslatesString
                .getCommon_cardnum());
        tv_tel.setText(mTranslatesString.getCommon_tellphone());
        cb_bank_isdefault.setText(mTranslatesString
                .getCommon_setdefault());
        tv_delete.setText(mTranslatesString.getCommon_delete());
        btn_bank_check.setText(mTranslatesString
                .getCommon_makesure());
    }

    public void initData() {
        showProgressDialog(Waiting);
        rdm.GetBankList(isLogin, userId, token);
        rdm.getbanklistListener = new GetBankListListener() {

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(mTranslatesString.getNotice_requesterror());
            }

            @Override
            public void onSuccess(List<BankListDetail> BankList) {
                initBankList(BankList);
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }

    public void initBankList(final List<BankListDetail> BankList) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                for (BankListDetail temp : BankList) {
                    bankList.add(temp.getBankName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddCardActivity.this,
                        android.R.layout.simple_spinner_item, bankList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_bank.setAdapter(adapter);
                sp_bank.setOnItemSelectedListener(new BankSelectListener(
                        BankList));
            }
        });
    }

    public void AddBankCard() {
        String holderName;
        String mobile;
        String bankCard;
        holderName = et_name.getText().toString();
        mobile = et_mobile.getText().toString();
        bankCard = et_card_num.getText().toString();
        if (holderName.equals("") || mobile.equals("") || bankCard.equals("")) {
            Toast.makeText(this,
                    mTranslatesString.getCommon_inputnotnull(),
                    Toast.LENGTH_LONG).show();
            return;
        }
        showProgressDialog(Loading);
        rdm.BindBankCard(isLogin, userId, token, bankId, holderName, mobile,
                bankCard, isDefault);
        rdm.responseListener = new ResponseListener() {

            @Override
            public void onSuccess(String errorInfo) {
                Intent i = new Intent(AddCardActivity.this,
                        MyCardActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(mTranslatesString.getNotice_requesterror());
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bank_check:
                AddBankCard();
                break;
            default:
                break;
        }
    }

    class BankSelectListener implements OnItemSelectedListener {
        private List<BankListDetail> list;

        public BankSelectListener(List<BankListDetail> list) {
            super();
            this.list = list;
        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
            bankId = list.get(position).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class IsDefaultListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean checked) {
            // TODO Auto-generated method stub
            if (checked) {
                isDefault = 1;
            } else {
                isDefault = 0;
            }
        }

    }
}
