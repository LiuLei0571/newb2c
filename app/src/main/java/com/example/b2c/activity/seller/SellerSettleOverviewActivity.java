package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.widget.SettingItemView2;

/**
 * 预览
 * 结算信息（个人/公司）
 */
public class SellerSettleOverviewActivity extends TempBaseActivity {

    private SettingItemView2 siv_bankName;
    private SettingItemView2 siv_bankBranchName;
    private SettingItemView2 siv_bankAccountName;
    private SettingItemView2 siv_bankAccount;
    private SellerCompanyEntry sellerCompanyEntry;

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
        siv_bankName.hide_RightIv();
        siv_bankBranchName = (SettingItemView2) findViewById(R.id.siv_bankBranchName);
        siv_bankBranchName.hide_RightIv();
        siv_bankAccountName = (SettingItemView2) findViewById(R.id.siv_bankAccountName);
        siv_bankAccountName.hide_RightIv();
        siv_bankAccount = (SettingItemView2) findViewById(R.id.siv_bankAccount);
        siv_bankAccount.hide_RightIv();

        siv_bankName.setTv_doc(sellerCompanyEntry.getBankName());
        siv_bankBranchName.setTv_doc(sellerCompanyEntry.getBankBranchName());
        siv_bankAccountName.setTv_doc(sellerCompanyEntry.getBankAccountName());
        siv_bankAccount.setTv_doc(sellerCompanyEntry.getBankAccount());

        addText(mTranslatesString.getCommon_modify(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerSettleOverviewActivity.this,SellerSettleEditActivity.class);
                i.putExtra("company",sellerCompanyEntry);
                startActivity(i);
                finish();
            }
        });
    }

}
