package com.example.b2c.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.IAct;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.event.model.EmptyEvent;
import com.example.b2c.helper.EventHelper;
import com.example.b2c.helper.LoadingHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.RemoteDataConnection;
import com.example.b2c.widget.RemoteDataManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;

public abstract class BaseFragment extends Fragment implements IAct {
    public Context mContext;
    public RemoteDataManager rdm;
    public RemoteDataConnection udm;
    protected DisplayImageOptions options;
    public ImageLoader loader;
    protected MobileStaticTextCode mTranslatesString;
    protected boolean isLogin;
    public boolean unLogin = false;
    public int userId, countryCode;
    public String token;
    public String username;
    public String loading;
    public String waiting;
    public String requestFailure;
    public String sureButtonText;

    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;

    protected boolean  isVisible;

    // 标志位，isFirst,是否第一次加载。
    protected boolean isFirst = true;
public BaseFragment(){}
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logs.d("setUserVisibleHint"+isVisibleToUser);
        Logs.d("getUserVisibleHint()"+getUserVisibleHint());
        if (isVisibleToUser) {
            //可见时执行的操作
            isVisible = true;
            lazyLoad();
        } else {
            //不可见时执行的操作
            isVisible = false;
        }
    }

    //懒加载
    protected  void lazyLoad(){}

    public void showAlertDialog(String title, String msg) {
        DialogHelper.showDialog(mContext, title, msg,
                Gravity.CENTER, new DialogHelper.BtnTv(sureButtonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })).show();
    }

    public void dismissAlertDialog(Dialog dialog) {
        DialogHelper.closeDialog(dialog);
    }

    public void showProgressDialog(String title, String msg) {
        showLoading(msg);
    }

    public void showProgressDialog(int titleResId, int msgResId) {
        showProgressDialog(getString(titleResId), getString(msgResId));
    }

    public void dismissProgressDialog() {
        dissLoading();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rdm = RemoteDataConnection.getInstance();
        udm=RemoteDataConnection.getInstance();
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(0)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnFail(R.drawable.ic_no_image)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
        loader = ImageLoader.getInstance();
        mContext = getActivity();
        isLogin = UserHelper.isBuyerLogin();
        userId = UserHelper.getBuyerId();
        token = UserHelper.getBuyertoken();
        countryCode = 10;
        username = UserHelper.getBuyerName();
        if (mTranslatesString != null) {
            loading = mTranslatesString.getCommon_loading();//"加载中"
            waiting = mTranslatesString.getCommon_waiting();//"请稍候"
            requestFailure = mTranslatesString.getNotice_requesterror();//请求失败
            sureButtonText = mTranslatesString.getCommon_sure();//确定
        }

        EventHelper.register(this);
    }

    public void showDialogOnUI(final String title, final String msg) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showAlertDialog(title, msg);
                }
            });
        }
    }

    public void showToast(final String message) {
        ToastHelper.makeToast(message);
    }

    public void showErrorToast(final String message) {
        ToastHelper.makeErrorToast(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        dissLoading();
    }

    protected void showLoading(String msg) {
        LoadingHelper.showLoading(getActivity(), msg);
    }

    protected void showLoading() {
        LoadingHelper.showLoading(getActivity());
    }

    protected void dissLoading() {
        LoadingHelper.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissLoading();
        EventHelper.unregister(this);
    }


    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }

    @Subscribe
    public void onEvent(EmptyEvent event) {

    }
}
