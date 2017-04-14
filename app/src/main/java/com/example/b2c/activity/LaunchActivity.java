package com.example.b2c.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.activity.logistic.LogisticsHomeActivity;
import com.example.b2c.activity.seller.BaseSetCompanyActivity;
import com.example.b2c.activity.seller.HomeActivity;
import com.example.b2c.activity.seller.SelectSellerTypeActivity;
import com.example.b2c.activity.staff.StaffHomeActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.TranslatesListener;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.response.translate.Translates;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.RemoteDataConnection;

public class LaunchActivity extends Activity {
    protected boolean unLogin = false;
    public int userId = -1;
    public String token = "";
    public RemoteDataConnection rdm;
    private TimeCount time;
    public static MobileStaticTextCode translates_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        rdm = RemoteDataConnection.getInstance();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.launch);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(imageView);
        startAnim(imageView);

        time = new TimeCount(2000, 1000);
        time.start();
    }

    /**
     * 开启动画
     */
    private void startAnim(ImageView imageView) {

        AnimationSet set = new AnimationSet(false);


        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(2000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态

        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态

        set.addAnimation(scale);
        set.addAnimation(alpha);

        // 设置动画监听
        set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });

        imageView.startAnimation(set);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            rdm.GetTranslate(unLogin, userId, token, null);
            rdm.translatesListener = new TranslatesListener() {
                @Override
                public void onFinish() {
                    translates_String = SPHelper.getBean("translate", MobileStaticTextCode.class);
//                    Intent intent = new Intent(LaunchActivity.this, ReleaseProductActivity.class);
//                    startActivity(intent);
                    if (UserHelper.getSellerFromLocal().getUserId() > 0) {
                        /**
                         * 获取卖家商家信息的注册步揍，没有注册完，就重新填写
                         */
                        if (UserHelper.getSellerStep() != BaseSetCompanyActivity.SELLERSTEPTHIRD) {
                            Intent intent = new Intent(LaunchActivity.this, SelectSellerTypeActivity.class);
                            intent.putExtra("type", BaseSetCompanyActivity.LAUNCH);
                            startActivity(intent);
                        } else {
                            //卖家首页
                            startActivity(new Intent(getApplication(), HomeActivity.class));
                        }
                    } else if (UserHelper.getExpressType() == 2) {
                        //物流首页
                        startActivity(new Intent(getApplication(), LogisticsHomeActivity.class));
                    } else if (UserHelper.getExpressType() == 3) {
                        //快递小哥首页
                        startActivity(new Intent(getApplicationContext(), StaffHomeActivity.class));
                    } else {
                        //买家首页
                        startActivity(new Intent(getApplication(), MainActivity.class));
                    }
                    LaunchActivity.this.finish();
                }

                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast("request error ...");
                }

                @Override
                public void onSuccess(Translates translates) {
                    SPHelper.putBean("translate", translates.getMobileStaticTextCode());
                    SPHelper.putBean("options", translates.getOptionList());
                    Logs.d(translates.getOptionList().getCargoSourceTypeCode().toString() + "");
                }

                @Override
                public void onError(int errorNO, String errorInfo) {
                    ToastHelper.makeErrorToast(errorInfo);
                }
            };
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }
}
