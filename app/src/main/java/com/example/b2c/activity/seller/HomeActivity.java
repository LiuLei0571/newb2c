package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.WebViewActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.BaseFragment;
import com.example.b2c.fragment.seller.HomeMineFragment;
import com.example.b2c.fragment.seller.HomeOrderFragment;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.util.Logs;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.example.b2c.R.id.rbt_order;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 * 卖家首页
 */

public class HomeActivity extends TempBaseActivity implements HomeOrderFragment.OnTitleListener {
    protected long mExitTime;
    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("workplate", HomeOrderFragment.class),
            new HomePage("mine", HomeMineFragment.class)
    };
    @Bind(rbt_order)
    RadioButton mRbtOrder;
    @Bind(R.id.rbt_mine)
    RadioButton mRbtMine;
    private String title;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBack.setVisibility(View.GONE);
        if (savedInstanceState == null) {
            mRight.setVisibility(View.VISIBLE);
            setTitle(mTranslatesString.getSeller_managermanager());
            setCurrentTab(1);
        }
        addText(mTranslatesString.getCommon_setting(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent(HomeActivity.this, SecurityActivity.class);
            }
        });
        addImage(R.drawable.ic_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("url", ConstantS.BASE_CHAT + "chat/sellerChat" + "?userId=" + UserHelper.getSellerID() + "&token=" + UserHelper.getSellerToken() + "&userType=" + "1" + "&appName=" + SPHelper.getString(Configs.APPNAME) + "&loginName=" + UserHelper.getSellerLoginName() + "&lang=" + SPHelper.getString(Configs.LANGUAGE));
                startActivity(intent);
                return;
            }
        });
        initText();
    }

    private void initText() {
        mRbtOrder.setText(mTranslatesString.getCommon_workplat());
        mRbtMine.setText(mTranslatesString.getSeller_managermanager());
    }


    @OnClick({R.id.rbt_order, R.id.rbt_mine})
    protected void getOnClick(View view) {
        int shopId = SPHelper.getInt("shopId");
        Logs.d("shopId", shopId + "");
        Logs.d("true***", (view.getId() == R.id.rbt_order) + "");
        if (shopId == 0 && view.getId() == R.id.rbt_order) {
            mRbtOrder.setChecked(false);
            mRbtMine.setChecked(true);
            ToastHelper.makeToast(mTranslatesString.getCommon_pleaseopenshopfirst());
            return;
        }
        switch (view.getId()) {
            case R.id.rbt_order:
                mRight.setVisibility(View.INVISIBLE);
                setCurrentTab(0);
                break;
            case R.id.rbt_mine:
                setCurrentTab(1);
                mRight.setVisibility(View.VISIBLE);
                setTitle(mTranslatesString.getSeller_managermanager());
                break;

        }
    }

    @Override
    public void onTitle(String title) {
        this.title = title;
        setTitle(title);
    }

    public class HomePage {
        public String tag;
        public Class<? extends BaseFragment> fragmentCls;

        public HomePage(String tag, Class<? extends BaseFragment> fragmentCls) {
            this.tag = tag;
            this.fragmentCls = fragmentCls;
        }
    }

    public synchronized BaseFragment setCurrentTab(int postion) {
        BaseFragment baseFragment = null;
        if (postion >= 0 && postion < fragmentPage.length) {
            HomePage page = fragmentPage[postion];
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
                    Bundle bundle = new Bundle();
                    bundle.putString("name", title);
                    baseFragment.setArguments(bundle);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeMineFragment.RQ_INIT) {
            Fragment f = getSupportFragmentManager().findFragmentByTag("mine");
            if (f != null) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
