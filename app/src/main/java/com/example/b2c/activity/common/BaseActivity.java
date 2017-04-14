package com.example.b2c.activity.common;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.b2c.R;
import com.example.b2c.helper.ActivityHelper;
import com.example.b2c.helper.LoadingHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.observer.EventObserver;
import com.example.b2c.observer.EventSubject;
import com.example.b2c.observer.module.BaseObserver;
import com.example.b2c.widget.ActivityManager;
import com.example.b2c.widget.RemoteDataConnection;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.lang.ref.WeakReference;

import static com.example.b2c.helper.ToastHelper.makeErrorToast;
import static com.example.b2c.helper.ToastHelper.makeToast;

public abstract class BaseActivity extends FragmentActivity {
    public RemoteDataConnection rdm;
    protected MobileStaticTextCode mTranslatesString;
    protected OptionList mTranslatesStringList;
    public SharedPreferences.Editor editor;
    public ImageLoader loader;

    public boolean isLogin = UserHelper.isBuyerLogin();
    protected boolean unLogin = false;
    public int userId = UserHelper.getBuyerId();
    public String token = UserHelper.getBuyertoken();
    protected String username = UserHelper.getBuyerName();
    protected DisplayImageOptions options;
    public static String Loading;
    public static String Waiting;
    public static String request_failure;
    public static String net_error;
    public static String sureButtonText;
    public static String success;
    protected ActivityEventObserver mActivityEventObserver;

    /**
     * Activity被回收导致fragment的getActivity为null的解决办法
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//          super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        mTranslatesStringList = SPHelper.getBean("options", OptionList.class);
        if (mTranslatesString != null) {
            Loading = mTranslatesString
                    .getCommon_loading();
            Waiting = mTranslatesString
                    .getCommon_waiting();
            request_failure = mTranslatesString
                    .getNotice_requesterror();
            net_error = mTranslatesString.getCommon_neterror();
            sureButtonText = mTranslatesString.getCommon_makesure();
            success = mTranslatesString.getCommon_caozuochenggong();
        }
        ActivityManager.create(this);
        rdm = RemoteDataConnection.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_fail_image)
                .showImageForEmptyUri(R.drawable.ic_no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(false)
                .displayer(new SimpleBitmapDisplayer()).build();
        loader = ImageLoader.getInstance();
        mActivityEventObserver = new ActivityEventObserver(this);
        registerObserver(mActivityEventObserver);
    }

    public void showAlertDialog(String msg) {
        makeErrorToast(msg);
    }

    public void showAlertTextDialog(String msg) {
        makeToast(msg);
    }

    public void showProgressDialog(String msg) {
        LoadingHelper.showLoading(this, msg);
    }

    public void dismissProgressDialog() {
        LoadingHelper.dismiss();
    }

    public boolean isLastActivity() {
        return ActivityHelper.isLast(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeObserver(mActivityEventObserver);
        ActivityManager.destroy(this);
    }

    public void registerObserver(EventObserver observer) {
        final String[] observerEventTypes = getObserverEventType();//获取所有需要监听的业务类型
        if (observerEventTypes != null && observerEventTypes.length > 0) {
            final EventSubject eventSubject = EventSubject.getInstance();
            for (String eventType : observerEventTypes) {
                eventSubject.registerObserver(eventType, observer);
            }
        }
    }

    public void removeObserver(EventObserver observer) {
        final String[] observerEventTypes = getObserverEventType();//获取所有需要监听的业务类型
        if (observerEventTypes != null && observerEventTypes.length > 0) {
            final EventSubject eventSubject = EventSubject.getInstance();
            for (String eventType : observerEventTypes) {
                eventSubject.removeObserver(eventType, observer);
            }
        }
    }

    private static class ActivityEventObserver extends EventObserver {
        //添加弱引用，防止对象不能被回收
        private final WeakReference<BaseActivity> mActivity;

        public ActivityEventObserver(BaseActivity activity) {
            mActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void onChange(String type,BaseObserver eventType) {
            BaseActivity activity = mActivity.get();
            if (activity != null) {
                activity.onChange(type,eventType);
            }
        }
    }

    /**
     * 该方法会在具体的观察者对象中调用，可以根据事件的类型来更新对应的UI，这个方法在UI线程中被调用，
     * 所以在该方法中不能进行耗时操作，可以另外开线程
     *
     * @param eventType 事件类型
     */
    protected abstract void onChange(String type,BaseObserver eventType);

    /**
     * 通过这个方法来告诉具体的观察者需要监听的业务类型
     *
     * @return
     */
    protected abstract String[] getObserverEventType();
}