package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.widget.SettingItemView3;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途:卖家商家信息:公司信息，个人信息
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 * 修改 yy
 */
public class InfoActivity extends TempBaseActivity {

    private int[] icons = { R.drawable.audit_wait, R.drawable.audit_yes,R.drawable.audit_no };
    private  String[]  labels;

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.siv_company_info)
    SettingItemView3 siv_company_info;
    @Bind(R.id.siv_settle_info)
    SettingItemView3 siv_settle_info;

    @Bind(R.id.iv_audit_label)
    ImageView iv_audit_label;
    @Bind(R.id.tv_audit_text)
    TextView tv_audit_text;


    SellerCompanyEntry sellerCompanyEntry;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        labels = new String[]{
                 mTranslatesString.getCommon_daishenhe(),
                mTranslatesString.getCommo_shenheyes(),
                mTranslatesString.getCommon_shenheno()};
        initText();
    }
    @Override
    protected void onResume(){
        super.onResume();
        initData();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_businessinfo());
        tv_title.setText(mTranslatesString.getCommon_shopauditnotice());
        siv_company_info.setTv_text(mTranslatesString.getCommon_gongsixinxi());
        siv_settle_info.setTv_text(mTranslatesString.getCommon_settleinfo());
    }

    @OnClick({R.id.siv_company_info , R.id.siv_settle_info})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.siv_company_info:
                if(sellerCompanyEntry.getType()== 1){
                    Intent  intent_company = new Intent(InfoActivity.this,SellerCompanyOverviewActivity.class);
                    intent_company.putExtra("company",sellerCompanyEntry);
                    startActivity(intent_company);
                }else{
                    Intent  intent_personal = new Intent(InfoActivity.this,SellerPersonalOverviewActivity.class);
                    intent_personal.putExtra("company",sellerCompanyEntry);
                    startActivity(intent_personal);
                }
                break;
            case R.id.siv_settle_info:
                Intent  intent_settle = new Intent(InfoActivity.this,SellerSettleOverviewActivity.class);
                intent_settle.putExtra("company",sellerCompanyEntry);
                startActivity(intent_settle);
                break;
            default:
                break;
        }

    }

    private void initData() {
        showLoading();
        sellerRdm.getCompanyRegInfo();
        sellerRdm.mOneDataListener = new OneDataListener<SellerCompanyEntry>() {
            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
            @Override
            public void onSuccess(final SellerCompanyEntry result,String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            sellerCompanyEntry = result;
                            if(sellerCompanyEntry.getAuditStatus()==0){
                                tv_audit_text.setText(labels[0]);
                                iv_audit_label.setImageResource(icons[0]);
                            }else if(sellerCompanyEntry.getAuditStatus()==1){
                                tv_audit_text.setText(labels[1]);
                                iv_audit_label.setImageResource(icons[1]);
                            }else if(sellerCompanyEntry.getAuditStatus()==2){
                                if(sellerCompanyEntry.getAuditFeedback()!=null){
                                    tv_audit_text.setText(Utils.cutNull(sellerCompanyEntry.getAuditFeedback()));
                                }else {
                                    tv_audit_text.setText(Utils.cutNull(labels[2]));
                                }
                                iv_audit_label.setImageResource(icons[2]);
                            }
                        }
                    }
                });
            }
            @Override
            public void onError(int errorNO, final String errorInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastHelper.makeErrorToast(errorInfo);
                        finish();
                    }
                });
            }
        };
    }
}
