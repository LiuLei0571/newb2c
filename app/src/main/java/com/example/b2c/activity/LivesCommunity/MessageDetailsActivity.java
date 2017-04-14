package com.example.b2c.activity.LivesCommunity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.WebViewActivity;
import com.example.b2c.adapter.GoodDetailBannerAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.helper.PicturePreviewActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.livesCommunity.LivesDetailsBean;
import com.example.b2c.widget.LazyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDetailsActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ViewPager lives_viewpagr;
    private LinearLayout dots_ll;
    private TextView tv_lives_title_content;
    private TextView tv_lives_miaoshu_title;
    private TextView tv_lives_miaoshu_content;
    private TextView tv_lianxiangshi;
    private TextView tv_dianhua;
    private RelativeLayout rl_pohone;
    private TextView tv_duanxin;
    private RelativeLayout rl_duanxin;
    private TextView tv_liaotian;
    private TextView tv_details_time;
    private RelativeLayout rl_liaotian;
    private TextView tv_wishijubao;
    private TextView tv_jubao;
    private RelativeLayout rl_jubao;
    private LinearLayout activity_message_details;
    private LivesDetailsBean livesDetailsBean;
    private ArrayList<String> imageList;
    private MyViewPageradapter myViewPageradapter;
    private List<ImageView> points;
    private Intent intent;

    @Override
    public int getRootId() {
        return R.layout.activity_message_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initLinter();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        lives_viewpagr = (ViewPager) findViewById(R.id.lives_viewpagr);
        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
        tv_lives_title_content = (TextView) findViewById(R.id.tv_lives_title_content);
        tv_details_time = (TextView) findViewById(R.id.tv_details_time);
        tv_lives_miaoshu_title = (TextView) findViewById(R.id.tv_lives_miaoshu_title);
        tv_lives_miaoshu_content = (TextView) findViewById(R.id.tv_lives_miaoshu_content);
        tv_lianxiangshi = (TextView) findViewById(R.id.tv_lianxiangshi);
        tv_dianhua = (TextView) findViewById(R.id.tv_dianhua);
        rl_pohone = (RelativeLayout) findViewById(R.id.rl_pohone);
        tv_duanxin = (TextView) findViewById(R.id.tv_duanxin);
        rl_duanxin = (RelativeLayout) findViewById(R.id.rl_duanxin);
        tv_liaotian = (TextView) findViewById(R.id.tv_liaotian);
        rl_liaotian = (RelativeLayout) findViewById(R.id.rl_liaotian);
        tv_wishijubao = (TextView) findViewById(R.id.tv_wishijubao);
        tv_jubao = (TextView) findViewById(R.id.tv_jubao);
        rl_jubao = (RelativeLayout) findViewById(R.id.rl_jubao);
        activity_message_details = (LinearLayout) findViewById(R.id.activity_message_details);
        //设置翻译串
        toolbar_title.setText(mTranslatesString.getCommon_xiangqing());
        tv_lives_miaoshu_title.setText(mTranslatesString.getCommon_xiangximiaoshu());
        tv_lianxiangshi.setText(mTranslatesString.getCommon_linkway());
        tv_dianhua.setText(mTranslatesString.getCommon_dianhua());
        tv_duanxin.setText(mTranslatesString.getCommon_duanxin());
        tv_liaotian.setText(mTranslatesString.getCommon_liaotian());
        tv_wishijubao.setText(mTranslatesString.getCommon_xujiaxinxi());
        tv_jubao.setText(mTranslatesString.getCommon_jubao());
    }

        private int lastPosition=0;
     private void initLinter(){
         intent = new Intent();
         lives_viewpagr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 int i = position % imageList.size();
                 points.get(lastPosition).setImageResource(R.drawable.huise);
                 points.get(i).setImageResource(R.drawable.baise);//改变当前点
                 lastPosition=i;
             }

             @Override
             public void onPageSelected(int position) {

             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
         rl_jubao.setOnClickListener(this);
         rl_pohone.setOnClickListener(this);
         rl_duanxin.setOnClickListener(this);
         rl_liaotian.setOnClickListener(this);
     }
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_jubao://举报
                if (!UserHelper.isLivesLogin()){
                    //跳转到登录界面
                    startActivity(new Intent(getApplication(),LivesCommunityLoginActivity.class).putExtra("tag","jubao")
                    .putExtra("id",livesDetailsBean.getId()+""));
                }else{
                    //跳转到反馈
                    intent.setClass(MessageDetailsActivity.this,JuBaoActivity.class);
                    intent.putExtra("tag","jubao");
                    intent.putExtra("id",livesDetailsBean.getId()+"");
                    startActivity(intent);
                }
                break;
            case R.id.rl_pohone://打电话
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone(livesDetailsBean.getContactsMobile());
                }
                break;
            case R.id.rl_duanxin://短信
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+livesDetailsBean.getContactsMobile()));
                startActivity(intent);
                break;
            case R.id.rl_liaotian://去聊天
                if (!UserHelper.isLivesLogin()){
                    //跳转到登录界面
                    startActivity(new Intent(getApplication(),LivesCommunityLoginActivity.class).putExtra("tag","liaotian")
                    .putExtra("liaotianId",livesDetailsBean.getId()+""));
                }else{
                    //买家登录
//                Intent intent = new Intent(GoodsDetailActivity.this, WebViewActivity.class);
                    intent.setClass(MessageDetailsActivity.this,WebViewActivity.class);
                    intent.putExtra("url", ConstantS.BASE_CHAT+"chat/chatWithSeller/" + livesDetailsBean.getId() + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&appName=" + SPHelper.getString(Configs.APPNAME) + "&loginName=" + UserHelper.getBuyerName()+ "&lang=" + SPHelper.getString(Configs.LANGUAGE));
                    startActivity(intent);
                }

                break;
        }
    }
    private void initData(){
        showLoading();
        points = new ArrayList<ImageView>();
        myViewPageradapter = new MyViewPageradapter();
        String id = getIntent().getStringExtra("id");
        imageList = new ArrayList<>();
        Map<String,String>map=new HashMap<>();
        map.put("informationId",id);
        if (!UserHelper.isLivesLogin()){
            //没登录
            mLogisticsDataConnection.getLivesDetails(getApplication(),map,-1);
        }else{
            mLogisticsDataConnection.getLivesDetails(getApplication(),map,UserHelper.getLivesID());
        }

        mLogisticsDataConnection.mOneDataListener=new OneDataListener() {
            @Override
            public void onSuccess(Object data, String successInfo) {
                livesDetailsBean = (LivesDetailsBean) data;
                for (LivesDetailsBean.ImageList ss: livesDetailsBean.getImageList()){
                    imageList.add(ss.getImagePath());
                }
                //设置数据
                lives_viewpagr.setAdapter(myViewPageradapter);
                addPoints(imageList.size());
                settingData();
                dissLoading();
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
                dissLoading();
            }

            @Override
            public void onFinish() {}
            @Override
            public void onLose() {
                dissLoading();
            }
        };
    }

    /**
     * 设置数据
     */
    private void  settingData(){
        tv_lives_title_content.setText(livesDetailsBean.getTitle());
        tv_lives_miaoshu_content.setText(livesDetailsBean.getContent());
        tv_details_time.setText(mTranslatesString.getCommon_fabu()+":"+livesDetailsBean.getCreateTime());
        if (livesDetailsBean.isMyInfo()){
            //是自己发布的就把发布隐藏
            rl_jubao.setVisibility(View.GONE);
        }else{
            rl_jubao.setVisibility(View.VISIBLE);
        }
    }
    //打电话
    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" +phone);
        intent.setData(data);
        startActivity(intent);
    }
    /**
     * 添加指示点的方法
     */
    private void addPoints(int geshu) {
        points.clear();
        dots_ll.removeAllViews();
        for (int i = 0; i < geshu; i++) {
            ImageView imageView = new ImageView(getApplication());
            imageView.setImageResource(R.drawable.huise);
            if(i ==0 ){
                imageView.setImageResource(R.drawable.baise);
            }
//                imageView.setImageResource(R.drawable.huise);

            //添加到线性布局中
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //添加间距
            params.leftMargin = 30;
            params.width=15;
            params.height=15;
            dots_ll.addView(imageView,params);
            points.add(imageView);
        }
    }

    class MyViewPageradapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
           ImageView imageView = (ImageView) View.inflate(getApplication(), R.layout.viewpager_item, null);
            imageView.setTag(position);
            ImageHelper.display(ConstantS.BASE_PIC_URL+imageList.get(position),imageView);
            container.addView(imageView);
            //点击轮播图的图片跳转到大图浏览界面
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test(ConstantS.BASE_PIC_URL+imageList.get(position));
                }
            });
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    /**
     * 获取网络图片进行大图浏览
     */
    private void test(String url) {
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        intent.putExtra("url", url);
        // intent.putExtra("smallPath", getSmallPath());
        intent.putExtra("indentify", getIdentify());
        this.startActivity(intent);
    }
    private int getIdentify() {
        return getResources().getIdentifier("test", "drawable",
                getPackageName());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhone(livesDetailsBean.getContactsMobile());
            } else
            {
                // Permission Denied
                Toast.makeText(getApplication(), mTranslatesString.getCommon_quanxian(), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
