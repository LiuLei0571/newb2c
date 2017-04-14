package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Md5Encrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 用途：添加子账号，编辑子账号
 * 这两个功能放在一起了，用tag来判断是进行新增还是修改
 * 新增密码邮箱用户名都可以改变，修改不行所以修改的时候用户名，密码，邮箱不能点击，而且这三项的
 * 为空判断也不用判断，
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 * ModifyTime  16/12/03
 */

public class AddAccountActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private EditText et_login_name;
    private EditText et_password;
    private EditText tv_username;
    private EditText et_ming;
    private EditText et_phone1;
    private EditText et_beizhu;
    private EditText et_youxiang;
    private TextView btn_add_zizhanghao;
    private TextView toolbar_right;
    private Response response;
    private TextView tv_login_name;
    private TextView tv_password;
    private TextView tv_xing_q;
    private TextView tv_ming_q;
    private TextView tv_email_q;
    private TextView tv_phone_q;
    private TextView tv_beizhu;
    private View view_zhedang;
    private LinearLayout ll_mima;
    private LinearLayout ll_youxiang;
    private String tag;
    private String accountId;
    private String ss;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_add_account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initClick();
    }


    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right = (TextView) findViewById(R.id.toolbar_right_text);
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_username = (EditText) findViewById(R.id.tv_username);
        et_ming = (EditText) findViewById(R.id.et_ming);
        et_phone1 = (EditText) findViewById(R.id.et_phone1);
        et_beizhu = (EditText) findViewById(R.id.et_beizhu);
        et_youxiang = (EditText) findViewById(R.id.et_youxiang);
        btn_add_zizhanghao = (TextView) findViewById(R.id.btn_add_zizhanghao);
        ll_mima = (LinearLayout) findViewById(R.id.ll_mima);
        ll_youxiang = (LinearLayout) findViewById(R.id.ll_youxiang);
        toolbar_back.setOnClickListener(this);
        btn_add_zizhanghao.setOnClickListener(this);
        toolbar_right.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getLogistic_sonaccountinfo());
        //根据不同页面跳转过来来改变右上角是编辑还是保存，如果点击新增过来的右上角显示保存，如果是点击item则显示编辑
        //新增的时候用户名是可以改变的，但是如果是修改用户名不能改动
        tv_login_name = (TextView) findViewById(R.id.tv_login_name);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_xing_q = (TextView) findViewById(R.id.tv_xing_q);
        tv_ming_q = (TextView) findViewById(R.id.tv_ming_q);
        tv_phone_q = (TextView) findViewById(R.id.tv_phone_q);
        tv_email_q = (TextView) findViewById(R.id.tv_email_q);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        tv_login_name.setText(mTranslatesString.getCommon_username());
        tv_password.setText(mTranslatesString.getCommon_password());
        tv_xing_q.setText(mTranslatesString.getCommon_xing());
        tv_ming_q.setText(mTranslatesString.getCommon_ming());
        tv_phone_q.setText(mTranslatesString.getCommon_tellphone());
        tv_email_q.setText(mTranslatesString.getCommon_email());
        tv_beizhu.setText(mTranslatesString.getApply_remark());
        view_zhedang = (View) findViewById(R.id.view_zhedang);
    }

    private void initData() {
        Intent intent = getIntent();
        //判断是哪个页面跳转过来的标记
        tag = intent.getStringExtra("add2bianji");

        if (tag.equals("add")) {
            ///说明点击增加跳转过来的
            btn_add_zizhanghao.setVisibility(View.GONE);
            ll_mima.setVisibility(View.VISIBLE);
            toolbar_right.setText(mTranslatesString.getCommon_save());
        } else {
            //说明是点击子账号条目跳转过来的，这个时候编辑框不能编辑，y
            toolbar_right.setText(mTranslatesString.getCommon_edit());
            //接收点击item跳转过来的接收数据
            accountId = intent.getStringExtra("accountId");
            String loginName = intent.getStringExtra("loginName");
            String firstName = intent.getStringExtra("firstName");
            String lastName = intent.getStringExtra("lastName");
            String mobile = intent.getStringExtra("mobile");
            String email = intent.getStringExtra("email");
            String remark = intent.getStringExtra("remark");
            String status = intent.getStringExtra("status");
            et_login_name.setText(loginName);
            tv_username.setText(lastName);
            et_ming.setText(firstName);
            et_phone1.setText(mobile);
            et_beizhu.setText(remark);
            et_youxiang.setText(email);
            //将遮挡布的控件向消费掉不往下传
            view_zhedang.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            if (status.equals("0")){
                //当是0的时候是启用状态，所以按钮显示停用，点击进行停用
                btn_add_zizhanghao.setText(mTranslatesString.getCommon_stopuse());
                type=1;
            }else{
                //反之
                btn_add_zizhanghao.setText(mTranslatesString.getCommon_beginuse());
                type=0;
            }
            et_login_name.setEnabled(false);
            et_youxiang.setEnabled(false);
            //TODO 邮箱跟用户名不能点击

        }
    }
    private void initClick(){

    }
    private int type;
    //记录此时右上角按钮的状态
    private boolean isBaocun=false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back://返回
                finish();
                break;
            case R.id.btn_add_zizhanghao:
                if (type==1){
                    //进行停用
                    status("1");
                }if(type==0){
                    //进行启用
                    status("0");
                }
                break;
            case R.id.toolbar_right_text://点击右上角的按钮，在这判断此时是保存，还是编辑
               if (isBaocun){
                   //如果是保存
                   save();
               }else{
                   //如果是编辑，先将事件向下传递
                   view_zhedang.setVisibility(View.GONE);
                   toolbar_right.setText(mTranslatesString.getCommon_save());
                   isBaocun=true;
               }
                break;

        }
    }

    /**
     * 保存
     */
    private void save(){
        Map<String, String> params = new HashMap<>();

        final String names = et_login_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
            final String username = tv_username.getText().toString().trim();
        final String ming = et_ming.getText().toString().trim();
        final String phone1 = et_phone1.getText().toString().trim();
        final String email = et_youxiang.getText().toString().trim();
        String beizhu = et_beizhu.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputfirstname(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ming)) {
            Toast.makeText(this, mTranslatesString.getCommon_pleaseinputlastname(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone1)) {
            Toast.makeText(this, mTranslatesString.getValidate_inputtellphone(), Toast.LENGTH_SHORT).show();
            return;
        }
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        params.put("firstName", ming);
        params.put("lastName", username);
        params.put("mobile", phone1);
        params.put("remark", beizhu);
        if (tag.equals("add")) {
            //如果是新增
            if (TextUtils.isEmpty(names)) {
                Toast.makeText(this, mTranslatesString.getValidate_usernamenotnull(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, mTranslatesString.getCommon_pleaseinputemail(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, mTranslatesString.getValidate_passwordnotnull(), Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("password", Md5Encrypt.md5(password));
            params.put("email", email);
            params.put("loginName", names);
            mLogisticsDataConnection.getAddCourierRequest(ConstantS.BASE_URL_EXPRESS + "addChildAccount.htm",params);
        }else{
            //修改
         params.put("accountId", accountId);

        mLogisticsDataConnection.getAddCourierRequest(ConstantS.BASE_URL+"express/editChildAccount.htm",params);
        }
            mLogisticsDataConnection.mNodataListener = new NoDataListener() {

                @Override
                public void onSuccess(String success) {
                    setResult(10);
                    finish();
                }

                @Override
                public void onError(int errorNo, String errorInfo) {
                        ss = errorInfo;
                        handler.sendEmptyMessage(100);
                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onLose() {

                }
            };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 进行停用启用
     */
    public void status(final String status){
        new Thread(new Runnable() {
    @Override
    public void run() {
        Map<String,String> map=new HashMap<>();
        map.put("accountId",accountId);
        map.put("deliveryCompanyId",UserHelper.getExpressLoginId()+"");
        map.put("function",status);
        try {//ConstantS.BASE_URL +
            Response response = HttpClientUtil.doPost( ConstantS.BASE_URL +"express/setUpChildAccount.htm",
                    map, true, UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode()==200){
                String jieguo = response.getContent();
                if (jieguo != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jieguo);
                        JSONObject meta = jsonObject.getJSONObject("meta");
                        if (meta.getBoolean("success")) {
                            if (status.equals("0")) {
                                handler.sendEmptyMessage(200);
                            }else{
                                handler.sendEmptyMessage(300);
                            }
                        }else{
                            handler.sendEmptyMessage(400);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NetException e) {
            e.printStackTrace();
        }
    }
}).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    Toast.makeText(getApplication(),ss,Toast.LENGTH_LONG).show();
                    break;
                case 200:
                    type=1;
                    btn_add_zizhanghao.setText(mTranslatesString.getCommon_stopuse());
                    EventBus.getDefault().post(new FirstEvent("finish"));
                    break;
                case 300:
                    type=0;
                    btn_add_zizhanghao.setText(mTranslatesString.getCommon_beginuse());
                    EventBus.getDefault().post(new FirstEvent("finish"));
                    break;
                case 400:
//                    Toast.makeText(getApplication(),mTranslatesString.getConnon_caozuoshibai(),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
