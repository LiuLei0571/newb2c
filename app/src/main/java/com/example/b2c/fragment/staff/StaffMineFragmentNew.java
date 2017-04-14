package com.example.b2c.fragment.staff;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.LivesCommunity.FaBuMessageActivity;
import com.example.b2c.activity.LivesCommunity.LivesCommuntiyHomeActivty;
import com.example.b2c.activity.staff.StaffSettingActivity;
import com.example.b2c.fragment.ZTHBaseFragment;

/**
 * 用途：
 * 作者：Created by john on 2017/2/13.
 * 邮箱：liulei2@aixuedai.com
 */


public class StaffMineFragmentNew extends ZTHBaseFragment implements View.OnClickListener {
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private TextView toolbar_right_text;
    private TextView tv_info;
    private RelativeLayout rl_staff_info;
    private TextView tv_message;
    private TextView tv_qufabu;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_mine_new;
    }

    @Override
    public void initView(View view) {
        toolbar_back= (ImageButton) view.findViewById(R.id.toolbar_back);
        toolbar_title= (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_right_text= (TextView) view.findViewById(R.id.toolbar_right_text);
        tv_info= (TextView) view.findViewById(R.id.tv_info);
        tv_message= (TextView) view.findViewById(R.id.tv_message);
        tv_qufabu = (TextView) view.findViewById(R.id.tv_qufabu);
        tv_qufabu.setOnClickListener(this);
        rl_staff_info= (RelativeLayout) view.findViewById(R.id.rl_staff_info);
        toolbar_back.setVisibility(View.GONE);
        toolbar_right_text.setText(mTranslatesString.getCommon_setting());
        tv_message.setText(mTranslatesString.getCommon_yongxin());
        tv_info.setText(mTranslatesString.getCommon_basicinfo());
        toolbar_right_text.setOnClickListener(this);
        rl_staff_info.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getLogistic_accountmanage());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:
                startActivity(new Intent(getActivity(), StaffSettingActivity.class));
                break;
            case R.id.rl_staff_info:
                startActivity(new Intent(getActivity(), StaffInfoActivity.class));
                break;
            case R.id.tv_qufabu:
                startActivity(new Intent(getActivity(), LivesCommuntiyHomeActivty.class));
                break;
//        }
        }
//    @Bind(R.id.tv_message)
//    TextView tvMessage;
//    @Bind(R.id.iv_mineone4)
//    ImageView ivMineone4;
//    @Bind(R.id.tv_info)
//    TextView mTvInfo;
//    @Bind(R.id.rl_staff_info)
//    RelativeLayout rlStaffInfo;
//    private TextView toolbar_right_text;
//    private ImageButton toolbar_back;
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.fragment_mine_new;
//    }
//
//    @Override
//    protected void initView(View rootView) {
//        toolbar_right_text = (TextView) rootView.findViewById(R.id.toolbar_right_text);
//        toolbar_right_text.setText("设置");
//        toolbar_back = (ImageButton) rootView.findViewById(R.id.toolbar_back);
//        toolbar_back.setVisibility(View.GONE);
//        mTvInfo.setText("基本信息");
//    }
//
//
//    @OnClick({R.id.toolbar_right, R.id.rl_staff_info})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.toolbar_right:
//                startActivity(new Intent(getActivity(), LanguageSettingActivity.class));
//                break;
//            case R.id.rl_staff_info:
//                break;
//        }
//    }
    }
}
