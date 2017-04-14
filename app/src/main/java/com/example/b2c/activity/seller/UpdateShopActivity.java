package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.seller.ShopDetail;
import com.example.b2c.widget.EditTextWithDelete;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class UpdateShopActivity extends TempBaseActivity {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.et_phone)
    EditTextWithDelete mEtPhone;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    private ShopDetail mShopDetail;
    private int type;//判断修改价格还是修改运费,0:修改价格 1:修改运费
    private String change;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_update_shop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShopDetail = (ShopDetail) getIntent().getSerializableExtra("shopDetail");
        type = getIntent().getIntExtra("category", 0);
        mBtnSubmit.setText(mTranslatesString.getCommon_sure());
        if (mShopDetail != null) {
            if (type == 0) {
                setTitle(mTranslatesString.getGoods_changeprice());
                mTvTitle.setText(mTranslatesString.getCommon_sampleprice());
                mEtPhone.setHint(mTranslatesString.getGoods_inputprice());
            } else {
                setTitle(mTranslatesString.getGoods_changefee());
                mTvTitle.setText(mTranslatesString.getCommon_freight());
                mEtPhone.setHint(mTranslatesString.getGoods_inputfee());
            }
        }

    }

//    @OnClick(R.id.btn_submit)
//    public void onClick() {
//        showLoading();
//        change = mEtPhone.getText().toString().trim();
//        if (type == 0) {
//            sellerRdm.updateShop(ConstantS.BASE_URL + "sample/updateSampleInfo.htm", change, 0, mShopDetail.getId());
//        } else if (type == 1) {
//            sellerRdm.updateShop(ConstantS.BASE_URL + "sample/updateSampleInfo.htm", change, 1, mShopDetail.getId());
//
//        }
//        sellerRdm.mResponseListener = new ResponseListener() {
//            @Override
//            public void onFinish() {
//                dissLoading();
//            }
//
//            @Override
//            public void onSuccess(String errorInfo) {
//                ToastHelper.makeToast(errorInfo);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int errorNO, String errorInfo) {
//                showError(UpdateShopActivity.this, errorInfo);
//            }
//
//            @Override
//            public void onLose() {
//               ToastHelper.makeErrorToast(request_failure);
//            }
//        };
//    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
