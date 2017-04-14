package com.example.b2c.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.BuyerInfoActivity;
import com.example.b2c.activity.BuyerSetActivity;
import com.example.b2c.activity.MyAddrActivity;
import com.example.b2c.activity.MyCouponsActivity;
import com.example.b2c.activity.OrderActivityNew;
import com.example.b2c.activity.UserSafeActivity;
import com.example.b2c.activity.common.OtherUserLoginActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.BuyerInfo;
import com.example.b2c.widget.CircleImg;
import com.example.b2c.widget.SettingItemView;

import org.apache.http.util.TextUtils;

import static com.example.b2c.activity.common.BaseActivity.request_failure;

/**
 * 我的
 */
public class HomeMineFragment extends BaseFragment implements OnClickListener {

    private static final int RQ_BUYERINFO = 51;
    private View mView;
    private SettingItemView siv_mine_order, siv_mine_collection, siv_mine_message,
            siv_mine_security, siv_mine_good, siv_mine_cas,siv_mine_coupons;
    private CircleImg ci_avatarImg;
    private Button btn_2login, btn_logout;
    private TextView tv_username;

    private RelativeLayout top, rl_head;
    private ImageView toolbar_back;
    private TextView toolbar_title, toolbar_right_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!UserHelper.isBuyerLogin()) {
            mView = inflater.inflate(R.layout.unlogin_layout, container, false);
            btn_2login = (Button) mView.findViewById(R.id.btn_to_login);
            btn_2login.setText(mTranslatesString.getCommon_tologin());
            btn_2login.setOnClickListener(this);
        } else {
            mView = inflater.inflate(R.layout.mine_layout, container, false);
            top = (RelativeLayout) mView.findViewById(R.id.top);
            toolbar_back = (ImageView) top.findViewById(R.id.toolbar_back);
            toolbar_title = (TextView) top.findViewById(R.id.toolbar_title);
            toolbar_right_text = (TextView) top.findViewById(R.id.toolbar_right_text);

            rl_head = (RelativeLayout) mView.findViewById(R.id.rl_head);
            siv_mine_order = (SettingItemView) mView.findViewById(R.id.siv_mine_order);
            siv_mine_collection = (SettingItemView) mView.findViewById(R.id.siv_mine_collection);
            siv_mine_message = (SettingItemView) mView.findViewById(R.id.siv_mine_message);
            siv_mine_security = (SettingItemView) mView.findViewById(R.id.siv_mine_security);
            siv_mine_good = (SettingItemView) mView.findViewById(R.id.siv_mine_good);
            siv_mine_cas = (SettingItemView) mView.findViewById(R.id.siv_mine_cas);
            siv_mine_coupons = (SettingItemView) mView.findViewById(R.id.siv_mine_coupons);
            ci_avatarImg = (CircleImg) mView.findViewById(R.id.ci_avatarImg);
            tv_username = (TextView) mView.findViewById(R.id.tv_user);
            btn_logout = (Button) mView.findViewById(R.id.btn_logout);
            toolbar_right_text.setOnClickListener(this);
            rl_head.setOnClickListener(this);
            siv_mine_order.setOnClickListener(this);
            siv_mine_collection.setOnClickListener(this);
            siv_mine_message.setOnClickListener(this);
            siv_mine_security.setOnClickListener(this);
            siv_mine_good.setOnClickListener(this);
            siv_mine_cas.setOnClickListener(this);
            siv_mine_coupons.setOnClickListener(this);
            btn_logout.setOnClickListener(this);
            initText();
            this.isPrepared = true;
            lazyLoad();
        }
        return mView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
    }

    private void initData() {
        showLoading();
        udm.getBuyerInfo(UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
        udm.mOneDataListener = new OneDataListener<BuyerInfo>() {
            @Override
            public void onSuccess(final BuyerInfo buyerInfo, String successInfo) {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isBlank(buyerInfo.getHeadPic())) {
                            ci_avatarImg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mine_head));
                        } else {
                            loader.displayImage(ConstantS.BASE_PIC_URL + buyerInfo.getHeadPic(), ci_avatarImg, options);
                        }
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

    public void initText() {
        tv_username.setText(username);
        toolbar_back.setVisibility(View.INVISIBLE);
        toolbar_title.setText(mTranslatesString.getCommon_mine());
        toolbar_right_text.setText(mTranslatesString.getCommon_setting());
        siv_mine_order.setTv_text(mTranslatesString.getCommon_myorder());
        siv_mine_security.setTv_text(mTranslatesString.getCommon_usersafe());
        siv_mine_good.setTv_text(mTranslatesString.getCommon_receivelocation());
        siv_mine_cas.setTv_text(mTranslatesString.getConnom_shoukuandizhi());
        siv_mine_coupons.setTv_text(mTranslatesString.getCommon_mycoupon());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siv_mine_order:
                Intent i_order = new Intent(mContext, OrderActivityNew.class);
                startActivity(i_order);
                break;
            case R.id.siv_mine_security:
                Intent i_user_safe = new Intent(mContext, UserSafeActivity.class);
                startActivity(i_user_safe);
                break;
            case R.id.siv_mine_good:
                Intent i_manage_good_addr = new Intent(mContext, MyAddrActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("addressType", 10);//收货地址
                i_manage_good_addr.putExtras(bundle1);
                startActivity(i_manage_good_addr);
                break;
            case R.id.siv_mine_cas:
                Intent i_manage_cas_addr = new Intent(mContext, MyAddrActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("addressType", 20);//收款地址
                i_manage_cas_addr.putExtras(bundle2);
                startActivity(i_manage_cas_addr);
                break;
            case R.id.siv_mine_coupons:
                Intent i_manage_coupons = new Intent(mContext, MyCouponsActivity.class);
                startActivity(i_manage_coupons);
                break;
            case R.id.btn_to_login:
                Intent intent = new Intent(mContext, OtherUserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_head://查看个人信息
                Intent i_to_userInfo = new Intent(mContext, BuyerInfoActivity.class);
                startActivityForResult(i_to_userInfo, RQ_BUYERINFO);
                break;
            case R.id.toolbar_right_text://设置
                Intent i_to_settle = new Intent(mContext, BuyerSetActivity.class);
                startActivity(i_to_settle);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_BUYERINFO) {
            initData();
        }
    }
}
