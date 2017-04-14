package com.example.b2c.activity.seller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 类型为公司
 * 设置公司信息第1步
 */
public class Setup1CompanyActivity extends BaseSetCompanyActivity {

    private PickImageDialog mPickImage;

    @Bind(R.id.tv_company_info)
    TextView tv_company_info;
    @Bind(R.id.tv_linkman_info)
    TextView tv_linkman_info;
    @Bind(R.id.tv_company_account)
    TextView tv_company_account;
    @Bind(R.id.tv_company_end)
    TextView tv_company_end;
    @Bind(R.id.et_company_name)
    EditTextWithDelete et_company_name;
    @Bind(R.id.et_register_address)
    EditTextWithDelete et_register_address;
    @Bind(R.id.tv_register_time)
    TextView tv_register_time;
    @Bind(R.id.et_company_personNum)
    EditTextWithDelete et_company_personNum;
    @Bind(R.id.et_company_businessScope)
    EditTextWithDelete et_company_businessScope;
    @Bind(R.id.btn_next)
    Button btn_next;
    @Bind(R.id.iv_license)
    ImageView iv_license;
    @Bind(R.id.tv_license_title)
    TextView tv_license_title;
    @Bind(R.id.iv_select_date)
    ImageView iv_select_date;


    int mYear, mMonth, mDay;
    private String company_name;
    private String register_address;
    private String register_time;
    private String company_personNum;
    private String company_businessScope;

    @Override
    public int getRootId() {
        return R.layout.activity_setup1_company;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        super.getSellerCompanyEntry();
    }


    private void initView() {
        currentType = getIntent().getExtras().getInt("type", REGISTER);
        currentSellerType = getIntent().getExtras().getInt("sellerType", PERSONAL);
        setCurrentType(currentType);
        setCurrentSellerType(currentSellerType);
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_completeinfo());
        tv_company_info.setText(mTranslatesString.getCommon_gongsixinxi());
        tv_linkman_info.setText(mTranslatesString.getCommon_lianxirenxinxi());
        tv_company_account.setText(mTranslatesString.getCommon_settleinfo());
        tv_company_end.setText(mTranslatesString.getCommo_shenheyes());

        et_company_name.setHint(mTranslatesString.getCommon_companyname());
        et_register_address.setHint(mTranslatesString.getCommon_zhucedizhi());
        tv_register_time.setHint(mTranslatesString.getCommon_zhucetime());
        et_company_personNum.setHint(mTranslatesString.getCommon_gongsinumber());
        et_company_businessScope.setHint(mTranslatesString.getCommon_managefanwei());
        tv_license_title.setText(mTranslatesString.getCommon_yingyezhizhao());
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
        et_company_name.setText(sellerCompanyEntry.getCompanyName());
        et_register_address.setText(sellerCompanyEntry.getRegAddress());
        Calendar ca = Calendar.getInstance();
        if (!TextUtils.isBlank(sellerCompanyEntry.getRegDate())) {
            ca.setTime(new Date(Long.parseLong(sellerCompanyEntry.getRegDate())));
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            display(mYear, mMonth, mDay);
        } else {
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        }
        if (sellerCompanyEntry.getTotalPersonNum() != 0) {
            et_company_personNum.setText(sellerCompanyEntry.getTotalPersonNum() + "");
        }
        et_company_businessScope.setText(sellerCompanyEntry.getBusinessScope());
        if (!TextUtils.isBlank(sellerCompanyEntry.getBusinessLicense())) {
            loader.displayImage(ConstantS.BASE_PIC_URL + sellerCompanyEntry.getBusinessLicense(), iv_license, options);
        }
    }


//    @Override
//    public void onBackClick() {
//        if (currentType == REGISTER || currentType == LOGIN || currentType == LAUNCH) {
//            //登录、注册、启动只要信息没有填全，就不允许登录到卖家首页
//        } else {
//            finish();
//        }
//    }


    @OnClick({R.id.btn_next, R.id.iv_select_date, R.id.iv_license})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
//              showNextPage();
                submitCompany();
                break;
            case R.id.iv_select_date:
//                showDatePickDlg();
                showDialog(0);
                break;
            case R.id.iv_license:
                pickImage(view.getId());
                break;
            default:
                break;
        }

    }

    private boolean checkParam() {
        company_name = et_company_name.getText().toString();
        register_address = et_register_address.getText().toString();
        register_time = tv_register_time.getText().toString();
        company_personNum = et_company_personNum.getText().toString();
        company_businessScope = et_company_businessScope.getText().toString();
        if (!TextUtils.isBlank(company_name) && !TextUtils.isBlank(register_address) && !TextUtils.isBlank(register_time)
                && !TextUtils.isBlank(company_personNum) && !TextUtils.isBlank(company_businessScope)
                && !TextUtils.isBlank(sellerCompanyEntry.getBusinessLicense())) {
            return true;
        }
        return false;
    }

    /**
     * 提交公司信息
     */
    protected void submitCompany() {
        boolean check = checkParam();
        if (!check) {
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return;
        }
        Map map = new HashMap();
        map.put("type", currentSellerType);
        map.put("companyName", company_name);
        map.put("regAddress", register_address);
        map.put("totalPersonNum", company_personNum);
        map.put("businessScope", company_businessScope);
        map.put("businessLicenseAttachment", sellerCompanyEntry.getBusinessLicense());
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        map.put("regDate", a.getTime().getTime());
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

    }
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
        Logs.d("showDatePickDlg", mYear + ":" + mMonth + ":" + mDay + ":");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(Setup1CompanyActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateBirthDate(year, monthOfYear, dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /**
     * 修改出生日期
     */
    public void updateBirthDate(final int mYear, final int mMonth, final int mDay) {
        Logs.d("updateBirthDate", mYear + ":" + mMonth + ":" + mDay + ":");
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        sellerCompanyEntry.setRegDate(a.getTime().getTime() + "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display(mYear, mMonth, mDay);
            }
        });

    }

    /**
     * 进入下一个设置界面
     */
    public void showNextPage() {
        Intent intent = new Intent(this, Setup2CompanyActivity.class);
        intent.putExtra("type", currentType);
        intent.putExtra("sellerType", currentSellerType);
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
            protected void onImageUpLoad(final int id, final String url, String url1) {
//                Log.d("onImageUpLoad callBack", url1);
                sellerCompanyEntry.setBusinessLicense(url1);
                UIHelper.displayImage((ImageView) Setup1CompanyActivity.this.findViewById(id), url);
            }
        };
        mPickImage.show();
    }
}
