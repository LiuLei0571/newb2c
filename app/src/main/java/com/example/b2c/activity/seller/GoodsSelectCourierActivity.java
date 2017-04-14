package com.example.b2c.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.common.LogisticDetailAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OwnLogisticResultListener;
import com.example.b2c.net.response.seller.LogisticDetail;

import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public class GoodsSelectCourierActivity extends TempBaseActivity {
    @Bind(R.id.list)
    ListView mList;
    private LogisticDetailAdapter mAdapter;
    int id;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_courier_select;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_selectexpress());
        id = getIntent().getIntExtra("id", 0);
        mAdapter = new LogisticDetailAdapter(this) {
            @Override
            public void onItemSelect(final LogisticDetail district) {
                sellerRdm.getSureLogistic(ConstantS.BASE_URL + "seller/order/sure.htm", id, district.getId());
                sellerRdm.mResponseListener = new ResponseListener() {
                    @Override
                    public void onSuccess(String errorInfo) {
                        mCallBack.onSuccess(district, errorInfo);
                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onLose() {
                        finish();
                    }
                };
            }
        };
        initData();
    }

    private void initData() {
        showLoading();
        sellerRdm.getOwnLogisticResult(ConstantS.BASE_URL + "deliver/company/own/list.htm");
        sellerRdm.mOwnLogisticResultListener = new OwnLogisticResultListener<LogisticDetail>() {
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                    ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final List<LogisticDetail> list, String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (list.isEmpty()) {
                            mEmpty.setVisibility(View.VISIBLE);
                            ToastHelper.makeToast(mTranslatesString.getCommon_toastsetexpress());
                            return;
                        }

                        mEmpty.setVisibility(View.GONE);
                        mAdapter.setData(list);
                        mList.setAdapter(mAdapter);
                    }
                });
            }

            @Override
            public void onError(int errorNO, final String errorInfo) {
                finish();
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }

    public static void select(Context context, int id, CallBack callBack) {
        Intent intent = new Intent(context, GoodsSelectCourierActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
        mCallBack = callBack;

    }

    private static CallBack mCallBack = null;

    public interface CallBack {
        void onSuccess(LogisticDetail district, String errorinfo);

        void onFailure(String status);

        void onCancel();
    }
}
