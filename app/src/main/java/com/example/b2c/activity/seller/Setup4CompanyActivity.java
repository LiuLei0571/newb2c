package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.Users;

import butterknife.Bind;

/**
 * 设置公司信息第四步
 */
public class Setup4CompanyActivity extends BaseSetCompanyActivity {

    @Bind(R.id.ll_title_company)
    LinearLayout ll_title_company;
    @Bind(R.id.tv_company_info)
    TextView tv_company_info;
    @Bind(R.id.tv_linkman_info)
    TextView tv_linkman_info;
    @Bind(R.id.tv_company_account)
    TextView tv_company_account;
    @Bind(R.id.tv_company_end)
    TextView tv_company_end;

    @Bind(R.id.ll_title_personal)
    LinearLayout ll_title_personal;
    @Bind(R.id.tv_personal_info)
    TextView tv_personal_info;
    @Bind(R.id.tv_settle_account)
    TextView tv_settle_account;
    @Bind(R.id.tv_personal_end)
    TextView tv_personal_end;

    @Bind(R.id.tv_notice)
    TextView tv_notice;
    @Bind(R.id.tv_time)
    TextView tv_time;

    private int  time = 4;

    Handler handler = new Handler();

    @Override
    public int getRootId() {
        return R.layout.activity_setup4_company;
    }

    @Override
    protected void initUI() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        /**
         * 保存卖家商家入驻步揍
         */
        Users seller = SPHelper.getBean(Configs.SELLER.INFO, Users.class);
        seller.setStep(BaseSetCompanyActivity.SELLERSTEPTHIRD);
        SPHelper.putBean(Configs.SELLER.INFO,seller);
        handler.postDelayed(runnable, 1000);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            tv_time.setText(time+mTranslatesString.getCommon_minusafterjump()+"..." );
            if(time==0){
                Intent intent = new Intent(Setup4CompanyActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }else{
                handler.postDelayed(this, 1000);
            }
        }
    };
    private void initView() {
        currentType = getIntent().getExtras().getInt("type",REGISTER);
        currentSellerType = getIntent().getExtras().getInt("sellerType",PERSONAL);
        setCurrentType(currentType);
        setCurrentSellerType(currentSellerType);
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_completeinfo());
        if(currentSellerType == COMPANY){
            ll_title_company.setVisibility(View.VISIBLE);
            ll_title_personal.setVisibility(View.INVISIBLE);
            tv_company_info.setText(mTranslatesString.getCommon_gongsixinxi());
            tv_linkman_info.setText(mTranslatesString.getCommon_lianxirenxinxi());
            tv_company_account.setText(mTranslatesString.getCommon_settleinfo());
            tv_company_end.setText(mTranslatesString.getCommo_shenheyes());
        }else if (currentSellerType == PERSONAL) {
            ll_title_company.setVisibility(View.INVISIBLE);
            ll_title_personal.setVisibility(View.VISIBLE);
            tv_personal_info.setText(mTranslatesString.getCommon_lianxirenxinxi());
            tv_settle_account.setText(mTranslatesString.getCommon_settleinfo());
            tv_personal_end.setText(mTranslatesString.getCommo_shenheyes());
        }
        tv_notice.setText(mTranslatesString.getCommon_infosubmityet());
    }

//    @OnClick(R.id.tv_link)
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.tv_link:
//                showFirstPage();
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onBackClick() {
        showPreviousPage();
    }
    /**
     * 进入上一个设置界面
     */
    public void showPreviousPage() {

        Intent intent = new Intent(this, Setup3CompanyActivity.class);
        intent.putExtra("type",currentType);
        intent.putExtra("sellerType",currentSellerType);
        startActivity(intent);
        finish();
        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_previous_in,R.anim.tran_previous_out);// 进入动画和退出动画

    }
//    /**
//     * 进入第一个设置界面
//     */
//    public void showFirstPage() {
//        Intent intent = new Intent(this, Setup1CompanyActivity.class);
//        intent.putExtra("type",currentType);
//        intent.putExtra("sellerType",currentSellerType);
//        startActivity(intent);
//        finish();
//        // 两个界面切换的动画
//        overridePendingTransition(R.anim.tran_previous_in,R.anim.tran_previous_out);// 进入动画和退出动画
//
//    }
}
