package com.example.b2c.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.BuyerInfo;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.CircleImg;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家信息页面
 */
public class BuyerInfoActivity extends TempBaseActivity implements View.OnClickListener {

    private static final  int  RQ_LASTNAME=11;
    private static final  int  RQ_FIRSTNAME=12;
    private static final  int  RQ_MOBILE=13;
    private static final  int  RQ_SEX=14;

    private  String  notSet;

    private PickImageDialog mPickImage;
    private String mPhoto;

    private TextView tv_user;
    private CircleImg ci_avatarImg;
    private SettingItemView2 siv_username,siv_email,siv_lastName,
            siv_firstName,siv_sex,siv_birthday,siv_mobile;
    private RelativeLayout rl_head;

    private BuyerInfo buyerInfoLocal;

    int mYear, mMonth, mDay;

    @Override
    public int getRootId() {
        return R.layout.activity_buyer_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }

    private void initView() {
        notSet = mTranslatesString.getCommon_notset();
        tv_user = (TextView) findViewById(R.id.tv_user);
        ci_avatarImg = (CircleImg) findViewById(R.id.ci_avatarImg);
        siv_username =  (SettingItemView2) findViewById(R.id.siv_username);
        siv_email = (SettingItemView2) findViewById(R.id.siv_email);
        siv_lastName = (SettingItemView2) findViewById(R.id.siv_lastName);
        siv_firstName = (SettingItemView2) findViewById(R.id.siv_firstName);
        siv_sex = (SettingItemView2) findViewById(R.id.siv_sex);
        siv_birthday = (SettingItemView2) findViewById(R.id.siv_birthday);
        siv_mobile = (SettingItemView2) findViewById(R.id.siv_mobile);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);

        rl_head.setOnClickListener(this);
        siv_username.setOnClickListener(this);
        siv_email.setOnClickListener(this);
        siv_lastName.setOnClickListener(this);
        siv_firstName.setOnClickListener(this);
        siv_sex.setOnClickListener(this);
        siv_birthday.setOnClickListener(this);
        siv_mobile.setOnClickListener(this);
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_personaldetail());
        tv_user.setText(mTranslatesString.getCommon_headphoto());

        siv_username.setTv_text(mTranslatesString.getCommon_username());
        siv_username.hide_RightIv();
        siv_email.setTv_text(mTranslatesString.getCommon_email());
        siv_email.hide_RightIv();

        siv_lastName.setTv_text(mTranslatesString.getCommon_xing());
        siv_firstName.setTv_text(mTranslatesString.getCommon_ming());
        siv_sex.setTv_text(mTranslatesString.getCommon_sex());
        siv_birthday.setTv_text(mTranslatesString.getCommon_birthdaydate());
        siv_mobile.setTv_text(mTranslatesString.getCommon_tellphone());
    }



    private void initData() {

        showProgressDialog(Loading);
        rdm.getBuyerInfo(UserHelper.isBuyerLogin(),UserHelper.getBuyerId(),UserHelper.getBuyertoken());
        rdm.mOneDataListener = new OneDataListener<BuyerInfo>() {
            @Override
            public void onSuccess(final BuyerInfo buyerInfo, String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initDoc(buyerInfo);
                    }
                });
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        };


    }

    /**
     * 初始化界面信息
     * @param buyerInfo
     */
    private void initDoc(BuyerInfo buyerInfo) {
        buyerInfoLocal = buyerInfo;
        if(TextUtils.isBlank(buyerInfo.getHeadPic())){
            ci_avatarImg.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mine_head));
        }else{
            loader.displayImage(ConstantS.BASE_PIC_URL+buyerInfo.getHeadPic(),ci_avatarImg,options);
        }
        if(TextUtils.isBlank(buyerInfo.getLoginName())){
            siv_username.setTv_doc(notSet);
        }else{
            siv_username.setTv_doc(buyerInfo.getLoginName());
        }
        if(TextUtils.isBlank(buyerInfo.getEmail())){
            siv_email.setTv_doc(notSet);
        }else{
            siv_email.setTv_doc(buyerInfo.getEmail());
        }
        if(TextUtils.isBlank(buyerInfo.getLastName())){
            siv_lastName.setTv_doc(notSet);
        }else{
            siv_lastName.setTv_doc(buyerInfo.getLastName());
        }
        if(TextUtils.isBlank(buyerInfo.getFirstName())){
            siv_firstName.setTv_doc(notSet);
        }else{
            siv_firstName.setTv_doc(buyerInfo.getFirstName());
        }
        String sexName=notSet;
        if(buyerInfo.getSex()==0){
            sexName=mTranslatesString.getCommon_man();
        }else if(buyerInfo.getSex()==1){
            sexName=mTranslatesString.getCommon_woman();
        }else if(buyerInfo.getSex()==2){
            sexName=mTranslatesString.getCommon_keepsecret();
        }
        siv_sex.setTv_doc(sexName);

        Calendar ca = Calendar.getInstance();
        if(!TextUtils.isBlank(buyerInfo.getBirthDate())){
            ca.setTime(new Date(Long.parseLong(buyerInfo.getBirthDate())));
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            display(mYear,mMonth,mDay);
        }else {
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            siv_birthday.setTv_doc(notSet);
        }
        if(TextUtils.isBlank(buyerInfo.getMobile())){
            siv_mobile.setTv_doc(notSet);
        }else{
            siv_mobile.setTv_doc(buyerInfo.getMobile());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rl_head:
                pickImage(R.id.ci_avatarImg);
                break;
            case R.id.siv_firstName:
                Intent  i_fn =new Intent(BuyerInfoActivity.this, CommonEditActivity.class);
                Bundle  bundle_fn =new Bundle();
                bundle_fn.putString("title",mTranslatesString.getCommon_ming());
                bundle_fn.putString("text", Utils.cutNull(buyerInfoLocal.getFirstName()));
                i_fn.putExtras(bundle_fn);
                startActivityForResult (i_fn,RQ_FIRSTNAME);
                break;
            case R.id.siv_lastName:
                Intent  i_ln =new Intent(BuyerInfoActivity.this, CommonEditActivity.class);
                Bundle  bundle_ln =new Bundle();
                bundle_ln.putString("title",mTranslatesString.getCommon_xing());
                bundle_ln.putString("text", Utils.cutNull(buyerInfoLocal.getLastName()));
                i_ln.putExtras(bundle_ln);
                startActivityForResult (i_ln,RQ_LASTNAME);
                break;
            case R.id.siv_mobile:
                Intent  i_m =new Intent(BuyerInfoActivity.this, CommonEditActivity.class);
                Bundle  bundle_m =new Bundle();
                bundle_m.putString("title",mTranslatesString.getCommon_tellphone());
                bundle_m.putString("text", Utils.cutNull(buyerInfoLocal.getMobile()));
                i_m.putExtras(bundle_m);
                startActivityForResult (i_m,RQ_MOBILE);
                break;

            case R.id.siv_sex:
                List<CellText> sexs = mTranslatesStringList.getSexTypeCode();
                CellText ck = null;
                for (CellText sex : sexs) {
                    if(sex.getValue().equals(buyerInfoLocal.getSex()+"")){
                        sex.setChecked(true);
                        ck = sex;
                    }
                }
                Intent  i_sex =new Intent(BuyerInfoActivity.this, CommonItemOneActivity.class);
                Bundle  bundle =new Bundle();
                bundle.putString("title",mTranslatesString.getCommon_sex());
                bundle.putSerializable("object",ck);
                bundle.putSerializable("items",new GoodSources(sexs));
                i_sex.putExtras(bundle);
                startActivityForResult(i_sex,RQ_SEX);
                break;
            case R.id.siv_birthday:
                showDialog(0);
                break;
            default:
                break;

        }

    }

    @Deprecated
    protected void showDatePickDlg() {
        Logs.d("showDatePickDlg",mYear+":"+mMonth+":"+mDay+":");
        DatePickerDialog datePickerDialog = new DatePickerDialog(BuyerInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(mYear == year && mMonth == monthOfYear && dayOfMonth == mDay ){
                    return;
                }else {
                    updateBirthDate(year,monthOfYear,dayOfMonth);
                }
            }
        },mYear, mMonth, mDay);
        datePickerDialog.show();
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
            if(DateUtils.compareDate(a.getTime())<0){
                ToastHelper.makeErrorToast(mTranslatesString.getCommon_birthnotmorenow());
                return;
            }
            Map map = new HashMap();
            map.put("birthDate",a.getTime().getTime());
            modifyUserInfo(map, new CallBack() {
                @Override
                public void doCallBack() {
                    display(mYear,mMonth,mDay);
                }
            });
        }
    };

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
        siv_birthday.setTv_doc(new StringBuffer().append(mYear_str).append("-").append(mMonth_str).append("-").append(mDay_str).append(" ").toString());
    }


    /**
     * 修改出生日期
     */
    public void updateBirthDate(final int mYear, final int mMonth, final int mDay ) {

        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        if(DateUtils.compareDate(a.getTime())<0){
            ToastHelper.makeErrorToast(mTranslatesString.getCommon_birthnotmorenow());
            return;
        }
        Map map = new HashMap();
        map.put("birthDate",a.getTime().getTime());
        modifyUserInfo(map, new CallBack() {
            @Override
            public void doCallBack() {
                display(mYear,mMonth,mDay);
            }
        });
    }

    public void pickImage(int id) {
        if (mPickImage != null) {
            mPickImage.destory();
        }
        mPickImage = new PickImageDialog(this, id, mLogisticsDataConnection) {
            @Override
            protected void onImageUpLoad(final  int id, final String url, String url1) {
                Log.d("onImageUpLoad callBack", url);
                mPhoto = url1;
                Map map =new HashMap();
                map.put("headPic",mPhoto);
                modifyUserInfo(map, new CallBack() {
                    @Override
                    public void doCallBack() {
                        UIHelper.displayImage((CircleImg) BuyerInfoActivity.this.findViewById(id), url);
                    }
                });
            }
        };
        mPickImage.show();
    }

    /**
     * 修改信息成功回调
     */
    public interface  CallBack{
       void doCallBack();
    }

    /**
     * 修改用户信息
     * @param map
     */
    private void modifyUserInfo(Map map,final CallBack callBack) {
        showLoading();
        rdm.updateBuyerInfo(map,UserHelper.isBuyerLogin(),UserHelper.getBuyerId(),UserHelper.getBuyertoken());
        rdm.responseListener = new ResponseListener(){
            @Override
            public void onSuccess(final String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.doCallBack();
                        ToastHelper.makeToast(successInfo);
                    }
                });
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
            @Override
            public void onFinish() {
                dissLoading();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Logs.d(""+requestCode+resultCode);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        Map map =new HashMap();
        switch(requestCode){
            case RQ_LASTNAME:
                final String text1 = data.getStringExtra("text");
                map.clear();
                if(TextUtils.isBlank(text1)){
                    return;
                }
                buyerInfoLocal.setLastName(text1);
                map.put("lastName",text1);
                modifyUserInfo(map, new CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_lastName.setTv_doc(text1);
                    }
                });
                break;
            case RQ_FIRSTNAME:
                final String text2 = data.getStringExtra("text");
                map.clear();
                if(TextUtils.isBlank(text2)){
                    return;
                }
                buyerInfoLocal.setFirstName(text2);
                map.put("firstName",text2);
                modifyUserInfo(map,new CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_firstName.setTv_doc(text2);
                    }
                });
                break;
            case RQ_MOBILE:
                final String text3 = data.getStringExtra("text");
                map.clear();
                if(!BaseValidator.MobileMatch(text3)){
                    ToastHelper.makeErrorToast(mTranslatesString.getCommon_mobilestyleerror());
                    return;
                }
                buyerInfoLocal.setMobile(text3);
                map.put("mobile",text3);
                modifyUserInfo(map, new CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_mobile.setTv_doc(text3);
                    }
                });
                break;
            case RQ_SEX:
                final CellText sexi = (CellText) data.getSerializableExtra("object");
                if(sexi.getValue()==null){
                    buyerInfoLocal.setSex(Integer.parseInt(sexi.getValue()));
                }
                map.clear();
                map.put("sex",sexi.getValue());
                modifyUserInfo(map, new CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_sex.setTv_doc(sexi.getText());
                    }
                });
                break;
            default:
                break;

        }
    }
}
