package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.logistics.AccountDetail;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;

/**
 * 用途：子账号编辑
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 * ModifyTime  16/12/03
 */

public class UpdateAccountActivity extends TempBaseActivity {
    @Bind(R.id.tv_username)
    EditTextWithDelete mEtUsername;
    @Bind(R.id.et_phone)
    EditTextWithDelete mEtPhone;
    @Bind(R.id.et_email)
    EditTextWithDelete mEtEmail;
    @Bind(R.id.et_address)
    EditTextWithDelete mEtAddress;
    private AccountDetail mAccountDetail;


    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @Bind(R.id.tv_email)
    TextView tv_email;
    @Bind(R.id.tv_location)
    TextView tv_location;


    @Override
    public int getRootId() {
        return R.layout.activity_logistics_update_account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountDetail = (AccountDetail) getIntent().getExtras().getSerializable("accountDetail");
        setTitle(mTranslatesString.getLogistic_sonaccountinfo());
        addText(mTranslatesString.getCommon_save(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isEmail(mEtEmail.getText().toString().trim())) {
                    ToastHelper.makeErrorToast(mTranslatesString.getNotice_pleaseinputcorrectemail());
                    return;
                }
                if (mEtPhone.getText().toString().trim().isEmpty()) {
                    ToastHelper.makeErrorToast(mTranslatesString.getCommon_pleaseinputcorrectmobile());
                    return;
                }
                showLoading();
                mLogisticsDataConnection.getUpdateCourierRequest(ConstantS.BASE_URL_EXPRESS + "editChildAccount.htm", mAccountDetail.getId(), mEtUsername.getText().toString(), mEtPhone.getText().toString(), mEtAddress.getText().toString(), mEtEmail.getText().toString());
                mLogisticsDataConnection.mNodataListener = new NoDataListener() {
                    @Override
                    public void onSuccess(String success) {
                        ToastHelper.makeToast(success);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onFinish();
                                getIntent(UpdateAccountActivity.this, ChildAccountListActivity.class);
                            }
                        });
                    }

                    @Override
                    public void onError(int errorNO,String errorInfo) {
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
        });
        initView();
        initText();
    }


    private void initView() {
        if (mAccountDetail != null) {
            mEtUsername.setText(mAccountDetail.getName());
            mEtPhone.setText(mAccountDetail.getMobile());
            mEtAddress.setText(mAccountDetail.getAddress());
            mEtEmail.setText(mAccountDetail.getEmail());
        }
    }

    private void initText() {
        tv_name.setText(mTranslatesString.getCommon_name());
        tv_mobile_number.setText(mTranslatesString.getCommon_mobile());
        tv_email.setText(mTranslatesString.getCommon_email());
        tv_location.setText(mTranslatesString.getCommon_locationname());
        mEtUsername.setHint(mTranslatesString.getCommon_pleaseinputusername());
        mEtAddress.setHint(mTranslatesString.getCommon_pleaseinputaddress());
        mEtEmail.setHint(mTranslatesString.getCommon_pleaseinputemail());
        mEtPhone.setHint(mTranslatesString.getCommon_pleaseinputcorrectmobile());
    }
}
