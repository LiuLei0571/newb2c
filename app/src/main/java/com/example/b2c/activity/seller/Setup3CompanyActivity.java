package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置公司信息第3步
 */
public class Setup3CompanyActivity extends BaseSetCompanyActivity {


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

    @Bind(R.id.et_bankName)
    EditTextWithDelete et_bankName;
    @Bind(R.id.et_bankBranchName)
    EditTextWithDelete et_bankBranchName;
    @Bind(R.id.et_bankAccountName)
    EditTextWithDelete et_bankAccountName;
    @Bind(R.id.et_bankAccount)
    EditTextWithDelete et_bankAccount;

    @Bind(R.id.btn_next)
    Button btn_next;
    private String bankName;
    private String bankBranchName;
    private String bankAccountName;
    private String bankAccount;

    @Override
    public int getRootId() {
        return R.layout.activity_setup3_company;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        super.getSellerCompanyEntry();
    }
    @Override
    protected void initUI() {
        et_bankName.setText(sellerCompanyEntry.getBankName());
        et_bankBranchName.setText(sellerCompanyEntry.getBankBranchName());
        et_bankAccountName.setText(sellerCompanyEntry.getBankAccountName());
        et_bankAccount.setText(sellerCompanyEntry.getBankAccount());
    }
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
        et_bankName.setHint(mTranslatesString.getCommon_kaihuyinhang());
        et_bankBranchName.setHint(mTranslatesString.getCommon_zhihangyinhang());
        et_bankAccountName.setHint(mTranslatesString.getCommon_yinhangname());
        et_bankAccount.setHint(mTranslatesString.getCommon_yinhangzhanghu());
        btn_next.setText(mTranslatesString.getCommon_nextstep());
    }
    @Override
    public void onBackClick() {
        showPreviousPage();
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                submitCompany();
                break;
            default:
                break;
        }

    }

    /**
     *
     * @return
     */
    private boolean checkParam(){
        bankName = et_bankName.getText().toString();
        bankBranchName = et_bankBranchName.getText().toString();
        bankAccountName = et_bankAccountName.getText().toString();
        bankAccount = et_bankAccount.getText().toString();
        if(TextUtils.isBlank(bankName) || TextUtils.isBlank(bankBranchName)
                || TextUtils.isBlank(bankAccountName) || TextUtils.isBlank(bankAccount)){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return true;
        }
        if(!BaseValidator.BankAccountMatch(bankAccount)){
            ToastHelper.makeToast(mTranslatesString.getCommon_yinhangzhanghu()+mTranslatesString.getCommon_styleerror());
            return true;
        }
        return false;
    }

    /**
     * 提交公司信息
     */
    protected void  submitCompany(){
        if(checkParam()){
            return;
        }
        Map map = new HashMap();
        map.put("bankName",bankName);
        map.put("bankBranchName",bankBranchName);
        map.put("bankAccountName",bankAccountName);
        map.put("bankAccount",bankAccount);
        showLoading();
        sellerRdm.postCompanyRegStepThree(map);
        sellerRdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(success);
                /**
                 * 进入下一个页面
                 */
                showNextPage();
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

    };
    /**
     * 进入下一个设置界面
     */
    public void showNextPage() {
        Intent intent = new Intent(this, Setup4CompanyActivity.class);
        intent.putExtra("type",currentType);
        intent.putExtra("sellerType",currentSellerType);
        startActivity(intent);
        finish();
        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画
    }
    /**
     *
     * 进入上一个设置界面
     */
    public void showPreviousPage() {
        Intent intent = null;
        if(currentType == COMPANY){
             intent = new Intent(this, Setup2CompanyActivity.class);
        }else if(currentType == PERSONAL){
             intent = new Intent(this, Setup1CompanyPersonalActivity.class);
        }
        intent.putExtra("type",currentType);
        intent.putExtra("sellerType",currentSellerType);
        startActivity(intent);
        finish();
        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_previous_in,R.anim.tran_previous_out);// 进入动画和退出动画
    }
}
