package com.example.b2c.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.b2c.R;

/**
 * Created by ThinkPad on 2017/3/16.
 */

public abstract class DialogGetHeadPicture extends Dialog implements View.OnClickListener{
private Activity activity;
    private String one;
    private String two;
    private String srcc;
    private TextView tv_saoma;
    private TextView tv_shuru;

    public DialogGetHeadPicture(Activity context,String one,String two,String srcc) {
        super(context);
        this.activity = context;
        this.one=one;
        this.two=two;
        this.srcc=srcc;
    }

    public DialogGetHeadPicture(Activity context, int themeResId,String one,String two,String srcc) {
        super(context, themeResId);
        this.activity = context;
        this.one=one;
        this.two=two;
        this.srcc=srcc;
    }

    protected DialogGetHeadPicture(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
            protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.dialog_setting_get_head_picture);

        FrameLayout flt_amble_upload=(FrameLayout)findViewById(R.id.flt_amble_upload);
        FrameLayout flt_take_photo_upload=(FrameLayout)findViewById(R.id.flt_take_photo_upload);
       Button btn_cancel=(Button)findViewById(R.id.btn_cancel);
        tv_saoma = (TextView) findViewById(R.id.tv_saoma);
        tv_shuru = (TextView) findViewById(R.id.tv_shuru);
        tv_saoma.setText(one);
        tv_shuru.setText(two);
        btn_cancel.setText(srcc);
    flt_amble_upload.setOnClickListener(this);
    flt_take_photo_upload.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    setViewLocation();
    setCanceledOnTouchOutside(true);//外部点击取消  
       }

            /**  
          * 设置dialog位于屏幕底部  
          */
     private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
       activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window =this.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();

       lp.x=0;
        lp.y=height;
       lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置  
        onWindowAttributesChanged(lp);
//         window.setWindowAnimations(R.style.dialogWindowAnim);
        }

            @Override
            public void onClick(View v){
        switch(v.getId()){
           case R.id.flt_amble_upload:
          saoma();
          this.cancel();
           break;
            case R.id.flt_take_photo_upload:
            shuru();
            this.cancel();
            break;
            case R.id.btn_cancel:
            this.cancel();
            break;
            }
        }

            public abstract void saoma();
            public abstract void shuru();
}
