package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.example.b2c.R.id.tv_small_title;

/**
 * 设置公司信息第2步
 */
public class Setup2CompanyActivity extends BaseSetCompanyActivity {

    @Bind(R.id.tv_company_info)
    TextView tv_company_info;
    @Bind(R.id.tv_linkman_info)
    TextView tv_linkman_info;
    @Bind(R.id.tv_company_account)
    TextView tv_company_account;
    @Bind(R.id.tv_company_end)
    TextView tv_company_end;
    @Bind(R.id.et_lastName)
    EditTextWithDelete et_lastName;
    @Bind(R.id.et_firstName)
    EditTextWithDelete et_firstName;
    @Bind(R.id.et_email)
    EditTextWithDelete et_email;
    @Bind(R.id.et_tel)
    EditTextWithDelete et_tel;
    @Bind(R.id.et_mobile)
    EditTextWithDelete et_mobile;
    @Bind(R.id.tv_contact_location)
    TextView tv_contact_location;
    @Bind(R.id.btn_next)
    Button btn_next;
    @Bind(R.id.iv_select_address)
    ImageView iv_select_address;


    private String lastName;
    private String firstName;
    private String email;
    private String mobile;
    private String tel;
    private String contact_location;
    private String contactProvinceCode;
    private String contactCityCode;


    @Override
    public int getRootId() {
        return R.layout.activity_setup2_company;
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


        et_firstName.setText(sellerCompanyEntry.getFirstName());
        et_lastName.setText(sellerCompanyEntry.getLastName());
        et_email.setText(sellerCompanyEntry.getContactEmail());
        et_mobile.setText(sellerCompanyEntry.getContactMobile());
        et_tel.setText(sellerCompanyEntry.getContactTel());
        if(!TextUtils.isBlank(sellerCompanyEntry.getContactProvinceName())||!TextUtils.isBlank(sellerCompanyEntry.getContactCityName())){
            tv_contact_location.setText(sellerCompanyEntry.getContactProvinceName()+
                    " "+sellerCompanyEntry.getContactCityName()+
                    ""+ Utils.cutNull(sellerCompanyEntry.getContactAddress()));
        }
        contactProvinceCode = sellerCompanyEntry.getContactProvinceCode();
        contactCityCode = sellerCompanyEntry.getContactCityCode();
        contact_location = sellerCompanyEntry.getContactAddress();

    }
    private void initView() {
        currentType = getIntent().getExtras().getInt("type",REGISTER);
        currentSellerType = getIntent().getExtras().getInt("sellerType",PERSONAL);
        setCurrentType(currentType);
        setCurrentSellerType(currentSellerType);
    }
    @Override
    public void onBackClick() {
        showPreviousPage();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_completeinfo());

        tv_company_info.setText(mTranslatesString.getCommon_gongsixinxi());
        tv_linkman_info.setText(mTranslatesString.getCommon_lianxirenxinxi());
        tv_company_account.setText(mTranslatesString.getCommon_settleinfo());
        tv_company_end.setText(mTranslatesString.getCommo_shenheyes());


        et_firstName.setHint(mTranslatesString.getCommon_ming());
        et_lastName.setHint(mTranslatesString.getCommon_xing());
        et_email.setHint(mTranslatesString.getCommon_email());
        et_mobile.setHint(mTranslatesString.getCommon_tellphone());
        et_tel.setHint(mTranslatesString.getCommon_dianhua());
        tv_contact_location.setHint(mTranslatesString.getCommon_lianxidizhi());
        btn_next.setText(mTranslatesString.getCommon_nextstep());

    }

    @OnClick({R.id.btn_next,R.id.iv_select_address})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_select_address:
                //跳往选择界面
                Intent intent=new Intent(Setup2CompanyActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", contactProvinceCode);
                intent.putExtra("cityCode", contactCityCode);
                intent.putExtra("detailAddress", contact_location);
                startActivityForResult(intent, 1001);
                break;
            case R.id.btn_next:
//               showNextPage();
                submitCompany();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode==1002){
            contactProvinceCode = data.getStringExtra("provinceCode");
            contactCityCode = data.getStringExtra("cityCode");
            contact_location = data.getStringExtra("detailAddress");
            tv_contact_location.setText(data.getStringExtra("addressShow"));
        }
    }
    /**
     *
     * @return
     */
    private boolean  checkParam(){
        lastName = et_lastName.getText().toString();
        firstName = et_firstName.getText().toString();
        email = et_email.getText().toString();
        mobile = et_mobile.getText().toString();
        tel = et_tel.getText().toString();
        if(TextUtils.isBlank(lastName) || TextUtils.isBlank(firstName) || TextUtils.isBlank(email)
                || TextUtils.isBlank(tel) || TextUtils.isBlank(mobile) || TextUtils.isBlank(contact_location)){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return true;
        }
        if(!BaseValidator.EmailMatch(email)){
            ToastHelper.makeToast(mTranslatesString.getCommon_email()+mTranslatesString.getCommon_styleerror());
            return true;
        }
        if(!BaseValidator.MobileMatch(mobile)){
            ToastHelper.makeToast(mTranslatesString.getCommon_tellphone()+mTranslatesString.getCommon_styleerror());
            return true;
        }
        if(!BaseValidator.TelMatch(tel)){
            ToastHelper.makeToast(mTranslatesString.getCommon_dianhua()+mTranslatesString.getCommon_styleerror());
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
        map.put("firstName",lastName);
        map.put("lastName",firstName);
        map.put("contactTel",tel);
        map.put("contactMobile",mobile);
        map.put("contactEmail",email);
        map.put("contactProvinceCode",contactProvinceCode);
        map.put("contactCityCode",contactCityCode);
        map.put("contactAddress",contact_location);
        showLoading();
        sellerRdm.postCompanyRegStepTwo(map);
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
        Intent intent = new Intent(this, Setup3CompanyActivity.class);
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
        Intent intent = new Intent(this, Setup1CompanyActivity.class);
        intent.putExtra("type",currentType);
        intent.putExtra("sellerType",currentSellerType);
        startActivity(intent);
        finish();
        // 两个界面切换的动画
        overridePendingTransition(R.anim.tran_previous_in,R.anim.tran_previous_out);// 进入动画和退出动画
    }
}
