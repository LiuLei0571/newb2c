package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.LaunchActivity;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.seller.BaseSetCompanyActivity;
import com.example.b2c.activity.seller.HomeActivity;
import com.example.b2c.activity.seller.SelectSellerTypeActivity;
import com.example.b2c.fragment.ZTHBaseFragment;
import com.example.b2c.fragment.livesCommunity.LivesHomeFragment;
import com.example.b2c.fragment.livesCommunity.LivesMeFragment;
import com.example.b2c.helper.UserHelper;

import java.util.ArrayList;
import java.util.List;

public class LivesCommuntiyHomeActivty extends TempBaseActivity implements View.OnClickListener {

    private FrameLayout lives_frame;
    private RadioButton lives_home_button;
    private RadioButton lives_home_me;
    private RadioGroup lives_group;
    private List<TempleBaseFragment> fragments;
    private TextView tv_title;
    private View fabu;
    private ImageButton back;

    @Override
    public int getRootId() {
        return R.layout.activity_lives_communtiy_home_activty;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.toolbar_title);
        back = (ImageButton) findViewById(R.id.toolbar_back);
        fabu = findViewById(R.id.home_fabu);
        TextView tv_fabu= (TextView) findViewById(R.id.tv_fabu);
        tv_fabu.setText(mTranslatesString.getCommon_fabu());
        lives_frame = (FrameLayout) findViewById(R.id.lives_frame);
        lives_home_button = (RadioButton) findViewById(R.id.lives_home_button);
        lives_home_me = (RadioButton) findViewById(R.id.lives_home_me);
        lives_group = (RadioGroup) findViewById(R.id.lives_group);
        lives_home_button.setText(mTranslatesString.getCommon_livescommunity());
        lives_home_me.setText(mTranslatesString.getLogistic_accountmanage());

    }
    private void initData(){
        fragments = new ArrayList<>();
        fragments.add(new LivesHomeFragment());
        fragments.add(new LivesMeFragment());
        loagFragment(null, fragments.get(0));
        tv_title.setText(mTranslatesString.getCommon_livescommunity());
    }
    //定义存储当前位置的变量
    private TempleBaseFragment currentFragment;
    private void initListener(){
        lives_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.lives_home_button:
                        loagFragment(currentFragment, fragments.get(0) );
                        tv_title.setText(mTranslatesString.getCommon_livescommunity());
                        break;
                    case R.id.lives_home_me:
                        loagFragment(currentFragment, fragments.get(1) );
                        tv_title.setText(mTranslatesString.getSeller_managermanager());
                        break;
                }
            }
        });
        fabu.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    //点击按钮切换fragment
    private void loagFragment(TempleBaseFragment from, TempleBaseFragment to) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (from == null) {
            //第一次进入时添加默认的fragment
            ft.add(R.id.lives_frame, to).commit();
            currentFragment = to;
        } else {

            if (from != to) {
                //如果不相等的时候说明是切换fragment
                if (to.isAdded()) {
                    //没有添加过了就将点击处的fragment替换之前的
                    ft.hide(from).show(to).commit();
                } else {
                    ft.hide(from).add(R.id.lives_frame, to).commit();
                }
                currentFragment = to;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_fabu:
                if (!UserHelper.isLivesLogin()){
                    //跳转到登录界面
                    startActivity(new Intent(getApplication(),LivesCommunityLoginActivity.class).putExtra("tag","fabu"));
                }else{
                    //跳转到类目选择界面界面
                    startActivity(new Intent(getApplication(),LeiMuListActivity.class));
                }
                break;
            case R.id.toolbar_back:
                if (UserHelper.getSellerFromLocal().getUserId() > 0) {
                    if (UserHelper.getSellerStep() != BaseSetCompanyActivity.SELLERSTEPTHIRD) {
                        Intent intent = new Intent(getApplication(), SelectSellerTypeActivity.class);
                        intent.putExtra("type", BaseSetCompanyActivity.LAUNCH);
                        startActivity(intent);
                    }
                    else{
                        //卖家首页
                        startActivity(new Intent(getApplication(), HomeActivity.class));
                    }
                }else{
                    finish();
                }
                break;
        }
    }
}
