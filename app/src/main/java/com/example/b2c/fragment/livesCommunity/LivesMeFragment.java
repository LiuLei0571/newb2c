package com.example.b2c.fragment.livesCommunity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.LivesCommunity.LivesCommunityLoginActivity;
import com.example.b2c.activity.LivesCommunity.MyFaBuMessageActivity;
import com.example.b2c.activity.common.ClassifyActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.livesCommunity.HomeList;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.example.b2c.widget.RoundImageView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LivesMeFragment extends TempleBaseFragment implements View.OnClickListener {


    private RoundImageView lives_image;
    private TextView tv_lives_me_name;
    private TextView tv_my_fabu;
    private RelativeLayout rl_live_fabu;

    public LivesMeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lives_me;
    }

    @Override
    protected void initView(View rootView) {
        tv_lives_me_name= (TextView) rootView.findViewById(R.id.tv_lives_me_name);
        tv_my_fabu= (TextView) rootView.findViewById(R.id.tv_my_fabu);
        lives_image= (RoundImageView) rootView.findViewById(R.id.lives_image);
        rl_live_fabu= (RelativeLayout) rootView.findViewById(R.id.rl_live_fabu);
        tv_my_fabu.setText(mTranslatesString.getCommon_myfabu());
        rl_live_fabu.setOnClickListener(this);
        //请求服务器
        initData();
    }
    private void initData(){
        showLoading();
       //请求个人中心的接口
        MyHttpUtils instance = MyHttpUtils.getInstance();
        final Gson mGson=new Gson();
        instance.doGet(ConstantS.BASE_URL + "life/getUserInfo.htm", true,
                    UserHelper.getLivesID(), UserHelper.getLivesToken(), new MyHttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result, int code) {
                            if (code==200){
                                try {
                                JSONObject meta = new JSONObject(result).getJSONObject("meta");
                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    tv_lives_me_name.setText(data.getString("loginName"));
                                    ImageHelper.display(ConstantS.BASE_PIC_URL+data.getString("headPic"),lives_image);
                                }else{
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dissLoading();
                            }else{
                                dissLoading();
                            }
                        }

                        @Override
                        public void onFailture() {
dissLoading();
                        }
                    });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_live_fabu:
                //跳转到我的发布界面
                if (!UserHelper.isLivesLogin()){
                    startActivity(new Intent(getActivity(),LivesCommunityLoginActivity.class).putExtra("tag","me"));
                }else {
                    startActivity(new Intent(getActivity(), MyFaBuMessageActivity.class));
                }
                break;
        }
    }
}
