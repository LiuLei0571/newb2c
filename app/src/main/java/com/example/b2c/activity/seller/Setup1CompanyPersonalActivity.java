package com.example.b2c.activity.seller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类型为个人
 * 设置公司信息第1步
 */
public class Setup1CompanyPersonalActivity extends BaseSetCompanyActivity {

    private PickImageDialog mPickImage;

    @Bind(R.id.tv_personal_info)
    TextView tv_personal_info;
    @Bind(R.id.tv_settle_account)
    TextView tv_settle_account;
    @Bind(R.id.tv_personal_end)
    TextView tv_personal_end;

    @Bind(R.id.et_lastName)
    EditTextWithDelete et_lastName;
    @Bind(R.id.et_firstName)
    EditTextWithDelete et_firstName;
    @Bind(R.id.rg_sex)
    RadioGroup rg_sex;
    @Bind(R.id.rb_male)
    RadioButton rb_male;
    @Bind(R.id.rb_female)
    RadioButton rb_female;
    @Bind(R.id.rb_secret)
    RadioButton rb_secret;
    @Bind(R.id.tv_contact_location)
    TextView tv_contact_location;
    @Bind(R.id.iv_select_address)
    ImageView iv_select_address;

    @Bind(R.id.et_idCard)
    EditTextWithDelete et_idCard;
    @Bind(R.id.et_company_businessScope)
    EditTextWithDelete et_company_businessScope;

    @Bind(R.id.tv_register_time)
    TextView tv_register_time;
    @Bind(R.id.iv_select_date)
    ImageView iv_select_date;

    @Bind(R.id.btn_next)
    Button btn_next;

    @Bind(R.id.iv_positive)
    ImageView iv_positive;
    @Bind(R.id.tv_positive_title)
    TextView tv_positive_title;
    @Bind(R.id.iv_reverse)
    ImageView iv_reverse;
    @Bind(R.id.tv_reverse_title)
    TextView tv_reverse_title;



    int mYear, mMonth, mDay;
    private String lastName;
    private String firstName;
    private String idCard;
    private String birthDate;
    private String company_businessScope ;
    private String contact_location;
    private String contactProvinceCode;
    private String contactCityCode;

    @Override
    public int getRootId() {
        return R.layout.activity_setup1_company_personal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        super.getSellerCompanyEntry();
    }


    private void initView() {
        currentType = getIntent().getExtras().getInt("type",REGISTER);
        currentSellerType = getIntent().getExtras().getInt("sellerType",PERSONAL);
        setCurrentType(currentType);
        setCurrentSellerType(currentSellerType);

    }
    private void initText() {
        setTitle(mTranslatesString.getCommon_completeinfo());
        tv_personal_info.setText(mTranslatesString.getCommon_linkinfo());
        tv_settle_account.setText(mTranslatesString.getCommon_settleinfo());
        tv_personal_end.setText(mTranslatesString.getCommo_shenheyes());

        et_firstName.setHint(mTranslatesString.getCommon_ming());
        et_lastName.setHint(mTranslatesString.getCommon_xing());
        rb_male.setText(mTranslatesString.getCommon_man());
        rb_female.setText(mTranslatesString.getCommon_woman());
        rb_secret.setText(mTranslatesString.getCommon_keepsecret());

        et_idCard.setHint(mTranslatesString.getCommon_idcard());
        tv_register_time.setHint(mTranslatesString.getCommon_birthdaydate());
        et_company_businessScope.setHint(mTranslatesString.getCommon_managefanwei());
        tv_contact_location.setHint(mTranslatesString.getCommon_lianxidizhi());

        tv_positive_title.setText(mTranslatesString.getCommon_idpositive());
        tv_reverse_title.setText(mTranslatesString.getCommon_idreverse());

        btn_next.setText(mTranslatesString.getCommon_nextstep());
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display(int mYear, int mMonth, int mDay) {
        String mYear_str = String.valueOf(mYear);
        String mMonth_str = String.valueOf(mMonth+1);
        String mDay_str = String.valueOf(mDay);
        if (mMonth_str.length() < 2)
            mMonth_str = "0" + mMonth_str;
        if(mDay_str.length() < 2)
            mDay_str = "0"+ mDay_str;
        tv_register_time.setText(new StringBuffer().append(mYear_str).append("-").append(mMonth_str).append("-").append(mDay_str).append(" ").toString());
    }

    @Override
    protected void initUI() {
        et_firstName.setText(sellerCompanyEntry.getFirstName());
        et_lastName.setText(sellerCompanyEntry.getLastName());
        int sex = sellerCompanyEntry.getSex();
        if(sex == 0){
            rb_male.setChecked(true);
        }else if(sex == 1){
            rb_female.setChecked(true);
        }else {
            rb_secret.setChecked(true);
        }
        et_idCard.setText(sellerCompanyEntry.getIdCard());
        Calendar ca = Calendar.getInstance();
        if(sellerCompanyEntry!= null && !TextUtils.isBlank(sellerCompanyEntry.getBirthDate())){
            ca.setTime(new Date(Long.parseLong(sellerCompanyEntry.getBirthDate())));
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            display(mYear,mMonth,mDay);
        }else {
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        }
        et_idCard.setText(sellerCompanyEntry.getIdCard());
        et_company_businessScope.setText(sellerCompanyEntry.getBusinessScope());
        if (!TextUtils.isBlank(sellerCompanyEntry.getCorporateFrontIdCardPic())) {
            loader.displayImage(ConstantS.BASE_PIC_URL+sellerCompanyEntry.getCorporateFrontIdCardPic(),iv_positive,options);
        }
        if (!TextUtils.isBlank(sellerCompanyEntry.getCorporateBackIdCardPic())) {
            loader.displayImage(ConstantS.BASE_PIC_URL+sellerCompanyEntry.getCorporateBackIdCardPic(),iv_reverse,options);
        }
        if(!TextUtils.isBlank(sellerCompanyEntry.getContactProvinceName())||!TextUtils.isBlank(sellerCompanyEntry.getContactCityName())){
            tv_contact_location.setText(sellerCompanyEntry.getContactProvinceName()+
                    " "+sellerCompanyEntry.getContactCityName()+
                    " "+ Utils.cutNull(sellerCompanyEntry.getContactAddress()));
        }
        contactProvinceCode = sellerCompanyEntry.getContactProvinceCode();
        contactCityCode = sellerCompanyEntry.getContactCityCode();
        contact_location = sellerCompanyEntry.getContactAddress();
    }


//    @Override
//    public void onBackClick() {
//        if(currentType == REGISTER|| currentType == LOGIN ||currentType == LAUNCH){
//            //登录、注册、启动只要信息没有填全，就不允许登录到卖家首页
//        }else {
//            finish();
//        }
//    }


    @OnClick({R.id.btn_next,R.id.iv_select_date,R.id.iv_select_address, R.id.iv_positive,R.id.iv_reverse})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
//              showNextPage();
                submitCompany();
                break;
            case R.id.iv_select_address:
                //跳往选择界面
                Intent intent=new Intent(Setup1CompanyPersonalActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", contactProvinceCode);
                intent.putExtra("cityCode", contactCityCode);
                intent.putExtra("detailAddress", contact_location);
                startActivityForResult(intent, 1001);
                break;
            case R.id.iv_select_date:
//                showDatePickDlg();
                showDialog(0);
                break;
            case R.id.iv_positive:
                pickImage(view.getId());
                break;
            case R.id.iv_reverse:
                pickImage(view.getId());
                break;
            default:
                break;
        }

    }
    private boolean  checkParam(){
        lastName = et_lastName.getText().toString();
        firstName = et_firstName.getText().toString();
        idCard = et_idCard.getText().toString();
        birthDate = tv_register_time.getText().toString();
        company_businessScope = et_company_businessScope.getText().toString();
        contact_location = tv_contact_location.getText().toString();
        if(!TextUtils.isBlank(lastName)
                && !TextUtils.isBlank(firstName)
                && !TextUtils.isBlank(idCard)
                && !TextUtils.isBlank(birthDate)
                && !TextUtils.isBlank(company_businessScope)
                && !TextUtils.isBlank(sellerCompanyEntry.getCorporateFrontIdCardPic())
                && !TextUtils.isBlank(sellerCompanyEntry.getCorporateBackIdCardPic())){
            return true;
        }
        return false;
    }

    /**
     * 提交公司信息
     */
    protected void  submitCompany(){
        boolean check = checkParam();
        if(!check){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return;
        }
        Map map = new HashMap();
        map.put("firstName",firstName);
        map.put("lastName",lastName);
        map.put("contactProvinceCode",contactProvinceCode);
        map.put("contactCityCode",contactCityCode);
        map.put("contactAddress",contact_location);
        map.put("type",currentSellerType);
        long selectedId = rg_sex.getCheckedRadioButtonId();
        int sex = 2;
        if(selectedId == rb_male.getId()){
            sex = 0;
        }else if(selectedId == rb_female.getId()){
            sex = 1;
        }
        map.put("idCard",idCard);
        map.put("sex",sex);
        map.put("corporateFrontIdCardPic",sellerCompanyEntry.getCorporateFrontIdCardPic());
        map.put("corporateBackIdCardPic",sellerCompanyEntry.getCorporateBackIdCardPic());
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        map.put("businessScope",company_businessScope);
        map.put("regDate",a.getTime().getTime());
        showLoading();
        sellerRdm.postCompanyRegStepOne(map);
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

    //选择日期
    @Override
    protected Dialog onCreateDialog(int id) {
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case 0:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }
    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if(mYear == year && mMonth == monthOfYear && dayOfMonth == mDay ){
                return;
            }
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, mYear);
            a.set(Calendar.MONTH, mMonth);
            a.set(Calendar.DATE, mDay);
            sellerCompanyEntry.setRegDate(a.getTime().getTime() + "");
            display(mYear,mMonth,mDay);
        }
    };


    @Deprecated
    protected void showDatePickDlg() {
        Logs.d("showDatePickDlg",mYear+":"+mMonth+":"+mDay+":");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(Setup1CompanyPersonalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateBirthDate(year,monthOfYear,dayOfMonth);
            }
        },mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    /**
     * 修改出生日期
     */
    public void updateBirthDate(final int mYear, final int mMonth, final int mDay ) {
        Logs.d("updateBirthDate",mYear+":"+mMonth+":"+mDay+":");
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        sellerCompanyEntry.setRegDate(a.getTime().getTime()+"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display(mYear,mMonth,mDay);
            }
        });

    }
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


    public void pickImage(int id) {
        if (mPickImage != null) {
            mPickImage.destory();
        }
        mPickImage = new PickImageDialog(this, id, mLogisticsDataConnection) {
            @Override
            protected void onImageUpLoad(final  int id, final String url, String url1) {
                Log.d("onImageUpLoad callBack", url1);
                if(id == R.id.iv_positive){
                    sellerCompanyEntry.setCorporateFrontIdCardPic(url1);
                }else{
                    sellerCompanyEntry.setCorporateBackIdCardPic(url1);
                }
                UIHelper.displayImage((ImageView) Setup1CompanyPersonalActivity.this.findViewById(id), url);
            }
        };
        mPickImage.show();
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




}
