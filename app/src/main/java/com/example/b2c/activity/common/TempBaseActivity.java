package com.example.b2c.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.fragment.BaseFragment;
import com.example.b2c.helper.ActivityHelper;
import com.example.b2c.helper.LoadingHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.observer.module.BaseObserver;
import com.example.b2c.widget.ActivityManager;
import com.example.b2c.widget.LogisticsDataConnection;
import com.example.b2c.widget.RemoteDataConnection;
import com.example.b2c.widget.SellerDataConnection;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author Created by xinzai on 2015/5/20.
 */
public abstract class TempBaseActivity extends BaseActivity {
    protected RelativeLayout mToolbar;
    protected TextView mTitle;
    protected TextView mTvNoData;
    protected ImageButton mBack;
    protected LinearLayout mRight;
    protected RelativeLayout mEmpty;
    protected RelativeLayout mTop;
    protected View mLine;
    private float mDensity;
    private boolean notification = false;
    private List<ActivityResultCallback> mActivityResultCallbacks = null;
    protected SellerDataConnection sellerRdm;
    protected LogisticsDataConnection mLogisticsDataConnection;
    protected RemoteDataConnection mbuyerDataConnection;
    protected ImageButton imageView = null;
    protected boolean isFirstLoad = true;

    public abstract int getRootId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootId());
        sellerRdm = SellerDataConnection.getInstance();
        mLogisticsDataConnection = LogisticsDataConnection.getInstance();
        mbuyerDataConnection = RemoteDataConnection.getInstance();
        DisplayMetrics metrics = new DisplayMetrics();
        ButterKnife.bind(this);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
        ActivityHelper.create(this);

    }

    @Override
    protected void onChange(String type, BaseObserver eventType) {

    }

    @Override
    protected String[] getObserverEventType() {
        return new String[0];
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    public void init() {
        mToolbar = (RelativeLayout) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTop = (RelativeLayout) findViewById(R.id.top);
        mLine = findViewById(R.id.line);
        mBack = (ImageButton) findViewById(R.id.toolbar_back);
        mEmpty = (RelativeLayout) findViewById(R.id.empty);
        mTvNoData = (TextView) findViewById(R.id.tv_nodata);
        if (mTvNoData != null && !TextUtils.isEmpty(mTranslatesString.getCommon_nodata())) {
            mTvNoData.setText(mTranslatesString.getCommon_nodata());
        }

        if (mBack != null) {
            mBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onBackClick();
                }
            });
        }
        mRight = (LinearLayout) findViewById(R.id.toolbar_right);
    }

    @Override
    public void setTitle(int titleId) {
        if (mTitle != null) {
            mTitle.setText(titleId);
            super.setTitle(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    public void onBackClick() {
        finish();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
            super.setTitle(title);
        } else {
            super.setTitle(title);
        }
    }

    public void setTitleTextColor(int textColor) {
        if (mTitle != null) {
            mTitle.setTextColor(textColor);
        }
    }

    protected ImageButton addImage(int drawable, View.OnClickListener listener) {
        if (mRight != null) {
            imageView = new ImageButton(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(drawable);
            imageView.setPadding((int) (mDensity * 10), 0, (int) (mDensity * 10), 0);
            imageView.setOnClickListener(listener);
            imageView.setBackgroundResource(R.drawable.item_selector);
            mRight.addView(imageView);
        }
        return imageView;
    }

    protected TextView addText(int text, View.OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setMinWidth((int) (mDensity * 44));
            textView.setPadding((int) (mDensity * 10), 0, (int) (mDensity * 10), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextColor(Color.BLACK);
            textView.setOnClickListener(listener);
            textView.setBackgroundResource(R.drawable.item_selector);
            mRight.addView(textView);
        }
        return textView;
    }

    protected TextView addText(String text, View.OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setMinWidth((int) (mDensity * 44));
            textView.setPadding((int) (mDensity * 10), 0, (int) (mDensity * 10), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextColor(Color.WHITE);
            textView.setOnClickListener(listener);
            textView.setBackgroundResource(R.drawable.item_selector);
            mRight.addView(textView);
        }
        return textView;
    }

    @Override
    public void finish() {
        if (notification) {
            int size = ActivityManager.size();
            if (size <= 1) {
                //此时需要打开主页
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        ActivityHelper.destroy(this);
        super.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mActivityResultCallbacks != null) {
            for (ActivityResultCallback callback : mActivityResultCallbacks) {
                callback.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void addActivityResultCallback(ActivityResultCallback callback) {
        if (mActivityResultCallbacks == null) {
            mActivityResultCallbacks = new ArrayList<>();
        }
        if (mActivityResultCallbacks.size() >= 1) mActivityResultCallbacks.clear();
        mActivityResultCallbacks.add(callback);
    }

    public void removeActivityResultCallback(ActivityResultCallback callback) {
        if (mActivityResultCallbacks != null) {
            mActivityResultCallbacks.remove(callback);
        }
    }

    public interface ActivityResultCallback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }


    protected void getIntent(BaseActivity baseActivity, Class cls) {
        Intent intent = new Intent(baseActivity, cls);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityHelper.destroy(this);
        dissLoading();
    }

    @Override
    public void onBackPressed() {
        dissLoading();
        super.onBackPressed();

    }

    protected void showLoading(String msg) {
        LoadingHelper.showLoading(this, msg);
    }

    protected void showLoading() {
        LoadingHelper.showLoading(this);
    }

    protected void dissLoading() {
        LoadingHelper.dismiss();
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG);
    }

    protected void showNetError(final Context context) {
        ToastHelper.makeErrorToast(mTranslatesString.getCommon_neterror());

    }

    protected void showError(final Context context, final String errorInfo) {
        ToastHelper.makeErrorToast(errorInfo);
    }


    public synchronized BaseFragment setCurrentTab(int position, HomePage[] fragmentPage) {
        BaseFragment baseFragment = null;
        if (imageView != null) {

            if (position == 0) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        if (position >= 0 && position < fragmentPage.length) {
            HomePage page = fragmentPage[position];
            Class<? extends BaseFragment> cls = page.fragmentCls;
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        continue;
                    }
                    if (cls.isInstance(fragment)) {
                        baseFragment = (BaseFragment) fragment;

                        trans.attach(baseFragment);
                    } else {
                        trans.detach(fragment);
                    }
                }
            }
            if (baseFragment == null) {

                try {
                    baseFragment = cls.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (baseFragment != null) {
                    trans.add(android.R.id.tabcontent, baseFragment, page.tag);
                }
            }
            trans.commitAllowingStateLoss();
        }
        return baseFragment;
    }

    public class HomePage {
        public String tag;
        public Class<? extends BaseFragment> fragmentCls;

        public HomePage(String tag, Class<? extends BaseFragment> fragmentCls) {
            this.tag = tag;
            this.fragmentCls = fragmentCls;
        }
    }
}

