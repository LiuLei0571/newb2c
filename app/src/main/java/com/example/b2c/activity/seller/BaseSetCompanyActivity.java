package com.example.b2c.activity.seller;

import android.os.Bundle;

import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.SellerCompanyEntry;

/**
 * 填写公司信息--基类
 */
public abstract class BaseSetCompanyActivity extends TempBaseActivity {

    /**
     * 商家信息提交步揍
     */
    public static final int SELLERSTEPNULL = 0;//没有提交任何商家信息
    public static final int SELLERSTEPFIRST = 10;//商家信息提交
    public static final int SELLERSTEPSECOND = 20;//公司负责人信息提交
    public static final int SELLERSTEPTHIRD = 40;//结算账号信息提交

    /**
     * 操作类型
     * 注册，登录，修改
     */
    public static final int LAUNCH = 1;
    public static final int REGISTER = 1;
    public static final int LOGIN = 2;
    public static final int UPDATE = 3;
    /**
     * 卖家类型：
     * 0： 个人
     * 1： 企业（绿色字段为个人；蓝色为企业字段）
     */
    public static final int PERSONAL = 0;
    public static final int COMPANY = 1;
    /**
     * 默认操作类型
     * 1： 注册
     */
    int currentType = 1;//默认注册
    /**
     * 默认卖家类型：
     * 0： 个人
     */
    int currentSellerType = 0;

    protected SellerCompanyEntry sellerCompanyEntry = null;

    protected abstract void initUI();


    protected void getSellerCompanyEntry() {
        showLoading();
        sellerRdm.getCompanyRegInfo();
        sellerRdm.mOneDataListener = new OneDataListener<SellerCompanyEntry>() {
            @Override
            public void onSuccess(SellerCompanyEntry companyEntry, String successInfo) {
                sellerCompanyEntry = companyEntry;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                    }
                });
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;

    }

    public void setCurrentSellerType(int currentSellerType) {
        this.currentSellerType = currentSellerType;
    }
}
