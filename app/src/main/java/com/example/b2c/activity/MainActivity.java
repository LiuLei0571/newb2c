package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.FragmentAdapter;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.observer.ObserverTypes;
import com.example.b2c.observer.module.BaseObserver;
import com.example.b2c.observer.module.HomeObserver;
import com.example.b2c.widget.CustomViewPager;

//这里因为是3.0一下版本，所以需继承FragmentActivity，通过getSupportFragmentManager()获取FragmentManager
//3.0及其以上版本，只需继承Activity，通过getFragmentManager获取事物
public class MainActivity extends TempBaseActivity {
    public final static int HOME_PAGE = 0;
    public final static int CLASSIFY = 1;
    public final static int SHOPPING_CART = 2;
    public final static int COLLECTION = 3;
    public final static int MINE = 4;
    public CustomViewPager viewpager;
    private RadioButton rb_home;
    private RadioButton rb_classify;
    private RadioButton rb_shopping_cart;
    private RadioButton rb_collection;
    private RadioButton rb_mine;
    private FragmentAdapter mAdapter;

    public static MainActivity instance = null;

    protected long mExitTime;

    @Override
    public int getRootId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onChange(String type, BaseObserver eventType) {
        if (!isLastActivity()) {
            return;
        }
        viewpager.setCurrentItem(((HomeObserver) eventType).getPosition());
        setCurrentItem(((HomeObserver) eventType).getPosition());
    }

    @Override
    protected String[] getObserverEventType() {
        return new String[]{ObserverTypes.HOME.getType()};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
    }

    public void initView() {
        viewpager = (CustomViewPager) findViewById(R.id.custom_viewpager);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_classify = (RadioButton) findViewById(R.id.rb_classify);
        rb_shopping_cart = (RadioButton) findViewById(R.id.rb_shopping_cart);
        rb_collection = (RadioButton) findViewById(R.id.rb_collection);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        rb_home.setOnClickListener(clickListener);
        rb_classify.setOnClickListener(clickListener);
        rb_shopping_cart.setOnClickListener(clickListener);
        rb_collection.setOnClickListener(clickListener);
        rb_mine.setOnClickListener(clickListener);
        initText();
    }

    private void setCurrentItem(int item) {
        rb_home.setChecked(false);
        rb_classify.setChecked(false);
        rb_shopping_cart.setChecked(false);
        rb_collection.setChecked(false);
        rb_mine.setChecked(false);
        switch (item) {
            case HOME_PAGE:
                rb_home.setChecked(true);
                break;
            case CLASSIFY:
                rb_classify.setChecked(true);
                break;
            case SHOPPING_CART:
                rb_shopping_cart.setChecked(true);
                break;
            case COLLECTION:
                rb_collection.setChecked(true);
                break;
            case MINE:
                rb_mine.setChecked(true);
                break;
        }
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rb_home:
                    viewpager.setCurrentItem(HOME_PAGE);
                    break;
                case R.id.rb_classify:
                    viewpager.setCurrentItem(CLASSIFY);
                    break;
                case R.id.rb_shopping_cart:
                    viewpager.setCurrentItem(SHOPPING_CART);
                    break;
                case R.id.rb_collection:
                    viewpager.setCurrentItem(COLLECTION);
                    break;
                case R.id.rb_mine:
                    viewpager.setCurrentItem(MINE);
                    break;
            }
        }

    };

    public void initText() {
        rb_home.setText(mTranslatesString.getCommon_home());
        rb_classify.setText(mTranslatesString.getCommon_classify());
        rb_shopping_cart.setText(mTranslatesString.getCommon_shoppingcart());
        rb_collection.setText(mTranslatesString.getCommon_favorites());
        rb_mine.setText(mTranslatesString.getCommon_mine());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode", requestCode + "");
        Log.d("currentItem", viewpager.getCurrentItem() + "");
        Log.d("id", mAdapter.getItem(viewpager.getCurrentItem()).getId() + "");
        Fragment f = getSupportFragmentManager().findFragmentById(mAdapter.getItem(viewpager.getCurrentItem()).getId());
        Log.d("f != null", (f != null) + "");
        if (f != null) {
            f.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastHelper.makeToast(mTranslatesString.getCommon_againlogput());
                mExitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        mTranslatesStringList = SPHelper.getBean("options", OptionList.class);
        initView();
    }
}
