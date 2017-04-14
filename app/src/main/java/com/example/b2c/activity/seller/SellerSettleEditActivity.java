package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.SettingItemView2;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 编辑
 * 结算信息（个人/公司）
 */
public class SellerSettleEditActivity extends TempBaseActivity {
    public static final int RQ_SETTLE_BANKNAME = 10;
    public static final int RQ_SETTLE_BANKBRANCHNAME= 11;
    public static final int RQ_SETTLE_BANKACCOUNTNAME = 13;
    public static final int RQ_SETTLE_BANKACCOUNT = 14;

    private SettingItemView2 siv_bankName;
    private SettingItemView2 siv_bankBranchName;
    private SettingItemView2 siv_bankAccountName;
    private SettingItemView2 siv_bankAccount;
    private SellerCompanyEntry sellerCompanyEntry;
    private SellerCompanyEntry sellerCompanyEntryModify = new SellerCompanyEntry();

    @Override
    public int getRootId() {
        return R.layout.activity_seller_settle_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_settleinfo());
        initView();
        initText();
    }

    private void initText() {
        siv_bankName.setTv_text(mTranslatesString.getCommon_kaihuyinhang());
        siv_bankBranchName.setTv_text(mTranslatesString.getCommon_zhihangyinhang());
        siv_bankAccountName.setTv_text(mTranslatesString.getCommon_yinhangname());
        siv_bankAccount.setTv_text(mTranslatesString.getCommon_yinhangzhanghu());
    }

    private void initView() {
        sellerCompanyEntry = (SellerCompanyEntry) getIntent().getSerializableExtra("company");
        siv_bankName = (SettingItemView2) findViewById(R.id.siv_bankName);
        siv_bankBranchName = (SettingItemView2) findViewById(R.id.siv_bankBranchName);
        siv_bankAccountName = (SettingItemView2) findViewById(R.id.siv_bankAccountName);
        siv_bankAccount = (SettingItemView2) findViewById(R.id.siv_bankAccount);

        siv_bankName.setTv_doc(sellerCompanyEntry.getBankName());
        siv_bankBranchName.setTv_doc(sellerCompanyEntry.getBankBranchName());
        siv_bankAccountName.setTv_doc(sellerCompanyEntry.getBankAccountName());
        siv_bankAccount.setTv_doc(sellerCompanyEntry.getBankAccount());

        addText(mTranslatesString.getCommon_submit(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateCompany(sellerCompanyEntryModify);
            }
        });
    }
    /**
     * 修改商家信息
     */
    private void doUpdateCompany(SellerCompanyEntry sellerCE) {
        Map map =  new HashMap();
        if(sellerCE.getBankName() != null){
            map.put("bankName",sellerCE.getBankName());
        }
        if(sellerCE.getBankBranchName() != null){
            map.put("bankBranchName",sellerCE.getBankBranchName());
        }
        if(sellerCE.getBankAccountName() != null){
            map.put("bankAccountName",sellerCE.getBankAccountName());
        }
        if(sellerCE.getBankAccount() != null){
            if(!BaseValidator.BankAccountMatch(sellerCE.getBankAccount())){
                ToastHelper.makeErrorToast(mTranslatesString.getCommon_yhzhfail());
                return;
            }
            map.put("bankAccount",sellerCE.getBankAccount());
        }else {
            map.put("bankAccount",sellerCompanyEntry.getBankAccount());

        }

        sellerRdm.postUpdateCompany(map);
        showLoading();
        sellerRdm.responseListener = new ResponseListener() {
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

    @OnClick({R.id.siv_bankName,R.id.siv_bankBranchName,R.id.siv_bankAccountName,R.id.siv_bankAccount})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.siv_bankName:
                Intent intent_company_name = new Intent(SellerSettleEditActivity.this, CommonEditActivity.class);
                intent_company_name.putExtra("title",mTranslatesString.getCommon_kaihuyinhang());
                intent_company_name.putExtra("text",sellerCompanyEntry.getBankName());
                startActivityForResult(intent_company_name,RQ_SETTLE_BANKNAME);
                break;
            case R.id.siv_bankBranchName:
                Intent intent_company_businessScope = new Intent(SellerSettleEditActivity.this, CommonEditActivity.class);
                intent_company_businessScope.putExtra("title",mTranslatesString.getCommon_zhihangyinhang());
                intent_company_businessScope.putExtra("text",sellerCompanyEntry.getBankBranchName());
                startActivityForResult(intent_company_businessScope,RQ_SETTLE_BANKBRANCHNAME);
                break;
            case R.id.siv_bankAccountName:
                Intent intent_company_lastName = new Intent(SellerSettleEditActivity.this, CommonEditActivity.class);
                intent_company_lastName.putExtra("title",mTranslatesString.getCommon_yinhangname());
                intent_company_lastName.putExtra("text",sellerCompanyEntry.getBankAccountName());
                startActivityForResult(intent_company_lastName,RQ_SETTLE_BANKACCOUNTNAME);
                break;
            case R.id.siv_bankAccount:
                Intent intent_company_firstName = new Intent(SellerSettleEditActivity.this, CommonEditActivity.class);
                intent_company_firstName.putExtra("title",mTranslatesString.getCommon_yinhangzhanghu());
                intent_company_firstName.putExtra("text",sellerCompanyEntry.getBankAccount());
                startActivityForResult(intent_company_firstName,RQ_SETTLE_BANKACCOUNT);
                break;
            default:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (data == null) {
            return;
        }
        final String finalText = (!TextUtils.isBlank(data.getStringExtra("text")))?data.getStringExtra("text").toString():"";
        switch (requestCode){
            case RQ_SETTLE_BANKNAME:
                siv_bankName.setTv_doc(finalText);
                sellerCompanyEntryModify.setBankName(finalText);
                break;
            case RQ_SETTLE_BANKBRANCHNAME:
                siv_bankBranchName.setTv_doc(finalText);
                sellerCompanyEntryModify.setBankBranchName(finalText);
                break;
            case RQ_SETTLE_BANKACCOUNTNAME:
                siv_bankAccountName.setTv_doc(finalText);
                sellerCompanyEntryModify.setBankAccountName(finalText);
                break;
            case RQ_SETTLE_BANKACCOUNT:
                siv_bankAccount.setTv_doc(finalText);
                sellerCompanyEntryModify.setBankAccount(finalText);
                break;
            default:
                break;
        }

    }

}
